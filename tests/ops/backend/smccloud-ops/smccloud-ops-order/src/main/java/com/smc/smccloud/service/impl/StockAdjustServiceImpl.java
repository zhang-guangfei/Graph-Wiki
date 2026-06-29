package com.smc.smccloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.until.SplitBatchUtils;
import com.sales.ops.dto.inventory.AdjustItemDTO;
import com.sales.ops.dto.inventory.AdjustParam;
import com.sales.ops.dto.inventory.AdjustType;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.order.InventoryForAdjustInputDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.enums.InventoryTypeEnum;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.OpsImpDataMapper;
import com.smc.smccloud.mapper.OpsPoInvoiceDetailMapper;
import com.smc.smccloud.mapper.OpsPurchaseOrderMapper;
import com.smc.smccloud.mapper.StockAdjustMapper;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceDetailMapper;
import com.smc.smccloud.mapper.impInvoice.PoInvoiceMasterMapper;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.Purchase.OpsPurchaseOrderDO;
import com.smc.smccloud.model.adjust.*;
import com.smc.smccloud.model.invoice.OpsPoInvoiceDO;
import com.smc.smccloud.model.invoice.PoInvoiceDetailDO;
import com.smc.smccloud.model.order.OpsImpDataDO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.OpsCommonService;
import com.smc.smccloud.service.StockAdjustService;
import com.smc.smccloud.service.StockAssemblyFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockAdjustServiceImpl implements StockAdjustService {

    @Resource
    private StockAdjustMapper stockAdjustMapper;
    @Resource
    private OpsPurchaseOrderMapper opsPurchaseOrderMapper;
    @Resource
    private PoInvoiceMasterMapper poInvoiceMasterMapper;
    @Resource
    private PoInvoiceDetailMapper poInvoiceDetailMapper;
    @Resource
    private OpsImpDataMapper opsImpDataMapper;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;
    @Resource
    private OpsPoInvoiceDetailMapper opsPoInvoiceDetailMapper;
    @Resource
    private StockAssemblyFeignApi stockAssemblyFeignApi;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private OpsCommonService opsCommonService;


    @Override
    public ResultVo<PageInfo<StockAdjustVO>> listAdjustData(StockAdjustRequest request) {
        LambdaQueryWrapper<StockAdjustDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getFullOrderNo()), StockAdjustDO::getFullOrderNo, request.getFullOrderNo())
                .eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), StockAdjustDO::getInvoiceNo, request.getInvoiceNo())
                .like(PublicUtil.isNotEmpty(request.getAdjustType()), StockAdjustDO::getAdjustType, request.getAdjustType())
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), StockAdjustDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getOptCode()), StockAdjustDO::getOptCode, request.getOptCode());
        if (PublicUtil.isNotEmpty(request.getStartTime())) {
            queryWrapper.ge(StockAdjustDO::getAdjustDate, request.getStartTime());
            queryWrapper.le(StockAdjustDO::getAdjustDate, request.getEndTime());
        }

        PageInfo<StockAdjustDO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> stockAdjustMapper.selectList(queryWrapper));
        PageInfo<StockAdjustVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, StockAdjustVO.class);

        return ResultVo.success(voPageInfo);
    }

    @Override
    @Transactional
    public ResultVo<String> addAdjustData(StockAdjustApplyDTO dto) {
        if (Objects.isNull(dto)) {
            return ResultVo.failure("请输入信息");
        }
//        //    <!--add by WuWeiDong 20230907  bug 12009   入库调整发票号+订单号长度限制 -->
//        if ((StringUtils.isNotBlank(dto.getInvoiceNo()) && dto.getInvoiceNo().length() > 10)
//                || (StringUtils.isNotBlank(dto.getOrderNo()) && dto.getOrderNo().length() > 10)) {
//            return ResultVo.failure("发票号或订单号长度不能超过10！");
//        }
        //    <!--edit by WuWeiDong 20230925  bug 12166
        // 1.发票号不能输入超过30位
        //2.fullOrderNo长度不能超过16位
        //3.解析fullOrderNo时，判断是否是旧订单，旧订单按照现有规则拆分，非旧订单以最后一个"-"拆分，order_item必须为数字  0<item<9999, orderNo最大长度11位。

        StringBuffer errorMsg = new StringBuffer();
        if ((StringUtils.isNotBlank(dto.getInvoiceNo()) && dto.getInvoiceNo().length() > 30)) {
            errorMsg.append("发票号不能输入超过30位,");
        }
        if (StringUtils.isNotBlank(dto.getOrderNo())) {
            if (dto.getOrderNo().length() > 16) {
                errorMsg.append("完整订单号不能超过16位,");
            }
            OrderNoInfo orderInfo = new OrderNoInfo().convertFullOrderNo(dto.getOrderNo());
            if (orderInfo.getItemNo().compareTo(9999) >= 0 || orderInfo.getItemNo().compareTo(0) == 0) {
                errorMsg.append("项号必须为数字  0<item<9999,");
            } else if (orderInfo.getOrderNo().length() > 11) {
                errorMsg.append("订单号最大长度11位,");
            }
        }

        if (StringUtils.isNotBlank(errorMsg.toString())) {
            return ResultVo.failure(errorMsg.substring(0, errorMsg.length() - 1) + "。");
        }

//        for (String s : dto.getAdjustType()) {
//            if (s.equals("3")) {
//                log.info("财务调整" + dto.getSupplierCode() + "," + dto.getExchangeRate());
//                if (StringUtils.isBlank(dto.getSupplierCode()) || PublicUtil.isEmpty(dto.getExchangeRate())) {
//                    return ResultVo.failure("财务调整时供应商和汇率必须填入");
//                }
//            }
//        }


        StockAdjustDO adjustDO = BeanCopyUtil.copy(dto, StockAdjustDO.class);
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        if (Objects.nonNull(dto.getAdjustType())) {
            for (String s : dto.getAdjustType()) {
                if (adjustDO.getAdjustType() == null) {
                    adjustDO.setAdjustType(Integer.valueOf(s));
                } else {
                    adjustDO.setAdjustType(Integer.valueOf(adjustDO.getAdjustType() + s));
                }
            }
        }


        //add by A78027 from bug11194 in 20230620
        if (adjustDO.getAdjustType().toString().contains("3") && StringUtils.isBlank(adjustDO.getSupplierCode())) {
            return ResultVo.failure("调财务库存必须输入供应商代码");
        }


        adjustDO.setCreateUser(userDTO.getUserNo());
        adjustDO.setCreateTime(new Date());
        OrderNoInfo orderNoInfo = new OrderNoInfo().convertFullOrderNo(dto.getOrderNo());
        adjustDO.setOrderNo(orderNoInfo.getOrderNo());
        adjustDO.setItemNo(orderNoInfo.getItemNo());
        adjustDO.setFullOrderNo(dto.getOrderNo());
        adjustDO.setSplitItemNo(orderNoInfo.getSplitItem());
        adjustDO.setOptCode(1);

        String msgCheckInventoryCode = opsCommonService.checkInventoryCode(adjustDO.getInventoryTypeCode(), adjustDO.getCustomerNo(), adjustDO.getPplNo(), adjustDO.getProjectNo(), adjustDO.getGroupCustomerNo());

        if (StringUtils.isNotBlank(msgCheckInventoryCode)) {
            return ResultVo.failure(msgCheckInventoryCode);
        }
        try {
            if (PublicUtil.isNotEmpty(dto.getId())) {
                adjustDO.setId(Long.valueOf(dto.getId()));
                adjustDO.setUpdateTime(new Date());
                adjustDO.setUpdateUser(userDTO.getUserNo());
                int update = stockAdjustMapper.updateById(adjustDO);

                return update == 1 ? ResultVo.success("修改成功！") : ResultVo.failure("修改失败");
            } else {
                int insert = stockAdjustMapper.insert(adjustDO);
                return insert == 1 ? ResultVo.success("新增成功！") : ResultVo.failure("新增失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


//    private void updatePoInvoice(StockAdjustDO adjustDO) {
//        OpsPoInvoiceDO invoiceDO = new OpsPoInvoiceDO();
//        invoiceDO.setInvoiceNo(adjustDO.getInvoiceNo());
//        invoiceDO.setAmount(adjustDO.getAmount());
//        invoiceDO.setAmountRmb(adjustDO.getAmount());
//        invoiceDO.setCustomsDate(adjustDO.getAdjustDate());
//        invoiceDO.setArrivedWarehouseCode(adjustDO.getWarehouseCode());
//        invoiceDO.setOrderQty(adjustDO.getQuantity());
//        QueryWrapper<OpsPoInvoiceDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("invoice_no",adjustDO.getInvoiceNo());
//        poInvoiceMasterMapper.update(invoiceDO,queryWrapper);
//        PoInvoiceDetailDO detail = new PoInvoiceDetailDO();
//        detail.setInvoiceNo(adjustDO.getInvoiceNo());
//        detail.setInvoiceId(invoiceDO.getId());
//        detail.setOrderNo(adjustDO.getOrderNo());
//        detail.setModelNo(adjustDO.getModelNo());
//        detail.setQuantity(adjustDO.getQuantity());
//        detail.setPrice(adjustDO.getPrice());
//        detail.setAmount(adjustDO.getAmount());
//        detail.setRemark(adjustDO.getReason());
//        QueryWrapper<PoInvoiceDetailDO> query = new QueryWrapper<>();
//        query.eq("invoice_no",detail.getInvoiceNo());
//        poInvoiceDetailMapper.update(detail,query);
//    }


    @Override
    public void exportStockAdjustData(StockAdjustRequest request) {
        LambdaQueryWrapper<StockAdjustDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getFullOrderNo()), StockAdjustDO::getFullOrderNo, request.getFullOrderNo())
                .eq(PublicUtil.isNotEmpty(request.getInvoiceNo()), StockAdjustDO::getInvoiceNo, request.getInvoiceNo())
                .like(PublicUtil.isNotEmpty(request.getAdjustType()), StockAdjustDO::getAdjustType, request.getAdjustType())
                .eq(PublicUtil.isNotEmpty(request.getModelNo()), StockAdjustDO::getModelNo, request.getModelNo())
                .eq(PublicUtil.isNotEmpty(request.getOptCode()), StockAdjustDO::getOptCode, request.getOptCode());

        List<StockAdjustDO> list = stockAdjustMapper.selectList(queryWrapper);

        String path = "templates/StockAdjust.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;

        for (StockAdjustDO data : list) {
            cel = 0;
            excel.setCellValue(row, cel++, data.getInvoiceNo());
            excel.setCellValue(row, cel++, data.getOrderNo());
            excel.setCellValue(row, cel++, data.getModelNo());
            excel.setCellValue(row, cel++, data.getQuantity());
            excel.setCellValue(row, cel++, data.getPrice());
            excel.setCellValue(row, cel++, data.getAmount());
            excel.setCellValue(row, cel++, String.valueOf(data.getAdjustType()).contains("1") ? "1" : "");
            excel.setCellValue(row, cel++, String.valueOf(data.getAdjustType()).contains("3") ? "1" : "");
            excel.setCellValue(row, cel++, String.valueOf(data.getAdjustType()).contains("2") ? "1" : "");
            excel.setCellValue(row, cel++, data.getReason());
            excel.setCellValue(row, cel++, data.getWarehouseCode());
            excel.setCellValue(row, cel++, data.getInventoryTypeCode());
            excel.setCellValue(row, cel++, data.getCustomerNo());
            excel.setCellValue(row, cel++, data.getPplNo());
            excel.setCellValue(row, cel++, data.getProjectNo());
            excel.setCellValue(row, cel++, data.getGroupCustomerNo());
            excel.setCellValue(row, cel, data.getResultMsg());
            row++;
        }

        excel.writeToResponse(response, "StockAdjust.xlsx");
    }

    @Override
    public ResultVo<String> delAdjustData(Integer id) {
        if (PublicUtil.isEmpty(id)) {
            return ResultVo.failure("删除失败");
        }
        StockAdjustDO adjustDO = stockAdjustMapper.selectById(id);
        if (adjustDO.getOptCode() == 2) {
            return ResultVo.failure("已确认的不可删除！");
        }
        int i = stockAdjustMapper.deleteById(id);
        return i == 1 ? ResultVo.success("删除成功") : ResultVo.failure("删除失败!");
    }

    @Override
    public ResultVo<String> createInvoiceNo() {
        ResultVo<String> billNo = commonServiceFeignApi.generatorBillNo("32");

        return ResultVo.success(billNo.getData());
    }

    @Override
    public ResultVo<StockAdjustDTO> getOrderInfoForImpAdjuest(String fullOrderNo) {
        OrderNoInfo orderNo = new OrderNoInfo().convertFullOrderNo(fullOrderNo);

        LambdaQueryWrapper<OpsPurchaseOrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPurchaseOrderDO::getOrderNo, orderNo.getOrderNo())
                .eq(OpsPurchaseOrderDO::getItemNo, orderNo.getItemNo());

        OpsPurchaseOrderDO orderDO = opsPurchaseOrderMapper.selectOne(queryWrapper);

        if (orderDO == null) {
            return ResultVo.failure("未查询到订单信息，请自行填写");
        }

        StockAdjustDTO dto = new StockAdjustDTO();
        dto.setModelNo(orderDO.getModelNo());
        dto.setPrice(orderDO.getStdPrice() == null ? BigDecimal.ZERO : new BigDecimal(orderDO.getStdPrice().toString()));
        dto.setQuantity(orderDO.getQuantity());
        dto.setWarehouseCode(orderDO.getReceiveWarehouseId());

        return ResultVo.success(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> importStockAdjustData(MultipartFile file) {

        try {
            ExcelHelper excel = new ExcelHelper(file.getInputStream());
            Sheet sheet = excel.getSheet();
            LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
            long statetime = System.currentTimeMillis();

            long statetime2 = System.currentTimeMillis();

            int cel;
            int lastRowNum = sheet.getLastRowNum();
            List<StockAdjustDO> stockAdjustDOList = new ArrayList<>();
            Map<String, String> mapRepeatData = new HashMap();
            Map<String, Integer> beImport = new HashMap();
            final int batchCount = 700; //单次缓存的数据量
            Integer repeatCount = 0;
            Integer doCount = 0;
            Integer errCount = 0;

            LambdaQueryWrapper<StockAdjustDO> queryWrapper = new LambdaQueryWrapper<>();
            StringBuilder errorMsg = new StringBuilder();

            Map<String, Boolean> mapWarehouse = new HashMap<>();
            Map<String, String> mapCheckInventory = new HashMap<>();

            for (int row = 1; row <= lastRowNum; row++) {
                Row rows = sheet.getRow(row);
                if (rows == null) {
                    break;
                }
                cel = 0;
                StockAdjustDO adjustDO = new StockAdjustDO();
                String invoiceNo = excel.getCellString(rows.getCell(cel++)).trim();

                if (StringUtils.isNotBlank(invoiceNo)) {
                    //    <!--add by WuWeiDong 20230907  bug 12009   入库调整发票号+订单号长度限制 -->
                    //    <!--edit by WuWeiDong 20230925  bug 12166   发票号不能输入超过30位 -->
                    if (invoiceNo.length() > 30) {
                        return ResultVo.failure("发票号长度不能超过30！");
                    }
                    adjustDO.setInvoiceNo(invoiceNo);

                } else {
                    ResultVo<String> resultVo = this.createInvoiceNo();
                    adjustDO.setInvoiceNo(resultVo.getData());
                }
                String dateString = excel.getCellString(rows.getCell(cel++));
                if (StringUtils.isBlank(dateString)) {
                    adjustDO.setAdjustDate(new Date());
                } else {
                    adjustDO.setAdjustDate(DateUtil.stringToDate(dateString));
                }
                String fullOrderNo = excel.getCellString(rows.getCell(cel++));
                if (StringUtils.isNotBlank(fullOrderNo)) {
                    fullOrderNo = fullOrderNo.trim();
                    //   <!--add by WuWeiDong 20230907  bug 12009   入库调整发票号+订单号长度限制 -->
                    //    <!--edit by WuWeiDong 20230925  bug 12166   2.fullOrderNo长度不能超过16位
                    //3.解析fullOrderNo时，判断是否是旧订单，旧订单按照现有规则拆分，非旧订单以最后一个"-"拆分，order_item必须为数字  0<item<9999, orderNo最大长度11位。

                    if (fullOrderNo.length() > 16) {
                        errorMsg.append("完整订单号不能超过16！");
                    }
                    OrderNoInfo orderInfo = new OrderNoInfo().convertFullOrderNo(fullOrderNo);
                    if (orderInfo.getItemNo().compareTo(9999) > 0 || orderInfo.getItemNo().compareTo(0) <= 0) {
                        errorMsg.append("项号必须为数字  0<item<9999,");
                    } else if (orderInfo.getOrderNo().length() > 11) {
                        errorMsg.append("订单号最大长度11位,");
                    }

                    if (StringUtils.isNotBlank(errorMsg.toString())) {
                        return ResultVo.failure(errorMsg.substring(0, errorMsg.length() - 1) + "。");
                    }
                } else {
                    fullOrderNo = "";
                }
                adjustDO.setFullOrderNo(fullOrderNo);


                if (mapRepeatData.containsKey(adjustDO.getInvoiceNo() + adjustDO.getFullOrderNo())) {
                    return ResultVo.failure("入库数据有发票号+订单号重复: " + adjustDO.getInvoiceNo() + "，" + adjustDO.getFullOrderNo());
                } else {
                    mapRepeatData.put(adjustDO.getInvoiceNo() + adjustDO.getFullOrderNo(), adjustDO.getInvoiceNo() + adjustDO.getFullOrderNo());
                }
                //判断发票是已导入，若已导入判断订单是否存在，存在不写入。
                if (!beImport.containsKey(adjustDO.getInvoiceNo())) {
                    queryWrapper.clear();
                    queryWrapper.eq(StockAdjustDO::getInvoiceNo, adjustDO.getInvoiceNo());
                    Integer rtnCount = stockAdjustMapper.selectCount(queryWrapper);
                    beImport.put(adjustDO.getInvoiceNo(), rtnCount);
                }
                if (beImport.containsKey(adjustDO.getInvoiceNo())) {
                    Integer count = beImport.get(adjustDO.getInvoiceNo());
                    if (count.compareTo(0) == 1) {
                        queryWrapper.clear();
                        queryWrapper.eq(StockAdjustDO::getInvoiceNo, adjustDO.getInvoiceNo())
                                .eq(StockAdjustDO::getFullOrderNo, adjustDO.getFullOrderNo());
                        Integer rtnCount = stockAdjustMapper.selectCount(queryWrapper);
                        if (rtnCount.compareTo(0) == 1) {
                            repeatCount++;
                            continue;
                        }
                    }
                }

                OrderNoInfo orderInfo = new OrderNoInfo().convertFullOrderNo(fullOrderNo);
                adjustDO.setOrderNo(orderInfo.getOrderNo());
                adjustDO.setItemNo(orderInfo.getItemNo());
                adjustDO.setSplitItemNo(orderInfo.getSplitItem());
                adjustDO.setModelNo(excel.getCellString(rows.getCell(cel++)).trim());
                if (StringUtils.isBlank(adjustDO.getModelNo())) {
                    break;
                }
                adjustDO.setQuantity(Integer.valueOf(excel.getCellString(rows.getCell(cel++)).trim()));
                String yw = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(yw) && yw.equals("1")) {
                    adjustDO.setAdjustType(1);
                }
                String cw = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(cw) && cw.equals("1")) {
                    adjustDO.setAdjustType(Integer.valueOf(adjustDO.getAdjustType() == null ? "3" : adjustDO.getAdjustType() + "3"));
                }
                String wms = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(wms) && wms.equals("1")) {
                    adjustDO.setAdjustType(Integer.valueOf(adjustDO.getAdjustType() == null ? "2" : adjustDO.getAdjustType() + "2"));
                }
                String price = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(price)) {
                    adjustDO.setPrice(new BigDecimal(price));
                }
                adjustDO.setCurrency(excel.getCellString(rows.getCell(cel++)).trim()); //货币
                String rate = excel.getCellString(rows.getCell(cel++));
                if (StringUtils.isNotBlank(rate)) {
                    adjustDO.setExchangeRate(new BigDecimal(rate));
                }
                String amount = excel.getCellString(rows.getCell(cel++)).trim();
                if (PublicUtil.isNotEmpty(amount)) {
                    adjustDO.setAmount(new BigDecimal(amount));
                }
                adjustDO.setReason(excel.getCellString(rows.getCell(cel++)).trim());
                adjustDO.setSupplierCode(excel.getCellString(rows.getCell(cel++)).trim());

                //    <!--add by WuWeiDong 20240308  bug 13688  入库调整导入按钮增加仓库号的校验 -->
                String warehouseCode = excel.getCellString(rows.getCell(cel++)).trim();
                Boolean isWarehouse = false;
                if (mapWarehouse.containsKey(warehouseCode)) {
                    isWarehouse = mapWarehouse.get(warehouseCode);
                } else {
                    ResultVo<WarehouseVO> warehouseResult = commonServiceFeignApi.getWarehouseInfoByCode(warehouseCode);
                    isWarehouse = warehouseResult.isSuccess();
                    mapWarehouse.put(warehouseCode, isWarehouse);
                }

                if (!isWarehouse) {
                    errCount += 1;
                    errorMsg.append("</br>");
                    if (!errorMsg.toString().contains(adjustDO.getFullOrderNo())) {
                        errorMsg.append(adjustDO.getFullOrderNo());
                    }
                    errorMsg.append("[").append(warehouseCode).append("]").append("仓库不存在。");
                    continue;
                }

                adjustDO.setWarehouseCode(warehouseCode);
                adjustDO.setInventoryTypeCode(excel.getCellString(rows.getCell(cel++)).trim());
                adjustDO.setCustomerNo(excel.getCellString(rows.getCell(cel++)).trim());
                adjustDO.setPplNo(excel.getCellString(rows.getCell(cel++)).trim());
                adjustDO.setProjectNo(excel.getCellString(rows.getCell(cel++)).trim());
                adjustDO.setGroupCustomerNo(excel.getCellString(rows.getCell(cel)).trim());
                adjustDO.setCreateUser(userDTO.getUserNo());
                adjustDO.setOptCode(1);
                adjustDO.setCreateTime(new Date());

                //add by A78027 from bug11194 in 20230620
                if (adjustDO.getAdjustType().toString().contains("3")
                        && StringUtils.isBlank(adjustDO.getSupplierCode())) {
                    errCount += 1;
                    errorMsg.append("</br>");
                    if (!errorMsg.toString().contains(adjustDO.getFullOrderNo())) {
                        errorMsg.append(adjustDO.getFullOrderNo());
                    }
                    errorMsg.append("调财务库存必须输入供应商代码：");
                    continue;
                }
                String checkInventoryKey = String.join("-", adjustDO.getInventoryTypeCode(), adjustDO.getCustomerNo(), adjustDO.getPplNo(), adjustDO.getProjectNo(), adjustDO.getGroupCustomerNo());
                String msgCheckInventoryCode = mapCheckInventory.getOrDefault(checkInventoryKey, "");
                if (StringUtils.isBlank(msgCheckInventoryCode)) {
                    msgCheckInventoryCode = opsCommonService.checkInventoryCode(adjustDO.getInventoryTypeCode(), adjustDO.getCustomerNo(), adjustDO.getPplNo(), adjustDO.getProjectNo(), adjustDO.getGroupCustomerNo());
                    if (StringUtils.isBlank(msgCheckInventoryCode)) {
                        msgCheckInventoryCode = "-1";
                    }
                    mapCheckInventory.put(checkInventoryKey, msgCheckInventoryCode);
                }
                if (!"-1".equalsIgnoreCase(msgCheckInventoryCode)) {
                    errCount += 1;
                    errorMsg.append("</br>");
                    if (!errorMsg.toString().contains(adjustDO.getFullOrderNo())) {
                        errorMsg.append(adjustDO.getFullOrderNo());
                    }
                    errorMsg.append("错误：").append(msgCheckInventoryCode);
                } else {
                    stockAdjustDOList.add(adjustDO);
                }


                // WuWeiDong 20230420  分多批处理
                if (stockAdjustDOList.size() >= batchCount) {
                    Integer insertNumber = this.insertStockAdjust(stockAdjustDOList);
                    doCount = doCount + insertNumber;
                    // 存储完成清理 list
                    stockAdjustDOList.clear();
                }

            }
            if (PublicUtil.isNotEmpty(stockAdjustDOList) && stockAdjustDOList.size() >= 1) {
                Integer insertNumber = this.insertStockAdjust(stockAdjustDOList);
                doCount = doCount + insertNumber;

            }

            log.info(Thread.currentThread().getName() +
                    "导入成功" + doCount.toString() + "笔,重复:" + repeatCount.toString() + "笔,耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);

            if (StringUtils.isBlank(errorMsg)) {
                return ResultVo.success("导入完成，" + doCount.toString() + "笔,重复:" + repeatCount.toString() + "笔。");
            } else {
                return ResultVo.success("导入完成，" + doCount.toString() + "笔,重复:" + repeatCount.toString() + "笔,有问题：" + errCount.toString() + "笔。" + errorMsg.toString());
            }


        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure("导入失败！" + ex);
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return ResultVo.failure("导入失败！" + ex);
        }

    }

    /***
     *    WuWeiDong 20230410  改用读寫分離，分多小任务处理
     * @param invoiceNo
     * @return
     */
    @Override
    // @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> determineStockAdjust(String invoiceNo) {

        String lockKey = Constants.REDIS_KEY_STOCK_ADJUST_LOCK + invoiceNo;

        if (!redissonUtil.tryLock(lockKey, 1, 60 * 1200, TimeUnit.SECONDS)) {
            log.error("票号：" + invoiceNo + "调整已在处理中。");
            return ResultVo.failure("票号：" + invoiceNo + "调整已在处理中。");
        }
        try {
            long statetime = System.currentTimeMillis();

            ResultVo<String> rtnval = ResultVo.failure("处理错误！");
            QueryWrapper<StockAdjustDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("invoiceNo", invoiceNo);
            queryWrapper.eq("optCode", 1);
            //   List<Integer> optCodes = Arrays.asList(1, 4);//末处理与处理失败一起处理。
            // queryWrapper.in("optCode", optCodes);

            List<StockAdjustDO> list = stockAdjustMapper.selectList(queryWrapper);
            if (PublicUtil.isEmpty(list)) {
                return ResultVo.failure("没有可确认的订单");
            }
            log.info(Thread.currentThread().getName() +
                    "1)查询耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
            StringBuilder errorMessage = new StringBuilder();

            //    <!--add by WuWeiDong 20230525  bug 10676  验证库存类型 -->
            // 按 库存类型~客户代码~PPL~项目号~客户群号 拼接
            Map<String, Long> inventoryTypCodeMap = list.stream().collect(
                    Collectors.groupingBy(g -> g.getInventoryTypeCode() + "~" + g.getCustomerNo() + "~" + g.getPplNo() + "~" + g.getProjectNo() + "~" + g.getGroupCustomerNo(), Collectors.counting()));
            if (CollectionUtil.isNotEmpty(inventoryTypCodeMap) && inventoryTypCodeMap.size() >= 1) {
                for (String key : inventoryTypCodeMap.keySet()) {
                    String[] inventoryKey = key.split("~", -1);
                    String checkMessage = opsCommonService.checkInventoryCode(inventoryKey[0], inventoryKey[1], inventoryKey[2], inventoryKey[3], inventoryKey[4]);
                    if (StringUtils.isNotBlank(checkMessage)) {
                        errorMessage.append(checkMessage).append(";");
                    }
                }
            }
            if (StringUtils.isNotBlank(errorMessage.toString())) {
                log.error("库存调整失败：" + errorMessage.toString());
                return ResultVo.failure("库存调整失败：" + errorMessage.toString());
            }

            //处理负数
            List<StockAdjustDO> adjustDOLis = list.stream().filter(i -> i.getQuantity() < 0).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(adjustDOLis) && adjustDOLis.size() >= 1) {
                rtnval = this.collateAdjustData(adjustDOLis, AdjustType.Subtraction);
                log.info(Thread.currentThread().getName() +
                        "2)处理负数耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
                if (!rtnval.isSuccess()) {
                    return rtnval;
                }
            }

            //处理正数
            adjustDOLis = list.stream().filter(i -> i.getQuantity() > 0).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(adjustDOLis) && adjustDOLis.size() >= 1) {
                rtnval = this.collateAdjustData(adjustDOLis, AdjustType.Addition);
                log.info(Thread.currentThread().getName() +
                        "3)处理正数(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
            }
            return rtnval;
        } catch (Exception ex) {
            log.error("库存调整失败：" + ex.getMessage());
            return ResultVo.failure("库存调整失败!");
        } finally {
            redissonUtil.unlock(lockKey);
        }
    }

    /**
     * 整理调整数据
     *
     * @return
     */
    private ResultVo<String> collateAdjustData(List<StockAdjustDO> list, AdjustType adjustType) {

        if (CollectionUtil.isEmpty(list) && list.size() == 0) {
            return ResultVo.failure(Thread.currentThread().getName() + "错误：没有处理数据。");
        }

        InventoryForAdjustInputDto dto;
        AdjustItemDTO adjustDto = new AdjustItemDTO();
        List<AdjustItemDTO> dtoList = new ArrayList<>();  //更新业务数据
        InventoryForAdjustDto wmsdto;
        List<InventoryForAdjustDto> wmsList = new ArrayList<>(); //更新物流数据
        List<StockAdjustDO> costList = new ArrayList<>();  //更新成本数据
        ResultVo<String> rtnval = ResultVo.failure("处理错误！");
        String optType = null;
        Map<String, String> invoiceMap = new HashMap<>();
        try {
            for (StockAdjustDO adjustDO : list) {
                if (adjustDO.getAdjustType().toString().contains("1")) {
                    adjustDto = new AdjustItemDTO();
                    adjustDto.setAdjustAvailableInventory(false);//请允许负库存
                    adjustDto.setId(adjustDO.getId().toString());
                    adjustDto.setInvoiceNo(adjustDO.getInvoiceNo());
                    adjustDto.setOrderId(adjustDO.getOrderNo());
                    adjustDto.setOrderItem(adjustDO.getItemNo());
                    adjustDto.setModelNo(adjustDO.getModelNo());
                    adjustDto.setQty(adjustDO.getAdjustType());
                    adjustDto.setPropertyType(InventoryTypeEnum.parse(adjustDO.getInventoryTypeCode()));

                    if (adjustDO.getQuantity() > 0) {
                        adjustDto.setAdjustType(AdjustType.Addition); //= "+";
                    } else {
                        adjustDto.setAdjustType(AdjustType.Subtraction);  // optType = "-";
                    }

                    adjustDto.setQty(Math.abs(adjustDO.getQuantity()));

                    adjustDto.setWarehouseCode(adjustDO.getWarehouseCode());
                    adjustDto.setCustomerNo(PublicUtil.isEmpty(adjustDO.getCustomerNo()) ? null : adjustDO.getCustomerNo());
                    adjustDto.setPpl(PublicUtil.isEmpty(adjustDO.getPplNo()) ? null : adjustDO.getPplNo());
                    adjustDto.setProjectCode(PublicUtil.isEmpty(adjustDO.getProjectNo()) ? null : adjustDO.getProjectNo());
                    adjustDto.setGroupCustomerNo(PublicUtil.isEmpty(adjustDO.getGroupCustomerNo()) ? null : adjustDO.getGroupCustomerNo());

                    dtoList.add(adjustDto);

                }

                if (adjustDO.getAdjustType().toString().contains("2")) {
                    wmsdto = new InventoryForAdjustDto();

                    wmsdto.setOrderId(adjustDO.getFullOrderNo());
                    wmsdto.setModelno(adjustDO.getModelNo());
                    wmsdto.setQty(Math.abs(adjustDO.getQuantity()));
                    if (adjustDO.getPrice() == null) {
                        wmsdto.setPrice(0L);
                    } else {
                        wmsdto.setPrice(adjustDO.getPrice().longValue());
                    }
                    wmsdto.setQaStatus("0");
                    wmsdto.setWarehouseCode(adjustDO.getWarehouseCode());
                    wmsdto.setCustomerNo(PublicUtil.isEmpty(adjustDO.getCustomerNo()) ? null : adjustDO.getCustomerNo());
                    wmsdto.setPpl(PublicUtil.isEmpty(adjustDO.getPplNo()) ? null : adjustDO.getPplNo());
                    wmsdto.setProjectCode(PublicUtil.isEmpty(adjustDO.getProjectNo()) ? null : adjustDO.getProjectNo());
                    wmsdto.setGroupCustomerNo(PublicUtil.isEmpty(adjustDO.getGroupCustomerNo()) ? null : adjustDO.getGroupCustomerNo());
                    wmsdto.setExpDate(adjustDO.getAdjustDate());
                    wmsList.add(wmsdto);

                }

                if (adjustDO.getAdjustType().toString().contains("3")) {
                    try {
                        if (invoiceMap.containsKey(adjustDO.getInvoiceNo())) {
                            adjustDO.setInvoiceId(invoiceMap.get(adjustDO.getInvoiceNo()));
                        } else {
                            String billNo = this.generatorBillNo("21");
                            adjustDO.setInvoiceId(billNo);
                            invoiceMap.put(adjustDO.getInvoiceNo(), billNo);
                        }
                        costList.add(adjustDO);
                    } catch (Exception e) {
                        log.error("财务库存调整失败：" + e.getMessage());
                        return ResultVo.failure("财务库存调整失败!");
                        /// throw new RuntimeException(e);
                    }
                }

            }

            ///写入更新数据
            log.info("写入更新数据,list:" + list.size() + " cost:" + costList.size() + "dolist:" + dtoList.size() + "wmslist:" + wmsList.size());

            return this.asyncWriteStockAdjust(PublicUtil.isNotEmpty(costList) && costList.size() >= 1 ? costList : list, dtoList, wmsList, PublicUtil.isNotEmpty(costList), adjustType);

        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure("库存调整失败！" + ex);
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return ResultVo.failure("库存调整失败！" + ex);
        }

    }


    private ExecutorService wmsService = Executors.newFixedThreadPool(1);
    private ExecutorService costService = Executors.newFixedThreadPool(1);

    /**
     * 写入更新数据
     *
     * @param adjustDOList
     * @param dtoList
     * @param wmsList
     * @param adjustCost
     * @param adjustType
     * @return
     */
    private ResultVo<String> asyncWriteStockAdjust(List<StockAdjustDO> adjustDOList, List<AdjustItemDTO> dtoList, List<InventoryForAdjustDto> wmsList,
                                                   Boolean adjustCost, AdjustType adjustType) {

        try {
            LoginUserDTO loginUserDTO = SMCApp.getLoginAuthDtoForSysUser();
            UserDto userDto = new UserDto();
            userDto.setUserName(loginUserDTO.getUserNo());
            userDto.setIp(IpUtil.getIpAddress());
            ResultVo<String> rtnval = ResultVo.failure("调整错误！");
            LambdaUpdateWrapper<StockAdjustDO> updateWrapper = new LambdaUpdateWrapper<>();
            wmsService = Executors.newFixedThreadPool(1);
            costService = Executors.newFixedThreadPool(1);

            long statetime = System.currentTimeMillis();

            if (PublicUtil.isNotEmpty(dtoList) && dtoList.size() >= 1) {
                Integer offset = 200;
                Integer size = dtoList.size();


                for (Integer idx = 0; idx < size; idx++) {
                    final List<AdjustItemDTO> listsub = (idx + offset) >= size ? dtoList.subList(idx, size) : dtoList.subList(idx, idx + offset + 1);
                    idx = idx + offset;
                    AdjustParam adjustsalse = new AdjustParam();
                    adjustsalse.setAdjustItems(listsub);
                    adjustsalse.setUserDto(userDto);


                    log.info("业务调整参数 {}", JSON.toJSONString(adjustsalse));
                    long statetime2 = System.currentTimeMillis();
                    try {
                        CommonResult<AdjustParam> result = opsWmDispatchForOrderFeignApi.adjustInventory(adjustsalse);
                        log.info("調整用adjustInventory返回：==》{}", JSON.toJSONString(result));
                        if (PublicUtil.isEmpty(result.getData())) {
                            log.error("业务库存调整失败：没有数据返回。");
                            return ResultVo.failure("业务库存调整失败：没有数据返回。" + result.getMessage());
                        }
                        List<AdjustItemDTO> ResultList = result.getData().getAdjustItems();
                        if (PublicUtil.isEmpty(ResultList) || ResultList.size() == 0) {
                            log.error("业务库存调整失败：没有明细数据。");
                            return ResultVo.failure("业务库存调整失败：没有明细数据。" + result.getMessage());
                        }
                        List<AdjustItemDTO> subList = new ArrayList<>();
                        //0:失败
                        subList = ResultList.stream().filter(i -> i.getResult() == 0).collect(Collectors.toList());
                        if (PublicUtil.isNotEmpty(subList) && subList.size() >= 1) {
                            for (AdjustItemDTO item : subList) {
                                LambdaUpdateWrapper<StockAdjustDO> stockAdjustWrapper = new LambdaUpdateWrapper<>();
                                String resultMsg = item.getMessage();
                                if (resultMsg.length() > 200) {
                                    resultMsg = resultMsg.substring(0, 200);
                                }
                                stockAdjustWrapper.eq(StockAdjustDO::getId, item.getId())
                                        .set(StockAdjustDO::getResultMsg, resultMsg)
                                        .set(StockAdjustDO::getOptCode, 4)
                                        .set(StockAdjustDO::getUpdateUser, loginUserDTO.getUserNo())
                                        .set(StockAdjustDO::getUpdateTime, DateUtil.getNow());
                                stockAdjustMapper.update(null, stockAdjustWrapper);
                            }

                        }
                        List<InventoryForAdjustDto> wmsSubList = new ArrayList<>(); //更新物流数据
                        List<StockAdjustDO> costSubList = new ArrayList<>();  //更新成本数据
                        subList = ResultList.stream().filter(i -> i.getResult() == 1 || i.getResult() == 2).collect(Collectors.toList());
                        if (PublicUtil.isNotEmpty(subList) && subList.size() >= 1) {

                            for (AdjustItemDTO itemDTO : subList) {
                                if (PublicUtil.isNotEmpty(wmsList) && wmsList.size() >= 1) {
                                    List<InventoryForAdjustDto> wmsSub = wmsList.stream().filter(f -> f.getOrderId().equalsIgnoreCase(itemDTO.getOrderId()) && f.getOrderItem().equals(itemDTO.getOrderItem()))
                                            .collect(Collectors.toList());
                                    if (PublicUtil.isNotEmpty(wmsSub) && wmsSub.size() >= 1) {
                                        wmsSubList.addAll(wmsSub);
                                    }
                                }
                                if (adjustCost) {
                                    List<StockAdjustDO> costSub = adjustDOList.stream().filter(f -> f.getId().compareTo(Long.valueOf(itemDTO.getId())) == 0)
                                            .collect(Collectors.toList());
                                    if (PublicUtil.isNotEmpty(costSub) && costSub.size() >= 1) {
                                        costSubList.addAll(costSub);
                                    }
                                }
                            }
                        }
                        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);//纺
                        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                        rtnval = transactionTemplate.execute(transactionStatus -> {
                            try {
                                if (PublicUtil.isNotEmpty(wmsSubList) && wmsSubList.size() > 0) {
                                    wmsService = this.insertWMSByList(wmsSubList, adjustType);
                                }
                                if (adjustCost && PublicUtil.isNotEmpty(costSubList) && costSubList.size() > 0) {
                                    costService = this.insertPoInvocieByList(costSubList);
                                }
                                wmsService.shutdown();
                                costService.shutdown();
                                while (true) {
                                    if (wmsService.isTerminated() && costService.isTerminated()) {
                                        List<AdjustItemDTO> subList2 = new ArrayList<>();
                                        //1：成功
                                        subList2 = ResultList.stream().filter(i -> i.getResult() == 1).collect(Collectors.toList());
                                        if (CollectionUtils.isNotEmpty(subList2)) {

                                            List<String> strIds = subList2.stream().map(AdjustItemDTO::getId).distinct().collect(Collectors.toList());
                                            LambdaUpdateWrapper<StockAdjustDO> stockAdjustWrapper = new LambdaUpdateWrapper<>();
                                            stockAdjustWrapper.in(StockAdjustDO::getId, strIds)
                                                    .set(StockAdjustDO::getOptCode, 2)
                                                    .set(StockAdjustDO::getUpdateUser, loginUserDTO.getUserNo())
                                                    .set(StockAdjustDO::getUpdateTime, DateUtil.getNow());
                                            stockAdjustMapper.update(null, stockAdjustWrapper);
                                        }
                                        //2：重复提交
                                        subList2 = ResultList.stream().filter(i -> i.getResult() == 2).collect(Collectors.toList());
                                        if (CollectionUtils.isNotEmpty(subList2)) {
                                            for (AdjustItemDTO item : subList2) {
                                                LambdaUpdateWrapper<StockAdjustDO> stockAdjustWrapper = new LambdaUpdateWrapper<>();
                                                //    <!--add by WuWeiDong 20240308  bug 13688  信息截断处理 -->
                                                String resultMsg = item.getMessage();
                                                if (resultMsg.length() > 200) {
                                                    resultMsg = resultMsg.substring(0, 200);
                                                }
                                                stockAdjustWrapper.eq(StockAdjustDO::getId, item.getId())
                                                        .set(StringUtils.isNotEmpty(resultMsg), StockAdjustDO::getResultMsg, resultMsg)
                                                        .set(StockAdjustDO::getOptCode, 2)
                                                        .set(StockAdjustDO::getUpdateUser, loginUserDTO.getUserNo())
                                                        .set(StockAdjustDO::getUpdateTime, DateUtil.getNow());
                                                stockAdjustMapper.update(null, stockAdjustWrapper);
                                            }
                                        }
                                        break;
                                    }
                                }
                                return ResultVo.success("库存调整完成");
                            } catch (Exception ex) {
                                log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                                return ResultVo.failure("库存调整失败！" + ex);
                            }
                        });


                    } catch (Exception ex) {
                        log.error("库存调整,調整用adjustInventory失败！：" + ex);
                        return ResultVo.failure("库存调整,調整用adjustInventory失败！：" + ex);
                    }

                    log.info(Thread.currentThread().getName() +
                            "调用业务库存调整接口完成(s):" + (System.currentTimeMillis() - statetime2) / 1000.0);
                }


            } else {
                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                rtnval = transactionTemplate.execute(transactionStatus -> {
                    try {
                        if (PublicUtil.isNotEmpty(wmsList) && wmsList.size() >= 1) {
                            wmsService = this.insertWMSByList(wmsList, adjustType);
                        }
                        if (adjustCost && PublicUtil.isNotEmpty(adjustDOList) && adjustDOList.size() >= 1) {
                            costService = this.insertPoInvocieByList(adjustDOList);
                        }
                        wmsService.shutdown();
                        costService.shutdown();
                        while (true) {
                            if (wmsService.isTerminated() && costService.isTerminated()) {
                                List<Long> ids = adjustDOList.stream().map(StockAdjustDO::getId).distinct().collect(Collectors.toList());
                                Integer offset = 2000;//服务器支持最多 2100 个参数,设置分每次2000来发
                                Integer size = ids.size();
                                for (Integer idx = 0; idx < size; idx++) {
                                    final List<Long> idsSub = (idx + offset) >= size ? ids.subList(idx, size) : ids.subList(idx, idx + offset + 1);
                                    idx = idx + offset;
                                    updateWrapper.clear();
                                    updateWrapper.in(StockAdjustDO::getId, idsSub)
                                            .set(StockAdjustDO::getOptCode, 2)
                                            .set(StockAdjustDO::getUpdateUser, loginUserDTO.getUserNo())
                                            .set(StockAdjustDO::getUpdateTime, DateUtil.getNow());
                                    stockAdjustMapper.update(null, updateWrapper);
                                }
                                break;
                            }
                        }
                        return ResultVo.success("库存调整完成");

                    } catch (Exception ex) {
                        log.error(Thread.currentThread().getName() + "->错误TransactionTemplate：" + ex);
                        return ResultVo.failure("库存调整失败！" + ex);
                    }
                });
            }
            log.info(Thread.currentThread().getName() +
                    "写入调整数据完成(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
            return rtnval;
        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure("库存调整失败！" + ex);
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return ResultVo.failure("库存调整失败！" + ex);
        }

    }

    /**
     * 取消 统一到createStockAssembleFromInvoiceError
     *
     * @return
     */
    @Override
    public ResultVo<String> threeCProductAdjust() {
        LambdaQueryWrapper<OpsImpDataDO> query = new LambdaQueryWrapper<>();
        query.eq(OpsImpDataDO::getOperate, 1);
        List<OpsImpDataDO> list = opsImpDataMapper.selectList(query);

        List<StockAssemblyItemDTO> outItems;
        List<StockAssemblyItemDTO> inItems;
        StockAssemblyItemDTO outItemDTO;
        StockAssemblyItemDTO inItemDTO;
        StockAssemblyApplyDTO applyDTO;
        for (OpsImpDataDO impDataDO : list) {
            applyDTO = new StockAssemblyApplyDTO();
            applyDTO.setStatus("6");
            applyDTO.setAssembleType("5"); // 组换-仅财务
            applyDTO.setApplyType("1"); // 组换申请
            if (impDataDO.getSplitItemNo() != null && impDataDO.getSplitItemNo() > 0) {
                applyDTO.setNeedForOrderNo(impDataDO.getOrderNo() + "-" + impDataDO.getItemNo() + "-" + impDataDO.getSplitItemNo());
            } else {
                applyDTO.setNeedForOrderNo(impDataDO.getOrderNo() + "-" + impDataDO.getItemNo());
            }
            applyDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + ",订单号:" + applyDTO.getNeedForOrderNo());
            applyDTO.setApplyDate(new Date());

            outItems = new ArrayList<>(1);
            outItemDTO = new StockAssemblyItemDTO();
            outItemDTO.setModelNo("3C-" + impDataDO.getModelNo());
            outItemDTO.setIsTransOut(Boolean.TRUE);
            outItemDTO.setQuantity(-impDataDO.getQuantity().doubleValue());
            outItemDTO.setOptCode(6);
            outItemDTO.setWarehouseCode(impDataDO.getReceiveWarehouseId());
            outItemDTO.setInventoryType("TY");
            outItemDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + ",订单号:" + applyDTO.getNeedForOrderNo() + ",入库调整型号:" + outItemDTO.getModelNo());
            outItems.add(outItemDTO);

            inItems = new ArrayList<>(1);
            inItemDTO = new StockAssemblyItemDTO();
            inItemDTO.setModelNo(impDataDO.getModelNo());
            inItemDTO.setIsTransOut(Boolean.FALSE);
            inItemDTO.setQuantity(impDataDO.getQuantity().doubleValue());
            inItemDTO.setOptCode(6);
            inItemDTO.setWarehouseCode(impDataDO.getReceiveWarehouseId());
            inItemDTO.setInventoryType("TY");
            inItemDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + ",订单号:" + applyDTO.getNeedForOrderNo() + ",入库调整型号:" + outItemDTO.getModelNo());
            inItems.add(inItemDTO);

            applyDTO.setOutItems(outItems);
            applyDTO.setInItems(inItems);

            ResultVo<String> resultVo = stockAssemblyFeignApi.createStockAssemblyApply(applyDTO);
            if (!resultVo.isSuccess()) {
                log.error("3C调整失败:" + resultVo.getMessage());
                return ResultVo.failure(resultVo.getMessage());
            }

            // 修改状态，防止重复调整
            impDataDO.setOperate(2);
            opsImpDataMapper.updateById(impDataDO);
        }
        return ResultVo.success("成功");
    }

    //    <!--add by WuWeiDong 20221027 bug 2084 -->
    @Override
    public ResultVo<String> createStockAssembleFromInvoiceError() {
        List<OpsImpDataDO> impList = opsImpDataMapper.selectInvoiceErrorModel();
        if (PublicUtil.isEmpty(impList)) {
            return ResultVo.success("入库没有差异型号");
        }
        LambdaQueryWrapper<PoInvoiceDetailDO> invoiceQuery;
        List<StockAssemblyItemDTO> outItems;
        List<StockAssemblyItemDTO> inItems;
        StockAssemblyItemDTO outItemDTO;
        StockAssemblyItemDTO inItemDTO;
        StockAssemblyApplyDTO applyDTO;

//        String returnMesseg="调整失败,入库差型号没有对应发票数据:";
        StringBuilder errMessage = new StringBuilder();
        StringBuilder sameModelMessage = new StringBuilder();
        int count = impList.size();
        int runCount = 0;

        Map<Integer, Integer> mapCost = new HashMap<>();
        Date costDate = DateUtil.getMonthEndDate(new Date());
        for (OpsImpDataDO impDataDO : impList) {

            //    <!--add by WuWeiDong 20240407  bug 13672    -->
            Integer costFlag = -1; //-1，不可以组换;0，可以组换（有costTime,并且status=3 ,才可以组换）
            if (mapCost.containsKey(impDataDO.getInvoiceId())) {
                costFlag = mapCost.get(impDataDO.getInvoiceId());
            } else {
                LambdaQueryWrapper<OpsPoInvoiceDO> masterQuery = Wrappers.lambdaQuery();
                masterQuery.eq(OpsPoInvoiceDO::getInvoiceId, impDataDO.getInvoiceId())
                        .ne(OpsPoInvoiceDO::getStatus, "9");

                OpsPoInvoiceDO poInvoiceDO = poInvoiceMasterMapper.selectOne(masterQuery);
                costFlag = -1;
                //有costTime,并且status=3 ,才可以组换

                if(ObjectUtils.isNotEmpty(poInvoiceDO.getCostTime()) && Optional.ofNullable(poInvoiceDO.getStatus()).orElse(0).compareTo(3)==0){
                    costFlag = 0;
                }

                mapCost.put(impDataDO.getInvoiceId(), costFlag);
            }
            //是<=当前月 才做组换。否则跳过
            if (costFlag.compareTo(0) != 0) {
                if( !errMessage.toString().contains(impDataDO.getInvoiceId().toString())) {
                    errMessage.append(impDataDO.getInvoiceId());
                    errMessage.append("\r\n");
                }
                continue;
            }

            /// OrderNoInfo orderNoInfo = new OrderNoInfo().convertPOOrder(impDataDO.getPoNo().trim(), impDataDO.getLineItem().toString());

            //    <!--add by WuWeiDong 20240407  bug 13672  订单号三项用‘-’链接 -->

            String fullOrderNo ;
            if (ObjectUtils.isNotEmpty(impDataDO.getSplitItemNo()) && impDataDO.getSplitItemNo().compareTo(0) > 0) {
                fullOrderNo = String.join("-", impDataDO.getOrderNo(), impDataDO.getItemNo().toString(), Optional.ofNullable(impDataDO.getSplitItemNo()).orElse(0).toString());
            }else{
                fullOrderNo = String.join("-", impDataDO.getOrderNo(), impDataDO.getItemNo().toString());
            }
            invoiceQuery = Wrappers.lambdaQuery();
            invoiceQuery.eq(PoInvoiceDetailDO::getInvoiceId, impDataDO.getInvoiceId())
                    .eq(PoInvoiceDetailDO::getOrderNo, fullOrderNo)
                    .ne(PoInvoiceDetailDO::getStatus, "9");
            List<PoInvoiceDetailDO> invoiceDetailList = opsPoInvoiceDetailMapper.selectList(invoiceQuery);
            if (PublicUtil.isEmpty(invoiceDetailList)) {
                errMessage.append(fullOrderNo);
                errMessage.append("\r\n");
                continue;
            }
            int totalQuantity = invoiceDetailList.stream().mapToInt(PoInvoiceDetailDO::getQuantity).sum();
            String invoiceModel = invoiceDetailList.get(0).getModelNo();

            if (impDataDO.getModelNo().equalsIgnoreCase(invoiceModel)) {
                sameModelMessage.append(fullOrderNo);
                sameModelMessage.append("\r\n");
                // 修改状态，防止重复调整 1-->4
                LambdaUpdateWrapper<OpsImpDataDO> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.eq(OpsImpDataDO::getInvoiceId, impDataDO.getInvoiceId())
                        .eq(OpsImpDataDO::getPoNo, impDataDO.getPoNo())
                        .eq(OpsImpDataDO::getLineItem, impDataDO.getLineItem())
                        .eq(OpsImpDataDO::getOperate, 1)
                        .set(OpsImpDataDO::getOperate, 4)
                        .set(OpsImpDataDO::getUpdateTime, new Date());
                opsImpDataMapper.update(new OpsImpDataDO(), updateWrapper);
                continue;
            }

            applyDTO = new StockAssemblyApplyDTO();
            applyDTO.setStatus("6");
            applyDTO.setAssembleType("5"); // 组换-仅财务
            applyDTO.setApplyType("1"); // 组换申请
            applyDTO.setNeedModelNo(impDataDO.getModelNo());
            applyDTO.setNeedQuantity(totalQuantity);
            applyDTO.setApplyPsn("发票");

            //    <!--add by WuWeiDong 20221121   -->
            if (impDataDO.getSplitItemNo() != null && impDataDO.getSplitItemNo() > 0) {
                applyDTO.setNeedForOrderNo(impDataDO.getOrderNo() + "-" + impDataDO.getItemNo() + "-" + impDataDO.getSplitItemNo());
            } else {
                applyDTO.setNeedForOrderNo(impDataDO.getOrderNo() + "-" + impDataDO.getItemNo());
            }

            applyDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + ",订单号:" + applyDTO.getNeedForOrderNo());
            applyDTO.setApplyDate(new Date());

            outItems = new ArrayList<>(1);
            outItemDTO = new StockAssemblyItemDTO();
            outItemDTO.setModelNo(invoiceModel);
            outItemDTO.setIsTransOut(Boolean.TRUE);
            outItemDTO.setQuantity(-Double.valueOf(String.valueOf(totalQuantity)));
            outItemDTO.setOptCode(6);
            outItemDTO.setWarehouseCode(impDataDO.getReceiveWarehouseId());
            outItemDTO.setInventoryType("TY");
            outItemDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + "," + applyDTO.getNeedForOrderNo());
            outItems.add(outItemDTO);

            inItems = new ArrayList<>(1);
            inItemDTO = new StockAssemblyItemDTO();
            inItemDTO.setModelNo(impDataDO.getModelNo());
            inItemDTO.setIsTransOut(Boolean.FALSE);
            inItemDTO.setQuantity(Double.valueOf(String.valueOf(totalQuantity)));
            inItemDTO.setOptCode(6);
            inItemDTO.setWarehouseCode(impDataDO.getReceiveWarehouseId());
            inItemDTO.setInventoryType("TY");
            inItemDTO.setRemark("发票号:" + impDataDO.getInvoiceNo() + "," + applyDTO.getNeedForOrderNo());
            inItems.add(inItemDTO);

            applyDTO.setOutItems(outItems);
            applyDTO.setInItems(inItems);

            ResultVo<String> resultVo = this.createStockAssemble(applyDTO, impDataDO);
            if (!resultVo.isSuccess()) {
                return ResultVo.failure("差异型号调整->" + resultVo.getMessage());
            }
            runCount++;
        }
        if (count == runCount) {
            return ResultVo.success("全部成功,共" + count + "笔。");
        } else {
            StringBuilder returnErrorMessage = new StringBuilder();
            if (runCount >= 1) {
                returnErrorMessage.append("部分成功，完成" + runCount + "/" + count + "笔。\r\n");
            }
            if (errMessage.length() >= 1) {
                returnErrorMessage.append("没有发票数据:\r\n");
                returnErrorMessage.append(errMessage);
            }
            if (sameModelMessage.length() >= 1) {
                returnErrorMessage.append("与发票型号一致:\r\n");
                returnErrorMessage.append(sameModelMessage);
            }
            return ResultVo.success(returnErrorMessage.toString());
        }


    }

    private ResultVo<String> createStockAssemble(StockAssemblyApplyDTO applyDTO, OpsImpDataDO impDataDO) {

        ResultVo<String> resultVo = stockAssemblyFeignApi.createStockAssemblyApply(applyDTO);
        if (!resultVo.isSuccess()) {
            log.error("写入组换失败:" + resultVo.getMessage());
            return ResultVo.failure(resultVo.getMessage());
        }

        // 修改状态，防止重复调整 1-->2
        LambdaUpdateWrapper<OpsImpDataDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(OpsImpDataDO::getInvoiceId, impDataDO.getInvoiceId())
                .eq(OpsImpDataDO::getPoNo, impDataDO.getPoNo())
                .eq(OpsImpDataDO::getLineItem, impDataDO.getLineItem())
                .eq(OpsImpDataDO::getOperate, 1)
                .set(OpsImpDataDO::getOperate, 2)
                .set(OpsImpDataDO::getUpdateTime, new Date());

        opsImpDataMapper.update(new OpsImpDataDO(), updateWrapper);
        return ResultVo.success("成功");

    }

    //    <!--add by WuWeiDong 20240318  bug 13734  入库调整批量写优化-->
    private Integer insertStockAdjust(List<StockAdjustDO> list) throws Exception {

        Map<Integer, List<StockAdjustDO>> listMap = SplitBatchUtils.getInsertBatchBySqlserver(list, StockAdjustDO.class);
        Integer count = 0;

        for (Map.Entry<Integer, List<StockAdjustDO>> entry : listMap.entrySet()) {
            stockAdjustMapper.InsertByBatch(entry.getValue());
            count += entry.getValue().size();
        }
        return count;
    }

    private ExecutorService insertWMSByList(List<InventoryForAdjustDto> list, AdjustType adjustType) throws Exception {

        Integer size = list.size();
        Integer offset = 200;
        int runSize = (size / offset) + 1;
        if (runSize > 200) {
            runSize = 200;
        }
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        log.info("===  task数:" + runSize + " 尾数：" + size);
        long statetime = System.currentTimeMillis();
        for (Integer idx = 0; idx < size; idx++) {
            List<InventoryForAdjustDto> wmsListSub;
            if ((idx + offset) >= size) {
                wmsListSub = list.subList(idx, size);
            } else {
                wmsListSub = list.subList(idx, idx + offset + 1);
            }
            final Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    log.info("=== start   task:" + Thread.currentThread().getName());
                    Thread.sleep(100);
                    try {
                        UserDto userDto = new UserDto(SMCApp.getLoginAuthDtoForSysUser().getUserNo(), IpUtil.getIpAddress());
                        InventoryForAdjustInputDto wmsAdjustsub = new InventoryForAdjustInputDto();
                        wmsAdjustsub.setDtoList(wmsListSub);
                        wmsAdjustsub.setOptType(adjustType.getType());
                        wmsAdjustsub.setUserDto(userDto);
                        CommonResult<String> result = opsWmDispatchForOrderFeignApi.wmsAdjust(wmsAdjustsub);
                        if (!result.isSuccess()) {
                            log.error("wms库存调整失败：" + result.getMessage());
                            throw new RuntimeException(result.getMessage());
                        }

                    } catch (NullPointerException ex) {
                        log.error(Thread.currentThread().getName() + "->错误1：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误1：" + ex);
                    } catch (Exception ex) {
                        log.error(Thread.currentThread().getName() + "->错误2：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误2：" + ex);
                    }

                    return 1;
                }
            });
            Thread.sleep(100);
            idx = idx + offset;

        }
        log.info("===finish task:" + Thread.currentThread().getName() +
                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
        return executor;
    }


    /**
     * 写入成本
     *
     * @param list
     * @return
     * @throws Exception
     */
    private ExecutorService insertPoInvocieByList(List<StockAdjustDO> list) throws Exception {

        Integer size = list.size();
        Integer offset = 200;
        int runSize = (size / offset) + 1;
        if (runSize > 200) {
            runSize = 200;
        }
        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        log.info("===  task数:" + runSize + " 尾数：" + size);
        long statetime = System.currentTimeMillis();
        for (Integer idx = 0; idx < size; idx++) {
            List<StockAdjustDO> adjustDOListSub;
            if ((idx + offset) >= size) {
                adjustDOListSub = list.subList(idx, size);
            } else {
                adjustDOListSub = list.subList(idx, idx + offset + 1);
            }
            final Future<Integer> future = executor.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {

                    log.info("=== start   task:" + Thread.currentThread().getName());

                    Thread.sleep(100);
                    try {
                        for (StockAdjustDO adjustDO : adjustDOListSub) {
                            LoginUserDTO userDTO = SMCApp.getLoginAuthDtoForSysUser();
                            OpsPoInvoiceDO invoiceDO = new OpsPoInvoiceDO();
                            String invoiceId = "";
                            if (StringUtils.isBlank(adjustDO.getInvoiceId())) {
                                invoiceId = generatorBillNo("21");
                            } else {
                                invoiceId = adjustDO.getInvoiceId();
                            }
                            if (adjustDO.getPrice() == null) {
                                adjustDO.setPrice(BigDecimal.ZERO);
                            }
                            if (adjustDO.getAmount() == null) {
                                adjustDO.setAmount(BigDecimal.ZERO);
                            }
                            invoiceDO.setInvoiceId(Long.valueOf(invoiceId));
                            invoiceDO.setInvoiceNo(adjustDO.getInvoiceNo());
                            invoiceDO.setStatus(2);
                            invoiceDO.setAmount(adjustDO.getAmount());
                            //    <!--add by WuWeiDong 20230302 bug 9847 入库调整写入发票入库时，金额保留2位小数 -->
                            invoiceDO.setAmountRmb(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            invoiceDO.setAmountadjust(BigDecimal.ZERO);
                            invoiceDO.setAmounttotal(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            invoiceDO.setCustomsDate(adjustDO.getAdjustDate());
                            invoiceDO.setCustomsFee(BigDecimal.ZERO);
                            invoiceDO.setVatFee(BigDecimal.ZERO);
                            invoiceDO.setTransFee(BigDecimal.ZERO);
                            invoiceDO.setOtherFee(BigDecimal.ZERO);
                            invoiceDO.setArrivedWarehouseCode(adjustDO.getWarehouseCode());
                            invoiceDO.setTotalQty(1);
                            invoiceDO.setOrderQty(adjustDO.getQuantity());
                            invoiceDO.setBoxQty(1);
                            invoiceDO.setImpDate(new Date());
                            invoiceDO.setShipDate(new Date());
                            invoiceDO.setReceiveTime(new Date());
                            invoiceDO.setInvoiceType(7);

                            if (StringUtils.isBlank(adjustDO.getSupplierCode())) {
                                invoiceDO.setSupplierCode("");
                            } else {
                                invoiceDO.setSupplierCode(adjustDO.getSupplierCode());
                            }
                            invoiceDO.setCurrencyCode(adjustDO.getCurrency());
                            invoiceDO.setExchangeRate(adjustDO.getExchangeRate());
                            LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
                            queryWrapper.eq(OpsPoInvoiceDO::getInvoiceNo, invoiceDO.getInvoiceNo())
                                    .eq(OpsPoInvoiceDO::getInvoiceType, 7)
                                    .orderByDesc(OpsPoInvoiceDO::getCreateTime);
                            List<OpsPoInvoiceDO> list = poInvoiceMasterMapper.selectList(queryWrapper);
                            if (CollectionUtil.isEmpty(list)) {
                                invoiceDO.setCreateUser(userDTO.getUserNo());
                                invoiceDO.setCreateTime(new Date());
                                invoiceDO.setUpdateTime(new Date());
                                invoiceDO.setUpdateUser(userDTO.getUserNo());
                                poInvoiceMasterMapper.insert(invoiceDO);
                            } else {
                                OpsPoInvoiceDO poInvoiceDO = list.get(0);
                                if (poInvoiceDO.getStatus().compareTo(3) == 0) {
                                    invoiceId = generatorBillNo("21");
                                    invoiceDO.setInvoiceId(Long.valueOf(invoiceId));
                                    invoiceDO.setCreateUser(userDTO.getUserNo());
                                    invoiceDO.setCreateTime(new Date());
                                    invoiceDO.setUpdateTime(new Date());
                                    invoiceDO.setUpdateUser(userDTO.getUserNo());
                                    poInvoiceMasterMapper.insert(invoiceDO);
                                } else {
                                    poInvoiceDO.setAmount(BigDecimalUtil.add(poInvoiceDO.getAmount(), invoiceDO.getAmount()));
                                    poInvoiceDO.setAmountRmb(BigDecimalUtil.add(poInvoiceDO.getAmountRmb(), invoiceDO.getAmountRmb()));
                                    poInvoiceDO.setAmounttotal(BigDecimalUtil.add(poInvoiceDO.getAmounttotal(), invoiceDO.getAmounttotal()));
                                    invoiceDO.setUpdateTime(new Date());
                                    invoiceDO.setUpdateUser(userDTO.getUserNo());
                                    poInvoiceMasterMapper.updateById(poInvoiceDO);
                                }
                            }
                            PoInvoiceDetailDO detail = new PoInvoiceDetailDO();
                            detail.setImpDate(new Date());
                            detail.setStatus(1);
                            detail.setInvoiceNo(adjustDO.getInvoiceNo());
                            detail.setInvoiceId(Long.valueOf(invoiceId));
                            detail.setOrderNo(adjustDO.getFullOrderNo());
                            detail.setModelNo(adjustDO.getModelNo());
                            detail.setQuantity(adjustDO.getQuantity());
                            detail.setPrice(adjustDO.getPrice());
                            detail.setAmount(adjustDO.getAmount());
                            detail.setAmountTotal(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            detail.setPriceTotal(BigDecimalUtil.div(detail.getAmountTotal(), new BigDecimal(detail.getQuantity()), 4));
                            detail.setPriceRmb(BigDecimalUtil.mul(adjustDO.getPrice(), adjustDO.getExchangeRate()));
                            detail.setAmountRmb(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
                            detail.setAmountAdjust(BigDecimal.ZERO);
                            detail.setImpType(0);
                            detail.setRemark(adjustDO.getReason());
                            detail.setSupplierCode(adjustDO.getSupplierCode());
                            detail.setCurrency(adjustDO.getCurrency());
                            detail.setCustomsFee(BigDecimal.ZERO);
                            detail.setVatFee(BigDecimal.ZERO);
                            detail.setTransFee(BigDecimal.ZERO);
                            detail.setOtherFee(BigDecimal.ZERO);
                            detail.setCreateUser(userDTO.getUserNo());
                            detail.setCreateTime(new Date());
                            detail.setUpdateUser(userDTO.getUserNo());
                            detail.setUpdateTime(new Date());
                            int insert1 = poInvoiceDetailMapper.insert(detail);


                        }


                    } catch (NullPointerException ex) {
                        log.error(Thread.currentThread().getName() + "->错误1：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误1：" + ex);
                    } catch (Exception ex) {
                        log.error(Thread.currentThread().getName() + "->错误2：" + ex);
                        throw new Exception(Thread.currentThread().getName() + "->错误2：" + ex);
                    }

                    return 1;
                }
            });
            Thread.sleep(100);
            idx = idx + offset;

        }
        log.info("===finish task:" + Thread.currentThread().getName() +
                "耗时(s):" + (System.currentTimeMillis() - statetime) / 1000.0);
        return executor;
    }

    private void insertPoInvocie(StockAdjustDO adjustDO) throws Exception {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDtoForSysUser();
        OpsPoInvoiceDO invoiceDO = new OpsPoInvoiceDO();
        String invoiceId = "";
        if (StringUtils.isBlank(adjustDO.getInvoiceId())) {
            invoiceId = this.generatorBillNo("21");
        } else {
            invoiceId = adjustDO.getInvoiceId();
        }
        if (adjustDO.getPrice() == null) {
            adjustDO.setPrice(BigDecimal.ZERO);
        }
        if (adjustDO.getAmount() == null) {
            adjustDO.setAmount(BigDecimal.ZERO);
        }
        invoiceDO.setInvoiceId(Long.valueOf(invoiceId));
        invoiceDO.setInvoiceNo(adjustDO.getInvoiceNo());
        invoiceDO.setStatus(2);
        invoiceDO.setAmount(adjustDO.getAmount());
        //    <!--add by WuWeiDong 20230302 bug 9847 入库调整写入发票入库时，金额保留2位小数 -->
        invoiceDO.setAmountRmb(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceDO.setAmountadjust(BigDecimal.ZERO);
        invoiceDO.setAmounttotal(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceDO.setCustomsDate(adjustDO.getAdjustDate());
        invoiceDO.setCustomsFee(BigDecimal.ZERO);
        invoiceDO.setVatFee(BigDecimal.ZERO);
        invoiceDO.setTransFee(BigDecimal.ZERO);
        invoiceDO.setOtherFee(BigDecimal.ZERO);
        invoiceDO.setArrivedWarehouseCode(adjustDO.getWarehouseCode());
        invoiceDO.setTotalQty(1);
        invoiceDO.setOrderQty(adjustDO.getQuantity());
        invoiceDO.setBoxQty(1);
        invoiceDO.setImpDate(new Date());
        invoiceDO.setShipDate(new Date());
        invoiceDO.setReceiveTime(new Date());
        invoiceDO.setInvoiceType(7);


        if (StringUtils.isBlank(adjustDO.getSupplierCode())) {
            invoiceDO.setSupplierCode("");
        } else {
            invoiceDO.setSupplierCode(adjustDO.getSupplierCode());
        }
        invoiceDO.setCurrencyCode(adjustDO.getCurrency());
        invoiceDO.setExchangeRate(adjustDO.getExchangeRate());
        LambdaQueryWrapper<OpsPoInvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpsPoInvoiceDO::getInvoiceNo, invoiceDO.getInvoiceNo());
        queryWrapper.orderByDesc(OpsPoInvoiceDO::getCreateTime);
        List<OpsPoInvoiceDO> list = poInvoiceMasterMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            invoiceDO.setCreateUser(userDTO.getUserNo());
            invoiceDO.setCreateTime(new Date());
            invoiceDO.setUpdateTime(new Date());
            invoiceDO.setUpdateUser(userDTO.getUserNo());
            poInvoiceMasterMapper.insert(invoiceDO);
        } else {
            OpsPoInvoiceDO poInvoiceDO = list.get(0);
            poInvoiceDO.setAmount(BigDecimalUtil.add(poInvoiceDO.getAmount(), invoiceDO.getAmount()));
            poInvoiceDO.setAmountRmb(BigDecimalUtil.add(poInvoiceDO.getAmountRmb(), invoiceDO.getAmountRmb()));
            poInvoiceDO.setAmounttotal(BigDecimalUtil.add(poInvoiceDO.getAmounttotal(), invoiceDO.getAmounttotal()));
            invoiceDO.setUpdateTime(new Date());
            invoiceDO.setUpdateUser(userDTO.getUserNo());
            poInvoiceMasterMapper.updateById(poInvoiceDO);
        }
        PoInvoiceDetailDO detail = new PoInvoiceDetailDO();
        detail.setImpDate(new Date());
        detail.setStatus(1);
        detail.setInvoiceNo(adjustDO.getInvoiceNo());
        detail.setInvoiceId(Long.valueOf(invoiceId));
        detail.setOrderNo(adjustDO.getFullOrderNo());
        detail.setModelNo(adjustDO.getModelNo());
        detail.setQuantity(adjustDO.getQuantity());
        detail.setPrice(adjustDO.getPrice());
        detail.setAmount(adjustDO.getAmount());
        detail.setAmountTotal(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        detail.setPriceTotal(BigDecimalUtil.div(detail.getAmountTotal(), new BigDecimal(detail.getQuantity()), 4));
        detail.setPriceRmb(BigDecimalUtil.mul(adjustDO.getPrice(), adjustDO.getExchangeRate()));
        detail.setAmountRmb(BigDecimalUtil.mul(adjustDO.getAmount(), adjustDO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
        detail.setAmountAdjust(BigDecimal.ZERO);
        detail.setImpType(0);
        detail.setRemark(adjustDO.getReason());
        detail.setSupplierCode(adjustDO.getSupplierCode());
        detail.setCurrency(adjustDO.getCurrency());
        detail.setCustomsFee(BigDecimal.ZERO);
        detail.setVatFee(BigDecimal.ZERO);
        detail.setTransFee(BigDecimal.ZERO);
        detail.setOtherFee(BigDecimal.ZERO);
        detail.setCreateUser(userDTO.getUserNo());
        detail.setCreateTime(new Date());
        detail.setUpdateUser(userDTO.getUserNo());
        detail.setUpdateTime(new Date());
        int insert1 = poInvoiceDetailMapper.insert(detail);

        // return insert1 == 1 ? ResultVo.success() : ResultVo.failure();
    }

    private String generatorBillNo(String billType) {

        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<String> billNo = commonServiceFeignApi.generatorBillNo(billType);
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        return billNo.getData();

    }


}
