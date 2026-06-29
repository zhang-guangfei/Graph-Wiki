package com.smc.smccloud;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.sales.ops.dto.inventory.InventoryForProducChangeDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.enums.OrderTypeEnum;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.ExcelHelper;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyDetailMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyDetailViewMapper;
import com.smc.smccloud.mapper.stockassembly.StockAssemblyMapper;
import com.smc.smccloud.model.enums.StockAssemblyAssemblyTypeEnum;
import com.smc.smccloud.model.enums.StockAssemblyDetailStatusEnum;
import com.smc.smccloud.model.stockassembly.*;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.CommonServiceFeignApi;
import com.smc.smccloud.service.ImpdataAdjustService;
import com.smc.smccloud.service.StockAssemblyService;
import com.smc.smccloud.service.TransStockFeignApi;
import com.smc.smccloud.service.impl.StockAssemblyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2021-10-28 08:45
 * Description:
 */
@Slf4j
public class StockAssemblyServiceTests extends OpsShareAppApplicationTests {

    @Resource
    private StockAssemblyMapper stockAssemblyMapper;
    @Resource
    private StockAssemblyDetailMapper stockAssemblyDetailMapper;
    @Resource
    private StockAssemblyService stockAssemblyService;

    @Resource
    private StockAssemblyDetailViewMapper stockAssemblyDetailViewMapper;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;

    @Resource
    private ImpdataAdjustService impdataAdjustService;

    @Resource
    private TransStockFeignApi transStockFeignApi;

    @Resource
    private StockAssemblyServiceImpl stockAssemblyServiceImpl;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }

    @Test
    public void stringTest() {
        String str = "1~KGZ~~~~";
        String[] arr = str.split("~", -1);
        log.info("==> result size = " + arr.length + ", array = " + Arrays.deepToString(arr));
    }

    @Test
    public void saveStockAssemblyApplyTest() {
        StockAssemblyApplyDTO applyDto = new StockAssemblyApplyDTO();
        applyDto.setAssembleType("3");
        applyDto.setApplyType("2");
        applyDto.setDlvDate(DateUtil.stringToDate("2021-11-30"));
        applyDto.setRemark("申请备注");

        List<StockAssemblyItemDTO> outItems = new ArrayList<>();
        List<StockAssemblyItemDTO> inItems = new ArrayList<>();

        StockAssemblyItemDTO itemDto1 = new StockAssemblyItemDTO();
        itemDto1.setModelNo("AXT661-85A");
        itemDto1.setIsTransOut(true);
        itemDto1.setQuantity(-20d);
        itemDto1.setInventoryType("TY");
        itemDto1.setWarehouseCode("K90");

        outItems.add(itemDto1);
        applyDto.setOutItems(outItems);

        StockAssemblyItemDTO itemDto2 = new StockAssemblyItemDTO();
        itemDto2.setModelNo("AXT661-85A");
        itemDto2.setIsTransOut(false);
        itemDto2.setQuantity(20d);
        itemDto2.setWarehouseCode("K90");
        itemDto2.setInventoryType("");
        itemDto2.setCustomerNo("GZ000");

        inItems.add(itemDto2);
        applyDto.setInItems(inItems);
        log.info("{}", stockAssemblyService.saveStockAssembly(applyDto));
    }

   /*@Test
    public void InventoryForOrderTest() {
        InventoryForOrderInputDto inputDto = new InventoryForOrderInputDto();
        List<String> list = new ArrayList<>();
        list.add("*AXT100-DS25-050");
        list.add("*AXT100-DS25-050");
        list.add("*EX600-DXPF");
        list.add("*KQ2P-04");
        inputDto.setModelno(list);
        //inputDto.setInventoryTypeCode("TY");
        // inputDto.setInventoryStatus("N");
        inputDto.setWarehouseCode("K11");
        CommonResult<List<InventoryForOrderOutDto>> result = opsWmDispatchForOrderFeignApi.InventoryNormalForOrder(inputDto);
        log.info("InventoryForOrderTest result code = " + result.getCode() + ", message = " + result.getMessage());
        List<InventoryForOrderOutDto> outDtoList = result.getData();
        log.info("InventoryForOrderTest outDtoList size = " + outDtoList.size());
        for (InventoryForOrderOutDto outDto : outDtoList) {
            log.info("===> InventoryForOrderOutDto { "
                    + "inventoryStatus = " + outDto.getInventoryStatus()
                    + ", warehouseCode = " + outDto.getWarehouseCode()
                    + ", modelno = " + outDto.getModelno()
                    + ", inventoryTypeCode = " + outDto.getInventoryTypeCode()
                    + ", customerNo = " + outDto.getCustomerNo()
                    + ", ppl = " + outDto.getPpl()
                    + ", projectCode = " + outDto.getProjectCode()
                    + ", groupCustomerNo = " + outDto.getGroupCustomerNo()
                    + ", po = " + outDto.getOrderno()
                    + ", qty = " + outDto.getQty()
                    + ", preqty = " + outDto.getPreqty() + "}");
        }
    }*/

    @Test
    public void handleTransferTest() {
        SMCApp.setForSysUser();
        log.info("result = {}", stockAssemblyService.handleTransfer(null, null));
    }

    @Test
    public void handleAssemblyTest() {
        SMCApp.setForSysUser();
        log.info("result = {}", stockAssemblyService.handleAssembly(null, null));
    }

    @Test
    public void listStockAssemblyTest() {
        StockAssemblyRequest request = new StockAssemblyRequest();
        request.setFromDate(DateUtil.stringToDate("2022-03-14"));
        request.setToDate(DateUtil.stringToDate("2022-03-16"));
        log.info("result = {}", stockAssemblyService.listStockAssembly(request));
        ;
    }

    @Test
    public void listStockAssemblyDetailTest() {
        stockAssemblyDetailRequest request = new stockAssemblyDetailRequest();
        request.setModelNo("MYH40-02WR-A");
        ResultVo<PageInfo<StockAssemblyDetailView>> result = stockAssemblyService.listStockAssemblyDetail(request);
        log.info("result = {}", result);
    }

    @Test
    public void exportPreStockDetailTest() {
        stockAssemblyDetailRequest request = new stockAssemblyDetailRequest();
//        request.setAssembleType("3");
//        request.setStatus("6");
        request.setFromDate(DateUtil.stringToDate("2023-06-01 00:00"));
        request.setToDate(DateUtil.addDay(request.getFromDate(), 100));
        ExcelUtil excelUtil = stockAssemblyService.exportStockAssemblyDetail(request);
        try {
            excelUtil.save("D:\\test\\组换调库申请项清单.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createApplyNoTest() {
        log.info("commonServiceFeignApi.generatorBillNo result = {}", commonServiceFeignApi.generatorBillNo("14"));
    }

    @Test
    public void inventoryKeysTest() {
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(StockAssemblyDetailDO::getId, StockAssemblyDetailDO::getInventoryKeys);
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQuery);
        String[] inventoryKeys;
        String inventoryKey;
        LambdaUpdateWrapper<StockAssemblyDetailDO> updateWrapper;
        int count = 0;
        for (StockAssemblyDetailDO detail : detailList) {
            inventoryKeys = detail.getInventoryKeys().split("~", -1);
            inventoryKey = spliceInventoryKeys(inventoryKeys);
            if (!inventoryKey.equals(detail.getInventoryKeys())) {
                updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(StockAssemblyDetailDO::getInventoryKeys, inventoryKey);
                updateWrapper.eq(StockAssemblyDetailDO::getId, detail.getId());
                count += stockAssemblyDetailMapper.update(new StockAssemblyDetailDO(), updateWrapper);
            }
        }
        System.out.println("===> update count = " + count);
    }

    /**
     * 按"库存类型~仓库代码~客户代码~客户群号~PPL~项目号"的格式拼接InventoryKeys
     */
    private String spliceInventoryKeys(String[] inventoryKeys) {
        String inventoryKey = inventoryKeys[0] + "~" + inventoryKeys[1] + "~";
        if (inventoryKeys[0].startsWith("GK")) {
            inventoryKey += inventoryKeys[2];
        }
        inventoryKey += "~";
        if (inventoryKeys[0].endsWith("JT")) {
            inventoryKey += inventoryKeys[3];
        }
        inventoryKey += "~";
        if (inventoryKeys[0].endsWith("PPL")) {
            inventoryKey += inventoryKeys[4];
        }
        inventoryKey += "~";
        if (inventoryKeys[0].endsWith("PJ")) {
            inventoryKey += inventoryKeys[5];
        }
        return inventoryKey;
    }

    @Test
    public void checkTransOutStockTest() {

    }

    @Test
    public void applyNoTest() {
        String no = commonServiceFeignApi.generatorBillNo("15").getData();
        if (StringUtils.isBlank(no)) {
            log.error("申请号生成失败");
            return;
        }
        System.out.println("no = " + no);
    }

    @Test
    public void listNoImportCostAssemblyDataTest() {
        log.info("listNoImportCostAssemblyDataTest result = {}", stockAssemblyService.listNoImportCostAssemblyData());
    }

    @Test
    public void handleApplyTest() {
        StockAssemblyHandleDTO dto = new StockAssemblyHandleDTO();
        dto.setApplyIds(Arrays.asList(54709L));
        dto.setHandleType("3");
        log.info("handleApplyTest result = {}", stockAssemblyService.handleApply(dto));
    }


    @Test
    public void importAssemblyCostDataTest() {
//        String applyType = "1"; // 组换申请
//        LambdaQueryWrapper<StockAssemblyDetailView> applyQuery = Wrappers.lambdaQuery();
//        applyQuery.select(StockAssemblyDetailView::getApplyId, StockAssemblyDetailView::getApplyNo,
//                StockAssemblyDetailView::getBillNo);
//        applyQuery.eq(StockAssemblyDetailView::getApplyType, applyType) // 组换申请
//                .eq(StockAssemblyDetailView::getOptCode, StockAssemblyDetailStatusEnum.finished.getCode()) // 已完成组换
//                .ne(StockAssemblyDetailView::getAssembleType, StockAssemblyAssemblyTypeEnum.ASSEMBLY_BUSINESS_ONLY.getCode()) // 排除类型-组换（仅业务）
//                .groupBy(StockAssemblyDetailView::getApplyId, StockAssemblyDetailView::getApplyNo,
//                        StockAssemblyDetailView::getBillNo);
//        List<StockAssemblyDetailView> applyList = stockAssemblyDetailViewMapper.selectList(applyQuery);
//        log.info("applyList = {}", applyList);
//
//        log.info("importAssemblyCostDataTest result = {}", impdataAdjustService.importAssemblyCostData(applyList));
        log.info("sendAssemblyApplyToCost result = {}", stockAssemblyService.sendAssemblyApplyToCost());
    }

    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;

    @Test
    public void getInventoryPropertyIdTest() {
        String[] inventoryKeys = "ZL-PJ~KBJ~~~~FPP01".split("~", -1);
        log.info("inventoryKeys = {}", Arrays.deepToString(inventoryKeys));
        log.info("getInventoryPropertyIdTest = {}", this.getInventoryPropertyId(inventoryKeys));
    }

    @Test
    public void checkApplyDetailTest() {
        long id = 200153L;
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectById(id);
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
        detailQuery.eq(StockAssemblyDetailDO::getApplyId, id);

        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQuery);
      ResultVo<String> result=  stockAssemblyServiceImpl.checkApplyDetail(applyInfo.getApplyType(),detailList);
      log.info("checkApplyDetailTest ==>{}",result);
    }

    @Test
    public void assemTest() {
        StockAssemblyDO applyInfo = stockAssemblyMapper.selectById(52062L);
        LambdaQueryWrapper<StockAssemblyDetailDO> detailQuery = Wrappers.lambdaQuery();
        detailQuery.eq(StockAssemblyDetailDO::getApplyId, 52062L)
                .eq(StockAssemblyDetailDO::getOptCode, 8);
        List<StockAssemblyDetailDO> detailList = stockAssemblyDetailMapper.selectList(detailQuery);

        InventoryForProducChangeDto inputDto = new InventoryForProducChangeDto();
        List<InventoryForAdjustDto> ckList = new ArrayList<>(detailList.size()); // 变更前的型号
        List<InventoryForAdjustDto> rkList = new ArrayList<>(detailList.size()); // 变更后的产出型号
        InventoryForAdjustDto adjustDto;
        String[] inventoryKeys;
        for (StockAssemblyDetailDO detail : detailList) {
            adjustDto = new InventoryForAdjustDto();
            adjustDto.setOrderId(applyInfo.getApplyNo());
            adjustDto.setQaStatus("0");
            adjustDto.setDeptno(applyInfo.getDeptNo());
            adjustDto.setModelno(detail.getModelNo());
            adjustDto.setQty(Math.abs(detail.getQuantity().intValue()));
            adjustDto.setWarehouseCode(detail.getWarehouseCode());

            // 按 库存类型~仓库代码~客户代码~客户群号~PPL~项目号 拼接
            inventoryKeys = detail.getInventoryKeys().split("~", -1);
            if (inventoryKeys[0].startsWith("GK")) {
                adjustDto.setCustomerNo(inventoryKeys[2]);
            }
            if (inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) {
                adjustDto.setGroupCustomerNo(inventoryKeys[3]);
            }
            if (inventoryKeys[0].endsWith("PPL")) {
                adjustDto.setPpl(inventoryKeys[4]);
            }
            if (inventoryKeys[0].endsWith("PJ")) {
                adjustDto.setProjectCode(inventoryKeys[5]);
            }
            if (detail.getIsTransOut()) {
                // 设置组换前型号信息
                ckList.add(adjustDto);
            } else {
                // 设置组换后型号信息
                rkList.add(adjustDto);
            }
        }
        inputDto.setCkList(ckList);
        inputDto.setRkList(rkList);
        log.info("组换调库-组换处理 data = {}", JSON.toJSONString(inputDto));
    }

    /**
     * 查询调库项的InventoryPropertyId
     *
     * @param inventoryKeys 调库申请项信息
     * @return InventoryPropertyId
     */
    private long getInventoryPropertyId(String[] inventoryKeys) {
        if ("TY".equals(inventoryKeys[0])) {
            return 1L;
        }
        OpsInventoryProperty propertyVO = new OpsInventoryProperty();
        propertyVO.setInventoryTypeCode(inventoryKeys[0]);
        if (inventoryKeys[0].startsWith("GK") && StringUtils.isNotBlank(inventoryKeys[2])) {
            propertyVO.setCustomerNo(inventoryKeys[2]);
        }
        if (inventoryKeys[0].endsWith("PPL") && StringUtils.isNotBlank(inventoryKeys[4])) {
            propertyVO.setPpl(inventoryKeys[4]);
        }
        if (inventoryKeys[0].endsWith("PJ") && StringUtils.isNotBlank(inventoryKeys[5])) {
            propertyVO.setProjectCode(inventoryKeys[5]);
        }
        if ((inventoryKeys[0].endsWith("JT") || inventoryKeys[0].endsWith("HY")) && StringUtils.isNotBlank(inventoryKeys[3])) {
            propertyVO.setGroupCustomerNo(inventoryKeys[3]);
        }
        log.info("propertyVO = {}", propertyVO);
        CommonResult checkResult = opsPropertyFeignApi.findProperty(propertyVO);
        if (!checkResult.isSuccess()) {
            throw new BusinessException("调库失败,查询调库项的inventoryPropertyId失败: " + checkResult.getMessage());
        }
        return Long.parseLong(checkResult.getData().toString());
    }

    @Test
    public void insertBatchTest() {
        List<StockAssemblyDetailDO> detailList = new ArrayList<>();
        StockAssemblyDetailDO detail;
        Date now = new Date();
        String optUser = "TEST";
        for (int i = 0; i < 6; i++) {
            detail = new StockAssemblyDetailDO();
            detail.setApplyId(1L);
            detail.setModelNo("TEST_" + i);
            detail.setQuantity((double) i);
            detail.setIsTransOut(Boolean.TRUE);
            detail.setWarehouseCode("KGZ");
            detail.setInventoryKeys("TY" + "~" + "KGZ" + "~" + "" + "~" + ""
                    + "~" + "" + "~" + "");
            detail.setOptCode(0);
            detail.setCreateTime(now);
            detail.setUpdateTime(now);
            detail.setCreateUser(optUser);
            detail.setUpdateUser(optUser);
            detailList.add(detail);
        }
        log.info("insertBatchTest result = {}", stockAssemblyDetailMapper.insertBatch(detailList));
    }

    @Test
    public void listStockAssemblyApplyDetailTest() {
        StockAssemblyRequest request = new StockAssemblyRequest();
        request.setId(375L);
        request.setIsTransOut(Boolean.TRUE);
        request.setPageNum(101);
        request.setPageSize(10);
        log.info("listStockAssemblyDetailTest = {}", stockAssemblyService.listStockAssemblyApplyDetail(request));
    }

    @Test
    public void importApplyDetailTest() {
        File file = new File("D:\\test\\北斗组换申请-2023-1-9更新.xlsx");
        try (FileInputStream inputStream = new FileInputStream(file)) {
            log.info("importApplyDetailTest ==> {}", stockAssemblyService.importApplyDetail(inputStream, 110858L));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateAssemblyStatusTest() {
        LambdaQueryWrapper<StockAssemblyDO> applyQuery = Wrappers.lambdaQuery();
        applyQuery.in(StockAssemblyDO::getApplyNo, Arrays.asList("ZH0000000327", "ZH0000000328", "ZH0000000329", "ZH0000000331"));
        List<StockAssemblyDO> applyList = stockAssemblyMapper.selectList(applyQuery);

        Date now = new Date();

        for (StockAssemblyDO applyInfo : applyList) {
            applyInfo.setStatus("6"); // 已完成
            applyInfo.setAssembleType("5"); // 组换（仅财务）
            applyInfo.setApplyDate(now);
            applyInfo.setApproveDate(now);

            LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdate = Wrappers.lambdaUpdate();
            detailUpdate.set(StockAssemblyDetailDO::getOptCode, 6)
                    .set(StockAssemblyDetailDO::getUpdateTime, now);
            detailUpdate.eq(StockAssemblyDetailDO::getApplyId, applyInfo.getId())
                    .notIn(StockAssemblyDetailDO::getOptCode, Arrays.asList(4, 5, 7, 8, 9));
            stockAssemblyDetailMapper.update(null, detailUpdate);

            stockAssemblyMapper.updateById(applyInfo);
        }
    }

    @Test
    public void importDataTest() {
        String filePath = "D:\\test\\拆分型号.xlsx";

        try {
            String invoiceNo = "KSHSP2022102401";
            StockAssemblyApplyDTO applyInfo = new StockAssemblyApplyDTO();
            applyInfo.setAssembleType("5"); // 组换（仅财务）
            applyInfo.setApplyType("1"); // 组换
            applyInfo.setWarehouseCode("KSH");
            applyInfo.setRemark("发票" + invoiceNo);
            StockAssemblyItemDTO item;

            ExcelHelper excel = new ExcelHelper(new FileInputStream(new File(filePath)));

            Sheet sheet = excel.getSheet();
            int lastRowNum = sheet.getLastRowNum();
            String splitModelNo;
            String splitQty;
            String orderNo;
            List<StockAssemblyItemDTO> inItems = new ArrayList<>(lastRowNum);

            // 调入项
            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                splitModelNo = excel.getCellString(excel.getCell(rowIndex, 0));
                splitQty = excel.getCellString(excel.getCell(rowIndex, 1));
                orderNo = excel.getCellString(excel.getCell(rowIndex, 2));

                log.info("==> splitModelNo={}, splitQty={} ",
                        splitModelNo, splitQty);
                item = new StockAssemblyItemDTO();
                item.setIsTransOut(Boolean.FALSE);
                item.setModelNo(splitModelNo);
                item.setQuantity(Double.valueOf(splitQty));
                item.setWarehouseCode(applyInfo.getWarehouseCode());
                item.setInventoryType("TY");
                item.setRemark(orderNo);

                inItems.add(item);
            }

            System.out.println("================================================");

            // 调出项
            excel.openSheet(1);
            sheet = excel.getSheet();
            lastRowNum = sheet.getLastRowNum();

            String modelNo;
            String quantity;
            List<StockAssemblyItemDTO> outItems = new ArrayList<>(lastRowNum);

            for (int rowIndex = 1; rowIndex <= lastRowNum; rowIndex++) {
                modelNo = excel.getCellString(excel.getCell(rowIndex, 0));
                quantity = excel.getCellString(excel.getCell(rowIndex, 1));
                orderNo = excel.getCellString(excel.getCell(rowIndex, 2));

                log.info("==> modelNo={}, quantity={}, orderNo={} ",
                        modelNo, quantity, orderNo);

                item = new StockAssemblyItemDTO();
                item.setWarehouseCode(applyInfo.getWarehouseCode());
                item.setIsTransOut(Boolean.TRUE);
                item.setModelNo(modelNo);
                item.setQuantity(Double.valueOf(quantity));
                item.setWarehouseCode(applyInfo.getWarehouseCode());
                item.setInventoryType("TY");
                item.setRemark(orderNo);

                outItems.add(item);
            }


            applyInfo.setOutItems(outItems);
            applyInfo.setInItems(inItems);

            log.info("applyInfo = {}", JSON.toJSONString(applyInfo));
            stockAssemblyService.createStockAssemblyApply(applyInfo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getInvoiceNoByOrderNoTest() {
        String applyNo = "ZH0000000437";
        String invoiceNo = impdataAdjustService.getInvoiceNoByOrderNo(applyNo);
        log.info("getInvoiceNoByOrderNoTest {} : {}", applyNo, invoiceNo);
    }

    @Test
    public void wmsTransStockTest() {
        List<TransOrderVO> transDtoList = JSON.parseArray("[{\"fromCustomerNo\":\"B71BL\",\"fromId\":133243,\"fromInventoryPropertyId\":82383,\"fromInventoryTypeCode\":\"GK-TY\",\"fromNo\":\"GTD2303150519\",\"fromType\":2,\"fromWarehouseCode\":\"KBJ\",\"itemNo\":1,\"modelNo\":\"MSQXB20A\",\"quantity\":10,\"status\":0,\"toCustomerNo\":\"B8ZR9\",\"toInventoryPropertyId\":6930,\"toInventoryTypeCode\":\"GK-TY\",\"toWarehouseCode\":\"KBJ\",\"transNo\":\"GTD2303150519\",\"transType\":1}]",
                TransOrderVO.class);
        log.info("组换调库-调拨处理 data = {}", JSON.toJSONString(transDtoList));
        ResultVo<String> transResult = transStockFeignApi.transStock(transDtoList);
        log.info("组换调库-调拨处理  响应 = {}", JSON.toJSONString(transResult));
    }

    @Test
    public void updateAssemblyFinistStatusTest() {
        log.info("updateAssemblyStatusTest = {}", stockAssemblyService.updateAssemblyStatus("ZH0000000837", Boolean.FALSE));
    }

    @Test
    public void stringTest2() {
        String modelNo = " ";
        String regex = "[^A-Za-z0-9]+";
        log.info("modelNo '{}' ==> '{}' ", modelNo, modelNo.replaceAll(regex, ""));
    }

    @Test
    public void EnumTest() {
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.saleOrder;
        log.info("EnumTest equals1 = {}", orderTypeEnum.equals(OrderTypeEnum.saleOrder));
        log.info("EnumTest equals2 = {}", orderTypeEnum.equals(OrderTypeEnum.gnjtOrder));
    }

    @Test
    public void resultTest() {
        ResultVo<String> transResult = JSON.parseObject("{\"code\":\"200\",\"data\":\"{\\\"GTD2303220034-1\\\":\\\"型号为ZK2G10K5ALA-06的可用数量不足\\\"}\",\"message\":\"调拨成功\",\"success\":true}",
                new TypeReference<ResultVo<String>>() {
                });
        boolean result = true;
        StringBuilder msg = new StringBuilder();
        // Add by DengDenghui 2022-11-14 for bug-8650
        Map<String, String> resultMap = JSON.parseObject(transResult.getData(), new TypeReference<Map<String, String>>() {
        });
        for (String value : resultMap.values()) {
            if (StringUtils.isBlank(value) || "成功".equals(value) || value.contains("trans_order已存在")) {
                // result = true;
            } else {
                result = false;
                msg.append(value).append(".");
            }
        }
        log.info("====> result = {}, msg = {}", result, msg.toString());
    }

    @Test
    public void updateTest() {
//        StockAssemblyDO applyInfo = new StockAssemblyDO();
//        applyInfo.setId(193687L);
//        applyInfo.setBillNo("M000001102");
//        stockAssemblyMapper.updateById(applyInfo);

        LambdaUpdateWrapper<StockAssemblyDO> updateWrapper1 = Wrappers.lambdaUpdate();
        updateWrapper1.set(StockAssemblyDO::getBillNo, "M000001112")
                .set(StockAssemblyDO::getTransTime, DateUtil.stringToDateTime("2023-07-28 23:00:00"))
                .set(StockAssemblyDO::getStatus, 6);
        updateWrapper1.in(StockAssemblyDO::getId, 193666L, 193667L);
        stockAssemblyMapper.update(null, updateWrapper1);

        LambdaUpdateWrapper<StockAssemblyDetailDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(StockAssemblyDetailDO::getOptCode, 8)
                .set(StockAssemblyDetailDO::getTransTime, DateUtil.stringToDateTime("2023-07-28 23:00:00"))
                .set(StockAssemblyDetailDO::getInvoiceNo, "M000001112")
                .set(StockAssemblyDetailDO::getRemark, "调平入库ZH0000001536");
        updateWrapper.in(StockAssemblyDetailDO::getApplyId, 193666L, 193667L)
                .in(StockAssemblyDetailDO::getId, 1146735L, 1146741L, 1146742L)
                .eq(StockAssemblyDetailDO::getOptCode, 7);
        stockAssemblyDetailMapper.update(null, updateWrapper);
    }

    @Test
    public void UpdateStockAssemblyDetailTest() {
        Date now = new Date();
        LambdaUpdateWrapper<StockAssemblyDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
        detailUpdateWrapper.set(StockAssemblyDetailDO::getOptCode, 7) // 调库失败
                .set(StockAssemblyDetailDO::getAllowOutQty, 0)
                .set(StockAssemblyDetailDO::getTransTime, now)
                .set(StockAssemblyDetailDO::getRemark, "可用数量不足")
                .set(StockAssemblyDetailDO::getUpdateUser, "test")
                .set(StockAssemblyDetailDO::getUpdateTime, now);
        detailUpdateWrapper.in(StockAssemblyDetailDO::getId, 1311630l, 1311629l);
        stockAssemblyDetailMapper.update(null, detailUpdateWrapper);
    }

    @Test
    public void stockAssemblyDetailTest() {

//        LambdaQueryWrapper<StockAssemblyDetailDO> queryWrapper=new LambdaQueryWrapper<>();
//        queryWrapper.eq(StockAssemblyDetailDO::getApplyId,163249l);
//      List<StockAssemblyDetailDO> detailList=  stockAssemblyDetailMapper.selectList(queryWrapper);
        List<StockAssemblyDetailDO> detailList = new ArrayList<>();
        StockAssemblyDetailDO a1 = new StockAssemblyDetailDO();
        a1.setIsTransOut(false);
        a1.setModelNo("A1");
        detailList.add(a1);
        StockAssemblyDetailDO a2 = new StockAssemblyDetailDO();
        a2.setIsTransOut(false);
        a2.setModelNo("A2");
        detailList.add(a2);
        StockAssemblyDetailDO a3 = new StockAssemblyDetailDO();
        a3.setIsTransOut(false);
        a3.setModelNo("A3");
        detailList.add(a3);

        StockAssemblyDetailDO a4 = new StockAssemblyDetailDO();
        a4.setIsTransOut(true);
        a4.setModelNo("A4");
        detailList.add(a4);
        StockAssemblyDetailDO a5 = new StockAssemblyDetailDO();
        a5.setIsTransOut(true);
        a5.setModelNo("A2");
        detailList.add(a5);
        StockAssemblyDetailDO a6 = new StockAssemblyDetailDO();
        a6.setIsTransOut(true);
        a6.setModelNo("A3");
        detailList.add(a6);

        StockAssemblyDetailDO a7 = new StockAssemblyDetailDO();
        a7.setIsTransOut(true);
        a7.setModelNo("A7");
        detailList.add(a7);
        // 区分调出/调入
        Map<Boolean, List<StockAssemblyDetailDO>> transferTypeMap = detailList.stream()
                .collect(Collectors.partitioningBy(StockAssemblyDetailDO::getIsTransOut));

        Map<Integer, List<StockAssemblyDetailDO>> itemMap = new HashMap<>(transferTypeMap.get(Boolean.FALSE).size());
        List<StockAssemblyDetailDO> items;
        int itemNo = 0;
        // 唯一约束
        List<Long> idSet = new ArrayList<>(transferTypeMap.get(Boolean.FALSE).size());

        for (StockAssemblyDetailDO outItem : transferTypeMap.get(Boolean.TRUE)) {
            for (StockAssemblyDetailDO inItem : transferTypeMap.get(Boolean.FALSE)) {
                if (idSet.contains(inItem.getId())) {
                    continue;
                }
                log.info("in:{},out:{}", inItem.getModelNo(), outItem.getModelNo());
                if (outItem.getModelNo().equals(inItem.getModelNo()) && outItem.getQuantity() + inItem.getQuantity() == 0) {
                    itemNo++;
                    items = new ArrayList<>(2);
                    items.add(outItem); // index-0 调出
                    items.add(inItem); // index-1 调入
                    itemMap.put(itemNo, items);
                    idSet.add(inItem.getId());
                    break;
                }
            }
        }
    }
}
