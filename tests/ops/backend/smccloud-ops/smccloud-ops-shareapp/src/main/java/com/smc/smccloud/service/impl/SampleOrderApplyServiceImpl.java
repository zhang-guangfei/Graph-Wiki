package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.sales.ops.enums.SendStatusEnum;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.DeliveryPlaceEnum;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.model.enums.TradeCompanyIdEnum;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.ExpdetailMapper;
import com.smc.smccloud.mapper.RcvdetailMapper;
import com.smc.smccloud.mapper.RcvmasterMapper;
import com.smc.smccloud.mapper.TaskNoticCommonMapper;
import com.smc.smccloud.mapper.salesInvoice.SalesInvoiceMidInfoMapper;
import com.smc.smccloud.mapper.sampleorder.*;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.OrderSales.OrderDlvDataVO;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.delivery.DeliveryInfo;
import com.smc.smccloud.model.delivery.DeliveryModelInfo;
import com.smc.smccloud.model.enums.BalTypeEnum;
import com.smc.smccloud.model.enums.SampleBalAppTypeEnum;
import com.smc.smccloud.model.enums.SampleBalOptCodeEnum;
import com.smc.smccloud.model.enums.SampleOrderApplyStatusEnum;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.order.SampleBalApplyCallBackVO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.product.ProductPriceVO;
import com.smc.smccloud.model.receiveorder.OpsOrderAssignResultVO;
import com.smc.smccloud.model.receiveorder.RcvDetailVO;
import com.smc.smccloud.model.receiveorder.RcvMasterVO;
import com.smc.smccloud.model.receiveorder.RcvOrderDTO;
import com.smc.smccloud.model.salesinvoice.SalesExpDO;
import com.smc.smccloud.model.salesinvoice.SalesInvoiceMidInfoDO;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.sampleorder.*;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.PriceCompute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2021-10-27
 */
@Service
@Slf4j
public class SampleOrderApplyServiceImpl extends ServiceImpl<SampleOrderApplyMapper, SampleOrderApplyDO> implements SampleOrderApplyService {

    @Resource
    private SampleOrderApplyMapper sampleorderApplyMapper;
    @Resource
    private SampleorderDetailMapper sampleorderDetailMapper;
    @Resource
    private SamplebalMapper samplebalMapper;
    @Resource
    private ReceiveOrderServiceFeignApi receiveOrderServiceFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;
    @Resource
    private OrderLogFeignApi orderLogFeignApi;
    @Resource
    private PlatformTransactionManager transactionManager;
    @Resource
    private ExpdetailServiceFeignApi expdetailServiceFeignApi;
    @Resource
    private SalesInvoiceMidInfoMapper salesInvoiceMidInfoMapper;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private RedisManager redisManager;
    @Resource
    private SalesExpService salesExpService;
    @Resource
    private SampleOrderDetailService sampleOrderDetailService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private SampleBalService sampleBalService;
    @Resource
    private CommonService commonService;
    @Resource
    private SampleOrderManageMapper sampleOrderManageMapper;
    @Resource
    private SampleOrderManageService sampleOrderManageService;
    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;
    @Value("${file.base}")
    private String serverPath;

    @Value("${ops-file-upload-path.url}")
    private String filePath;
    @Resource
    private RcvdetailMapper rcvdetailMapper;
    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private RcvmasterMapper rcvmasterMapper;
    @Resource
    private GroupCompanyService groupCompanyService;

    @Resource
    private TaskNoticCommonMapper taskNoticCommonMapper;

    @Resource
    private SampleBalPropertyAssignMapper sampleBalPropertyAssignMapper;

    @Resource
    private SampleBalApplyMapper sampleBalApplyMapper;


    @Override
    public ResultVo<PageInfo<SampleOrderVO>> listSampleOrderData(SampleOrderRequest request, Page page) {

        PageInfo<SampleOrderVO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> sampleorderApplyMapper.selectJoinList(SampleOrderVO.class,
                        new MPJLambdaWrapper<SampleOrderApplyDO>()
                                .select(SampleOrderApplyDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("id"))
                                .select(SampleorderDetailDO.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("rorder_no")
                                        && !tableFieldInfo.getColumn().equals("create_time") && !tableFieldInfo.getColumn().equals("create_user")
                                        && !tableFieldInfo.getColumn().equals("update_time") && !tableFieldInfo.getColumn().equals("update_user")
                                        && !tableFieldInfo.getColumn().equals("remark") && !tableFieldInfo.getColumn().equals("order_no"))
                                .selectAs(SampleorderDetailDO::getId, SampleOrderVO::getSdId)
                                .leftJoin(SampleorderDetailDO.class, SampleorderDetailDO::getApplyId, SampleOrderApplyDO::getId)
                                .likeRight(PublicUtil.isNotEmpty(request.getApplyNo()), SampleOrderApplyDO::getApplyNo, request.getApplyNo())
                                .likeRight(PublicUtil.isNotEmpty(request.getOrderNo()), SampleOrderApplyDO::getOrderNo, request.getOrderNo())
                                .eq(PublicUtil.isNotEmpty(request.getStatus()), SampleOrderApplyDO::getStatus, request.getStatus())
                                .eq(PublicUtil.isNotEmpty(request.getCostStatus()), SampleorderDetailDO::getCostStatus, request.getCostStatus())
                                .eq(PublicUtil.isNotEmpty(request.getApplyType()), SampleOrderApplyDO::getApplyType, request.getApplyType())
                                .eq(PublicUtil.isNotEmpty(request.getCostType()), SampleOrderApplyDO::getCostType, request.getCostType())
                                .in(CollectionUtils.isNotEmpty(request.getApplyDeptNo()), SampleOrderApplyDO::getApplyDeptNo, request.getApplyDeptNo())
                                .in(CollectionUtils.isNotEmpty(request.getDeptNos()), SampleOrderApplyDO::getDeptNo, request.getDeptNos())
                                .likeRight(PublicUtil.isNotEmpty(request.getModelNo()), SampleorderDetailDO::getModelNo, request.getModelNo())
                                .likeRight(PublicUtil.isNotEmpty(request.getCmodelNo()), SampleorderDetailDO::getCmodelNo, request.getCmodelNo())
                                .likeRight(PublicUtil.isNotEmpty(request.getUserNo()), SampleOrderApplyDO::getUserNo, request.getUserNo())
                                .ge(PublicUtil.isNotEmpty(request.getApplyTimeStart()), SampleOrderApplyDO::getApplyTime, request.getApplyTimeStart())
                                .le(PublicUtil.isNotEmpty(request.getApplyTimeEnd()), SampleOrderApplyDO::getApplyTime, request.getApplyTimeEnd())
                                .ge(PublicUtil.isNotEmpty(request.getOrderDateStart()), SampleOrderApplyDO::getOrderDate, request.getOrderDateStart())
                                .le(PublicUtil.isNotEmpty(request.getOrderDateEnd()), SampleOrderApplyDO::getOrderDate, request.getOrderDateEnd())
                                .ge(PublicUtil.isNotEmpty(request.getAnswerTimeStart()), SampleOrderApplyDO::getAnswerTime, request.getAnswerTimeStart())
                                .le(PublicUtil.isNotEmpty(request.getAnswerTimeEnd()), SampleOrderApplyDO::getAnswerTime, request.getAnswerTimeEnd())
                ));
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String,String> name = new HashMap<>(pageInfo.getList().size());
            for (SampleOrderVO item : pageInfo.getList()) {
                // 费用承担部门
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    if (!name.containsKey(item.getDeptNo())) {
                        String deptNameByNo = commonService.getDeptNameByNo(item.getDeptNo());
                        if (StringUtils.isNotBlank(deptNameByNo)) {
                            name.put(item.getDeptNo(),deptNameByNo);
                        }
                    }
                    item.setDeptNo(name.get(item.getDeptNo()) + "[" + item.getDeptNo() + "]");
                }

                // 申请部门
                if (StringUtils.isNotBlank(item.getApplyDeptNo())) {
                    if (!name.containsKey(item.getApplyDeptNo())) {
                        String deptNameByNo = commonService.getDeptNameByNo(item.getApplyDeptNo());
                        if (StringUtils.isNotBlank(deptNameByNo)) {
                            name.put(item.getApplyDeptNo(),deptNameByNo);
                        }
                    }
                    item.setApplyDeptNo(name.get(item.getApplyDeptNo()) + "[" + item.getApplyDeptNo() + "]");
                }

            }
        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportSampleOrderApplyData(SampleOrderRequest request) {
        Page page = new Page();
        page.setPageNumber(1);
        page.setPageSize(com.smc.smccloud.model.constants.Constants.max_export_num);
        ResultVo<PageInfo<SampleOrderVO>> pageInfoResultVo = listSampleOrderData(request, page);
        if (pageInfoResultVo == null || pageInfoResultVo.getData() == null || CollectionUtils.isEmpty(pageInfoResultVo.getData().getList())) {
            return;
        }
        List<SampleOrderVO> list = pageInfoResultVo.getData().getList();
        List<ExportSampleOrderVO> exportSampleOrderVOS = BeanCopyUtil.copyList(list, ExportSampleOrderVO.class);
        for (ExportSampleOrderVO item : exportSampleOrderVOS) {
            item.setStatusName(SampleOrderApplyStatusEnum.getCodeNameByCode(item.getStatus()));

            item.setCostTypeName(BalTypeEnum.getCodeName(item.getCostType()));

            item.setApplyTypeName(SampleBalAppTypeEnum.getCodeName(item.getApplyType()));

            item.setOrderDateStr(DateUtil.dateToDateTimeString(item.getOrderDate()));

            item.setDlvDateStr(DateUtil.dateToDateTimeString(item.getDlvDate()));

            item.setApplyTimeStr(DateUtil.dateToDateTimeString(item.getApplyTime()));

            item.setAnswerTimeStr(DateUtil.dateToDateTimeString(item.getAnswerTime()));
        }

        try {
            String fileName = URLEncoder.encode("样品订单数据导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), ExportSampleOrderVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(exportSampleOrderVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    @Override
    public ResultVo<String> createOrderBySampleOrder(SampleOrderParams sampleOrderParams) {

        if (sampleOrderParams == null || sampleOrderParams.getApplyIds().length == 0) {
            return ResultVo.failure("请选择待生成订单的数据");
        }
        // 查税率
        BigDecimal taxRate;
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "1");
        if (PublicUtil.isNotEmpty(dataTypeCodesInfo.getData())) {
            taxRate = new BigDecimal(dataTypeCodesInfo.getData().getExtNote1());
        } else {
            taxRate = new BigDecimal("0.13");
        }

        // 失败原因
        AtomicReference<String> result = new AtomicReference<>("");
        AtomicReference<Boolean> isSuccess = new AtomicReference<>(true);

        AtomicReference<LoginUserDTO> userDTO = new AtomicReference<>();
        try {
            userDTO.set(SMCApp.getLoginAuthDto());
        } catch (Exception e) {
            userDTO = null;
        }

        for (String id : sampleOrderParams.getApplyIds()) {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            AtomicReference<LoginUserDTO> finalUserDTO = userDTO;
            transactionTemplate.execute(status ->
            {
                // 根据id查sampleOrder_apply
                SampleOrderApplyDO sampleOrderApplyDO = sampleorderApplyMapper.selectById(id);
                // 状态为5 代表已经生成订单
                if (sampleOrderApplyDO != null && sampleOrderApplyDO.getStatus() != 5) {
                    if (sampleOrderApplyDO.getStatus() != 3) {
                        isSuccess.set(false);
                        result.set("状态为处理中才可进行生成订单操作");
                        return ResultVo.failure("状态为处理中才可进行生成订单操作");
                    }
                    // 根据applyId 查 sampleOrder_detail
                    QueryWrapper<SampleorderDetailDO> detailDOQuery = new QueryWrapper<>();
                    detailDOQuery.eq("apply_id", id);
                    List<SampleorderDetailDO> sampleorderDetailDOList = sampleorderDetailMapper.selectList(detailDOQuery);
                    if (sampleorderDetailDOList.isEmpty()) {
                        isSuccess.set(false);
                        result.set("样品详细订单为空");
                        return ResultVo.failure("样品详细订单为空");
                    }

                    String rorderNo;
                    if (PublicUtil.isEmpty(sampleOrderApplyDO.getOrderNo())) {
                        // 生成订单号
                        ResultVo<String> randomOrderNoGenerator = dictDataServiceFeignApi.getRandomOrderNoGenerator("9001", "1");
                        if (!randomOrderNoGenerator.isSuccess()) {
                            isSuccess.set(false);
                            result.set("未成功生成订单号,生成订单失败");
                            return ResultVo.failure("未成功生成订单号,生成订单失败");
                        }
                        rorderNo = randomOrderNoGenerator.getData();
                    } else {
                        rorderNo = sampleOrderApplyDO.getOrderNo();
                    }

                    // sampleOrderApplyDO => rcvMaster
                    if (StringUtils.isBlank(sampleOrderApplyDO.getCustomerNo())) {
                        return ResultVo.failure("申请号:" + sampleOrderApplyDO.getApplyNo() + ",申请类型:" + sampleOrderApplyDO.getApplyType() + ",客户代码不可为空.");
                    }
                    RcvMasterVO rcvMasterVO = BeanCopyUtil.copy(sampleOrderApplyDO, RcvMasterVO.class);

                    if(sampleOrderApplyDO.getAddressType() != null) {
                        rcvMasterVO.setDlvSite(String.valueOf(sampleOrderApplyDO.getAddressType()));
                    }

                    rcvMasterVO.setRorderNo(rorderNo);
                    if (StringUtils.isBlank(rcvMasterVO.getEndUser())) {
                        if (StringUtils.isBlank(rcvMasterVO.getUserNo())) {
                            rcvMasterVO.setEndUser(rcvMasterVO.getCustomerNo());
                        } else {
                            rcvMasterVO.setEndUser(rcvMasterVO.getUserNo());
                        }
                    }
                    rcvMasterVO.setRordDate(new Date());

                    if (StringUtils.isBlank(rcvMasterVO.getDeptNo())) {
                        rcvMasterVO.setDeptNo(sampleOrderApplyDO.getApplyDeptNo());
                    }

                    // deptNo和HLCode
                    ResultVo<String> deptNoResult = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(rcvMasterVO.getDeptNo());
                    if (!deptNoResult.isSuccess() || deptNoResult.getData() == null) {
                        rcvMasterVO.setDeptNo(null);
                    } else {
                        if (!rcvMasterVO.getDeptNo().equals(deptNoResult.getData())) {
                            rcvMasterVO.setHlCode(rcvMasterVO.getDeptNo());
                            rcvMasterVO.setDeptNo(deptNoResult.getData());
                        } else {
                            rcvMasterVO.setHlCode(rcvMasterVO.getDeptNo());
                        }
                    }

                    // 转换收货地所在的营业所 如果是HLCode 转成 营业所 不是 直接写入
                    if (StringUtils.isNotBlank(sampleOrderApplyDO.getDeliveryDeptNo())) {
                        ResultVo<String> deliveryNoResult = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(sampleOrderApplyDO.getDeliveryDeptNo());
                        if (deliveryNoResult.isSuccess()) {
                            if (!sampleOrderApplyDO.getDeliveryDeptNo().equals(deliveryNoResult.getData())) {
                                rcvMasterVO.setDeliveryDeptNo(deliveryNoResult.getData());
                            }
                        }
                    }
                    rcvMasterVO.setContractNo(sampleOrderApplyDO.getApplyNo());
                    rcvMasterVO.setOptTime(new Date());
                    rcvMasterVO.setEmployeeNo(StringUtils.isBlank(sampleOrderApplyDO.getApplyPsn()) ? "" : sampleOrderApplyDO.getApplyPsn());
                    rcvMasterVO.setTradeCompanyId(StringUtils.isBlank(sampleOrderApplyDO.getTradeCompanyId()) ? "CN0" : sampleOrderApplyDO.getTradeCompanyId());

                    // sampleOrderApplyDO => rcvDetail
                    List<RcvDetailVO> rcvDetailVOList = new ArrayList<>();
                    for (SampleorderDetailDO sampleorderDetailDO : sampleorderDetailDOList) {
                        RcvDetailVO rcvDetailVO = BeanCopyUtil.copy(sampleorderDetailDO, RcvDetailVO.class);
                        rcvDetailVO.setRorderNo(rorderNo);
                        rcvDetailVO.setRorderItem(sampleorderDetailDO.getItemNo());
                        rcvDetailVO.setRorderFno(rorderNo + "-" + sampleorderDetailDO.getItemNo());
                        rcvDetailVO.setStatus(1);
                        rcvDetailVO.setOrderType(9); // 样品订单
                        // rcvDetailVO.setCustomerType(CstmTypeEnum.ZX.getCode());
                        // 反算物流货期
                        DeliveryInfo deliveryInfo = new DeliveryInfo();
                        deliveryInfo.setDeptNo(sampleOrderApplyDO.getApplyDeptNo());
                        deliveryInfo.setCustomerNo(sampleOrderApplyDO.getCustomerNo());
                        List<DeliveryModelInfo> modelNoList = new ArrayList<>();
                        DeliveryModelInfo modelInfo = new DeliveryModelInfo();
                        modelInfo.setModelNo(sampleorderDetailDO.getModelNo());
                        modelInfo.setQuantity(sampleorderDetailDO.getQuantity());
                        modelInfo.setDlvDate(sampleorderDetailDO.getDlvDate());
                        modelNoList.add(modelInfo);
                        deliveryInfo.setModelList(modelNoList);
                        rcvDetailVO.setCdlvDate(sampleorderDetailDO.getDlvDate());
                        if (sampleorderDetailDO.getDlvDate() != null) {
                            rcvDetailVO.setWmsDlvDate(DateUtil.addDay(sampleorderDetailDO.getDlvDate(),-2));
                        }
                        // update by LiYingChao from bug 10751 in 2023-05-23
                        rcvDetailVO.setPrice(sampleorderDetailDO.getPrice());
                        if (rcvDetailVO.getPrice() == null) {
                            isSuccess.set(false);
                            result.set(sampleorderDetailDO.getModelNo()+"单价不可为空");
                            throw new RuntimeException(sampleorderDetailDO.getModelNo()+"单价不可为空");
                        }
                        rcvDetailVO.setEPrice(sampleorderDetailDO.getPrice());
                        rcvDetailVO.setTaxRate(taxRate); // 税率
                        //不含税单价
                        BigDecimal ntaxPrice = PriceCompute.unitPriceExcludingTax(rcvDetailVO.getPrice(), taxRate);
                        rcvDetailVO.setNtaxPice(ntaxPrice);
                        if (PublicUtil.isNotEmpty(rcvDetailVO.getQuantity())) {
                            if (rcvDetailVO.getAmount() == null || rcvDetailVO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                                rcvDetailVO.setAmount(rcvDetailVO.getPrice().multiply(new BigDecimal(rcvDetailVO.getQuantity())));
                            }
                            // 不含税金额
                            BigDecimal ntaxAMount = PriceCompute.ntaxAmount(rcvDetailVO.getAmount(), rcvDetailVO.getTaxRate());
                            rcvDetailVO.setNtaxAmount(ntaxAMount);
                            // 税额
                            BigDecimal taxAmount = PriceCompute.taxAmount(rcvDetailVO.getAmount(),rcvDetailVO.getNtaxAmount());
                            rcvDetailVO.setTaxAmount(taxAmount);
                            // 含税金额
                            rcvDetailVO.setAmount(ntaxAMount.add(taxAmount));
                        }
                        rcvDetailVO.setDiscount(CommonFormulaUtil.calcDiscount(rcvDetailVO.getNtaxPice(),rcvDetailVO.getEPrice()));

//                        if (PublicUtil.isNotEmpty(rcvDetailVO.getModelNo())) {
//                            ResultVo<ProductPriceVO> productPriceVOResultVo = productServiceFeignApi.findPriceByModelNo(rcvDetailVO.getModelNo());
//
//                            if (PublicUtil.isEmpty(productPriceVOResultVo.getData().getSpriceRMB())) {
//                                if (PublicUtil.isNotEmpty(productPriceVOResultVo.getData().getEprice())) {
//                                    rcvDetailVO.setPrice(productPriceVOResultVo.getData().getEprice()); // 单价为空取E价
//                                }
//                            } else {
//                                rcvDetailVO.setPrice(productPriceVOResultVo.getData().getSpriceRMB()); // 单价
//                            }
//
//                            if (PublicUtil.isNotEmpty(productPriceVOResultVo.getData().getEprice())) {
//                                rcvDetailVO.setEPrice(productPriceVOResultVo.getData().getEprice());  // e价
//                            }
//                            rcvDetailVO.setTaxRate(taxRate); // 税率
//                            //不含税单价
//                            BigDecimal ntaxPrice = PriceCompute.unitPriceExcludingTax(rcvDetailVO.getPrice(), taxRate);
//                            rcvDetailVO.setNtaxPice(ntaxPrice);
//                            if (PublicUtil.isNotEmpty(rcvDetailVO.getQuantity())) {
//                                if (rcvDetailVO.getAmount() == null || rcvDetailVO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
//                                    rcvDetailVO.setAmount(rcvDetailVO.getPrice().multiply(new BigDecimal(rcvDetailVO.getQuantity())));
//                                }
//                                // 不含税金额
//                                BigDecimal ntaxAMount = PriceCompute.ntaxAmount(rcvDetailVO.getAmount(), rcvDetailVO.getTaxRate());
//                                rcvDetailVO.setNtaxAmount(ntaxAMount);
//                                // 税额
//                                BigDecimal taxAmount = PriceCompute.taxAmount(rcvDetailVO.getAmount(),rcvDetailVO.getNtaxAmount());
//                                rcvDetailVO.setTaxAmount(taxAmount);
//                                // 含税金额
//                                rcvDetailVO.setAmount(ntaxAMount.add(taxAmount));
//                            }
//                            rcvDetailVO.setDiscount(CommonFormulaUtil.calcDiscount(rcvDetailVO.getNtaxPice(),rcvDetailVO.getEPrice()));
//                        }

                        rcvDetailVO.setCarrierId(sampleOrderApplyDO.getReceiverCarrierId());

                        rcvDetailVOList.add(rcvDetailVO);

                        if (PublicUtil.isEmpty(sampleorderDetailDO.getOrderNo())) {
                            sampleorderDetailDO.setOrderNo(rorderNo + "-" + sampleorderDetailDO.getItemNo());
                            sampleorderDetailMapper.updateById(sampleorderDetailDO);
                        }

                        // 记录到日志表
                        OrderLogVO orderLogVO = new OrderLogVO();
                        orderLogVO.setOrderNo(rcvDetailVO.getRorderFno());
                        orderLogVO.setOptType(11);
                        orderLogVO.setDescription("生成样品订单");
                        orderLogVO.setOptTime(new Date());
                        orderLogVO.setIp(IpUtil.getIpAddress());
                        if (finalUserDTO != null) {
                            orderLogVO.setOptUserName(finalUserDTO.get().getUserName());
                            orderLogVO.setOptUserId(finalUserDTO.get().getUserNo());
                        }

                        ResultVo<String> orderLog = orderLogFeignApi.sendOrderLogMsgToMQ(orderLogVO);
                        if (!orderLog.isSuccess()) {
                            isSuccess.set(false);
                            result.set(orderLog.getMessage());
                            throw new RuntimeException(orderLog.getMessage());
                        }
                        // 推送状态信息至mq
                        SampleOrderVO sampleOrderVO = BeanCopyUtil.copy(sampleOrderApplyDO, SampleOrderVO.class);
                        SampleOrderVO sampleOrderVO2 = BeanCopyUtil.copy(sampleorderDetailDO, SampleOrderVO.class);
                        BeanCopyUtil.mergeObject(sampleOrderVO, sampleOrderVO2);

                        sampleOrderVO2.setOrderNo(rorderNo + "-" + sampleOrderVO2.getItemNo());
                        OrderStateVO orderStateVO = BeanCopyUtil.copy(sampleOrderVO2, OrderStateVO.class);
                        orderStateVO.setOrderType(9); // 样品订单
                        orderStateVO.setRorderNo(rorderNo);
                        orderStateVO.setStateCode(12); // 接入订单
                        orderStateVO.setStateDes("样品订单接入");
                        orderStateVO.setItemNo(sampleOrderVO2.getItemNo());
                        orderStateVO.setStateType(1); // // 待处理
                        orderStateVO.setStateDate(new Date());
                        orderStateVO.setCustDlvDate(sampleOrderVO2.getDlvDate());
                        orderStateVO.setProvider("OPS");

                        if (finalUserDTO != null) {
                            orderStateVO.setOptUserName(finalUserDTO.get().getUserName());
                            orderStateVO.setOptUserNo(finalUserDTO.get().getUserNo());
                        }

                        if (sampleorderDetailDO.getDlvDate() != null) {
                            orderStateVO.setCustDlvDate(sampleorderDetailDO.getDlvDate());
                        }
                        ResultVo<String> stringResultVo = orderStateServiceFeignApi.addOrderState(orderStateVO);
                        if (!stringResultVo.isSuccess()) {
                            isSuccess.set(false);
                            result.set(stringResultVo.getMessage());
                            throw new RuntimeException(stringResultVo.getMessage());
                        }
                    }
                    // 保存订单收货信息
                    OrderDlvDataVO orderDlvDataVO = new OrderDlvDataVO();
                    orderDlvDataVO.setOrderNo(rcvMasterVO.getRorderNo());
                    orderDlvDataVO.setCustomerNo(rcvMasterVO.getCustomerNo());
                    orderDlvDataVO.setItemNo(0);
                    orderDlvDataVO.setCstmName(StringUtils.isBlank(sampleOrderApplyDO.getReceiverCompany()) ? "未设置" : sampleOrderApplyDO.getReceiverCompany());
                    // 收货地址
                    if (PublicUtil.isNotEmpty(sampleOrderApplyDO.getReceiverAddress())) {
                        orderDlvDataVO.setDlvAddress(sampleOrderApplyDO.getReceiverAddress());
                        if (sampleOrderApplyDO.getReceiverAddress().contains("客户自提")) {
                            orderDlvDataVO.setDlvFlag(DeliveryPlaceEnum.zt.getCode());
                        }
                    }

                    if (sampleOrderApplyDO.getAddressType() != null) {
                        orderDlvDataVO.setDlvFlag(String.valueOf(sampleOrderApplyDO.getAddressType()));
                    }

                    // 收货人
                    if (PublicUtil.isNotEmpty(sampleOrderApplyDO.getReceiverName())) {
                        orderDlvDataVO.setContactPsn(sampleOrderApplyDO.getReceiverName());
                    }
                    // 收货人电话
                    if (PublicUtil.isNotEmpty(sampleOrderApplyDO.getReceiverPhone())) {
                        orderDlvDataVO.setTelNo(sampleOrderApplyDO.getReceiverPhone());
                    }
                    if (PublicUtil.isNotEmpty(sampleOrderApplyDO.getCreateUser())) {
                        orderDlvDataVO.setCreateUser(sampleOrderApplyDO.getCreateUser());
                    }
                    orderDlvDataVO.setCreateTime(new Date());
                    orderDlvDataVO.setUpdateTime(new Date());
                    orderDlvDataVO.setPostCode(StringUtils.isBlank(sampleOrderApplyDO.getReceiverPostCode()) ? "" : sampleOrderApplyDO.getReceiverPostCode());
                    orderDlvDataVO.setProvince(StringUtils.isBlank(sampleOrderApplyDO.getReceiverProvince()) ? "" : sampleOrderApplyDO.getReceiverProvince());
                    orderDlvDataVO.setCity(StringUtils.isBlank(sampleOrderApplyDO.getReceiverCity()) ? "" : sampleOrderApplyDO.getReceiverCity());
                    orderDlvDataVO.setDistrict(StringUtils.isBlank(sampleOrderApplyDO.getReceiverDistrict()) ? "" : sampleOrderApplyDO.getReceiverDistrict());
                    orderDlvDataVO.setCarrierId(StringUtils.isBlank(sampleOrderApplyDO.getReceiverCarrierId()) ? "" : sampleOrderApplyDO.getReceiverCarrierId());
                    // 写入 rcvDetail rcvMaster orderDlvData
                    RcvOrderDTO rcvOrderDlvDTO = BeanCopyUtil.copy(rcvMasterVO, RcvOrderDTO.class);
                    rcvOrderDlvDTO.setItems(rcvDetailVOList);
                    rcvOrderDlvDTO.setAddressInfo(orderDlvDataVO);

                    try {
                        ResultVo<String> stringResultVo = receiveOrderServiceFeignApi.addRcvOrder(rcvOrderDlvDTO);
                        if (!stringResultVo.isSuccess()) {
                            isSuccess.set(false);
                            result.set(stringResultVo.getMessage());
                            throw new RuntimeException(stringResultVo.getMessage());
                        }

                        //修改样品状态为已生成订单
                        sampleOrderApplyDO.setStatus(5);
                        sampleOrderApplyDO.setOrderNo(rorderNo);
                        sampleOrderApplyDO.setUpdateTime(new Date());
                        sampleOrderApplyDO.setId(sampleOrderApplyDO.getId());
                        sampleorderApplyMapper.updateById(sampleOrderApplyDO);
                    } catch (RuntimeException e) {
                        isSuccess.set(false);
                        result.set(e.getMessage());
                        throw new RuntimeException(e);
                    }

                }
                return true;
            });
        }
        if (isSuccess.get()) {
            return ResultVo.success("生成订单结束");
        } else {
            return ResultVo.failure(result.get());
        }
    }

    @Override
    public ResultVo<String> autoCreateOrerBySampleOrder() {

        // 获取状态为待处理的订单
        List<SampleOrderVO> sampleOrderVOS = sampleorderApplyMapper.listSampleOrderByStatus();
        if (sampleOrderVOS.isEmpty()) {
            return ResultVo.success("暂无数据可生成订单");
        }

        List<Long> applyIdList = sampleOrderVOS.stream().map(SampleOrderVO::getApplyId).collect(Collectors.toList());
        if (applyIdList.isEmpty()) {
            return ResultVo.success("暂无数据可生成订单");
        }
        List<String> newStringList = new ArrayList<>();
        for (Long item : applyIdList) {
            newStringList.add(String.valueOf(item));
        }
        SampleOrderParams sampleOrderParams = new SampleOrderParams();
        String[] applyIds = new String[newStringList.size()];
        String[] strings = newStringList.toArray(applyIds);
        sampleOrderParams.setApplyIds(strings);
        return createOrderBySampleOrder(sampleOrderParams);

    }

//    /**
//     * 接口废弃
//     * @param orderCarryParams
//     * @return
//     */
//    @Override
//    public ResultVo<String> orderCarryTurn(OrderCarryParams orderCarryParams) {
//        if (orderCarryParams.getIds().length == 0) {
//            return ResultVo.success("暂无结转的数据");
//        }
//        QueryWrapper<SamplebalDO> queryWrapper;
//        SamplebalDO samplebalDO;
//        for (String id : orderCarryParams.getIds()) {
//            queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("id", id);
//            samplebalDO = samplebalMapper.selectOne(queryWrapper);
//            if (samplebalDO == null) {
//                return ResultVo.success("暂无结转的数据");
//            }
//
//            if (!samplebalDO.getOptCode().equals("1")) {
//                return ResultVo.failure("当前订单暂时无法免费结转");
//            }
//
//            if ("1".equals(samplebalDO.getOptCode())) {
//                List<String> canFreeCostList = new ArrayList<>();
//                canFreeCostList.add("101"); // 赠品
//                canFreeCostList.add("201"); // 展示品
//                canFreeCostList.add("102"); // 样品
//                canFreeCostList.add("103"); // 已报废处理
//                canFreeCostList.add("104"); // 维修品
//
//                if (StringUtils.isBlank(samplebalDO.getBalType()) || !canFreeCostList.contains(samplebalDO.getBalType())) {
//                    continue;
//                }
//            }
//
//            // 写入SalesExp
//            if (StringUtils.isNotBlank(samplebalDO.getDeptNo())) {
//                // 根据结转类型获取发票前缀生成发票号
//                String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
//                insertSalesExpFromSampleBal(samplebalDO, invoiceNo);
//            }
//            samplebalDO.setOptDate(new Date());
//            samplebalDO.setInDate(new Date());
//            samplebalDO.setOptCode("6"); // 修改状态为 已内耗结转
//            int i = samplebalMapper.updateById(samplebalDO);
//            if (i == 0) {
//                return ResultVo.failure("修改结转状态失败,结转失败!");
//            }
//        }
//        return ResultVo.success("结转完毕");
//    }

    @Override
    @Transactional
    public ResultVo<List<SampleOrderReturnDTO>> addSampleOrder(SampleOrderDTO sampleOrderDTO) {
        if (PublicUtil.isEmpty(sampleOrderDTO)) {
            return ResultVo.failure("新增样品数据失败");
        }
        log.info("[addSampleOrder] 共计申请 {} 项明细,接口参数 {}", sampleOrderDTO.getDetailItem().size(), JSONObject.toJSONString(sampleOrderDTO));

        // 校验交易主体是否合法
        boolean existGroupCompany = groupCompanyService.isExistGroupCompany(sampleOrderDTO.getTradeCompanyId());
        if (!existGroupCompany) {
            return ResultVo.failure("交易主体不合法,申请退回.");
        }

        // bug 11715 2023-08-14 校验省市区是否为空
        // 12070bug 样品申请地址、样品订单修改地址希望增加自提功能  2023-09-11
        boolean zt = false;
        if (StringUtils.isNotBlank(sampleOrderDTO.getReceiverAddress())) {
            if (sampleOrderDTO.getReceiverAddress().contains("客户自提")) {

                if (StringUtils.isBlank(sampleOrderDTO.getReceiverPhone())) {
                    return ResultVo.failure("客户自提时,收货人电话不可为空");
                }
                zt = true;
            }
        }
        if (!zt) {
            if (StringUtils.isBlank(sampleOrderDTO.getReceiverProvince()) ||
                    StringUtils.isBlank(sampleOrderDTO.getReceiverCity()) ||
                    StringUtils.isBlank(sampleOrderDTO.getReceiverDistrict())) {
                return ResultVo.failure("地址校验不通过,省市区不可为空");
            }
        }

        long startTime = System.currentTimeMillis();
        SampleOrderApplyDO sampleOrderApplyDO = BeanCopyUtil.copy(sampleOrderDTO, SampleOrderApplyDO.class);
        // 根据申请号判断 该条数据是否存在表中 有则修改 无则新增
        LambdaQueryWrapper<SampleOrderApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SampleOrderApplyDO::getId, SampleOrderApplyDO::getOrderNo, SampleOrderApplyDO::getStatus)
                .eq(SampleOrderApplyDO::getApplyNo, sampleOrderApplyDO.getApplyNo());
        List<SampleOrderApplyDO> sampleOrderApplyDOList = sampleorderApplyMapper.selectList(queryWrapper);
        Long id;
        try {
            if (sampleOrderApplyDOList.isEmpty()) {
                // 生成订单号
                ResultVo<String> randomOrderNoGenerator = commonServiceFeignApi.generatorBillNo("1");
                if (!randomOrderNoGenerator.isSuccess() || StringUtils.isBlank(randomOrderNoGenerator.getData())) {
                    return ResultVo.failure("生成订单号有误,新增失败");
                }
                if (!sampleOrderDTO.getApplyType().equals("3")) {
                    if (StringUtils.isBlank(sampleOrderDTO.getCustomerNo())) {
                        return ResultVo.failure(sampleOrderDTO.getApplyNo() + " 客户代码为空.");
                    }
                    if (StringUtils.isBlank(sampleOrderDTO.getTradeCompanyId())) {
                        return ResultVo.failure(sampleOrderDTO.getApplyNo() + " 交易主体为空.");
                    }
                } else {
                    sampleOrderApplyDO.setCustomerNo("C1D7V");
                    sampleOrderApplyDO.setTradeCompanyId("CN0");
                }
                sampleOrderApplyDO.setApplyTime(Optional.ofNullable(sampleOrderApplyDO.getApplyTime()).orElse(new Date()));
                sampleOrderApplyDO.setOrderDate(Optional.ofNullable(sampleOrderApplyDO.getOrderDate()).orElse(new Date()));
                sampleOrderApplyDO.setAnswerTime(Optional.ofNullable(sampleOrderApplyDO.getAnswerTime()).orElse(new Date()));
                sampleOrderApplyDO.setCreateTime(Optional.ofNullable(sampleOrderApplyDO.getCreateTime()).orElse(new Date()));
                sampleOrderApplyDO.setUpdateTime(Optional.ofNullable(sampleOrderApplyDO.getUpdateTime()).orElse(new Date()));
                sampleOrderApplyDO.setOrderNo(randomOrderNoGenerator.getData());
                sampleOrderApplyDO.setStatus(3);
                sampleorderApplyMapper.insert(sampleOrderApplyDO);
                id = sampleOrderApplyDO.getId();
            } else {
                SampleOrderApplyDO sampleOrderApplyDO1 = sampleOrderApplyDOList.get(0);
                id = sampleOrderApplyDO1.getId();
                sampleOrderApplyDO.setOrderNo(sampleOrderApplyDO1.getOrderNo());
                sampleOrderApplyDO.setApplyNo(sampleOrderApplyDO1.getApplyNo());
            }
            // 新增数据至 sampleOrderDetail 表
            List<SampleorderDetailDO> sampleorderDetailDOList = BeanCopyUtil.copyList(sampleOrderDTO.getDetailItem(), SampleorderDetailDO.class);
            List<SampleOrderReturnDTO> returnList = new ArrayList<>(sampleorderDetailDOList.size());
            Map<String, ProductPriceVO> map = new HashMap<>();
            ResultVo<ProductPriceVO> resultForPrice;
            for (SampleorderDetailDO sampleorderDetailDO : sampleorderDetailDOList) {
                LambdaQueryWrapper<SampleorderDetailDO> detailDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
                detailDOLambdaQueryWrapper
                        .eq(SampleorderDetailDO::getApplyId, id)
                        .eq(SampleorderDetailDO::getItemNo, sampleorderDetailDO.getItemNo());
                List<SampleorderDetailDO> list = sampleorderDetailMapper.selectList(detailDOLambdaQueryWrapper);
                if (!list.isEmpty()) {
                    SampleOrderReturnDTO dto = new SampleOrderReturnDTO();
                    dto.setOrderNo(sampleOrderApplyDO.getOrderNo() + "-" + sampleorderDetailDO.getItemNo());
                    dto.setModelNo(sampleorderDetailDO.getModelNo());
                    dto.setItemNo(sampleorderDetailDO.getItemNo());
                    dto.setApplyNo(sampleOrderApplyDO.getApplyNo());
                    returnList.add(dto);
                    continue;
                }
                if (null == sampleorderDetailDO.getQuantity()) {
                    throw new BusinessException(sampleorderDetailDO.getModelNo()+"数量不可为空.");
                }
                if (PublicUtil.isEmpty(sampleorderDetailDO.getPrice())) {
                    // update by LiYingChao from 10751 in 2023-05-23
                    throw new BusinessException(sampleorderDetailDO.getModelNo()+"单价不可为空.");
//                    // 若型号为null 或者 根据型号未查出价钱
//                    if (PublicUtil.isEmpty(sampleorderDetailDO.getModelNo())) {
//                        return ResultVo.failure("型号不可为空");
//                    } else {
//                        if (map.containsKey(sampleorderDetailDO.getModelNo())) {
//                            sampleorderDetailDO.setPrice(map.get(sampleorderDetailDO.getModelNo()).getSpriceRMB());
//                        } else {
//                            resultForPrice = productServiceFeignApi.findPriceByModelNo(sampleorderDetailDO.getModelNo());
//                            if (resultForPrice.isSuccess() && PublicUtil.isNotEmpty(resultForPrice.getData().getSpriceRMB())) {
//                                sampleorderDetailDO.setPrice(resultForPrice.getData().getSpriceRMB());
//                                map.put(sampleorderDetailDO.getModelNo(), resultForPrice.getData());
//                            } else {
//                                // sampleorderDetailDO.setPrice(new BigDecimal("0.00"));
//                                return ResultVo.failure("单价不可为空,未根据" + sampleorderDetailDO.getModelNo() + "型号查出单价.");
//                            }
//                        }
//                    }
                }
                sampleorderDetailDO.setOrderNo(sampleOrderApplyDO.getOrderNo() + "-" + sampleorderDetailDO.getItemNo());
                sampleorderDetailDO.setCreateTime(sampleorderDetailDO.getCreateTime() == null ? new Date() : sampleorderDetailDO.getCreateTime());
                sampleorderDetailDO.setUpdateTime(sampleorderDetailDO.getUpdateTime() == null ? new Date() : sampleorderDetailDO.getUpdateTime());
                sampleorderDetailDO.setApplyId(id);
                int insert = sampleorderDetailMapper.insert(sampleorderDetailDO);
                if (insert < 1) {
                    log.info("======生成样品异常[add]======== 异常数据 {}", JSONObject.toJSONString(sampleorderDetailDO));
                }
                SampleOrderReturnDTO dto = new SampleOrderReturnDTO();
                dto.setOrderNo(sampleOrderApplyDO.getOrderNo() + "-" + sampleorderDetailDO.getItemNo());
                dto.setModelNo(sampleorderDetailDO.getModelNo());
                dto.setItemNo(sampleorderDetailDO.getItemNo());
                dto.setApplyNo(sampleOrderApplyDO.getApplyNo());
                returnList.add(dto);
            }
            log.info("生成样品订单完毕 耗时: {} s", (System.currentTimeMillis() - startTime) / 1000);
            log.info("样品申请返回参数{} ", JSONUtil.toJsonStr(returnList));
            return ResultVo.success(returnList);
        } catch (Exception e) {
            log.error("生成样品订单抛出异常: {}", e.getMessage(), e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultVo<String> rebackOrder(SampleOrderParams sampleOrderParams) {
        if (sampleOrderParams.getApplyIds().length == 0) {
            return ResultVo.failure("请选择回退订单!");
        }
        List<SampleorderDetailDO> sampleorderDetailDOList;
        SampleOrderApplyDO sampleOrderApplyDO;
        for (String id : sampleOrderParams.getApplyIds()) {

            // 根据id查sampleOrder_apply
            sampleOrderApplyDO = sampleorderApplyMapper.selectById(id);

            if (sampleOrderApplyDO.getStatus() == 4) {
                continue;
            }
            // 根据applyId、itemNo 查 sampleOrder_detail
            QueryWrapper<SampleorderDetailDO> detailDOQuery = new QueryWrapper<>();
            detailDOQuery.eq("apply_id", id);
            sampleorderDetailDOList = sampleorderDetailMapper.selectList(detailDOQuery);
            try {
                UpdateWrapper<SampleOrderApplyDO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", id);
                SampleOrderApplyDO sampleOrder = new SampleOrderApplyDO();
                sampleOrder.setStatus(4);
                sampleOrder.setRemark(sampleOrderParams.getRemark());
                sampleorderApplyMapper.update(sampleOrder, updateWrapper);

                for (SampleorderDetailDO sampleorderDetailDO : sampleorderDetailDOList) {
                    LambdaUpdateWrapper<SampleorderDetailDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper.eq(SampleorderDetailDO::getApplyId, id).set(SampleorderDetailDO::getRemark, sampleOrderParams.getRemark());
                    sampleorderDetailMapper.update(null, lambdaUpdateWrapper);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ResultVo.success("订单回退成功");
    }

    // 物流发货数据结转
    @Override
    public ResultVo<String> autoGenerateSampleBalData() {
        // 查询遍历expDetaill
        ExpdetailRequest expdetailRequest = new ExpdetailRequest();
        List<String> orderTypes = new ArrayList<>();
        orderTypes.add("9");
        expdetailRequest.setOrderType(orderTypes); // 样品订单
        expdetailRequest.setOptCode(1);
        // 获取发货时间
        // update by LiYingChao from bug 9279 in 2023-1-6
        String[] code = com.smc.smccloud.model.constants.Constants.PARSE_EXPDETAIL_STARTtIMECODE.split(";");
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo(code[0], code[1]);
        if (!dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("获取查询发货数据的起始时间有误.");
        }
        String startTimeStr = dataTypeCodesInfo.getData().getExtNote1();
        if (StringUtils.isBlank(startTimeStr)) {
            return ResultVo.failure("获取查询发货数据的起始时间有误.");
        }
        expdetailRequest.setStrFromDate(startTimeStr);
        ResultVo<List<ExpdetailVO>> expDetailByOrderType = expdetailServiceFeignApi.findExpDetailByOrderType(expdetailRequest);
        if (!expDetailByOrderType.isSuccess()) {
            return ResultVo.failure("暂无所需生成结算的数据");
        }
        List<ExpdetailVO> list = expDetailByOrderType.getData();

        // 根据订单号+型号合并
        List<ExpdetailVO> newList = mergeAttributeByOrderNoWithModelNo(list);
        return generateSampleBalData(newList);

    }

    public List<ExpdetailVO> mergeAttributeByOrderNoWithModelNo(List<ExpdetailVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        Map<String, ExpdetailVO> map = new HashMap<>();
        for (ExpdetailVO item : list) {
            String key = item.getOrderFno().trim()+item.getModelNo().trim();
            if (map.containsKey(key)) {
                ExpdetailVO expdetailVO = map.get(key);
                List<Long> ids = expdetailVO.getIdList();
                ids.add(item.getId());
                expdetailVO.setIdList(ids);
                expdetailVO.setQuantity(expdetailVO.getQuantity()+item.getQuantity());
                map.put(key,expdetailVO);
            } else {
                List<Long> id = new ArrayList<>();
                id.add(item.getId());
                item.setIdList(id);
                map.put(key,item);
            }
        }
        Collection<ExpdetailVO> values = map.values();
        return new ArrayList<ExpdetailVO>(values);
    }

    public ResultVo<String> generateSampleBalData(List<ExpdetailVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return ResultVo.failure("暂无所需结算数据");
        }
        LambdaQueryWrapper<SampleorderDetailDO> sampleOrderDetailQuery = new LambdaQueryWrapper<>();;
        LambdaQueryWrapper<SampleOrderApplyDO> orderApplyDOQuery = new LambdaQueryWrapper<>();
        SampleOrderApplyDO sampleOrderApplyDO;
        SampleorderDetailDO sampleorderDetailDO;
        SamplebalDO samplebalDO;
        OrderLogVO orderLogVO;

        // 获取字典表的申请类型和结转类型对应关系
        Map<String,String> map = new HashMap<>();
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.DICT_CODE_SAMPLEORDER_APPTYPE);
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return ResultVo.failure("无法获取字典参数配置信息");
        }
        for (DataCodeVO item : dataCodes.getData()) {
            map.put(item.getCode(),item.getExtNote1());
        }

        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        for (ExpdetailVO item : list) {

            RcvDetailDO rcvDetailInfo = findRcvDetailInfo(item);
            if (rcvDetailInfo == null) {
                continue;
            }
            // 查询结转的数量
            queryWrapper.clear();
            queryWrapper.eq(SamplebalDO::getRorderNo,item.getOrderFno()).ne(SamplebalDO::getOptCode,SampleBalOptCodeEnum.CANCEL.getCode());
            List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
            int alreadyBalNum = 0;
            if (CollectionUtils.isNotEmpty(samplebalDOList)) {
                alreadyBalNum = samplebalDOList.stream().filter(i -> i.getQuantity() != null).mapToInt(SamplebalDO::getQuantity).sum();
            }
            // 判断是否整型号 控制写入sample_bal里面的整型号数量不能超过rcvdetail的接单数量
            if (StringUtils.isBlank(item.getModelNo()) || !item.getModelNo().trim().equals(rcvDetailInfo.getModelNo().trim())
                                         || ( (item.getQuantity() + alreadyBalNum) > rcvDetailInfo.getQuantity() ))
            {
                upExpdetailOptCodeById(item.getIdList(),"4");
                continue;
            }
            samplebalDO = new SamplebalDO();
            // 根据expdetail 订单号 项号 查样品明细表
            sampleOrderDetailQuery.clear();
            String fulOrderNo = item.getOrderFno();
            sampleOrderDetailQuery.eq(SampleorderDetailDO::getOrderNo, fulOrderNo);
            sampleorderDetailDO = sampleorderDetailMapper.selectOne(sampleOrderDetailQuery);
            // 根据明细的申请号id查主体表
            if (sampleorderDetailDO != null && sampleorderDetailDO.getApplyId() != null) {
                orderApplyDOQuery.clear();
                orderApplyDOQuery.eq(SampleOrderApplyDO::getId, sampleorderDetailDO.getApplyId());
                sampleOrderApplyDO = sampleorderApplyMapper.selectOne(orderApplyDOQuery);
                // hl所 (费用承担部门)
                // deptNo和HLCode
                RcvMasterDO rcvMaster = findRcvMaster(rcvDetailInfo.getRorderNo());
                if (rcvMaster != null) {
                    samplebalDO.setDeptDesc(rcvMaster.getHlCode());
                    samplebalDO.setDeptNo(rcvMaster.getDeptNo());
                } else {
                    ResultVo<String> deptNoResult = opsCommonService.getDeptNoByHRSalesDeptNo(sampleOrderApplyDO.getDeptNo());
                    if (!deptNoResult.isSuccess() || deptNoResult.getData() == null) {
                        samplebalDO.setDeptNo(null);
                        samplebalDO.setRemark("部门数据错误");
                    } else {
                        if (!sampleOrderApplyDO.getDeptNo().equals(deptNoResult.getData())) {
                            samplebalDO.setDeptDesc(sampleOrderApplyDO.getDeptNo());
                            samplebalDO.setDeptNo(deptNoResult.getData());
                        } else {
                            samplebalDO.setDeptNo(sampleOrderApplyDO.getDeptNo());
                            samplebalDO.setDeptDesc(sampleOrderApplyDO.getDeptNo());
                        }
                    }
                }
                // 判断索赔号,索赔金额,物流公司名称
               if (!isAlreadyInto(samplebalDOList)) {
                   samplebalDO.setClaimNo(sampleOrderApplyDO.getClaimNo());
                   samplebalDO.setClaimAmount(sampleOrderApplyDO.getClaimAmount());
                   samplebalDO.setExpressCompany(sampleOrderApplyDO.getExpressCompany());
               }
                samplebalDO.setRcvDeptNo(samplebalDO.getDeptNo());
                samplebalDO.setApplyCode(sampleOrderApplyDO.getApplyNo());
                samplebalDO.setAppType(sampleOrderApplyDO.getApplyType());
                samplebalDO.setBalType(sampleOrderApplyDO.getCostType());
                if (StringUtils.isBlank(samplebalDO.getBalType())) {
                    if (StringUtils.isNotBlank(sampleOrderApplyDO.getApplyType())) {
                        String applyType = sampleOrderApplyDO.getApplyType();
                        String balType = map.get(applyType);
                        if (StringUtils.isNotBlank(balType)) {
                            samplebalDO.setBalType(balType);
                        }
                    }
                }
            }
            // 型号拆分
            if (StringUtils.isNotBlank(rcvDetailInfo.getProdFlag()) && "2".equals(rcvDetailInfo.getProdFlag())) {
                samplebalDO.setProdFlag("1");
            } else {
                samplebalDO.setProdFlag("0");
            }
            // 这个地方注释 统一通过定时作业结转
//            Boolean canAutoBal = canAutoBal(samplebalDO.getAppType(), samplebalDO.getBalType());
//            if (canAutoBal) {
//                String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
//                if (StringUtils.isBlank(invoiceNo)) {
//                    samplebalDO.setOptCode("1");
//                } else {
//                    samplebalDO.setOptCode("3");
//                    samplebalDO.setInDate(new Date());
//                    samplebalDO.setOptDate(new Date());
//                    samplebalDO.setOptTime(new Date());
//                    samplebalDO.setInvoiceNo(invoiceNo.trim());
//                }
//            } else {
//                samplebalDO.setOptCode("1");
//            }
            samplebalDO.setOptCode("1");
            samplebalDO.setPrice(item.getPrice());
            // 申请价格默认单价
            samplebalDO.setPriceApply(item.getPrice());
            samplebalDO.setCustomerNo(item.getCustomerNo());
            samplebalDO.setRorderNo(item.getOrderFno());
            samplebalDO.setModelNo(item.getModelNo());
            samplebalDO.setQuantity(item.getQuantity());
            samplebalDO.setOptDate(new Date());
            samplebalDO.setOptTime(new Date());
            // 工厂产品标识 prodCode
            // ECode
            samplebalDO.setRemark("样品结转");
            samplebalDO.setExpDate(item.getShipDate());
            samplebalDO.setOrdType("9"); // 样品订单
            samplebalDO.setCreatetime(new Date());
            samplebalDO.setStockCode(item.getStockCode());
            samplebalDO.setWarehouseCode(item.getWarehouseCode());
            // 写入sampleBal
            sampleBalService.insertSampleBal(samplebalDO);

            // 更改expDetail的状态 1 -> 2
            upExpdetailOptCodeById(item.getIdList(),"2");
            // 记录到日志表
            orderLogVO = new OrderLogVO();
            orderLogVO.setOrderNo(samplebalDO.getRorderNo());
            orderLogVO.setOptType(11);
            orderLogVO.setDescription("发货数据生成结转数据,数量"+item.getQuantity()+"型号"+item.getModelNo());
            orderLogVO.setOptTime(new Date());
            orderLogVO.setIp(IpUtil.getIpAddress());
            orderLogVO.setOptUserName("system");
            orderLogVO.setOptUserId("system");
            orderLogFeignApi.sendOrderLogMsgToMQ(orderLogVO);
        }
        return ResultVo.success("数据结算成功! 共计: " + list.size());
    }

    // 判断是否已经写入过索赔号/索赔金额/物流公司
    // true 写入过
    public Boolean isAlreadyInto(List<SamplebalDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (SamplebalDO item : list) {
            if (StringUtils.isNotBlank(item.getClaimNo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据订单号获取订单明细信息
     */
    public RcvDetailDO findRcvDetailInfo (ExpdetailVO expdetailVO) {
        if (expdetailVO == null || StringUtils.isBlank(expdetailVO.getOrderFno())) {
            return null;
        }

        LambdaQueryWrapper<RcvDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvDetailDO::getRorderFno,expdetailVO.getOrderFno());
        return rcvdetailMapper.selectOne(queryWrapper);
    }

    /**
     * 根据订单号获取主单信息
     */
    public RcvMasterDO findRcvMaster(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        LambdaQueryWrapper<RcvMasterDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RcvMasterDO::getRorderNo,orderNo);
        return rcvmasterMapper.selectOne(queryWrapper);
    }

    /**
     * 修改发货表expdetail的状态
     */
    public void upExpdetailOptCodeById(List<Long> idList, String optCode) {

        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        LambdaUpdateWrapper<ExpdetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .in(ExpdetailDO::getId,idList)
                .set(ExpdetailDO::getUpdateTime,new Date())
                .set(ExpdetailDO::getOptCode,optCode);
        expdetailMapper.update(null,updateWrapper);
//        if (StringUtils.isBlank(expdetailVO.getOrderFno()) && StringUtils.isBlank(expdetailVO.getModelNo()) && StringUtils.isBlank(optCode)) {
//            return;
//        }
//        LambdaUpdateWrapper<ExpdetailDO> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper
//                .eq(StringUtils.isNotBlank(expdetailVO.getOrderFno()),ExpdetailDO::getOrderFno,expdetailVO.getOrderFno())
//                .eq(StringUtils.isNotBlank(expdetailVO.getModelNo()),ExpdetailDO::getModelNo,expdetailVO.getModelNo())
//                .eq(expdetailVO.getId() != null,ExpdetailDO::getId,expdetailVO.getId())
//                .set(ExpdetailDO::getOptCode,optCode);
//        expdetailMapper.update(null,updateWrapper);
    }


    /**
     * 转销售开票
     *
     * @param params
     * @return
     */
    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultVo<String> toSalesInvoice(InvoicingSalesParams params) {

        String lockKey = "ops:rediss:shareapp:sorder:toinvoice" + params.getId()[0];
        redissonUtil.lock(lockKey, 300);
        try {

            if (params.getId().length == 0) {
                return ResultVo.failure("请选择需要开票的订单");
            }
            if (params.getId().length > 1) {
                return ResultVo.failure("一次只能开票一个");
            }
            if (PublicUtil.isEmpty(params.getPrice())) {
                return ResultVo.failure("单价不可为空");
            }
            @Valid @NotEmpty String id = params.getId()[0];
            QueryWrapper<SamplebalDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Id", id);
            SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper);
            if (samplebalDO == null) {
                return ResultVo.failure("订单在结转表里面不存在，请检查是否发货");
            }
            if (StringUtils.isBlank(samplebalDO.getBalType())) {
                return ResultVo.failure("结转方式为空,该订单不能转销售开票,请先点击[结转]按钮选择转销售开票再进行开票操作");
            }

            if (!"1".equals(samplebalDO.getOptCode()) && !"3".equals(samplebalDO.getOptCode()) ) {
                return ResultVo.failure("该订单不能转销售开票,处理状态不是待结转");
            }
            if (!BalTypeEnum.XSKP.getCode().equals(samplebalDO.getBalType())) {
                return ResultVo.failure("该订单不能转销售开票,请先点击[结转]按钮选择转销售开票再进行开票操作");
            }
            OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(samplebalDO.getRorderNo());
            ResultVo<RcvMasterVO> orderMaster = receiveOrderServiceFeignApi.findOrderMaster(orderNoInfo.getOrderNo());
            if (!orderMaster.isSuccess() || orderMaster.getData() == null) {
                return ResultVo.failure("无接单数据，无法生成开票");
            }
            RcvMasterVO rcvMasterVO = orderMaster.getData();

            if (StringUtils.isBlank(samplebalDO.getDeptNo())) {
                samplebalDO.setDeptNo(rcvMasterVO.getDeptNo());
                if (StringUtils.isBlank(samplebalDO.getDeptNo())) {
                    return ResultVo.failure("无部门编码，无法生成开票");
                }
            }

            SalesInvoiceMidInfoDO salesInvoiceDO = new SalesInvoiceMidInfoDO();

            // 20530bug 读取sample_bal_apply表的交易主体
            List<SampleBalApplyDO> sampleBalApplyDOList = sampleBalService.getSampleBalApplyDOListByBalId(samplebalDO.getId());

            if(CollectionUtils.isNotEmpty(sampleBalApplyDOList)) {
                if(StringUtils.isNotBlank(sampleBalApplyDOList.get(0).getTradeCompanyId())) {
                    salesInvoiceDO.setTradeCompanyId(sampleBalApplyDOList.get(0).getTradeCompanyId());
                }
                if (StringUtils.isNotBlank(sampleBalApplyDOList.get(0).getCustomerType())) {
                    salesInvoiceDO.setCustomerType(sampleBalApplyDOList.get(0).getCustomerType());
                }
            } else {
                // 根据客户获取交易主体
                com.smc.smccloud.model.CustomerVO customerByCustomerNo = opsCommonService.getCustomerByCustomerNo(samplebalDO.getCustomerNo());
                if (customerByCustomerNo == null || StringUtils.isBlank(customerByCustomerNo.getTradeSubjectId())) {
                    return ResultVo.failure(samplebalDO.getCustomerNo()+"无交易主体，无法生成开票");
                }
                salesInvoiceDO.setTradeCompanyId(customerByCustomerNo.getTradeSubjectId());
            }
            salesInvoiceDO.setOrderNo(samplebalDO.getRorderNo());
            salesInvoiceDO.setRorderNo(orderNoInfo.getOrderNo());
            salesInvoiceDO.setRorderItem(orderNoInfo.getItemNo());
            salesInvoiceDO.setApplynoItem(orderNoInfo.getItemNo());
            salesInvoiceDO.setModelNo(samplebalDO.getModelNo());
            salesInvoiceDO.setQuantity(samplebalDO.getQuantity());
            salesInvoiceDO.setPrice(new BigDecimal(params.getPrice()));
            salesInvoiceDO.setCustomerNo(samplebalDO.getCustomerNo());
            salesInvoiceDO.setUserNo(samplebalDO.getUserNo());
            if(StringUtils.isNotBlank(samplebalDO.getUserNo())) {
                salesInvoiceDO.setEndUser(samplebalDO.getUserNo());
            } else {
                salesInvoiceDO.setEndUser(samplebalDO.getCustomerNo());
            }
            salesInvoiceDO.setDeptNo(samplebalDO.getDeptNo());
            salesInvoiceDO.setInsertTime(new Date());
            salesInvoiceDO.setIsNew("0");
            salesInvoiceDO.setType("YS");
            salesInvoiceDO.setFeeRate(BigDecimal.ZERO);
            salesInvoiceDO.setApplyNo(samplebalDO.getApplyCode());
            try {
                // 写入 salesInvoice
                int insert = salesInvoiceMidInfoMapper.insert(salesInvoiceDO);
                if (insert != 1) {
                    return ResultVo.failure("写入开票表失败");
                }
                // 修改 sampleBal 状态为 已转销售(5)
                LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .eq(SamplebalDO::getId, samplebalDO.getId())
                        .set(SamplebalDO::getOptDate,new Date())
                        .set(SamplebalDO::getOptTime,new Date())
                        .set(SamplebalDO::getInDate,new Date())
                        .set(SamplebalDO::getOptCode, "5");
                samplebalMapper.update(null, updateWrapper);

                // 记录到日志表
                OrderLogVO orderLogVO = new OrderLogVO();
                orderLogVO.setOrderNo(samplebalDO.getRorderNo());
                orderLogVO.setOptType(70);
                orderLogVO.setDescription("样品转销售");
                orderLogVO.setOptTime(new Date());
                orderLogVO.setIp(IpUtil.getIpAddress());
                LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
                orderLogVO.setOptUserName(userDTO.getUserName());
                orderLogVO.setOptUserId(userDTO.getUserNo());
                ResultVo<String> orderLogMsgToMQ = orderLogFeignApi.sendOrderLogMsgToMQ(orderLogVO);

                return ResultVo.success("", "转销售成功" + samplebalDO.getRorderNo());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            redissonUtil.unlock(lockKey);
        }
    }

    @Override
    public ResultVo<PageInfo<SamplebalDO>> listSampleBal(SampleBalRequest request, Page page) {
        LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
        query
                .likeRight(StringUtils.isNotBlank(request.getCustomerNo()), SamplebalDO::getCustomerNo, request.getCustomerNo())
                .likeRight(StringUtils.isNotBlank(request.getModelNo()), SamplebalDO::getModelNo, request.getModelNo())
                .likeRight(StringUtils.isNotBlank(request.getRorderNo()), SamplebalDO::getRorderNo, request.getRorderNo())
                .likeRight(StringUtils.isNotBlank(request.getApplyCode()), SamplebalDO::getApplyCode, request.getApplyCode())
                .in(CollectionUtils.isNotEmpty(request.getOptCode()), SamplebalDO::getOptCode, request.getOptCode())
                .in(CollectionUtils.isNotEmpty(request.getApplyType()), SamplebalDO::getAppType, request.getApplyType())
                .in(CollectionUtils.isNotEmpty(request.getBalType()), SamplebalDO::getBalType, request.getBalType())
                // 操作日期
                .ge(StringUtils.isNotBlank(request.getOptDateStart()), SamplebalDO::getOptDate, request.getOptDateStart())
                .le(StringUtils.isNotBlank(request.getOptDateEnd()), SamplebalDO::getOptDate, request.getOptDateEnd())
                // 出库日期
                .ge(StringUtils.isNotBlank(request.getExpDateStart()), SamplebalDO::getExpDate, request.getExpDateStart())
                .le(StringUtils.isNotBlank(request.getExpDateEnd()), SamplebalDO::getExpDate, request.getExpDateEnd())
                // 结转日期
                .ge(StringUtils.isNotBlank(request.getInDateStart()), SamplebalDO::getInDate, request.getInDateStart())
                .le(StringUtils.isNotBlank(request.getInDateEnd()), SamplebalDO::getInDate, request.getInDateEnd())
                // 创建日期
                .ge(StringUtils.isNotBlank(request.getCreDateStart()), SamplebalDO::getCreatetime, request.getCreDateStart())
                .le(StringUtils.isNotBlank(request.getCreDateEnd()), SamplebalDO::getCreatetime, request.getCreDateEnd());
        if (CollectionUtils.isNotEmpty(request.getDeptNos())) {
            query.and(wrapper -> wrapper.in(SamplebalDO::getDeptNo, request.getDeptNos())
                    .or().in(SamplebalDO::getDeptDesc, request.getDeptNos()));
        }
        query.orderByDesc(SamplebalDO::getOptDate);

        PageInfo<SamplebalDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> samplebalMapper.selectList(query));

        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
            for (SamplebalDO item : pageInfo.getList()) {
                // HL所名称
                if (StringUtils.isNotBlank(item.getDeptDesc())) {
                    if (!nameMap.containsKey(item.getDeptDesc())) {
                        nameMap.put(item.getDeptDesc(), opsCommonService.getDeptNameByNo(item.getDeptDesc()));
                    }
                    item.setHlCodeName(nameMap.get(item.getDeptDesc()) + "[" + item.getDeptDesc() + "]");
                }
                // 营业所名称
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    if (!nameMap.containsKey(item.getDeptNo())) {
                        nameMap.put(item.getDeptNo(), opsCommonService.getDeptNameByNo(item.getDeptNo()));
                    }
                    item.setDeptName(nameMap.get(item.getDeptNo()) + "[" + item.getDeptNo() + "]");
                }
                // 展览展示品实物所在营业所
                if (StringUtils.isNotBlank(item.getRcvDeptNo())) {
                    if (!nameMap.containsKey(item.getRcvDeptNo())) {
                        nameMap.put(item.getRcvDeptNo(), opsCommonService.getDeptNameByNo(item.getRcvDeptNo()));
                    }
                    item.setRcvDeptName(nameMap.get(item.getRcvDeptNo()) + "[" + item.getRcvDeptNo() + "]");
                }
            }
        }
        return ResultVo.success(pageInfo);
    }

    /**
     * 样品订单 自动结转 定时作业(待结转->待转出)
     *
     * @return
     */
    @Override
    public ResultVo<String> autoOrderCarryTurn() {

        // 判断待结转订单是否有退货,若有退货则根据退货数量抵消掉待结转数量
        // offSetReturnQty();
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getOptCode, 1);
        queryWrapper.ne(SamplebalDO::getQuantity,0);
        queryWrapper.ne(SamplebalDO::getAppType,"2");
        List<SamplebalDO> samplebalDOS = samplebalMapper.selectList(queryWrapper);
        if (samplebalDOS.isEmpty()) {
            return ResultVo.failure("暂无待结转数据");
        }
        // 记录结转类型为空的订单号
        StringBuilder msg = new StringBuilder();
        StringBuilder deptNoMsgerror = new StringBuilder();
        int count = 0;
        String invoiceNo;

        for (SamplebalDO item : samplebalDOS) {

            if (StringUtils.isBlank(item.getBalType())) {
                if (StringUtils.isNotBlank(item.getAppType()) && item.getAppType().equals(SampleBalAppTypeEnum.SYPMFSY.getCode())) {
                    item.setBalType(BalTypeEnum.zp.getCode());
                }
            }

            if (StringUtils.isBlank(item.getBalType())) {
                msg.append(item.getRorderNo()+";");
                continue;
            }

            if (StringUtils.isBlank(item.getDeptNo())) {
                deptNoMsgerror.append(item.getRorderNo()+";");
                continue;
            }

            // 查询发票号
            // 根据结转类型获取发票前缀生成发票号
            SamplebalDO sampleObj = null;
            if (item.getQuantity() < 0 && item.getBalType().equals(BalTypeEnum.LPFH.getCode())) {
                queryWrapper.clear();
                queryWrapper
                        .eq(SamplebalDO::getRorderNo,item.getRorderNo())
                        .ne(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
                        .eq(SamplebalDO::getOptCode,6)
                        .gt(SamplebalDO::getQuantity,0);
                List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
                if (CollectionUtils.isEmpty(samplebalDOList)) {
                    continue;
                } else {
                    sampleObj = samplebalDOList.get(0);
                }
                invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(sampleObj);
            } else {
                invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(item);
            }

            if (StringUtils.isNotBlank(invoiceNo)) {
                if ((StringUtils.isNotBlank(item.getBalType()) && canAutoBal(item.getAppType(),item.getBalType())) ||
                        (item.getQuantity() < 0 && item.getBalType().equals(BalTypeEnum.LPFH.getCode()))) {

                    if (StringUtils.isBlank(item.getAppType()) && sampleObj != null && StringUtils.isNotBlank(sampleObj.getAppType())) {
                        item.setAppType(sampleObj.getAppType());
                    }

                    LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper
                            .set(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DZC.getCode())  // 待转出
                            .set(SamplebalDO::getOptDate,new Date())
                            .set(SamplebalDO::getOptTime,new Date())
                            .set(SamplebalDO::getInDate,new Date()) // 结转时间
                            .set(SamplebalDO::getInvoiceNo,invoiceNo.trim())
                            .set(SamplebalDO::getBalType,item.getBalType())
                            .eq(SamplebalDO::getId, item.getId());
                    samplebalMapper.update(null, updateWrapper);
                }
            }

//            if (BalTypeEnum.ZS.getCode().equals(item.getBalType())) {
//                SampleOrderManageDO sampleOrderManageDO = conventSampleExhibition(item);
//                sampleOrderManageService.insertSampleOrderManage(sampleOrderManageDO);
//            }
            count++;
        }
        return ResultVo.success("结转完毕,共计结转 " + count +" 条数据,未结转的数据[缺少结转类型]:" + msg.toString()+"未结转的数据[缺少部门]:" + deptNoMsgerror.toString());
    }

    /**
     * 是否可以自动结转
     * @param appType 申请类型
     * @param balType 结转类型
     * @return true 是
     */
    public Boolean canAutoBal(String appType,String balType) {
        if (StringUtils.isBlank(appType) || StringUtils.isBlank(balType)) {
            return false;
        }
        // 获取字典表自动配置
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.DICT_CODE_SAMPLEORDER_APPTYPE);
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return false;
        }
        List<DataCodeVO> dataCodeVOList = dataCodes.getData();
        Map<String,DataCodeVO> map = new HashMap<>();
        for (DataCodeVO item : dataCodeVOList) {
            map.put(item.getCode()+"-"+item.getExtNote1(),item);
        }
        String key = appType+"-"+balType;
        if (!map.containsKey(key)) {
            return false;
        }
        DataCodeVO dataCodeVO = map.get(key);
        if ("Y".equals(dataCodeVO.getExtNote3())) {
            return true;
        }
        return false;
    }

    public SampleOrderManageDO conventSampleExhibition(SamplebalDO samplebalDO) {
        SampleOrderManageDO item = new SampleOrderManageDO();
        item.setModelNo(samplebalDO.getModelNo());
        item.setOrderNo(samplebalDO.getRorderNo());
        item.setShipDate(samplebalDO.getExpDate());
        item.setQtyOnhand(samplebalDO.getQuantity());
        item.setOutQty(0);
        item.setImpQty(samplebalDO.getQuantity());
        item.setStatus(1);
        item.setDeptNo(samplebalDO.getDeptNo());
        item.setManager(samplebalDO.getUserName());
        item.setManager(samplebalDO.getUserName());
        item.setCreateTime(new Date());
        item.setOutTime(new Date());
        return item;
    }

    @Override
    public ResultVo<String> autoInsertSales() {

        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                    .eq(SamplebalDO::getOptCode, 3)
                    .ne(SamplebalDO::getQuantity,0)
                    .ne(SamplebalDO::getOptCode,9)
                    .ne(SamplebalDO::getBalType,"301");
        List<SamplebalDO> samplebalDOS = samplebalMapper.selectList(queryWrapper);
        if (samplebalDOS.isEmpty()) {
            return ResultVo.success("暂无待转出数据");
        }
        int count = 0;
        StringBuilder msg = new StringBuilder();
        for (SamplebalDO item : samplebalDOS) {

            if (StringUtils.isBlank(item.getDeptNo())) {
                msg.append(item.getRorderNo()+"部门代码为空;");
                continue;
            }
            if (StringUtils.isBlank(item.getInvoiceNo())) {
                msg.append(item.getRorderNo()+"发票号为空;");
                continue;
            }
            // add by LiYingChao from bug 9143 in 2022-12-27
            if (item.getInDate() == null) {
                msg.append(item.getRorderNo()+"结转日期为空;");
                continue;
            }

            // 免费样品结转还需要写入到  [smc_cost_auto].[dbo].[SalesExp]
            // 先查rcvdetail 是否型号拆分，如果是型号拆分，查ops_order_assign_result的子型号来写入子型号
            ResultVo<String> resultVo = insertSalesExpFromSampleBal(item, item.getInvoiceNo());
            if (resultVo.isSuccess()) {
                LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .set(SamplebalDO::getOptCode, 6)
                        .set(SamplebalDO::getOptDate,new Date())
                        .set(SamplebalDO::getOptTime,new Date())
                        .eq(SamplebalDO::getId, item.getId());
                samplebalMapper.update(null, updateWrapper);
                count++;
            } else {
                msg.append(item.getRorderNo()+"写入失败:"+resultVo.getMessage());
            }
        }

        if(StringUtils.isBlank(msg.toString())) {
            return ResultVo.success("写入完毕,共计写入 " + count + " 条数据");
        }
        return ResultVo.success("共计写入 " + count + " 条数据"+"; 未写入数据描述: "+msg.toString());
    }

    // 查rcvdetail 是否型号拆分，如果是型号拆分，查ops_order_assign_result的子型号来写入子型号
    public ResultVo<String> insertSalesExpFromSampleBal(SamplebalDO samplebalDO, String invoiceNo) {
        // 查明细
        ResultVo<RcvDetailVO> orderDetail = receiveOrderServiceFeignApi.findOrderDetail(samplebalDO.getRorderNo());
        if (!orderDetail.isSuccess()) {
            return ResultVo.failure(samplebalDO.getRorderNo()+"订单表不存在");
        }
        RcvDetailVO rcvDetailVO = orderDetail.getData();
        // 查订单Master表
        ResultVo<RcvMasterVO> orderMaster = receiveOrderServiceFeignApi.findOrderMaster(rcvDetailVO.getRorderNo());
        if (!orderMaster.isSuccess() || orderMaster.getData() == null) {
            return ResultVo.failure(rcvDetailVO.getRorderNo()+"订单表不存在");
        }
        RcvMasterVO rcvMasterVO = orderMaster.getData();
        if (StringUtils.isBlank(rcvDetailVO.getProdFlag())) {
            return ResultVo.failure(samplebalDO.getRorderNo()+"拆分标识为空");
        }
        // 先查rcvdetail 是否型号拆分，如果是型号拆分，查ops_order_assign_result的子型号来写入子型号
        // 拆分型号
        SalesExpDO salesExpDO;
        CustomerVO customerVO = commonService.getCustomerInfoByNo(samplebalDO.getCustomerNo());

        if (DateUtil.getYear(rcvDetailVO.getCreateTime()) >= 2025) {
            List<SampleBalPropertyAssignDO> sampleBalPropertyAssignDOList = getSampleBalPropertyAssignDOList(samplebalDO);
            if(CollectionUtils.isEmpty(sampleBalPropertyAssignDOList)) {
                return ResultVo.failure(samplebalDO.getRorderNo()+"资产分配为空");
            }
            boolean prodflag = "2".equals(rcvDetailVO.getProdFlag());
            int count = 1;
            for (SampleBalPropertyAssignDO sampleBalPropertyAssignDO : sampleBalPropertyAssignDOList) {
                if (prodflag) {
                    salesExpDO = new SalesExpDO();
                    salesExpDO.setInvoiceNo(invoiceNo);
                    salesExpDO.setSubModel(sampleBalPropertyAssignDO.getModelNo());
                    salesExpDO.setSubQty(sampleBalPropertyAssignDO.getQuantity());
                    salesExpDO.setProdFlag("1");
                    salesExpDO.setMainQty(samplebalDO.getQuantity());
                    if (count == 1) {
                        salesExpDO.setRemark("免费样品订单"+count);
                    }
                    if (customerVO != null) {
                        salesExpDO.setCustType(customerVO.getCustomerType());
                    }
                    salesExpDO.setOwnerCompanyId(sampleBalPropertyAssignDO.getCompanyId());
                    insertSalesExp(salesExpDO,samplebalDO,rcvDetailVO,rcvMasterVO);
                    count++;
                } else {
                    salesExpDO = new SalesExpDO();
                    salesExpDO.setInvoiceNo(invoiceNo);
                    salesExpDO.setSubModel("");
                    salesExpDO.setSubQty(0);
                    salesExpDO.setMainQty(sampleBalPropertyAssignDO.getQuantity());
                    salesExpDO.setProdFlag("0");
                    if (customerVO != null) {
                        salesExpDO.setCustType(customerVO.getCustomerType());
                    }
                    salesExpDO.setOwnerCompanyId(sampleBalPropertyAssignDO.getCompanyId());
                    insertSalesExp(salesExpDO,samplebalDO,rcvDetailVO,rcvMasterVO);
                }
            }
        } else {
            if ("2".equals(rcvDetailVO.getProdFlag())) {
                // 查orderStatus
                if (rcvDetailVO.getRorderItem() == null) {
                    rcvDetailVO.setRorderItem(0);
                }
                ResultVo<List<OpsOrderAssignResultVO>> orderAssItemsResult = receiveOrderServiceFeignApi.getOrderAssItemsByModelNo(rcvDetailVO.getRorderNo(), rcvDetailVO.getRorderItem());
                if (orderAssItemsResult.isSuccess() && orderAssItemsResult.getData() != null) {
                    List<OpsOrderAssignResultVO> orderAssItemList = orderAssItemsResult.getData();
                    // 计算数量写入SalesExp
                    int count = 1;
                    for (OpsOrderAssignResultVO orderAssItem : orderAssItemList) {
                        int num = 0;
                        if (orderAssItem.getQuantity() != null) {
                            num = (orderAssItem.getQuantity() / rcvDetailVO.getQuantity()) * samplebalDO.getQuantity();
                        }
                        salesExpDO = new SalesExpDO();
                        salesExpDO.setInvoiceNo(invoiceNo);
                        salesExpDO.setSubModel(orderAssItem.getModelno());
                        salesExpDO.setSubQty(num);
                        salesExpDO.setMainQty(samplebalDO.getQuantity());
                        salesExpDO.setProdFlag("1");
                        salesExpDO.setOwnerCompanyId("CN0");
                        if (count == 1) {
                            salesExpDO.setRemark("免费样品订单"+count);
                        }
                        if (customerVO != null) {
                            salesExpDO.setCustType(customerVO.getCustomerType());
                        }
                        insertSalesExp(salesExpDO,samplebalDO,rcvDetailVO,rcvMasterVO);
                        count++;
                    }
                }
            } else {
                salesExpDO = new SalesExpDO();
                salesExpDO.setInvoiceNo(invoiceNo);
                salesExpDO.setSubModel("");
                salesExpDO.setSubQty(0);
                salesExpDO.setMainQty(samplebalDO.getQuantity());
                salesExpDO.setProdFlag("0");
                salesExpDO.setOwnerCompanyId("CN0");
                if (customerVO != null) {
                    salesExpDO.setCustType(customerVO.getCustomerType());
                }
                insertSalesExp(salesExpDO,samplebalDO,rcvDetailVO,rcvMasterVO);
            }
        }
        return ResultVo.success("写入成本成功");
    }

    public List<SampleBalPropertyAssignDO> getSampleBalPropertyAssignDOList(SamplebalDO samplebalDO) {
        LambdaQueryWrapper<SampleBalPropertyAssignDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SampleBalPropertyAssignDO::getSampleBalId, samplebalDO.getId()).eq(SampleBalPropertyAssignDO::getDelFlag, 0);
        return sampleBalPropertyAssignMapper.selectList(queryWrapper);
    }


    public void insertSalesExp(SalesExpDO salesExpDO, SamplebalDO samplebalDO, RcvDetailVO rcvDetailVO,RcvMasterVO rcvMasterVO) {
        salesExpDO.setTradeCompanyId("CN0");
        salesExpDO.setExpDate(DateUtil.getDate(samplebalDO.getInDate()));
        salesExpDO.setROrderNo(samplebalDO.getRorderNo());
        salesExpDO.setEcode(getECodeByModelNo(samplebalDO.getModelNo())); // E分类码
        salesExpDO.setModelNo(rcvDetailVO.getModelNo());
        // salesExpDO.setMainQty(samplebalDO.getQuantity());
        salesExpDO.setSalesAmt(BigDecimal.ZERO);
//        if (SampleBalAppTypeEnum.ZLZS.getCode().equals(samplebalDO.getAppType()) || SampleBalAppTypeEnum.PKBS.getCode().equals(samplebalDO.getAppType())) {
//            salesExpDO.setCustomerNo("");
//        } else {
//            salesExpDO.setCustomerNo(samplebalDO.getCustomerNo());
//        }
        // bug 19021 20250923
        salesExpDO.setCustomerNo(samplebalDO.getCustomerNo());
        salesExpDO.setDeptNo(samplebalDO.getDeptNo());
//        if(StringUtils.isNotBlank(samplebalDO.getDeptNo())) {
//            salesExpDO.setDeptNo(samplebalDO.getDeptNo());
//        } else {
//            salesExpDO.setDeptNo(rcvMasterVO.getDeptNo());
//        }
        salesExpDO.setProdId("0");
        salesExpDO.setExpCode("4");
        salesExpDO.setEpriceAmt(BigDecimal.ZERO);
        salesExpDO.setPriceCal(BigDecimal.ZERO);
        salesExpDO.setUserNo(samplebalDO.getUserNo());
        if (StringUtils.isNotBlank(samplebalDO.getUserNo())) {
            salesExpDO.setEndUser(samplebalDO.getUserNo());
        } else {
            salesExpDO.setEndUser(samplebalDO.getCustomerNo());
        }
        salesExpDO.setOrdType(samplebalDO.getOrdType());
        salesExpDO.setApplyCode(samplebalDO.getApplyCode());
        salesExpDO.setDataSource(TradeCompanyIdEnum.getCodeName(rcvMasterVO.getTradeCompanyId()));
        if (StringUtils.isBlank(salesExpDO.getRemark())) {
            salesExpDO.setRemark("免费样品订单");
        }
        salesExpDO.setCretime(new Date());
        salesExpDO.setProdType(samplebalDO.getProdCode());
        salesExpDO.setCountryCode("");

        if (StringUtils.isNotBlank(samplebalDO.getDeptDesc())) {
            salesExpDO.setHrUnitId(samplebalDO.getDeptDesc());
        } else {
            salesExpDO.setHrUnitId(samplebalDO.getDeptNo());
//            if (StringUtils.isNotBlank(rcvMasterVO.getHlCode())) {
//                salesExpDO.setHrUnitId(rcvMasterVO.getHlCode());
//            } else if (StringUtils.isNotBlank(rcvMasterVO.getDeptNo())) {
//                salesExpDO.setHrUnitId(rcvMasterVO.getDeptNo());
//            } else if (StringUtils.isNotBlank(rcvMasterVO.getDeliveryDeptNo())) {
//                salesExpDO.setHrUnitId(rcvMasterVO.getDeliveryDeptNo());
//            }
        }
        salesExpDO.setWarehouseCode(samplebalDO.getWarehouseCode());
        salesExpDO.setWarehouseType(getWareHouseType(samplebalDO.getWarehouseCode()));
        log.info("样品订单自动结转时写入SalesExp参数:" + JSONObject.toJSONString(salesExpDO));
        salesExpService.insertSalesExp(salesExpDO);
    }


    /**
     * 根据型号查ECode
     */
    private String getECodeByModelNo(String modelNo) {
        ResultVo<ProductInfoVO> productInfoByModelNo = productServiceFeignApi.getProductInfoByModelNo(modelNo);
        if (productInfoByModelNo.isSuccess() && productInfoByModelNo.getData() != null) {
            ProductInfoVO data = productInfoByModelNo.getData();
            return data.getECode();
        }
        return "";

    }

    private String getWareHouseType(String warehouseCode) {
        if (StringUtils.isBlank(warehouseCode)) {
            return "";
        }
        Object o = redisManager.hGet(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode);
        if (o != null) {
            return o.toString();
        }
        ResultVo<String> warehouseType = commonServiceFeignApi.getWarehouseType(warehouseCode);
        if (warehouseType.isSuccess() && warehouseType.getData() != null) {
            return warehouseType.getData();
        }
        return "";
    }

    /**
     * 导入退货订单，抵消样品结转
     */
//    private void offSetReturnQty() {
//        /**
//         * 查询所有退货样品订单
//         */
//        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SamplebalDO::getOptCode, 3);
//        queryWrapper.eq(SamplebalDO::getBalType, BalTypeEnum.LPFH.getCode());
//        queryWrapper.lt(SamplebalDO::getQuantity, 0);
//        List<SamplebalDO> samplebalDOS = samplebalMapper.selectList(queryWrapper);
//        if (CollectionUtils.isEmpty(samplebalDOS)) {
//            return;
//        }
//        LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
//        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
//        List<SamplebalDO> samplebalDOList;
//        // 遍历所有样品退货时写入的结转数据 (负数)
//        for (SamplebalDO obj : samplebalDOS) {
//            String rorderNo = obj.getRorderNo();
//            query.clear();
//            query.eq(SamplebalDO::getRorderNo, rorderNo)
//                    .gt(SamplebalDO::getQuantity, 0)
//                    .eq(SamplebalDO::getOptCode, "3");
//            samplebalDOList = samplebalMapper.selectList(queryWrapper); // 查询所有待结转的数据
//            if (CollectionUtils.isEmpty(samplebalDOList)) {
//                continue;
//            }
//            int qty = Math.abs(obj.getQuantity());
//            SamplebalDO samplebalDO;
//            // 待转出数据 (数量正数)
//            for (SamplebalDO item : samplebalDOList) {
//                if (item.getQuantity() == null || item.getQuantity() == 0 || qty == 0) {
//                    continue;
//                }
//                if (item.getQuantity() > qty) {
//                    samplebalDO = item;
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,item.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"3")
//                            .set(SamplebalDO::getQuantity,item.getQuantity() - qty);
//                    samplebalMapper.update(null,updateWrapper);
//
//                    samplebalDO.setId(null);
//                    samplebalDO.setQuantity(qty);
//                    samplebalDO.setAppType("9");
//                    samplebalDO.setBalType(BalTypeEnum.LPFH.getCode());
//                    samplebalDO.setOptCode("9");
//                    samplebalDO.setRemark("样品退货结转已抵消");
//                    sampleBalService.insertSampleBal(samplebalDO);
//
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,obj.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"9")
//                            .set(SamplebalDO::getRemark,"退货")
//                            .set(SamplebalDO::getAppType,"9");
//                    samplebalMapper.update(null,updateWrapper);
//                    break;
//                } else if (item.getQuantity() == qty) {
//
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,item.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"9")
//                            .set(SamplebalDO::getRemark,"退货")
//                            .set(SamplebalDO::getAppType,"9");
//                    samplebalMapper.update(null,updateWrapper);
//
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,obj.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"9")
//                            .set(SamplebalDO::getRemark,"样品退货结转已抵消")
//                            .set(SamplebalDO::getAppType,"9");
//                    samplebalMapper.update(null,updateWrapper);
//                    break;
//
//                } else if (item.getQuantity() < qty) {
//                    samplebalDO = item;
//
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,item.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"3")
//                            .set(SamplebalDO::getQuantity, (qty - item.getQuantity())*-1 );
//                    samplebalMapper.update(null,updateWrapper);
//
//                    samplebalDO.setId(null);
//                    samplebalDO.setQuantity(item.getQuantity() * -1);
//                    samplebalDO.setAppType("9");
//                    samplebalDO.setBalType(BalTypeEnum.LPFH.getCode());
//                    samplebalDO.setOptCode("9");
//                    samplebalDO.setRemark("样品退货结转已抵消");
//                    sampleBalService.insertSampleBal(samplebalDO);
//
//                    updateWrapper.clear();
//                    updateWrapper
//                            .eq(SamplebalDO::getId,obj.getId())
//                            .set(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
//                            .set(SamplebalDO::getOptCode,"9")
//                            .set(SamplebalDO::getRemark,"退货")
//                            .set(SamplebalDO::getAppType,"9");
//                    samplebalMapper.update(null,updateWrapper);
//                    qty = qty - item.getQuantity();
//                }
//            }
//        }
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DS("opsdb")
    public ResultVo<String> splitCarryType(SplitSampleBalVO splitSampleBalVO) {

        if (CollectionUtils.isEmpty(splitSampleBalVO.getIds())) {
            return ResultVo.failure("请选择需要拆分数据.");
        }
        if (splitSampleBalVO.getIds().size() == 1 && splitSampleBalVO.getQuantity() == 0) {
            return ResultVo.failure("请输入拆分结转数量.");
        }

        if (StringUtils.isBlank(splitSampleBalVO.getBalType())) {
            return ResultVo.failure("请选择拆分结转类型.");
        }

        List<String> noInsertSalesExpBalType = new ArrayList<>();
        noInsertSalesExpBalType.add(BalTypeEnum.XSKP.getCode()); // 转销售开票
        // 不允许操作结转的状态集
        List<String> notAllowSplitCarryStatus = getNotAllowSplitCarryStatus();
        if (CollectionUtils.isEmpty(notAllowSplitCarryStatus)) {
            return ResultVo.failure("未能从字典表获取结转状态是否可结转.");
        }
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        SamplebalDO samplebalDO;
        StringBuilder msg = new StringBuilder();
        if (splitSampleBalVO.getIds().size() > 1) {
            try {
                for (Long id : splitSampleBalVO.getIds()) {
                    queryWrapper.clear();
                    queryWrapper.eq(SamplebalDO::getId, id);
                    samplebalDO = samplebalMapper.selectOne(queryWrapper);

                    if (samplebalDO == null) {
                        msg.append("id:"+id+"不存在");
                        continue;
                    }
                    // add by LiYingChao from 20221102 bugId 8541
                    if (notAllowSplitCarryStatus.contains(samplebalDO.getOptCode())) {
                        msg.append(" 订单号:"+samplebalDO.getRorderNo()+"已做数据转出的订单，不可修改结转信息.");
                        continue;
                    }

                    if (samplebalDO.getQuantity() == 0) {
                        msg.append(" 订单号:"+samplebalDO.getRorderNo()+"数量为0，不可修改结转信息.");
                        continue;
                    }
                    if (StringUtils.isBlank(samplebalDO.getBalType())) {
                        samplebalDO.setBalType(splitSampleBalVO.getBalType());
                    }
                    // 判断是否营业所
                    if (StringUtils.isNotBlank(splitSampleBalVO.getDeptNo())) {
                        ResultVo<String> resultVo = opsCommonService.getDeptNoByHRSalesDeptNo(splitSampleBalVO.getDeptNo());
                        if (resultVo.isSuccess() && splitSampleBalVO.getDeptNo().equals(resultVo.getData())) {
                            splitSampleBalVO.setHlCode(splitSampleBalVO.getDeptNo());
                        } else {
                            // 根据hl所查找营业所
                            ResultVo<HROrganizationDO> hrOrganInfoByCode = opsCommonService.getHrOrganInfoByCode(splitSampleBalVO.getDeptNo());
                            if (hrOrganInfoByCode.isSuccess()) {
                                splitSampleBalVO.setHlCode(splitSampleBalVO.getDeptNo());
                                splitSampleBalVO.setDeptNo(hrOrganInfoByCode.getData().getParentId());
                            }
                        }
                    }
                    upSampleBal(splitSampleBalVO,samplebalDO,noInsertSalesExpBalType,id);
                    msg.append(" 订单号:"+samplebalDO.getRorderNo()+"结转为"+BalTypeEnum.getCodeName(splitSampleBalVO.getBalType()));
                }
                return ResultVo.success(msg.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            queryWrapper.clear();
            queryWrapper.eq(SamplebalDO::getId, splitSampleBalVO.getIds().get(0));
            samplebalDO = samplebalMapper.selectOne(queryWrapper);
            if (samplebalDO == null) {
                return ResultVo.failure("结转失败:操作数据为假数据(不存在)");
            }
            if (notAllowSplitCarryStatus.contains(samplebalDO.getOptCode())) {
                return ResultVo.failure(samplebalDO.getRorderNo()+"已做数据转出的订单，不可修改结转信息.");
            }
            if (splitSampleBalVO.getQuantity() > samplebalDO.getQuantity()) {
                return ResultVo.failure("输入的结转数量不可大于原数量.");
            }
            if (StringUtils.isBlank(samplebalDO.getBalType())) {
                samplebalDO.setBalType(splitSampleBalVO.getBalType());
            }

            // 判断是否营业所
            if (StringUtils.isNotBlank(splitSampleBalVO.getDeptNo())) {
                ResultVo<String> resultVo = opsCommonService.getDeptNoByHRSalesDeptNo(splitSampleBalVO.getDeptNo());
                if (resultVo.isSuccess() && splitSampleBalVO.getDeptNo().equals(resultVo.getData())) {
                    splitSampleBalVO.setHlCode(splitSampleBalVO.getDeptNo());
                } else {
                    // 根据hl所查找营业所
                    ResultVo<HROrganizationDO> hrOrganInfoByCode = opsCommonService.getHrOrganInfoByCode(splitSampleBalVO.getDeptNo());
                    if (hrOrganInfoByCode.isSuccess()) {
                        splitSampleBalVO.setHlCode(splitSampleBalVO.getDeptNo());
                        splitSampleBalVO.setDeptNo(hrOrganInfoByCode.getData().getParentId());
                    }
                }
            }

            if (splitSampleBalVO.getQuantity() == samplebalDO.getQuantity()) {
                upSampleBal(splitSampleBalVO,samplebalDO,noInsertSalesExpBalType,null);
                return ResultVo.success("订单号:"+samplebalDO.getRorderNo()+"结转为"+BalTypeEnum.getCodeName(splitSampleBalVO.getBalType()));
            }
            List<Long> ids = new ArrayList<>();
            try {
                LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .eq(SamplebalDO::getId, samplebalDO.getId())
                        .set(SamplebalDO::getOptDate,new Date())
                        .set(SamplebalDO::getOptTime,new Date())
                        .set(StringUtils.isNotBlank(splitSampleBalVO.getUserNo()), SamplebalDO::getUserName, splitSampleBalVO.getUserNo())
                        .set(SamplebalDO::getQuantity, samplebalDO.getQuantity() - splitSampleBalVO.getQuantity())
                        .set(StringUtils.isNotBlank(splitSampleBalVO.getDeptNo()), SamplebalDO::getDeptNo, splitSampleBalVO.getDeptNo())
                        .set(StringUtils.isNotBlank(splitSampleBalVO.getHlCode()), SamplebalDO::getDeptDesc, splitSampleBalVO.getHlCode());
                samplebalMapper.update(null, updateWrapper);
                ids.add(samplebalDO.getId());
                // 新增拆分数据
                samplebalDO.setId(null);
                samplebalDO.setQuantity(splitSampleBalVO.getQuantity());
                samplebalDO.setBalType(splitSampleBalVO.getBalType());
                samplebalDO.setDeptNo(splitSampleBalVO.getDeptNo());
                samplebalDO.setRcvDeptNo(splitSampleBalVO.getDeptNo());
                samplebalDO.setDeptDesc(splitSampleBalVO.getHlCode());
                samplebalDO.setOptTime(new Date());
                samplebalDO.setOptDate(new Date());
                samplebalDO.setUserName(splitSampleBalVO.getUserNo());
                if (noInsertSalesExpBalType.contains(splitSampleBalVO.getBalType())) {
                    samplebalDO.setOptCode(SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
                } else {
                    samplebalDO.setBalType(splitSampleBalVO.getBalType());
                    // 当结转方式为良品返回时 选择该单号数量大于0 且 状态为已内耗结转的 结转类型获取发票号前缀
                    if (samplebalDO.getBalType().equals(BalTypeEnum.LPFH.getCode())) {
                        LambdaQueryWrapper<SamplebalDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                        lambdaQueryWrapper
                                .eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo())
                                .gt(SamplebalDO::getQuantity,0)
                                .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.NHJZ.getCode());
                        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(lambdaQueryWrapper);
                        if (CollectionUtils.isNotEmpty(samplebalDOList)) {
                            samplebalDO.setBalType(samplebalDOList.get(0).getBalType());
                        }
                    }
                    String invoice = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
                    if (StringUtils.isBlank(invoice)) {
                        samplebalDO.setOptCode(SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
                    } else {
                        samplebalDO.setInvoiceNo(invoice.trim());
                        samplebalDO.setInDate(new Date());
                        samplebalDO.setOptCode(SampleBalOptCodeEnum.DZC.getCode()); // 待转出
                    }
                }
                samplebalMapper.insert(samplebalDO);
                ids.add(samplebalDO.getId());

                sampleBalService.upSampleBalPropertyAssignResult(ids);

                return ResultVo.success("结转成功.");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * 获取不可结转的状态
     * @return
     */
    public List<String> getNotAllowSplitCarryStatus() {
        List<String> list = new ArrayList<>();
        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.DICT_CODE_SAMPLEORDER_BALSTATUS);
        if (!dataCodes.isSuccess() || CollectionUtils.isEmpty(dataCodes.getData())) {
            return list;
        }
        for (DataCodeVO item : dataCodes.getData()) {
            if ("N".equalsIgnoreCase(item.getExtNote3())) {
                list.add(item.getCode());
            }
        }
        return list;
    }

    public void upSampleBal(SplitSampleBalVO splitSampleBalVO,SamplebalDO samplebalDO,List<String> noInsertSalesExpBalType, Long id) {
        if (id != null) {
            samplebalDO.setId(id);
        }
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getId, samplebalDO.getId())
                .set(SamplebalDO::getOptDate,new Date())
                .set(SamplebalDO::getOptTime,new Date())
                .set(StringUtils.isNotBlank(splitSampleBalVO.getUserNo()), SamplebalDO::getUserName, splitSampleBalVO.getUserNo())
                .set(SamplebalDO::getBalType, splitSampleBalVO.getBalType())
                .set(StringUtils.isNotBlank(splitSampleBalVO.getDeptNo()), SamplebalDO::getDeptNo, splitSampleBalVO.getDeptNo())
                .set(StringUtils.isNotBlank(splitSampleBalVO.getHlCode()), SamplebalDO::getDeptDesc, splitSampleBalVO.getHlCode())
                .set(StringUtils.isNotBlank(splitSampleBalVO.getDeptNo()), SamplebalDO::getRcvDeptNo, splitSampleBalVO.getDeptNo());
        if (noInsertSalesExpBalType.contains(splitSampleBalVO.getBalType())) {
            if (StringUtils.isNotBlank(samplebalDO.getInvoiceNo())) {
                updateWrapper.set(SamplebalDO::getInvoiceNo, "");
            }
            updateWrapper.set(SamplebalDO::getInDate, null);
            updateWrapper.set(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
        } else {
            // 获取发票号
            samplebalDO.setBalType(splitSampleBalVO.getBalType());
           // 当结转方式为良品返回时 选择该单号数量大于0 且 状态为已内耗结转的 结转类型获取发票号前缀
            if (samplebalDO.getBalType().equals(BalTypeEnum.LPFH.getCode())) {
                LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper
                        .eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo())
                        .gt(SamplebalDO::getQuantity,0)
                        .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.NHJZ.getCode());
                List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
                if (CollectionUtils.isNotEmpty(samplebalDOList)) {
                    samplebalDO.setBalType(samplebalDOList.get(0).getBalType());
                }
            }
            String invoice = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
            if (StringUtils.isNotBlank(invoice)) {
                updateWrapper.set(SamplebalDO::getInvoiceNo,invoice.trim());
                updateWrapper.set(SamplebalDO::getInDate,new Date());
                updateWrapper.set(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DZC.getCode()); // 待转出
            } else {
                updateWrapper.set(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
            }
        }
        samplebalMapper.update(null, updateWrapper);
        String desc = "订单号:"+samplebalDO.getRorderNo()+"结转为"+BalTypeEnum.getCodeName(splitSampleBalVO.getBalType()) + "结转部门:"+samplebalDO.getDeptNo()+"->"+splitSampleBalVO.getDeptNo();
        // 记录日志
        insertOrderLog(samplebalDO,desc);
    }

    @Override
    public void exportSampleBalData(SampleBalRequest request) {

        long startTimer = System.currentTimeMillis();

        LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
        query
                // .notIn(SamplebalDO::getOptCode, optCodes)
                .likeRight(StringUtils.isNotBlank(request.getCustomerNo()), SamplebalDO::getCustomerNo, request.getCustomerNo())
                .likeRight(StringUtils.isNotBlank(request.getModelNo()), SamplebalDO::getModelNo, request.getModelNo())
                .likeRight(StringUtils.isNotBlank(request.getRorderNo()), SamplebalDO::getRorderNo, request.getRorderNo())
                .likeRight(StringUtils.isNotBlank(request.getApplyCode()), SamplebalDO::getApplyCode, request.getApplyCode())
                .in(CollectionUtils.isNotEmpty(request.getOptCode()), SamplebalDO::getOptCode, request.getOptCode())
                .in(CollectionUtils.isNotEmpty(request.getApplyType()), SamplebalDO::getAppType, request.getApplyType())
                .in(CollectionUtils.isNotEmpty(request.getBalType()), SamplebalDO::getBalType, request.getBalType())
                // 操作日期
                .ge(StringUtils.isNotBlank(request.getOptDateStart()), SamplebalDO::getOptDate, request.getOptDateStart())
                .le(StringUtils.isNotBlank(request.getOptDateEnd()), SamplebalDO::getOptDate, request.getOptDateEnd())
                // 出库日期
                .ge(StringUtils.isNotBlank(request.getExpDateStart()), SamplebalDO::getExpDate, request.getExpDateStart())
                .le(StringUtils.isNotBlank(request.getExpDateEnd()), SamplebalDO::getExpDate, request.getExpDateEnd())
                // 入库日期
                .ge(StringUtils.isNotBlank(request.getInDateStart()), SamplebalDO::getInDate, request.getInDateStart())
                .le(StringUtils.isNotBlank(request.getInDateEnd()), SamplebalDO::getInDate, request.getInDateEnd())
                // 创建日期
                .ge(StringUtils.isNotBlank(request.getCreDateStart()), SamplebalDO::getCreatetime, request.getCreDateStart())
                .le(StringUtils.isNotBlank(request.getCreDateEnd()), SamplebalDO::getCreatetime, request.getCreDateEnd());
        if (CollectionUtils.isNotEmpty(request.getDeptNos())) {
            query.and(wrapper -> wrapper.in(SamplebalDO::getDeptNo, request.getDeptNos())
                    .or().in(SamplebalDO::getDeptDesc, request.getDeptNos()));
        }
        query.orderByDesc(SamplebalDO::getCreatetime);
        List<SamplebalDO> samplebalDOS = samplebalMapper.selectList(query);

        String path = "template/ExportSampleBalData.xlsx";
        ExcelUtil excel = new ExcelUtil(path);
        excel.openSheet(0);
        // 向模板中写入数据
        int row = 1;
        int cel;
        Map<String, String> nameMap = new HashMap<>();

        for (SamplebalDO item : samplebalDOS) {

            if (StringUtils.isNotBlank(item.getDeptNo())) {
                if (!nameMap.containsKey(item.getDeptNo())) {
                    nameMap.put(item.getDeptNo(), opsCommonService.getDeptNameByNo(item.getDeptNo()));
                }
                item.setDeptName(nameMap.get(item.getDeptNo()));
            }

            // 展览展示品实物所在营业所
            if (StringUtils.isNotBlank(item.getRcvDeptNo())) {
                if (!nameMap.containsKey(item.getRcvDeptNo())) {
                    nameMap.put(item.getRcvDeptNo(), opsCommonService.getDeptNameByNo(item.getRcvDeptNo()));
                }
                item.setRcvDeptName(nameMap.get(item.getRcvDeptNo()));
            }

            if (StringUtils.isNotBlank(item.getDeptDesc())) {
                if (!nameMap.containsKey(item.getDeptDesc())) {
                    nameMap.put(item.getDeptDesc(), opsCommonService.getDeptNameByNo(item.getDeptDesc()));
                }
                item.setHlCodeName(nameMap.get(item.getDeptDesc()));
            }

            item.setOrdType(OrderTypeEnum.getCodeName(item.getOrdType()));
            if (StringUtils.isNotBlank(item.getAppType())) {
                item.setAppType(SampleBalAppTypeEnum.getCodeName(item.getAppType().trim()));
            }
            item.setBalType(BalTypeEnum.getCodeName(item.getBalType()));
            item.setOptCode(SampleBalOptCodeEnum.getCodeName(item.getOptCode()));

            cel = 0;
            excel.setCellValue(row, cel++, item.getRorderNo());
            excel.setCellValue(row, cel++, item.getApplyCode());
            excel.setCellValue(row, cel++, item.getCustomerNo());
            excel.setCellValue(row, cel++, item.getUserNo());
            excel.setCellValue(row, cel++, item.getModelNo());
            excel.setCellValue(row, cel++, item.getQuantity());
            excel.setCellValue(row, cel++, item.getDeptDesc());
            excel.setCellValue(row, cel++, item.getHlCodeName());
            excel.setCellValue(row, cel++, item.getDeptNo());
            excel.setCellValue(row, cel++, item.getDeptName());
            if (StringUtils.isNotBlank(item.getBalType()) && item.getBalType().equals(BalTypeEnum.zp.getCodeName())) {
                excel.setCellValue(row, cel++, item.getPrice());
            } else {
                excel.setCellValue(row,cel++,"");
            }
            excel.setCellValue(row, cel++, item.getPriceApply());
            excel.setCellValue(row, cel++, item.getOrdType());
            excel.setCellValue(row, cel++, item.getAppType());
            excel.setCellValue(row, cel++, item.getBalType());
            excel.setCellValue(row, cel++, item.getOptDate());
            excel.setCellValue(row, cel++, item.getOptCode());
            excel.setCellValue(row, cel++, item.getRemark());
            excel.setCellValue(row, cel++, item.getExpDate());
            excel.setCellValue(row, cel++, item.getInDate());
            excel.setCellValue(row, cel++, item.getInvoiceNo());
            excel.setCellValue(row, cel++, item.getRcvDeptNo());
            excel.setCellValue(row, cel++, item.getRcvDeptName());
            excel.setCellValue(row, cel++, item.getClaimNo());
            excel.setCellValue(row, cel++, item.getClaimAmount());
            excel.setCellValue(row, cel, item.getExpressCompany());
            row++;
        }
        excel.writeToResponse(response, "ExportSampleBalData.xlsx");
        long endTimer = System.currentTimeMillis();
        log.info("样品结转导出 " + samplebalDOS.size() + " 耗时: " + (endTimer - startTimer) / 1000 + " 秒");
    }

    /**
     * @param
     * @return
     */
    @Override
    public ResultVo<String> autoToSalesInvoice() {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                    .eq(SamplebalDO::getBalType,BalTypeEnum.XSKP.getCode())
                    .eq(SamplebalDO::getOptCode, SampleBalOptCodeEnum.DZC.getCode());
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.success("暂无所需开票数据");
        }
        int successCount = 0;
        for (SamplebalDO item : samplebalDOList) {
            InvoicingSalesParams params = new InvoicingSalesParams();
            params.setId(new String[]{String.valueOf(item.getId())});
            params.setPrice(String.valueOf(item.getPriceApply()));
            ResultVo<String> resultVo = toSalesInvoice(params);
            if (resultVo.isSuccess()) {
                successCount++;
            }
        }
        return ResultVo.success("自动开票完毕. 共计:" + successCount +"条");
    }

    @Override
    public ResultVo<String> againBal(SplitSampleBalVO splitSampleBalVO) {
        if (splitSampleBalVO == null || splitSampleBalVO.getId() == null) {
            return ResultVo.failure("请选择待结转数据.");
        }
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getId, splitSampleBalVO.getId());
        SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper);
        if (samplebalDO == null) {
            return ResultVo.failure("数据不存在.");
        }
        Long oldId = samplebalDO.getId();
        if (!samplebalDO.getOptCode().equals(SampleBalOptCodeEnum.NHJZ.getCode()) && !samplebalDO.getOptCode().equals(SampleBalOptCodeEnum.YZCPD.getCode())) {
            return ResultVo.failure("已作出数据转出的数据才可重新结转.");
        }

        if(samplebalDO.getOptCode().equals(SampleBalOptCodeEnum.YWJ.getCode()) || samplebalDO.getOptCode().equals(SampleBalOptCodeEnum.ZXS.getCode())) {
            return ResultVo.failure("结转方式为已开票和已良品返回的不允许再次结转.");
        }

        String oldOptCode = samplebalDO.getOptCode();
        String oldBalType = samplebalDO.getBalType();
        Date oldInDate = samplebalDO.getInDate();
        Date nowDate = new Date();
        // 生成发票号
        String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
        if (StringUtils.isBlank(invoiceNo)) {
            return ResultVo.failure("生成冲负数据的发票号为空.重新结转失败.");
        }

        List<Long> ids = new ArrayList<>();
        ids.add(samplebalDO.getId());

        // 生成正负两条数据
        // 正数
        samplebalDO.setBalType("");
        samplebalDO.setOptCode(SampleBalOptCodeEnum.DJZ.getCode()); // 待结转
        samplebalDO.setInDate(null);
        samplebalDO.setOptDate(nowDate);
        samplebalDO.setOptTime(nowDate);
        samplebalDO.setInvoiceNo(null);
        samplebalDO.setUserName(splitSampleBalVO.getUserNo());
        samplebalMapper.insert(samplebalDO);
        ids.add(samplebalDO.getId());
        // 负数
        samplebalDO.setBalType(oldBalType);
        samplebalDO.setQuantity(samplebalDO.getQuantity() * -1);
        samplebalDO.setOptCode(SampleBalOptCodeEnum.DZC.getCode()); // 待转出
        samplebalDO.setOptDate(nowDate);
        samplebalDO.setOptTime(nowDate);
        samplebalDO.setInvoiceNo(invoiceNo);
        samplebalDO.setInDate(nowDate);
        samplebalDO.setUserName(splitSampleBalVO.getUserNo());
        samplebalMapper.insert(samplebalDO);
        ids.add(samplebalDO.getId());

        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SamplebalDO::getId,oldId).set(SamplebalDO::getSource,String.valueOf(oldId));
        samplebalMapper.update(null,updateWrapper);

        LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
        query
                .eq(SamplebalDO::getRorderNo,samplebalDO.getRorderNo())
                .eq(SamplebalDO::getQuantity,samplebalDO.getQuantity())
                .eq(SamplebalDO::getBalType,oldBalType).eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode());
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(query);
        if (CollectionUtils.isNotEmpty(samplebalDOList)){
            SamplebalDO obj = samplebalDOList.get(0);
            updateWrapper.clear();
            updateWrapper.eq(SamplebalDO::getId,obj.getId()).set(SamplebalDO::getSource,oldId+"-"+obj.getId());
            samplebalMapper.update(null,updateWrapper);
        }

        /**
         * 修正资产分配表
         * 25年1月1日之后才写入到资产分配表
         */
        if(DateUtil.getYear(samplebalDO.getCreatetime()) >= 2025) {
            sampleBalService.againBalUpPropertyAssign(ids);
        }

        // 当已转出盘点做重新结转 需要将负数写入sampleOrderManage表
        if (oldOptCode.equals(SampleBalOptCodeEnum.YZCPD.getCode())) {
            SampleOrderManageDO sampleOrderManageDO = new SampleOrderManageDO();
            sampleOrderManageDO.setModelNo(samplebalDO.getModelNo());
            sampleOrderManageDO.setOrderNo(samplebalDO.getRorderNo());
            sampleOrderManageDO.setShipDate(samplebalDO.getExpDate());
            sampleOrderManageDO.setImpQty(samplebalDO.getQuantity());
            sampleOrderManageDO.setStatus(1);
            if (StringUtils.isBlank(samplebalDO.getRcvDeptNo())) {
                samplebalDO.setRcvDeptNo(samplebalDO.getDeptNo());
            }
            sampleOrderManageDO.setDeptNo(samplebalDO.getRcvDeptNo());
            sampleOrderManageDO.setUpdateTime(nowDate);
            sampleOrderManageDO.setCreateTime(nowDate);
            sampleOrderManageDO.setOutTime(samplebalDO.getInDate()); // 结转时间
            sampleOrderManageMapper.insert(sampleOrderManageDO);
        }

        // 记录重新结转日志
        insertOrderLog(samplebalDO,"重新结转");
        return ResultVo.success("操作成功.");
    }

//    @Override
//    public ResultVo<PageInfo<SampleOrderManageVO>> listSampleOrderManage(SampleOrderManageQuery request, Page page) {
//        if (request == null) {
//            return ResultVo.failure("参数不可为空");
//        }
//
//        LambdaQueryWrapper<SampleOrderManageDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .in(CollectionUtils.isNotEmpty(request.getDeptNos()),SampleOrderManageDO::getDeptNo,request.getDeptNos())
//                .eq(StringUtils.isNotBlank(request.getOrderNo()),SampleOrderManageDO::getOrderNo,request.getOrderNo())
//                .eq(StringUtils.isNotBlank(request.getModelNo()),SampleOrderManageDO::getModelNo,request.getModelNo())
//                .ge(StringUtils.isNotBlank(request.getInDateStart()),SampleOrderManageDO::getOutTime,request.getInDateStart())
//                .le(StringUtils.isNotBlank(request.getInDateEnd()),SampleOrderManageDO::getOutTime,request.getInDateEnd())
//                .ge(StringUtils.isNotBlank(request.getCreDateStart()),SampleOrderManageDO::getCreateTime,request.getCreDateStart())
//                .le(StringUtils.isNotBlank(request.getCreDateEnd()),SampleOrderManageDO::getCreateTime,request.getCreDateEnd())
//                .ge(StringUtils.isNotBlank(request.getShipDateStart()),SampleOrderManageDO::getShipDate,request.getShipDateStart())
//                .le(StringUtils.isNotBlank(request.getShipDateEnd()),SampleOrderManageDO::getShipDate,request.getShipDateEnd());
//
//        PageInfo<SampleOrderManageDO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize()).doSelectPageInfo(() -> {
//            sampleOrderManageMapper.selectList(queryWrapper);
//        });
//        PageInfo<SampleOrderManageVO> pageInfoVO = BeanCopyUtil.pageDto2Vo(pageInfo, SampleOrderManageVO.class);
//        pageInfoVO.setList(handCommonData(pageInfoVO.getList()));
//        return ResultVo.success(pageInfoVO);
//    }
//
//    public List<SampleOrderManageVO> handCommonData(List<SampleOrderManageVO> list) {
//        if (CollectionUtils.isEmpty(list)) {
//            return new ArrayList<>();
//        }
//        ResultVo<List<DataCodeVO>> dataCodes = dictCommonService.getDataCodes(com.smc.smccloud.model.constants.Constants.ZLZS_MANAGE_STATUS);
//        Map<String,String> map = new HashMap<>();
//        if (dataCodes.isSuccess() && CollectionUtils.isNotEmpty(dataCodes.getData())) {
//           for (DataCodeVO item : dataCodes.getData()) {
//               map.put(item.getCode(),item.getCodeName());
//           }
//        }
//        for (SampleOrderManageVO item : list) {
//            if (StringUtils.isNotBlank(item.getDeptNo())) {
//                item.setDeptName("["+item.getDeptNo()+"]"+opsCommonService.getDeptNameByNo(item.getDeptNo()));
//            }
//            if (StringUtils.isNotBlank(item.getManager())) {
//                item.setManageName("["+item.getManager()+"]"+opsCommonService.getEmpSaleNameByNo(item.getManager()));
//            }
//            if (item.getStatus() != null) {
//                item.setStatusName(map.get(String.valueOf(item.getStatus())));
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public void exportSampleOrderManage(SampleOrderManageQuery request) {
//        long startTimer = System.currentTimeMillis();
//        LambdaQueryWrapper<SampleOrderManageDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper
//                .in(CollectionUtils.isNotEmpty(request.getDeptNos()),SampleOrderManageDO::getDeptNo,request.getDeptNos())
//                .eq(StringUtils.isNotBlank(request.getOrderNo()),SampleOrderManageDO::getOrderNo,request.getOrderNo())
//                .eq(StringUtils.isNotBlank(request.getModelNo()),SampleOrderManageDO::getModelNo,request.getModelNo())
//                .ge(StringUtils.isNotBlank(request.getInDateStart()),SampleOrderManageDO::getOutTime,request.getInDateStart())
//                .le(StringUtils.isNotBlank(request.getInDateEnd()),SampleOrderManageDO::getOutTime,request.getInDateEnd())
//                .ge(StringUtils.isNotBlank(request.getCreDateStart()),SampleOrderManageDO::getCreateTime,request.getCreDateStart())
//                .le(StringUtils.isNotBlank(request.getCreDateEnd()),SampleOrderManageDO::getCreateTime,request.getCreDateEnd())
//                .ge(StringUtils.isNotBlank(request.getShipDateStart()),SampleOrderManageDO::getShipDate,request.getShipDateStart())
//                .le(StringUtils.isNotBlank(request.getShipDateEnd()),SampleOrderManageDO::getShipDate,request.getShipDateEnd());
//
//        List<SampleOrderManageDO> sampleOrderManageDOS = sampleOrderManageMapper.selectList(queryWrapper);
//
//        if (CollectionUtils.isEmpty(sampleOrderManageDOS)) {
//            return;
//        }
//        String path = "template/ExportSampleOrderManage.xlsx";
//        ExcelUtil excel = new ExcelUtil(path);
//        excel.openSheet(0);
//        // 向模板中写入数据
//        int row = 1;
//        int cel;
//        List<SampleOrderManageVO> sampleOrderManageVOS = handCommonData(BeanCopyUtil.copyList(sampleOrderManageDOS, SampleOrderManageVO.class));
//        for (SampleOrderManageVO item : sampleOrderManageVOS) {
//            cel = 0;
//            excel.setCellValue(row, cel++, item.getOrderNo());
//            excel.setCellValue(row, cel++, item.getModelNo());
//            excel.setCellValue(row, cel++, item.getImpQty());
//            excel.setCellValue(row, cel++, item.getShipDate());
//            excel.setCellValue(row, cel++, item.getOutTime());
//            excel.setCellValue(row, cel++, item.getStatusName());
//            excel.setCellValue(row, cel++, item.getDeptName());
//            excel.setCellValue(row, cel++, item.getManageName());
//            excel.setCellValue(row, cel++, item.getCreateTime());
//            excel.setCellValue(row, cel, item.getUpdateTime());
//            row++;
//        }
//        excel.writeToResponse(response, "ExportSampleBalData.xlsx");
//        long endTimer = System.currentTimeMillis();
//        log.info("展览展示品导出 " + sampleOrderManageDOS.size() + " 耗时: " + (endTimer - startTimer) / 1000 + " 秒");
//    }

    @Override
    public ResultVo<String> exportOverdueBalData() {
        // 查询所有超过2月未加转的数据
        Date date = DateUtil.addDay(DateUtil.getNow(), -60);
        String dateString = DateUtil.dateToDateString(date) + " 00:00:00";

        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DJZ.getCode())
                .lt(SamplebalDO::getCreatetime,dateString);
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(samplebalDOList)) {
            return ResultVo.failure("暂无逾期数据");
        }
        // 按照营业所分组
        Map<String, List<SamplebalDO>> map = new HashMap<>();
        List<SamplebalDO> mapList;
        for (SamplebalDO item : samplebalDOList) {
            if (StringUtils.isBlank(item.getDeptNo())) {
                continue;
            }
            if (!map.containsKey(item.getDeptNo().trim())) {
                mapList = new ArrayList<>();
                mapList.add(item);
                map.put(item.getDeptNo(), mapList);
            } else {
                map.get(item.getDeptNo()).add(item);
            }
        }
        List<SamplebalDO> list ;
        StringBuilder msg = new StringBuilder();
        for (String getDeptNo : map.keySet()) {
            if (redisManager.hasKey(com.smc.smccloud.model.constants.Constants.OVER_TIME_NOBAL_DEPTNOS+getDeptNo)) {
                continue;
            }
            list = map.get(getDeptNo);
            ResultVo<String> resultVo = writeExcel(list, getDeptNo);
            if (resultVo.isSuccess()) {
                // 记录已发送逾期未结转通知的营业所
                redisManager.set(com.smc.smccloud.model.constants.Constants.OVER_TIME_NOBAL_DEPTNOS+getDeptNo,getDeptNo,60*60*24*7);
            } else {
                msg.append(resultVo.getMessage());
            }
        }
        if (StringUtils.isNotBlank(msg.toString())) {
            return ResultVo.failure(msg.toString());
        }
        return ResultVo.success("操作完毕.");
    }

    @Override
    public void downLoadNoBalFileByYearMonth(String yearMonth) {
        if (StringUtils.isBlank(yearMonth)) {
            return;
        }
        // 下载服务器上的文件
        String path = serverPath + com.smc.smccloud.model.constants.Constants.SAVEFILEPATH_SAMPLEVERNOBAL+yearMonth;
        log.info("下载服务器上的文件 path = " + path);
        try {
            FileUtil.zipDownloadRelativePath(response,path);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 写入excel并推送给营业所
    //    1、发布后，如有未发布成功的需要做系统提示。如因报错需要重新发布的，已成功发布的不要重复发布。
    //    2、当次清单留存，可自动保存到某个文件夹。
    //    3、页面筛选和发布数据时不按holon管理，而是该营业所的全部数据。
    @Override
    public ResultVo<String> writeExcel(List<SamplebalDO> list,String deptNo) {

        // samplebal表里的deptNo值
        String realDeptNo = deptNo;

        // 获取营业所的名称
        ResultVo<HROrganizationDO> hrOrganInfoByCode = opsCommonService.getHrOrganInfoByCode(realDeptNo);
        String realname = "";
        if (hrOrganInfoByCode != null && hrOrganInfoByCode.isSuccess()) {
            realname = hrOrganInfoByCode.getData().getName();
        }

        if (StringUtils.isBlank(realname)) {
            return ResultVo.failure("未能获取"+deptNo+"部门名称,发送邮件失败");
        }

        String path = "template/overdueNoBal.xlsx";
        String backUpEmail = "renyan@smc.com.cn;cn_sales@smc.com.cn";
        String csEmail = "smccnorder@smc.com.cn";

        // 如果是HL 转为营业所 如果是营业所 直接返回营业所
        ResultVo<String> deptNoByHRSalesDeptNo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(deptNo);
        if (!deptNoByHRSalesDeptNo.isSuccess()) {
            return ResultVo.failure("未能获取"+deptNo+"部门信息.请确认部门是否正确.");
        }

        deptNo = deptNoByHRSalesDeptNo.getData();

        // 获取邮箱
        ResultVo<com.smc.smccloud.model.DepartmentVO> departmentInfo = opsCommonService.getDepartmentInfo(deptNo);
        if (!departmentInfo.isSuccess() || departmentInfo.getData() == null) {
            return ResultVo.failure("未能获取"+deptNo+"部门信息.请确认部门是否正确.");
        }
        DepartmentVO departmentVO = departmentInfo.getData();

        StringBuilder emailAddr = new StringBuilder();
        if (StringUtils.isNotBlank(departmentVO.getEmailAddr())) {
            emailAddr.append(departmentVO.getEmailAddr()).append(";");
        }
        if (StringUtils.isNotBlank(departmentVO.getEmailDirector())) {
            emailAddr.append(departmentVO.getEmailDirector()).append(";");
        }

        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 3;
        int cel;
        for (SamplebalDO item : list) {

            if (StringUtils.isNotBlank(item.getAppType())) {
                item.setAppType(SampleBalAppTypeEnum.getCodeName(item.getAppType().trim()));
            }
            item.setOptCode(SampleBalOptCodeEnum.getCodeName(item.getOptCode()));
            if (StringUtils.isNotBlank(item.getCustomerNo())) {
                item.setCustomerNo(opsCommonService.getCustomerNameByNo(item.getCustomerNo()));
            }

            cel = 0;
            excel.setCellValue(row, cel++, item.getRorderNo());
            excel.setCellValue(row, cel++, item.getModelNo());
            excel.setCellValue(row, cel++, item.getQuantity());
            excel.setCellValue(row, cel++, item.getCustomerNo());
            excel.setCellValue(row, cel++, item.getAppType());
            excel.setCellValue(row, cel++, item.getExpDate());
            excel.setCellValue(row, cel++, item.getRemark());
            excel.setCellValue(row, cel, DateUtil.addDay(item.getCreatetime(),60));
            row++;
        }

        String fileName = realDeptNo+realname+"未结转清单.xlsx";
        InputStream excelInputStream = excel.convertTo();
        List<InputStream> inputStreams;
        try {
           inputStreams = FileUtil.cloneInputStream(excelInputStream, 2);
        } catch (Exception e) {
            log.error("复制流发生异常: {}",e.getMessage(),e);
            return ResultVo.failure("复制流发生异常"+e.getMessage());
        }
        if (CollectionUtils.isEmpty(inputStreams) || inputStreams.size() != 2) {
            return ResultVo.failure("复制InputStream流发生异常");
        }

        // 保存发送附件至服务器  /ops/files/overTimeNoBalFiles 下
        // FileUtil.uploadFileWithStream(inputStreams.get(0),serverPath+ com.smc.smccloud.model.constants.Constants.SAVEFILEPATH_SAMPLEVERNOBAL+DateUtil.getYearMonthCode(new Date()),fileNameWithDate);

        // 发送邮件
        String subject = realDeptNo+realname+"未结转清单";
        Map<String, InputStream> attachment = new LinkedHashMap<>(1);
        attachment.put(fileName, inputStreams.get(1)); // 附件

        if (StringUtils.isNotBlank(emailAddr.toString())) {

            String pathFile = filePath + File.separator + "overdueNoBal" + File.separator + DateUtil.getYearMonthDay(new Date());
            Boolean aBoolean = FileUtil.uploadFileWithStream(inputStreams.get(1), pathFile, fileName);
            if (aBoolean) {
                OpsMailDO opsMailDO = new OpsMailDO();
                opsMailDO.setMailTo(emailAddr.toString().replaceAll(";", ","));
                opsMailDO.setSubject(subject);
                opsMailDO.setContext("详情请查看附件[" + fileName + "],本邮件由系统自动发送，请勿直接回复本邮件");
                opsMailDO.setSendDate(new Date());
                if (StringUtils.isNotBlank(csEmail)) {
                    opsMailDO.setCc(csEmail.replaceAll(";",","));
                }
                opsMailDO.setBcc(null);
                opsMailDO.setStatus(SendStatusEnum.INIT.getType());
                opsMailDO.setFileUrls(pathFile+ File.separator+fileName);
                opsMailDO.setInsertTime(new Date());
                opsCommonService.insertOpsMail(opsMailDO);
            }
//            EmailUtil.send(mailSender,emailAddr.toString() , csEmail,null, subject,
//                    "详情请查看附件[" + fileName + "],本邮件由系统自动发送，请勿直接回复本邮件", attachment);
        } else {
            emailAddr.append(backUpEmail);
            EmailUtil.send(mailSender,emailAddr.toString() , csEmail,null, subject,
                    "未查到"+realname+"邮箱地址,请进行维护,邮件内容详情请查看附件[" + fileName + "],本邮件由系统自动发送，请勿直接回复本邮件", attachment);
        }
        return ResultVo.success(realDeptNo+"成功发送逾期未结转通知邮件.");
    }



    @Override
    @DS("sharedb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public SampleOrderApplyDO findSampleOrderApplyByOrderNo(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return null;
        }
        LambdaUpdateWrapper<SampleOrderApplyDO> query = new LambdaUpdateWrapper<>();
        query.eq(SampleOrderApplyDO::getOrderNo, orderNo);
        return sampleorderApplyMapper.selectOne(query);
    }

    @Override
    @DS("sharedb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public SampleorderDetailDO findSampleOrderDetail(String fullOrderNo) {
        if (StringUtils.isBlank(fullOrderNo)) {
            return null;
        }
        LambdaUpdateWrapper<SampleorderDetailDO> query = new LambdaUpdateWrapper<>();
        query.eq(SampleorderDetailDO::getOrderNo, fullOrderNo);
        return sampleorderDetailMapper.selectOne(query);
    }

    /**
     * 取消转销售开票
     * 取消条件：
     * 1.结转状态是已转销售
     * 2.salesinvoice_mid_info是未处理状态isnew=0，并且数量一致
     *
     * 变更内容：
     * 1.状态变成待结转1，结转类型变成null
     * 2.salesinvoice_mid_info 的isnew从0变9，statecode从0变9
     * (变更时间要记录）
     * 3 写入一条orderLog记录
     * @param sampleBalVO
     * @return
     */
    // add by LiYingChao from bugId 8534 in 20221104
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @DS("opsdb")
    public ResultVo<String> cancelTurnSalesInvoice(SplitSampleBalVO sampleBalVO) {
        if (sampleBalVO == null || CollectionUtils.isEmpty(sampleBalVO.getIds())) {
            return ResultVo.failure("请选择需要操作的数据");
        }
        LoginUserDTO loginAuthDto;
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto = new LoginUserDTO();
        }

        List<Long> ids = sampleBalVO.getIds();
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        SamplebalDO samplebalDO;
        StringBuilder msg = new StringBuilder();
        for (Long id : ids) {
            try {
                queryWrapper.clear();
                queryWrapper.eq(SamplebalDO::getId,id);
                samplebalDO = samplebalMapper.selectOne(queryWrapper);
                if (samplebalDO == null) {
                    msg.append(samplebalDO.getRorderNo()+"不存在;");
                    continue;
                }

                if (!"5".equals(samplebalDO.getOptCode())) {
                    msg.append(samplebalDO.getRorderNo()+"处理状态不是已转销售,不可取消;");
                    continue;
                }
                LambdaQueryWrapper<SalesInvoiceMidInfoDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper
                        .eq(SalesInvoiceMidInfoDO::getOrderNo,samplebalDO.getRorderNo())
                        .eq(SalesInvoiceMidInfoDO::getApplyNo,samplebalDO.getApplyCode())
                        .eq(SalesInvoiceMidInfoDO::getModelNo,samplebalDO.getModelNo())
                        .eq(SalesInvoiceMidInfoDO::getQuantity,samplebalDO.getQuantity())
                        .eq(SalesInvoiceMidInfoDO::getIsNew,"0");
                SalesInvoiceMidInfoDO salesInvoiceMidInfoDO = salesInvoiceMidInfoMapper.selectOne(lambdaQueryWrapper);
                if (salesInvoiceMidInfoDO == null) {
                    msg.append(samplebalDO.getRorderNo()+"转销售数据已开票处理不能取消;");
                    continue;
                }
                // 处理状态变为1待结转,结转类型为null
                LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper
                        .eq(SamplebalDO::getId,samplebalDO.getId())
                        .set(SamplebalDO::getOptCode,1)
                        .set(SamplebalDO::getBalType,null)
                        .set(SamplebalDO::getOptTime,new Date())
                        .set(SamplebalDO::getUserName,loginAuthDto.getUserNo());
                samplebalMapper.update(null,updateWrapper);
                // salesinvoice_mid_info 的isnew从0变9，statecode从0变9
                LambdaUpdateWrapper<SalesInvoiceMidInfoDO> up = new LambdaUpdateWrapper<>();
                up.eq(SalesInvoiceMidInfoDO::getId,salesInvoiceMidInfoDO.getId())
                        .set(SalesInvoiceMidInfoDO::getIsNew,"9")
                        .set(SalesInvoiceMidInfoDO::getStateCode,9)
                        .set(SalesInvoiceMidInfoDO::getUpdateTime,new Date());
                salesInvoiceMidInfoMapper.update(null,up);
                // 写入日志
                OrderLogVO orderLogVO = new OrderLogVO();
                orderLogVO.setOrderNo(samplebalDO.getRorderNo());
                orderLogVO.setOptTime(new Date());
                orderLogVO.setDescription(loginAuthDto.getUserNo()+"取消转销售订单");
                orderLogVO.setOptUserId(loginAuthDto.getUserNo());
                orderLogVO.setOptUserName(loginAuthDto.getUserName());
                orderLogFeignApi.addOrderLog(orderLogVO);
                msg.append(samplebalDO.getRorderNo()+"取消转销售成功;");
            } catch (Exception e) {
                log.error("转销售开票处理异常:",e);
                throw new BusinessException(e.getMessage(), e);
            }
        }
        return ResultVo.success(msg.toString());
    }

    // sampleBal optCode 6 -> 8
    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int updateSampleBalOptCodeById(Long id) {
        if(id == null) {
            return 0;
        }
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SamplebalDO::getId,id).set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.YZCPD.getCode());
        return samplebalMapper.update(null,updateWrapper);
    }

    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean updateLPFHData(SampleBalApplyDO sampleBalApplyDO,String optUser) {

        // 当结转方式为良品返回时 选择该单号数量大于0 且 状态为已内耗结转的 结转类型获取发票号前缀
        LambdaQueryWrapper<SamplebalDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(SamplebalDO::getRorderNo,sampleBalApplyDO.getOrderNo())
                .gt(SamplebalDO::getQuantity,0)
                .in(SamplebalDO::getOptCode,Arrays.asList(SampleBalOptCodeEnum.NHJZ.getCode(),SampleBalOptCodeEnum.DZC.getCode()));
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(lambdaQueryWrapper);

        SamplebalDO samplebalDO = new SamplebalDO();
        if (CollectionUtils.isNotEmpty(samplebalDOList)) {
            samplebalDO.setBalType(samplebalDOList.get(0).getBalType());
        }
        samplebalDO.setAppType(sampleBalApplyDO.getApplyType());
        String invoice = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getRorderNo,sampleBalApplyDO.getOrderNo())
                .eq(SamplebalDO::getBalType,BalTypeEnum.LPFH.getCode())
                .in(SamplebalDO::getOptCode,Arrays.asList(SampleBalOptCodeEnum.DJZ.getCode(),SampleBalOptCodeEnum.DZC.getCode()))
                .set(SamplebalDO::getOptTime,new Date())
                .set(SamplebalDO::getInDate,new Date())
                .set(SamplebalDO::getOptDate,new Date());
        if (StringUtils.isNotBlank(invoice)) {
            updateWrapper.set(SamplebalDO::getInvoiceNo,invoice).set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode());
            samplebalMapper.update(null,updateWrapper);
        }
        return true;
    }

    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public ResultVo<String> againBal(SampleBalApplyDO sampleBalApplyDO) {
        boolean xskpFlag = false;
        if (BalTypeEnum.XSKP.getCode().equals(sampleBalApplyDO.getApplyBalType())) {
            xskpFlag = true;
        }

        List<Long> ids = new ArrayList<>();

        // 查询结转源数据
        SamplebalDO sampleBalDOById = getSampleBalDOById(sampleBalApplyDO.getSampleBalId());

        if (sampleBalDOById == null) {
            return ResultVo.failure("暂未找到源结转数据");
        }
        ids.add(sampleBalDOById.getId());

        SamplebalDO cloneSampleBalObj = SerializationUtils.clone(sampleBalDOById);

        // 填充当前单sourceId
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SamplebalDO::getId,sampleBalDOById.getId()).set(SamplebalDO::getSource,String.valueOf(sampleBalDOById.getId()));
        samplebalMapper.update(null,updateWrapper);

        Date nowDate = new Date();
        String invoiceNo = sampleOrderDetailService.getInvoiceNoByCostType(sampleBalDOById);
        if (StringUtils.isBlank(invoiceNo)) {
            return ResultVo.failure("生成冲负数据的发票号为空.重新结转失败.");
        }

        if ( cloneSampleBalObj.getQuantity() > sampleBalApplyDO.getApplyBalQty()) {
            int leftQty = cloneSampleBalObj.getQuantity() - sampleBalApplyDO.getApplyBalQty();
            cloneSampleBalObj.setQuantity(leftQty);
            cloneSampleBalObj.setInvoiceNo(invoiceNo);
            cloneSampleBalObj.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
            cloneSampleBalObj.setOptDate(nowDate);
            cloneSampleBalObj.setOptTime(nowDate);
            cloneSampleBalObj.setInDate(nowDate);
            cloneSampleBalObj.setId(null);
            samplebalMapper.insert(cloneSampleBalObj);
            ids.add(cloneSampleBalObj.getId());
        }

        SamplebalDO balDo = new SamplebalDO();
        balDo.setCustomerNo(sampleBalApplyDO.getCustomerNo());
        balDo.setRorderNo(sampleBalApplyDO.getOrderNo());
        balDo.setModelNo(sampleBalApplyDO.getModelNo());
        balDo.setQuantity(sampleBalApplyDO.getApplyBalQty());
        balDo.setDeptDesc(sampleBalApplyDO.getBalDeptNo());
        balDo.setPrice(sampleBalApplyDO.getPrice());
        if (sampleBalApplyDO.getApplyBalPrice() != null) {
            balDo.setAmount(sampleBalApplyDO.getApplyBalPrice().multiply(BigDecimal.valueOf(sampleBalApplyDO.getApplyBalQty())).setScale(4, RoundingMode.HALF_UP));
        }
        // 获取税率
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfoWithDS("9002", "1");
        BigDecimal taxRate;
        if (dataTypeCodesInfo.isSuccess() && dataTypeCodesInfo.getData() != null) {
            taxRate = new BigDecimal(dataTypeCodesInfo.getData().getExtNote1());
        } else {
            taxRate = new BigDecimal("0.13");
        }
        if(balDo.getAmount() != null) {
            BigDecimal ntaxAmount = PriceCompute.ntaxAmount(balDo.getAmount(), taxRate);
            balDo.setTaxAmount(PriceCompute.taxAmount(balDo.getAmount(),ntaxAmount));
        }

        balDo.setOptDate(nowDate);
        balDo.setAppType(sampleBalApplyDO.getApplyType());
        balDo.setBalType(sampleBalApplyDO.getApplyBalType());
        balDo.setProdFlag(sampleBalDOById.getProdFlag());
        balDo.setProdCode(sampleBalDOById.getProdCode());
        balDo.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
        balDo.setECode(sampleBalDOById.getECode());
        balDo.setDeptNo(sampleBalApplyDO.getApplyDeptNo());
        balDo.setExpDate(sampleBalDOById.getExpDate());
        balDo.setModelinchn(sampleBalDOById.getModelinchn());
        balDo.setInDate(nowDate);
        balDo.setOrdType(sampleBalDOById.getOrdType());
        balDo.setUserName("");
        balDo.setOptTime(nowDate);
        balDo.setApplyCode(sampleBalApplyDO.getApplyNo());
        balDo.setPriceApply(sampleBalApplyDO.getApplyBalPrice());
        balDo.setCreatetime(nowDate);
        balDo.setStockCode(sampleBalDOById.getStockCode());
        balDo.setRcvDeptNo(sampleBalDOById.getRcvDeptNo());
        if(!xskpFlag) {
            SamplebalDO samplebalDO = new SamplebalDO();
            samplebalDO.setAppType(balDo.getAppType());
            samplebalDO.setBalType(balDo.getBalType());
            // 生成发票号
            String invoiceNo2 = sampleOrderDetailService.getInvoiceNoByCostType(samplebalDO);
            if (StringUtils.isBlank(invoiceNo2)) {
                return ResultVo.failure("生成冲负数据的发票号为空.重新结转失败.");
            }
            balDo.setInvoiceNo(invoiceNo2);
        }
        balDo.setUserNo(sampleBalApplyDO.getUserNo());
        samplebalMapper.insert(balDo);
        ids.add(balDo.getId());

        // 生成对冲的负数据
        sampleBalDOById.setQuantity(sampleBalDOById.getQuantity()*-1);
        sampleBalDOById.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
        sampleBalDOById.setInvoiceNo(invoiceNo);
        sampleBalDOById.setOptDate(nowDate);
        sampleBalDOById.setOptTime(nowDate);
        sampleBalDOById.setInDate(nowDate);
        sampleBalDOById.setCreatetime(nowDate);
        sampleBalDOById.setId(null);
        samplebalMapper.insert(sampleBalDOById);
        ids.add(sampleBalDOById.getId());

        /**
         * 修正资产分配表
         * 25年1月1日之后才写入到资产分配表
         */
        if(DateUtil.getYear(sampleBalDOById.getCreatetime()) >= 2025) {
            if(ids.size() == 3) {
                sampleBalService.againBalUpPropertyAssign(ids);
            } else {
                sampleBalService.newAgainBalUpPropertyAssign(ids);
            }
        }

        // 设置负数数据的sourceId
        LambdaQueryWrapper<SamplebalDO> query = new LambdaQueryWrapper<>();
        query
                .eq(SamplebalDO::getRorderNo,sampleBalDOById.getRorderNo())
                .eq(SamplebalDO::getQuantity,sampleBalDOById.getQuantity())
                .eq(SamplebalDO::getBalType,sampleBalDOById.getBalType())
                .eq(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode());
        List<SamplebalDO> samplebalDOList = samplebalMapper.selectList(query);
        if (CollectionUtils.isNotEmpty(samplebalDOList)){
            SamplebalDO obj = samplebalDOList.get(0);
            updateWrapper.clear();
            updateWrapper.eq(SamplebalDO::getId,obj.getId()).set(SamplebalDO::getSource,sampleBalApplyDO.getSampleBalId()+"-"+obj.getId());
            samplebalMapper.update(null,updateWrapper);
        }

        /**
         * 将门户侧申请的结转id 回调给门户 bug 18517
         */
        insertTask(balDo.getId(), sampleBalApplyDO.getSampleBalApplyNo());


        return ResultVo.success("操作成功");
    }

    /**
     * bug bug 18517
     * @param id
     * @param applyNo
     */
    public void insertTask(Long id, String applyNo) {
        DealReturnOpsParamVO dealReturnOpsParamVO = new DealReturnOpsParamVO();
        OpsSalesCommonParamVO opsSalesCommonParamVO = new OpsSalesCommonParamVO();
        SampleBalApplyCallBackVO callBackVO = new SampleBalApplyCallBackVO();
        callBackVO.setId(id);
        callBackVO.setSampleBalApplyNo(applyNo);
        opsSalesCommonParamVO.setData(callBackVO);
        DealReturnOpsParam param = new DealReturnOpsParam();
        param.setOpsSalesCommonParamVo(opsSalesCommonParamVO);
        // 样品结转来源ID
        dealReturnOpsParamVO.setApplyType(13);
        dealReturnOpsParamVO.setDealReturnOpsParam(param);


        OpsSalesCommonParamVO bean = new OpsSalesCommonParamVO();
        bean.setData(dealReturnOpsParamVO);

        String jsonStr = JSONUtil.toJsonStr(bean);

        taskNoticCommonMapper.insertTaskNotice(UIDGenerator.generateUID(), "S",
                applyNo, "",
                jsonStr, "1", "0", "sys");
    }


    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Boolean updateSampleBalOptCodeById(SampleBalApplyDO sampleBalApplyDO,String optUser,String invoiceNo) {
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getId,sampleBalApplyDO.getSampleBalId())
                .set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode())
                .set(SamplebalDO::getOptDate,new Date())
                .set(SamplebalDO::getOptTime, new Date())
                .set(SamplebalDO::getInDate, new Date())
                .set(SamplebalDO::getInvoiceNo, invoiceNo)
                .set(SamplebalDO::getUserNo, sampleBalApplyDO.getUserNo())
                .set(SamplebalDO::getCustomerNo,sampleBalApplyDO.getCustomerNo())
                .set(SamplebalDO::getUserName,optUser)
                .set(SamplebalDO::getPriceApply,sampleBalApplyDO.getApplyBalPrice())
                .set(SamplebalDO::getQuantity,sampleBalApplyDO.getApplyBalQty())
                .set(SamplebalDO::getBalType,sampleBalApplyDO.getApplyBalType());
        int update = samplebalMapper.update(null, updateWrapper);
        if (update == 1) {
            return true;
        }
        return false;
    }

    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int updateSamplebalDO(SampleBalApplyDO sampleBalApplyDO) {
        LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(SamplebalDO::getId,sampleBalApplyDO.getSampleBalId())
                .set(SamplebalDO::getOptCode,SampleBalOptCodeEnum.DZC.getCode())
                .set(SamplebalDO::getPriceApply,sampleBalApplyDO.getApplyBalPrice())
                .set(SamplebalDO::getOptTime,new Date())
                .set(SamplebalDO::getOptDate,new Date());
       return samplebalMapper.update(null, updateWrapper);
    }

    @Override
    @DS("opsdb")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Boolean updateSampleBySmapleApply(SampleBalApplyDO sampleBalApplyDO, String optUser, String invoiceNo) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SamplebalDO::getId,sampleBalApplyDO.getSampleBalId());
        SamplebalDO samplebalDO = samplebalMapper.selectOne(queryWrapper);
        if (samplebalDO == null) {
            throw new RuntimeException("未找到对应的结转数据");
        }
        Integer quantity = samplebalDO.getQuantity();
        Integer applyBalQty = sampleBalApplyDO.getApplyBalQty();
        if (quantity.equals(applyBalQty)) {
           return updateSampleBalOptCodeById(sampleBalApplyDO, optUser,invoiceNo);
        } else if (quantity > applyBalQty) {
            int leftQty = quantity - applyBalQty;
            List<Long> ids = new ArrayList<>();
            LambdaUpdateWrapper<SamplebalDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper
                    .eq(SamplebalDO::getId,samplebalDO.getId())
                    .set(SamplebalDO::getOptTime,new Date())
                    .set(SamplebalDO::getOptDate, new Date())
                    .set(SamplebalDO::getQuantity,leftQty)
                    .set(SamplebalDO::getUserName,optUser);
            samplebalMapper.update(null,updateWrapper);
            ids.add(samplebalDO.getId());

            SamplebalDO obj = new SamplebalDO();
            obj.setInvoiceNo(invoiceNo);
            obj.setInDate(new Date());
            obj.setQuantity(applyBalQty);
            obj.setOptCode(SampleBalOptCodeEnum.DZC.getCode());
            obj.setBalType(sampleBalApplyDO.getApplyBalType());
            obj.setAppType(sampleBalApplyDO.getApplyType());
            obj.setCustomerNo(sampleBalApplyDO.getCustomerNo());
            obj.setRorderNo(sampleBalApplyDO.getOrderNo());
            obj.setModelNo(sampleBalApplyDO.getModelNo());
            obj.setDeptDesc(sampleBalApplyDO.getBalDeptNo());
            obj.setPrice(sampleBalApplyDO.getPrice());
            obj.setSubQty(samplebalDO.getSubQty());
            obj.setSubModelNo(samplebalDO.getSubModelNo());
            // obj.setAmount();
            obj.setOptDate(new Date());
            obj.setProdFlag(samplebalDO.getProdFlag());
            obj.setProdCode(samplebalDO.getProdCode());
            obj.setECode(samplebalDO.getECode());
            obj.setDeptNo(sampleBalApplyDO.getApplyDeptNo());
            obj.setExpDate(samplebalDO.getExpDate());
            obj.setModelinchn(samplebalDO.getModelinchn());
            obj.setOrdType(samplebalDO.getOrdType());
            obj.setUserName(optUser);
            obj.setOptTime(new Date());
            obj.setApplyCode(samplebalDO.getApplyCode());
            obj.setPriceApply(sampleBalApplyDO.getApplyBalPrice());
            obj.setCreatetime(new Date());
            obj.setStockCode(samplebalDO.getStockCode());
            obj.setRcvDeptNo(samplebalDO.getRcvDeptNo());
            obj.setClaimNo(samplebalDO.getClaimNo());
            obj.setClaimAmount(samplebalDO.getClaimAmount());
            obj.setExpressCompany(samplebalDO.getExpressCompany());
            obj.setUserNo(sampleBalApplyDO.getUserNo());
            samplebalMapper.insert(obj);
            ids.add(obj.getId());

            sampleBalService.upSampleBalPropertyAssignResult(ids);

            /**
             * 插入任务,回调门户拆出数量对应的id  bug 18517
             */
            insertTask(obj.getId(),sampleBalApplyDO.getSampleBalApplyNo());

            return true;
        } else {
            throw new RuntimeException("结转数量不可超出原结转数量,申请结转数量为"+quantity+"原结转数量为"+applyBalQty);
        }
    }

    public SamplebalDO getSampleBalDOById(Long sourceId) {
        LambdaQueryWrapper<SamplebalDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(SamplebalDO::getId,sourceId)
                .ne(SamplebalDO::getOptCode,SampleBalOptCodeEnum.CANCEL.getCode());
        return samplebalMapper.selectOne(queryWrapper);
    }

    // 人工样品结转记录日志操作
    public void insertOrderLog(SamplebalDO samplebalDO,String desc) {
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserName("未知");
        }
        OrderLogVO log = new OrderLogVO();
        log.setOptUserName(loginAuthDto.getUserName());
        log.setOrderNo(samplebalDO.getRorderNo());
        log.setDescription(desc);
        log.setCreateTime(new Date());
        log.setOptTime(new Date());
        log.setOptType(9);
        orderLogFeignApi.addOrderLog(log);
    }

}
