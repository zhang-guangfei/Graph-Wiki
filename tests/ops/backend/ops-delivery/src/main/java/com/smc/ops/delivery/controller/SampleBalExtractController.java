package com.smc.ops.delivery.controller;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.SampleBal;
import com.sales.ops.dto.expdetail.ExpdetailDto;
import com.sales.ops.dto.expdetail.SampleOrderApplyDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.ExpdetailSampleBalEnum;
import com.sales.ops.enums.OrderTypeEnum;
import com.smc.ops.delivery.mapper.DictdataDao;
import com.smc.ops.delivery.mapper.SampleBalExtractDao;
import com.smc.ops.delivery.service.barcode.SampleBalExtractService;
import com.smc.smccloud.log.annotation.SysLog;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 样品出库数据 抽取到sample_bal 表中
 */
@RestController
@RequestMapping(value = "/samplebal")
public class SampleBalExtractController {

    @Autowired
    private SampleBalExtractService sampleBalExtractService;

    @Resource
    private DictdataDao dictdataDao;

    @Autowired
    private SampleBalExtractDao sampleBalExtractDao;


    /**
     * 样品出库订单抽取，非拆分型号订单
     *
     * @return
     * @throws OpsException
     */
    @SysLog("样品出库，不拆分订单抽取")
    @RequestMapping("/unprodextract")
    public CommonResult<String> unprodextract() throws OpsException {
        // 查询不拆分的 样品出库清单
        List<ExpdetailDto> unProdFlagList = sampleBalExtractDao.getUnProdFlagData(OrderTypeEnum.YANGPIN, ExpdetailSampleBalEnum.CHUSHI);
        if (CollectionUtils.isNotEmpty(unProdFlagList)) {
            try {
                // 根据订单号+型号合并
                List<ExpdetailDto> newList = sampleBalExtractService.mergeByOrderNoWithModelNo(unProdFlagList);
                // 获取字典参数
                Map<String, String> map = sampleBalExtractService.getDictData(ExpdetailSampleBalEnum.DICT_CODE_SAMPLEORDER_APPTYPE);
                if (map == null) {
                    return CommonResult.failure("无法获取字典参数配置信息");
                }
                SampleOrderApplyDto sampleOrderApplyDto;
                SampleBal sampleBal;
                for (ExpdetailDto item : newList) {
                    // 查找rcvdetail 接单数据做对比
                    Rcvdetail rcvdetail = sampleBalExtractService.findRcvDetail(item);
                    if (rcvdetail == null) {
                        continue;
                    }
                    List<SampleBal> samplebalList = sampleBalExtractService.getSampleBalList(item.getOrderFno());
                    // 判断是否为拆分型号订单，再校验数量
                    int alreadyBalNum = 0;
                    if (CollectionUtils.isNotEmpty(samplebalList)) {
                        alreadyBalNum = samplebalList.stream().filter(i -> i.getQuantity() != null).mapToInt(SampleBal::getQuantity).sum();
                    }
                    // 判断是否整型号 控制写入sample_bal里面的整型号数量不能超过rcvdetail的接单数量
                    if (StringUtils.isBlank(item.getModelNo()) || !item.getModelNo().trim().equals(rcvdetail.getModelNo().trim())
                            || ((item.getQuantity() + alreadyBalNum) > rcvdetail.getQuantity())) {
                        // 更新出库表异常状态
                        sampleBalExtractService.upExpdetailOptCodeById(item.getIdList(), ExpdetailSampleBalEnum.YICHANG);
                        //异常数据，加入邮件提醒
                        String message = "样品订单：" + item.getOrderFno() + ",出库待抽取数据加sample_bal中的数据，超过rcvdetail的接单数量，请排查！ ";
                        sampleBalExtractService.sendErrorMail(message);
                        continue;
                    }
                    sampleBal = new SampleBal();
                    // 根据expdetail 订单号 项号 查样品申请主表
                    sampleOrderApplyDto = sampleBalExtractService.getSampleApply(item.getOrderFno());
                    // 写入sampleBal 表
                    sampleBal = sampleBalExtractService.InsertSampleBal(sampleOrderApplyDto, item, samplebalList, map, ExpdetailSampleBalEnum.BUCHAIFEN);
                    // 更改expDetail的状态 1 -> 2
                    sampleBalExtractService.upExpdetailOptCodeById(item.getIdList(), ExpdetailSampleBalEnum.WANCHENG);
                    // 写入日志??
                    sampleBalExtractService.sendOrderLog(item);
                }
            }
            catch (Exception e) {
                String message = "样品订单抽取数据到sample_bal出错：" + e.getMessage();
                sampleBalExtractService.sendErrorMail(message);
                return CommonResult.failure(e.getMessage());
            }
        }
        return CommonResult.success("不拆分数据结算成功! 共计: " + unProdFlagList.size() +" 项");
    }


    /**
     * 样品出库订单抽取，拆分型号订单
     * 拆分型号订单只抽取一次，当订单状态变为 已发货时，取得整数量抽取到 sampleBal中
     *
     * @return
     * @throws OpsException
     */
    @SysLog("样品出库，拆分订单抽取")
    @RequestMapping("/prodextract")
    public CommonResult<String> prodextract() throws OpsException {
//        return sampleBalExtractService.extractSampleBal(true);
        // 查询不拆分的 样品出库清单
        List<ExpdetailDto> prodFlagList = sampleBalExtractDao.getProdFlagData(OrderTypeEnum.YANGPIN, ExpdetailSampleBalEnum.CHUSHI);
        if (CollectionUtils.isNotEmpty(prodFlagList)) {
            try {
                // 获取字典参数
                Map<String, String> map = sampleBalExtractService.getDictData(ExpdetailSampleBalEnum.DICT_CODE_SAMPLEORDER_APPTYPE);
                if (map == null) {
                    return CommonResult.failure("无法获取字典参数配置信息");
                }
                SampleOrderApplyDto sampleOrderApplyDto;
                SampleBal sampleBal;
                for (ExpdetailDto item : prodFlagList) {
                    // 查找rcvdetail 接单数据做对比
                    Rcvdetail rcvdetail = sampleBalExtractService.findRcvDetail(item);
                    if (rcvdetail == null) {
                        continue;
                    }
                    List<SampleBal> samplebalList = sampleBalExtractService.getSampleBalList(item.getOrderFno());

                    // 判断 拆分型号订单，是否在 结转表中已经存在，存在时提示异常，因为拆分型号订单只抽取一次
                    if (CollectionUtils.isNotEmpty(samplebalList)) {
                        // 更新出库表异常状态
                        sampleBalExtractService.updateOptCodeProdFlag(item.getOrderFno(), ExpdetailSampleBalEnum.YICHANG);
                        //异常数据，加入邮件提醒
                        String message = "样品订单：" + item.getOrderFno() + "拆分型号，样品数据抽取到结转表重复抽取，请排查！ ";
                        sampleBalExtractService.sendErrorMail(message);
                        continue;
                    }

                    sampleBal = new SampleBal();
                    // 根据expdetail 订单号 项号 查样品申请主表
                    sampleOrderApplyDto = sampleBalExtractService.getSampleApply(item.getOrderFno());
                    // 写入sampleBal 表
                    sampleBal = sampleBalExtractService.InsertSampleBal(sampleOrderApplyDto, item, samplebalList, map, ExpdetailSampleBalEnum.CHAIFEN);
                    // 更改expDetail的状态 1 -> 2
                    sampleBalExtractService.updateOptCodeProdFlag(item.getOrderFno(), ExpdetailSampleBalEnum.WANCHENG);
                    // 写入日志??
                    sampleBalExtractService.sendOrderLog(item);
                }
            }
            catch (Exception e) {
                // 异常报错时，加入邮件提醒
                String message = "样品订单抽取数据到sample_bal出错：" + e.getMessage();
                sampleBalExtractService.sendErrorMail(message);
                return CommonResult.failure(e.getMessage());
            }
        }
        return CommonResult.success("拆分型号出库数据结算成功! 共计: " + prodFlagList.size() +" 项");
    }

}
