package com.smc.smccloud;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.ChineseUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.ExcelUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.prestock.PreStockApplyMapper;
import com.smc.smccloud.mapper.prestock.PreStockDetailMapper;
import com.smc.smccloud.mapper.prestock.PreStockResultMapper;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.inventory.InventoryRequestDTO;
import com.smc.smccloud.model.orderlog.OrderLogVO;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.service.*;
import com.smc.smccloud.service.impl.PreStockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-01-12 13:41
 * Description:
 */
@Slf4j
public class PreStockServiceTests extends OpsShareAppApplicationTests {

    @Resource
    private PreStockService preStockService;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private DictDataServiceFeignApi dictDataServiceFeignApi;
    @Resource
    private OrderLogFeignApi orderLogFeignApi;
    @Resource
    private PreStockResultMapper preStockResultMapper;
    @Resource
    private SampleOrderApplyService sampleOrderApplyService;
    @Resource
    private PreStockServiceImpl preStockServiceImpl;

    @Resource
    private PreStockApplyMapper preStockApplyMapper;
    @Resource
    private PreStockDetailMapper preStockDetailMapper;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;

    @Before
    public void before() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        SMCApp.setForSysUser();
    }


//    @Test
//    public  void updateDataByHand(){
//        PreStockDetailDO  detailDO=new PreStockDetailDO();
//        detailDO.setId(193551L);
//        detailDO.setStatus("6");
//        detailDO.setPoQty(1000);
//        preStockDetailMapper.updateById(detailDO);
//    }

    @Test
    public void listPreStockApplyTest() {
        PreStockApplyRequest request = new PreStockApplyRequest();
        //request.setApplyNo("PT00000104");
        request.setApplyPsn("张");
        log.info("result = {}", preStockService.listPreStockApply(request));
    }

    @Test
    public void listPreStockResultTest() {
        ResultVo<String> resultVo = sampleOrderApplyService.autoOrderCarryTurn();
        System.out.println("resultVo = " + resultVo.toString());
    }

    @Test
    public void autoHandlePreStockApplyTest() {
        PreStockApplyHandleDTO dto = new PreStockApplyHandleDTO();
        dto.setApplyIds(Arrays.asList(7237L));
        dto.setDetailIds(Arrays.asList(200011L));
        log.info("result = {}", preStockService.autoHandlePreStockApply(dto));
    }

    @Test
    public void createPreStockApplyNoTest() {
        log.info("result = {}", commonServiceFeignApi.generatorBillNo("12").getData());
    }

    @Test
    public void CreateRequestPurcaseOrderNoTest() {
        log.info("result = {}", commonServiceFeignApi.generatorBillNo("3").getData());
    }

//    @Test
//    public void createTransferProcessResultTest() {
//        ResultVo<String> handleResult = preStockService.autoHandlePreStockApply(1665L, null);
//        log.info("handleResult = {}", handleResult);
//    }

    @Test
    public void getWareCodeConfigTest() {
        System.out.println("=====================================================");
        PreStockApplyDO applyInfo = preStockApplyMapper.selectById(6544L);
        System.out.println("applyInfo = " + applyInfo);
        System.out.println("=====================================================");
        ResultVo<List<String>> warehouseConfig = preStockService.getWarehouseConfig(applyInfo);
        System.out.println("warehouseConfig = " + warehouseConfig.getData());
        System.out.println("=====================================================");
    }


    @Test
    public void createMoveProcessResultTest() {
         PreStockApplyDO applyInfo = preStockApplyMapper.selectById(6616L);

        //PreStockApplyDO applyInfo = preStockApplyMapper.selectById(6611L);
        if (Objects.isNull(applyInfo.getTransFlag())) {
            applyInfo.setTransFlag(false);
        }
        PreStockDetailDO detail = preStockDetailMapper.selectById(193784l);

        //PreStockDetailDO detail = preStockDetailMapper.selectById(193774l);

        List<PreStockResultDTO> list = preStockServiceImpl.createMoveProcessResult(applyInfo, detail);
        list.forEach(i -> System.out.println("---> " + i));
        if (!CollectionUtils.isEmpty(list)) {
            preStockServiceImpl.createMoverOrder(applyInfo, detail, list);
            log.info("createMoverOrder  -->生成预占完成");
        } else{
            log.info("createMoverOrder  -->无预占数据");
        }



    }

    @Test
    public void createPurchaseProcessResultTest(){
        PreStockApplyDO applyInfo = preStockApplyMapper.selectById(5780L);
        if (Objects.isNull(applyInfo.getTransFlag())) {
            applyInfo.setTransFlag(false);
        }
        PreStockDetailDO detail = preStockDetailMapper.selectById(184956l);

        List<PreStockResultDTO> list = preStockServiceImpl.createPurchaseProcessResult(applyInfo, detail);
        list.forEach(i -> System.out.println("---> " + i));
    }
    @Test
    public void getApplyModelInventoryInfosTest() {

        String modelNo = "KQ2U10-00A";
        List<String> warehouseConfig = Arrays.asList("KBJ", "KSH", "KGZ");
        log.info("modelNo = {}, warehouseConfig = {}", modelNo, warehouseConfig);

        List<PreModelStockInfo> inventoryInfoResult = preStockService.getPreModelInventoryInfo(modelNo, warehouseConfig);
        inventoryInfoResult.forEach(i -> System.out.println("---> " + i));
    }

    @Test
    public void listIdxTest() {
        List<String> warehouseConfig = Arrays.asList("KBJ", "KSH", "KGZ");
        log.info("KSH = {}", warehouseConfig.indexOf("KSH"));
        log.info("KGZ = {}", warehouseConfig.indexOf("KGZ"));

        log.info("KK = {}", warehouseConfig.indexOf("KK"));

        List<String> warehouseConfig2 = new ArrayList<>();
        log.info("KGZ2 = {}", warehouseConfig2.indexOf("KGZ"));
    }



    @Test
    public void shikomiEmailTest() {
        String classCode = "9004";
        ResultVo<List<DataCodeVO>> mailResult = dictDataServiceFeignApi.getDataCodes(classCode);
        log.info("mailResult = {}", mailResult);
    }


    @Test
    public void shikomiEmailTest22() {
        // SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        OrderLogVO orderLogVO = new OrderLogVO();
        orderLogVO.setOrderNo("xxxx");
        orderLogVO.setIp("1212");
        orderLogVO.setOptType(1);
        ResultVo<String> integerResultVo = orderLogFeignApi.addOrderLog(orderLogVO);
        System.out.println("integerResultVo = " + integerResultVo.toString());
    }

    @Test
    public void applyDetailRemarkTest() {
        LambdaQueryWrapper<PreStockResultDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreStockResultDO::getApplyId, 1580L);
        List<PreStockResultDO> resultList = preStockResultMapper.selectList(queryWrapper);
        resultList.forEach(r -> System.out.println("==> " + r));
        // 处理
        Map<String, List<String>> remarkMap = new HashMap<>();
        List<String> remarkList = null;
        for (PreStockResultDO result : resultList) {
            String[] rmk = result.getRemark().split(";");
            for (String str : rmk) {
                String[] s = str.split("-");
                if (remarkMap.containsKey(s[0])) {
                    remarkList.add(s[1]);
                } else {
                    remarkList = new ArrayList<>();
                    if (result.getModelNo().equals(result.getApplyModelNo())) {
                        remarkList.add(s[1]);
                    } else {
                        remarkList.add("拆分型号" + result.getModelNo() + " " + s[1]);
                    }
                }
                remarkMap.put(s[0], remarkList);
            }
        }
        for (Map.Entry<String, List<String>> map : remarkMap.entrySet()) {
            System.out.println("==> " + map.getKey() + " : " + String.join(",", map.getValue()));
        }
    }

    @Test
    public void exportShikomiFileTest() {
        ResultVo<String> resultVo = preStockService.exportShikomiFile();
        System.out.println("result = " + resultVo);
    }

    @Test
    public void setSupplierCodeTest() {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);

        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getApplyId, PreStockDetailDO::getModelNo);
        detailQuery.eq(PreStockDetailDO::getStatus, 5)
                .and(a -> a.eq(PreStockDetailDO::getSupplierCode, "").or().isNull(PreStockDetailDO::getSupplierCode))
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);

        LambdaUpdateWrapper<PreStockDetailDO> updateWrapper;
        Map<String, String> supplierMap = new HashMap<>(detailList.size());
        ResultVo<String> supplierResult;
        String supplierCode;

        for (PreStockDetailDO detail : detailList) {
            if (supplierMap.containsKey(detail.getModelNo())) {
                supplierCode = supplierMap.get(detail.getModelNo());
            } else {
                // 根据型号查询供应商
                supplierResult = productServiceFeignApi.getSupplierNoByModelNo(detail.getModelNo());
                if (!supplierResult.isSuccess()) {
                    log.error("申请型号供应商信息获取失败: modelNo = {},  {}", detail.getModelNo(), supplierResult);
                    throw new BusinessException("申请型号的供应商信息获取失败: " + supplierResult.getMessage());
                }
                supplierCode = supplierResult.getData();
                supplierMap.put(detail.getModelNo(), supplierCode);
            }

            updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(PreStockDetailDO::getSupplierCode, supplierCode);
            updateWrapper.eq(PreStockDetailDO::getId, detail.getId());
            preStockDetailMapper.update(null, updateWrapper);
        }
    }

    @Test
    public void getPreStockResultTest() {
//        ResultVo<PreStockResultVO> result = preStockService.getPreStockResult(1535L, "CDS1BN200-300-DNY2199");
        ResultVo<PreStockResultVO> result = preStockService.getPreStockResult(375L, 5042L);
        System.out.println("getPreStockResultTest result = " + result);
    }

    @Test
    public void listPreStockDetailTest() {
        PreStockDetailRequest request = new PreStockDetailRequest();
        request.setApplyNo("ZK2308250004");
        log.info("listPreStockDetailTest result ===> {}", preStockService.listPreStockDetail(request));
    }

    @Test
    public void exportPreStockDetailTest() {
        PreStockDetailRequest request = new PreStockDetailRequest();
        request.setApplyType("1");
        request.setApplyStatus(Arrays.asList("3", "6"));
        ExcelUtil excelUtil = preStockService.exportPreStockDetail(request);
        try {
            excelUtil.save("D:\\test\\先行在库申请项清单.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getPreStockApplyTest() {
        log.info("getPreStockApplyTest result = {}", preStockService.getPreStockApply(143L, "84c55e00f4654726a996b2add16ae0bc"));
    }

    @Test
    public void handleShikomiApplyInfoTest() {
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getCustomerNo, PreStockApplyDO::getEnname);
        applyQuery.eq(PreStockApplyDO::getApplyType, 2);
        List<PreStockApplyDO> applyList = preStockApplyMapper.selectList(applyQuery);

        ResultVo<String> customerEnName;
        for (PreStockApplyDO applyInfo : applyList) {
            if (StringUtils.isBlank(applyInfo.getEnname())) {
                customerEnName = commonServiceFeignApi.getCustomerENameByNo(applyInfo.getCustomerNo());
                log.info("commonServiceFeignApi.getCustomerENameByNo {} == {}", applyInfo.getCustomerNo(), customerEnName);
                if (customerEnName.isSuccess()) {
                    applyInfo.setEnname(customerEnName.getData());
                    preStockApplyMapper.updateById(applyInfo);
                }
            }
        }
    }

    @Test
    public void autoCreateProcessItemTest() {
        System.out.println("=====================================================");
        PreStockApplyDO applyInfo = preStockApplyMapper.selectById(1871);
        System.out.println("applyInfo = " + applyInfo);
        System.out.println("=====================================================");
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getApplyId, 1871)
                .eq(PreStockDetailDO::getId, 76763)
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        System.out.println("=====================================================");
        ResultVo<List<String>> warehouseConfig = preStockService.getWarehouseConfig(applyInfo);
        System.out.println("warehouseConfig = " + warehouseConfig.getData());
        System.out.println("=====================================================");

        boolean canTransfer;
        Map<String, List<PreModelStockInfo>> stockInfoMap = new HashMap<>();
        List<PreStockResultDTO> resultList;
        for (PreStockDetailDO detail : detailList) {
            detail.setStockQty(0);
            detail.setPoQty(0);
            detail.setStatus("2");
            detail.setInterceptId(null);
            detail.setProcessText("");
            canTransfer = preStockService.checkTransfer(applyInfo, detail, warehouseConfig.getData());
            if (canTransfer && !stockInfoMap.containsKey(detail.getModelNo())) {
                stockInfoMap.put(detail.getModelNo(), preStockService.getPreModelInventoryInfo(detail.getModelNo(), warehouseConfig.getData()));
            }
            System.out.println("stockInfo = " + stockInfoMap.get(detail.getModelNo()));

            resultList = preStockService.autoCreateProcessItem(applyInfo, detail, canTransfer, stockInfoMap.get(detail.getModelNo()));

            List<PreStockResultDTO> transList = new ArrayList<>();
            List<PreStockResultDTO> moveList = new ArrayList<>();
            List<PreStockResultDTO> purchaseList = new ArrayList<>();
            for (PreStockResultDTO result : resultList) {
                System.out.println("result = " + result);
                if ("1".equals(result.getProcessType()) || "2".equals(result.getProcessType())) {
                    purchaseList.add(result);
                }
                if ("4".equals(result.getProcessType()) || "5".equals(result.getProcessType())) {
                    transList.add(result);
                }
            }
            log.info(">>>>>>>>> transList = {}", transList);
            log.info(">>>>>>>>> purchaseList = {}", purchaseList);
            log.info(">>>>>>>>> detail = {}", detail);

            log.info("===== filterInterceptedProcessingItems ==> {}", preStockServiceImpl.filterInterceptedProcessingItems(applyInfo, detail, transList, moveList, purchaseList, true));
            log.info("<<<<<<<<< transList = {}", transList);
            log.info("<<<<<<<<< purchaseList = {}", purchaseList);
            log.info("<<<<<<<<< detail = {}", detail);

        }
        System.out.println("=====================================================");
    }

   /* @Test
    public void exportCNshikomiTest() {
        long applyId = 151L;

        PreStockApplyDO applyInfo = preStockApplyMapper.selectById(applyId);
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getApplyId, applyId)
                .eq(PreStockDetailDO::getSupplierCode, "CM")
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);

        ExcelHelper excel = new ExcelHelper(FileUtil.getTemplate("template/仕込依頼書.xlsx"));

        preStockService.createCNShikomiApplyFile(excel, applyInfo, detailList,
                "SHM2206100001-01",
                "广州艾德包装机械有限公司",
                 "番禺所");
        try {
            excel.save("D:\\test\\仕込依頼書SHM2206100001-01.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @Test
    public void exportJPShikomiFileTest() {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getStatus, 5)
                .eq(PreStockDetailDO::getSupplierCode, "JP")
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            System.out.println("可导出数据为空");
        }
        DataCodeVO mailInfo = new DataCodeVO();
        mailInfo.setCode("JP");
        mailInfo.setExtNote1("edp07@smcgz.com.cn;");

        ResultVo<String> resultVo = preStockService.exportJPShikomiFile(detailList, mailInfo);
        System.out.println("exportJPShikomiFileTest result = " + resultVo);
    }

    @Test
    public void exportCNShikomiFileTest() {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getStatus, 5)
                .ne(PreStockDetailDO::getSupplierCode, "JP")
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            System.out.println("可导出数据为空");
        }
        Map<String, List<PreStockDetailDO>> shikomiDataMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getSupplierCode));
        ResultVo<List<DataCodeVO>> mailResult = dictDataServiceFeignApi.getDataCodes("9004");
        Map<String, List<DataCodeVO>> mailMap = mailResult.getData().stream().collect(Collectors.groupingBy(DataCodeVO::getCode));

        for (Map.Entry<String, List<PreStockDetailDO>> map : shikomiDataMap.entrySet()) {
            ResultVo<String> resultVo = preStockService.exportJPShikomiFile(map.getValue(), mailMap.get(map.getKey()).get(0));
            System.out.println("exportJPShikomiFileTest result = " + resultVo);
        }
    }

    @Resource
    private RedisManager redisManager;

    @Test
    public void redisTest() {
        System.out.println("result = " + redisManager.get("ops:order:orderSales:saved:" + "HDD2206090001" + ":" + "FZD2206090002"));
    }

    @Test
    public void pinYinTest() {
        String name = "卞璐";
        System.out.println("name = " + name + " >>> pinYin = " + PinyinUtil.getPinyin(name, ""));

        String name2 = "李鑫";
        System.out.println("name2 = " + name2 + " >>> pinYin = " + PinyinUtil.getPinyin(name2, ""));
    }

    @Test
    public void initOrderNosTest() {
        LambdaQueryWrapper<PreStockResultDO> resultQuery = new LambdaQueryWrapper<>();
        resultQuery.select(PreStockResultDO::getFromId, PreStockResultDO::getOrderNo);
        resultQuery.isNotNull(PreStockResultDO::getOrderNo);
        List<PreStockResultDO> resultDOList = preStockResultMapper.selectList(resultQuery);

        Map<Long, List<PreStockResultDO>> resultMap = resultDOList.stream().collect(Collectors.groupingBy(PreStockResultDO::getFromId));

        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper;
        StringBuilder orderNos;
        for (Map.Entry<Long, List<PreStockResultDO>> entry : resultMap.entrySet()) {
            orderNos = new StringBuilder();
            for (PreStockResultDO result : entry.getValue()) {
                orderNos.append(result.getOrderNo()).append(";");
            }

            detailUpdateWrapper = new LambdaUpdateWrapper<>();
            detailUpdateWrapper.set(PreStockDetailDO::getOrderNos, orderNos.toString());
            detailUpdateWrapper.eq(PreStockDetailDO::getId, entry.getKey());
            preStockDetailMapper.update(null, detailUpdateWrapper);
        }
    }

    @Test
    public void initPoQtyOrStockQtyTest() {
        QueryWrapper<PreStockDetailDO> applyQuery = new QueryWrapper<>();
        applyQuery.select("distinct apply_id");
        applyQuery.lambda().like(PreStockDetailDO::getOrderNos, "ZK2208220017-1;ZK2208220017-2")
                .ne(PreStockDetailDO::getStatus, "9");
        List<PreStockDetailDO> detailDOList = preStockDetailMapper.selectList(applyQuery);

        LambdaQueryWrapper<PreStockResultDO> resultQuery = Wrappers.lambdaQuery();
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdate = Wrappers.lambdaUpdate();
        ;
        List<PreStockResultDO> resultList;
        Map<Long, List<PreStockResultDO>> resultMap;

        for (PreStockDetailDO detail : detailDOList) {
            resultQuery.clear();
            resultQuery.select(PreStockResultDO::getApplyId, PreStockResultDO::getFromId, PreStockResultDO::getOrderQty,
                    PreStockResultDO::getProcessType, PreStockResultDO::getOrderNo);
            resultQuery.eq(PreStockResultDO::getApplyId, detail.getApplyId())
                    .eq(PreStockResultDO::getOptStatus, 2)
                    .in(PreStockResultDO::getProcessType, 1, 2, 4, 5); // 处理类型 采购 or 调库
            resultList = preStockResultMapper.selectList(resultQuery);
            if (CollectionUtils.isEmpty(resultList)) {
                continue;
            }

            resultMap = resultList.stream().collect(Collectors.groupingBy(PreStockResultDO::getFromId));

            for (Map.Entry<Long, List<PreStockResultDO>> entry : resultMap.entrySet()) {
                int stockQty = 0;
                int poQty = 0;
                StringBuilder orderNos = new StringBuilder();
                for (PreStockResultDO result : entry.getValue()) {
                    if ("1".equals(result.getProcessType()) || "2".equals(result.getProcessType())) {
                        poQty += result.getOrderQty();
                    }
                    if ("4".equals(result.getProcessType()) || "5".equals(result.getProcessType())) {
                        stockQty += result.getOrderQty();
                    }
                    orderNos.append(result.getOrderNo()).append(";");
                }

                if (poQty + stockQty > 0) {
                    detailUpdate.clear();
                    detailUpdate.set(PreStockDetailDO::getStockQty, stockQty)
                            .set(PreStockDetailDO::getPoQty, poQty)
                            .set(PreStockDetailDO::getOrderNos, orderNos.toString()); // 处理数量: 采购数量 or 调库数量
                    detailUpdate.eq(PreStockDetailDO::getId, entry.getKey());
                    preStockDetailMapper.update(null, detailUpdate);
                }
            }
        }
    }

    @Test
    public void updateQtyBinToBinDataTest() {
        preStockService.updateQtyBinToBinData(380L);
        log.info("updateQtyBinToBinDataTest ");
    }

    @Test
    public void opsReturnPurchaseNoTest() {
        String applyNo = "ZK2211160018"; // ZK2207210022/434 FK2207210003/431  WT2207160001/401
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getItemNo, PreStockDetailDO::getOrderNos);
        detailQuery.eq(PreStockDetailDO::getApplyId, 1622L)
                .eq(PreStockDetailDO::getReplyStatus, 1)
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);

        preStockService.callbackPurchaseNoToPortal(applyNo, detailList);
    }

    @Test
    public void autoCallbackPortalTest() {
        log.info("autoCallbackPortalTest = {}", preStockService.autoCallbackPortal());
    }

    @Test
    public void callBackResultToPortalByBatchNoTest() {
        String batchNo = "";
        log.info("callBackResultToPortalByBatchNoTest applyNo={}, {}", preStockService.callBackResultToPortalByBatchNo(batchNo));
    }

    @Test
    public void initDetailStockTypeTest() {
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getStockType, PreStockApplyDO::getPplNo,
                PreStockApplyDO::getProjectNo, PreStockApplyDO::getGroupCustomerNo);
        applyQuery.ne(PreStockApplyDO::getApplyType, 2);
        List<PreStockApplyDO> applyList = preStockApplyMapper.selectList(applyQuery);

        LambdaQueryWrapper<PreStockDetailDO> detailQuery;
        List<PreStockDetailDO> detailList;
        for (PreStockApplyDO applyInfo : applyList) {
            detailQuery = new LambdaQueryWrapper<>();
            detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getPplNo, PreStockDetailDO::getProjectNo);
            detailQuery.eq(PreStockDetailDO::getApplyId, applyInfo.getId())
                    .eq(PreStockDetailDO::getDelFlag, 0);
            detailList = preStockDetailMapper.selectList(detailQuery);

            for (PreStockDetailDO detail : detailList) {
                detail.setStockType(applyInfo.getStockType());
                if (StringUtils.isBlank(detail.getPplNo()) && StringUtils.isNotBlank(applyInfo.getPplNo())) {
                    detail.setPplNo(applyInfo.getPplNo());
                }
                if (StringUtils.isBlank(applyInfo.getProjectNo()) && StringUtils.isNotBlank(applyInfo.getProjectNo())) {
                    detail.setProjectNo(applyInfo.getProjectNo());
                }
                detail.setGroupCustomerNo(applyInfo.getGroupCustomerNo());

                if (StringUtils.isNotBlank(detail.getPplNo()) && !detail.getStockType().endsWith("PPL")) {
                    detail.setStockType(InventoryTypeEnum.GK_PPL.getCode());
                }
                if (StringUtils.isNotBlank(detail.getProjectNo()) && !detail.getStockType().endsWith("PJ")) {
                    detail.setStockType(InventoryTypeEnum.GK_PJ.getCode());
                }
                if (StringUtils.isNotBlank(detail.getGroupCustomerNo()) && !detail.getStockType().startsWith("ZL")) {
                    detail.setStockType(InventoryTypeEnum.ZL_JT.getCode());
                }
                preStockDetailMapper.updateById(detail);

            }
        }
    }

    @Test
    public void purchaseOrderCancelHandleTest() {
//        String[] orderNos = new String[]{
//                "8800004842-1", "8800004843-1"
//        };
//        for (String orderNo : orderNos) {
//            preStockService.purchaseOrderCancelHandle(orderNo);
//        }

        preStockService.purchaseOrderCancelHandle("8800006587-1");
    }

    @Test
    public void getProcessItemByDetailIdTest() {
        List<Long> detailIds = Arrays.asList(
                184056L,
                184057L,
                184058L,
                184059L);
        for (Long detailId : detailIds) {
            ResultVo<List<PreStockResultDTO>> resultVo = preStockService.getProcessItemByDetailId(detailId);
            log.info("getProcessItemByDetailIdTest = {}", JSON.toJSONString(resultVo.getData()));
            for (PreStockResultDTO resultDTO : resultVo.getData()) {
                resultDTO.setTransType("1"); // 空运
            }
            log.info("handleProcessItemTest = {}", preStockService.handleProcessItem(resultVo.getData()));
        }
    }

    @Test
    public void handleProcessItemTest() {
        List<PreStockResultDTO> processItemList = JSON.parseArray("[{\"applyId\":3535,\"applyModelNo\":\"MXZ12L-5\",\"dlvDateJp\":1684252800000,\"fromId\":127893,\"inventoryType\":\"GK-TY\",\"itemNo\":29,\"modelNo\":\"MXZ12L-5\",\"optStatus\":\"1\",\"orderQty\":130,\"orderStock\":\"JP\",\"processType\":\"9\",\"productTag\":\"9\",\"remark\":\"采购130;\",\"specMark\":\"0\",\"toWarehouseCode\":\"SCZ\",\"transType\":\"0\"}]",
                PreStockResultDTO.class);
        log.info("handleProcessItemTest = {}", preStockService.handleProcessItem(processItemList));
    }

    @Test
    public void listPreStockApplyDetailTest() {
        PreStockApplyRequest request = new PreStockApplyRequest();
        request.setApplyId(388L);
        request.setApplyType("3");
        request.setPageNum(1);
        request.setPageSize(10);
        log.info("listPreStockApplyDetailTest ==> {}", preStockService.listPreStockApplyDetail(request));
    }

    @Test
    public void test33() {
        String s1 = "(";
        String s2 = "（";
        log.info("'{}'.equest('{}') = {}", s1, s2, s1.equals(s2));
    }

    @Test
    public void getLastRequestPurchaseOrderTest() {
        OpsRequestpurchase lastRequestPurchaseOrder = preStockResultMapper.getLastRequestPurchaseOrder("FK2301160008");
        log.info("getLastRequestPurchaseOrderTest = {}", JSON.toJSONString(lastRequestPurchaseOrder));
        log.info("getRequesttime Date = " + DateUtil.getDate(lastRequestPurchaseOrder.getRequesttime()));
    }

    @Test
    public void updateData() {
//        preStockResultMapper.deleteById(147563);

//        PreStockDetailDO detailDO = new PreStockDetailDO();
//        detailDO.setId(6870L);
//        detailDO.setStatus("6");
//        preStockDetailMapper.updateById(detailDO);

//        PreStockApplyDO applyDO = new PreStockApplyDO();
//        applyDO.setId(4243L);
//        applyDO.setStatus("6");
//        preStockApplyMapper.updateById(applyDO);

//        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
//        detailUpdateWrapper
//                .set(PreStockDetailDO::getSupplierCode, "JP");
//        detailUpdateWrapper.eq(PreStockDetailDO::getApplyId, 5227L)
//                .eq(PreStockDetailDO::getStatus, 5)
//                .eq(PreStockDetailDO::getSupplierCode, "CM");
//        preStockDetailMapper.update(null, detailUpdateWrapper);


//        long applyId = 3685L;
//        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
//        detailQuery.eq(PreStockDetailDO::getApplyId, applyId)
//                .notIn(PreStockDetailDO::getStatus, 4, 6, 9)
//                .eq(PreStockDetailDO::getDelFlag, 0);
//        int unProcessedCount = preStockDetailMapper.selectCount(detailQuery);
//        if (unProcessedCount == 0) {
//            int applyStatus =  3; // 5-已确认（shikomi）else 3-待处理
//            int applyUpdateStatus = 6; // 已处理
//            LambdaUpdateWrapper<PreStockApplyDO> applyUpdateWrapper = new LambdaUpdateWrapper<>();
//            applyUpdateWrapper.set(PreStockApplyDO::getStatus, applyUpdateStatus)
//                    .set(PreStockApplyDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
//            applyUpdateWrapper.eq(PreStockApplyDO::getId, applyId)
//                    .eq(PreStockApplyDO::getStatus, applyStatus);
//            preStockApplyMapper.update(new PreStockApplyDO(), applyUpdateWrapper);
//        }
    }

    @Test
    public void interceptErrorTest() {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = Wrappers.lambdaQuery();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getApplyId,
                PreStockDetailDO::getStatus, PreStockDetailDO::getPoQty, PreStockDetailDO::getStockQty,
                PreStockDetailDO::getInterceptId, PreStockDetailDO::getProcessText);
        detailQuery.gt(PreStockDetailDO::getUpdateTime, DateUtil.stringToDate("2023-02-21"))
                .isNotNull(PreStockDetailDO::getInterceptId)
                .eq(PreStockDetailDO::getStatus, 6)
                .last("and ISNULL(stock_qty,0) + ISNULL(po_qty,0)=0");
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        log.info("==> detailList.size = {}", detailList.size());


        // 按申请id分组
        Map<Long, List<PreStockDetailDO>> applyIdMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getApplyId));

        List<String> applyNoList = new ArrayList<>(applyIdMap.keySet().size());
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = Wrappers.lambdaQuery();
        PreStockApplyDO applyInfo;
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdate = Wrappers.lambdaUpdate();
        LambdaUpdateWrapper<PreStockApplyDO> applyUpdate = Wrappers.lambdaUpdate();
        Date now = new Date();

        for (Map.Entry<Long, List<PreStockDetailDO>> map : applyIdMap.entrySet()) {

            applyQuery.clear();
            applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo, PreStockApplyDO::getStatus);
            applyQuery.eq(PreStockApplyDO::getId, map.getKey());
            applyInfo = preStockApplyMapper.selectOne(applyQuery);

            List<Long> detailIdList = map.getValue().stream().map(PreStockDetailDO::getId).collect(Collectors.toList());

            detailUpdate.clear();
            detailUpdate.set(PreStockDetailDO::getStatus, 2)
                    .set(PreStockDetailDO::getInterceptId, null)
                    .set(PreStockDetailDO::getProcessText, null)
                    .set(PreStockDetailDO::getUpdateTime, now)
                    .set(PreStockDetailDO::getUpdateUser, "B98075");
            detailUpdate.in(PreStockDetailDO::getId, detailIdList);
            preStockDetailMapper.update(null, detailUpdate);

            applyUpdate.clear();
            applyUpdate.set(PreStockApplyDO::getStatus, 3);
            applyUpdate.eq(PreStockApplyDO::getId, applyInfo.getId())
                    .eq(PreStockApplyDO::getStatus, 6);
            preStockApplyMapper.update(null, applyUpdate);

            applyNoList.add(applyInfo.getApplyNo());
        }
        log.info("===> applyNoList size = {}, values = {}", applyNoList.size(), applyNoList);
        log.info("===> updateTime = {}", DateUtil.dateToDateTimeString(now));
    }

    @Test
    public void csTest() {
        String[] englishPunctuation = {"!", ",", ".", ";", "<", ">", "(", ")", "?",
                "{", "}", "\"", ":", "{", "}", "\"", "\'", "\'", "哈", "1", "。"};
        String engStr = StringUtils.join(englishPunctuation, "");
        System.out.println("containsChinese = " + ChineseUtil.containsChinese(engStr));
        System.out.println("containsChinesePunctuation = " + ChineseUtil.containsChinesePunctuation(engStr));

        System.out.println("==============================================");
        String[] chinesePunctuation = {"！", "，", "。", "；", "《", "》", "（", "）", "？",
                "｛", "｝", "“", "：", "【", "】", "”", "‘", "’", "啊", "a", "&", "9"};
        String chnStr = StringUtils.join(chinesePunctuation, "");
        System.out.println("containsChinese = " + ChineseUtil.containsChinese(chnStr));
        System.out.println("containsChinesePunctuation = " + ChineseUtil.containsChinesePunctuation(chnStr));

    }

    private String convertStr(String str) {
        return ChineseUtil.chinesePunctuationToEnglish(Convert.toDBC(str.replaceAll("[\u4e00-\u9fa5]", "")));
    }

    @Test
    public void orderQtyTest() {
        log.info("newPoQty = {}", this.calcCorrectPoQty(26, 10, true));
    }

    private int calcCorrectPoQty(int poQty, int minPackUnit, boolean isEven) { // bug-10503
        int unPackQty = poQty % minPackUnit;
        if (unPackQty != 0) {
            poQty = poQty + minPackUnit - unPackQty; // 加上余数 -> 最小包装数的倍数
        }
        if (isEven && checkParity(poQty) == 1) {
            poQty += minPackUnit;
        }
        return poQty;
    }

    /**
     * 判断奇偶性
     *
     * @param number 数字
     * @return 偶数-0; 奇数-1
     */
    private int checkParity(int number) {
        return number & 0x1;
    }

    @Test
    public void listPreStockApplyDetailForBaseInfoTest() {
        PreStockApplyRequest request = new PreStockApplyRequest();
        request.setApplyId(5429l);
        request.setApplyType("1");
        request.setStockType("ZL-JT");
        request.setCustomerNo("C38TH");
        request.setUserNo("");
        request.setProjectNo("");
        request.setPplNo("");
        request.setGroupCustomerNo("000XD");
        request.setWarehouseCode("SCZ");
        ResultVo<PageInfo<PreStockDetailDTO>> resultVo = preStockServiceImpl.listPreStockApplyDetailForBaseInfo(request);

        System.out.println("测试完成");

//        applyId: this.applyForm.id,
//                applyType: this.applyForm.applyType,
//                pageNum: this.pageNum,
//                pageSize: this.pageSize
    }

    @Autowired
    private DictCommonService dictCommonService;

    @Test
    public void getmasterWarhouseTest() {
        String dd = dictCommonService.getMasterWarehouseByCode("WCH016VM");
        System.out.println(dd);
    }

    @Autowired
    private InventoryServiceFeignApi inventoryServiceFeignApi;

    @Test
    public void listInventorySummaryByPropertyIdTest() {
        // SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setModelNos(Arrays.asList("SY3A00-5UD1-NA", "SY30M-1-1A-C8-NA", "SY30M-3-1A-C8-NA", "EX260-SPN1", "EX260-SPR5", "EX260-SPR1", "SY30M-2-1DA-C6-NA"));
        dto.setPropertyId(552L);
        dto.setWarehouseCode("KSH");
        System.out.println("listCustomerBinModelInventoryTest result = " + inventoryServiceFeignApi.listInventorySummaryByPropertyId(dto));
        // ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
    }

}
