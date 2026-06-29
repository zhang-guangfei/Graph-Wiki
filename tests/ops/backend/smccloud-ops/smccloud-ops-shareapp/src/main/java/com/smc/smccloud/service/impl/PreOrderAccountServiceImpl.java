package com.smc.smccloud.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.constants.CommonConstants;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.enums.InventoryTypeEnum;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.DateUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.prestock.PreOrderAccountDetailMapper;
import com.smc.smccloud.mapper.prestock.PreOrderAccountMapper;
import com.smc.smccloud.model.OrganizationVO;
import com.smc.smccloud.model.enums.PreOrderDetailStatusEnum;
import com.smc.smccloud.model.inventory.InventoryVO;
import com.smc.smccloud.model.inventory.OpsInventoryPropertyRequestDTO;
import com.smc.smccloud.model.preaccount.PreAccountStatusEnum;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * //
 *
 * @author wuweidong
 * @create 2023/12/7 10:19
 * @description bug 12759   WTSR2023000683-顾客在库&战略在库决算功能
 */
@Slf4j
@Service
public class PreOrderAccountServiceImpl implements PreOrderAccountService {

    private final String REDIS_KEY_PREORDER_CALC_LASTTIME = CommonConstants.OPS_PRESTOCK + "preOrderCalcLastTime";
    private final String REDIS_KEY_INVENTORY_lOG_ARCHIVETIME = CommonConstants.OPS_TIMELINE + "inventoryLogArchiveTime";

    private final String REDIS_KEY_OPS_PRESTOCK_EPRICE = CommonConstants.OPS_PRESTOCK + "eprice:";
    private final String REDIS_KEY_OPS_PRESTOCK_BINDATA = CommonConstants.OPS_PRESTOCK + "bidata:";
    private final String REDIS_KEY_OPS_PRESTOCK_AVGQTY12 = CommonConstants.OPS_PRESTOCK + "avgqty12:";
    private final String REDIS_KEY_OPS_PRESTOCK_RUNING = CommonConstants.OPS_PRESTOCK + "runing";
    private final String REDIS_KEY_OPS_PRESTOCK_COUNTDATE = CommonConstants.OPS_PRESTOCK + "countdate";
    private final String REDIS_KEY_OPS_PRESTOCK_TRANSNO = CommonConstants.OPS_PRESTOCK + "transno";
    private final String REDIS_KEY_OPS_PRESTOCK_STATUS = CommonConstants.OPS_PRESTOCK + "status";
    private final String REDIS_KEY_OPS_PRESTOCK_ERROR = CommonConstants.OPS_PRESTOCK + "error";
    private final String REDIS_KEY_OPS_PRESTOCK_RUNSIZE = CommonConstants.OPS_PRESTOCK + "runsize";




    @Value("${file.base}")
    private String serverPath;
    @Value("${spring.datasource.dynamic.datasource.sharedb.url}")
    private String DATABASE;
    @Value("${spring.datasource.dynamic.datasource.sharedb.username}")
    private String USER;
    @Value("${spring.datasource.dynamic.datasource.sharedb.password}")
    private String PASSWORD;

    @Resource
    private RedisManager redisManager;

    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private PreOrderAccountMapper preOrderAccountMapper;
    @Resource
    private PreOrderAccountDetailMapper preOrderAccountDetailMapper;
    @Resource
    private PreOrderAccountConfigService preOrderAccountConfigService;
    @Resource
    private TransStockFeignApi transStockFeignApi;





    @Override
    public ResultVo<PageInfo<PreOrderAccountDTO>> listPreOrderAccount(PreOrderAccountRequest request) {
        LambdaQueryWrapper<PreOrderAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CollectionUtils.isNotEmpty(request.getDeptNos()), PreOrderAccountDO::getDeptNo, request.getDeptNos())
                .eq(StringUtils.isNotBlank(request.getModelNo()), PreOrderAccountDO::getModelNo, request.getModelNo())
                // .eq(StringUtils.isNotBlank(request.getInventoryTypeCode()), PreOrderAccountDO::getInventoryTypeCode, request.getInventoryTypeCode())
                .eq(StringUtils.isNotBlank(request.getCustomerNo()), PreOrderAccountDO::getCustomerNo, request.getCustomerNo())
                .eq(StringUtils.isNotBlank(request.getPpl()), PreOrderAccountDO::getPpl, request.getPpl())
                .eq(StringUtils.isNotBlank(request.getProjectCode()), PreOrderAccountDO::getProjectCode, request.getProjectCode())
                .eq(StringUtils.isNotBlank(request.getGroupCustomerNo()), PreOrderAccountDO::getGroupCustomerNo, request.getGroupCustomerNo())
                .eq(StringUtils.isNotBlank(request.getCharger()), PreOrderAccountDO::getCharger, request.getCharger())
                .in(CollectionUtils.isNotEmpty(request.getInventoryTypeCodes()), PreOrderAccountDO::getInventoryTypeCode, request.getInventoryTypeCodes())
                .in(CollectionUtils.isNotEmpty(request.getWarehouseCodes()), PreOrderAccountDO::getWarehouseCode, request.getWarehouseCodes());
        if (ObjectUtils.isNotEmpty(request.getBeginDate()) && ObjectUtils.isNotEmpty(request.getBeginDate())) {
            queryWrapper.ge(PreOrderAccountDO::getCreateTime, request.getBeginDate())
                    .le(PreOrderAccountDO::getCreateTime, request.getBeginDate());

        }
        PageHelper.startPage(request.getPageNum(), request.getPageSize());

        List<PreOrderAccountDO> doList = preOrderAccountMapper.selectList(queryWrapper);
        PageInfo<PreOrderAccountDTO> pageInfo = BeanCopyUtil.pageDto2Vo(new PageInfo<>(doList), PreOrderAccountDTO.class);
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            Map<String, OrganizationVO> nameMap = new HashMap<>();
            for (PreOrderAccountDTO item : pageInfo.getList()) {
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNoWithZZ(item.getDeptNo());
                    if(deptNoByHRSalesDeptNo.isSuccess()) {
                        item.setDeptNo(deptNoByHRSalesDeptNo.getData());
                    }
                    OrganizationVO organizationVO = nameMap.getOrDefault(item.getDeptNo(), new OrganizationVO());
                    if (ObjectUtils.isEmpty(organizationVO.getDeptNo())) {
                        organizationVO = this.getDeptName(item.getDeptNo());
                        nameMap.put(item.getDeptNo(), organizationVO);
                    }

                    item.setCompanyNo(organizationVO.getCompanyNo());
                    item.setCompanyName(organizationVO.getCompanyName());
                    item.setParentNo(organizationVO.getParentNo());
                    item.setParentName(organizationVO.getParentName());
                    item.setSalesNo(organizationVO.getSalesNo());
                    item.setSalesName(organizationVO.getSalesName());
                    item.setDeptName(organizationVO.getDeptName());
                }
                item.setInventoryTypeCode(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
                if (item.getIsBin() == null) {
                    item.setBinStr("是");
                } else {
                    item.setBinStr(item.getIsBin() ? "是" : "否");
                }
            }

        }

        return ResultVo.success(pageInfo);
    }

    @Override
    public void exportPreOrderAccount(PreOrderAccountRequest request, HttpServletResponse response) {
        if (request == null) {
            return;
        }
        request.setPageSize(9999999);
        ResultVo<PageInfo<PreOrderAccountDTO>> pageInfoResultVo = this.listPreOrderAccount(request);
        if (pageInfoResultVo == null) {
            return;
        }
        if (CollectionUtils.isEmpty(pageInfoResultVo.getData().getList())) {
            return;
        }
        List<PreOrderAccountExportVO> preOrderAccountExportVOS = BeanCopyUtil.copyList(pageInfoResultVo.getData().getList(), PreOrderAccountExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsInventoryData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), PreOrderAccountExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(preOrderAccountExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }

    }

    @Override
    public ResultVo<PageInfo<PreOrderAccountDetailDTO>> listPreOrderDetail(PreOrderAccountRequest request) {

        if(request == null) {
            return ResultVo.failure("入参不可为空");
        }

        if(StringUtils.isNotBlank(request.getBeginDateStr())) {
            request.setBeginDateStr(request.getBeginDateStr()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(request.getEndDateStr())) {
            request.setEndDateStr(request.getEndDateStr()+" 23:59:59");
        }

        if (StringUtils.isNotBlank(request.getApplyNo())) {
            request.setApplyNo(request.getApplyNo()+"%");
        }

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<PreOrderAccountDetailDTO> list = preOrderAccountDetailMapper.listPreOrderDetail(request);
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, OrganizationVO> nameMap = new HashMap<>();
            for (PreOrderAccountDetailDTO item : list) {
                if (StringUtils.isNotBlank(item.getDeptNo())) {
                    ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNoWithZZ(item.getDeptNo());
                    if(deptNoByHRSalesDeptNo.isSuccess()) {
                        item.setDeptNo(deptNoByHRSalesDeptNo.getData());
                    }
                    OrganizationVO organizationVO = nameMap.getOrDefault(item.getDeptNo(), new OrganizationVO());
                    if (ObjectUtils.isEmpty(organizationVO.getDeptNo())) {
                        organizationVO = this.getDeptName(item.getDeptNo());
                        nameMap.put(item.getDeptNo(), organizationVO);
                    }
                    item.setCompanyNo(organizationVO.getCompanyNo());
                    item.setCompanyName(organizationVO.getCompanyName());
                    item.setParentNo(organizationVO.getParentNo());
                    item.setParentName(organizationVO.getParentName());
                    item.setSalesNo(organizationVO.getSalesNo());
                    item.setSalesName(organizationVO.getSalesName());
                    item.setDeptName(organizationVO.getDeptName());
                }
                item.setStatusName(PreAccountStatusEnum.getNameByCode(item.getStatus()));
                item.setInventoryTypeCode(InventoryTypeEnum.getName(item.getInventoryTypeCode()));
                if (item.getIsBin() == null) {
                    item.setBinStr("是");
                } else {
                    item.setBinStr(item.getIsBin() ? "是" : "否");
                }
                /**
                 * 审批中数
                 * 待审批: 确认决算数 + 确认延期数
                 * 反之 0
                 */
                item.setApproveCountQty(item.getApproveQty());

            }
        }

        return ResultVo.success(PageInfo.of(list));
    }

    @Override
    public void exportPreOrderDetail(PreOrderAccountRequest request, HttpServletResponse response) {
        if (request == null) {
            return;
        }
        request.setPageSize(9999999);
        ResultVo<PageInfo<PreOrderAccountDetailDTO>> pageInfoResultVo = this.listPreOrderDetail(request);
        if (pageInfoResultVo == null) {
            return;
        }
        if (CollectionUtils.isEmpty(pageInfoResultVo.getData().getList())) {
            return;
        }
        List<PreOrderAccountDetailExportVO> preOrderAccountExportVOS = BeanCopyUtil.copyList(pageInfoResultVo.getData().getList(), PreOrderAccountDetailExportVO.class);
        try {
            String fileName = URLEncoder.encode("opsInventoryData", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;utf-8;filename="+fileName+".xlxs");
            response.setHeader("Access-Control-Expose-Headres","Content-Disposition");
            InputStream inputStream = new ClassPathResource(CommonConstants.others_data_export_excel).getInputStream();

            EasyExcel.write(response.getOutputStream(), PreOrderAccountDetailExportVO.class)
                    .withTemplate(inputStream)
                    .sheet("Sheet1").doWrite(preOrderAccountExportVOS);
        } catch (IOException e) {
            response.reset();
            throw new RuntimeException("导出数据发生异常: "+e.getMessage());
        }
    }

    @Override
    public ResultVo<String> handleTransferByAuto() {
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Integer.parseInt(PreOrderDetailStatusEnum.processing.getCode()));
        statusList.add(Integer.parseInt(PreOrderDetailStatusEnum.delay.getCode()));

        LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountDetailDO::getStatus, PreOrderDetailStatusEnum.processing.getCode())
                .or(i -> i.eq(PreOrderAccountDetailDO::getStatus, PreOrderDetailStatusEnum.delay.getCode())
                        .le(PreOrderAccountDetailDO::getDelayDate, DateUtil.getCurrentDate()));

        List<PreOrderAccountDetailDO> detailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(detailDOS)) {
            return ResultVo.success("没有需要处理数据。");
        }
        this.handleTransfer(detailDOS);
        return ResultVo.success("处理完成！");
    }

    /**
     * 调库规则：
     * ①0≤现有效在库数＜确认决算数 ，则按 清算数=现有效在库数；现有效在库数≥确认决算数，则清算数=确认决算数
     * ②按所在库房同仓进行调库，调入同仓物流中心 TY
     * ③决算调库号：JS开头
     * 清算调库成功后/库存已消耗完（无有效在库），决算处理后，决算状态变更”已清算“，有调库数据的（0＜调库数）并追加调库号，可以在决算查询中查询，也可以在库调库查询中查询
     * 确认延期数更新进子项详细基础数据中” 延期中数“ 字段里，可在决算查询中查询
     *
     * @param ids
     * @return
     */
    @Override
    public ResultVo<String> handleTransferByIds(List<Long> ids) {
        try {
            if (CollectionUtils.isEmpty(ids)) {
                return ResultVo.failure("输入处理数据。");
            }
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Integer.parseInt(PreOrderDetailStatusEnum.processing.getCode()));
            statusList.add(Integer.parseInt(PreOrderDetailStatusEnum.delay.getCode()));

            LambdaQueryWrapper<PreOrderAccountDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(PreOrderAccountDetailDO::getId, ids)
                    .in(PreOrderAccountDetailDO::getStatus, statusList);
            List<PreOrderAccountDetailDO> detailDOS = preOrderAccountDetailMapper.selectList(queryWrapper);
            if (CollectionUtils.isEmpty(detailDOS)) {
                return ResultVo.success("没有需要处理数据。");
            }
            this.handleTransfer(detailDOS);
            return ResultVo.success("处理完成！");
        } catch (Exception e) {
            log.error("先行在库决算处理失败-->:".concat(e.getMessage()));
            return ResultVo.failure("先行在库决算处理失败-->:".concat(e.getMessage()));
        }

    }

    /**
     * 传入决算调库数据进行调库。
     *
     * @param detailDOS
     */
    private void handleTransfer(List<PreOrderAccountDetailDO> detailDOS) {
        /**
         * 1) 申请决算>0  && 申请决算>申请延迟数
         * 1) 申请延迟数>0  && 申请延日期>=当时日期
         */

        List<PreOrderAccountDetailDO> transDetail = new ArrayList<>();
        //inventoryId集合，查询是否有库存
        List<Long> inventoryIds = transDetail.stream().map(PreOrderAccountDetailDO::getInventoryId).collect(Collectors.toList());
        OpsInventoryPropertyRequestDTO requestDTO = new OpsInventoryPropertyRequestDTO();
        requestDTO.setInventoryIds(inventoryIds);
        requestDTO.setPageSize(1);
        requestDTO.setPageSize(inventoryIds.size());
        ResultVo<PageInfo<InventoryVO>> resultVo = inventoryServiceFeignApi.listInventoryByProperty(requestDTO);
        if (!resultVo.isSuccess()) {
            throw new BusinessException(resultVo.getMessage());

        }
        if ((ObjectUtils.isEmpty(resultVo.getData()) ||
                (ObjectUtils.isNotEmpty(resultVo.getData()) && CollectionUtils.isEmpty(resultVo.getData().getList())))) {

            throw new BusinessException("查询库存错误,无数据。");
        }
        // 在库数>预约数，有有效库数
        List<InventoryVO> inventoryVOS = resultVo.getData().getList().stream()
                .filter(i -> i.getQuantity() > i.getPrepareQuantity()).collect(Collectors.toList());

        for (InventoryVO vo : inventoryVOS) {
            List<PreOrderAccountDetailDO> transList = detailDOS.stream().filter(i -> i.getInventoryId().compareTo(vo.getId()) == 0).collect(Collectors.toList());
            Integer onHandQty = Optional.ofNullable(vo.getQuantity()).orElse(0) - Optional.ofNullable(vo.getPrepareQuantity()).orElse(0);
            if (onHandQty.compareTo(0) == 1) {
                this.createTransfer(transList, onHandQty);
            }
        }
    }

    @Override
    public ResultVo<String> updatePreOrderAccountFromSales(PreOrderAccountDetailDTO detailDTO) {
        log.info("门户-更新决算数据 params = {}", JSONObject.toJSONString(detailDTO));
        if (ObjectUtils.isEmpty(detailDTO.getApproveCountQty())) {
            detailDTO.setApproveCountQty(0);
        }
        if (ObjectUtils.isEmpty(detailDTO.getApproveDelayQty())) {
            detailDTO.setApproveDelayQty(0);
        }
//        if (detailDTO.getApproveCountQty().compareTo(0) == 0 && detailDTO.getApproveDelayQty().compareTo(0) == 0) {
//            log.info("请输入确认决算数或延期数。");
//            return ResultVo.failure("请输入确认决算数或延期数。");
//        }

        if (detailDTO.getApproveDelayQty().compareTo(0) == 1 && ObjectUtils.isEmpty(detailDTO.getDelayDate())) {
            log.info("请设置延期日期。");
            return ResultVo.failure("请请设置延期日期。");
        }

        PreOrderAccountDetailDO detailDO = new PreOrderAccountDetailDO();
        if (ObjectUtils.isNotEmpty(detailDTO.getId())) {
            detailDO = preOrderAccountDetailMapper.selectById(detailDTO.getId());
        } else {
            LambdaQueryWrapper<PreOrderAccountDetailDO> detailQueryWrapper = new LambdaQueryWrapper<>();
            detailQueryWrapper.eq(PreOrderAccountDetailDO::getInventoryId, detailDTO.getInventoryId())
                    .eq(PreOrderAccountDetailDO::getOrderNo, detailDTO.getOrderNo())
                    .eq(PreOrderAccountDetailDO::getRoDate, detailDO.getRoDate());
            detailDO = preOrderAccountDetailMapper.selectOne(detailQueryWrapper);
        }
        if (detailDO.getStatus() != Integer.parseInt(PreOrderDetailStatusEnum.approving.getCode())) {
            log.info("只有待审批中才能处理，请确认确状态。");
            return ResultVo.failure("只有待审批中才能处理，请确认确状态。");
        }
        LambdaQueryWrapper<PreOrderAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountDO::getInventoryId, detailDO.getInventoryId());
        PreOrderAccountDO accountDO = preOrderAccountMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(accountDO)) {
            return ResultVo.failure("数据有问题，请联系IT。");
        }
        // Integer pushQty = detailDO.getPushQty() - Optional.ofNullable(detailDO.getClearQty()).orElse(0);
        Integer pushQty = 0;
        //延期处理
        if (detailDTO.getApproveDelayQty().compareTo(0) == 1) {
            //确认延期数量：默认值0，可填写范围：0≤确认延期数量≤决算数-确认决算数

            if (detailDTO.getApproveDelayQty().compareTo(pushQty) == 1 || detailDTO.getApproveDelayQty().compareTo(0) == -1) {
                log.info("延期数量错误，0≤延期数量≤决算数-确认决算数范围，请确认。");
                return ResultVo.failure("延期数量错误，0≤延期数量≤决算数-确认决算数范围，请确认。");
            }
            if (ObjectUtils.isEmpty(detailDTO.getDelayDate())) {
                log.info("请设定延迟日期");
                return ResultVo.failure("请设定延迟日期");
            }
            PreOrderAccountRequest request = new PreOrderAccountRequest();
            // request.setInventoryTypeCode(accountDO.getInventoryTypeCode());
            request.setCustomerNo(accountDO.getCustomerNo());
            request.setPpl(accountDO.getPpl());
            request.setProjectCode(accountDO.getProjectCode());
            request.setGroupCustomerNo(accountDO.getGroupCustomerNo());
            ResultVo<List<PreOrderAccountConfigDTO>> resultVo = preOrderAccountConfigService.getPreOrderAccountConfig(request);
            Integer delayMaxDay = 0;
            if (resultVo.isSuccess() && ObjectUtils.isNotEmpty(resultVo.getData())) {

                PreOrderAccountConfigDTO configDTO = resultVo.getData().get(0);
                if (!configDTO.getIsDelay()) {
                    log.info("不允许延期。");
                    return ResultVo.failure("不允许延期。");
                }
                delayMaxDay = configDTO.getDelayMaxDay();
            }

            if (delayMaxDay.compareTo(0) <= 0) {
                /**
                 * 1）顾客在库 2个月 60天
                 * 2）战略在库 6个月 180天
                 */
                if (accountDO.getInventoryTypeCode().startsWith("GK")) {
                    delayMaxDay = 60;
                } else {
                    delayMaxDay = 180;
                }
            }
            Long diffDay = DateUtil.getDiffDay(detailDO.getRequirementDate(), detailDO.getRoDate());
            delayMaxDay = delayMaxDay + diffDay.intValue();
            /***
             * 确认延期日期：
             * ①入库日期＞需求日期，则 需求日期＜可填写日期范围≤delayMaxDay+(入库日期-需求日期）
             * ②入库日期≤需求日期，则 需求日期＜可填写日期范围≤delayMaxDay
             */
            Date maxDate = DateUtil.addDay(detailDO.getRequirementDate(), delayMaxDay);
            if (detailDTO.getDelayDate().compareTo(detailDO.getRequirementDate()) < 0
                    || detailDTO.getDelayDate().compareTo(maxDate) > 0) {
                String message = "延期不在要求范围[".concat(DateUtil.dateToDateString(detailDO.getRequirementDate()))
                        .concat("~").concat(DateUtil.dateToDateString(maxDate).concat("]"));
                log.info(message);
                return ResultVo.failure(message);
            }
        }

        Integer appCountQty = pushQty - detailDTO.getApproveDelayQty();
        if (detailDTO.getApproveCountQty().compareTo(0) == 1) {
            appCountQty = detailDTO.getApproveCountQty();
        }
        //单条单号同时有多种状态判断决算状态：显示状态最小值，
        String newStatus = PreOrderDetailStatusEnum.processing.getCode();
        if (appCountQty.compareTo(0) <= 0 && detailDTO.getApproveDelayQty().compareTo(0) == 1) {
            newStatus = PreOrderDetailStatusEnum.delay.getCode();
        }

        String modifier = detailDTO.getModifier();
        if (StringUtils.isBlank(modifier)) {
            modifier = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
        }
        LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PreOrderAccountDetailDO::getId, detailDO.getId())
                .eq(PreOrderAccountDetailDO::getStatus, PreOrderDetailStatusEnum.editing.getCode())
                .set(PreOrderAccountDetailDO::getStatus, newStatus)
                .set(PreOrderAccountDetailDO::getModifier, modifier)
                .set(PreOrderAccountDetailDO::getModifyTime, DateUtil.getNow());

        int count = preOrderAccountDetailMapper.update(null, updateWrapper);
        if (count >= 1) {
            log.info("处理成功！");
            return ResultVo.success("处理成功！");
        } else {
            log.info("处理失败！可能状态已发变化了。");
            return ResultVo.success("处理失败！可能状态已发变化了。");
        }
    }

    /**
     * 生成调拨数据
     * 1)0≤现有效在库数＜确认决算数 ，则按 清算数=现有效在库数；现有效在库数≥确认决算数，则清算数=确认决算数
     * 2)单条单号同时有多种状态判断决算状态：显示状态最小值，
     *
     * @param transList
     * @param onHandQty
     */
    private void createTransfer(List<PreOrderAccountDetailDO> transList, Integer onHandQty) {
        if (CollectionUtils.isEmpty(transList)) {
            return;
        }
        Long inventoryId = transList.get(0).getInventoryId();
        LambdaQueryWrapper<PreOrderAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreOrderAccountDO::getInventoryId, inventoryId);
        PreOrderAccountDO accountDO = preOrderAccountMapper.selectOne(queryWrapper);
        ResultVo<String> resultVo = commonServiceFeignApi.getWarehouseParentCode(accountDO.getWarehouseCode());
        if (!resultVo.isSuccess()) {
            String error = "取物流中心错误：".concat(resultVo.getMessage());
            log.error(error);
            throw new BusinessException(error);
        }
        String toWarehouse = accountDO.getWarehouseCode();
        if (StringUtils.isNotBlank(resultVo.getData())) {
            toWarehouse = resultVo.getData();
        }
        String redisKey = REDIS_KEY_OPS_PRESTOCK_TRANSNO;
        String keyTransNo = String.join("~", accountDO.getWarehouseCode(), "no");
        String keyItemNo = String.join("~", accountDO.getWarehouseCode(), "item");
        String keyTime = String.join("~", accountDO.getWarehouseCode(), "Time");
        Object objTransNo = redisManager.hGet(redisKey, keyTransNo);
        String transNo = (String) objTransNo;
        Integer itemNo = 0;
        if (StringUtils.isEmpty(transNo)) {

            transNo = commonServiceFeignApi.generatorBillNo("34").getData();
            itemNo = 0;
            redisManager.hPut(redisKey, keyTransNo, transNo);
            redisManager.hPut(redisKey, keyItemNo, itemNo);
            redisManager.hPut(redisKey, keyTime, DateUtil.getCurrentDate());
        } else {
            Object objTime = redisManager.hGet(redisKey, keyTime);
            Date time = (Date) objTime;
            if (ObjectUtils.isEmpty(time) || time.before(DateUtil.getCurrentDate())) {
                transNo = commonServiceFeignApi.generatorBillNo("34").getData();
                itemNo = 0;
                redisManager.hPut(redisKey, keyTransNo, transNo);
                redisManager.hPut(redisKey, keyItemNo, itemNo);
                redisManager.hPut(redisKey, keyTime, DateUtil.getCurrentDate());
            } else {
                Object objItem = redisManager.hGet(redisKey, keyItemNo);
                itemNo = (Integer) objItem;
            }
        }

        itemNo = Optional.ofNullable(itemNo).orElse(0) + 1;

        Integer leftQty = onHandQty;
        List<PreOrderAccountDetailDO> newTransList = new ArrayList<>(transList.size());
        // Integer clearQtyTotal = Optional.ofNullable(accountDO.getClearQty()).orElse(0);
        Integer clearQtyTotal = 0;
        Integer currClearQtyTotal = 0;
        Date transTime = DateUtil.getNow();
        for (PreOrderAccountDetailDO accountDetailDO : transList) {
            PreOrderAccountDetailDO newDetailDo = new PreOrderAccountDetailDO();
            // Integer clearQty = Optional.ofNullable(accountDetailDO.getClearQty()).orElse(0);
            Integer clearQty = 0;
            // Integer currClearQty = Optional.ofNullable(accountDetailDO.getApproveCountQty()).orElse(0) - Optional.ofNullable(accountDetailDO.getClearQty()).orElse(0);
            Integer currClearQty = 0;
            if (leftQty.compareTo(0) <= 0) {
                currClearQty = 0;
            } else if (clearQty.compareTo(leftQty) == 1) {
                currClearQty = leftQty;
            }
            String newCountStatus = PreOrderDetailStatusEnum.transfer.getCode();
            Integer delayQty = Optional.ofNullable(accountDetailDO.getDelayQty()).orElse(0);
            Date delayDate = Optional.ofNullable(accountDetailDO.getDelayDate()).orElse(DateUtil.getCurrentDate());
            Boolean updateDelay = false;
            if (delayQty.compareTo(0) == 1) {
                if (delayDate.compareTo(DateUtil.getCurrentDate()) <= 0) {
                    delayQty = 0;
                    currClearQty = currClearQty + delayQty;
                    updateDelay = true;

                } else {
                    newCountStatus = PreOrderDetailStatusEnum.delay.getCode();
                }
            }

            newDetailDo.setId(accountDetailDO.getId());
            newDetailDo.setInventoryId(accountDetailDO.getInventoryId());
            newDetailDo.setStatus(Integer.parseInt(newCountStatus));
            // newDetailDo.setClearQty(currClearQty);
            newDetailDo.setTransQty(currClearQty);
            newDetailDo.setTransNo(transNo);
            newDetailDo.setTransTime(transTime);
            newDetailDo.setModifier(SMCApp.getLoginAuthDtoForSysUser().getUserNo());
            newDetailDo.setModifyTime(DateUtil.getNow());
            if (updateDelay) {
                newDetailDo.setDelayQty(delayQty);
            }
            clearQtyTotal = clearQtyTotal + currClearQty;
            currClearQtyTotal = currClearQtyTotal + currClearQty;
            newTransList.add(newDetailDo);
        }


        TransOrderVO transDto = new TransOrderVO();
        transDto.setTransType(1);
        transDto.setTransNo(transNo);
        transDto.setItemNo(itemNo);
        transDto.setModelNo(accountDO.getModelNo());
        transDto.setQuantity(currClearQtyTotal);
        transDto.setStatus(0);
        transDto.setFromNo(inventoryId.toString());

        transDto.setFromType(3);// 3:先行在库,4:委托在库
        transDto.setWmsDlvDate(transTime);
        // 调出

        transDto.setFromInventoryPropertyId(accountDO.getInventoryPropertyId());
        transDto.setFromInventoryTypeCode(accountDO.getInventoryTypeCode());
        transDto.setFromWarehouseCode(accountDO.getWarehouseCode());
        transDto.setFromCustomerNo(accountDO.getCustomerNo());
        transDto.setFromPpl(accountDO.getPpl());
        transDto.setFromProjectCode(accountDO.getProjectCode());
        transDto.setFromGroupCustomerNo(accountDO.getGroupCustomerNo());

        // 调入
        // 备库库存属性ID Bug-9942

        transDto.setToInventoryPropertyId(1l);
        transDto.setToWarehouseCode(toWarehouse);
        transDto.setToInventoryTypeCode("TY");
        transDto.setToCustomerNo("");

        transDto.setToPpl("");
        transDto.setToProjectCode("");
        transDto.setToGroupCustomerNo("");

        log.info("先行在库-调拨处理 {} data  {}", inventoryId, JSON.toJSONString(transDto));
        List<TransOrderVO> transDtoList = new ArrayList<>();
        transDtoList.add(transDto);

        ResultVo<String> transResult = transStockFeignApi.transStock(transDtoList);
        log.info("先行在库-调拨处理 {} 响应 = {}", transDtoList.get(0).getFromNo(), JSON.toJSONString(transResult));
        if (!transResult.isSuccess()) {
            throw new RuntimeException("调库执行异常-" + transResult.getMessage());
        }
        //保存项号
        redisManager.hPut(redisKey, keyItemNo, itemNo);
        this.handleClearQuantity(newTransList);

    }

    private void handleClearQuantity(List<PreOrderAccountDetailDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("没有传入数据。");
        }

        for (PreOrderAccountDetailDO detailDO : list) {
            LambdaUpdateWrapper<PreOrderAccountDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(PreOrderAccountDetailDO::getId, detailDO.getId())
                    .eq(PreOrderAccountDetailDO::getStatus, PreOrderDetailStatusEnum.processing.getCode())
                    .set(PreOrderAccountDetailDO::getStatus, detailDO.getStatus())
                    .set(PreOrderAccountDetailDO::getTransNo, detailDO.getTransNo())
                    .set(PreOrderAccountDetailDO::getTransTime, detailDO.getTransTime())
                    .set(PreOrderAccountDetailDO::getModifier, detailDO.getModifier())
                    .set(PreOrderAccountDetailDO::getModifyTime, detailDO.getModifyTime());
            if (ObjectUtils.isNotEmpty(detailDO.getDelayQty())) {
                updateWrapper.set(PreOrderAccountDetailDO::getDelayQty, detailDO.getDelayQty());
            }
            preOrderAccountDetailMapper.update(null, updateWrapper);
        }

        preOrderAccountMapper.updateClearDelayQty(list.get(0).getInventoryId());


    }

    private OrganizationVO getDeptName(String code) {
        OrganizationVO organizationVO = opsCommonService.getHrOrganMasterInfoByCode(code).getData();
        return organizationVO;
    }


}

