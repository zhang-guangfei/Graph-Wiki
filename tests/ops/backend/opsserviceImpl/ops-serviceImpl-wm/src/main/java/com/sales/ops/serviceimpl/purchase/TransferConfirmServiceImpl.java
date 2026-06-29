package com.sales.ops.serviceimpl.purchase;

import com.sales.ops.db.dao.TransferInfoMapper;
import com.sales.ops.db.entity.ImpInvoiceDetail;
import com.sales.ops.db.entity.ImpInvoiceMaster;
import com.sales.ops.db.entity.InvoiceSplit;
import com.sales.ops.db.entity.TransferInfo;
import com.sales.ops.db.extdao.SplitSmcCodeDao;
import com.sales.ops.dto.expdetail.TransferVO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.service.purchase.TransferConfirmService;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $ bugid:19186 c14717 20251127 合并smccode 转运信息确认
 * @description：
 * @date ：Created in 2025/11/27 10:32
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TransferConfirmServiceImpl implements TransferConfirmService {

    @Autowired
    private SplitSmcCodeDao splitSmcCodeDao;

    @Autowired
    private TransferInfoMapper transferInfoMapper;
    @Autowired
    private PurchaseEventPublisher purchaseEventPublisher;



    @Override
    public CommonResult<String> handleTransferConfirm(TransferVO param){
        if(param.getStatus() == 1){
            return signTransferConfirm(param);
        }else {
            return shipTransferConfirm(param);
        }
    }

    // 1.签收写入发运信息 需要加锁 发票号加锁
    public CommonResult<String> signTransferConfirm(TransferVO param) {
        // 验证发票是否存在  imp_invoice_master  近90天的数据
        Integer countImpInvoiceMaster = splitSmcCodeDao.countImpInvoiceMaster(param.getOriginalInvoiceNo());
        if(Objects.nonNull(countImpInvoiceMaster) && countImpInvoiceMaster != 0){
            ImpInvoiceMaster impInvoiceMaster = splitSmcCodeDao.getImpInvoiceMaster(param.getOriginalInvoiceNo());
            Integer countransferInfo = splitSmcCodeDao.countTransferInfo(param.getOriginalInvoiceNo());
            if(Objects.nonNull(countransferInfo) && countransferInfo != 0){
                return CommonResult.failure(400,"不可重复签收");
            }
            TransferInfo transferInfo = getSignInfo(param,impInvoiceMaster.getId(),impInvoiceMaster.getInvoiceNo());
            transferInfoMapper.insertSelective(transferInfo);
            //通过母发票号，查询子发票号
            List<InvoiceSplit> invoiceSplitList = splitSmcCodeDao.findInvoiceSplit(param.getOriginalInvoiceNo());
            for(InvoiceSplit obj : invoiceSplitList){
                //通过子发票号，查询采购单号
                List<ImpInvoiceDetail> list = splitSmcCodeDao.getImpInvoiceDetailList(obj.getSplitInvoiceId());
                for (ImpInvoiceDetail invoiceDetail : list) {
                    purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_TRANSFER, invoiceDetail);
                }
            }
            return CommonResult.success();
        }
        //  验证发票是否存在  invoice_split  近90天的数据
        Integer countInvoiceSplit = splitSmcCodeDao.countInvoiceSplit(param.getOriginalInvoiceNo());
        if(Objects.nonNull(countInvoiceSplit) && countInvoiceSplit != 0){
            List<InvoiceSplit> invoiceSplitList = splitSmcCodeDao.findInvoiceSplit(param.getOriginalInvoiceNo());
            for(InvoiceSplit obj : invoiceSplitList){
                Integer countransferInfo = splitSmcCodeDao.countTransferInfo(obj.getSplitInvoiceNo());
                if(Objects.nonNull(countransferInfo) && countransferInfo != 0){
                    return CommonResult.failure(400,"不可重复签收");
                }
            }
            for(InvoiceSplit obj : invoiceSplitList){
                TransferInfo transferInfo = getSignInfo(param, obj.getSplitInvoiceId(),obj.getSplitInvoiceNo());
                transferInfoMapper.insertSelective(transferInfo);
            }
            //C12961 查询子发票号，创建事件
            for(InvoiceSplit obj : invoiceSplitList){
                List<ImpInvoiceDetail> list = splitSmcCodeDao.getImpInvoiceDetailList(obj.getSplitInvoiceId());
                for (ImpInvoiceDetail invoiceDetail : list) {
                    purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_TRANSFER, invoiceDetail);
                }
            }
            return CommonResult.success();
        }
        return CommonResult.failure(400,"发票号不存在");
    }

    private TransferInfo getSignInfo(TransferVO param, Long invoiceId , String invoiceNo) {
        TransferInfo transferInfo = new TransferInfo();
        transferInfo.setInvoiceId(invoiceId);
        transferInfo.setInvoiceNo(invoiceNo);
        transferInfo.setCreator(param.getUpdator());
        transferInfo.setSignUser(param.getUpdator());
        transferInfo.setSignWarehouse(param.getSignWarehouse());
        transferInfo.setLogisticsStatus(param.getStatus());
        transferInfo.setSignDate(param.getOperationDate());
        return transferInfo;
    }


    // 2.发货
    public CommonResult<String> shipTransferConfirm(TransferVO param){
        Integer countransferInfo = splitSmcCodeDao.countTransferInfo(param.getOriginalInvoiceNo());
        String splitInvoiceNo = param.getOriginalInvoiceNo()+"-"+param.getEndReceiveWarehouse();
        Integer countransferInfoSplit = splitSmcCodeDao.countTransferInfo(splitInvoiceNo);
        if(Objects.isNull(countransferInfo) ){
            countransferInfo = 0;
        }
        if(Objects.isNull(countransferInfoSplit) ){
            countransferInfoSplit = 0;
        }
        if((countransferInfo + countransferInfoSplit) == 0){
            return CommonResult.failure(400,"无发票信息");
        }
        if(countransferInfo>0 && countransferInfoSplit > 0){
            return CommonResult.failure(400,"整票和拆分票同时存在");
        }

        String invoiceNo = param.getOriginalInvoiceNo();
        if(countransferInfoSplit >0 ){
            invoiceNo = splitInvoiceNo;
        }
        Integer countTransferInfoExpress = splitSmcCodeDao.countTransferInfoExpress(invoiceNo, param.getExpressCode());
        if(Objects.nonNull(countTransferInfoExpress) && countTransferInfoExpress != 0){
            return CommonResult.failure(400,"发票和运单号重复回传");
        }
        List<TransferInfo> transferInfos = splitSmcCodeDao.getTransferInfoExpress(invoiceNo);
        if(transferInfos.size() == 1 && transferInfos.get(0).getLogisticsStatus()==1){
            // 更新
            TransferInfo transferInfo = getShipTransferInfo(param, transferInfos);
            transferInfoMapper.updateByPrimaryKeySelective(transferInfo);
        }else {
            // 写入
            TransferInfo transferInfo = getShipTransferInfo(param, transferInfos);
            transferInfoMapper.insertSelective(transferInfo);
        }
        //C12961 查询子发票号，创建事件
        List<InvoiceSplit> invoiceSplitList = splitSmcCodeDao.findInvoiceSplit(param.getOriginalInvoiceNo());
        for(InvoiceSplit obj : invoiceSplitList){
            List<ImpInvoiceDetail> list = splitSmcCodeDao.getImpInvoiceDetailList(obj.getSplitInvoiceId());
            for (ImpInvoiceDetail invoiceDetail : list) {
                purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_INVOICE_TRANSFER, invoiceDetail);
            }
        }
        return CommonResult.success();
    }

    private TransferInfo getShipTransferInfo(TransferVO param, List<TransferInfo> transferInfos) {
        TransferInfo transferInfo = transferInfos.get(0);
        transferInfo.setCreator(param.getUpdator());
        transferInfo.setSignUser(transferInfo.getSignUser());
        transferInfo.setSignDate(transferInfo.getSignDate() );
        transferInfo.setSignWarehouse(param.getSignWarehouse());
        transferInfo.setLogisticsStatus(param.getStatus());
        transferInfo.setShipDate(param.getOperationDate());
        transferInfo.setEndReceiveWarehouse(param.getEndReceiveWarehouse());
        transferInfo.setCarried(param.getCarried());
        transferInfo.setExpressCode(param.getExpressCode());
        return transferInfo;
    }
}
