package com.sales.ops.serviceimpl.ips;

import com.alibaba.fastjson.JSON;
import com.sales.ops.common.opsexception.Exceptions;
import com.sales.ops.common.until.OPSRedisUtils;
import com.sales.ops.db.dao.*;
import com.sales.ops.db.entity.*;
import com.sales.ops.db.extdao.OpsRequisitionCNDao;
import com.sales.ops.db.extdao.PurchaseOrderDao;
import com.sales.ops.dto.ips.IpsReceiveOrderAllOriginalInfoDto;
import com.sales.ops.dto.ips.PsiDataType;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseCommonEnum;
import com.sales.ops.enums.ipsPurchase.IpsPurchaseSendSwitchEnum;
import com.sales.ops.feign.inqb.InqbCommonFeignApi;
import com.sales.ops.feign.ips.IpsPurchaseSendFeignApi;
import com.sales.ops.service.ips.IpsPurchaseSendCommonService;
import com.sales.ops.service.ips.PsiPurchaseSendJpService;
import com.sales.ops.service.mail.PurchaseMailService;
import com.sales.ops.service.purchase.MinPriceService;
import com.sales.ops.service.purchase.PurchaseExportTxtService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2025/10/23 14:06
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class PsiPurchaseSendJpServiceImpl implements PsiPurchaseSendJpService {

    @Resource
    private OpsPurchaseinvoiceMapper opsPurchaseinvoiceMapper;

    @Resource
    private PurchaseExportTxtService purchaseExportTxtService;

    @Resource
    private PurchaseMailService purchaseMailService;

    @Resource
    private IpsPurchaseSendFeignApi ipsPurchaseSendFeignApi;

    @Resource
    private OpsMaxinvoiceMapper opsMaxinvoiceMapper;

    @Resource
    private IpsPurchaseSendCommonService ipsPurchaseSendCommonService;

    @Resource
    private MinPriceService minPriceService;

    /**
     * 日本订单向psi发单
     *
     * @param likeString
     */
    @Override
    public ResultVo<String> psiJpOrderSend(List<OpsPurchaseorder> purchaseorders, String likeString, String email) throws Exception {
        // 1.先查询出，待发送的采购发票数据
        OpsPurchaseinvoiceExample expInvoiceam = new OpsPurchaseinvoiceExample();
        if (StringUtils.isNotBlank(likeString)) {
            expInvoiceam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP").andSendversionLike(likeString);
        } else {
            expInvoiceam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP");
        }
        List<OpsPurchaseinvoice> invoiceList = opsPurchaseinvoiceMapper.selectByExample(expInvoiceam);
        if (invoiceList == null || invoiceList.size() == 0) {
            return ResultVo.success("暂时没有待发送的日本订单");
        }
        // 2.增加字典开关的配置, 字典值：0= 只发老系统，1 = 只发送新系统，2=新老同时发
        ResultVo<PsiDataType> dictResult = ipsPurchaseSendCommonService.getSwitchflag(IpsPurchaseCommonEnum.JP_SWITCH.getCode());
        PsiDataType dictDataValues = dictResult.getData();
        String swithchConfigValue = dictDataValues.getExtNote1();
        String batchJPNo = "JP"; //发单日本时，默认批次号前缀为JP
        if (StringUtils.isNotBlank(dictResult.getData().getExtNote2())) {
            batchJPNo = dictResult.getData().getExtNote2(); //当note2的配置不为空时，采用note2中的配置
        }
        ResultVo<String> batchNoResultVo = ipsPurchaseSendCommonService.generateIpsOrderBatchNo(batchJPNo);
        if (batchNoResultVo.isSuccess()) {
            batchJPNo = batchNoResultVo.getData();
        }

        // 3.回更采购表状态，以及批次标识  bug修改点：先更新采购表标识，再执行发送操作
//        OpsPurchaseinvoiceExample exam = new OpsPurchaseinvoiceExample();
//        exam.createCriteria().andStatecodeEqualTo("0").andSupplieridEqualTo("JP").andSendversionLike(likeString);
//        OpsPurchaseinvoice record = new OpsPurchaseinvoice();
//        record.setStatecode("1");
//        record.setUpdatetime(new Date());
//        record.setSendtime(new Date());
//        record.setFilepath(pathJP);
//        record.setCnno(batchJPNo);
//        int result = opsPurchaseinvoiceMapper.updateByExampleSelective(record, exam);
        // 2.旧系统 或者 新旧同时发，调用老的生成方法
        String opsPathJP = "";
        if (IpsPurchaseSendSwitchEnum.OLD.getCode().equals(swithchConfigValue) || IpsPurchaseSendSwitchEnum.ALL.getCode().equals(swithchConfigValue)) {
            // 旧程序发送时，推送最低售价的清单
            minPriceService.minPriceOperate(purchaseorders);
            String textInfo = purchaseExportTxtService.textInfoJP(likeString);
            if (StringUtils.isNotBlank(textInfo) && StringUtils.isNotBlank(email)) {
                opsPathJP = purchaseExportTxtService.writeToTxtJP(textInfo);
            }
        }
        // 3.回更采购表状态，以及批次标识  bug修改点：先更新采购表标识，再执行发送操作
        ipsPurchaseSendCommonService.updatePurchaseInvoiceStatus(invoiceList, batchJPNo, opsPathJP);
        // 4.只发旧系统或者新旧同时发，调用TXT邮件发送方法
        if (StringUtils.isNotBlank(opsPathJP) && StringUtils.isNotBlank(email)) {
            // 调用日本邮件发送接口，发送TXT邮件给日本,// 直接调用发送邮件
            purchaseMailService.sendMailJP(opsPathJP, email);
        }
        // 5.只发新系统或者新旧同时发，调用PSI发送方法
        if (IpsPurchaseSendSwitchEnum.ALL.getCode().equals(swithchConfigValue) || IpsPurchaseSendSwitchEnum.NEW.getCode().equals(swithchConfigValue)) {
            // 获取发送PSI的批次号信息，由于要用该号码作为文件名的结尾
            // PSI批次号，采用CNNO批次号
//            if (StringUtils.isBlank(opsPathJP)) {
//                batchJPNo = jpMaxOrderNo("JP");
//            }
            // 发送给PSI新系统
            List<IpsReceiveOrderAllOriginalInfoDto> ipsSendOrderConvert = ipsPurchaseSendCommonService
                    .ipsSendOrderConvert(invoiceList, null, "JP", batchJPNo, null,null);
//            String batchCnno = ipsSendOrderConvert.get(0).getBatchNo(); //  CNNO批次号优化
            // 打印发送json信息
            log.info("JP发送到IPS的json信息：" + JSON.toJSONString(ipsSendOrderConvert));
            // 调用feign接口，发送到IPS表中,关务异常数据的推送
            CommonResult<String> ipsResult = ipsPurchaseSendFeignApi.insertPurchaseOrderToIps(ipsSendOrderConvert);
            if (!ipsResult.isSuccess()) { // 抛出异常回滚
                throw Exceptions.OpsException(ipsResult.getMessage());
            }
        }
        return ResultVo.success("PSI日本发单成功，共计发送：" + invoiceList.size());
    }

    /**
     * 获取当前导出文件名invNo
     *
     * @param supplierid
     * @return
     * @throws Exception
     */
    public String jpMaxOrderNo(String supplierid) throws Exception{
        // 获取当前导出文件名invNo
        String invNo = "";
        String fileEmail = "";
        OpsMaxinvoiceExample example = new OpsMaxinvoiceExample();
        example.createCriteria().andDescriptionEqualTo("日本").andPidEqualTo("D");
        List<OpsMaxinvoice> inv = opsMaxinvoiceMapper.selectByExample(example);
        if (inv != null && inv.size() > 0) {
            invNo = inv.get(0).getRemark();
        } else {
            throw new Exception("获取最大invNo失败！");
        }
        if (StringUtils.isBlank(invNo)) {
            invNo = "000";
        }
        // 更新invNo最大号
        OpsMaxinvoice temp = new OpsMaxinvoice();
        temp.setRemark(String.format("%03d", Integer.parseInt(invNo) + 1));
        example.clear();
        example.createCriteria().andPidEqualTo("D");
        opsMaxinvoiceMapper.updateByExampleSelective(temp, example);
        return invNo;
    }
}
