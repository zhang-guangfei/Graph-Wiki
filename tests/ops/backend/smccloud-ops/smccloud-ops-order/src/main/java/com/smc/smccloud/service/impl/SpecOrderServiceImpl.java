package com.smc.smccloud.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.rabbitmq.RabbitMqMessage;
import com.smc.smccloud.core.rabbitmq.constants.Constants;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.ExpdetailMapper;
import com.smc.smccloud.mapper.RcvdetailMapper;
import com.smc.smccloud.mapper.RcvmasterMapper;
import com.smc.smccloud.mapper.SpecOrderMapper;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.OrderSales.OrderDlvDataDO;
import com.smc.smccloud.model.SpecOrder.SpecOrderDO;
import com.smc.smccloud.model.SpecOrder.SpecOrderExpDetailData;
import com.smc.smccloud.model.customer.CustomerVO;
import com.smc.smccloud.model.enums.SpecOrderStatusEnum;
import com.smc.smccloud.model.enums.SpecOrderTypeEnum;
import com.smc.smccloud.model.expdetail.ExpdetailDO;
import com.smc.smccloud.model.order.RcvDetailDO;
import com.smc.smccloud.model.order.RcvMasterDO;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.product.OrderModelInfoVO;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.receiveorder.ReceiveOrderVO;
import com.smc.smccloud.model.specorder.SpecOrderDTO;
import com.smc.smccloud.model.specorder.SpecOrderDetailDTO;
import com.smc.smccloud.model.specorder.SpecOrderExportVO;
import com.smc.smccloud.model.specorder.SpecOrderVO;
import com.smc.smccloud.rabbitmq.SendMessage;
import com.smc.smccloud.service.*;
import com.smc.smccloud.util.PriceCompute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;


@Service
@Slf4j
public class SpecOrderServiceImpl implements SpecOrderService {

    @Resource
    private SpecOrderMapper specOrderMapper;

    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;

    @Resource
    private RcvdetailMapper rcvdetailMapper;

    @Resource
    private RcvmasterMapper rcvmasterMapper;

    @Resource
    private SendMessage sendMessage;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;

    @Resource
    private OrderDlvDataService orderDlvDataService;

    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private CommonService commonService;

    @Resource
    private ExpdetailMapper expdetailMapper;
    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private OpsCommonService opsCommonService;


    @Override
    public ResultVo<SpecOrderDTO> saveSpecOrder(SpecOrderDTO dto) {
        if (PublicUtil.isEmpty(dto.getCustomerNo()) || PublicUtil.isEmpty(dto.getTradeCompanyId())) {
            return ResultVo.failure("无效输入参数");
        }
        if (PublicUtil.isEmpty(dto.getList())) {
            return ResultVo.failure("请填写订单的详细信息");
        }
        if (dto.getOrderType() == 12 || ((dto.getOrderType() == 24 || dto.getOrderType() == 25) && "1".equals(dto.getDlvEntire()))) {
            if (PublicUtil.isEmpty(dto.getProvince()) || PublicUtil.isEmpty(dto.getCity()) || PublicUtil.isEmpty(dto.getDistrict())) {
                return ResultVo.failure("该客户省市区代码不全，保存失败");
            }
        }
        if (StringUtils.isNotBlank(dto.getCustomerNo())) {
            dto.setCustomerNo(dto.getCustomerNo().trim());
        }
        if (StringUtils.isNotBlank(dto.getUserNo())) {
            dto.setUserNo(dto.getUserNo().trim());
        }
        ResultVo<String> order = addSpecOrder(String.valueOf(dto.getGroupNo()));
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        dto.setGroupNo(order.getData());

        SpecOrderDO orderDO = BeanCopyUtil.copy(dto, SpecOrderDO.class);

        if (dto.getOrderType() == 12 || dto.getOrderType() == 11) {
            orderDO.setDeptNo("200000");
        } else {
            orderDO.setDeptNo(dto.getDeptNo());
        }
        if (PublicUtil.isEmpty(dto.getDeptNo())) {
            CustomerVO customer = commonService.getCustomerInfoByNo(dto.getCustomerNo());
            if (customer != null) {
                orderDO.setDeptNo(customer.getHRUnitId());
            }
        }

//        int insert = specOrderMapper.insert(orderDO);
        if (PublicUtil.isEmpty(dto.getList())) {
            return ResultVo.failure("0", "订单详细不能为空");
        }
        try {
//            List<SpecOrderDO> doList = BeanCopyUtil.copyList(adapter.getList(), SpecOrderDO.class);
            for (SpecOrderDetailDTO detailDTO : dto.getList()) {
                orderDO.setOrderItem(detailDTO.getOrderItem());
                orderDO.setModelNo(detailDTO.getModelNo());
                orderDO.setQuantity(detailDTO.getQuantity());
                orderDO.setPrice(detailDTO.getPrice());
                orderDO.setProductName(detailDTO.getProductName());
                orderDO.setOrgCurrency(detailDTO.getOrgCurrency());
                orderDO.setOrgPrice(detailDTO.getOrgPrice());
                orderDO.setEprice(detailDTO.getEprice());
                orderDO.setDlvDate(detailDTO.getDlvDate());
                orderDO.setTransType(detailDTO.getTransType());
                orderDO.setManuDlvDate(detailDTO.getManuDlvDate());
                orderDO.setCproductNo(detailDTO.getCproductNo());
                orderDO.setComplaintNo(detailDTO.getComplaintNo());
                if (PublicUtil.isEmpty(detailDTO.getRemark())) {
                    detailDTO.setRemark("");
                }
                orderDO.setRemark(dto.getRemark() + " " + detailDTO.getRemark());
                orderDO.setStatus(1);
                orderDO.setSpecMark(detailDTO.getSpecMark());
                if (PublicUtil.isEmpty(detailDTO.getId())) {
                    orderDO.setCreateUser(userDTO.getUserNo());
                    specOrderMapper.insert(orderDO);
                    detailDTO.setId(orderDO.getId());
                } else {
                    SpecOrderDO specOrderDO = specOrderMapper.selectById(detailDTO.getId());
                    if (PublicUtil.isEmpty(specOrderDO)) {
                        orderDO.setCreateUser(userDTO.getUserNo());
                        specOrderMapper.insert(orderDO);
                        detailDTO.setId(orderDO.getId());
                    } else if (specOrderDO.getStatus() == 2) {
                        orderDO.setCreateUser(userDTO.getUserNo());
                        specOrderMapper.insert(orderDO);
                        detailDTO.setId(orderDO.getId());
                    } else if (specOrderDO.getStatus() == 1) {
                        orderDO.setUpdateUser(userDTO.getUserNo());
                        orderDO.setId(detailDTO.getId());
                        specOrderMapper.updateById(orderDO);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVo.success(dto, "保存成功");
    }


    @Override
    public PageInfo<SpecOrderVO> listSpecOrder(SpecOrderVO specOrderVO, Page page) {

        LambdaQueryWrapper<SpecOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(specOrderVO.getGroupNo()), SpecOrderDO::getGroupNo, specOrderVO.getGroupNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getCustomerNo()), SpecOrderDO::getCustomerNo, specOrderVO.getCustomerNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getUserNo()), SpecOrderDO::getUserNo, specOrderVO.getUserNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getStatus()), SpecOrderDO::getStatus, specOrderVO.getStatus())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getOrderType()), SpecOrderDO::getOrderType, specOrderVO.getOrderType())
                .eq(StringUtils.isNotBlank(specOrderVO.getCreateUser()),SpecOrderDO::getCreateUser,specOrderVO.getCreateUser())
                .between(PublicUtil.isNotEmpty(specOrderVO.getShipTimeStart()), SpecOrderDO::getCreateTime, specOrderVO.getShipTimeStart(), specOrderVO.getShipTimeEnd());

        if (PublicUtil.isNotEmpty(specOrderVO.getOrderNo()) && specOrderVO.getOrderNo().contains("-")) {
            String[] split = specOrderVO.getOrderNo().split("-");
            queryWrapper.eq(SpecOrderDO::getOrderNo, split[0]);
            queryWrapper.eq(SpecOrderDO::getOrderItem, split[1]);
        } else {
            queryWrapper.eq(PublicUtil.isNotEmpty(specOrderVO.getOrderNo()), SpecOrderDO::getOrderNo, specOrderVO.getOrderNo());
        }
        queryWrapper.orderByDesc(SpecOrderDO::getCreateTime);
        PageInfo<SpecOrderVO> pageInfo = PageHelper.startPage(page.getPageNumber(), page.getPageSize())
                .doSelectPageInfo(() -> specOrderMapper.selectList(queryWrapper));

        if (PublicUtil.isEmpty(pageInfo.getList())) {
            return null;
        }
        return pageInfo;
    }

    @Override
    public void exportListSpecOrder(SpecOrderVO specOrderVO, HttpServletResponse response) {
        LambdaQueryWrapper<SpecOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(specOrderVO.getGroupNo()), SpecOrderDO::getGroupNo, specOrderVO.getGroupNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getCustomerNo()), SpecOrderDO::getCustomerNo, specOrderVO.getCustomerNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getUserNo()), SpecOrderDO::getUserNo, specOrderVO.getUserNo())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getStatus()), SpecOrderDO::getStatus, specOrderVO.getStatus())
                .eq(PublicUtil.isNotEmpty(specOrderVO.getOrderType()), SpecOrderDO::getOrderType, specOrderVO.getOrderType())
                .eq(StringUtils.isNotBlank(specOrderVO.getCreateUser()),SpecOrderDO::getCreateUser,specOrderVO.getCreateUser())
                .between(PublicUtil.isNotEmpty(specOrderVO.getShipTimeStart()), SpecOrderDO::getCreateTime, specOrderVO.getShipTimeStart(), specOrderVO.getShipTimeEnd());

        if (PublicUtil.isNotEmpty(specOrderVO.getOrderNo()) && specOrderVO.getOrderNo().contains("-")) {
            String[] split = specOrderVO.getOrderNo().split("-");
            queryWrapper.eq(SpecOrderDO::getOrderNo, split[0]);
            queryWrapper.eq(SpecOrderDO::getOrderItem, split[1]);
        } else {
            queryWrapper.eq(PublicUtil.isNotEmpty(specOrderVO.getOrderNo()), SpecOrderDO::getOrderNo, specOrderVO.getOrderNo());
        }
        queryWrapper.orderByDesc(SpecOrderDO::getCreateTime);
        List<SpecOrderDO> specOrderDOS = specOrderMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(specOrderDOS)) {
            return;
        }

        List<SpecOrderExportVO> specOrderExportVOS = BeanCopyUtil.copyList(specOrderDOS, SpecOrderExportVO.class);
        for (SpecOrderExportVO item : specOrderExportVOS) {
            if(item.getStatus() != null) {
                item.setStatusName(SpecOrderStatusEnum.getCodeName(item.getStatus()));
            }
            if(item.getOrderType() != null) {
                item.setOrderTypeName(SpecOrderTypeEnum.getCodeName(item.getOrderType()));
            }
        }

        try {
            String fileName = URLEncoder.encode("特殊订单数据导出", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.other_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), SpecOrderExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(specOrderExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }


    @Override
    public ResultVo<String> addSpecOrder(String groupNo) {

        if (groupNo == null) {
            return ResultVo.success(commonServiceFeignApi.generatorBillNo("31").getData());
        }
        //判断是否存在该批次号
        QueryWrapper<SpecOrderDO> query = new QueryWrapper<>();
        query.eq("group_no", groupNo);
        query.eq("status", 1);
        if (PublicUtil.isNotEmpty(specOrderMapper.selectList(query))) {
            return ResultVo.success(groupNo);
        } else {
            return ResultVo.success(commonServiceFeignApi.generatorBillNo("31").getData());
        }
    }


    @Override
    public ResultVo<List<SpecOrderDO>> findSpecOrder(String groupNo) {
        if (PublicUtil.isEmpty(groupNo)) {
            return null;
        }
        QueryWrapper<SpecOrderDO> query = new QueryWrapper<>();
        query.eq("group_no", groupNo);
        query.eq("status", 1);
        List<SpecOrderDO> list = specOrderMapper.selectList(query);

        if (PublicUtil.isEmpty(list)) {
            return ResultVo.failure("未查询到正在编辑的订单,请重试");
        }

        return ResultVo.success(list);
    }

    @Override
    public ResultVo<String> deleteSpecOrder(Integer id) {
        if (id == null) {
            return null;
        }
        SpecOrderDO specOrderDO = specOrderMapper.selectById(id);
        if (specOrderDO.getStatus() == 2) {
            return ResultVo.failure("已生成的订单不允许删除！");
        }
        int i = specOrderMapper.deleteById(id);
        return i == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败");
    }

    @Override
    public ResultVo<String> exportSpecOrderExpDetailToExcel() {
        //客户代码,客户名称,出库单号,发货日期,订单号,型号,数量,货物条码,箱号,重量,出口单价,币种,交易方式,希望出口日期,品名, 收货人（海外公司名称、地址、电话）,发货物流中心
        //根据expdetail 和 特殊订单表,导出上面这些信息到excel
        List<SpecOrderExpDetailData> detail = specOrderMapper.findSpecOrderExpDetail();

        String path = "templates/SpecOrderExpDetail.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(inputStream);
        excel.openSheet(0);

        try {
            // 向模板中写入数据
            int row = 1;
            int cel;
            for (SpecOrderExpDetailData data : detail) {
                cel = 0;
                ResultVo<ProductInfoVO> resultVo = productServiceFeignApi.getProductInfoByModelNo(data.getModelNo());
                excel.setCellValue(row, cel++, resultVo.getData().getChineseName());
                excel.setCellValue(row, cel++, resultVo.getData().getEnglishName());
                excel.setCellValue(row, cel++, data.getModelNo());
                excel.setCellValue(row, cel++, data.getQuantity());
                excel.setCellValue(row, cel++, "个");
                excel.setCellValue(row, cel++, data.getOrgCurrency());
                excel.setCellValue(row, cel++, data.getPrice());
                excel.setCellValue(row, cel++, data.getPrice().multiply(new BigDecimal(String.valueOf(data.getQuantity()))));//总价
                excel.setCellValue(row, cel++, data.getShipDate()); //原进口日期
                excel.setCellValue(row, cel++, data.getInvoiceNo()); // 发票号
                excel.setCellValue(row, cel++, data.getWeight()); //重量
                excel.setCellValue(row, cel++, data.getCaseNo());
                if (PublicUtil.isEmpty(data.getVolume())) {
                    cel += 3;
                } else {
                    String[] split = data.getVolume().split("\\*");
                    excel.setCellValue(row, cel++, split[0]);  //长
                    excel.setCellValue(row, cel++, split[1]); //宽
                    excel.setCellValue(row, cel++, split[2]); //高
                }
                excel.setCellValue(row, cel++, data.getBoxType()); //材料
                excel.setCellValue(row, cel++, data.getRemark()); //用途
                excel.setCellValue(row, cel++, data.getFullOrderNo()); //订单号
                excel.setCellValue(row, cel, ""); //集装箱分箱
                row++;

                ExpdetailDO expdetailDO = new ExpdetailDO();
                expdetailDO.setId(data.getId());
                expdetailDO.setOptCode(2);
                expdetailMapper.updateById(expdetailDO);
            }
            ResultVo<DataTypeVO> codesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9004", "SPC");
            String to = codesInfo.getData().getExtNote1();
            String tocc = codesInfo.getData().getExtNote2();
            String mc = codesInfo.getData().getExtNote3();
            InputStream fileInputStream = excel.convertTo();
            String fileName = "特殊订单发货信息.xlsx";
            String subject = "特殊订单发货信息";
            StringBuilder sb = new StringBuilder();
            sb.append("<span>您好：</span></br>");
            sb.append("<span>这是特殊订单发货信息</span></br>");
            sb.append("<span>详情请见附件: [").append(fileName).append("]</span></br>");
            sb.append("SMC（广州）自动化有限公司</br>").append("本邮件由系统自动发送，请勿直接回复本邮件");
            Map<String, InputStream> attachment = new LinkedHashMap<>(1);
            attachment.put(fileName, fileInputStream);
            EmailUtil.send(mailSender, to, tocc, mc,subject, sb.toString(), attachment);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVo.success("发送成功");
    }


    @Override
    @Transactional
    public ResultVo<String> generateSpecOrder(List<SpecOrderVO> specOrderVOS) {

        if (PublicUtil.isEmpty(specOrderVOS)) {
            return ResultVo.failure("请选择订单生成");
        }

        QueryWrapper<SpecOrderDO> query;
        List<SpecOrderVO> voList;
        List<RequestPurchaseInfoDto> list;
        RequestPurchaseInfoDto info;
        CommonResult<String> addResult;
        SpecOrderDO specOrderDO;

        for (SpecOrderVO OrderVO : specOrderVOS) {
            query = new QueryWrapper<>();
            query.eq("group_no", OrderVO.getGroupNo());
            query.eq("status", "1");
            //查询未生成的订单
            List<SpecOrderDO> doList = specOrderMapper.selectList(query);
            if (PublicUtil.isEmpty(doList)) {
                continue;
            }

            voList = BeanCopyUtil.copyList(doList, SpecOrderVO.class);
            list = new ArrayList<>(voList.size());

            int itemNo = 1;
//            String code = null;
            String orderNo = null;
            String poOrderNo = null;

            for (SpecOrderVO specOrderVO : voList) {
                String code = null;
                //防止项号为空
                if (PublicUtil.isEmpty(specOrderVO.getOrderItem())) {
                    specOrderVO.setOrderItem(itemNo++);
                }

                switch (specOrderVO.getOrderType()) {
                    case 11:
                        code = "6";
                        break;
                    case 12:
                        code = "7";
                        break;
                    case 24:
                        code = "4";
                        break;
                    case 25:
                        code = "5";
                        break;
                }

                // 如果为DR/CR订单且为到货入库则生成采购订单
                if ((specOrderVO.getOrderType() == 24 || specOrderVO.getOrderType() == 25) && "2".equals(specOrderVO.getDlvEntire())) {
                    LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
                    // 生成采购订单
                    // 20240105 bug 12906  by A78027
                    if(poOrderNo==null) {
                        poOrderNo = commonServiceFeignApi.generatorBillNo(code).getData();//生成订单号
                        if (PublicUtil.isEmpty(poOrderNo)) {
                            return ResultVo.failure("生成申请单号异常");
                        }
                    }

                    info = new RequestPurchaseInfoDto();
                    info.setOrderno(poOrderNo);
                    info.setItemno(specOrderVO.getOrderItem());
                    info.setModelno(specOrderVO.getModelNo());
                    info.setQuantity(specOrderVO.getQuantity());
                    info.setStatecode("1");
                    info.setDeptno(specOrderVO.getDeptNo());
                    info.setOrdtype(String.valueOf(specOrderVO.getOrderType()));
                    info.setSpecmark("0");
                    info.setRequestwarehouseid(specOrderVO.getExportWarehouse());
                    info.setPurchasewarehouseid(specOrderVO.getExportWarehouse());
                    info.setPurchasetype("A");
                    info.setSupplierid("JP");
                    info.setInventorypropertyid(1L);
                    info.setInventorytypecode("TY");
                    info.setStdprice(specOrderVO.getPrice());
                    info.setHopedeliverydate(specOrderVO.getDlvDate());
                    info.setHopeexportdate(specOrderVO.getManuDlvDate());
                    info.setOrderdate(DateUtil.getNow());
                    info.setApplyDeptNo(userDTO.getDeptNo());
                    info.setRequesttime(DateUtil.getNow());
                    info.setCorderno(specOrderVO.getGroupNo());
                    info.setCustomerno(specOrderVO.getCustomerNo());
                    info.setUserno(specOrderVO.getUserNo());
                    if (PublicUtil.isEmpty(specOrderVO.getComplaintNo())) {
                        specOrderVO.setComplaintNo("");
                    }
                    info.setRemark(specOrderVO.getComplaintNo());
                    info.setIslot(false);
                    info.setTranstype(specOrderVO.getTransType());
                    info.setSpecmark(specOrderVO.getSpecMark());
                    // bug19576 写入采购表时，增加endUser字段，字段取值：先判定userno，不存在时取customerno
                    info.setEndUser(specOrderVO.getCustomerNo());
                    if (StringUtils.isNotBlank(specOrderVO.getUserNo())) {
                        info.setEndUser(specOrderVO.getUserNo());
                    }
                    list.add(info);
                    addResult = requestPurchaseFeignApi.addRequest(list);

                    // 推送数据到货期状态
                    sendMsgToOrderState(info);

                    if (addResult.getCode() == 500) {
                        log.error("生成采购订单失败: {}", addResult.getMessage());
                        throw new RuntimeException("生成采购订单失败");
                    }

                    try {
                        // 修改订单状态为已生成订单
                        specOrderDO = BeanCopyUtil.copy(specOrderVO, SpecOrderDO.class);
                        specOrderDO.setStatus(2);
                        specOrderDO.setOrderNo(poOrderNo);
                        specOrderDO.setId(specOrderDO.getId());
                        specOrderDO.setFullOrderNo(poOrderNo+ "-" + specOrderVO.getOrderItem());
                        specOrderDO.setUpdateUser(userDTO.getUserNo());
                        specOrderDO.setOrderDate(DateUtil.getNow());
                        specOrderMapper.updateById(specOrderDO);
                    } catch (Exception e) {
                        log.error("修改订单状态时出错--", specOrderVO, e.getMessage(), e);
                        throw new RuntimeException("修改订单信息时出错");
                    }

                } else {
                    // 生成特殊订单
                    if (orderNo == null) {
                        orderNo = dictDataServiceFeignApi.getRandomOrderNoGenerator("9001", code).getData();
                    }
                    specOrderVO.setOrderNo(orderNo);
                    ResultVo<String> vo = createSpecOrder(specOrderVO);
                    if (!vo.isSuccess()) {
                        return ResultVo.failure(vo.getMessage());
                    }
                }

            }

        }

        return ResultVo.success("生成订单成功");
    }

    @Override
    @Transactional
    public ResultVo<String> createSpecOrder(SpecOrderVO specOrderVO) {
        String rorderNo;
        BigDecimal taxRate;

        // 查税率
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictDataServiceFeignApi.getDataTypeCodesInfo("9002", "1");
        if (PublicUtil.isNotEmpty(dataTypeCodesInfo.getData())) {
            taxRate = new BigDecimal(dataTypeCodesInfo.getData().getExtNote1());
        } else {
            taxRate = new BigDecimal("0.13");
        }

        ReceiveOrderVO receiveOrderVO = new ReceiveOrderVO();
        try {
            receiveOrderVO = BeanCopyUtil.copy(specOrderVO, ReceiveOrderVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receiveOrderVO.setStatus(1); // 待处理


//        ResultVo<String> randomOrderNoGenerator = dictDataServiceFeignApi.getRandomOrderNoGenerator(classCode, code);// 生成订单号
//
//        String rorderNo = randomOrderNoGenerator.getData(); // 生成订单号
        if (PublicUtil.isEmpty(specOrderVO.getOrderNo())) {
            return ResultVo.failure("订单号生成异常");
        } else {
            receiveOrderVO.setRorderNo(specOrderVO.getOrderNo());
            rorderNo = specOrderVO.getOrderNo();
        }

        // receiveOrderVO.setTypeCode(specOrderVO.getOrderType()); // 订单
        receiveOrderVO.setOrderType(specOrderVO.getOrderType()); // 订单
        receiveOrderVO.setRorderItem(specOrderVO.getOrderItem());
        receiveOrderVO.setRorderFno(rorderNo + "-" + specOrderVO.getOrderItem());

        if (PublicUtil.isEmpty(specOrderVO.getProductName())) {
            ResultVo<OrderModelInfoVO> info = productServiceFeignApi.getModelInfoForOrder(specOrderVO.getModelNo());
            if (PublicUtil.isNotEmpty(info.getData())) {
                specOrderVO.setEprice(info.getData().getEprice());
                specOrderVO.setProductName(info.getData().getProductName());
            }
        }
        receiveOrderVO.setEPrice(specOrderVO.getEprice());//E价
        receiveOrderVO.setProductName(specOrderVO.getProductName());
        receiveOrderVO.setTaxRate(taxRate); // 税率

        if (PublicUtil.isNotEmpty(specOrderVO.getPrice())) {
            if (receiveOrderVO.getAmount() == null || receiveOrderVO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                receiveOrderVO.setAmount(receiveOrderVO.getPrice().multiply(new BigDecimal(receiveOrderVO.getQuantity())));
            }
            if (receiveOrderVO.getTaxRate() == null || receiveOrderVO.getTaxRate().compareTo(BigDecimal.ZERO) == 0) {
                receiveOrderVO.setTaxRate(new BigDecimal("0.13"));
            }
            //不含税单价
            BigDecimal ntaxPrice = PriceCompute.unitPriceExcludingTax(specOrderVO.getPrice(), taxRate);
            receiveOrderVO.setNtaxPice(ntaxPrice);
            // 不含税金额
            BigDecimal ntaxAMount = PriceCompute.ntaxAmount(receiveOrderVO.getAmount(), receiveOrderVO.getTaxRate());
            receiveOrderVO.setNtaxAmount(ntaxAMount);
            // 税额
            BigDecimal taxAmount = PriceCompute.taxAmount(receiveOrderVO.getAmount(),receiveOrderVO.getNtaxAmount());
            receiveOrderVO.setTaxAmount(taxAmount);
            // 含税金额
            receiveOrderVO.setAmount(ntaxAMount.add(taxAmount));
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        try {
            QueryWrapper<RcvMasterDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rorder_no", specOrderVO.getOrderNo());
            RcvMasterDO masterDO = rcvmasterMapper.selectOne(queryWrapper);

            if (masterDO == null) {
                //添加进 rcvMaster
                RcvMasterDO rcvMasterDO = null;
                try {
                    rcvMasterDO = BeanCopyUtil.copy(receiveOrderVO, RcvMasterDO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("receiveOrderVO转RcvMasterDO出错");
                }
                rcvMasterDO.setRordDate(new Date());
                //    <!--add by WuWeiDong 20230830  bug 11916  一般贸易订单，更新enduser -->
                rcvMasterDO.setEndUser(rcvMasterDO.getCustomerNo());
                if (StringUtils.isNotEmpty(rcvMasterDO.getUserNo())) {
                    rcvMasterDO.setEndUser(rcvMasterDO.getUserNo());
                } else {
                    rcvMasterDO.setUserNo("");
                }

                if (StringUtils.isNotEmpty(receiveOrderVO.getDeptNo())) {
//                    if (specOrderVO.getOrderType() == 11) {
//                        ResultVo<String> resultVo = commonServiceFeignApi.getDlvDeptNoByNo(specOrderVO.getCustomerNo());
//                        if (resultVo.isSuccess()) {
//                            receiveOrderVO.setDeptNo(resultVo.getData());
//                        } else {
//                            return ResultVo.failure("客户代码不匹配，请修改为正确的客户");
//                        }
//                    }
                    ResultVo<String> deptNo = commonServiceFeignApi.getDeptNoByHRSalesDeptNo(receiveOrderVO.getDeptNo());
                    if (deptNo.getData().equals(receiveOrderVO.getDeptNo())) {
                        rcvMasterDO.setDeptNo(deptNo.getData());
                    } else {
                        rcvMasterDO.setDeptNo(deptNo.getData());
                        rcvMasterDO.setHlCode(receiveOrderVO.getDeptNo());
                    }
                } else {
                    rcvMasterDO.setDeptNo("");
                }
                rcvMasterDO.setRordDate(new Date());
                rcvMasterDO.setDlvEntire(receiveOrderVO.getDlvType());
                rcvMasterDO.setOptTime(new Date());
                if (StringUtils.isNotEmpty(specOrderVO.getExportWarehouse())) {
                    switch (specOrderVO.getExportWarehouse()) {
                        case "KGZ":
                            rcvMasterDO.setDeliveryDeptNo("261110");
                            break;
                        case "KBJ":
                            rcvMasterDO.setDeliveryDeptNo("223110");
                            break;
                        case "KSH":
                            rcvMasterDO.setDeliveryDeptNo("243310");
                            break;
                        case "SCZ":
                            rcvMasterDO.setDeliveryDeptNo("243660");
                            break;
                    }
                }

                rcvmasterMapper.insert(rcvMasterDO);
            }

            // 添加进 rcvDetail
            RcvDetailDO rcvDetailDO = null;
            try {
                rcvDetailDO = BeanCopyUtil.copy(receiveOrderVO, RcvDetailDO.class);
                rcvDetailDO.setCorderNo(specOrderVO.getCorderNo());
                rcvDetailDO.setRemark(specOrderVO.getRemark());
                rcvDetailDO.setWmsDlvDate(specOrderVO.getDlvDate());
                if (PublicUtil.isEmpty(rcvDetailDO.getWmsDlvDate())) {
                    rcvDetailDO.setWmsDlvDate(new Date());
                }

                if (specOrderVO.getOrderType() == 24) {
                    rcvDetailDO.setRemark(specOrderVO.getComplaintNo());
                }
                if (specOrderVO.getOrderType() == 25) {
                    rcvDetailDO.setRemark(specOrderVO.getComplaintNo());
                }
                // 客户类型默认直销
                // rcvDetailDO.setCustomerType(CstmTypeEnum.ZX.getCode());
                com.smc.smccloud.model.CustomerVO info = opsCommonService.getCustomerByCustomerNo(specOrderVO.getCustomerNo());
                if (info != null) {
                    rcvDetailDO.setCustomerType(info.getCustomerType());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("对象receiveOrderVO转RcvDetailDO异常");
            }
            rcvdetailMapper.insert(rcvDetailDO);

            if (specOrderVO.getOrderType() == 12 || "1".equals(specOrderVO.getDlvEntire())) {
                //添加收货信息至OrderDlvData表
                OrderDlvDataDO dlvDataDO = new OrderDlvDataDO();
                dlvDataDO.setOrderNo(rorderNo);
                dlvDataDO.setItemNo(specOrderVO.getOrderItem());
                dlvDataDO.setCustomerNo(specOrderVO.getCustomerNo());
                dlvDataDO.setCstmName(specOrderVO.getReceiverCompany());
                dlvDataDO.setDlvAddress(specOrderVO.getReceiverAddress());
                dlvDataDO.setContactPsn(specOrderVO.getReceiverName());
                dlvDataDO.setTelNo(specOrderVO.getReceiverPhone());
                dlvDataDO.setProvince(specOrderVO.getProvince());
                dlvDataDO.setCity(specOrderVO.getCity());
                dlvDataDO.setDistrict(specOrderVO.getDistrict());
                dlvDataDO.setCreateTime(new Date());
                orderDlvDataService.saveOrderDlvData(dlvDataDO);
            }


            // 记录到日志表
//            OrderLogDO orderLogDO = new OrderLogDO();
//            orderLogDO.setOrderNo(receiveOrderVO.getRorderFno());
//            orderLogDO.setOptType(12);
//            orderLogDO.setDescription("生成特殊订单至rcvdetail");
//            orderLogDO.setOptTime(new Date());
//            try {
//                orderLogDO.setIp(IpUtil.getIpAddress());
//            } catch (Exception e) {
//                orderLogDO.setIp("");
//            }
//            orderLogDO.setOptUserId(userDTO.getUserNo());
//            orderLogDO.setOptUserName(userDTO.getUserName());
//            orderLogMapper.insert(orderLogDO);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加失败，出现异常");
            throw new RuntimeException(e);
        }

        //发送状态信息至mq
        OrderStateVO o = BeanCopyUtil.copy(specOrderVO, OrderStateVO.class);

        try {
            o = BeanCopyUtil.copy(specOrderVO, OrderStateVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        o.setOrderNo(rorderNo + "-" + specOrderVO.getOrderItem());
        o.setRorderNo(rorderNo);
        o.setItemNo(specOrderVO.getOrderItem());
        o.setSplitNo(0);
        o.setOrderType(specOrderVO.getOrderType()); // 订单
        o.setStateCode(12); // 接入订单
        o.setStateDes("特殊订单接入");
        o.setOptUserName(userDTO.getRealName() == null ? "" : userDTO.getRealName());
        o.setOptUserNo(userDTO.getUserNo() == null ? "" : userDTO.getUserNo());
        o.setStateType(1); // // 待处理
        o.setStateDate(new Date());
        o.setFirstDate(new Date());
        o.setMinDate(new Date());
        // o.setMaxDate(new Date());
        o.setProvider("OPS");
        o.setRemark(specOrderVO.getRemark());
//        if (specOrderVO.getOrderType() == 24) {
//            o.setRemark(specOrderVO.getComplaintNo());
//        }
//        if (specOrderVO.getOrderType() == 25) {
//            o.setRemark(specOrderVO.getComplaintNo());
//        }
        o.setEndUser(PublicUtil.isEmpty(specOrderVO.getUserNo()) ? specOrderVO.getCustomerNo() : specOrderVO.getUserNo());
        o.setDeptNo(specOrderVO.getDeptNo());
        o.setIsAddLog(true);

        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(o));
        rabbitMqMessage.setFlag(Constants.OPS_ORDER_STATE);
        rabbitMqMessage.setDataType(Constants.OPS_ORDER);
        rabbitMqMessage.setSystem(Constants.OPS);
        Boolean msg = sendMessage.sendOrderStateMsg(rabbitMqMessage);
        if (!msg) {
            log.error("推送货期状态至MQ失败");
            throw new RuntimeException("推送货期状态至MQ失败");
        }

        //修改样品状态为已生成订单
        try {
            SpecOrderDO specOrderDO = BeanCopyUtil.copy(specOrderVO, SpecOrderDO.class);
            specOrderDO.setStatus(2);
            specOrderDO.setOrderNo(rorderNo);
            specOrderDO.setFullOrderNo(rorderNo + "-" + specOrderVO.getOrderItem());
            specOrderDO.setId(specOrderVO.getId());
//            specOrderDO.setRemark(o.getRemark());
            specOrderDO.setOrderDate(DateUtil.getNow());
            specOrderMapper.updateById(specOrderDO);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改订单状态时出现异常" + specOrderVO, e.getMessage(), e);
            throw new RuntimeException(e);
        }
//            }
//        }
        return ResultVo.success("生成订单成功");
    }


    public void sendMsgToOrderState(RequestPurchaseInfoDto info) {

        if (info == null) {
            return;
        }

        OrderStateVO orderStateVO = new OrderStateVO();
        orderStateVO.setOrderNo(info.getOrderno()+"-"+info.getItemno());
        orderStateVO.setRorderNo(info.getOrderno());
        orderStateVO.setItemNo(info.getItemno());
        orderStateVO.setSplitNo(0);
        orderStateVO.setQuantity(info.getQuantity());
        orderStateVO.setStateCode(20);
        orderStateVO.setOrderType(Integer.parseInt(info.getOrdtype()));
        orderStateVO.setDeptNo(info.getDeptno());
        orderStateVO.setPurchaseType(info.getPurchasetype());
        orderStateVO.setSupplierCode(info.getSupplierid());
        orderStateVO.setOrderDate(info.getOrderdate());
        orderStateVO.setCorderNo(info.getCorderno());
        orderStateVO.setCustomerNo(info.getCustomerno());
        orderStateVO.setUserNo(info.getUserno());
        orderStateVO.setStateDes(DateUtil.dateToDateString(new Date())+"发出采购单");
        orderStateVO.setEndUser(StringUtils.isBlank(info.getUserno()) ? info.getCustomerno() : info.getUserno());
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage();
        rabbitMqMessage.setContent(JSON.toJSONString(orderStateVO));
        rabbitMqMessage.setFlag(Constants.OPS_ORDER_STATE);
        rabbitMqMessage.setDataType(Constants.OPS_ORDER);
        rabbitMqMessage.setSystem(Constants.OPS);
        sendMessage.sendOrderStateMsg(rabbitMqMessage);
    }

}
