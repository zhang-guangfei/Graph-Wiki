package com.sales.ops.serviceimpl.inventory;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.rabbitmq.RabbitMqMessage;
import com.sales.ops.db.dao.ImpInvoiceMasterMapper;
import com.sales.ops.db.dao.OpsPoInvoiceMapper;
import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.InvoiceSplit;
import com.sales.ops.db.entity.OpsPoInvoice;
import com.sales.ops.db.extdao.ImpInvoiceMasterDao;
import com.sales.ops.db.extdao.SplitSmcCodeDao;
import com.sales.ops.enums.invoice.ImpInvoiceConstants;
import com.sales.ops.rabbitmq.SendMessage;
import com.sales.ops.service.inventory.ReceiveGoodsService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.invoice.ImpInvoiceProcessDTO;
import com.smc.smccloud.model.invoice.ImpInvoiceReceiveDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/7 16:20
 */
@Slf4j
@Service
public class ReceiveGoodsServiceImpl implements ReceiveGoodsService {

    @Autowired
    private SendMessage sendMessage;

    @Autowired
    private ImpInvoiceMasterMapper impInvoiceMasterMapper;

    @Autowired
    private ImpInvoiceMasterDao impInvoiceMasterDao;

    @Autowired
    private OpsPoInvoiceMapper opsPoInvoiceMapper;

    @Autowired
    private SplitSmcCodeDao splitSmcCodeDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceMaster getImpInvoiceMasterById(Long invoiceId){
        return impInvoiceMasterMapper.selectByPrimaryKey(invoiceId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ImpInvoiceMaster getLastImpInvoiceMaster(String invoiceNo, String supplierNo) {
        return impInvoiceMasterDao.getLastImpInvoiceMaster(invoiceNo, supplierNo);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public OpsPoInvoice getPoInvoiceOne(Long invoiceId) {
        return impInvoiceMasterDao.getPoInvoiceOne(invoiceId);
    }



    /**
     * budid:17829 c14717 20250707 order 解耦
     * 收货确认 //把发票从预计到货改成已收货待入库 //调用关务系统回调接口更新已收货
     * <p>
     * //触发自动入库调用入库方法--后期加
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> receiveGoods(ImpInvoiceReceiveDTO dto) {
        log.info("receiveGood:" + JSON.toJSONString(dto));
        if (PublicUtil.isEmpty(dto.getReceiveTime())) {
            return ResultVo.failure("签收时间不能为空" + dto.getInvoiceNo());
        }
        ImpInvoiceMaster invoiceMasterDO;
        if (dto.getInvoiceId() != null && dto.getInvoiceId() != 0) {
            invoiceMasterDO = getImpInvoiceMasterById(dto.getInvoiceId());
        } else {
            invoiceMasterDO = getLastImpInvoiceMaster(dto.getInvoiceNo(), dto.getSupplierNo());
        }
        if (invoiceMasterDO == null) {
            return ResultVo.failure(dto.getInvoiceNo() + "无发票数据无法签收");
        }
        if (invoiceMasterDO.getStatus() != 3 && invoiceMasterDO.getStatus() != 6) {
            // 20230406 发送异步消息重新再检查一次发票是否入库完成和补充数据
            sendImpInvocieConfirmProcessMsg(invoiceMasterDO);
            return ResultVo.failure(dto.getInvoiceNo() + "还没发票入库，不可以收货,重试一次");
        }
        ImpInvoiceMaster updImpInvoiceMasterDo = new ImpInvoiceMaster();
        updImpInvoiceMasterDo.setId(invoiceMasterDO.getId());
        updImpInvoiceMasterDo.setUpdateTime(DateUtil.getNow());
        updImpInvoiceMasterDo.setArrivedWarehouseCode(dto.getReceiveWarehouseCode());
        updImpInvoiceMasterDo.setArriveDate(dto.getReceiveTime());
        updImpInvoiceMasterDo.setUpdateUser("wms-rcv");
        impInvoiceMasterMapper.updateByPrimaryKeySelective(updImpInvoiceMasterDo);

        if (StringUtils.isNotBlank(invoiceMasterDO.getCinvoiceNo())) {//
            // 发送异步处理关务调用
            ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
            processDTO.setInvoiceNo(invoiceMasterDO.getCinvoiceNo());
            processDTO.setReceiveTime(dto.getReceiveTime());
            processDTO.setProcessType(7);
            sendInvoiceProcessMsgToMQ(processDTO);
        }
        // 已发票入库待转成本还需要更新发票入库表单签收时间
        if (invoiceMasterDO.getStatus() == 3) {
            // selectOne底层代码逻辑是调用的selectList,语句加top 1，查一条数据即返回 begin bug8732
            OpsPoInvoice poInvoiceDO = getPoInvoiceOne(invoiceMasterDO.getId());
            // bugid 19186 合并smccode
            if(Objects.isNull(poInvoiceDO)){
                InvoiceSplit invoiceSplit = splitSmcCodeDao.getInvoiceSplitListById(invoiceMasterDO.getId());
                if(Objects.nonNull(invoiceSplit)){
                    Long mergeInvoiceId = invoiceSplit.getMergeInvoiceId();
                    poInvoiceDO = getPoInvoiceOne(mergeInvoiceId);
                }
            }
            // begin bug8732　
            if (poInvoiceDO != null && poInvoiceDO.getStatus() == 1) {
                OpsPoInvoice updOpsPoInvoiceDo = new OpsPoInvoice();
                updOpsPoInvoiceDo.setId(poInvoiceDO.getId());
                updOpsPoInvoiceDo.setReceiveTime(dto.getReceiveTime());
                updOpsPoInvoiceDo.setArrivedWarehouseCode(dto.getReceiveWarehouseCode());
                updOpsPoInvoiceDo.setUpdateTime(DateUtil.getNow());
                updOpsPoInvoiceDo.setUpdateUser("rcv");
                updOpsPoInvoiceDo.setStatus(2);
                opsPoInvoiceMapper.updateByPrimaryKeySelective(updOpsPoInvoiceDo);

                // 发送发票处理消息
                ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
                processDTO.setInvoiceId(poInvoiceDO.getInvoiceId());
                processDTO.setProcessType(2);
                sendInvoiceProcessMsgToMQ(processDTO);
            } else {
                log.error("更新成本签收时间失败,成本数据不存在或状态不对" + dto.getInvoiceNo());
                if (poInvoiceDO == null) {
                    // 20230406 发送异步消息重新再检查一次发票是否入库完成和补充数据
                    sendImpInvocieConfirmProcessMsg(invoiceMasterDO);
                }
            }
        }
        return ResultVo.success("收货成功" + invoiceMasterDO.getInvoiceNo());
    }


    public void sendImpInvocieConfirmProcessMsg(ImpInvoiceMaster invoiceMasterDO) {
        ImpInvoiceProcessDTO processDTO = new ImpInvoiceProcessDTO();
        processDTO.setInvoiceNo(invoiceMasterDO.getInvoiceNo());
        processDTO.setReceiveTime(invoiceMasterDO.getConfirmDate());
        processDTO.setInvoiceId(invoiceMasterDO.getId());
        processDTO.setProcessType(8);
        processDTO.setRemark("完成发票入库");
        processDTO.setOptTime(invoiceMasterDO.getConfirmDate());
        processDTO.setOptUser(invoiceMasterDO.getUpdateUser());
        sendInvoiceProcessMsgToMQ(processDTO);
    }
    public void sendInvoiceProcessMsgToMQ(ImpInvoiceProcessDTO dto){
        RabbitMqMessage mqMsg = new RabbitMqMessage();
        mqMsg.setContent(JSON.toJSONString(dto));
        mqMsg.setFlag(ImpInvoiceConstants.OPS_INVOICE_PROSECC);
        mqMsg.setDataType(ImpInvoiceConstants.OPS_INVOICE);
        mqMsg.setSystem(ImpInvoiceConstants.OPS);
        sendMessage.sendInvoiceProcessMsg(mqMsg);
    }
}
