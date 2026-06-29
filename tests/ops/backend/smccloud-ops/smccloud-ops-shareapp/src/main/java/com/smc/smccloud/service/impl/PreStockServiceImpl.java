package com.smc.smccloud.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsRequestpurchase;
import com.sales.ops.db.entity.ProductBomDetail;
import com.sales.ops.dto.common.BomSelectParam;
import com.sales.ops.dto.common.BomSelectResult;
import com.sales.ops.dto.order.TransOrderDtoForMove;
import com.sales.ops.dto.order.TransOrderQueryMoveParam;
import com.sales.ops.dto.order.TransOrderQueryMoveResult;
import com.sales.ops.dto.purchase.RequestPurchaseInfo;
import com.sales.ops.dto.purchase.RequestPurchaseInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.enums.WMPurchaseTagEnum;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.sales.ops.enums.common.BomSelectEnum;
import com.sales.ops.feign.OpsPropertyFeignApi;
import com.sales.ops.feign.OpsWmDispatchForOrderFeignApi;
import com.sales.ops.feign.PrepareOrderFeignApi;
import com.sales.ops.feign.RequestPurchaseFeignApi;
import com.sales.ops.feign.product.BomSelectorFeignApi;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.constants.GlobalConstant;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.model.enums.*;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.TransOrderMapper;
import com.smc.smccloud.mapper.prestock.PreStockApplyMapper;
import com.smc.smccloud.mapper.prestock.PreStockDetailMapper;
import com.smc.smccloud.mapper.prestock.PreStockDetailViewMapper;
import com.smc.smccloud.mapper.prestock.PreStockResultMapper;
import com.smc.smccloud.model.CustomerVO;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.Employee.EmployeeVO;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.ModelExpFreqRequest;
import com.smc.smccloud.model.binorder.ModelExpFreqVO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.enums.*;
import com.smc.smccloud.model.inventory.*;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.model.orderstate.OrderStateDTO;
import com.smc.smccloud.model.orderstate.OrderStateEnum;
import com.smc.smccloud.model.orderstate.OrderStateRequest;
import com.smc.smccloud.model.orderstate.OrderStateVO;
import com.smc.smccloud.model.prestock.*;
import com.smc.smccloud.model.product.ProductInfoVO;
import com.smc.smccloud.model.product.ProductInterceptRuleDTO;
import com.smc.smccloud.model.product.ProductRemark;
import com.smc.smccloud.model.salestask.OpsSalesCommonParamVO;
import com.smc.smccloud.model.salestask.UpTaskInfoVO;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;
import com.smc.smccloud.model.stock.DealReturnOpsParam;
import com.smc.smccloud.model.stock.DealReturnOpsParamVO;
import com.smc.smccloud.model.stock.ReturnPurchaseNoParam;
import com.smc.smccloud.model.trans.TransOrderVO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2021-10-25 10:20
 * Description:
 */
@Slf4j
@Service
public class PreStockServiceImpl implements PreStockService {

    /**
     * 先行在库申请号
     */
    private final static String PRE_STOCK_APPLY_BILLTYPE = "12";
    /**
     * 专用备库订单号
     */
    private final static String PURCHASE_ORDER_BILLTYPE = "3";
    /**
     * 委托在库仓库采购单号
     */
    private final static String WTCG = "11";
    /**
     * 委托在库仓库调拨单号
     */
    private final static String WTDB = "17";

    @Value("${menhu.url}")
    private String menHuUrl;

    /**
     * 回调门户处理单号接口
     */
    private static final String RETURN_PURCHASE_NO_URL = "/saleManageSystem/opsReturnPurchaseNo/dealPurchaseNo";

    /**
     * 回调门户处理结果接口
     */
    private static final String RETURN_DEAL_INFO_URL = "/saleManageSystem/opsReturnInfo/dealReturnInfo";


    @Resource
    private PreStockApplyMapper preStockApplyMapper;
    @Resource
    private PreStockDetailMapper preStockDetailMapper;
    @Resource
    private PreStockResultMapper preStockResultMapper;
    @Resource
    private PreStockDetailViewMapper preStockDetailViewMapper;

    @Resource
    private ProductBomService productBomService;
    @Resource
    private OpsWarehouseService opsWarehouseService;

    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private ProductServiceFeignApi productServiceFeignApi;
    @Resource
    private BinServiceFeignApi binServiceFeignApi;
    @Resource
    private RequestPurchaseFeignApi requestPurchaseFeignApi;
    @Resource
    private InventoryServiceFeignApi inventoryServiceFeignApi;
    @Resource
    private TransStockFeignApi transStockFeignApi;
    @Resource
    private OrderStateServiceFeignApi orderStateServiceFeignApi;
    @Resource
    private SMSStockServiceFeignApi smsStockServiceFeignApi;
    @Resource
    private OpsPropertyFeignApi opsPropertyFeignApi;
    @Resource
    private OrderModifyServiceFeignApi orderModifyServiceFeignApi;

    @Resource
    private TransactionTemplate transactionTemplate;
    @Resource
    private CreateTokenForOtherServer createTokenForOtherServer;
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private RedisManager redisManager;
    @Resource
    private PreStockService preStockService;

    @Resource
    private PreStockNewService preStockNewService;

    @Resource
    private PrepareOrderFeignApi prepareOrderFeignApi;

    @Resource
    private OpsWmDispatchForOrderFeignApi opsWmDispatchForOrderFeignApi;

    @Autowired
    private BomSelectorFeignApi bomSelectorFeignApi;

    @Resource
    private TransOrderMapper transOrderMapper;

    // 调拨限制时间
    private Integer LIMIT_DAY = -1;

    @Override
    public ResultVo<PageInfo<PreStockApplyDTO>> listPreStockApply(PreStockApplyRequest request) {
        if (request.getToTime() != null) {
            request.setToTime(DateUtil.getEndTime(request.getToTime()));
        }

        LambdaQueryWrapper<PreStockApplyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq((request.getApplyId() != null), PreStockApplyDO::getId, request.getApplyId())
                .in((request.getApplyIds() != null && request.getApplyIds().size() > 0), PreStockApplyDO::getId, request.getApplyIds())
                .eq(StringUtils.isNotEmpty(request.getApplyNo()), PreStockApplyDO::getApplyNo, request.getApplyNo())
                .in((request.getStatuss() != null && request.getStatuss().size() > 0), PreStockApplyDO::getStatus, request.getStatuss())
                .eq(StringUtils.isNotEmpty(request.getApplyType()), PreStockApplyDO::getApplyType, request.getApplyType())
                .eq(StringUtils.isNotEmpty(request.getReplType()), PreStockApplyDO::getReplType, request.getReplType())
                .eq(StringUtils.isNotEmpty(request.getStockType()), PreStockApplyDO::getStockType, request.getStockType())
                .eq(StringUtils.isNotBlank(request.getWarehouseCode()), PreStockApplyDO::getWarehouseCode, request.getWarehouseCode())
                .ge((request.getFromTime() != null), PreStockApplyDO::getApplyTime, request.getFromTime())
                .le((request.getToTime() != null), PreStockApplyDO::getApplyTime, request.getToTime());
        // 根据申请人模糊查询 bug-9190
        if (StringUtils.isNotBlank(request.getApplyPsn())) {
            queryWrapper.nested(a -> a.likeRight(PreStockApplyDO::getApplyPsnNo, request.getApplyPsn())
                    .or().likeRight(PreStockApplyDO::getApplyPsn, request.getApplyPsn()));
        }

        PageHelper.startPage(request.getPageNum(), request.getPageSize(), "apply_time DESC");
        List<PreStockApplyDO> list = preStockApplyMapper.selectList(queryWrapper);

        PageInfo<PreStockApplyDTO> pageInfo = BeanCopyUtil.pageDto2Vo(new PageInfo<>(list), PreStockApplyDTO.class);

        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());

        for (PreStockApplyDTO detail : pageInfo.getList()) {
            // 部门名称
            if (!nameMap.containsKey(detail.getDeptNo())) {
                nameMap.put(detail.getDeptNo(), "【" + detail.getDeptNo() + "】" + opsCommonService.getDeptNameByNo(detail.getDeptNo()));
            }
            detail.setDeptNo(nameMap.getOrDefault(detail.getDeptNo(), detail.getDeptNo()));
            // 客户名称
            if (StringUtils.isNotBlank(detail.getCustomerNo())) {
                if (!nameMap.containsKey(detail.getCustomerNo())) {
                    nameMap.put(detail.getCustomerNo(), "【" + detail.getCustomerNo() + "】" + opsCommonService.getCustomerNameByNo(detail.getCustomerNo()));
                }
                detail.setCustomerNo(nameMap.getOrDefault(detail.getCustomerNo(), detail.getCustomerNo()));
            }
            // 用户名称
            if (StringUtils.isNotBlank(detail.getUserNo())) {
                if (!nameMap.containsKey(detail.getUserNo())) {
                    nameMap.put(detail.getUserNo(), "【" + detail.getUserNo() + "】" + opsCommonService.getCustomerNameByNo(detail.getUserNo()));
                }
                detail.setUserNo(nameMap.getOrDefault(detail.getUserNo(), detail.getUserNo()));
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVo<Map<String, String>> savePreStockApply(PreStockApplyDetailDTO dto) {
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        // redissonUtil.lock("ops:rediss:preStock:saving:" + userDto.getUserNo(), 10);

        String key = null;
        if (ObjectUtils.isEmpty(dto.getId())) {
            key = userDto.getUserNo();
        } else {
            key = String.valueOf(dto.getId());
        }

        for (PreStockDetailDTO detailDTO : dto.getDetails()) {
            boolean specialModel = opsCommonService.isSpecialModel(detailDTO.getModelNo());
            if (specialModel) {
                if (!"KLS".equalsIgnoreCase(dto.getWarehouseCode())) {
                    return ResultVo.failure("特殊型号，仓库必须为KLS");
                }
            }
        }

        key = "ops:rediss:preStock:saving:" + key;
        //  redissonUtil.lock(key, 10);
        if (!redissonUtil.tryLock(key, 0, 0)) {
            log.info("正在处理中" + key);
            return ResultVo.failure("正在处理中");
        }
        try {
            if (StringUtils.isBlank(dto.getDeptNo())) {
                dto.setDeptNo(userDto.getDeptNo());
            }
            if (StringUtils.isBlank(dto.getApplyPsnNo())) {
                dto.setApplyPsnNo(userDto.getUserNo());
                dto.setApplyPsn(userDto.getRealName());
            }
            if (ObjectUtils.isEmpty(dto.getTransFlag())) {
                dto.setTransFlag(false);
            }
            //    <!--add by WuWeiDong 20240327  bug 13800  服务备库补库类型，默认勾选调至备库仓-->
            if (PreStockApplyTypeEnum.WT.getCode().equalsIgnoreCase(dto.getApplyType())
                    && !dto.getTransFlag()) {
                dto.setTransFlag(true);
            }
            PreStockApplyDO applyInfo = BeanCopyUtil.copy(dto, PreStockApplyDO.class);
            ResultVo<String> checkResult = this.checkInventoryType(applyInfo);
            if (!checkResult.isSuccess()) {
                return ResultVo.failure("保存失败," + checkResult.getMessage());
            }
            checkResult = this.checkPreStockWarehouse(applyInfo);
            if (!checkResult.isSuccess()) {
                return ResultVo.failure("保存失败," + checkResult.getMessage());
            }
            // 默认为 1-SMC提案
            if (StringUtils.isBlank(applyInfo.getReplType())) {
                applyInfo.setReplType(PreStockReplTypeEnum.SMC.getCode());
            }
            if (applyInfo.getCustomerNo() == null) {
                applyInfo.setCustomerNo("");
            }

            // 前端点击过快导致页面中的申请号未刷新
            if (applyInfo.getId() != null && applyInfo.getId() > 0 && StringUtils.isBlank(applyInfo.getApplyNo())) {
                LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
                applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo);
                applyQuery.eq(PreStockApplyDO::getId, applyInfo.getId());
                PreStockApplyDO oldApplyInfo = preStockApplyMapper.selectOne(applyQuery);
                applyInfo.setApplyNo(oldApplyInfo.getApplyNo());
            }

            applyInfo.setUpdateUser(userDto.getUserNo());

            if (applyInfo.getId() == null || applyInfo.getId() == 0) {
                String status = "1";
                String applyNo = this.createPreStockApplyNo().getData();
                if (StringUtils.isBlank(applyNo)) {
                    return ResultVo.failure("先行在库申请号生成失败");
                }
                applyInfo.setApplyNo(applyNo);
                applyInfo.setStatus(status);
                applyInfo.setCreateUser(userDto.getUserNo());
                applyInfo.setPasskey(UUIDGenerator.getUUID());
                preStockApplyMapper.insert(applyInfo);
            } else {
                preStockApplyMapper.updateById(applyInfo);
            }

            // 保存申请项
            PreStockDetailDO detail;
            Map<String, String> supplierMap = new HashMap<>(dto.getDetails().size());
            String detailStatus = "1";
            int maxItemNo = Optional.ofNullable(preStockDetailMapper.getMaxItemByApplyId(applyInfo.getId())).orElse(0);
            StringBuilder errMsg = new StringBuilder();
            Date today = DateUtil.getToday();

            for (PreStockDetailDTO detailDTO : dto.getDetails()) {
                // 校验日期
                if (detailDTO.getDlvDate() != null && detailDTO.getDlvDate().compareTo(today) < 0) {
                    errMsg.append(detailDTO.getApplyModelNo()).append("货期异常,不能早于当天.");
                }
                // 委托在库申请不能申请拆分型号
                if ("3".equals(applyInfo.getApplyType()) && productBomService.isCanSplit(detailDTO.getApplyModelNo())) {
                    errMsg.append(detailDTO.getApplyModelNo()).append("是拆分型号不能申请服务备库.");
                    continue;
                }
                detail = BeanCopyUtil.copy(detailDTO, PreStockDetailDO.class);
                if (detail.getItemNo() == null || detail.getItemNo() == 0) {
                    maxItemNo++;
                    detail.setItemNo(maxItemNo);
                }
                detail.setApplyModelNo(detail.getApplyModelNo().trim());
                detail.setModelNo(detail.getApplyModelNo());
                // 如果是自动周转类型且qtyBin > 0, 则binCell = 2
                if (PreStockReplTypeEnum.AUTO.getCode().equals(applyInfo.getReplType())
                        && Optional.ofNullable(detailDTO.getQtybin()).orElse(0) > 0) {
                    detail.setBincell(2);
                }
                // 设置申请型号供应商信息
                if (StringUtils.isEmpty(detail.getSupplierCode())) {
                    this.setDetailSupplier(detail, supplierMap);
                }
                // 设置特殊出货标识
                this.setDetailSpecExpType(detail, detailDTO);
                this.setDetailStockType(applyInfo, detail);

                detail.setUpdateUser(userDto.getUserNo());
                if (detail.getId() == null || detail.getId() == 0) {
                    detail.setApplyId(applyInfo.getId());
                    detail.setSplitItem(0);
                    detail.setStatus(detailStatus);
                    detail.setCreateUser(userDto.getUserNo());
                    preStockDetailMapper.insert(detail);
                } else {
                    preStockDetailMapper.updateById(detail);
                }
            }

            Map<String, String> map = new HashMap<>(2);
            map.put("applyId", applyInfo.getId().toString());
            map.put("passkey", applyInfo.getPasskey());
            return ResultVo.success(map, "保存成功. " + errMsg.toString());

        } finally {
            redissonUtil.unlock(key);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> createPreStockApply(PreStockApplyDetailDTO dto) {
        log.info("createPreStockApply data = {}", dto);
        LoginUserDTO userDto = SMCApp.getLoginAuthDto();
        if (StringUtils.isBlank(dto.getDeptNo())) {
            dto.setDeptNo(userDto.getDeptNo());
        }
        if (StringUtils.isBlank(dto.getApplyPsnNo())) {
            dto.setApplyPsnNo(userDto.getUserNo());
            dto.setApplyPsn(userDto.getRealName());
        }
        PreStockApplyDO applyInfo = BeanCopyUtil.copy(dto, PreStockApplyDO.class);
        ResultVo<String> checkResult = this.checkInventoryType(applyInfo);
        if (!checkResult.isSuccess()) {
            return ResultVo.failure("保存失败," + checkResult.getMessage());
        }
        checkResult = this.checkPreStockWarehouse(applyInfo);
        if (!checkResult.isSuccess()) {
            return ResultVo.failure("保存失败," + checkResult.getMessage());
        }
        if (applyInfo.getCustomerNo() == null) {
            applyInfo.setCustomerNo("");
        }
        if (ObjectUtils.isEmpty(applyInfo.getTransFlag())) {
            applyInfo.setTransFlag(false);
        }
        if (StringUtils.isBlank(applyInfo.getApplyNo())) {
            String applyNo = this.createPreStockApplyNo().getData();
            if (StringUtils.isBlank(applyNo)) {
                return ResultVo.failure("先行在库申请号生成失败");
            }
            applyInfo.setApplyNo(applyNo);
        }

        String key = "ops:rediss:preStock:saving:" + applyInfo.getApplyNo();

        try {
            if (redissonUtil.tryLock(key, 0, 0)) {
                // 判断申请是否已存在
                LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
                applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo, PreStockApplyDO::getStatus);
                applyQuery.eq(PreStockApplyDO::getApplyNo, applyInfo.getApplyNo())
                        .ne(PreStockApplyDO::getStatus, 9);
                PreStockApplyDO oldApplyInfo = preStockApplyMapper.selectOne(applyQuery);
                if (oldApplyInfo != null) {
                    if ("6".equals(oldApplyInfo.getStatus())) {
                        return ResultVo.failure("申请已完成,不可重复申请");
                    }
                    applyInfo.setId(oldApplyInfo.getId());
                }

                String applyStatus = "3"; // 待处理
                applyInfo.setStatus(Optional.ofNullable(applyInfo.getStatus()).orElse(applyStatus));
                applyInfo.setUpdateUser(userDto.getUserNo());
                // 默认为 1-SMC提案
                if (StringUtils.isBlank(applyInfo.getReplType())) {
                    applyInfo.setReplType(PreStockReplTypeEnum.SMC.getCode());
                }
                // 设置shikomi申请信息
                if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType())) {
                    this.handleShikomiApplyInfo(applyInfo);
                    // 共享客户
                    if (CollectionUtils.isNotEmpty(dto.getShareCustomerNos())) {
                        applyInfo.setCustomerNos(String.join(",", dto.getShareCustomerNos()));
                    }
                }
                if (StringUtils.isBlank(applyInfo.getApplyPsn())) {
                    applyInfo.setApplyPsn(opsCommonService.getEmpSaleNameByNo(applyInfo.getApplyPsnNo()));
                }
                //    <!--add by WuWeiDong 20240327  bug 13800  服务备库补库类型，默认勾选调至备库仓-->
                if (PreStockApplyTypeEnum.WT.getCode().equalsIgnoreCase(applyInfo.getApplyType())
                        && !applyInfo.getTransFlag()) {
                    applyInfo.setTransFlag(true);
                }

                if (applyInfo.getId() == null || applyInfo.getId() == 0) {
                    applyInfo.setCreateUser(userDto.getUserNo());
                    applyInfo.setPasskey(UUIDGenerator.getUUID());
                    preStockApplyMapper.insert(applyInfo);
                } else {
                    preStockApplyMapper.updateById(applyInfo);
                }

                Map<String, String> supplierMap = new HashMap<>(dto.getDetails().size());
                List<PreStockDetailDO> detailList = new ArrayList<>(dto.getDetails().size());
                PreStockDetailDO detailDO;
                LambdaQueryWrapper<PreStockDetailDO> detailQuery;
                PreStockDetailDO detailOld;
                String detailStatus = "2"; // 处理中

                for (PreStockDetailDTO detailDTO : dto.getDetails()) {
                    if (StringUtils.isBlank(detailDTO.getApplyModelNo()) && StringUtils.isNotBlank(detailDTO.getModelNo())) {
                        detailDTO.setModelNo(detailDTO.getModelNo().trim());
                        detailDTO.setApplyModelNo(detailDTO.getModelNo());
                    }
                    if (StringUtils.isBlank(detailDTO.getModelNo()) && StringUtils.isNotBlank(detailDTO.getApplyModelNo())) {
                        detailDTO.setApplyModelNo(detailDTO.getApplyModelNo().trim());
                        detailDTO.setModelNo(detailDTO.getApplyModelNo());
                    }

                    detailDO = BeanCopyUtil.copy(detailDTO, PreStockDetailDO.class);


                    /** <!--add by WuWeiDong 20240311  bug 13655   -->
                     *   明细没有库存类开，集团号等，就用主表的。
                     */
                    if (StringUtils.isBlank(detailDO.getStockType())) {
                        detailDO.setStockType(applyInfo.getStockType());
                    }
                    if (StringUtils.isBlank(detailDO.getPplNo())) {
                        detailDO.setPplNo(applyInfo.getPplNo());
                    }
                    if (StringUtils.isBlank(detailDO.getProjectNo())) {
                        detailDO.setProjectNo(applyInfo.getProjectNo());
                    }
                    if (StringUtils.isBlank(detailDO.getGroupCustomerNo())) {
                        detailDO.setGroupCustomerNo(applyInfo.getGroupCustomerNo());
                    }

                    // 如果是自动周转申请且qtyBin > 0, 则binCell = 2
                    if (PreStockReplTypeEnum.AUTO.getCode().equals(applyInfo.getReplType()) && Optional.ofNullable(detailDTO.getQtybin()).orElse(0) > 0) {
                        detailDO.setBincell(2);
                    }
                    // 设置申请型号供应商信息
                    if (StringUtils.isEmpty(detailDO.getSupplierCode())) {
                        this.setDetailSupplier(detailDO, supplierMap);
                    }
                    // 设置shikomi申请项信息
                    if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType())) {
                        this.handleShikomiApplyDetail(detailDO);
                    }
                    // 设置特殊出货标识
                    this.setDetailSpecExpType(detailDO, detailDTO);
                    detailList.add(detailDO);
                }
                this.setNewFlag(applyInfo, detailList);

                // 设置申请项备库库存属性ID
                if (!applyInfo.getApplyType().equals(PreStockApplyTypeEnum.SHIKOMI.getCode())) {
                    this.setDetailInventoryPropertyId(detailList, applyInfo);
                }

                for (PreStockDetailDO detail : detailList) {
                    // 判断申请项是否已存在
                    if (oldApplyInfo != null) {
                        detailQuery = Wrappers.lambdaQuery();
                        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getItemNo, PreStockDetailDO::getStatus,
                                PreStockDetailDO::getPoQty, PreStockDetailDO::getStockQty);
                        detailQuery.eq(PreStockDetailDO::getApplyId, oldApplyInfo.getId())
                                .eq(PreStockDetailDO::getModelNo, detail.getModelNo())
                                .eq(PreStockDetailDO::getItemNo, detail.getItemNo())
                                // .eq(PreStockDetailDO::getSplitItem, detail.getSplitItem())
                                .eq(!PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType()), PreStockDetailDO::getDlvDate, detail.getDlvDate())
                                .eq(PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType()), PreStockDetailDO::getDlvDateJp, detail.getDlvDateJp())
                                .eq(PreStockDetailDO::getDelFlag, 0);
                        detailOld = preStockDetailMapper.selectOne(detailQuery);
                        if (detailOld != null) {
                            if ("6".equals(detailOld.getStatus())
                                    || Optional.ofNullable(detailOld.getStockQty()).orElse(0) > 0
                                    || Optional.ofNullable(detailOld.getPoQty()).orElse(0) > 0) {
                                continue; // 申请项已处理, 跳过
                            }
                            detail.setId(detailOld.getId());
                        }
                    }
                    detail.setApplyId(applyInfo.getId());
                    detail.setUpdateUser(userDto.getUserNo());

                    if (detail.getId() == null) {
                        detail.setSplitItem(0);
                        detail.setStatus(Optional.ofNullable(detail.getStatus()).orElse(detailStatus));
                        detail.setCreateUser(userDto.getUserNo());
                        preStockDetailMapper.insert(detail);
                    } else {
                        preStockDetailMapper.updateById(detail);
                    }
                }

                // 审核后的自动周转类型申请才更新BinData.BinQty
                this.updateQtyBinToBinData(applyInfo.getId());
            }
        } catch (Exception ex) {
            return ResultVo.failure("系统异常: "+ex.getMessage());
        } finally {
            redissonUtil.unlock(key);
        }
        return ResultVo.success(applyInfo.getId().toString());
    }

    @Override
    public ResultVo<PreStockApplyDetailVO> getPreStockApply(Long applyId, String passkey) {
        LambdaQueryWrapper<PreStockApplyDO> applyQueryWrapper = new LambdaQueryWrapper<>();
        applyQueryWrapper.eq(PreStockApplyDO::getId, applyId).eq(PreStockApplyDO::getPasskey, passkey);
        PreStockApplyDO applyInfo = preStockApplyMapper.selectOne(applyQueryWrapper);
        if (applyInfo == null) {
            return ResultVo.failure("申请不存在");
        }
        if (ObjectUtils.isEmpty(applyInfo.getTransFlag())) {
            applyInfo.setTransFlag(false);
        }
        PreStockApplyDetailVO vo = BeanCopyUtil.copy(applyInfo, PreStockApplyDetailVO.class);
        vo.setDetails(Collections.emptyList());
        vo.setResultDetails(Collections.emptyList());
        return ResultVo.success(vo);
    }

    @Override
    public ResultVo<PageInfo<PreStockDetailDTO>> listPreStockApplyDetail(PreStockApplyRequest request) {
        return this.listPreStockApplyDetailForBaseInfo(request);
    }

    //    <!--add by WuWeiDong 20230515  bug 10730  查询包含了在库数，Bin等信息 -->
    // @Override
    public ResultVo<PageInfo<PreStockDetailDTO>> listPreStockApplyDetailForBaseInfo(PreStockApplyRequest request) {

        LambdaQueryWrapper<PreStockDetailDO> detailQueryWrapper = Wrappers.lambdaQuery();
        detailQueryWrapper.eq(PreStockDetailDO::getApplyId, request.getApplyId())
                .eq(PreStockDetailDO::getDelFlag, 0);
        if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(request.getApplyType())) {
            detailQueryWrapper.orderByAsc(PreStockDetailDO::getItemNo, PreStockDetailDO::getDlvDateJp);
        } else {
            detailQueryWrapper.orderByAsc(PreStockDetailDO::getItemNo, PreStockDetailDO::getSplitItem, PreStockDetailDO::getDlvDate);
        }
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQueryWrapper);

        long propertypeId = 0;
        if (request.getApplyType().equals("2") || request.getStockType().equalsIgnoreCase("TY")) {
            propertypeId = 1;
        } else {
            OpsInventoryPropertyVO propertyVO = new OpsInventoryPropertyVO();
            propertyVO.setInventoryTypeCode(request.getStockType());
            propertyVO.setCustomerNo(PublicUtil.isNotEmpty(request.getUserNo()) ? request.getUserNo() : request.getCustomerNo());
            propertyVO.setPpl(request.getPplNo());
            propertyVO.setProjectCode(request.getProjectNo());
            propertyVO.setGroupCustomerNo(request.getGroupCustomerNo());

            ResultVo<OpsInventoryPropertyVO> resultVo = inventoryServiceFeignApi.checkInventoryProperty(propertyVO);
            if (resultVo.isSuccess() && Objects.nonNull(resultVo.getData())) {
                propertypeId = resultVo.getData().getInventoryPropertyId();
            } else {
                //    <!--add by WuWeiDong 20230619  bug 11146  没有查到数据，继续往下走 -->
                if (resultVo.getData() == null) {
                    log.info("无法取到InventoryPropertyId");
                } else {
                    log.info(resultVo.getMessage());
                }
            }
            ;
        }
        List<String> modelNos = detailList.stream().map(PreStockDetailDO::getModelNo).distinct().collect(Collectors.toList());

        InventoryRequestDTO inventoryRequestDTO = new InventoryRequestDTO();
        inventoryRequestDTO.setPropertyId(propertypeId);
        inventoryRequestDTO.setWarehouseCode(request.getWarehouseCode());
        inventoryRequestDTO.setModelNos(modelNos);
        ResultVo<List<InventorySummaryVO>> resultInventorySummaryVO = inventoryServiceFeignApi.listInventorySummaryByPropertyId(inventoryRequestDTO);
        if (!resultInventorySummaryVO.isSuccess()) {
            log.info(resultInventorySummaryVO.getMessage());
        }
        if (Objects.isNull(resultInventorySummaryVO.getData())) {
            log.info("无法取到型号基本信息InventorySummary。");
        }

        PageInfo<PreStockDetailDO> pageInfo = new PageInfo<>(detailList);
        List<PreStockDetailDTO> voList = new ArrayList<>(detailList.size());


        PreStockDetailDTO vo;
        for (PreStockDetailDO detail : detailList) {
            vo = BeanCopyUtil.copy(detail, PreStockDetailDTO.class);

            // E金额
            if(vo.getEprice() != null && vo.getEprice().compareTo(BigDecimal.ZERO) >= 0) {
                vo.setEprice(vo.getEprice().setScale(2, RoundingMode.HALF_UP));
                vo.setEAmount(vo.getEprice().multiply(new BigDecimal(vo.getQuantity())).setScale(2, RoundingMode.HALF_UP));
            }

            vo.setRohs10(this.getRohs10(detail.getSpecExpType()));
            if (PublicUtil.isNotEmpty(resultInventorySummaryVO.getData())) {
                List<InventorySummaryVO> inventorySummaryVOS = resultInventorySummaryVO.getData().stream()
                        .filter(i -> i.getModelNo().equalsIgnoreCase(detail.getModelNo()))
                        .collect(Collectors.toList());
                if (PublicUtil.isNotEmpty(inventorySummaryVOS)) {
                    vo.setQtyOnHand(inventorySummaryVOS.get(0).getQtyOnHand());
                    vo.setOrdingQty(inventorySummaryVOS.get(0).getOrdingQty());
                    vo.setBinQty(inventorySummaryVOS.get(0).getBinQty());
                    vo.setMonthAvgQty(inventorySummaryVOS.get(0).getMonthAvgQty());
                    vo.setCanUseMonths(inventorySummaryVOS.get(0).getCanUseMonths());
                }
            }
            voList.add(vo);
        }
        pageInfo.setList(null);
        PageInfo<PreStockDetailDTO> voPageInfo = new PageInfo<>();
        BeanCopyUtil.copy(pageInfo, voPageInfo);
        voPageInfo.setList(voList);
        return ResultVo.success(voPageInfo);
    }


    @Override
    public ResultVo<List<PreStockResultDTO>> getApplyDetailResult(List<Long> detailIds) {
        LambdaQueryWrapper<PreStockResultDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(PreStockResultDO::getFromId, detailIds)
                .orderByAsc(PreStockResultDO::getItemNo, PreStockResultDO::getFromId);
        List<PreStockResultDO> resultList = preStockResultMapper.selectList(queryWrapper);
        List<PreStockResultDTO> voList = BeanCopyUtil.copyList(resultList, PreStockResultDTO.class);
        return ResultVo.success(voList);
    }

    @Override
    public void removeDetail(List<Long> detailIds) {
        if (CollectionUtils.isEmpty(detailIds)) {
            return;
        }
        LambdaUpdateWrapper<PreStockDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(PreStockDetailDO::getDelFlag, 1);
        updateWrapper.in(PreStockDetailDO::getId, detailIds);
        preStockDetailMapper.update(new PreStockDetailDO(), updateWrapper);
    }

    @Transactional
    @Override
    public ResultVo<String> submitPreStockApply(PreStockApplyDetailDTO dto) {

        int applyQueryStatus = 1; // 编辑中
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        applyQuery.eq(PreStockApplyDO::getId, dto.getId())
                .eq(PreStockApplyDO::getStatus, applyQueryStatus);
        PreStockApplyDO applyInfo = preStockApplyMapper.selectOne(applyQuery);
        if (applyInfo == null) {
            return ResultVo.failure("提交失败,申请不存在");
        }

        int detailQueryStatus = 1; // 提交中
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getApplyId, dto.getId())
                .eq(PreStockDetailDO::getStatus, detailQueryStatus)
                .eq(PreStockDetailDO::getDelFlag, 0)
                .orderByAsc(PreStockDetailDO::getItemNo);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("提交失败,申请项不能为空");
        }

        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        Date now = new Date();
        int applyUpdateStatus = 2; // 待审核确认
        LambdaUpdateWrapper<PreStockApplyDO> applyUpdateWrapper = new LambdaUpdateWrapper<>();
        applyUpdateWrapper.set(PreStockApplyDO::getApplyTime, now)
                .set(PreStockApplyDO::getStatus, applyUpdateStatus)
                .set(StringUtils.isBlank(applyInfo.getApplyPsnNo()), PreStockApplyDO::getApplyPsnNo, userDTO.getUserNo())
                .set(StringUtils.isBlank(applyInfo.getApplyPsn()), PreStockApplyDO::getApplyPsn, userDTO.getRealName())
                .set(PreStockApplyDO::getUpdateUser, userDTO.getUserNo());
        applyUpdateWrapper.eq(PreStockApplyDO::getId, dto.getId());
        preStockApplyMapper.update(new PreStockApplyDO(), applyUpdateWrapper);

        // 设置申请项备库库存属性ID
        if (!applyInfo.getApplyType().equals(PreStockApplyTypeEnum.SHIKOMI.getCode())) {
            this.setDetailInventoryPropertyId(detailList, applyInfo);
        }

        String detailUpdateStatus = "2"; // 处理中
        int itemNo = 0;
        Map<String, String> supplierMap = new HashMap<>(detailList.size());

        for (PreStockDetailDO detail : detailList) {
            itemNo++;
            detail.setItemNo(itemNo);
            // 设置型号供应商信息
            if (StringUtils.isEmpty(detail.getSupplierCode())) {
                this.setDetailSupplier(detail, supplierMap);
            }
            detail.setStatus(detailUpdateStatus);
            detail.setUpdateUser(userDTO.getUserNo());
            preStockDetailMapper.updateById(detail);
        }
        return ResultVo.success("提交成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> approvePreStockApply(PreStockApplyHandleDTO dto) {
        if (dto.getApplyIds() == null || dto.getApplyIds().isEmpty()) {
            return ResultVo.failure("审核确认失败");
        }
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        Date now = new Date();

        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        LambdaUpdateWrapper<PreStockApplyDO> applyUpdateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
        PreStockApplyDO applyInfo;
        String unApprove = "2"; // 待确认
        int approveStatus = 3; // 待处理

        for (Long applyId : dto.getApplyIds()) {
            applyQuery.clear();
            applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyType, PreStockApplyDO::getStatus);
            applyQuery.eq(PreStockApplyDO::getId, applyId);
            applyInfo = preStockApplyMapper.selectOne(applyQuery);
            if (!unApprove.equals(applyInfo.getStatus())) {
                continue;
            }

            applyUpdateWrapper.clear();
            applyUpdateWrapper.set(PreStockApplyDO::getStatus, approveStatus)
                    .set(PreStockApplyDO::getApproveTime, now)
                    .set(PreStockApplyDO::getApproverNo, userDTO.getUserNo())
                    .set(PreStockApplyDO::getApproverName, userDTO.getRealName());
            applyUpdateWrapper.eq(PreStockApplyDO::getId, applyInfo.getId());
            preStockApplyMapper.update(new PreStockApplyDO(), applyUpdateWrapper);

            detailUpdateWrapper.clear();
            detailUpdateWrapper.set(PreStockDetailDO::getConfirmUser, userDTO.getUserNo());
            detailUpdateWrapper.eq(PreStockDetailDO::getApplyId, applyInfo.getId())
                    .eq(PreStockDetailDO::getDelFlag, 0);
            preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper);

            // 审核通过后,自动周转类型的才更新BinData.BinQty
            this.updateQtyBinToBinData(applyId);
        }

        return ResultVo.success("审核确认");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> saveShikomiInfo(PreStockApplyDetailDTO dto) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        StringBuilder errorMsg = new StringBuilder();
        if (StringUtils.isBlank(dto.getApplyPsnMail())) {
            errorMsg.append("申请担当邮箱不能为空.</br>");
        }
        if (StringUtils.isBlank(dto.getApproverMail())) {
            errorMsg.append("负责人邮箱不能为空.</br>");
        }
        if (StringUtils.isNotBlank(dto.getCustomerNo()) && StringUtils.isBlank(dto.getEnname())) {
            errorMsg.append("客户名称不能为空.</br>");
        }
        if (StringUtils.isBlank(dto.getReasonEn())) {
            errorMsg.append("申请原因（英文）不能为空.</br>");
        }
        String detailErrorMsg;
        boolean hasJP = false;
        for (PreStockDetailDTO detail : dto.getDetails()) {
            detailErrorMsg = "";
            if (StringUtils.isBlank(detail.getSupplierCode())) {
                detailErrorMsg += "供应商不能为空.";
            }
            if ("JP".equals(detail.getSupplierCode())) {
                hasJP = true;
                if (StringUtils.isBlank(detail.getModelName())) {
                    detailErrorMsg += "型号英文名称不能为空.";
                } else {
                    if (ChineseUtil.containsChinese(detail.getModelName())) {
                        detailErrorMsg += "型号英文名称包含中文字符.";
                    }
                }
            }
            if (StringUtils.isNotBlank(detailErrorMsg)) {
                errorMsg.append(detail.getModelNo()).append(detailErrorMsg).append("</br>");
            }
        }
        // JP供应商: 检查申请原因、客户英文名称中是否包含非法字符 bug-10164
        if (hasJP) {
            if (ChineseUtil.containsChinese(dto.getEnname())) {
                errorMsg.append("客户英文名称包含中文字符.");
            }
            if (ChineseUtil.containsChinese(dto.getReasonEn())) {
                errorMsg.append("申请原因包含中文字符.");
            }
        }
        if (StringUtils.isNotBlank(errorMsg)) {
            return ResultVo.failure("处理失败: " + errorMsg.toString());
        }
        // 保存 ApplyPsnMail, ApproverMail, Enname, ReasonEn
        int shikomiConfirmStatus = 5; // shikomi已确认
        LambdaUpdateWrapper<PreStockApplyDO> applyUpdateWrapper = new LambdaUpdateWrapper<>();
        applyUpdateWrapper.set(StringUtils.isNotBlank(dto.getApplyPsnMail()), PreStockApplyDO::getApplyPsnMail, dto.getApplyPsnMail())
                .set(StringUtils.isNotBlank(dto.getApproverMail()), PreStockApplyDO::getApproverMail, dto.getApproverMail())
                .set(StringUtils.isNotBlank(dto.getEnname()), PreStockApplyDO::getEnname, dto.getEnname())
                .set(StringUtils.isNotBlank(dto.getReason()), PreStockApplyDO::getReason, dto.getReason())
                .set(StringUtils.isNotBlank(dto.getReasonEn()), PreStockApplyDO::getReasonEn, dto.getReasonEn())
                .set(PreStockApplyDO::getStatus, shikomiConfirmStatus)
                .set(PreStockApplyDO::getApproveTime, new Date())
                .set(PreStockApplyDO::getUpdateUser, userDTO.getUserNo());
        applyUpdateWrapper.eq(PreStockApplyDO::getId, dto.getId())
                .eq(PreStockApplyDO::getStatus, 3);
        preStockApplyMapper.update(new PreStockApplyDO(), applyUpdateWrapper);

        // 保存supplierCode,
        Date now = new Date();
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
        for (PreStockDetailDTO detail : dto.getDetails()) {
            detailUpdateWrapper.clear();
            detailUpdateWrapper.set(PreStockDetailDO::getSupplierCode, detail.getSupplierCode())
                    .set(StringUtils.isNotBlank(detail.getModelName()), PreStockDetailDO::getModelName, detail.getModelName())
                    .set(PreStockDetailDO::getStatus, shikomiConfirmStatus)
                    .set(PreStockDetailDO::getConfirmUser, userDTO.getUserNo());
            detailUpdateWrapper.eq(PreStockDetailDO::getId, detail.getId());
            preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper);
        }

        return ResultVo.success("保存成功");
    }

    @Override
    public ResultVo<String> exportShikomiFile() {
        // 查询shikomi申请项
        int detailStatus = 5; // 已确认
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getStatus, detailStatus)
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("导出SHIKOMI文件失败:</br> 没有待确认的shikomi申请项.");
        }
        // 检查申请项是否已填写供应商
        StringBuilder error = new StringBuilder();
        for (PreStockDetailDO detail : detailList) {
            if (StringUtils.isBlank(detail.getSupplierCode())) {
                error.append("申请").append(detail.getApplyId()).append(", ").append(detail.getModelNo()).append("未设置供应商信息.</br>");
            }
        }
        if (error.length() > 0) {
            return ResultVo.failure("导出SHIKOMI文件失败:</br>" + error);
        }
        // 获取shikomi文件附件收件人邮箱
        String classCode = "9004";
        ResultVo<List<DataCodeVO>> mailResult = dictCommonService.getDataCodes(classCode);
        if (!mailResult.isSuccess()) {
            return ResultVo.failure("导出SHIKOMI文件失败:</br>获取shikomi邮件收件人失败," + mailResult.getMessage());
        }
        if (CollectionUtils.isEmpty(mailResult.getData())) {
            return ResultVo.failure("导出SHIKOMI文件失败:</br>获取shikomi邮件收件人失败,请检查是否已设置收件人");
        }
        // 按供应商分组
        Map<String, List<DataCodeVO>> mailMap = mailResult.getData().stream().collect(Collectors.groupingBy(DataCodeVO::getCode));
        Map<String, List<PreStockDetailDO>> shikomiDataMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getSupplierCode));
        for (String supplier : shikomiDataMap.keySet()) {
            if (!mailMap.containsKey(supplier)) {
                error.append("未设置供应商").append(supplier).append("的SHIKOMI邮件收件人.</br>");
            }
        }
        if (error.length() > 0) {
            return ResultVo.failure("导出SHIKOMI文件失败:</br>" + error);
        }

        ResultVo<String> exportResult;
        boolean success = true;
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, List<PreStockDetailDO>> map : shikomiDataMap.entrySet()) {
            if ("JP".equals(map.getKey())) {
                exportResult = this.exportJPShikomiFile(map.getValue(), mailMap.get(map.getKey()).get(0));
            } else {
                exportResult = this.exportCNShikomiFile(map.getValue(), mailMap.get(map.getKey()).get(0));
            }
            if (exportResult.isSuccess()) {
                message.append(exportResult.getData());
            } else {
                success = false;
                message.append(exportResult.getMessage());
            }
        }

        if (success) {
            return ResultVo.success("SHIKOMI文件已导出,请注意查看邮箱: " + message);
        } else {
            return ResultVo.failure("SHIKOMI文件导出报错: " + message);
        }
    }

    @Override
    public ResultVo<PreStockResultVO> getPreStockResult(Long applyId, Long detailId) {

        try {
            PreStockApplyDO applyInfo = preStockApplyMapper.selectById(applyId);

            PreStockDetailDO detailInfo = preStockDetailMapper.selectById(detailId);

            PreStockResultVO vo = new PreStockResultVO();
            vo.setApplyId(applyInfo.getId());
            vo.setModelNo(detailInfo.getModelNo());

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<BindataVO> binDataFuture = executorService.submit(new Callable<BindataVO>() {
                @Override
                public BindataVO call() throws Exception {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    return binServiceFeignApi.getBindataByModelNo(detailInfo.getModelNo(), applyInfo.getWarehouseCode()).getData();
                }
            });
            Future<ProductRemark> productFuture = executorService.submit(new Callable<ProductRemark>() {
                @Override
                public ProductRemark call() throws Exception {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    return productServiceFeignApi.getProductRemarkByModelNo(detailInfo.getModelNo()).getData();
                }
            });
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    break;
                }
            }
            // 设置BinData信息
            BindataVO bindataVO = binDataFuture.get();
            if (bindataVO != null) {
                vo.setQtyBin(Optional.ofNullable(bindataVO.getQtyBin()).orElse(0));
                vo.setInventoryType(bindataVO.getStockType().toString());
                vo.setFreq(bindataVO.getFreq());
                vo.setMean(bindataVO.getMean());
            }
            // <!--add by WuWeiDong 20230926  bug 10925  手动生成请购数量按最小包装数量 -->
            ProductRemark productRemarkInfo = productFuture.get();
            if (Objects.nonNull(productRemarkInfo)) {
                vo.setMinPackageQty(Optional.ofNullable(productRemarkInfo.getMinPackUnit()).orElse(0));
            }

            // 设置申请型号每月备库明细
            PreStockApplyMonthlyDetail monthlyDetail = new PreStockApplyMonthlyDetail();
            monthlyDetail.setModelNo(detailInfo.getModelNo());
            monthlyDetail.setId(detailInfo.getId());
            monthlyDetail.setApplyId(detailInfo.getApplyId());
            monthlyDetail.setDlvDate(detailInfo.getDlvDate());
            monthlyDetail.setStatus(detailInfo.getStatus());
            monthlyDetail.setQuantity(detailInfo.getQuantity() - Optional.ofNullable(detailInfo.getPoQty()).orElse(0) - Optional.ofNullable(detailInfo.getStockQty()).orElse(0)- Optional.ofNullable(detailInfo.getPrepareQty()).orElse(0));
            vo.setMonthlyDetails(Collections.singletonList(monthlyDetail));
            // 计算总数量
            vo.setQuantity(vo.getMonthlyDetails().stream().mapToInt(PreStockApplyMonthlyDetail::getQuantity).sum());
            // 设置申请型号可出库库房情况
            ResultVo<List<String>> warehouseConfig = this.getWarehouseConfig(applyInfo);
            if (!warehouseConfig.isSuccess()) {
                return ResultVo.failure("系统生成待处理项失败: " + warehouseConfig.getMessage());
            }

            String inventoryTypeCode = StringUtils.isBlank(detailInfo.getStockType()) ? applyInfo.getStockType() : detailInfo.getStockType();
            List<PreModelStockInfo> stockInfos = this.getPreModelInventoryInfoByStockClass(applyInfo, detailInfo, warehouseConfig.getData(), inventoryTypeCode);

            //    <!--add by WuWeiDong 20240130  bug 13489  有效库存进行汇总 -->
            Map<String, PreModelStockInfo> mapInfo = new HashMap<>();

            for (PreModelStockInfo info : stockInfos) {
                PreModelStockInfo stockInfo = mapInfo.get(info.getWarehouseCode());
                if (ObjectUtils.isEmpty(stockInfo)) {
                    info.setInventoryTypeCode("");
                    mapInfo.put(info.getWarehouseCode(), info);
                } else {
                    int avaQty = Optional.ofNullable(stockInfo.getAvaQty()).orElse(0) + Optional.ofNullable(info.getAvaQty()).orElse(0);
                    int avaQty_TY = Optional.ofNullable(stockInfo.getAvaQty_ty()).orElse(0) + Optional.ofNullable(info.getAvaQty_ty()).orElse(0);
                    int avaQty_ZY = Optional.ofNullable(stockInfo.getAvaQty_zy()).orElse(0) + Optional.ofNullable(info.getAvaQty_zy()).orElse(0);
                    int excessQty = Optional.ofNullable(stockInfo.getExcessQty()).orElse(0) + Optional.ofNullable(info.getExcessQty()).orElse(0);
                    stockInfo.setAvaQty(avaQty);
                    stockInfo.setAvaQty_ty(avaQty_TY);
                    stockInfo.setAvaQty_zy(avaQty_ZY);
                    stockInfo.setExcessQty(excessQty);
                }
            }
            List<PreModelStockInfo> newStockInfos = mapInfo.values().stream().collect(Collectors.toList());
            vo.setInventoryInfos(newStockInfos);


            return ResultVo.success(vo);
        } catch (Exception ex) {
            log.error("{} ==》错误：{}", Thread.currentThread().getName(), ex);
            return ResultVo.failure("错误:" + ex.getMessage());
        }
    }

    @Override
    public List<PreStockResultDO> listPreStockResult(Long applyId, String modelNo) {
        LambdaQueryWrapper<PreStockResultDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreStockResultDO::getApplyId, applyId)
                .eq(StringUtils.isNotEmpty(modelNo), PreStockResultDO::getApplyModelNo, modelNo);
        return preStockResultMapper.selectList(queryWrapper);
    }

    @Override
    public ResultVo<List<PreStockResultDTO>> getProcessItemByDetailId(long detailId) {
        // 查询申请项
        //int detailStatus = 2; // 处理中
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getId, detailId)
                //.eq(PreStockDetailDO::getStatus, detailStatus)
                .eq(PreStockDetailDO::getDelFlag, 0);
        PreStockDetailDO detail = preStockDetailMapper.selectOne(detailQuery);
        if (detail == null) {
            return ResultVo.failure("申请项不存在: " + detailId);
        }
        if ("4".equals(detail.getStatus())) {
            return ResultVo.failure("该申请已驳回");
        }
        if ("6".equals(detail.getStatus())) {
            return ResultVo.failure("该申请项已处理");
        }
        // 查询申请信息
        PreStockApplyDO applyInfo = preStockApplyMapper.selectById(detail.getApplyId());
        // 查询申请可出仓库
        ResultVo<List<String>> warehouseConfig = this.getWarehouseConfig(applyInfo);
        log.info("先行在库-{} warehouseConfig = {}", applyInfo.getApplyNo(), warehouseConfig.getData());
        // 判断是否可调库
        boolean canTransfer = this.checkTransfer(applyInfo, detail, warehouseConfig.getData());
        // 获取可出在库库存
        List<PreModelStockInfo> stockInfoList = Collections.emptyList();
        if (canTransfer) {
            String inventoryTypeCode = StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType();

            //把明细的库存属性放在主信息传送
            PreStockApplyDO newApplyInfo = applyInfo;
            if (StringUtils.isNotBlank(detail.getStockType())) {
                newApplyInfo.setStockType(detail.getStockType());
            }
            if (StringUtils.isNotBlank(detail.getGroupCustomerNo())) {
                newApplyInfo.setGroupCustomerNo(detail.getGroupCustomerNo());
            }
            if (StringUtils.isNotBlank(detail.getGroupCustomerNo())) {
                newApplyInfo.setGroupCustomerNo(detail.getGroupCustomerNo());
            }
            if (StringUtils.isNotBlank(detail.getProjectNo())) {
                newApplyInfo.setProjectNo(detail.getProjectNo());
            }
            if (StringUtils.isNotBlank(detail.getPplNo())) {
                newApplyInfo.setPplNo(detail.getPplNo());
            }
            stockInfoList = this.getPreModelInventoryInfoByStockClass(newApplyInfo, detail, warehouseConfig.getData(), inventoryTypeCode);
        }
        // 生成处理项
        List<PreStockResultDTO> createResultList = this.autoCreateProcessItem(applyInfo, detail, canTransfer, stockInfoList);

        return ResultVo.success(createResultList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<String> handleProcessItem(List<PreStockResultDTO> processItemList) {
        log.info("先行在库手动处理 data = {}", JSON.toJSONString(processItemList));
        LoginUserDTO userDTO;
        try {
            userDTO = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            userDTO = new LoginUserDTO();
            userDTO.setUserNo("");
            userDTO.setUserName("未知");
        }
        if (CollectionUtils.isEmpty(processItemList)) {
           return ResultVo.failure("手动处理-执行处理数据不能为空");
        }
        long applyId = processItemList.get(0).getApplyId();

        String key = "ops:rediss:preStock:processing:" + applyId;
        if (redissonUtil.tryLock(key, 0, 0)) {
            try {
                Integer appQuantity = 0;
                Integer qty = processItemList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum(); //手动处理数累计
                Integer cancelQty = processItemList.stream()
                        .filter(i -> "9".equalsIgnoreCase(i.getProcessType())).mapToInt(PreStockResultDTO::getOrderQty).sum(); //手动处理暂不处理数累计
                // 查询申请项信息
                Iterator<PreStockResultDTO> iterator = processItemList.iterator();
                Map<Long, PreStockDetailDO> detailMap = new HashMap<>(processItemList.size());
                Long detailId;
                PreStockDetailDO detail;

                while (iterator.hasNext()) {
                    PreStockResultDTO preStockResultDTO = iterator.next();
                    detailId = preStockResultDTO.getFromId();

                    // <!--add by WuWeiDong 20230926  bug 10925  手动生成请购数量按最小包装数量 -->
                    String modelNo = preStockResultDTO.getModelNo();
                    ResultVo<Integer> resultVo = this.getMinPackageQty(modelNo);
                    if (!resultVo.isSuccess()) {
                        return ResultVo.failure(resultVo.getMessage());
                    }
                    /**
                     * 1-采购(生成采购订单);
                     * 2-BIN采购(生成采购订单);
                     * 3-预约BIN在途(预约到在途订单);
                     * 4-调库;
                     * 5-异仓调拨;
                     * 9-不备库;
                     */
                    Integer minPackageQty = Optional.ofNullable(resultVo.getData()).orElse(0);
                    if (PreStockProcessTypeEnum.purchase.getCode().equalsIgnoreCase(preStockResultDTO.getProcessType()) || PreStockProcessTypeEnum.binPurchase.getCode().equalsIgnoreCase(preStockResultDTO.getProcessType())) {
                        Integer orderQty = Optional.ofNullable(preStockResultDTO.getOrderQty()).orElse(0);
                        if (orderQty.compareTo(0) > 0 && minPackageQty.compareTo(0) > 0) {
                            if (orderQty % minPackageQty != 0) {
                                return ResultVo.failure("采购数量必须是最小包装数倍数。订单数：" + orderQty + " 最小包装数：" + minPackageQty);
                            }
                        }
                    }
                    if (detailMap.containsKey(detailId)) {
                        continue;
                    }

                    detail = preStockDetailMapper.selectById(detailId);
                    appQuantity = detail.getQuantity();
                    // 6-已完成 9取消
                    if (detail == null || "6".equals(detail.getStatus()) || "9".equals(detail.getStatus())) {
                        iterator.remove();
                    } else {
                        detail.setQuantity(detail.getQuantity()
                                - Optional.ofNullable(detail.getStockQty()).orElse(0)
                                - Optional.ofNullable(detail.getPoQty()).orElse(0)
                                - Optional.ofNullable(detail.getPrepareQty()).orElse(0));
                        detailMap.put(detailId, detail);
                    }
                }
                if (detailMap.isEmpty()) {
                    return ResultVo.failure("没有待处理的申请项");
                }

                // 查询申请信息
                PreStockApplyDO applyInfo = preStockApplyMapper.selectById(applyId);

                // 获取可出在库仓库
                List<String> warehouseConfig = this.getWarehouseConfig(applyInfo).getData();
                log.info("先行在库-{} warehouseConfig = {}", applyInfo.getApplyNo(), warehouseConfig);

                Map<Long, Integer> statusMap = new HashMap<>();
                // 手动处理项
                for (PreStockResultDTO result : processItemList) {
                    if (StringUtils.isBlank(result.getOrderStock())) {
                        return ResultVo.failure("手动处理失败: 供应商不能为空");
                    }
                    detail = detailMap.get(result.getFromId());
                    /** getProcessType:2030
                     * 1	采购;2	BIN采购;3	预约在途;4	调库;5	异仓调拨; 6  准备订单;  9	不备库
                     */

                    switch (result.getProcessType()) {
                        case "1":
                        case "2":
                        case "6":
                            // 验证供应商是否正确
                            String name = opsCommonService.getSupplierNameByCode(result.getOrderStock());
                            if (StringUtils.isBlank(name)) {
                                return ResultVo.failure("手动处理失败: 采购供应商设置错误");
                            }
                            if (result.getProcessType().equals("6")) {
                                result.setRemark("准备订单采购" + result.getOrderQty() + ";");
                            } else {
                                result.setRemark("采购" + result.getOrderQty() + ";");
                            }

                            // 增加限制，顾客在库PPL,顾客在库项目，战略在库（PJ）这三个类型暂时不生成准备订单
                            if (InventoryTypeEnum.GK_PPL.getCode().equalsIgnoreCase(detail.getStockType()) ||
                                    InventoryTypeEnum.GK_PJ.getCode().equalsIgnoreCase(detail.getStockType()) ||
                                    InventoryTypeEnum.ZL_PJ.getCode().equalsIgnoreCase(detail.getStockType())) {
                                result.setRemark("采购" + result.getOrderQty() + ";");
                                result.setProcessType("1");
                            }

                            detail.setQuantity(detail.getQuantity() - result.getOrderQty());
                            break;
                        case "3":
                        case "4":
                        case "5":
                            if (!warehouseConfig.contains(result.getOrderStock())) {
                                return ResultVo.failure("手动处理失败: 申请部门没有" + result.getOrderStock() + "出库权限");
                            }
                            if ("3".equals(result.getProcessType())) {
                                String prepareOrder = new OrderNoInfo().createFullOrderNo(result.getAssociateNo(), result.getAssociateNoItem(), result.getAssociateNoSplit())
                                        .getFullOrderNo();
                                result.setRemark("预占" + prepareOrder + "在途" + result.getOrderQty() + ";");
                            } else {
                                result.setRemark("分配" + result.getOrderStock() + "通用库存" + result.getOrderQty() + ";");
                            }
                            result.setSpecMark(detail.getSpecMark());
                            result.setProductTag(this.getRohs10(detail.getSpecExpType()) ? "0" : "9");
                            detail.setQuantity(detail.getQuantity() - result.getOrderQty());
                            break;
                        case "9":
                            //    <!--add by WuWeiDong 20231012  bug 12340  暂不处理数量=补库数量才更新取消状态 -->
                            Integer processQty = Optional.ofNullable(detail.getStockQty()).orElse(0) + Optional.ofNullable(detail.getPoQty()).orElse(0) + qty;//已处理数量
                            if (cancelQty.compareTo(appQuantity) >= 0) {
                                detail.setStatus("9");
                            } else if (processQty.compareTo(appQuantity) >= 0) {
                                detail.setStatus("6");
                            } else {
                                detail.setStatus("2");
                            }
                            detail.setStockQty(null);
                            detail.setPoQty(null);
                            break;

                    }

                    Integer status = statusMap.getOrDefault(result.getFromId(), 2);
                    Integer newStatus = Integer.parseInt(detail.getStatus());
                    if (status.compareTo(newStatus) < 0) {
                        statusMap.put(result.getFromId(), newStatus);
                    }
                    if (detail.getQuantity() < 0) {
                        return ResultVo.failure("手动处理失败: 处理数量不得大于申请数量");
                    }
                }

                // 按Detail分组
                Map<Long, List<PreStockResultDTO>> resultMap = processItemList.stream().collect(Collectors.groupingBy(PreStockResultDTO::getFromId));


                for (Map.Entry<Long, List<PreStockResultDTO>> subResultMap : resultMap.entrySet()) {
                    detail = detailMap.get(subResultMap.getKey());


                    // 根据处理类型分组处理
                    Map<String, List<PreStockResultDTO>> processTypeMap = subResultMap.getValue().stream()
                            .collect(Collectors.groupingBy(PreStockResultDTO::getProcessType));
                    List<PreStockResultDTO> transferList = new ArrayList<>(subResultMap.getValue().size());
                    List<PreStockResultDTO> moveList = new ArrayList<>(subResultMap.getValue().size());
                    List<PreStockResultDTO> purchaseList = new ArrayList<>(subResultMap.getValue().size());
                    // 采购
                    if (processTypeMap.containsKey("1")) {
                        purchaseList.addAll(processTypeMap.get("1"));
                    }
                    if (processTypeMap.containsKey("2")) {
                        purchaseList.addAll(processTypeMap.get("2"));
                    }
                    if (processTypeMap.containsKey("6")) {
                        purchaseList.addAll(processTypeMap.get("6"));
                    }
                    //预占
                    if (processTypeMap.containsKey("3")) {
                        moveList.addAll(processTypeMap.get("3"));
                    }
                    // 调库
                    if (processTypeMap.containsKey("4")) {
                        transferList.addAll(processTypeMap.get("4"));
                    }
                    if (processTypeMap.containsKey("5")) {
                        transferList.addAll(processTypeMap.get("5"));
                    }

                    // 先行在库拦截规则过滤
                    List<PreStockResultDTO> resultList = new ArrayList<>();
                    if (transferList.size() + purchaseList.size() > 0) {
                        resultList = this.filterInterceptedProcessingItems(applyInfo, detail, transferList, moveList, purchaseList, false);
                    }

                    // 执行调拨处理
                    if (CollectionUtils.isNotEmpty(transferList)) {
                        try {
                            for (PreStockResultDTO transData : transferList) {
                                transData.setToCustomerNo(applyInfo.getCustomerNo());
                                transData.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
                                transData.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
                                transData.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());

                            }
                            ResultVo<String> transResult = this.createTransferOrder(applyInfo, detail, transferList);
                            if (!transResult.isSuccess()) {
                                return transResult;
                            }
                        } catch (Exception e) {
                            log.error("先行在库申请{} 执行调拨处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                            throw new BusinessException("执行调拨处理失败,请稍后重试. " + e.getMessage());
                        }
                    }
                    //执行预占处理
                    if (CollectionUtils.isNotEmpty(moveList)) {
                        try {
                            for (PreStockResultDTO moveData : moveList) {
                                moveData.setToCustomerNo(applyInfo.getCustomerNo());
                                moveData.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
                                moveData.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
                                moveData.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());
                            }
                            ResultVo<String> moveResult = this.createMoverOrder(applyInfo, detail, moveList);
                            if (!moveResult.isSuccess()) {
                                return moveResult;
                            }
                        } catch (Exception e) {
                            log.error("先行在库申请{} 执行预占BIN在途处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                            throw new BusinessException("执行预占BIN在途处理失败,请稍后重试. " + e.getMessage());
                        }
                    }
                    // 执行采购处理
                    if (CollectionUtils.isNotEmpty(purchaseList)) {
                        try {
                            for (PreStockResultDTO purchaseData : purchaseList) {
                                //  purchaseData.setInventoryTypeCode(applyInfo.getStockType());
                                purchaseData.setToCustomerNo(applyInfo.getCustomerNo());
                                purchaseData.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
                                purchaseData.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
                                purchaseData.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());

                            }
                            ResultVo<String> purchaseResult = preStockNewService.createPurchaseOrder(applyInfo, detail, purchaseList,false);
                           // ResultVo<String> purchaseResult = this.createPurchaseOrder(applyInfo, detail, purchaseList);
                            if (!purchaseResult.isSuccess()) {
                                return purchaseResult;
                            }
                        } catch (Exception e) {
                            log.error("先行在库申请{} 执行采购处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                            throw new BusinessException("执行采购处理失败,请稍后重试. " + e.getMessage());
                        }
                    }
                    // 执行其他处理
//                    if ("9".equals(detail.getStatus())) {
//
//                        // 保存[不备库]处理结果
//                        if (CollectionUtils.isEmpty(resultList)) {
//                            resultList = new ArrayList<>();
//                        }
//                        resultList.addAll(processTypeMap.getOrDefault("9", Collections.emptyList()));
//                        PreStockResultDO result;
//                        for (PreStockResultDTO resultDTO : resultList) {
//                            result = BeanCopyUtil.copy(resultList.get(0), PreStockResultDO.class);
//                            result.setOptStatus("2"); // 已处理
//                            result.setCreateUser(userDTO.getUserNo());
//                            result.setUpdateUser(userDTO.getUserNo());
//                            preStockResultMapper.insert(result);
//                        }
//                        // 更新申请项处理状态
//                        this.updateDetailProcessState(Collections.singletonList(detail), Collections.emptyMap());
//                    } else
                    //  保存[不备库]处理结果
                    List<PreStockResultDTO> cancelPreStockResults = processTypeMap.getOrDefault("9", Collections.emptyList());
                    if (CollectionUtils.isNotEmpty(cancelPreStockResults)) {
                        resultList.addAll(cancelPreStockResults);
                    }
                    Integer status = 0;
                    if (detail.getInterceptId() != null && StringUtils.isNotBlank(detail.getStatus())) {
                        status = Integer.parseInt(detail.getStatus());
                    } else {
                        status = statusMap.getOrDefault(detail.getId(), 2);
                    }
                    detail.setStatus(status.toString());
                    if (status.compareTo(9) == 0) {
                        detail.setProcessText("手动取消");
                    }
                    if (CollectionUtils.isNotEmpty(resultList)) {
                        for (PreStockResultDTO resultDTO : resultList) {
                            PreStockResultDO result = BeanCopyUtil.copy(resultList.get(0), PreStockResultDO.class);
                            result.setOptStatus("2"); // 已处理
                            result.setCreateUser(userDTO.getUserNo());
                            result.setUpdateUser(userDTO.getUserNo());
                            preStockResultMapper.insert(result);
                        }
                        // 更新申请项处理状态
                        this.updateDetailProcessState(Collections.singletonList(detail), Collections.emptyMap());
                    }

                    if ("7".equals(detail.getStatus()) || "10".equals(detail.getStatus()) || "9".equals(detail.getStatus())) {
                        // 保存[异常拦截|暂不处理]处理结果
                        this.updateDetailProcessState(Collections.singletonList(detail), Collections.emptyMap());

                    }

                    // 处理完成，触发回调门户
                    if (StringUtils.isNotBlank(detail.getBatchNo())) {
                        UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
                        upTaskInfoVO.setBatchNo(detail.getBatchNo());
                        OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
                        vo.setData(detail.getBatchNo());
                        upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
                        orderModifyServiceFeignApi.upTaskInfoByBatchNo(upTaskInfoVO);
                    }
                }
                // 更新申请的处理状态
                this.updateApplyFinishStatus(applyId, false);
            } finally {
                redissonUtil.unlock(key);
            }
        } else {
            return ResultVo.failure("正在处理，请耐心等待");
        }
        return ResultVo.success("手动处理成功");
    }


    @Override
    public ResultVo<List<PreStockResultDTO>> listPreStockResultByApplyNo(String applyNo) {
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo);
        applyQuery.eq(PreStockApplyDO::getApplyNo, applyNo);
        PreStockApplyDO applyInfo = preStockApplyMapper.selectOne(applyQuery);
        if (applyInfo == null) {
            return ResultVo.failure("申请号不存在");
        }
        return this.listPreStockResultByApplyId(applyInfo.getId());
    }

    @Override
    public ResultVo<List<PreStockResultDTO>> listPreStockResultByApplyId(long applyId) {
        LambdaQueryWrapper<PreStockResultDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(PreStockResultDO::getItemNo, PreStockResultDO::getApplyModelNo, PreStockResultDO::getOrderNo);
        queryWrapper.eq(PreStockResultDO::getApplyId, applyId)
                .orderByAsc(PreStockResultDO::getItemNo, PreStockResultDO::getFromId);
        List<PreStockResultDO> list = preStockResultMapper.selectList(queryWrapper);
        List<PreStockResultDTO> resultList = BeanCopyUtil.copyList(list, PreStockResultDTO.class);
        return ResultVo.success(resultList);
    }

    @Override
    public ResultVo<String> autoHandlePreStockApply(PreStockApplyHandleDTO dto) {
        StringBuilder msg = new StringBuilder();
        ResultVo<String> handleResult;
        String keyPrefix = "ops:rediss:preStock:processing:";
        String applyKey;
        List<Integer> detailStatus = Arrays.asList(2, 7, 10); // 处理中,异常拦截，暂不处理
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = Wrappers.lambdaQuery();

        if (dto.getApplyIds().size() > 1) {
            dto.setModelNos(null);
        }

        // 申请查询页面的自动处理
        for (Long applyId : dto.getApplyIds()) {
            applyKey = keyPrefix + applyId.toString();
            if (redissonUtil.tryLock(applyKey, 0, 0)) {
                try {
                    PreStockApplyDO applyInfo = preStockApplyMapper.selectById(applyId);
                    if (ObjectUtils.isEmpty(applyInfo) || !"3".equals(applyInfo.getStatus())) {
                        msg.append(applyId).append("自动处理失败,备库申请不存在");
                        continue;
                    }
                    if (ObjectUtils.isEmpty(applyInfo.getTransFlag())) {
                        applyInfo.setTransFlag(false);
                    }
                    //    <!--add by WuWeiDong 202306020  bug 11100  补库，调库申请校验追加 -->
                    ResultVo<WarehouseVO> warehouseVOResultVo = commonServiceFeignApi.getWarehouseInfoByCode(applyInfo.getWarehouseCode());
                    WarehouseDO warehouseInfo = BeanCopyUtil.copy(warehouseVOResultVo.getData(), WarehouseDO.class);
                    if (warehouseVOResultVo.isSuccess()) {

                        if (warehouseInfo.getTransferFlag().compareTo(0) == 0 && warehouseInfo.getPrestockFlag().compareTo(0) == 0) {
                            msg.append(warehouseInfo.getWarehouseCode() + "此仓库不可补库和调库。");
                            continue;
                        }
                    } else {
                        msg.append(applyInfo.getWarehouseCode() + warehouseVOResultVo.getMessage());
                        continue;
                    }

                    detailQuery.clear();
                    detailQuery.eq(PreStockDetailDO::getApplyId, applyInfo.getId())
                            .in((CollectionUtils.isNotEmpty(dto.getDetailIds())), PreStockDetailDO::getId, dto.getDetailIds())
                            .in(CollectionUtils.isNotEmpty(dto.getModelNos()), PreStockDetailDO::getModelNo, dto.getModelNos())
                            .in(PreStockDetailDO::getStatus, detailStatus)
                            .eq(PreStockDetailDO::getDelFlag, 0);
                    List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
                    if (CollectionUtils.isEmpty(detailList)) {
                        msg.append(applyInfo.getApplyNo()).append("自动处理失败,没有待处理的备库申请项");
                        continue;
                    }
                    // 获取备库申请的出库规则
                    ResultVo<List<String>> warehouseConfig = this.getWarehouseConfig(applyInfo);
                    if (!warehouseConfig.isSuccess()) {
                        msg.append(applyInfo.getApplyNo()).append("自动处理失败: </br>" + warehouseConfig.getMessage());
                        continue;
                    }
                    log.info("先行在库-{} warehouseConfig = {}", applyInfo.getApplyNo(), warehouseConfig.getData());
                    // 按项号分组处理
                    Map<Integer, List<PreStockDetailDO>> itemMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getItemNo));

                    for (Map.Entry<Integer, List<PreStockDetailDO>> map : itemMap.entrySet()) {
                        // 手动开启事务管理
                        handleResult = transactionTemplate.execute(action -> {
                            try {
                                return this.autoHandlePreStockApply(applyInfo, map.getValue(), warehouseConfig.getData(), warehouseInfo, dto.getIsIntercept());
                            } catch (Exception e) {
                                action.setRollbackOnly();
                                log.error("先行在库-自动处理失败 {} itemNo-{} {}", applyInfo.getApplyNo(), map.getKey(), e.getMessage(), e);
                                return ResultVo.failure(e.getMessage());
                            }
                        });
                        if (!handleResult.isSuccess()) {
                            msg.append(applyInfo.getApplyNo()).append("-").append(map.getKey()).append("自动处理失败: ").append(handleResult.getMessage()).append("<br/>");
                        } else {
                            // 处理成功，触发回调门户
                            if (StringUtils.isNotBlank(map.getValue().get(0).getBatchNo())) {
                                UpTaskInfoVO upTaskInfoVO = new UpTaskInfoVO();
                                upTaskInfoVO.setBatchNo(map.getValue().get(0).getBatchNo());
                                OpsSalesCommonParamVO vo = new OpsSalesCommonParamVO();
                                vo.setData(map.getValue().get(0).getBatchNo());
                                upTaskInfoVO.setCallBackParameter(JSONUtil.toJsonStr(vo));
                                orderModifyServiceFeignApi.upTaskInfoByBatchNo(upTaskInfoVO);
                            }
                        }
                    }

                } finally {
                    redissonUtil.unlock(applyKey);
                }
            } else {
                msg.append(applyId).append("正在处理,请耐心等待");
            }
        }

        return msg.length() > 0 ? ResultVo.failure(msg.toString()) : ResultVo.success("自动处理成功");
    }

    @Override
    public ResultVo<String> autoHandlePreStockApply(PreStockApplyDO applyInfo, List<PreStockDetailDO> detailList,
                                                    List<String> warehouseConfig, WarehouseDO warehouseDO, Boolean isIntercept) {

        LambdaQueryWrapper<PreStockDetailDO> detailQuery = Wrappers.lambdaQuery();
        Map<String, List<PreModelStockInfo>> stockInfoMap = new HashMap<>();
        List<PreModelStockInfo> stockInfoList = new ArrayList<>();

        //    <!--add by WuWeiDong 20231106  bug 12536  判断是否为BIN -->
        List<String> modelNos = detailList.stream().map(PreStockDetailDO::getModelNo).distinct().collect(Collectors.toList());
        Map<String, Boolean> mapBin = new HashMap<>();
        for (String modelNo : modelNos) {
            Boolean isBin = false;
            ResultVo<Boolean> resultIsBin = binServiceFeignApi.isBinModel(modelNo);
            if (resultIsBin.isSuccess() && resultIsBin.getData()) {
                isBin = true;
            }
            mapBin.put(modelNo, isBin);
        }

        for (PreStockDetailDO detail : detailList) {
            // 并发检查
            detailQuery.clear();
            detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getApplyId, PreStockDetailDO::getItemNo);
            detailQuery.eq(PreStockDetailDO::getId, detail.getId())
                    .eq(PreStockDetailDO::getStatus, detail.getStatus());
            if (detail.getStockQty() == null) {
                detailQuery.isNull(PreStockDetailDO::getStockQty);
            } else {
                detailQuery.eq(PreStockDetailDO::getStockQty, detail.getStockQty());
            }
            if (detail.getPoQty() == null) {
                detailQuery.isNull(PreStockDetailDO::getPoQty);
            } else {
                detailQuery.eq(PreStockDetailDO::getPoQty, detail.getPoQty());
            }
            if (this.getDetailInfoByWrapper(detailQuery) == null) {
                continue;
            }

            // 检查申请项是否可调库
            boolean canTransfer = false;
            if (warehouseDO.getTransferFlag().compareTo(0) != 0) {
                canTransfer = this.checkTransfer(applyInfo, detail, warehouseConfig);
            }
            stockInfoList = null;

            // 获取申请项的可调库库存
            if (canTransfer) {
                stockInfoList = stockInfoMap.get(detail.getModelNo());
                if (stockInfoList == null) {
                    String inventoryTypeCode = StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType();
                    stockInfoList = this.getPreModelInventoryInfoByStockClass(applyInfo, detail, warehouseConfig, inventoryTypeCode);
                    stockInfoList.removeIf(item -> item.getAvaQty() == 0); // 清除有效在库等于0的数据
                    stockInfoMap.put(detail.getModelNo(), stockInfoList);
                }
            }

            // 判断型号是否可拆分
            boolean canSplit = false;
            BomSelectResult bom = null;
            /**
             * 非委托在库和非Bin 可以拆分型号补库
             * 拆分型号不能申请委托在库
             */
            //bugid:17799 特殊bom
            if ("1".equals(applyInfo.getApplyType()) && !mapBin.getOrDefault(detail.getModelNo(), false)) {
                //bugid:17799 c14717 20250617
                BomSelectParam param = new BomSelectParam();
                param.setCustomerCode(applyInfo.getCustomerNo());
                param.setModelNo(detail.getModelNo());
                ResultVo<BomSelectResult> resultVo = bomSelectorFeignApi.bomSelector(param);
                if(resultVo.isSuccess()){
                    bom = resultVo.getData();
                    canSplit = bom.getBomVersionsList().get(0).getBomType();
                } else if(BomSelectEnum.NONE.getCode().equals(resultVo.getCode())){
                    canSplit = false;
                } else {
                    return ResultVo.failure("处理失败,"+resultVo.getMessage());
                }

            }

            // 可拆分整型号先执行调库，再拆分处理
            if (canSplit) {
                // 先整型号调库处理(仅调库)
                if (canTransfer && CollectionUtils.isNotEmpty(stockInfoList)) {
                    ResultVo resultVo = this.handleDetail(applyInfo, detail, stockInfoMap.get(detail.getModelNo()), canTransfer, false, isIntercept);
                    if (!resultVo.isSuccess()) {
                        return resultVo;
                    }
                }
                // 整型号调库处理后，计算待处理数量
                int quantity = detail.getQuantity() - Optional.ofNullable(detail.getStockQty()).orElse(0) - Optional.ofNullable(detail.getPoQty()).orElse(0);
                if (quantity > 0) {
                    // 型号拆分处理
                    List<PreStockDetailDO> detailTemps = this.modelSplitHandle(detail, bom);

                    // 拆分型号处理
                    for (PreStockDetailDO splitDetail : detailTemps) {
                        // 获取拆分型号库存
                        if (canTransfer) {
                            if (stockInfoMap.get(splitDetail.getModelNo()) == null) {
                                String inventoryTypeCode = StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType();
                                stockInfoList = this.getPreModelInventoryInfoByStockClass(applyInfo, detail, warehouseConfig, inventoryTypeCode);
                                stockInfoList.removeIf(item -> item.getAvaQty() == 0); // 清除有效在库等于0的数据
                                stockInfoMap.put(splitDetail.getModelNo(), stockInfoList); // bug-8890
                            }
                        }
                        ResultVo resultVo = this.handleDetail(applyInfo, splitDetail, stockInfoMap.get(splitDetail.getModelNo()), canTransfer, warehouseDO.getPrestockFlag().compareTo(0) != 0, isIntercept);
                        if (!resultVo.isSuccess()) {
                            return resultVo;
                        }
                    }
                }
            } else {
                // 单型号处理
                ResultVo resultVo = this.handleDetail(applyInfo, detail, stockInfoMap.get(detail.getModelNo()), canTransfer, warehouseDO.getPrestockFlag().compareTo(0) != 0, isIntercept);
                if (!resultVo.isSuccess()) {
                    return resultVo;
                }
            }

            // 更新申请的处理状态
            this.updateApplyFinishStatus(applyInfo.getId(), false);
        }
        return ResultVo.success("处理成功");
    }

    /**
     * 采购或调整处理
     *
     * @param applyInfo     主表数据
     * @param detail        明细数据
     * @param stockInfoList 生成调整或采购 项
     * @param canTransfer   是否调拨
     * @param canPurchase   是否采购
     * @param canPurchase   是否检查拦截条件
     * @return
     */
    private ResultVo<String> handleDetail(PreStockApplyDO applyInfo, PreStockDetailDO detail, List<PreModelStockInfo> stockInfoList,
                                          boolean canTransfer, boolean canPurchase, Boolean isIntercept) {
        /**
         * detail.getQuantity() 在计算中会扣减，暂用appQty保存申请数量
         */
        Integer appQty = detail.getQuantity();

        // 初始化申请项待处理数量

        int quantity = detail.getQuantity() - Optional.ofNullable(detail.getStockQty()).orElse(0) - Optional.ofNullable(detail.getPoQty()).orElse(0);
        log.info("在库申请{} 申请项{} 申请型号{}, 申请数量{},已调库{},已采购{},待处理数量{}", applyInfo.getApplyNo(),
                detail.getId(), detail.getModelNo(), detail.getQuantity(), detail.getStockQty(), detail.getPoQty(), quantity);
        detail.setQuantity(quantity);

        // 最新包装数量、偶数订货标识
        ResultVo<ProductRemark> productRemarkInfo = productServiceFeignApi.getProductRemarkByModelNo(detail.getModelNo());
        if (!productRemarkInfo.isSuccess()) {
            return ResultVo.failure("处理失败,无法获取最小包装数: " + detail.getModelNo() + productRemarkInfo.getMessage());
        }
        if (productRemarkInfo.getData() == null) {
            return ResultVo.failure("处理失败,无法获取最小包装数: " + detail.getModelNo() + "型号不存在");
        }

        // 1) 生成调库处理项
        List<PreStockResultDTO> transferList = Collections.emptyList();
        if (canTransfer && CollectionUtils.isNotEmpty(stockInfoList) && detail.getQuantity().compareTo(0) > 0) {
            transferList = this.createTransferProcessResult(applyInfo, detail, stockInfoList, productRemarkInfo.getData());
        }
        // 2) 生成预占BIN在途
        List<PreStockResultDTO> moveList = Collections.emptyList();
        if (canTransfer && detail.getQuantity().compareTo(0) > 0) {
            moveList = this.createMoveProcessResult(applyInfo, detail);
        }
        // 3) 生成采购处理项
        List<PreStockResultDTO> purchaseList = Collections.emptyList();
        if (canPurchase && detail.getQuantity().compareTo(0) > 0) {
            purchaseList = this.createPurchaseProcessResult(applyInfo, detail);
        }

        // 最小包装数订货处理
        this.converUnPackPoQty(applyInfo, detail, transferList, moveList, purchaseList, productRemarkInfo.getData());

        // 过滤先行在库处理拦截规则
        List<PreStockResultDTO> resultList = this.filterInterceptedProcessingItems(applyInfo, detail, transferList, moveList, purchaseList, isIntercept);

        // 执行调库处理
        if (CollectionUtils.isNotEmpty(transferList)) {
            try {
                //    <!--add by WuWeiDong 20231009  bug 12216  调拨时间限制 -->申请补库的数小于bin数据,并小于限制时间,才执行设库.
                if (isIntercept) { //检查拦截条件
                    Integer binQty = 0;
                    ResultVo<BindataVO> binResultVo = binServiceFeignApi.getBindataByModelNo(detail.getModelNo(), "ALL"); //取自动化Bin
                    if (binResultVo.isSuccess() && Objects.nonNull(binResultVo.getData())) {
                        binQty = Optional.ofNullable(binResultVo.getData().getQtyBin()).orElse(0);
                    }
                    if (appQty.compareTo(binQty) <= 0 && binQty.compareTo(0) == 1) {
                        if (LIMIT_DAY.compareTo(-1) <= 0) {
                            ResultVo<DataTypeVO> resultVo = dictCommonService.getDataTypeCodesInfo("9002", "15"); // 取限制时间。
                            if (resultVo.isSuccess() && Objects.nonNull(resultVo.getData())) {
                                LIMIT_DAY = Integer.parseInt(Optional.ofNullable(resultVo.getData().getExtNote1()).orElse("0"));
                            }
                        }
                        if (LIMIT_DAY.compareTo(0) == 1) {
                            Date limitDate = DateUtil.addDay(DateUtil.getCurrentDate(), LIMIT_DAY);
                            if (DateUtil.isAfterDate(detail.getDlvDate(), limitDate)) {
                                if (PreStockDetailStatusEnum.processing.getCode().equalsIgnoreCase(detail.getStatus())) {
                                    LambdaUpdateWrapper<PreStockDetailDO> updateWrapper = Wrappers.lambdaUpdate();
                                    updateWrapper.eq(PreStockDetailDO::getId, detail.getId())
                                            .eq(PreStockDetailDO::getStatus, PreStockDetailStatusEnum.processing.getCode())
                                            .set(PreStockDetailDO::getStatus, PreStockDetailStatusEnum.no_process.getCode())  //状态：处理中（2） ->暂不处理（10）
                                            .set(PreStockDetailDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                                    preStockDetailMapper.update(new PreStockDetailDO(), updateWrapper);

                                }
                                log.info("在库申请{} 申请项{} 申请型号{} 超过限制时间{},交货期{}，截止日期{}", applyInfo.getApplyNo(),
                                        detail.getId().toString(), detail.getModelNo(), LIMIT_DAY.toString(), detail.getDlvDate().toString(), limitDate.toString());
                                return ResultVo.success();
                            }
                        }
                    }
                }
                ResultVo<String> transResult = this.createTransferOrder(applyInfo, detail, transferList);
                if (!transResult.isSuccess()) {
                    return transResult;
                }
            } catch (Exception e) {
                log.error("先行在库申请{} 执行调拨处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                throw new BusinessException("执行调拨处理失败,请稍后重试. " + e.getMessage());
            }
        }
        // 执行预占处理
        if (CollectionUtils.isNotEmpty(moveList)) {
            try {
                ResultVo<String> moveResult = this.createMoverOrder(applyInfo, detail, moveList);
                if (!moveResult.isSuccess()) {
                    return moveResult;
                }
            } catch (Exception e) {
                log.error("先行在库申请{} 执行预占BIN在途处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                return ResultVo.failure("执行预占BIN在途处理失败-" + e.getMessage());
            }
        }

        // 执行采购处理
        if (CollectionUtils.isNotEmpty(purchaseList)) {
            try {
                // ResultVo<String> purchaseResult = this.createPurchaseOrder(applyInfo, detail, purchaseList);
                ResultVo<String> purchaseResult = preStockNewService.createPurchaseOrder(applyInfo, detail, purchaseList,true);
                if (!purchaseResult.isSuccess()) {
                    return purchaseResult;
                }
            } catch (Exception e) {
                log.error("先行在库申请{} 执行采购处理失败: {}", applyInfo.getApplyNo(), e.getMessage(), e);
                return ResultVo.failure("执行采购处理失败-" + e.getMessage());
            }
        }
        // 记录其他处理
        if ("9".equals(detail.getStatus())) {
            // 保存[不备库]处理结果
            PreStockResultDO result = BeanCopyUtil.copy(resultList.get(0), PreStockResultDO.class);
            result.setOptStatus("2"); // 已处理
            result.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            result.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            preStockResultMapper.insert(result);
            // 更新申请项处理状态
            this.updateDetailProcessState(Collections.singletonList(detail), Collections.emptyMap());

        } else if ("7".equals(detail.getStatus()) || "10".equals(detail.getStatus())) {
            // 保存[异常拦截|暂不处理]处理结果
            this.updateDetailProcessState(Collections.singletonList(detail), Collections.emptyMap());

        }
        return ResultVo.success();
    }

    /**
     * 根据处理类型分组
     */
    private void groupByProcessType(List<PreStockResultDTO> resultList, List<PreStockResultDTO> transferList, List<PreStockResultDTO> purchaseList) {
        for (PreStockResultDTO result : resultList) {
            // 1-采购 || 2-Bin采购
            if ("1".equals(result.getProcessType()) || "2".equals(result.getProcessType())) {
                purchaseList.add(result);
            }
            // 4-同仓调库 || 5-异仓调拨
            if ("4".equals(result.getProcessType()) || "5".equals(result.getProcessType())) {
                transferList.add(result);
            }
        }
    }


    /**
     * 更新申请项处理状态
     *
     * @param detailList 申请项
     */
    private void updateDetailProcessState(List<PreStockDetailDO> detailList, Map<Long, String> orderMap) {
        LambdaUpdateWrapper<PreStockDetailDO> updateWrapper = Wrappers.lambdaUpdate();
        PreStockDetailDO detailDO = new PreStockDetailDO();

        for (PreStockDetailDO detail : detailList) {
            if (detail.getQuantity().compareTo(0) <= 0) {
                detail.setStatus(PreStockDetailStatusEnum.finished.getCode()); // 已处理
            }
            if (StringUtils.isNotBlank(orderMap.get(detail.getId()))) {
                if (StringUtils.isBlank(detail.getOrderNos())) {
                    detail.setOrderNos(orderMap.get(detail.getId()));
                } else if (detail.getOrderNos().length() + orderMap.get(detail.getId()).length() < 400) { // 单号长度超过400截断
                    detail.setOrderNos(detail.getOrderNos() + ";" + orderMap.get(detail.getId()));
                }
            }
            detail.setReplyStatus(1); // 待回调门户信息
            Boolean noCancel = !PreStockApplyStatusEnum.cancel.getCode().equalsIgnoreCase(detail.getStatus());
            updateWrapper.clear();
            updateWrapper.set(PreStockDetailDO::getStatus, detail.getStatus())
                    .set(noCancel, PreStockDetailDO::getStockQty, detail.getStockQty())
                    .set(noCancel, PreStockDetailDO::getPoQty, detail.getPoQty())
                    .set(noCancel, PreStockDetailDO::getPrepareQty, detail.getPrepareQty())
                    .set(PreStockDetailDO::getSupplierCode, detail.getSupplierCode())
                    .set(PreStockDetailDO::getInterceptId, detail.getInterceptId())
                    .set(PreStockDetailDO::getProcessText, detail.getProcessText())
                    .set(StringUtils.isNotBlank(detail.getOrderNos()), PreStockDetailDO::getOrderNos, detail.getOrderNos())
                    .set(PreStockDetailDO::getInventoryPropertyId, detail.getInventoryPropertyId()) // 记录库存属性ID Bug-9942
                    .set(PreStockDetailDO::getReplyStatus, detail.getReplyStatus())
                    .set(PreStockDetailDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
            updateWrapper.eq(PreStockDetailDO::getId, detail.getId());
            preStockDetailMapper.update(detailDO, updateWrapper);
        }
    }

    @Override
    public void autoProcessInterceptedApplyDetail() {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getApplyId);
        detailQuery.eq(PreStockDetailDO::getStatus, "10")
                //.eq(PreStockDetailDO::getProcessText, "等待大库补库到货后调库抽取（每天自动判断能否调库抽取）")
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            log.info("没有可执行的等待大库补库处理的先行在库申请项");
            return;
        }

        Map<Long, List<PreStockDetailDO>> detailMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getApplyId));
        PreStockApplyHandleDTO dto;

        for (Map.Entry<Long, List<PreStockDetailDO>> entry : detailMap.entrySet()) {
            dto = new PreStockApplyHandleDTO();
            dto.setApplyIds(Collections.singletonList(entry.getKey()));
            dto.setDetailIds(entry.getValue().stream().map(PreStockDetailDO::getId).collect(Collectors.toList()));
            dto.setIsIntercept(true);
            this.autoHandlePreStockApply(dto);
        }
    }

    /**
     * 1)采购单号取消。
     * 2）调拨（trans_order）取消。
     *
     * @param orderNo 采购单号 /Trans_order的transNo+'-'+itemNO
     * @return
     */
    @Override
    // @Transactional
    public ResultVo<String> purchaseOrderCancelHandle(String orderNo) {
        log.info("先行在库-被取消采购单: {}", orderNo);
        LambdaQueryWrapper<PreStockResultDO> resultQuery = new LambdaQueryWrapper<>();
        resultQuery.eq(PreStockResultDO::getOrderNo, orderNo)
                   .eq(PreStockResultDO::getOptStatus, "2"); // 已处理
        PreStockResultDO resultInfo = preStockResultMapper.selectOne(resultQuery);
        if (resultInfo == null) {
            LambdaQueryWrapper<PreStockResultDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(PreStockResultDO::getPrepareOrder, orderNo);
            List<PreStockResultDO> preStockResultDOS = preStockResultMapper.selectList(lambdaQueryWrapper);
            if (CollectionUtils.isEmpty(preStockResultDOS)) {
                return ResultVo.failure("处理项不存在 " + orderNo);
            }
            for(PreStockResultDO preStockResultDO : preStockResultDOS) {
                handlePreStockStatus(preStockResultDO,orderNo,true);
            }
            return ResultVo.success("取消处理项");
        } else {
            handlePreStockStatus(resultInfo,orderNo,false);
            return ResultVo.success("取消处理项");
        }
    }

    public void handlePreStockStatus(PreStockResultDO resultInfo,String orderNo,boolean transOrderFlag) {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getId, resultInfo.getFromId());
        PreStockDetailDO detailInfo = preStockDetailMapper.selectOne(detailQuery);

        // 更新处理项状态
        resultInfo.setOptStatus("4"); // 取消处理
        //更新备注
        String remark = "";
        switch (resultInfo.getProcessType()) {
            case "1":
            case "2":
                remark = "采购取消";
                break;
            case "3": // 预约在途
                remark = "预约取消";
                break;
            case "4": // 调库
            case "5": // 异仓调拨
                remark = "调库取消";
                break;
            default:
                remark = "取消";
                break;
        }
        resultInfo.setRemark(remark);
        if (transOrderFlag) {
            resultInfo.setPrepareOrder("");
        }
        resultInfo.setUpdateTime(new Date());
        preStockResultMapper.updateById(resultInfo);

        //    <!--add by WuWeiDong 20231026  bug 12235
        //    .1）若有几笔采购或采购和调拨其中一下采购退单，prestock_detail的状态都改为3.
        //    .2）若还存在调拨或采购项，调拨+采购数量>0，就不能取消备库吧，就把状态改为完成-->

        String newStatus = PreStockDetailStatusEnum.purchaseCancel.getCode();
        Integer processQty = Optional.ofNullable(detailInfo.getStockQty()).orElse(0)
                + Optional.ofNullable(detailInfo.getPoQty()).orElse(0)
                + Optional.ofNullable(detailInfo.getPrepareQty()).orElse(0)
                - resultInfo.getOrderQty();//已处理数量
        Integer newQty = detailInfo.getQuantity() - resultInfo.getOrderQty();
        if (processQty.compareTo(newQty) > 0) {
            newStatus = PreStockDetailStatusEnum.finished.getCode();
        }


        // 更新申请项明细状态为3退单
        if (resultInfo.getProcessType().equalsIgnoreCase(PreStockProcessTypeEnum.purchase.getCode())
                || resultInfo.getProcessType().equalsIgnoreCase(PreStockProcessTypeEnum.binPurchase.getCode())) {
            detailInfo.setPoQty(detailInfo.getPoQty() - resultInfo.getOrderQty());
            detailInfo.setReplyStatus(1); // 待回调门户信息
        } else if (resultInfo.getProcessType().equalsIgnoreCase(PreStockProcessTypeEnum.trans.getCode())
                || resultInfo.getProcessType().equalsIgnoreCase(PreStockProcessTypeEnum.transDiff.getCode())) {
            detailInfo.setStockQty(detailInfo.getStockQty() - resultInfo.getOrderQty());
        } else if (resultInfo.getProcessType().equalsIgnoreCase(PreStockProcessTypeEnum.prepareBin.getCode())) {
            detailInfo.setPrepareQty(detailInfo.getPrepareQty() - resultInfo.getOrderQty());
        }
        // 判断是否prestock_result里的数据都取消  如果存在其中一项没有取消 则detail该单需是处理中
        LambdaQueryWrapper<PreStockResultDO> resultQuery = new LambdaQueryWrapper<>();
        resultQuery.eq(PreStockResultDO::getFromId, resultInfo.getFromId()).ne(PreStockResultDO::getOptStatus, "4");
        if (preStockResultMapper.selectCount(resultQuery) > 0) {
            detailInfo.setStatus(PreStockDetailStatusEnum.processing.getCode()); // 待处理
        } else {
            detailInfo.setStatus(newStatus); // 已退单
        }

        if (StringUtils.isNotBlank(detailInfo.getOrderNos())) {
            String newOrders = detailInfo.getOrderNos().replace(orderNo + ";", "");
            detailInfo.setOrderNos(newOrders);
        }
        if (StringUtils.isNotBlank(detailInfo.getPrepareOrders())) {
            String newOrders = detailInfo.getPrepareOrders().replace(orderNo + ";", "");
            detailInfo.setPrepareOrders(newOrders);
        }
        if (transOrderFlag) {
            if (StringUtils.isNotBlank(resultInfo.getOrderNo())) {
                if (StringUtils.isNotBlank(detailInfo.getOrderNos())) {
                    String newOrders = detailInfo.getOrderNos().replace(resultInfo.getOrderNo() + ";", "");
                    detailInfo.setOrderNos(newOrders);
                }
            }
        }
        preStockDetailMapper.updateById(detailInfo);


        // 更新申请状态为待处理
        PreStockApplyDO applyInfo = new PreStockApplyDO();
        applyInfo.setId(resultInfo.getApplyId());
        applyInfo.setStatus(PreStockApplyStatusEnum.processing.getCode()); // 待处理
        applyInfo.setUpdateTime(new Date());
        preStockApplyMapper.updateById(applyInfo);
        log.info("先行在库-被取消采购单-已重置处理状态: {}", orderNo);

        if (StringUtils.isNotBlank(resultInfo.getOrderNo())) {
            String[] split = resultInfo.getOrderNo().split("-");
            if(split.length == 2) {
                // 如果有调库 则调库单状态修改为取消
                transOrderMapper.updateStatusWithCancel(String.valueOf(resultInfo.getFromId()),split[0], Integer.parseInt(split[1]));
            }
        }
    }

    @Override
    public ResultVo<PreStockApplyDetailDTO> findPreStockApplyByNo(String applyNo) {
        QueryWrapper<PreStockApplyDO> applyQuery = new QueryWrapper<>();
        applyQuery.eq("apply_no", applyNo);

        PreStockApplyDO applyDO = preStockApplyMapper.selectOne(applyQuery);
        if (applyDO == null) {
            return ResultVo.failure("申请号不存在" + applyNo);
        }

        PreStockApplyDetailDTO dto = BeanCopyUtil.copy(applyDO, PreStockApplyDetailDTO.class);
        if (PublicUtil.isNotEmpty(applyDO.getCustomerNos())) {
            String customerNos = applyDO.getCustomerNos();
            String[] split = customerNos.split(",");
            List<String> list = Arrays.asList(split);
            dto.setShareCustomerNos(list);
        }
        return ResultVo.success(dto);
    }

    @Override
    public ResultVo<List<PreStockDetailDTO>> findPreStockDetailByNo(Long applyId, String modelNo) {
        QueryWrapper<PreStockDetailDO> detailQuery = new QueryWrapper<>();
        detailQuery.eq("apply_id", applyId);
        detailQuery.eq("model_no", modelNo);

        List<PreStockDetailDO> list = preStockDetailMapper.selectList(detailQuery);
        List<PreStockDetailDTO> dtoList = new ArrayList<>();
        PreStockDetailDTO dto;
        for (PreStockDetailDO detailDO : list) {
            dto = BeanCopyUtil.copy(detailDO, PreStockDetailDTO.class);
            dto.setRohs10(this.getRohs10(detailDO.getSpecExpType()));
            dto.setQuantity(list.stream().mapToInt(PreStockDetailDO::getQuantity).sum());
            dtoList.add(dto);
        }
        return ResultVo.success(dtoList);
    }

    @Override
    public ResultVo<PageInfo<PreStockDetailView>> listPreStockDetail(PreStockDetailRequest request) {
        if (StringUtils.isNotEmpty(request.getDateType()) && request.getEndTime() != null) {
            request.setEndTime(DateUtil.getEndTime(request.getEndTime()));
        }

        LambdaQueryWrapper<PreStockDetailView> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotBlank(request.getApplyNo()), PreStockDetailView::getApplyNo, request.getApplyNo())
                .eq(StringUtils.isNotBlank(request.getDeptNo()), PreStockDetailView::getDeptNo, request.getDeptNo())
                .eq(StringUtils.isNotBlank(request.getApplyModelNo()), PreStockDetailView::getApplyModelNo, request.getApplyModelNo())
                .eq(StringUtils.isNotBlank(request.getModelNo()), PreStockDetailView::getModelNo, request.getModelNo())
                .eq(StringUtils.isNotBlank(request.getCustomerNo()), PreStockDetailView::getCustomerNo, request.getCustomerNo())
                .in(CollectionUtils.isNotEmpty(request.getApplyStatus()), PreStockDetailView::getApplyStatus, request.getApplyStatus())
                .in(CollectionUtils.isNotEmpty(request.getDetailStatus()), PreStockDetailView::getDetailStatus, request.getDetailStatus())
                .eq(StringUtils.isNotBlank(request.getApplyType()), PreStockDetailView::getApplyType, request.getApplyType())
                .eq(StringUtils.isNotBlank(request.getReplType()), PreStockDetailView::getReplType, request.getReplType())
                .eq(StringUtils.isNotBlank(request.getWarehouseCode()), PreStockDetailView::getWarehouseCode, request.getWarehouseCode())
                .eq(StringUtils.isNotBlank(request.getIndCode()), PreStockDetailView::getIndCode, request.getIndCode())
                .eq(StringUtils.isNotBlank(request.getPplNo()), PreStockDetailView::getPplNo, request.getPplNo())
                .eq(StringUtils.isNotBlank(request.getProjectNo()), PreStockDetailView::getProjectNo, request.getProjectNo())
                .eq(StringUtils.isNotBlank(request.getGroupCustomerNo()), PreStockDetailView::getGroupCustomerNo, request.getGroupCustomerNo())
                .eq(StringUtils.isNotBlank(request.getShikomiClass()), PreStockDetailView::getShikomiClass, request.getShikomiClass())
                .eq(StringUtils.isNotBlank(request.getInventoryType()), PreStockDetailView::getStockType, request.getInventoryType())
                .eq(StringUtils.isNotBlank(request.getSupplierCode()), PreStockDetailView::getSupplierCode, request.getSupplierCode())
                .like(StringUtils.isNotBlank(request.getTransferNo()), PreStockDetailView::getOrderNos, request.getTransferNo())
                .like(StringUtils.isNotBlank(request.getOrderNo()), PreStockDetailView::getOrderNos, request.getOrderNo())
                .like(StringUtils.isNotBlank(request.getPrepareOrders()), PreStockDetailView::getPrepareOrders, request.getPrepareOrders())
                .eq(PreStockDetailView::getDelFlag, 0);
        if (request.getLotQueryType() != null) {
            if (CollectionUtils.isEmpty(request.getLotQueryData())) {
                return ResultVo.failure("请输入批量查询条件");
            }
            switch (request.getLotQueryType()) {
                case 1:  // 申请号
                    query.in(PreStockDetailView::getApplyNo, request.getLotQueryData());
                    break;
                case 2: //处理型号
                    query.in(PreStockDetailView::getModelNo, request.getLotQueryData());
                    break;
                case 3: //采购单号
                case 4: //调库单号
                    List<Long> ids = preStockDetailMapper.getPrestockDetailIDByOrders(request.getLotQueryData());
                    if (CollectionUtils.isNotEmpty(ids)) {
                        query.in(PreStockDetailView::getDetailId, ids);
                    } else {
                        log.error(" 采购/调库单号批量查询无对应数据：{}", JSONUtil.toJsonStr(request.getLotQueryData()));
                        return ResultVo.failure("采购/调库单号批量查询无对应数据");
                    }
                    break;
                default:
                    log.error("listPreStockDetail批量查询无对应条件：{}", request.getLotQueryType());
                    return ResultVo.failure("无对应批量查询条件");
            }
        }
        if (StringUtils.isNotBlank(request.getDateType())) {
            switch (request.getDateType()) {
                case "1":
                    query.ge((request.getStartTime() != null), PreStockDetailView::getApplyTime, request.getStartTime())
                            .le((request.getEndTime() != null), PreStockDetailView::getApplyTime, request.getEndTime());
                    break;
                case "2":
                    query.ge((request.getStartTime() != null), PreStockDetailView::getApproveTime, request.getStartTime())
                            .le((request.getEndTime() != null), PreStockDetailView::getApproveTime, request.getEndTime());
                    break;
                default:
                    log.error("listPreStockDetail无对应日期查询条件：{}", request.getLotQueryType());
                    return ResultVo.failure("无对应日期查询条件");
            }
        }

        query.orderByDesc(PreStockDetailView::getApplyTime);
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<PreStockDetailView> list = preStockDetailViewMapper.selectList(query);
        PageInfo<PreStockDetailView> pageInfo = PageInfo.of(list);

        Map<String, String> nameMap = new HashMap<>(pageInfo.getList().size());
        String[] nos;
        StringBuilder orderNos = new StringBuilder(50);
        StringBuilder transferNos = new StringBuilder(50);
        StringBuilder prepareOrders = new StringBuilder(50);

        for (PreStockDetailView detail : pageInfo.getList()) {
            // 部门名称
            if (!nameMap.containsKey(detail.getDeptNo())) {
                nameMap.put(detail.getDeptNo(), opsCommonService.getDeptNameByNo(detail.getDeptNo()));
            }
            detail.setDeptName(nameMap.getOrDefault(detail.getDeptNo(), detail.getDeptNo()));
            // 客户名称
            if (!nameMap.containsKey(detail.getCustomerNo())) {
                nameMap.put(detail.getCustomerNo(), opsCommonService.getCustomerNameByNo(detail.getCustomerNo()));
            }
            detail.setCustomerName(nameMap.getOrDefault(detail.getCustomerNo(), detail.getCustomerNo()));
            // 用户名称
            if (StringUtils.isNotBlank(detail.getUserNo()) && !nameMap.containsKey(detail.getUserNo())) {
                nameMap.put(detail.getUserNo(), opsCommonService.getCustomerNameByNo(detail.getUserNo()));
            }
            detail.setUserName(nameMap.getOrDefault(detail.getUserNo(), detail.getUserNo()));

            // 区分调库单号和采购单号
            if (StringUtils.isNotBlank(detail.getOrderNos())) {
                nos = detail.getOrderNos().split(";");
                orderNos.setLength(0);
                transferNos.setLength(0);
                for (String no : nos) {
                    if (no.startsWith("77") || no.startsWith("88") || (no.startsWith("V") && !no.startsWith("VT"))) {
                        orderNos.append(no).append(";");
                    } else {
                        transferNos.append(no).append(";");
                    }
                }
                detail.setOrderNos(orderNos.toString());
                detail.setTransferNos(transferNos.toString());
                detail.setZbNo(prepareOrders.toString());
            }
            // E金额
            if(detail.getEprice() != null && detail.getEprice().compareTo(BigDecimal.ZERO) >= 0) {
                detail.setEprice(detail.getEprice().setScale(2, RoundingMode.HALF_UP));
                detail.setEAmount(detail.getEprice().multiply(new BigDecimal(detail.getQuantity())).setScale(2, RoundingMode.HALF_UP));
            }
        }
        return ResultVo.success(pageInfo);
    }

    @Override
    public ExcelUtil exportPreStockDetail(PreStockDetailRequest request) {
        request.setPageNum(1);
        request.setPageSize(2000);
        ExcelUtil excel = new ExcelUtil(FileUtil.getTemplate("template/preStockDetails.xlsx"));
        int row = 1;
        int colIndex;
        try {
            while (true) {
                ResultVo<PageInfo<PreStockDetailView>> resultVo = this.listPreStockDetail(request);
                if (!resultVo.isSuccess()) {
                    throw new BusinessException(resultVo.getMessage());
                }
                PageInfo<PreStockDetailView> pageInfo = resultVo.getData();
                if (pageInfo.getTotal() > 150000) {
                    throw new BusinessException("数据超过15W，请缩小日期范围。 ");
                }
                for (PreStockDetailView detail : pageInfo.getList()) {
                    colIndex = 0;
                    excel.setCellValue(row, colIndex++, detail.getApplyNo());
                    excel.setCellValue(row, colIndex++, detail.getItemNo());
                    excel.setCellValue(row, colIndex++, detail.getApplyModelNo());
                    excel.setCellValue(row, colIndex++, detail.getModelNo());
                    excel.setCellValue(row, colIndex++, PreStockDetailStatusEnum.getNameByCode(detail.getDetailStatus()));
                    excel.setCellValue(row, colIndex++, detail.getQuantity());
                    excel.setCellValue(row, colIndex++, detail.getQtyBin());
                    excel.setCellValue(row, colIndex++, PreStockNewFlagEnum.getNameByCode(detail.getNewFlag()));
                    excel.setCellValue(row, colIndex++, detail.getDlvDate());
                    excel.setCellValue(row, colIndex++, detail.getDlvDateJP());
                    excel.setCellValue(row, colIndex++, detail.getSupplierCode());
                    if (detail.getEprice()  != null) {
                        excel.setCellValue(row, colIndex++, detail.getEprice().toString());
                        excel.setCellValue(row, colIndex++, detail.getEprice().multiply(new BigDecimal(detail.getQuantity())).setScale(2, RoundingMode.HALF_UP).toString()); // E金额 = e价 * 数量
                    } else {
                        excel.setCellValue(row, colIndex++, "");
                        excel.setCellValue(row, colIndex++, "");
                    }
                    excel.setCellValue(row, colIndex++, detail.getTransferNos());
                    // excel.setCellValue(row, colIndex++, detail.getZbNo());
                    excel.setCellValue(row, colIndex++, detail.getOrderNos());
                    excel.setCellValue(row, colIndex++, detail.getPrepareOrders());
                    excel.setCellValue(row, colIndex++, detail.getStockQty());
                    excel.setCellValue(row, colIndex++, detail.getPoQty());
                    excel.setCellValue(row, colIndex++, PreStockApplyTypeEnum.getNameByCode(detail.getApplyType()));
                    excel.setCellValue(row, colIndex++, PreStockReplTypeEnum.getNameByCode(detail.getReplType()));
                    excel.setCellValue(row, colIndex++, detail.getDeptNo());
                    excel.setCellValue(row, colIndex++, detail.getDeptName());
                    excel.setCellValue(row, colIndex++, detail.getCustomerNo());
                    excel.setCellValue(row, colIndex++, detail.getCustomerName());
                    excel.setCellValue(row, colIndex++, detail.getUserNo());
                    excel.setCellValue(row, colIndex++, detail.getUserName());
                    excel.setCellValue(row, colIndex++, detail.getWarehouseCode());
                    excel.setCellValue(row, colIndex++, detail.getStockType());
                    excel.setCellValue(row, colIndex++, detail.getPplNo());
                    excel.setCellValue(row, colIndex++, detail.getProjectNo());
                    excel.setCellValue(row, colIndex++, detail.getGroupCustomerNo());
                    excel.setCellValue(row, colIndex++, PreStockApplyStatusEnum.getNameByCode(detail.getApplyStatus()));
                    excel.setCellValue(row, colIndex++, detail.getApplyTime());
                    excel.setCellValue(row, colIndex++, detail.getApplyPsn());
                    excel.setCellValueWithHHMM(row, colIndex, detail.getApproveTime());
                    row++;
                }
                //flushRows方法可以将达到行数的数据通过文件流输出到硬盘文件，并清空了缓存，避免大数据占用内存导致内存溢出的问题。
                excel.getSxssfSheet().flushRows();
                if (pageInfo.isIsLastPage()) {
                    break;
                }
                request.setPageNum(request.getPageNum() + 1);
            }
        } catch (Exception e) {
            log.error("导出先行在库申请项清单: params = {}, {}", request, e.getMessage(), e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
        return excel;
    }

    @Override
    public ResultVo<String> handleSMSPreStockApply(PreStockApplyDetailDTO createDto) {
//        // 保存申请信息
//        ResultVo<String> applyResult = this.createPreStockApply(createDto);
//        log.info("保存门户备库申请: {} >>> {}", createDto.getApplyNo(), applyResult);
//        if (!applyResult.isSuccess()) {
//            return applyResult;
//        }

        // 是否自动处理备库申请
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "8");
        if (dataTypeCodesInfo.getData() == null || !"1".equals(dataTypeCodesInfo.getData().getExtNote1())) {
            log.info("【自动处理先行在库申请-已关闭】: 将不会自动处理先行在库申请{}", createDto.getApplyNo());
            return ResultVo.success("已接入申请");
        }

        // 处理申请 05-18 暂时不自动处理
        PreStockApplyHandleDTO dto = new PreStockApplyHandleDTO();
        dto.setApplyIds(Collections.singletonList(createDto.getId()));
        ResultVo<String> handleResult = this.autoHandlePreStockApply(dto);
        log.info("处理备库申请: {} >>> {}", createDto.getApplyNo(), handleResult);
        if (!handleResult.isSuccess()) {
            throw new BusinessException(handleResult.getMessage());
        }
        return ResultVo.success("处理完成");
    }

    /**
     * 先行在库申请为专备时,检查允许备库仓库
     * (允许备库类别: 物流中心专用和分库的通用与专用,不包括物流中心通用在库类型)
     *
     * @param applyInfo 申请信息
     * @return string
     */
    private ResultVo<String> checkPreStockWarehouse(PreStockApplyDO applyInfo) {
        if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType())) {
            return ResultVo.success("SHIKOMI申请,无需检查允许备库仓库");
        }
        ResultVo<List<String>> configResult = this.getWarehouseConfig(applyInfo);
        if (!configResult.isSuccess()) {
            log.error("检查允许备库仓库失败: {}", configResult);
            return ResultVo.failure("检查允许备库仓库: " + configResult.getMessage());
        }

        // 分库/委托在库 通用在库备库允许
        if (InventoryTypeEnum.TY.getCode().equals(applyInfo.getStockType()) && CollectionUtils.isNotEmpty(configResult.getData())) {
            return ResultVo.success("仓库允许备库");
        }

        if (configResult.getData().contains(applyInfo.getWarehouseCode())) {
            return ResultVo.success("仓库允许备库");
        } else {
            return ResultVo.failure("检查允许备库仓库,该专备允许备库仓库为: " + configResult.getData());
        }
    }

    /**
     * 设置申请型号是新增型号或增加旧有备库型号数量
     *
     * @param applyInfo  申请信息
     * @param detailList 申请项信息
     */
    private void setNewFlag(PreStockApplyDO applyInfo, List<PreStockDetailDO> detailList) {
        // shikomi不做该检验
        if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType()) || CollectionUtils.isEmpty(detailList)) {
            return;
        }
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? applyInfo.getCustomerNo() : applyInfo.getUserNo();
        long inventoryPropertyId = this.getOpsInventoryPropertyId(applyInfo.getStockType(), customerNo, applyInfo.getGroupCustomerNo(), applyInfo.getPplNo(), applyInfo.getProjectNo());
        String[] modelNos = detailList.stream().map(PreStockDetailDO::getModelNo).distinct().toArray(String[]::new);

        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setPropertyId(inventoryPropertyId);
        dto.setModelNos(Arrays.asList(modelNos));
        dto.setWarehouseCode(applyInfo.getWarehouseCode());
        ResultVo<List<InventorySummaryVO>> summaryResult = inventoryServiceFeignApi.listInventorySummaryByPropertyId(dto);
        if (!summaryResult.isSuccess()) {
            log.error("申请项的NewFlag标识设置失败: {}", summaryResult);
            throw new BusinessException("申请项的NewFlag标识设置失败: " + summaryResult.getMessage());
        }

        Map<String, Integer> stockQtyMap = new HashMap<>(summaryResult.getData().size());
        int stockQty;
        for (InventorySummaryVO summaryVO : summaryResult.getData()) {
            if (stockQtyMap.containsKey(summaryVO.getModelNo())) {
                stockQty = stockQtyMap.get(summaryVO.getModelNo()) + summaryVO.getQtyOnHand() + summaryVO.getOrdingQty();
            } else {
                stockQty = summaryVO.getQtyOnHand() + summaryVO.getOrdingQty();
            }
            stockQtyMap.put(summaryVO.getModelNo(), stockQty);
        }
        for (PreStockDetailDO detail : detailList) {
            stockQty = stockQtyMap.getOrDefault(detail.getModelNo(), 0);
            // 在库或在途>0就是旧申请
            // 1-新增申请型号 or 2-增加旧有备库型号数量
            detail.setNewFlag(stockQty > 0 ? "2" : "1");
        }
    }

    /**
     * 查询申请型号每月需求
     *
     * @param applyId 申请id
     * @param modelNo 申请型号
     * @return 申请型号每月需求
     */
    private List<PreStockApplyMonthlyDetail> listPreStockApplyModelMonthlyDetail(Long applyId, String modelNo) {
        String[] columns = new String[]{"id", "apply_id", "model_no", "dlv_date", "quantity", "status", "po_qty", "stock_qty"};
        QueryWrapper<PreStockDetailDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(columns);
        queryWrapper.eq("apply_id", applyId)
                .eq(StringUtils.isNotEmpty(modelNo), "model_no", modelNo)
                .eq("delFlag", 0);
        queryWrapper.groupBy(columns);
        queryWrapper.orderByAsc(columns[2]);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(queryWrapper);
        List<PreStockApplyMonthlyDetail> monthlyDetailList = new ArrayList<>(detailList.size());
        PreStockApplyMonthlyDetail monthlyDetail;
        for (PreStockDetailDO detail : detailList) {
            monthlyDetail = BeanCopyUtil.copy(detail, PreStockApplyMonthlyDetail.class);
            if (PreStockDetailStatusEnum.error.getCode().equals(detail.getStatus())
                    || PreStockDetailStatusEnum.no_process.getCode().equals(detail.getStatus())) {
                detail.setQuantity(detail.getQuantity() - Optional.ofNullable(detail.getPoQty()).orElse(0) - Optional.ofNullable(detail.getStockQty()).orElse(0));
            }
            monthlyDetailList.add(monthlyDetail);
        }
        return monthlyDetailList;
    }

    /**
     * @param applyInfo     申请信息
     * @param detail        申请项
     * @param canTransfer   是否可调库
     * @param stockInfoList 库存信息
     * @return 处理信息
     */
    @Override
    public List<PreStockResultDTO> autoCreateProcessItem(PreStockApplyDO applyInfo, PreStockDetailDO detail, boolean canTransfer, List<PreModelStockInfo> stockInfoList) {
        List<PreStockResultDTO> resultList = new ArrayList<>();
        // 初始化待处理数量
        int quantity = detail.getQuantity() - Optional.ofNullable(detail.getStockQty()).orElse(0) - Optional.ofNullable(detail.getPoQty()).orElse(0);
        log.info("在库申请{} 申请项{} 申请型号{}, 申请数量{},已调库{},已采购{},待处理数量{}", applyInfo.getApplyNo(),
                detail.getId(), detail.getModelNo(), detail.getQuantity(), detail.getStockQty(), detail.getPoQty(), quantity);
        detail.setQuantity(quantity);

        // 最新包装数量、偶数订货标识 bug-10503
        ResultVo<ProductRemark> productRemarkInfo = productServiceFeignApi.getProductRemarkByModelNo(detail.getModelNo());
        if (!productRemarkInfo.isSuccess()) {
            throw new BusinessException("处理失败,无法获取最小包装数: " + detail.getModelNo() + productRemarkInfo.getMessage());
        }
        if (productRemarkInfo.getData() == null) {
            throw new BusinessException("处理失败,无法获取最小包装数: " + detail.getModelNo() + "型号不存在");
        }

        // 1.生成出库处理项
        List<PreStockResultDTO> transferList = Collections.emptyList();
        stockInfoList.removeIf(item -> item.getAvaQty() == 0); // 清楚有效在库等于0的数据
        if (canTransfer && CollectionUtils.isNotEmpty(stockInfoList)) {
            transferList = this.createTransferProcessResult(applyInfo, detail, stockInfoList, productRemarkInfo.getData());
        }
        // 2) 生成预占BIN在途
        List<PreStockResultDTO> moveList = Collections.emptyList();
        if (canTransfer && detail.getQuantity().compareTo(0) > 0) {
            moveList = this.createMoveProcessResult(applyInfo, detail);
        }
        // 3.生成采购处理项
        List<PreStockResultDTO> purchaseList = Collections.emptyList();
        if (detail.getQuantity() > 0) {
            purchaseList = this.createPurchaseProcessResult(applyInfo, detail);
        }
        // 3.最小包装数订货处理
        this.converUnPackPoQty(applyInfo, detail, transferList, moveList, purchaseList, productRemarkInfo.getData());

        resultList.addAll(transferList);
        resultList.addAll(moveList);
        resultList.addAll(purchaseList);
        for (PreStockResultDTO result : resultList) {
            log.info("在库申请{} 申请项{} 处理结果: {}", applyInfo.getApplyNo(), detail.getId(), result.getRemark());
        }
        return resultList;
    }

    /**
     * 判断申请项是否可调库
     *
     * @param applyInfo       申请信息
     * @param detail          申请项
     * @param warehouseConfig 可出库仓库
     * @return true-可调库, false-不可调库
     */
    @Override
    public boolean checkTransfer(PreStockApplyDO applyInfo, PreStockDetailDO detail, List<String> warehouseConfig) {
        // 判断是否生成出在库处理
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            log.info("在库申请{} 没有可出在库仓库,跳过出在库处理", applyInfo.getApplyNo());
            return false;
        }
        // 战略在库（产品/行业） 不调库
        if (applyInfo.getStockType().equals(InventoryTypeEnum.ZL_CP.getCode()) || applyInfo.getStockType().equals(InventoryTypeEnum.ZL_HY.getCode())) {
            log.info("在库申请{} 为{}备库类型,跳过出在库处理", applyInfo.getApplyNo(), InventoryTypeEnum.getName(applyInfo.getStockType()));
            return false;
        }
        // 希望备库方式为[采购]
        if ("2".equals(detail.getExpType())) {
            log.info("在库申请{} 申请项{} 的希望备库方式为[采购],跳过出在库处理", applyInfo.getApplyNo(), detail.getId());
            return false;
        }
        return true;
    }

    @Override
    public void callbackProcessStatusToPortal(String applyNo, List<PreStockDetailDO> detailList) {
        if (StringUtils.isBlank(applyNo) || applyNo.startsWith("PT") || applyNo.startsWith("WT") || applyNo.startsWith("SHM")) {
            log.info("无需推送处理状态给门户: {}", applyNo);
            return;
        }

        AdapterPreStockDTO dto = new AdapterPreStockDTO();
        dto.setNo(applyNo);
        dto.setERPno(applyNo);
        dto.setDataType("1");

        Map<Integer, List<PreStockDetailDO>> itemMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getItemNo));

        // 0-待处理; 1-已处理; 2-已拆分; 3-取消;
        Map<Integer, String> resultMap = new HashMap<>(detailList.size());
        Map<String, Long> statusCount;

        for (Map.Entry<Integer, List<PreStockDetailDO>> itemEntry : itemMap.entrySet()) {
            if (itemEntry.getValue().size() == 1) {
                if (PreStockDetailStatusEnum.finished.getCode().equals(itemEntry.getValue().get(0).getStatus())) {
                    resultMap.put(itemEntry.getKey(), "1");
                } else if (PreStockDetailStatusEnum.cancel.getCode().equals(itemEntry.getValue().get(0).getStatus())) {
                    resultMap.put(itemEntry.getKey(), "3");
                } else {
                    resultMap.put(itemEntry.getKey(), "0");
                }
            } else {
                // 统计各拆分项处理状态数量
                statusCount = itemEntry.getValue().stream().collect(Collectors.groupingBy(PreStockDetailDO::getStatus, Collectors.counting()));
                if (Optional.ofNullable(statusCount.get(PreStockDetailStatusEnum.finished.getCode())).orElse(0L) == statusCount.size()) {
                    resultMap.put(itemEntry.getKey(), "1");
                } else if (Optional.ofNullable(statusCount.get(PreStockDetailStatusEnum.cancel.getCode())).orElse(0L) == statusCount.size()) {
                    resultMap.put(itemEntry.getKey(), "3");
                } else {
                    resultMap.put(itemEntry.getKey(), "2");
                }
            }
        }
        dto.setItemListStatus(resultMap);

        ResultVo<Boolean> sendResult = smsStockServiceFeignApi.sendPreStockDetailStatusMessage(dto);
        if (!sendResult.isSuccess()) {
            throw new BusinessException("推送处理状态给门户失败: " + sendResult.getMessage());
        }

        log.info("已推送处理状态给门户 {}", applyNo);
    }

    // Add by Dengdenghui 2022-12-08 for bug-8980
    @Override
    public void callbackPurchaseNoToPortal(String applyNo, List<PreStockDetailDO> detailList) {
        // 过滤申请类型 PL*|SHM*|ZL*
        if (applyNo.startsWith("PT") || applyNo.startsWith("SHM") || applyNo.startsWith("ZL")) {
            // log.info("无需回调门户处理单号: {}", applyNo);
            return;
        }
        // 1.剔除处理单号中的调库单号
        String[] nos;
        Map<Integer, Set<String>> purchaseNoMap = new HashMap<>(detailList.size());
        Map<Integer, Set<String>> transferNoMap = new HashMap<>(detailList.size());
        Set<String> purchaseNos;
        Set<String> transferNos;
        // bug 17924
        Map<Integer, String> prepareOrderMap = new HashMap<>(detailList.size()); // 预占单号
        for (PreStockDetailDO detail : detailList) {
            if (StringUtils.isBlank(detail.getOrderNos())) {
                continue;
            }

            prepareOrderMap.put(detail.getItemNo(), detail.getPrepareOrders());

            nos = detail.getOrderNos().split(";");
            purchaseNos = new HashSet<>();
            transferNos = new HashSet<>();
            for (String no : nos) {
                if (StringUtils.isBlank(no)) {
                    continue;
                }
                if (no.startsWith("77") || no.startsWith("88") || (no.startsWith("V") && !no.startsWith("VT"))) {
                    purchaseNos.add(no);
                } else {
                    transferNos.add(no);
                }
            }
            if (purchaseNos.size() > 0) {
                if (purchaseNoMap.containsKey(detail.getItemNo())) {
                    purchaseNoMap.get(detail.getItemNo()).addAll(purchaseNos);
                } else {
                    purchaseNoMap.put(detail.getItemNo(), purchaseNos);
                }
            }
            if (transferNos.size() > 0) {
                if (transferNoMap.containsKey(detail.getItemNo())) {
                    transferNoMap.get(detail.getItemNo()).addAll(transferNos);
                } else {
                    transferNoMap.put(detail.getItemNo(), transferNos);
                }
            }
        }
        if (MapUtils.isEmpty(purchaseNoMap) && MapUtils.isEmpty(transferNoMap)) {
            log.info("无处理单号需回调门户 {}", applyNo);
            return;
        }

        // 获取门户token
        String authorization = createTokenForOtherServer.getMHToken();
        log.info("回调门户-Authorization = {}", authorization);

        Set<Integer> itemSet = new HashSet<>(detailList.size());
        itemSet.addAll(purchaseNoMap.keySet());
        itemSet.addAll(transferNoMap.keySet());
        List<ReturnPurchaseNoParam> paramList = new ArrayList<>(detailList.size());
        ReturnPurchaseNoParam param;

        for (Integer itemNo : itemSet) {
            param = new ReturnPurchaseNoParam();
            param.setApplyNo(applyNo);
            param.setItemNo(itemNo);
            param.setPrepareOrderNos(prepareOrderMap.get(itemNo));
            if (CollectionUtils.isNotEmpty(purchaseNoMap.get(itemNo))) {
                param.setPurchaseNo(String.join(",", purchaseNoMap.get(itemNo)));
            }
            if (CollectionUtils.isNotEmpty(transferNoMap.get(itemNo))) {
                param.setTransferNos(String.join(",", transferNoMap.get(itemNo)));
            }
            if (applyNo.startsWith("ZK")) {
                param.setType(1);
            } else if (applyNo.startsWith("FK")) {
                param.setType(2);
            } else if (applyNo.startsWith("WT")) {
                param.setType(3);
            }
            paramList.add(param);
        }
        List<List<ReturnPurchaseNoParam>> pList = ListUtils.partition(paramList, 200);
        String url = menHuUrl + RETURN_PURCHASE_NO_URL;
        String data;
        ResultVo resultVo;

        for (List<ReturnPurchaseNoParam> subParamList : pList) {
            data = JSONUtil.toJsonStr(subParamList);
            log.info("回调门户处理单号 url = {}, data = {}", url, data);

            try {
                HttpResponse response = HttpUtil.createPost(url)
                        .header("Authorization", authorization)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .body(data)
                        .execute();
                log.info("回调门户采购单号 响应: {}", response.body());
                resultVo = JSONUtil.toBean(response.body(), ResultVo.class);
            } catch (HttpException e) {
                throw new BusinessException("回调门户处理单号异常: " + e.getMessage());
            }
            if (!resultVo.isSuccess()) {
                throw new BusinessException("回调门户处理单号失败: " + resultVo.getMessage());
            }
        }

        log.info("回调门户处理单号成功 {} ", applyNo);
    }

    @Override
    public ResultVo<String> autoCallbackPortal() {
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getApplyId, PreStockDetailDO::getItemNo,
                PreStockDetailDO::getStatus, PreStockDetailDO::getOrderNos, PreStockDetailDO::getPrepareOrders);
        detailQuery.eq(PreStockDetailDO::getReplyStatus, 1);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.success("自动回调门户完成-暂无同步数据");
        }

        Map<Long, List<PreStockDetailDO>> detailMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getApplyId));
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        PreStockApplyDO applyInfo;
        List<Long> detailIdList;
        String callbackResult;
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdate = new LambdaUpdateWrapper<>();
        StringBuilder errorMsg = new StringBuilder();

        for (Map.Entry<Long, List<PreStockDetailDO>> map : detailMap.entrySet()) {
            applyQuery.clear();
            applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo);
            applyQuery.eq(PreStockApplyDO::getId, map.getKey())
                    .notLike(PreStockApplyDO::getApplyNo, "ZL"); // 排除门户战略在库申请
            applyInfo = preStockApplyMapper.selectOne(applyQuery);
            if (applyInfo == null) {
                continue;
            }

            callbackResult = null;
            if (applyInfo.getApplyNo().startsWith("ZK") || applyInfo.getApplyNo().startsWith("FK")
                    || applyInfo.getApplyNo().startsWith("WT")) {
                try {
                    this.callbackProcessStatusToPortal(applyInfo.getApplyNo(), map.getValue());
                    this.callbackPurchaseNoToPortal(applyInfo.getApplyNo(), map.getValue());
                } catch (Exception e) {
                    callbackResult = "回调门户失败";
                    errorMsg.append(applyInfo.getApplyNo()).append(callbackResult).append("-").append(e.getMessage()).append(";");
                    log.error("回调门户失败-{}", e.getMessage());
                }
            }

            // 如果回调失败,未回调状态不变,记录回调门户失败
            detailIdList = map.getValue().stream().map(PreStockDetailDO::getId).collect(Collectors.toList());
            detailUpdate.clear();
            detailUpdate.set(StringUtils.isEmpty(callbackResult), PreStockDetailDO::getReplyStatus, 2)
                    .set(PreStockDetailDO::getReplyResult, callbackResult);
            detailUpdate.in(PreStockDetailDO::getId, detailIdList);
            preStockDetailMapper.update(new PreStockDetailDO(), detailUpdate);
        }

        return StringUtils.isBlank(errorMsg.toString()) ? ResultVo.success("自动回调门户完成") : ResultVo.failure(errorMsg.toString());
    }

    /* bug-11757 */
    @Override
    public ResultVo<String> callBackResultToPortalByBatchNo(String batchNo) {
        // 查询处于回调状态的申请项
        LambdaQueryWrapper<PreStockDetailView> detailQuery = Wrappers.lambdaQuery();
        detailQuery.select(PreStockDetailView::getApplyId, PreStockDetailView::getApplyNo, PreStockDetailView::getApplyStatus,
                PreStockDetailView::getItemNo, PreStockDetailView::getDetailStatus, PreStockDetailView::getBatchNo);
        detailQuery.eq(PreStockDetailView::getBatchNo, batchNo)
                .eq(PreStockDetailView::getReplyStatus, 1);
        List<PreStockDetailView> detailList = preStockDetailViewMapper.selectList(detailQuery);
        if (CollectionUtils.isEmpty(detailList)) {
            return ResultVo.failure("暂无数据回调门户");
        }

        // 判断回调门户申请类型
        String applyNo = detailList.get(0).getApplyNo();
        int applyType;
        if (applyNo.startsWith("ZL")) {
            applyType = 1; // 战略在库申请
        } else {
            return ResultVo.failure("未知类型的回调申请" + applyNo);
        }

        Long applyId = Long.parseLong(detailList.get(0).getApplyId());
        // 提取项号
        List<Integer> itemNoList = detailList.stream().map(PreStockDetailView::getItemNo).distinct().collect(Collectors.toList());

        // 根据项号查询处理单号
        LambdaQueryWrapper<PreStockResultDO> resultQuery = Wrappers.lambdaQuery();
        resultQuery.select(PreStockResultDO::getOrderNo, PreStockResultDO::getProcessType, PreStockResultDO::getItemNo,PreStockResultDO::getPrepareOrder);
        resultQuery.eq(PreStockResultDO::getApplyId, applyId)
                .in(PreStockResultDO::getItemNo, itemNoList)
                .eq(PreStockResultDO::getOptStatus, 2);
        List<PreStockResultDO> resultInfoList = preStockResultMapper.selectList(resultQuery);

        // 根据项号对处理单号分组
        Map<Integer, List<PreStockResultDO>> resultMap = resultInfoList.stream().collect(Collectors.groupingBy(PreStockResultDO::getItemNo));

        // 声明回调门户参数
        List<DealReturnOpsParamVO> paramVOList = new ArrayList<>(1);
        DealReturnOpsParamVO paramVO = new DealReturnOpsParamVO();
        DealReturnOpsParam opsParam = new DealReturnOpsParam();
        List<ReturnPurchaseNoParam> paramList = new ArrayList<>(itemNoList.size());
        opsParam.setReturnPurchaseNoParam(paramList);
        paramVO.setDealReturnOpsParam(opsParam);
        paramVO.setApplyType(applyType);
        paramVOList.add(paramVO);

        ReturnPurchaseNoParam param;
        List<String> purchaseNoList;
        List<String> transferNoList;
        List<String> prepareOrderList;
        for (Integer itemNo : itemNoList) {
            purchaseNoList = new ArrayList<>();
            transferNoList = new ArrayList<>();
            prepareOrderList = new ArrayList<>();
            for (PreStockResultDO resultInfo : resultMap.get(itemNo)) {
                if (PreStockProcessTypeEnum.purchase.getCode().equals(resultInfo.getProcessType())
                        || PreStockProcessTypeEnum.binPurchase.getCode().equals(resultInfo.getProcessType())
                        || PreStockProcessTypeEnum.prepareOrder.getCode().equals(resultInfo.getProcessType())) {
                    purchaseNoList.add(resultInfo.getOrderNo());
                }
                if (PreStockProcessTypeEnum.trans.getCode().equals(resultInfo.getProcessType())
                        || PreStockProcessTypeEnum.transDiff.getCode().equals(resultInfo.getProcessType())
                        || PreStockProcessTypeEnum.prepareBin.getCode().equals(resultInfo.getProcessType())) {
                    transferNoList.add(resultInfo.getOrderNo());
                }

                if (StringUtils.isNotBlank(resultInfo.getPrepareOrder())) {
                    prepareOrderList.add(resultInfo.getPrepareOrder());
                }
            }

            param = new ReturnPurchaseNoParam();
            param.setApplyNo(applyNo);
            param.setItemNo(itemNo);
            param.setType(1);
            param.setPurchaseNo(purchaseNoList.stream().collect(Collectors.joining(",")));
            param.setTransferNos(transferNoList.stream().collect(Collectors.joining(",")));
            param.setPrepareOrderNos(prepareOrderList.stream().collect(Collectors.joining(",")));
            paramList.add(param);
        }

        // 获取门户token
        String authorization = createTokenForOtherServer.getMHToken();
        log.info("回调门户-Authorization = {}", authorization);
        String url = menHuUrl + RETURN_DEAL_INFO_URL;

        ResultVo resultVo;
        String data = JSONUtil.toJsonStr(paramVOList.get(0));
        log.info("回调门户处理单号 url = {}, param = {}", url, data);
        // 调用回调门户接口
        try {
            HttpResponse response = HttpUtil.createPost(url)
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(data)
                    .execute();
            log.info("回调门户处理单号 响应: {}", response.body());
            resultVo = JSONUtil.toBean(response.body(), ResultVo.class);
        } catch (Exception e) {
            resultVo = ResultVo.failure("回调门户失败," + e.getMessage());
        }

        // 提取回调项号
        itemNoList = paramList.stream().map(ReturnPurchaseNoParam::getItemNo).distinct().collect(Collectors.toList());
        // 更新申请项回调结果
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = Wrappers.lambdaUpdate();
        detailUpdateWrapper.set(resultVo.isSuccess(), PreStockDetailDO::getReplyStatus, 2) // 已同步
                .set(PreStockDetailDO::getReplyResult, resultVo.getMessage());
        detailUpdateWrapper.eq(PreStockDetailDO::getApplyId, applyId)
                .eq(PreStockDetailDO::getReplyStatus, 1)
                .in(PreStockDetailDO::getItemNo, itemNoList);
        preStockDetailMapper.update(null, detailUpdateWrapper);

        return resultVo;
    }

    @Override
    public ResultVo<String> updatePreStockDetailStatus(List<Long> ids, String status, String newStatus) {
        try {
            if (CollectionUtils.isEmpty(ids)
                    || StringUtils.isBlank(status)
                    || StringUtils.isBlank(newStatus)) {
                return ResultVo.failure("更新失败！输入参数有空值。");
            }
            List<String> statusList = new ArrayList<>();
            QueryWrapper<PreStockDetailDO> detailQuery = new QueryWrapper<>();

            // 取 apply_id
            detailQuery = new QueryWrapper<>();
            detailQuery.select(" distinct apply_id").lambda()
                    .in(PreStockDetailDO::getId, ids);
            List<PreStockDetailDO> detailDOS = preStockDetailMapper.selectList(detailQuery);
            List<Long> applyIds = detailDOS.stream().map(PreStockDetailDO::getApplyId).collect(Collectors.toList());

            if (newStatus.equals("9")) {

                statusList.addAll(Arrays.asList("3", "2")); //退单,处理中
                LambdaQueryWrapper<PreStockDetailDO> detailQueryWrapper = new LambdaQueryWrapper<>();
                detailQueryWrapper.in(PreStockDetailDO::getId, ids)
                        .notIn(CollectionUtils.isNotEmpty(statusList), PreStockDetailDO::getStatus, statusList);
                int count = preStockDetailMapper.selectCount(detailQueryWrapper);
                if (count > 0) {
                    return ResultVo.failure("取消失败！退单或处理中才能取消。");
                }

                detailQueryWrapper.clear();
                detailQueryWrapper.in(PreStockDetailDO::getId, ids)
                        .in(CollectionUtils.isNotEmpty(statusList), PreStockDetailDO::getStatus, statusList);
                detailQueryWrapper.and(i -> i.gt(PreStockDetailDO::getPoQty, 0)
                        .or().gt(PreStockDetailDO::getStockQty, 0)
                        .or().gt(PreStockDetailDO::getPrepareQty, 0)
                );
                count = preStockDetailMapper.selectCount(detailQueryWrapper);
                if (count > 0) {
                    return ResultVo.failure("取消失败！存在已采购，调拨或预约处理。");
                }
            }
            if (StringUtils.isNotBlank(status)) {
                statusList.add(status);
            }

            Date updateTime = DateUtil.getNow();
            String updateUser = SMCApp.getLoginAuthDtoForSysUser().getUserNo();

            LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
            detailUpdateWrapper.in(PreStockDetailDO::getId, ids)
                    .in(CollectionUtils.isNotEmpty(statusList), PreStockDetailDO::getStatus, statusList)
                    .set(PreStockDetailDO::getStatus, newStatus)
                    .set(PreStockDetailDO::getReplyStatus, 1) // 待回调门户信息
                    .set(PreStockDetailDO::getUpdateTime, updateTime)
                    .set(PreStockDetailDO::getUpdateUser, updateUser);
            preStockDetailMapper.update(null, detailUpdateWrapper);

            // <!--add by WuWeiDong 20240424 bug 14050  根据明细状态,反算更新主表状态,如  明细全部取消，主表也取消-->
            for (Long applyId : applyIds) {
                this.updatePreStockApplyStatus(applyId);
            }
            return ResultVo.success("更新成功！");
        } catch (Exception e) {
            log.error("updatePreStockDetailStatus 更新失败 ==>:", e);
            return ResultVo.failure("更新失败," + e.getMessage());
        }
    }

    private void updatePreStockApplyStatus(Long applyId) {

        QueryWrapper<PreStockDetailDO> detailQuery = new QueryWrapper<>();
        detailQuery.select(" status ").lambda()
                .eq(PreStockDetailDO::getApplyId, applyId);
        List<PreStockDetailDO> detailDOS = preStockDetailMapper.selectList(detailQuery);
        Map<String, Long> statusMap = detailDOS.stream().collect(Collectors.groupingBy(PreStockDetailDO::getStatus, Collectors.counting()));
        long count = detailDOS.size();
        /**
         * 1)取消项数=总项数 ，取消
         * 2）(6)完成项数=总项数 ,完成项数+取消项数=总数，完成
         * 3）(5)已确项数=总项数 ,完成项数+取消项数=总数，已确
         */

        Integer newStatus = 0;
        if (statusMap.getOrDefault("9", 0l) >= count) {
            newStatus = 9;
        } else if (statusMap.getOrDefault("5", 0l) >= count) {
            newStatus = 5;
        } else if (statusMap.getOrDefault("5", 0l) + statusMap.getOrDefault("9", 0l) >= count) {
            newStatus = 5;
        } else if (statusMap.getOrDefault("6", 0l) >= count) {
            newStatus = 6;
        } else if (statusMap.getOrDefault("6", 0l) + statusMap.getOrDefault("9", 0l) >= count) {
            newStatus = 6;
        }

        if (newStatus.compareTo(0) == 1) {
            Date updateTime = DateUtil.getNow();
            String updateUser = SMCApp.getLoginAuthDtoForSysUser().getUserNo();
            LambdaUpdateWrapper<PreStockApplyDO> UpdateWrapper = new LambdaUpdateWrapper<>();
            UpdateWrapper.in(PreStockApplyDO::getId, applyId)
                    .set(PreStockApplyDO::getStatus, newStatus)
                    .set(PreStockApplyDO::getUpdateTime, updateTime)
                    .set(PreStockApplyDO::getUpdateUser, updateUser);

            preStockApplyMapper.update(null, UpdateWrapper);

        }

    }

    /**
     * <!--add by WuWeiDong 20231213  bug 12235  更新异仓调拨 -->
     * <p>
     * 以下子项状态不可以更新。
     * 3	退单
     * 5	已确认
     * 6	已处理
     * 7	异常拦截
     * 9	取消
     * 10	暂不处理
     *
     * @param id
     * @param transFlag true:异仓调拨;false:同仓调拨
     * @return
     */
    @Override
    public ResultVo<String> updatePreStockDetailTranFlag(Long id, Boolean transFlag) {

        String key = "ops:rediss:preStock:saving:" + id.toString();
        //  redissonUtil.lock(key, 10);
        if (!redissonUtil.tryLock(key, 0, 0)) {
            log.info("正在处理中" + key);
            return ResultVo.failure("正在处理中");
        }
        try {

            List<Integer> status = Arrays.asList(3, 5, 6, 7, 9, 10);
            LambdaQueryWrapper<PreStockDetailDO> applyQuery = new LambdaQueryWrapper<>();
            applyQuery.select(PreStockDetailDO::getStatus);
            applyQuery.eq(PreStockDetailDO::getApplyId, id)
                    .in(PreStockDetailDO::getStatus, status);
            Integer count = preStockDetailMapper.selectCount(applyQuery);

            if (count.compareTo(0) == 1) {
                return ResultVo.failure("当前状态不可更改！子项存在 退单,已确认,已处理,取消等。");
            } else {
                LambdaUpdateWrapper<PreStockApplyDO> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(PreStockApplyDO::getId, id)
                        .set(PreStockApplyDO::getTransFlag, transFlag)
                        .set(PreStockApplyDO::getUpdateTime, DateUtil.getNow())
                        .set(PreStockApplyDO::getUpdateUser, SMCApp.getLoginAuthDtoForSysUser().getUserNo());
                preStockApplyMapper.update(null, updateWrapper);

            }
            return ResultVo.success("更改成功！");

        } finally {
            redissonUtil.unlock(key);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PreStockResultDO getPreStockResultByOrderNo(String orderNo) {
        LambdaQueryWrapper<PreStockResultDO> queryResult = new LambdaQueryWrapper<>();
        queryResult.eq(PreStockResultDO::getOrderNo, orderNo)
                .select(PreStockResultDO::getFromId);
        List<PreStockResultDO> list = preStockResultMapper.selectList(queryResult);
        PreStockResultDO resultDO = new PreStockResultDO();
        if (CollectionUtils.isNotEmpty(list)) {
            resultDO = list.get(0);
        }
        return resultDO;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PreStockDetailDO getPreStockDetailById(Long id) {
        LambdaQueryWrapper<PreStockDetailDO> queryDetail = new LambdaQueryWrapper<>();
        queryDetail.eq(PreStockDetailDO::getId, id)
                .select(PreStockDetailDO::getModelNo, PreStockDetailDO::getQuantity,
                        PreStockDetailDO::getDlvDate, PreStockDetailDO::getEprice, PreStockDetailDO::getStockType);
        List<PreStockDetailDO> list = preStockDetailMapper.selectList(queryDetail);
        PreStockDetailDO detailDO = new PreStockDetailDO();
        if (CollectionUtils.isNotEmpty(list)) {
            detailDO = list.get(0);
        }
        return detailDO;
    }

    @Override
    public ResultVo<List<ShikomiCallbackDTO>> getShikomiStockData(List<String> applyNos) {
        List<ShikomiCallbackDTO> dtoList = new ArrayList<>();
        ShikomiCallbackDTO dto;

        Map<String, Set<Integer>> applyItemMap = new HashMap<>();
        String[] split;
        Set<Integer> itemSet;
        for (String applyNo : applyNos) {
            split = applyNo.split("-");
            if (applyItemMap.containsKey(split[0])) {
                itemSet = applyItemMap.get(split[0]);
            } else {
                itemSet = new HashSet<>();
                applyItemMap.put(split[0], itemSet);
            }
            itemSet.add(Integer.valueOf(split[1]));
        }

        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        PreStockApplyDO applyInfo;
        List<PreStockDetailDO> detailList;
        for (Map.Entry<String, Set<Integer>> entry : applyItemMap.entrySet()) {
            applyQuery.clear();
            applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyNo);
            applyQuery.eq(PreStockApplyDO::getApplyNo, entry.getKey());
            applyInfo = preStockApplyMapper.selectOne(applyQuery);
            if (applyInfo == null) {
                continue;
            }
            detailQuery.clear();
            detailQuery.select(PreStockDetailDO::getApplyId, PreStockDetailDO::getItemNo, PreStockDetailDO::getModelNo);
            detailQuery.eq(PreStockDetailDO::getApplyId, applyInfo.getId())
                    .in(PreStockDetailDO::getItemNo, entry.getValue());
            detailList = preStockDetailMapper.selectList(detailQuery);
            if (CollectionUtils.isEmpty(detailList)) {
                continue;
            }

            for (PreStockDetailDO detail : detailList) {
                dto = new ShikomiCallbackDTO();
                dto.setModelNo(detail.getModelNo());
                dto.setApplyNo(applyInfo.getApplyNo() + "-" + detail.getItemNo());
                dtoList.add(dto);
            }
        }
        return ResultVo.success(dtoList);
    }

    /**
     * 生成出库处理项:
     * 判断BinData表stockType是否BIN类型
     * 1.如果stockType=1 BIN类型,判断是否(通用在库数 - 申请数) >= 2*月用量 ---> true-分配通用在库数出库, false-使用过剩数量出库（可以全部使用）
     * 2.如果stackType=3 非BIN类型,判断是否在库数>0 ---> true-分配通用在库数出库, false-使用过剩数量出库（可以全部使用）
     * 3.如果stackType=其他类型 or 没有登录BinData数据,使用仓库的过剩数量出库（可以全部使用）
     * 4.还有剩余做采购处理
     *
     * @param applyInfo         申请信息
     * @param detail            申请项 (已按型号做分组处理,为同一型号)
     * @param stockInfoList     可出库库存列表
     * @param productRemarkInfo 型号最小包装数量、偶数订货标识
     * @return 出库处理项
     */
    public List<PreStockResultDTO> createTransferProcessResult(PreStockApplyDO applyInfo,
                                                               PreStockDetailDO detail,
                                                               List<PreModelStockInfo> stockInfoList,
                                                               ProductRemark productRemarkInfo) {

        List<PreStockResultDTO> resultList = new ArrayList<>(stockInfoList.size());
        PreStockResultDTO result;

        Iterator<PreModelStockInfo> stockInfoIterator = stockInfoList.iterator();
        PreModelStockInfo stockInfo;

        boolean isEven = "1".equals(productRemarkInfo.getIsEven());

        while (stockInfoIterator.hasNext()) {
            stockInfo = stockInfoIterator.next();
            if ("3".equals(detail.getExpType()) && applyInfo.getWarehouseCode().equals(stockInfo.getWarehouseCode())) {
                log.info("在库申请{} 申请项{} 希望备库方式为[异仓调拨]跳过{}备库仓库", applyInfo.getApplyNo(), detail.getId(), stockInfo.getWarehouseCode());
                continue;
            }

            log.info("在库申请{} 申请项{} {} {} stockType={},月用量{},通用在库{},待处理数量{},分配在库处理",
                    applyInfo.getApplyNo(), detail.getId(), detail.getModelNo(), stockInfo.getWarehouseCode(),
                    stockInfo.getStockType(), stockInfo.getMean(), stockInfo.getAvaQty(), detail.getQuantity());

            int transferQty = stockInfo.getAvaQty() > detail.getQuantity() ? detail.getQuantity() : stockInfo.getAvaQty(); // 调库数量
            if (isEven && this.checkParity(transferQty) == 1) { // 调拨需要符合偶数订货标识 bug-10503
                transferQty--;
            }
            StringBuilder remark = new StringBuilder(); // 备注

            if (transferQty > 0) {
                remark.append("分配").append(stockInfo.getWarehouseCode()).append(stockInfo.getInventoryTypeCode());
                remark.append(" ").append(Optional.ofNullable(stockInfo.getCustomerNo()).orElse(""));
                remark.append(" ").append(Optional.ofNullable(stockInfo.getPpl()).orElse(""));
                remark.append(" ").append(Optional.ofNullable(stockInfo.getProjectCode()).orElse(""));
                remark.append(" ").append(Optional.ofNullable(stockInfo.getGroupCustomerNo()).orElse(""));
                remark.append(transferQty).append(";");

                result = new PreStockResultDTO();
                result.setProcessType("4");
                result.setApplyId(applyInfo.getId());
                result.setModelNo(detail.getModelNo());
                result.setDlvDateJp(detail.getDlvDate());
                result.setOrderStock(stockInfo.getWarehouseCode());
                result.setApplyModelNo(detail.getApplyModelNo());
                result.setFromId(detail.getId());
                result.setItemNo(detail.getItemNo());
                result.setOptStatus("1"); // 设置处理项状态为待处理
                result.setOrderQty(transferQty);
                result.setRemark(remark.toString());
                result.setFromInventoryTypeCode(stockInfo.getInventoryTypeCode());
                result.setFromCustomerNo(stockInfo.getCustomerNo());
                result.setFromPplNo(stockInfo.getPpl());
                result.setFromProjectNo(stockInfo.getProjectCode());
                result.setFromGroupCustomerNo(stockInfo.getGroupCustomerNo());


                /**
                 * bug:12235, transflag=0,必须调至备库仓
                 * 1）本地同仓库。
                 * 2）异地同仓库。
                 *
                 */

                if (applyInfo.getTransFlag()) {
                    result.setToWarehouseCode(applyInfo.getWarehouseCode());
                } else {
                    result.setToWarehouseCode(stockInfo.getWarehouseCode());
                }
                result.setToCustomerNo(applyInfo.getCustomerNo());

                result.setToInventoryTypeCode(StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType());
                result.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
                result.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
                result.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());
                result.setTransType(applyInfo.getTransFlag() ? "1" : "0"); //是否异仓调拨
                result.setSpecMark(detail.getSpecMark());
                result.setProductTag(this.getRohs10(detail.getSpecExpType()) ? "0" : "9");
                resultList.add(result);
                // 计算申请项未处理数量
                detail.setQuantity(detail.getQuantity() - transferQty);
                // 计算申请项调库数量
                //detail.setStockQty(Optional.ofNullable(detail.getStockQty()).orElse(0) + transferQty);
                // 计算剩余的可用在库数量
                stockInfo.setAvaQty(stockInfo.getAvaQty() - transferQty);
                log.info("在库申请{} 申请项{} 分配{}通用库存{}", applyInfo.getApplyNo(), detail.getId(), stockInfo.getWarehouseCode(), transferQty);
            }
            // 移除在库数为0的库存项
            if (stockInfo.getAvaQty() == 0) {
                stockInfoIterator.remove();
            }
            if (detail.getQuantity() == 0) {
                break;
            }
        }

        return resultList;
    }

    /**
     * <!--add by WuWeiDong 20231206  bug 12563  先行在库预约bin生产在途 -->
     * 预计占BIN在途补库单
     * 只允许预占move状态为P的bin在途补库单
     *
     * @param applyInfo
     * @param detail
     * @return
     */

    public List<PreStockResultDTO> createMoveProcessResult(PreStockApplyDO applyInfo,
                                                           PreStockDetailDO detail) {
        if (detail.getQuantity() == 0) {
            return Collections.emptyList();
        }

        int offsize = 10;//批量查询上限
        List<String> transWarehouse = new ArrayList<>();

        ResultVo<List<WarehouseVO>> masterWarehouse = commonServiceFeignApi.getWarehouseByType(WarehouseTypeEnum.RDC.getHouseTypeCode());

        if (masterWarehouse.isSuccess() && CollectionUtils.isNotEmpty(masterWarehouse.getData())) {
            List<String> masterList = masterWarehouse.getData().stream().filter(i -> i.getTransferFlag().compareTo(1) == 0)
                    .map(WarehouseVO::getWarehouseCode).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(masterList)) {
                transWarehouse.addAll(masterList);
            }
        }
        ResultVo<List<WarehouseVO>> subWarehouse = commonServiceFeignApi.getWarehouseByType(WarehouseTypeEnum.FDC.getHouseTypeCode());
        if (subWarehouse.isSuccess() && CollectionUtils.isNotEmpty(subWarehouse.getData())) {
            List<String> subList = subWarehouse.getData().stream().filter(i -> i.getTransferFlag().compareTo(1) == 0)
                    .map(WarehouseVO::getWarehouseCode).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(subList)) {
                transWarehouse.addAll(subList);
            }
        }
        if (!transWarehouse.contains(applyInfo.getWarehouseCode())) {
            transWarehouse.add(applyInfo.getWarehouseCode()); //追加同仓
        }

        // 1)取P BIN 的在途补库单

        TransOrderQueryMoveParam moveParam = new TransOrderQueryMoveParam();
        moveParam.setModelno(detail.getModelNo());
        moveParam.setStatusCode(Arrays.asList("P"));  //生产中
        moveParam.setAssociateNoType(Arrays.asList(1)); // 99开头的Bin补库单
        moveParam.setSourceType(Arrays.asList("0")); // 采购
        moveParam.setWarehouseCode(transWarehouse);  //仓库

        CommonResult<List<TransOrderQueryMoveResult>> moveResult = opsWmDispatchForOrderFeignApi.findMoveForCreateTransOrder(moveParam);
        if (!moveResult.isSuccess()) {
            log.error("查询Bin在途失败：{} ,{} ->{}", applyInfo.getApplyNo(), detail.getModelNo(), moveResult.getMessage());
            throw new BusinessException(StringUtils.join("查询Bin在途失败：", applyInfo.getApplyNo(),
                    ",", detail.getModelNo(), " ->", moveResult.getMessage()));
        }

        List<TransOrderQueryMoveResult> moveList = moveResult.getData();

        if (CollectionUtils.isEmpty(moveList)) {
            return Collections.emptyList();
        }

        /** 2 ) 查询 Bin基础和Bin数据。
         *  有客户代码，客户代码+型号+订货型号
         */
        BigDecimal customerExpRate = BigDecimal.ZERO;
        List<ModelExpFreqVO> freqVOS = new ArrayList<>();
        if (StringUtils.isNotBlank(applyInfo.getCustomerNo())) {
            Integer allExpQty = 0;
            Integer customerExpQty = 0;

            ModelExpFreqRequest freqRequest = new ModelExpFreqRequest();
            freqRequest.setModelNo(detail.getModelNo());
            freqRequest.setStockcode("ALL");
            ResultVo<List<ModelExpFreqVO>> modelExpFreqResult = binServiceFeignApi.getModelExpFreq(freqRequest);
            if (!modelExpFreqResult.isSuccess()) {
                log.info("查询Bin基础失败：{} ,{} ->{}", applyInfo.getApplyNo(), detail.getModelNo(), modelExpFreqResult.getMessage());

            }

            if (CollectionUtils.isNotEmpty(modelExpFreqResult.getData())) {
                freqVOS = modelExpFreqResult.getData();
                allExpQty = freqVOS.stream().max(Comparator.comparing(ModelExpFreqVO::getQtyOf12)).get().getQtyOf12();
            }
            if (allExpQty.compareTo(0) == 1) {
                freqRequest = new ModelExpFreqRequest();
                freqRequest.setModelNo(detail.getModelNo());
                freqRequest.setStockcode(applyInfo.getCustomerNo());
                freqRequest.setModelTYpe("2"); // 1基础型号 2订货型号
                modelExpFreqResult = binServiceFeignApi.getModelExpFreq(freqRequest);
                if (!modelExpFreqResult.isSuccess()) {
                    log.info("查询Bin基础失败：{} ,{} ->{}", applyInfo.getApplyNo(), detail.getModelNo(), modelExpFreqResult.getMessage());

                }
                if (CollectionUtils.isNotEmpty(modelExpFreqResult.getData())) {
                    freqVOS = modelExpFreqResult.getData();
                    customerExpQty = freqVOS.get(0).getQtyOf12();
                }
                if (customerExpQty.compareTo(0) == 1) {
                    customerExpRate = BigDecimal.valueOf(customerExpQty).divide(BigDecimal.valueOf(allExpQty), 2, RoundingMode.HALF_UP);
                }
            }
        }


        // 3) 预占判断规则

        // 客户订货占全国比>=70%
        if (customerExpRate.compareTo(new BigDecimal("0.7")) >= 0) {
            // 判断本仓是否是BIN
            String warehouseMasterCode = this.getWarehouseMasterCode(applyInfo);
            ResultVo<Map<String, BindataVO>> binDataResult = binServiceFeignApi.getBindataByModelNoAndWarehouse(detail.getModelNo(), Arrays.asList(warehouseMasterCode));
            Boolean isBin = binDataResult.isSuccess() && Objects.nonNull(binDataResult.getData()) && binDataResult.getData().size() > 0;
            if (isBin) {
                // 只预占本仓的，删除其他他库数据
                moveList.removeIf(i -> !warehouseMasterCode.equalsIgnoreCase(i.getWarehouseCode()));
            } else {
                //  查询Bin 运营区分=[bin-自动化]
                ResultVo<List<BindataVO>> binTypeResult = binServiceFeignApi.getBindataByModelNoAnBinType(detail.getModelNo(), "BIN-自动化");
                Boolean isBinAll = binTypeResult.isSuccess() && Objects.nonNull(binTypeResult.getData()) && binTypeResult.getData().size() > 0;
                if (!isBinAll) {
                    //不做预占，直接采购
                    moveList.clear();
                }

            }
        } else {
            // Bin在途合计
            Integer sumQty = moveList.stream().mapToInt(TransOrderQueryMoveResult::getAvailableQty).sum();
            if (detail.getQuantity().compareTo(sumQty) > 0) {
                //不做预占，直接采购
                moveList.clear();
            }
        }


        if (CollectionUtils.isEmpty(moveList)) {
            return Collections.emptyList();
        }
        // 可调拨的仓库顺序
        List<String> warehouseConfig = this.getWarehouseConfig(applyInfo).getData();

        // 3）查询订单日期和预到货期
        List<OrderStateDTO> orderStateDTOS = new ArrayList<>();
        List<String> orderNos = new ArrayList<>();
        Page page = new Page();
        page.setPageSize(offsize);
        page.setPageNumber(1);
        Integer runTime = 1;
        for (TransOrderQueryMoveResult move : moveList) {

            String fullOrderNo = new OrderNoInfo().createFullOrderNo(move.getAssociateNo(), move.getAssociateNoItem(), move.getAssociateNoSplit())
                    .getFullOrderNo();


            orderNos.add(fullOrderNo);

            if (orderNos.size() % offsize == 0 || runTime >= moveList.size()) {
                OrderStateRequest orderStateRequest = new OrderStateRequest();
                orderStateRequest.setOrderNos(orderNos);
                orderStateRequest.setPage(page);
                ResultVo<PageInfo<OrderStateDTO>> resultVo = orderStateServiceFeignApi.listOrderState(orderStateRequest);
                if (resultVo.isSuccess() && Objects.nonNull(resultVo.getData())) {
                    List<OrderStateDTO> orderStateList = resultVo.getData().getList();
                    if (CollectionUtils.isNotEmpty(orderStateList)) {
                        orderStateDTOS.addAll(orderStateList);
                    }
                }
                orderNos.clear();
            }
            runTime++;
        }
        // 3）整合在途数据
        List<PreStockMoveDTO> preStockMoveDTOS = new ArrayList<>();
        // 根据订单转成map,方便用订单搜索对应数据
        Map<String, OrderStateDTO> orderStateMap = orderStateDTOS.stream().collect(Collectors.toMap(m -> m.getOrderNo(), t -> t));
        for (TransOrderQueryMoveResult move : moveList) {
            String fullOrderNo = new OrderNoInfo().createFullOrderNo(move.getAssociateNo(), move.getAssociateNoItem(), move.getAssociateNoSplit())
                    .getFullOrderNo();
            orderNos.add(fullOrderNo);

            OrderStateDTO orderStateDTO = orderStateMap.get(fullOrderNo);
            PreStockMoveDTO preStockMoveDTO = new PreStockMoveDTO();
            preStockMoveDTO.setAssociateNo(move.getAssociateNo());
            preStockMoveDTO.setAssociateNoItem(move.getAssociateNoItem());
            preStockMoveDTO.setAssociateNoSplit(move.getAssociateNoSplit());
            preStockMoveDTO.setInventoryID(move.getInventoryId());
            preStockMoveDTO.setWarehouseCode(move.getWarehouseCode());
            // 仓库补库优先顺序
            int idx = warehouseConfig.indexOf(preStockMoveDTO.getWarehouseCode());
            if (idx < 0) {
                idx = 9;
            }
            preStockMoveDTO.setWarehouseIdx(idx);
            preStockMoveDTO.setInventoryStatus(move.getInventoryStatus());
            preStockMoveDTO.setModelNo(move.getModelno());
            preStockMoveDTO.setQuantity(move.getQuantity());
            preStockMoveDTO.setPrepareQty(Optional.ofNullable(move.getPrepareQty()).orElse(0));
            preStockMoveDTO.setAvailableQty(preStockMoveDTO.getQuantity() - preStockMoveDTO.getPrepareQty());
            preStockMoveDTO.setInventoryPropertyId(move.getInventoryPropertyId());
            Date currentDate = DateUtil.getCurrentDate();  //默认当前时间为订单日期
            Date esArrivalDate = DateUtil.addDay(currentDate, 365 * 2);//默认预计到货期。
            Long diffMaxDay = 10000l; //默认交货期与预计到货期的差异。加大，有利于排序
            if (Objects.nonNull(orderStateDTO)) {
                preStockMoveDTO.setOrderDate(Optional.ofNullable(orderStateDTO.getOrderDate()).orElse(currentDate));
                // preStockMoveDTO.setEsArrivalDate(Optional.ofNullable(orderStateDTO.getEsArrivalDate()).orElse(esArrivalDate));
                preStockMoveDTO.setEsArrivalDate(Optional.ofNullable(orderStateDTO.getPoReplyDate()).orElse(esArrivalDate));
                preStockMoveDTO.setDlvDate(Optional.ofNullable(detail.getDlvDate()).orElse(esArrivalDate));
                Long diffDay = diffMaxDay;
                if (Objects.nonNull(preStockMoveDTO.getEsArrivalDate())) {
                    diffDay = DateUtil.getDiffDay(preStockMoveDTO.getEsArrivalDate(), preStockMoveDTO.getDlvDate());
                    diffDay = Math.abs(diffDay);
                }
                if (diffDay >= 7 && diffDay < diffMaxDay) {
                    diffDay = diffMaxDay;
                }
                preStockMoveDTO.setDiffDay(Math.abs(Integer.parseInt(diffDay.toString())));
            } else {
                preStockMoveDTO.setOrderDate(currentDate);
                preStockMoveDTO.setEsArrivalDate(esArrivalDate);
                preStockMoveDTO.setDlvDate(esArrivalDate);
                preStockMoveDTO.setDiffDay(Integer.parseInt(diffMaxDay.toString()));
            }
            preStockMoveDTOS.add(preStockMoveDTO);
        }
        preStockMoveDTOS.removeIf(i -> i.getAvailableQty().compareTo(0) <= 0);

        // 1)最优先预占：ABS(预计到货时间-先行客户需求时间)＜7的BIN补库单，无符合条件的再按下列判断：
        // 2）预计到货时间：早→晚
        // 3）订货日期：早→晚
        // 4) *仓库补库顺序
        // 5）订单号：小→大

        // ABS(预计到货时间-先行客户需求时间)＜7的BIN补库单
        List<PreStockMoveDTO> preStockMoveSorted = new ArrayList<>();
        List<PreStockMoveDTO> moveSorted = preStockMoveDTOS.stream().filter(i -> Objects.nonNull(i.getDiffDay()) && i.getDiffDay().compareTo(7) < 0).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(moveSorted)) {
            moveSorted = moveSorted.stream().sorted(Comparator.comparing(PreStockMoveDTO::getDiffDay)
                            .thenComparing(PreStockMoveDTO::getAssociateNo).thenComparing(PreStockMoveDTO::getAssociateNoItem))
                    .collect(Collectors.toList());
            preStockMoveSorted.addAll(moveSorted);
        }
        // ABS(预计到货时间-先行客户需求时间)>=7的BIN补库单
        moveSorted = preStockMoveDTOS.stream().filter(i -> Objects.nonNull(i.getDiffDay()) && i.getDiffDay().compareTo(7) >= 0).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(moveSorted)) {

            moveSorted = moveSorted.stream().sorted(Comparator.comparing(PreStockMoveDTO::getDiffDay)
                            .thenComparing(PreStockMoveDTO::getEsArrivalDate).thenComparing(PreStockMoveDTO::getOrderDate)
                            .thenComparing(PreStockMoveDTO::getAssociateNo).thenComparing(PreStockMoveDTO::getAssociateNoItem))
                    .collect(Collectors.toList());
            preStockMoveSorted.addAll(moveSorted);
        }
        Integer quantity = detail.getQuantity();
        List<PreStockResultDTO> resultList = new ArrayList<>();
        for (PreStockMoveDTO move : preStockMoveSorted) {
            if (quantity.compareTo(0) <= 0) {
                break;
            }
            PreStockResultDTO preStockResultDTO = new PreStockResultDTO();
            preStockResultDTO.setApplyId(detail.getApplyId());
            preStockResultDTO.setModelNo(detail.getModelNo());
            preStockResultDTO.setProcessType(PreStockProcessTypeEnum.prepareBin.getCode());
            // preStockResultDTO.setOrderNo(applyInfo.getApplyNo());
            String fullOrderNo = new OrderNoInfo().createFullOrderNo(move.getAssociateNo(), move.getAssociateNoItem(), move.getAssociateNoSplit())
                    .getFullOrderNo();
            //<!--add by WuWeiDong 20240229 bug 13634 补库，调库申请校验追加-->
            // String fullOrderNo = String.join("-", applyInfo.getApplyNo(), detail.getItemNo().toString());

            preStockResultDTO.setOrderNo(fullOrderNo);
            preStockResultDTO.setDlvDateJp(move.getDlvDate());
            preStockResultDTO.setDlvDateJp(move.getDlvDate());
            preStockResultDTO.setFromId(detail.getId());
            preStockResultDTO.setItemNo(detail.getItemNo());
            preStockResultDTO.setTransType(applyInfo.getTransFlag() ? "1" : "0");
            /** add by WuWeiDong 20240413  bug 13717
             * transflag=0, 异仓调拨到异仓先行在库
             * transflag=0, 异仓调拨到备库仓先行在库
             */
            if (applyInfo.getTransFlag()) {
                preStockResultDTO.setToWarehouseCode(applyInfo.getWarehouseCode());
            } else {
                preStockResultDTO.setToWarehouseCode(move.getWarehouseCode());
            }

            preStockResultDTO.setOrderStock(move.getWarehouseCode());
            preStockResultDTO.setFromInventoryId(move.getInventoryID());
            preStockResultDTO.setToCustomerNo(applyInfo.getCustomerNo());
            preStockResultDTO.setToInventoryTypeCode(StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType());
            preStockResultDTO.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
            preStockResultDTO.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
            preStockResultDTO.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());
            preStockResultDTO.setAssociateNo(move.getAssociateNo());
            preStockResultDTO.setAssociateNoItem(move.getAssociateNoItem());
            preStockResultDTO.setAssociateNoSplit(move.getAssociateNoSplit());
            preStockResultDTO.setOptStatus(move.getInventoryStatus());
            preStockResultDTO.setOptStatus("1"); // 设置处理项状态为待处理
            Integer orderQty = quantity;
            if (quantity.compareTo(move.getAvailableQty()) <= 0) {
                orderQty = quantity;
                quantity = 0;
            } else {
                orderQty = move.getAvailableQty();
                quantity -= move.getAvailableQty();
            }
            preStockResultDTO.setOrderQty(orderQty);
            // detail.setPrepareQty(Optional.ofNullable(detail.getPrepareQty()).orElse(0) + orderQty);
            String prepareOrder = new OrderNoInfo().createFullOrderNo(move.getAssociateNo(), move.getAssociateNoItem(), move.getAssociateNoSplit())
                    .getFullOrderNo();
            preStockResultDTO.setRemark("预占" + prepareOrder + "在途" + orderQty + ";");
            resultList.add(preStockResultDTO);
        }
        //保存剩余数，进行采购处理
        detail.setQuantity(quantity);
        return resultList;

    }

    /**
     * 生成采购处理项 :
     * 如果分配出库后还有剩余的就分配采购,优先分配Bin采购（BinData.stockType == 1）；
     * 如果分配Bin采购后还有余数且型号存在最小包装数，就按最小包装数分配采购，不满足最小包装的数量可忽略；
     * 如果分配Bin采购后还有余数且型号不存在最小包装数，就直接分配采购；
     *
     * @param applyInfo 申请信息
     * @param detail    申请项 (已按型号做分组处理,为同一型号)
     * @return 采购处理项
     */
    public List<PreStockResultDTO> createPurchaseProcessResult(PreStockApplyDO applyInfo,
                                                               PreStockDetailDO detail) {
        if (detail.getQuantity() == 0) {
            return Collections.emptyList();
        }
        // 获取型号供应商信息
        //if (StringUtils.isEmpty(detail.getSupplierCode())) {
        ResultVo<String> supplierResult = productServiceFeignApi.getSupplierNoByModelNo(detail.getModelNo());
        if (!supplierResult.isSuccess() || StringUtils.isBlank(supplierResult.getData())) {
            throw new BusinessException("生成采购订单失败, " + detail.getModelNo() + "没有供应商信息");
        }
        detail.setSupplierCode(supplierResult.getData().trim());
        //}
        log.info("在库申请{} 申请项{} {}, 待处理数量{},分配采购处理", applyInfo.getApplyNo(), detail.getId(),
                detail.getModelNo(), detail.getQuantity());

        // 按（处理类型+货期）合并采购处理
        List<PreStockResultDTO> resultList = new ArrayList<>(1);
        PreStockResultDTO result;
        int purchaseQty = detail.getQuantity(); // 采购数量

        // 按最小包装数采购
        result = new PreStockResultDTO();
        result.setProcessType("1");
        result.setApplyId(applyInfo.getId());
        result.setModelNo(detail.getModelNo());
        result.setOrderQty(purchaseQty);
        result.setDlvDateJp(detail.getDlvDate());
        result.setApplyModelNo(detail.getApplyModelNo());
        result.setOrderStock(detail.getSupplierCode()); // 采购供应商
        result.setFromId(detail.getId());
        result.setItemNo(detail.getItemNo());
        result.setTransType(Optional.ofNullable(detail.getTransType()).orElse("0"));
        result.setOptStatus("1"); // 设置处理项状态为待处理
        result.setRemark("采购" + purchaseQty + ";");
        result.setToWarehouseCode(applyInfo.getWarehouseCode());
        result.setToInventoryTypeCode(StringUtils.isBlank(detail.getStockType()) ? applyInfo.getStockType() : detail.getStockType());
        //    <!--add by WuWeiDong 20231128 bug 12715   -->
        result.setToPplNo(StringUtils.isBlank(detail.getPplNo()) ? applyInfo.getPplNo() : detail.getPplNo());
        result.setToProjectNo(StringUtils.isBlank(detail.getProjectNo()) ? applyInfo.getProjectNo() : detail.getProjectNo());
        result.setToGroupCustomerNo(StringUtils.isBlank(detail.getGroupCustomerNo()) ? applyInfo.getGroupCustomerNo() : detail.getGroupCustomerNo());

        result.setSpecMark(detail.getSpecMark());
        result.setProductTag(this.getRohs10(detail.getSpecExpType()) ? "0" : "9");


        /**
         * 是否满足准备订单
         * 获取准备订单的交货期天数
         */
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo("9002", "31");
        if (!dataTypeCodesInfo.isSuccess() || StringUtils.isBlank(dataTypeCodesInfo.getData().getExtNote1())) {
            throw new BusinessException("未能获取准备订单交货期天数:" + dataTypeCodesInfo.getMessage());
        }

        // 增加限制，顾客在库PPL,顾客在库项目，战略在库（PJ）这三个类型暂时不生成准备订单
        if (!(InventoryTypeEnum.GK_PPL.getCode().equalsIgnoreCase(detail.getStockType()) ||
                InventoryTypeEnum.GK_PJ.getCode().equalsIgnoreCase(detail.getStockType()) ||
                InventoryTypeEnum.ZL_PJ.getCode().equalsIgnoreCase(detail.getStockType()))) {
            if(result.getDlvDateJp() != null && DateUtil.getDiffDay(new Date(),result.getDlvDateJp()) > Integer.parseInt(dataTypeCodesInfo.getData().getExtNote1())) {
                ResultVo<Map<String, Boolean>> mapResultVo = prepareOrderFeignApi.prepareOrderVerify(Arrays.asList(detail.getModelNo()));
                if(mapResultVo.isSuccess()) {
                    Map<String, Boolean> data = mapResultVo.getData();
                    if(data != null && data.get(detail.getModelNo())) {
                        result.setProcessType("6");
                        result.setRemark("准备订单采购" + purchaseQty + ";");
                    }
                }
            }
        }


        resultList.add(result);
        // 计算申请项采购数量
        //detail.setPoQty(Optional.ofNullable(detail.getPoQty()).orElse(0) + purchaseQty);
        // 计算申请项未处理数量
        detail.setQuantity(detail.getQuantity() - purchaseQty);
        log.info("在库申请{} 申请项{} 按最小包装数采购{}个", applyInfo.getApplyNo(), detail.getId(), purchaseQty);

        return resultList;
    }

    /**
     * 按最小包装数采购有剩余数量,修改调库数量使得满足最小包装数
     * 减少预占或调拨数，增加采购数。
     *
     * @param detail       申请项
     * @param transferList 调库处理
     * @param convertQty   调库转采购数量
     */
    private int convertTransferQtyToPurchase(PreStockDetailDO detail, List<PreStockResultDTO> transferList, int convertQty) {
        // 因为出库优先顺序所以倒序遍历处理
        PreStockResultDTO result;
        int purchaseQty = convertQty; // 调库转采购数量
        for (int i = transferList.size() - 1; i > -1; i--) {
            if (purchaseQty == 0) {
                break;
            }
            result = transferList.get(i);
            if (result.getOrderQty() > purchaseQty) {
                result.setOrderQty(result.getOrderQty() - purchaseQty);
                detail.setQuantity(detail.getQuantity() + purchaseQty);

                purchaseQty = 0;
            } else {
                purchaseQty -= result.getOrderQty();
                detail.setQuantity(detail.getQuantity() + result.getOrderQty());
                result.setOrderQty(0);
            }
            if (StringUtils.isBlank(result.getAssociateNo())) {
                result.setRemark("分配" + result.getOrderStock() + "通用库存" + result.getOrderQty());
            } else {
                String prepareOrder = new OrderNoInfo().createFullOrderNo(result.getAssociateNo(), result.getAssociateNoItem(), result.getAssociateNoSplit())
                        .getFullOrderNo();
                result.setRemark("预占" + prepareOrder + "在途" + result.getOrderQty() + ";");
            }
        }
        // 删除调库数=0的调库处理项
        transferList.removeIf(item -> item.getOrderQty() == 0);
        return purchaseQty;
    }

    @Override
    /// @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> createTransferOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> transferList) {
        if (CollectionUtils.isEmpty(transferList)) {
            return ResultVo.success("没有调库处理项需要执行");
        }

        log.info("先行在库申请{} 开始执行调拨处理.", applyInfo.getApplyNo());
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? Optional.ofNullable(applyInfo.getCustomerNo()).orElse("") : applyInfo.getUserNo();

        String transNo = applyInfo.getApplyNo();
        int itemNo = 0;
        long fromInventoryPropertyId = 1L;
        String fromInventoryTypeCode = "TY";

        // 查询申请的调库处理单号 Add by Dengdenghui 2022-11-23 for bug-8754
        String transferOrderNo = preStockResultMapper.getTransferOrderNo(applyInfo.getApplyNo());
        if (StringUtils.isNotBlank(transferOrderNo)) {
            String[] orderNoItem = transferOrderNo.split("-");
            transNo = orderNoItem[0];
            itemNo = Integer.parseInt(orderNoItem[1]);
        } else {
            if (applyInfo.getWarehouseCode().startsWith("W") && !transNo.startsWith("VT")) {
                ResultVo<String> billNoResult = commonServiceFeignApi.generatorBillNo(WTDB);
                if (!billNoResult.isSuccess() || StringUtils.isBlank(transNo)) {
                    throw new BusinessException("生成服务备库" +
                            "调库单号失败: " + billNoResult.getMessage());
                }
                transNo = billNoResult.getData();
            }
        }
        int transFromType = 3; // 先行在库
        if (transNo.startsWith("VT")) {
            transFromType = 4; // 委托在库
        }

        List<TransOrderVO> transDtoList = new ArrayList<>(transferList.size());
        TransOrderVO transDto;

        for (PreStockResultDTO result : transferList) {

            itemNo++;
            transDto = new TransOrderVO();
            transDto.setTransType(1);
            transDto.setTransNo(transNo);
            transDto.setItemNo(itemNo);
            transDto.setModelNo(result.getModelNo());
            transDto.setQuantity(result.getOrderQty());
            transDto.setStatus(0);
            transDto.setFromNo(applyInfo.getApplyNo());
            transDto.setFromId(result.getFromId());
            transDto.setFromType(transFromType);
            transDto.setWmsDlvDate(result.getDlvDateJp());
            //服务备库写入PO号和物料号
            if(PreStockApplyTypeEnum.WT.getCode().equals(applyInfo.getApplyType())){
                transDto.setCorderNo(applyInfo.getCorderNo());
                transDto.setCproductNo(detailInfo.getCproductNo());
            }
            // 调出
            fromInventoryPropertyId = this.getOpsInventoryPropertyId(result.getFromInventoryTypeCode(), result.getFromCustomerNo(),
                    result.getFromGroupCustomerNo(), result.getFromPplNo(), result.getFromProjectNo());
            transDto.setFromInventoryPropertyId(fromInventoryPropertyId);

            transDto.setFromInventoryTypeCode(result.getFromInventoryTypeCode());
            transDto.setFromWarehouseCode(result.getOrderStock());
            transDto.setFromCustomerNo(result.getFromCustomerNo());
            transDto.setFromPpl(result.getFromPplNo());
            transDto.setFromProjectCode(result.getFromProjectNo());
            transDto.setFromGroupCustomerNo(result.getFromGroupCustomerNo());

            // 调入
            // 备库库存属性ID Bug-9942
            if (detailInfo.getInventoryPropertyId() == null) {
                long toInventoryPropertyId = this.getOpsInventoryPropertyId(result.getToInventoryTypeCode(),
                        result.getToCustomerNo(), result.getToGroupCustomerNo(), result.getToPplNo(), result.getToProjectNo());
                detailInfo.setInventoryPropertyId(toInventoryPropertyId);
            }
            transDto.setToInventoryPropertyId(detailInfo.getInventoryPropertyId());
            transDto.setToWarehouseCode(result.getToWarehouseCode());
            transDto.setToCustomerNo(result.getToCustomerNo());
            transDto.setToInventoryTypeCode(result.getToInventoryTypeCode());
            transDto.setToPpl(result.getToPplNo());
            transDto.setToProjectCode(result.getToProjectNo());
            transDto.setToGroupCustomerNo(result.getToGroupCustomerNo());
            transDtoList.add(transDto);
            result.setOrderNo(transNo + "-" + itemNo);
        }
        log.info("先行在库-调拨处理 {} data  {}", applyInfo.getApplyNo(), JSON.toJSONString(transDtoList));
        return preStockService.saveTransferOrder(transDtoList, detailInfo, transferList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> saveTransferOrder(List<TransOrderVO> transDtoList, PreStockDetailDO detailInfo, List<PreStockResultDTO> transferList) {
        ResultVo<String> transResult = transStockFeignApi.transStock(transDtoList);
        log.info("先行在库-调拨处理 {} 响应 = {}", transDtoList.get(0).getFromNo(), JSON.toJSONString(transResult));
        if (!transResult.isSuccess()) {
            throw new RuntimeException("调库执行异常-" + transResult.getMessage());
        }

        String failMsg = "";
        Map<String, String> resultMap = JSONUtil.toBean(transResult.getData(), Map.class);
        Map<String, PreStockResultDTO> resultDTOMap = transferList.stream().collect(Collectors.toMap(PreStockResultDTO::getOrderNo, item -> item));
        PreStockResultDO result;
        Map<Long, String> detailOrderMap = new HashMap<>();

        for (Map.Entry<String, String> resultEntry : resultMap.entrySet()) {
            result = BeanCopyUtil.copy(resultDTOMap.get(resultEntry.getKey()), PreStockResultDO.class);

            if ("成功".equals(resultEntry.getValue())) {
                result.setOptStatus("2"); // 已处理
                // 记录已申请调拨数量
                detailInfo.setStockQty(Optional.ofNullable(detailInfo.getStockQty()).orElse(0) + result.getOrderQty());
                // 记录申请项的处理单号
                if (!"9".equals(result.getProcessType())) {
                    detailOrderMap.put(result.getFromId(), detailOrderMap.getOrDefault(result.getFromId(), "") + result.getOrderNo() + ";");
                }
            } else {
                result.setOptStatus("4"); // 取消处理
                result.setRemark(resultEntry.getValue());
                //detailInfo.setStockQty(detailInfo.getStockQty() - result.getOrderQty());
                detailInfo.setQuantity(detailInfo.getQuantity() + result.getOrderQty());
                failMsg = resultEntry.getValue().toString() + "; ";
            }

            result.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            result.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            preStockResultMapper.insert(result);
        }

        if (detailOrderMap.size() > 0) {
            // 更新申请项处理状态
            if (PreStockDetailStatusEnum.finished.getCode().equals(detailInfo.getStatus()) && detailInfo.getQuantity().compareTo(0) == 1) {
                detailInfo.setStatus(PreStockDetailStatusEnum.processing.getCode());
            }
            this.updateDetailProcessState(Collections.singletonList(detailInfo), detailOrderMap);
        }
        if (StringUtils.isNotBlank(failMsg)) {
            return ResultVo.failure("调库失败-" + failMsg);
        }
        return ResultVo.success("调库执行完成");
    }

    /**
     * <!--add by WuWeiDong 20231206  bug 12563  先行在库预约bin生产在途 -->
     *
     * @param applyInfo  申请信息
     * @param detailInfo 申请项信息
     * @param moveList   在途处理项
     * @return
     */
    @Override
    // @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> createMoverOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> moveList) {
        if (CollectionUtils.isEmpty(moveList)) {
            return ResultVo.success("没有预占在途处理项需要执行");
        }
        log.info("先行在库申请{} 开始预占在途处理.", applyInfo.getApplyNo());
        String transNo = applyInfo.getApplyNo();
        int itemNo = 0;
        // 查询申请的调库处理单号 Add by Dengdenghui 2022-11-23 for bug-8754
        String transferOrderNo = preStockResultMapper.getTransferOrderNo(applyInfo.getApplyNo());
        if (StringUtils.isNotBlank(transferOrderNo)) {
            String[] orderNoItem = transferOrderNo.split("-");
            transNo = orderNoItem[0];
            itemNo = Integer.parseInt(orderNoItem[1]);
        }
        int transFromType = 3; // 先行在库
        if (transNo.startsWith("VT")) {
            transFromType = 4; // 委托在库
        }
        List<TransOrderDtoForMove> moveOrderVOList = new ArrayList<>(moveList.size());
        for (PreStockResultDTO result : moveList) {
            itemNo++;
            TransOrderDtoForMove moveOrderVO = new TransOrderDtoForMove();
            moveOrderVO.setTransNo(transNo);
            moveOrderVO.setItemNo(itemNo);
            moveOrderVO.setFromNo(applyInfo.getApplyNo());
            moveOrderVO.setFromId(result.getFromId());
            moveOrderVO.setFromType(transFromType);
            moveOrderVO.setModelNo(result.getModelNo());
            moveOrderVO.setHopeQty(result.getOrderQty());
            moveOrderVO.setTransFlag(result.getTransType().equals("1") ? true : false);
            moveOrderVO.setFromInventoryId(result.getFromInventoryId());
            moveOrderVO.setFromInventoryStatus("P");
            moveOrderVO.setFromAssociateNo(result.getAssociateNo());
            moveOrderVO.setFromAssociateNoItem(result.getAssociateNoItem());
            moveOrderVO.setFromAssociateNoSplit(result.getAssociateNoSplit());
            moveOrderVO.setToInventoryTypeCode(result.getToInventoryTypeCode());
            moveOrderVO.setToWarehouseCode(result.getToWarehouseCode());
            moveOrderVO.setToCustomerNo(result.getToCustomerNo());
            moveOrderVO.setToPpl(result.getToPplNo());
            moveOrderVO.setToProjectNo(result.getToProjectNo());
            moveOrderVO.setToGroupCustomerNo(result.getToGroupCustomerNo());
            //服务备库传递PO号和物料号
            if (PreStockApplyTypeEnum.WT.getCode().equals(applyInfo.getApplyType())) {
                moveOrderVO.setCorderNo(applyInfo.getCorderNo());
                moveOrderVO.setCproductNo(detailInfo.getCproductNo());
            }
            moveOrderVOList.add(moveOrderVO);
            result.setOrderNo(transNo + "-" + itemNo);
        }
        //  调用预约在途接口
        log.info("先行在库-预约在途处理 {} data  {}", applyInfo.getApplyNo(), JSON.toJSONString(moveList));
        return preStockService.saveMoveOrder(moveOrderVOList, detailInfo, moveList);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> saveMoveOrder(List<TransOrderDtoForMove> moveOrderList, PreStockDetailDO detailInfo, List<PreStockResultDTO> moveList) {


        ResultVo<String> moveResult = transStockFeignApi.createTransOrderForMove(moveOrderList);
        log.info("先行在库-预约在途处理 {} 响应 = {}", moveOrderList.get(0).getFromNo(), JSON.toJSONString(moveResult));
        if (!moveResult.isSuccess()) {
            throw new RuntimeException("预约在途执行异常-" + moveResult.getMessage());
        }
        String failMsg = "";
        Map<String, String> resultMap = JSONUtil.toBean(moveResult.getData(), Map.class);


        Map<String, PreStockResultDTO> resultDTOMap = moveList.stream().collect(Collectors.toMap(PreStockResultDTO::getOrderNo, item -> item));
        PreStockResultDO result;
        Map<Long, String> detailOrderMap = new HashMap<>();
        Map<Long, String> prepareOrderMap = new HashMap<>();
        for (Map.Entry<String, String> resultEntry : resultMap.entrySet()) {

            PreStockResultDTO dto = resultDTOMap.get(resultEntry.getKey());
            result = BeanCopyUtil.copy(dto, PreStockResultDO.class);
            //  String fullOrderNo=String.join("-",result.ap)
            String prepareOrder = new OrderNoInfo().createFullOrderNo(dto.getAssociateNo(),
                    dto.getAssociateNoItem(), dto.getAssociateNoSplit()).getFullOrderNo();

            //    result.setOrderNo(fullOrderNo);  //保存预约的单号
            result.setPrepareOrder(prepareOrder);
            result.setDlvDateJp(dto.getDlvDateJp());
            result.setItemNo(dto.getItemNo());
            result.setApplyModelNo(detailInfo.getApplyModelNo());

            if (StringUtils.isNotBlank(resultEntry.getValue()) && resultEntry.getValue().contains("成功")) {
                result.setOptStatus("2"); // 已处理
                // 记录已申请调拨数量
                detailInfo.setPrepareQty(Optional.ofNullable(detailInfo.getPrepareQty()).orElse(0) + result.getOrderQty());
                // 记录申请项的处理单号
                if (!"9".equals(result.getProcessType())) {
                    detailOrderMap.put(result.getFromId(), detailOrderMap.getOrDefault(result.getFromId(), "") + result.getOrderNo() + ";");
                    //<!--add by WuWeiDong 20240229 bug 13634 更新预占订单-->
                    prepareOrderMap.put(result.getFromId(), prepareOrderMap.getOrDefault(result.getFromId(), "") + result.getPrepareOrder() + ";");
                }
            } else {
                result.setOptStatus("4"); // 取消处理
                result.setRemark(resultEntry.getValue());
                detailInfo.setQuantity(detailInfo.getQuantity() + result.getOrderQty());
                failMsg = resultEntry.getValue().toString() + "; ";
            }

            result.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            result.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            preStockResultMapper.insert(result);
        }

        if (detailOrderMap.size() > 0) {
            // 更新申请项处理状态
            if (PreStockDetailStatusEnum.finished.getCode().equals(detailInfo.getStatus()) && detailInfo.getQuantity().compareTo(0) == 1) {
                detailInfo.setStatus(PreStockDetailStatusEnum.processing.getCode());
            }
            this.updateDetailProcessState(Collections.singletonList(detailInfo), detailOrderMap);
            //<!--add by WuWeiDong 20240229 bug 13634 更新预占订单-->
            String prepareOrder = prepareOrderMap.get(detailInfo.getId());
            if (StringUtils.isBlank(detailInfo.getPrepareOrders())) {
                detailInfo.setPrepareOrders(prepareOrder);
            } else if (detailInfo.getOrderNos().length() + prepareOrder.length() < 400) { // 单号长度超过400截断
                if (!detailInfo.getPrepareOrders().contains(prepareOrder)) {
                    detailInfo.setPrepareOrders(detailInfo.getPrepareOrders() + prepareOrder);
                }
            }
            LambdaUpdateWrapper<PreStockDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(PreStockDetailDO::getId, detailInfo.getId())
                    .set(PreStockDetailDO::getPrepareOrders, detailInfo.getPrepareOrders());
            preStockDetailMapper.update(null, updateWrapper);

        }
        if (StringUtils.isNotBlank(failMsg)) {
            return ResultVo.failure("预约在途失败-" + failMsg);
        }
        return ResultVo.success("预约在途执行完成");
    }

    @Override
    // @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> createPurchaseOrder(PreStockApplyDO applyInfo, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList) {
        if (CollectionUtils.isEmpty(purchaseList)) {
            return ResultVo.success("没有采购处理项需要执行");
        }
        log.info("先行在库申请{} 开始执行采购处理.", applyInfo.getApplyNo());
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? Optional.ofNullable(applyInfo.getCustomerNo()).orElse("") : applyInfo.getUserNo();
        // 请购applyDeptNo=收货所在地营业所代码（有备库客户时取所属营业所代码，否则取申请部门代码）
        String applyDeptNo = applyInfo.getDeptNo();
        if (StringUtils.isNotBlank(customerNo)) {
            CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(customerNo);
            if (customerInfo == null) {
                log.error("生成采购订单失败,无法获取申请客户信息: {}", customerInfo);
                throw new BusinessException("生成采购订单失败,无法获取申请客户信息");
            }
            applyDeptNo = customerInfo.getHRUnitId();
        }

        int itemNo = 0;
        String pOrderNo = null;

        // 如果该申请上一次生成采购处理未超过一天,复用上一次的采购单号
        OpsRequestpurchase lastRequestPurchaseOrder = preStockResultMapper.getLastRequestPurchaseOrder(applyInfo.getApplyNo());
        if (lastRequestPurchaseOrder != null
                && DateUtil.getDate(lastRequestPurchaseOrder.getRequesttime()).compareTo(DateUtil.getToday()) == 0) {
            pOrderNo = lastRequestPurchaseOrder.getOrderno();
            itemNo = lastRequestPurchaseOrder.getItemno();
        }

        if (StringUtils.isBlank(pOrderNo)) {
            // 生成专用备库订单号
            ResultVo<String> generatorResult = commonServiceFeignApi.generatorBillNo(applyInfo.getWarehouseCode().startsWith("W") ? WTCG : PURCHASE_ORDER_BILLTYPE);
            log.info("先行在库-生成采购单号 orderNo = {}", generatorResult);
            if (!generatorResult.isSuccess() || StringUtils.isBlank(generatorResult.getData())) {
                return ResultVo.failure("生成采购订单失败,生成订单号异常: " + generatorResult.getMessage());
            }
            pOrderNo = generatorResult.getData();
        }

        List<RequestPurchaseInfoDto> list = new ArrayList<>(purchaseList.size());
        RequestPurchaseInfoDto purchase;
        Map<String, WarehouseDO> warehouseMap = new HashMap<>();
        Date now = new Date();

        List<OrderStateVO> orderStateVOList = new ArrayList<>(purchaseList.size());
        OrderStateVO orderStateVO;
        String orderType = applyInfo.getWarehouseCode().startsWith("W") ? OrderTypeEnum.wtzkOrder.getCode() : OrderTypeEnum.xxbkOrder.getCode();
        boolean isFromOPS = applyInfo.getApplyNo().startsWith("PT"); // 判断申请是OPS申请的还是门户申请的 bug-8871
        boolean isMasterWarehouse = opsWarehouseService.isMasterWarehouse(applyInfo.getWarehouseCode());

        // 生成采购单
        for (PreStockResultDTO result : purchaseList) {
            // 备库库存属性ID  Bug-9942
            if (detailInfo.getInventoryPropertyId() == null) {
                detailInfo.setInventoryPropertyId(this.getOpsInventoryPropertyId(result.getToInventoryTypeCode(), customerNo, result.getToGroupCustomerNo(), result.getToPplNo(), result.getToProjectNo()));
            }


            itemNo++;
            purchase = new RequestPurchaseInfoDto();
            purchase.setOrderno(pOrderNo);
            purchase.setItemno(itemNo);
            purchase.setModelno(result.getModelNo());
            purchase.setQuantity(result.getOrderQty());
            // bug-11679 请购deptNo=申请deptNo, 请购applyDeptNo=收货所在地营业所代码（有备库客户时取所属营业所代码，否则取申请部门代码）
            // bug 12910 先行在库写入请购表代码优化
            ResultVo<String> deptNoByHRSalesDeptNo = opsCommonService.getDeptNoByHRSalesDeptNo(applyDeptNo);
            if(deptNoByHRSalesDeptNo.isSuccess() && deptNoByHRSalesDeptNo.getData() != null) {
                purchase.setDeptno(deptNoByHRSalesDeptNo.getData());
            }
            purchase.setApplyDeptNo(applyDeptNo);
            purchase.setHopedeliverydate(result.getDlvDateJp()); // 期望货期
            purchase.setHopeexportdate(result.getDlvDateJp());
            if (isFromOPS) {
                // OPS申请,需要设置制造出荷日HopeExportDate bug-8871
                purchase.setHopeexportdate(result.getDlvDateJp());
            } else {
                // 门户申请,不需要设置HopeExportDate bug-8871
                purchase.setHopeexportdate(null);
            }
            purchase.setRequesttime(now); // 请购时间
            purchase.setInventorytypecode(result.getToInventoryTypeCode());
            purchase.setInventorypropertyid(detailInfo.getInventoryPropertyId());
            purchase.setCustomerno(Optional.ofNullable(applyInfo.getCustomerNo()).orElse(""));
            purchase.setUserno(applyInfo.getUserNo());
            purchase.setPpl(result.getToPplNo());
            purchase.setProjectcode(result.getToProjectNo());
            purchase.setGroupcustomerno(result.getToGroupCustomerNo());
            purchase.setTranstype(result.getTransType());
            purchase.setOrdtype(orderType);
            purchase.setPurchasetype(PurchaseTypeEnum.PRE.getCode());
            purchase.setWmtag(WMPurchaseTagEnum.WHOLE.getType());
            // purchase.setRequestwarehouseid(result.getToWarehouseCode()); // 请购仓库
            purchase.setRequestwarehouseid(applyInfo.getWarehouseCode()); // bug 14242 请购仓应以申请单的备库仓为准
            if (isMasterWarehouse) {
                purchase.setPurchasewarehouseid(purchase.getRequestwarehouseid()); // 采购仓库
            } else {
                if (!warehouseMap.containsKey(purchase.getRequestwarehouseid())) {
                    warehouseMap.put(purchase.getRequestwarehouseid(), opsWarehouseService.getWarehouseInfoByCode(purchase.getRequestwarehouseid()));
                }
                purchase.setPurchasewarehouseid(warehouseMap.get(purchase.getRequestwarehouseid()).getParentCode()); // 采购仓库
            }
            purchase.setCorderno(applyInfo.getApplyNo());
            purchase.setSupplierid(result.getOrderStock());
            purchase.setSpecmark(result.getSpecMark()); // 组装标识
            purchase.setProducttag(result.getProductTag()); // ROHS10


            //add by A78028 20231017 from bug 12284
            RequestPurchaseInfo infoJson = new RequestPurchaseInfo();
            infoJson.setVip(Optional.ofNullable(applyInfo.getVip()).orElse(false));
            purchase.setInfojson(infoJson);
            purchase.setEndUser(Optional.ofNullable(applyInfo.getCustomerNo()).orElse("")); // bug19576 采购发单给老生管对于最终代码的传值，先行在库补库取客户代码
            list.add(purchase);
            result.setOrderNo(pOrderNo + "-" + itemNo);

            orderStateVO = new OrderStateVO();
            orderStateVO.setOrderNo(result.getOrderNo());
            orderStateVO.setRorderNo(pOrderNo);
            orderStateVO.setItemNo(purchase.getItemno());
            orderStateVO.setModelNo(purchase.getModelno());
            orderStateVO.setQuantity(purchase.getQuantity());
            orderStateVO.setOrderType(Integer.valueOf(purchase.getOrdtype()));
            orderStateVO.setStateCode(OrderStateEnum.Purchareing.code());
            orderStateVO.setStateDes("生成先行在库请购单");
            orderStateVO.setStateType(1);
            orderStateVO.setStateDate(now);
            orderStateVO.setCustDlvDate(purchase.getHopedeliverydate());
            orderStateVO.setCustomerNo(purchase.getCustomerno());
            orderStateVO.setUserNo(purchase.getUserno());
            orderStateVO.setDeptNo(purchase.getApplyDeptNo());
            orderStateVO.setPoTransType(purchase.getTranstype());
            orderStateVO.setTransType(purchase.getTranstype());
            orderStateVO.setWarehouseCode(purchase.getRequestwarehouseid());
            orderStateVO.setPoDlvDate(purchase.getHopedeliverydate());
            orderStateVO.setOrderDate(now);
            orderStateVO.setReceiveDate(now);
            orderStateVO.setOrderPsnNo("ops");
            orderStateVO.setOptUserNo("ops");
            orderStateVO.setOptUserName("ops");
            orderStateVO.setProvider("OPS");
            orderStateVOList.add(orderStateVO);
        }
        log.info("先行在库-采购处理 {} data = {}", applyInfo.getApplyNo(), JSON.toJSON(list));
        return preStockService.savePurchaseOrder(list, orderStateVOList, detailInfo, purchaseList);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public ResultVo<String> savePurchaseOrder(List<RequestPurchaseInfoDto> PurchaseOrderList, List<OrderStateVO> orderStateList, PreStockDetailDO detailInfo, List<PreStockResultDTO> purchaseList) {
        CommonResult<String> addResult = requestPurchaseFeignApi.addRequest(PurchaseOrderList);
        log.info("先行在库-采购处理 {} 响应 = {}", PurchaseOrderList.get(0).getCorderno(), JSON.toJSONString(addResult));
        if (addResult.getCode() == 500) {
            // 重置请购处理数量
            //this.resetDetailQty(purchaseList, Collections.singletonList(detailInfo));
            return ResultVo.failure("生成采购订单失败: " + addResult.getMessage());
        }

        // 保存采购处理结果
        Map<Long, String> detailOrderMap = new HashMap<>();
        PreStockResultDO result;
        for (PreStockResultDTO resultDTO : purchaseList) {
            result = BeanCopyUtil.copy(resultDTO, PreStockResultDO.class);
            result.setOptStatus("2"); // 已处理
            result.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            result.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
            preStockResultMapper.insert(result);
            // 记录已申请采购数量
            detailInfo.setPoQty(Optional.ofNullable(detailInfo.getPoQty()).orElse(0) + result.getOrderQty());
            // 记录申请项的处理单号
            if (!"9".equals(result.getProcessType())) {
                detailOrderMap.put(result.getFromId(), detailOrderMap.getOrDefault(result.getFromId(), "") + result.getOrderNo() + ";");
            }
        }

        // 更新申请项处理状态
        this.updateDetailProcessState(Collections.singletonList(detailInfo), detailOrderMap);

        // 发送请购单状态消息
        this.sendRequestPurchaseOrderState(orderStateList);

        return ResultVo.success("采购执行完成");
    }

    /**
     * 发送生成采购订单状态
     */
    private void sendRequestPurchaseOrderState(List<OrderStateVO> orderStateVOList) {
        ResultVo<String> addResult;
        for (OrderStateVO orderStateVO : orderStateVOList) {
            try {
                addResult = orderStateServiceFeignApi.addOrderState(orderStateVO);
                if (!addResult.isSuccess()) {
                    log.error("保存先行在库请购单状态失败: {}", addResult.getMessage());
                }
            } catch (Exception e) {
                log.error("保存先行在库请购单状态发生异常: {}", e.getMessage(), e);
            }
        }
    }

    // Edit by DengDengHui, 2022-10-20 for bug-8370
    private Long getOpsInventoryPropertyId(String inventoryTypeCode, String customerNo, String groupCustomerNo, String pplNo, String projectNo) {
        if (InventoryTypeEnum.TY.getCode().equalsIgnoreCase(inventoryTypeCode)) {
            return 1L;
        } else {
            OpsInventoryProperty propertyVO = new OpsInventoryProperty();
            propertyVO.setInventoryTypeCode(inventoryTypeCode);
            if (inventoryTypeCode.startsWith("GK") && StringUtils.isNotBlank(customerNo)) {
                propertyVO.setCustomerNo(customerNo);
            }
            if ((inventoryTypeCode.endsWith("HY") || inventoryTypeCode.endsWith("JT")) && StringUtils.isNotBlank(groupCustomerNo)) {
                propertyVO.setGroupCustomerNo(groupCustomerNo);
            }
            if (inventoryTypeCode.endsWith("PPL") && StringUtils.isNotBlank(pplNo)) {
                propertyVO.setPpl(pplNo);
            }
            if (inventoryTypeCode.endsWith("PJ") && StringUtils.isNotBlank(projectNo)) {
                propertyVO.setProjectCode(projectNo);
            }
            CommonResult checkResult = opsPropertyFeignApi.findProperty(propertyVO);
            if (!checkResult.isSuccess() || checkResult.getData() == null) {
                throw new BusinessException("验证备库的inventoryPropertyId失败: " + checkResult.getMessage());
            }
            return Long.parseLong(checkResult.getData().toString());
        }
    } // end

    /**
     * 先行在库申请型号可拆分处理
     *
     * @param source 原申请项
     * @return 可拆分型号申请项/单型号申请项
     */
    private List<PreStockDetailDO> modelSplitHandle(PreStockDetailDO source,BomSelectResult bom) {
        // 如果申请型号存在可拆分型号就转换为可拆分型号，否则返回原单型号
        List<PreStockDetailDO> target;
        // 查询申请型号的可拆分型号
        //bugid:17799 c14717 20250617
        List<ProductBomDetail> bomDetails = bom.getBomVersionsList().get(0).getBomDetails();
        int splitItem = 0;
        if (CollectionUtils.isNotEmpty(bomDetails)) {
            target = new ArrayList<>(bomDetails.size());
            PreStockDetailDO temp;
            for (ProductBomDetail bomDetail : bomDetails) {
                splitItem++;
                temp = BeanCopyUtil.copy(source, PreStockDetailDO.class);
                if (splitItem > 1) {
                    temp.setId(null);
                }
                temp.setSplitItem(splitItem);
                temp.setModelNo(bomDetail.getModelno());
                temp.setQuantity((source.getQuantity() + Optional.ofNullable(source.getStockQty()).orElse(0)) * bomDetail.getQuantity());
                temp.setStockQty(Optional.ofNullable(source.getStockQty()).orElse(0) * bomDetail.getQuantity());
                target.add(temp);
            }

            // 保存拆分项结果
            for (PreStockDetailDO detail : target) {
                // 设置拆分型号的供应商
                if (detail.getSplitItem() > 0) {
                    this.setDetailSupplier(detail, null);
                }
                if (detail.getId() == null) {
                    preStockDetailMapper.insert(detail);
                } else {
                    preStockDetailMapper.updateById(detail);
                }
            }
        } else {
            target = new ArrayList<>(1);
            source.setSplitItem(Optional.ofNullable(source.getSplitItem()).orElse(splitItem));
            target.add(source);
        }
        return target;
    }

    /**
     * <!--add by WuWeiDong 20230929  bug 12191  先行在库补库规则的改善  -->
     * 一）分库补库规则
     * 1）委托在库/服务备库
     * 1.1)委托在库/服务备库（PPL）:顾客在库（PPL）【分库】->顾客在库（项目）【分库】-> 顾客在库（PPL）【中心】->顾客在库（项目）【中心】 ->战略在库（PJ）【分库】->战略在库（PJ）【中心】
     * -> 顾客在库通用【分库】-> 顾客在库通用【中心】 ->通用【分库】【本地】->通用【中心】【本地】->通用【中心】【异地】
     * 1.2)委托在库/服务备库（Other）:  顾客在库通用【分库】-> 顾客在库通用【中心】 ->通用【分库】【本地】  ->通用【中心】【本地】->通用【中心】【异地】
     * <!--add by WuWeiDong 20240207  bug 13489  先行在库补库规则的改善
     * （1）服务备库顾客在库PPL（非直采）：分库（ppl）-->物流中心（PPL）-->分库（顾客在库通用）-->物流中心（顾客在库通用）-->分库（通用）-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     * （2）服务备库顾客在库项目（非直采）：分库（项目）-->物流中心（项目）-->物流中心PJ战略在库-->分库（顾客在库通用）-->物流中心（顾客在库通用）-->分库（通用）-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     * （3）服务备库顾客在库通用、服务备库通用在库（非直采）：分库顾客在库通用-->物流中心顾客在库通用-->分库通用在库-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     * -->
     * 2） 顾客在库
     * 2.1）顾客在库（PPL）【分库】 ：顾客在库（PPL）【中心】->顾客在库（项目）【中心】 ->顾客在库通用【中心】 ->通用【分库】【本地】 -->通用【中心】【本地】-->通用【中心】【异地】
     * 2.2）顾客在库（通用）【分库】 ：顾客在库（PPL）【中心】->顾客在库（项目）【中心】 ->顾客在库通用【中心】 ->通用【分库】【本地】 -->通用【中心】【本地】-->通用【中心】【异地】
     * 2.3）顾客在库（项目）【分库】：顾客在库（项目）【中心】 -> 顾客在库（PPL）【中心】-> 顾客在库通用【中心】->通用【分库】 【本地】-->战略在库（PJ）【分库】 -->战略在库（PJ）【中心】-->通用【中心】【本地】-->通用【中心】【异地】
     * <!--add by WuWeiDong 20240327  bug 13800  先行在库补库规则的改善
     * 2.3.1）顾客在库（项目）【分库】：顾客在库（项目）【中心】 -> 顾客在库（PPL）【中心】-> 顾客在库通用【中心】->通用【分库】 【本地】-->战略在库（PJ）【分库】
     * -->战略在库（PJ）【中心】【本地】-->战略在库（PJ）【中心】【异地】-->通用【中心】【本地】-->通用【中心】【异地】
     * <!--add by WuWeiDong 20240130  bug 13489  物流中心补库规则
     * 二）物流中心补库规则
     * 1）顾客在库
     * 1.1）顾客在库（PPL）【大库】 ：通用【中心】【本地】-->通用【中心】【异地】
     * 1.2）顾客在库（通用）【大库】 ：通用【中心】【本地】-->通用【中心】【异地】
     * 1.3）顾客在库（项目）【大库】：战略在库（PJ）【中心】-->通用【中心】【本地】-->通用【中心】【异地】
     * -->
     * <!-- 取消add by WuWeiDong 20231122  bug 12235  增加同异仓调拨判断
     * 1)trans_flag默认不勾选，值为0，不可异仓调拨，
     * 2)trans_flag勾选，值为1，可异仓调拨
     * -->
     * <!--add by WuWeiDong 20240313 bug 13717  增加同异仓调拨判断
     * 1)trans_flag默认不勾选，值为0，异仓调拨到异仓先行在库，
     * 2)trans_flag勾选，值为1，可异仓调拨回备库仓先行在库
     * -->
     * <!--add by WuWeiDong 20240408 bug 13800  增加异地物流中心
     * 一）顾客在库
     * 1）顾客在库（PPL）【分库】 ：顾客在库（PPL）【中心】【本地】->顾客在库（PPL）【中心】【异地】->顾客在库（项目）【中心】【本地】->顾客在库（项目）【中心】【异地】->   顾客在库通用【中心】【本地】->顾客在库通用【中心】【异地】 ->通用【分库】【本地】  -->通用【中心】【本地】-->通用【中心】【异地】
     * 3）顾客在库（通用）【分库】 ：顾客在库（PPL）【中心】【本地】->顾客在库（PPL）【中心】【异地】->顾客在库（项目）【中心】 【本地】->顾客在库（项目）【中心】【异地】->   顾客在库通用【中心】 【本地】->顾客在库通用【中心】【异地】->通用【分库】【本地】  -->通用【中心】【本地】-->通用【中心】【异地】
     * 2）顾客在库（项目）【分库】：顾客在库（项目）【中心】【本地】->顾客在库（项目）【中心】【异地】 -> 顾客在库（PPL）【中心】【本地】->顾客在库（PPL）【中心】【异地】-> 顾客在库通用【中心】【本地】->顾客在库通用【中心】【异地】 ->通用【分库】 【本地】-->战略在库（PJ）【分库】 -->战略在库（PJ）【中心】【本地】-->战略在库（PJ）【中心】【异地】-->通用【中心】【本地】-->通用【中心】【异地】
     * <p>
     * 4）顾客在库（PPL）【大库】 ：通用【中心】【本地】-->通用【中心】【异地】
     * 6）顾客在库（通用）【大库】 ：通用【中心】【本地】-->通用【中心】【异地】
     * 5）顾客在库（项目）【大库】：战略在库（PJ）【中心】【本地】-->战略在库（PJ）【中心】【异地】-->通用【中心】【本地】-->通用【中心】【异地】
     * <p>
     * 二）服务备库
     * （1）服务备库顾客在库PPL（非直采）：分库（ppl）-->物流中心（PPL）【本地】-->物流中心（PPL）【异地】-->分库（顾客在库通用）-->物流中心（顾客在库通用）【本地】-->物流中心（顾客在库通用）【异地】-->分库（通用）-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     * （2）服务备库顾客在库项目（非直采）：分库（项目）-->物流中心（项目）【本地】-->物流中心（项目）【异地】-->物流中心PJ战略在库【本地】-->物流中心PJ战略在库【异地】-->分库（顾客在库通用）-->物流中心（顾客在库通用）【本地】-->物流中心(顾客在库通用)【异地】-->分库（通用）-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     * （3）服务备库顾客在库通用、服务备库通用在库（非直采）：分库顾客在库通用-->物流中心顾客在库通用【本地】-->物流中心顾客在库通用【异地】-->分库通用在库-->物流中心【通用】【本地】-->物流中心【通用】【异地】
     *
     * @param applyInfo         库存已属性
     * @param detailDO          型号
     * @param warehouseConfig   出库规则，可以出库的仓库代码
     * @param inventoryTypeCode 库存分类
     * @return
     */
    // @Override
    public List<PreModelStockInfo> getPreModelInventoryInfoByStockClass(PreStockApplyDO applyInfo, PreStockDetailDO detailDO, List<String> warehouseConfig, String inventoryTypeCode) {


        ResultVo<WarehouseVO> resultVo = commonServiceFeignApi.getWarehouseInfoByCode(applyInfo.getWarehouseCode());
        if (!resultVo.isSuccess()) {
            throw new BusinessException("获取仓库信息失败: " + applyInfo.getWarehouseCode() + resultVo.getMessage());
        }
        if (Objects.isNull(resultVo.getData())) {
            throw new BusinessException("无此仓库信息: " + applyInfo.getWarehouseCode());
        }
        WarehouseVO warehouseVO = resultVo.getData();
        if (ObjectUtils.isEmpty(applyInfo.getTransFlag())) {
            applyInfo.setTransFlag(false);
        }
        Boolean isSubWarehouse = false;
        List<PreModelStockInfo> rtnData = new ArrayList<>();
        List<String> calcList = new ArrayList<>(); //计算补库顺序
        String stockClass = StringUtils.left(inventoryTypeCode, 2);//TY(通用),GK（顾客）,ZL（战略）
        String warehouseCode = applyInfo.getWarehouseCode();
        String masterCode = StringUtils.isNotBlank(warehouseVO.getParentCode()) ? warehouseVO.getParentCode() : warehouseCode;
        Boolean isMaster = warehouseVO.getWarehouseType().equalsIgnoreCase(WarehouseTypeEnum.RDC.getHouseTypeCode());//是否是物流中心
        switch (warehouseVO.getWarehouseType().toUpperCase()) {
            case "MASTER": //物流中心
                switch (stockClass) {
                    case "GK":  // 顾客在库
                        if (inventoryTypeCode.contains("PJ")) {
                            calcList = Arrays.asList("ZL-PJ:MASTER", "ZL-PJ:MASTER:OTHER", "TY:MASTER");

                        } else {
                            calcList = Arrays.asList("TY:MASTER");
                        }
                        rtnData = this.setPreModelStockInfo(applyInfo, detailDO, warehouseCode, masterCode, calcList);

                        break;
                    default:
                        List<String> newWarehouseConfig = new ArrayList<String>(warehouseConfig);
                        rtnData = this.setPreModelStockInfo(detailDO.getModelNo(), newWarehouseConfig);

                }
                break;
            case "SUB":  //分库
                switch (stockClass) {
                    case "GK":  // 顾客在库
                        if (inventoryTypeCode.contains("PJ")) {
                            //顾客在库（项目）【分库】：顾客在库（项目）【中心】【本地】->顾客在库（项目）【中心】【异地】 -> 顾客在库（PPL）【中心】【本地】->顾客在库（PPL）【中心】【异地】-> 顾客在库通用【中心】【本地】->顾客在库通用【中心】【异地】 ->通用【分库】 【本地】
                            // -->战略在库（PJ）【分库】 -->战略在库（PJ）【中心】【本地】-->战略在库（PJ）【中心】【异地】-->通用【中心】【本地】-->通用【中心】【异地】
                            calcList = Arrays.asList("GK-PJ:MASTER", "GK-PJ:MASTER:OTHER", "GK-PPL:MASTER", "GK-PPL:MASTER:OTHER", "GK-TY:MASTER", "GK-TY:MASTER:OTHER", "TY:SUB", "ZL-PJ:SUB", "ZL-PJ:MASTER", "ZL-PJ:MASTER:OTHER", "TY:MASTER");
                        } else {
                            //顾客在库（通用/PPL）【分库】 ：顾客在库（PPL）【中心】【本地】->顾客在库（PPL）【中心】【异地】->顾客在库（项目）【中心】 【本地】->顾客在库（项目）【中心】【异地】->
                            // 顾客在库通用【中心】 【本地】->顾客在库通用【中心】【异地】->通用【分库】【本地】  -->通用【中心】【本地】-->通用【中心】【异地】
                            calcList = Arrays.asList("GK-PPL:MASTER", "GK-PPL:MASTER:OTHER", "GK-PJ:MASTER", "GK-PJ:MASTER:OTHER", "GK-TY:MASTER", "GK-TY:MASTER:OTHER", "TY:SUB", "TY:MASTER");
                        }
                        List<String> doCalcList = calcList;
                        rtnData = this.setPreModelStockInfo(applyInfo, detailDO, warehouseCode, masterCode, doCalcList);
                        break;
                    default:
                        rtnData = this.setPreModelStockInfo(detailDO.getModelNo(), warehouseConfig);
                }

                break;
            case "WT":  //委托在库
                if (inventoryTypeCode.contains("PPL")) {
                    //分库（ppl）-->物流中心（PPL）【本地】-->物流中心（PPL）【异地】-->分库（顾客在库通用）-->物流中心（顾客在库通用）【本地】-->物流中心（顾客在库通用）【异地】-->分库（通用）
                    // -->物流中心【通用】【本地】-->物流中心【通用】【异地
                    calcList = Arrays.asList("GK-PPL:SUB", "GK-PPL:MASTER", "GK-PPL:MASTER:OTHER", "GK-TY:SUB", "GK-TY:MASTER", "GK-TY:MASTER:OTHER", "TY:SUB", "TY:MASTER");
                } else if (inventoryTypeCode.contains("PJ")) {
                    //分库（项目）-->物流中心（项目）【本地】-->物流中心（项目）【异地】-->物流中心PJ战略在库【本地】-->物流中心PJ战略在库【异地】-->分库（顾客在库通用）-->物流中心（顾客在库通用）【本地】-->物流中心(顾客在库通用)【异地】-->分库（通用）
                    // -->物流中心【通用】【本地】-->物流中心【通用】【异地】
                    calcList = Arrays.asList("GK-PJ:SUB", "GK-PJ:MASTER", "GK-PJ:MASTER:OTHER", "ZL-PJ:MASTER", "ZL-PJ:MASTER:OTHER", "GK-TY:SUB", "GK-TY:MASTER", "GK-TY:MASTER:OTHER", "TY:SUB", "TY:MASTER");
                } else {
                    //分库顾客在库通用-->物流中心顾客在库通用【本地】-->物流中心顾客在库通用【异地】-->分库通用在库-->物流中心【通用】【本地】-->物流中心【通用】【异地】
                    calcList = Arrays.asList("GK-TY:SUB", "GK-TY:MASTER", "GK-TY:MASTER:OTHER", "TY:SUB", "TY:MASTER");
                }
                List<String> doCalcList = calcList;
                // 去掉委托在库的仓库，委托在库的仓库不做补库
                warehouseConfig.removeIf(i -> i.equalsIgnoreCase(warehouseCode));

                //取对应的分库仓库
                List<String> subCodes = new ArrayList<>();
                List<String> masterCodes = new ArrayList<>();
                Integer idx = 1;//出多个分库，写第一个分库后面。

                for (String code : warehouseConfig) {
                    warehouseVO = commonServiceFeignApi.getWarehouseInfoByCode(code).getData();
                    //是否是分库
                    if (warehouseVO.getWarehouseType().equalsIgnoreCase(WarehouseTypeEnum.FDC.getHouseTypeCode())) {
                        subCodes.add(code);
                        masterCodes.add(Optional.ofNullable(warehouseVO.getParentCode()).orElse(code));
                        //   break;
                    }
                }
                if (CollectionUtils.isEmpty(subCodes)) {
                    subCodes.add(masterCode);
                    masterCodes.add(masterCode);
                }
                if (subCodes.size() == 1) {
                    //单个分库处理
                    rtnData = this.setPreModelStockInfo(applyInfo, detailDO, subCodes.get(0), masterCodes.get(0), doCalcList);
                } else {
                    //多个分库处理
                    for (int i = 0; i < subCodes.size(); i++) {
                        String subCode = subCodes.get(i);
                        List<PreModelStockInfo> stockInfos = this.setPreModelStockInfo(applyInfo, detailDO, subCode, masterCodes.get(i), doCalcList);
                        if (CollectionUtils.isEmpty(rtnData) && CollectionUtils.isNotEmpty(stockInfos)) {
                            rtnData.addAll(stockInfos);
                            idx = stockInfos.stream().filter(f -> f.getWarehouseCode().equalsIgnoreCase(subCode)).collect(Collectors.toList()).size();
                        } else {
                            for (PreModelStockInfo info : stockInfos) {
                                List<PreModelStockInfo> checkStockInfo = new ArrayList<>();
                                if (StringUtils.isNotBlank(info.getPpl())) {
                                    //ppl
                                    checkStockInfo = rtnData.stream().filter(f -> info.getWarehouseCode().equalsIgnoreCase(f.getWarehouseCode())
                                                    && info.getInventoryTypeCode().equalsIgnoreCase(f.getInventoryTypeCode())
                                                    && info.getPpl().equalsIgnoreCase(f.getPpl()))
                                            .collect(Collectors.toList());
                                } else if (StringUtils.isNotBlank(info.getProjectCode())) {
                                    checkStockInfo = rtnData.stream().filter(f -> info.getWarehouseCode().equalsIgnoreCase(f.getWarehouseCode())
                                                    && info.getInventoryTypeCode().equalsIgnoreCase(f.getInventoryTypeCode())
                                                    && info.getProjectCode().equalsIgnoreCase(f.getProjectCode()))
                                            .collect(Collectors.toList());
                                } else {
                                    checkStockInfo = rtnData.stream().filter(f -> info.getWarehouseCode().equalsIgnoreCase(f.getWarehouseCode())
                                                    && info.getInventoryTypeCode().equalsIgnoreCase(f.getInventoryTypeCode()))
                                            .collect(Collectors.toList());

                                }
                                if (CollectionUtils.isEmpty(checkStockInfo)) {
                                    if (info.getWarehouseCode().equalsIgnoreCase(subCode)) {
                                        rtnData.add(idx, info);
                                        idx++;
                                    } else {
                                        rtnData.add(info);
                                    }
                                }
                            }
                        }

                    }
                }
                break;
            default:
                List<String> newWarehouseConfig = new ArrayList<String>(warehouseConfig);

                rtnData = this.setPreModelStockInfo(detailDO.getModelNo(), newWarehouseConfig);
        }
        // 已计算的通用在库的仓库。
        List<String> doWarehouseList = rtnData.stream().filter(i -> i.getAvaQty_ty() > 0).map(PreModelStockInfo::getWarehouseCode).distinct().collect(Collectors.toList());
        //去掉已计算的通用在库的仓库
        List<String> newWarehouseConfig = warehouseConfig.stream().filter(i -> !doWarehouseList.contains(i)).collect(Collectors.toList());
        //去掉委托在库的仓库
        newWarehouseConfig.removeIf(i -> i.startsWith("W") && i.length() >= 6);
        if (CollectionUtils.isNotEmpty(newWarehouseConfig)) {
            // 计算的异仓库通用在库
            List<PreModelStockInfo> preModelStockInfos = this.setPreModelStockInfo(detailDO.getModelNo(), newWarehouseConfig);
            rtnData.addAll(preModelStockInfos);
        }

        return rtnData.stream().filter(i -> i.getAvaQty() > 0).collect(Collectors.toList());
    }


    /**
     * bug-9750 有效在库、安全库存
     *
     * @param modelNo         型号
     * @param warehouseConfig 出库规则，可以出库的仓库代码
     * @return
     */
    @Override
    public List<PreModelStockInfo> getPreModelInventoryInfo(String modelNo, List<String> warehouseConfig) {
        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setWarehouseCodes(warehouseConfig);
        dto.setModelNos(Collections.singletonList(modelNo));
        ResultVo<List<WarehouseInventoryVO>> stockInfoList = inventoryServiceFeignApi.getWarehouseCanUseStock(dto);
        if (!stockInfoList.isSuccess()) {
            throw new BusinessException("获取备库型号的在库信息失败: " + modelNo + stockInfoList.getMessage());
        }
        if (CollectionUtils.isEmpty(stockInfoList.getData())) {
            return Collections.emptyList();
        }
        // 获取BIN信息
        ResultVo<Map<String, BindataVO>> binDataMap = binServiceFeignApi.getBindataByModelNoAndWarehouse(modelNo, warehouseConfig);
        if (!binDataMap.isSuccess()) {
            throw new BusinessException("获取备库型号的在库信息失败: " + modelNo + binDataMap.getMessage());
        }

        List<PreModelStockInfo> inventoryInfoList = new ArrayList<>(stockInfoList.getData().size());
        PreModelStockInfo temp;
        BindataVO binDataInfo;

        for (WarehouseInventoryVO stockInfo : stockInfoList.getData()) {
            temp = new PreModelStockInfo();
            temp.setModelNo(stockInfo.getModelNo());
            temp.setWarehouseCode(stockInfo.getWarehouseCode());
            temp.setAvaQty_ty(stockInfo.getTyAvaQty());
            temp.setAvaQty_zy(stockInfo.getZyAvaQty());
            temp.setAvaQty(stockInfo.getTyAvaQty());

            if (binDataMap.getData() != null && binDataMap.getData().containsKey(temp.getWarehouseCode())) {
                binDataInfo = binDataMap.getData().get(temp.getWarehouseCode());
                temp.setStockType(binDataInfo.getStockType());
                temp.setQtyBin(Optional.ofNullable(binDataInfo.getQtyBin()).orElse(0));
                temp.setBinCell(Optional.ofNullable(binDataInfo.getBinCell()).orElse(0));
                temp.setFreq(Optional.ofNullable(binDataInfo.getFreq()).orElse(0));
                temp.setMean(Optional.ofNullable(binDataInfo.getMean()).orElse(0));
                temp.setSafeQty(Optional.ofNullable(binDataInfo.getSafeQty()).orElse(0));
            }
            // 计算可用月数
            temp.setMonths(temp.getMean() == 0 ? 0 : (temp.getAvaQty_ty() + temp.getAvaQty_zy()) / temp.getMean());
            // 计算剩余数量
            int excessQty = temp.getAvaQty_ty() - (temp.getQtyBin() * (1 + temp.getBinCell()));
            temp.setExcessQty(excessQty < 0 ? 0 : excessQty);

            // BIN类型: 可用库存 = 通用在库有效库存- 2倍月用量
            if (temp.getStockType() != null && temp.getStockType() == 1) {
                int canUseQty = temp.getAvaQty() - temp.getSafeQty();
                temp.setAvaQty(canUseQty);
            }
            if (temp.getAvaQty_ty() > 0) {
                inventoryInfoList.add(temp);
            }
        }
        return inventoryInfoList.stream()
                // 根据出库优先级排序
                .sorted(Comparator.comparingInt(o -> warehouseConfig.indexOf(o.getWarehouseCode())))
                .collect(Collectors.toList());
    }

    /**
     * 获取先行备库出在库仓库
     * 不同仓库/库存类型的处理逻辑
     * 委托在库 (WT) 类型
     *   根据客户代码获取营业所信息
     *   获取该营业所的仓库配置规则
     *   移除当前仓库代码（避免自调拨）
     *   优先使用包含客户代码或代理商标识的仓库
     * 通用在库 (TY) 类型
     *      RDC(物流中心)类型的通用在库不被支持
     *      FDC(分库)/WT(委托仓库)的通用在库，出库仓库为其归属的物流中心
     * 关东地区库存 (GK) 类型
     *      根据客户或用户代码获取营业所配置
     *      对FDC(分库)类型，限制只能从物流中心或当前分库抽取库存
     * 战略库存 (ZL) 类型
     *      集团/项目类型: 可以调库，包括当前仓库和父级仓库
     *      产品/行业类型: 仅限采购，不允许调库
     * 后置过滤规则
     * RDC仓库作为备库时，只能包含其他RDC仓库(防止跨层级调拨)
     * @param applyInfo 申请信息
     * @return 出在库
     */
    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ResultVo<List<String>> getWarehouseConfig(PreStockApplyDO applyInfo) {
        List<String> warehouseConfig = new ArrayList<>();

        String id = "0";
        if (ObjectUtils.isNotEmpty(applyInfo.getId())) {
            id = applyInfo.getId().toString();
        }
        String key = Constants.REDIS_TRANS_WAREHOUSE_BY_WAREHOUSE_CODE + id;
        Object object = redisManager.get(key);
        if (Objects.nonNull(object)) {
            warehouseConfig = JSONArray.parseArray(object.toString(), String.class);
        } else {

            String warehouseType = opsWarehouseService.getWarehouseType(applyInfo.getWarehouseCode());

            if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseType)) {
                // 委托在库仓库专备
                // 客户专备，根据客户代码或用户代码查出它所属营业所代码来获取出库规则
                CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(applyInfo.getCustomerNo());
                if (customerInfo == null) {
                    log.error("出库规则获取失败-获取客户信息失败: {}", applyInfo.getCustomerNo());
                    return ResultVo.failure("出库规则获取失败-获取客户信息失败");
                }
                warehouseConfig.addAll(opsWarehouseService.getOpsWarehouseSalesBranchConfig(customerInfo.getHRUnitId()));
                if (CollectionUtils.isEmpty(warehouseConfig)) {
                    return ResultVo.failure("出库规则获取失败-请检查客户营业所是否已设置出库规则");
                }
                //    <!--add by WuWeiDong 20240218  bug 13489   分库+ 物流中心) -->
                warehouseConfig.removeIf(s -> s.equals(applyInfo.getWarehouseCode()));

                // 委托在库专备优先出委托仓库
                String warehouseCode = null;
                if (applyInfo.getWarehouseCode().contains(applyInfo.getCustomerNo())) {
                    warehouseCode = applyInfo.getWarehouseCode();
                } else {
                    String agentNo = customerInfo.getAgentNo(); // 代理商
                    if (StringUtils.isNotBlank(agentNo) && applyInfo.getWarehouseCode().contains(agentNo)) {
                        warehouseCode = applyInfo.getWarehouseCode();
                    }
                }
                if (StringUtils.isNotBlank(warehouseCode) && !warehouseConfig.contains(warehouseCode)) {
                    warehouseConfig.add(0, warehouseCode);
                }
            } else if (InventoryTypeEnum.TY.getCode().equals(applyInfo.getStockType())) {
                // 通用在库类型的备库仓库范围不包括-物流中心
                if (WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseType)) {
                    return ResultVo.failure("先行在库申请不包括物流中心的通用在库");
                } else {
                    // （分库/委托仓库）的通用在库类型备库的出库仓库是它们的归属物流中心
                    WarehouseDO warehouseInfo = opsWarehouseService.getWarehouseInfoByCode(applyInfo.getWarehouseCode());
                    if (warehouseInfo == null) {
                        log.error("出库规则获取失败-获取仓库信息失败");
                        return ResultVo.failure("出库规则获取失败-获取仓库信息失败");
                    }
                    warehouseConfig.add(warehouseInfo.getParentCode());
                }
            } else if (applyInfo.getStockType().startsWith("GK")) {
                String customerNo;
                if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseType)) {
                    customerNo = applyInfo.getCustomerNo();
                } else {
                    customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? applyInfo.getCustomerNo() : applyInfo.getUserNo();
                }
                // 客户专备，根据客户代码或用户代码查出它所属营业所代码来获取出库规则
                CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(customerNo);
                if (customerInfo == null) {
                    log.error("出库规则获取失败-获取客户信息失败: {}", customerNo);
                    return ResultVo.failure("出库规则获取失败-获取客户信息失败");
                }

                warehouseConfig.addAll(opsWarehouseService.getOpsWarehouseSalesBranchConfig(customerInfo.getHRUnitId()));
                if (CollectionUtils.isEmpty(warehouseConfig)) {
                    return ResultVo.failure("出库规则获取失败-请检查客户营业所是否已设置出库规则");
                }
                /***
                 // 委托在库仓库专备
                 if (WarehouseTypeEnum.WT.getHouseTypeCode().equals(warehouseType)) {
                 // 委托在库不能抽分库 (可抽取: 物流中心+当前委托在库)
                 // List<String> masterWarehouses = dictCommonService.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode()).getData();
                 // warehouseConfig.removeIf(s -> !masterWarehouses.contains(s) && !s.equals(applyInfo.getWarehouseCode()));

                 //    <!--add by WuWeiDong 20240218  bug 13489   分库+ 物流中心) -->
                 warehouseConfig.removeIf(s -> s.equals(applyInfo.getWarehouseCode()));

                 // 委托在库专备优先出委托仓库
                 String warehouseCode = null;
                 if (applyInfo.getWarehouseCode().contains(customerNo)) {
                 warehouseCode = applyInfo.getWarehouseCode();
                 } else {
                 String agentNo = customerInfo.getAgentNo(); // 代理商
                 if (StringUtils.isNotBlank(agentNo) && applyInfo.getWarehouseCode().contains(agentNo)) {
                 warehouseCode = applyInfo.getWarehouseCode();
                 }
                 }
                 if (StringUtils.isNotBlank(warehouseCode) && !warehouseConfig.contains(warehouseCode)) {
                 warehouseConfig.add(0, warehouseCode);
                 }
                 }
                 ***/
                // 客户专备-分库备库不能抽取其他分库的库存(可抽取: 物流中心+当前分库)
                if (WarehouseTypeEnum.FDC.getHouseTypeCode().equals(warehouseType)) {
                    List<String> masterWarehouses = dictCommonService.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode()).getData();
                    warehouseConfig.removeIf(s -> !masterWarehouses.contains(s) && !s.equals(applyInfo.getWarehouseCode()));
                }
            } else if (applyInfo.getStockType().startsWith("ZL")) {
                // 战略在库产品和行业仅做采购, 集团和项目可以调库根据部门代码返回备库仓库
                if (applyInfo.getStockType().equals(InventoryTypeEnum.ZL_JT.getCode()) || applyInfo.getStockType().equals(InventoryTypeEnum.ZL_PJ.getCode())) {
                    warehouseConfig = new ArrayList<>(4);
                    if (WarehouseTypeEnum.RDC.getHouseTypeCode().equalsIgnoreCase(warehouseType)) {
                        warehouseConfig.add(applyInfo.getWarehouseCode());
                    } else {
                        WarehouseDO warehouseInfo = opsWarehouseService.getWarehouseInfoByCode(applyInfo.getWarehouseCode());


                        if (warehouseInfo == null) {
                            log.error("出库规则获取失败-获取仓库信息失败");
                            return ResultVo.failure("出库规则获取失败-获取仓库信息失败");
                        }
                        warehouseConfig.add(warehouseInfo.getWarehouseCode());
                        warehouseConfig.add(warehouseInfo.getParentCode());
                    }
                    this.setSubTransferWarehouseConfig(warehouseConfig);
                } else {
                    warehouseConfig.add(applyInfo.getWarehouseCode());
                }
            }
            // 如果备库仓库是物流中心调库仓库不能包含分库&委托在库(可抽取: 物流中心)
            if (CollectionUtils.isNotEmpty(warehouseConfig) && WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseType)) {
                List<String> masterWarehouses = dictCommonService.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode()).getData();
                warehouseConfig.removeIf(s -> !masterWarehouses.contains(s));
            }
            if (PublicUtil.isNotEmpty(warehouseConfig) && !id.equalsIgnoreCase("0")) {
                redisManager.set(key, JSON.toJSONString(warehouseConfig), 60 * 60 * 12);
            }
        }

        return ResultVo.success(warehouseConfig);
    }

    /**
     * 更新shikomi申请处理状态
     *
     * @param detailList shikomi申请项
     */
    private void updatePreStockShikomiStatus(List<PreStockDetailDO> detailList) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        int updateDetailStatus = 6; // 已处理
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
        boolean updateResult;
        List<Long> applyIds = new ArrayList<>();

        // 更新Shikimi申请项的处理状态
        for (PreStockDetailDO detail : detailList) {
            detailUpdateWrapper.clear();
            detailUpdateWrapper.set(PreStockDetailDO::getStatus, updateDetailStatus)
                    .set(PreStockDetailDO::getUpdateUser, userDTO.getUserNo());
            detailUpdateWrapper.eq(PreStockDetailDO::getId, detail.getId());
            updateResult = preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper) > 0;
            // 更新Shikomi申请的处理状态
            if (updateResult) {
                applyIds.add(detail.getApplyId());
            }
        }
        // 更新shikomi申请的处理状态
        if (applyIds.size() > 0) {
            for (Long applyId : applyIds) {
                this.updateApplyFinishStatus(applyId, true);
            }
        }
    }

    /**
     * 更新申请处理完成状态
     *
     * @param applyId 申请id
     */
    private void updateApplyFinishStatus(long applyId, boolean isShikomi) {
        // 查询申请的未处理项数量，如果等于0，则表示申请已完成
        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.eq(PreStockDetailDO::getApplyId, applyId)
                .notIn(PreStockDetailDO::getStatus, 4, 6, 9)
                .eq(PreStockDetailDO::getDelFlag, 0);
        int unProcessedCount = preStockDetailMapper.selectCount(detailQuery);
        if (unProcessedCount == 0) {
            int applyStatus = isShikomi ? 5 : 3; // 5-已确认（shikomi）else 3-待处理
            int applyUpdateStatus = 6; // 已处理
            LambdaUpdateWrapper<PreStockApplyDO> applyUpdateWrapper = new LambdaUpdateWrapper<>();
            applyUpdateWrapper.set(PreStockApplyDO::getStatus, applyUpdateStatus)
                    .set(PreStockApplyDO::getUpdateUser, SMCApp.getLoginAuthDto().getUserNo());
            applyUpdateWrapper.eq(PreStockApplyDO::getId, applyId)
                    .eq(PreStockApplyDO::getStatus, applyStatus);
            preStockApplyMapper.update(new PreStockApplyDO(), applyUpdateWrapper);
        }
    }

    /**
     * 先行在库申请处理时，将自动周转类型申请的qtybin更新到BinData
     *
     * @param applyId 申请id
     */
    @Override
    public void updateQtyBinToBinData(long applyId) {
        LambdaQueryWrapper<PreStockApplyDO> applyQuery = new LambdaQueryWrapper<>();
        applyQuery.select(PreStockApplyDO::getId, PreStockApplyDO::getApplyType, PreStockApplyDO::getReplType,
                PreStockApplyDO::getStockType, PreStockApplyDO::getCustomerNo, PreStockApplyDO::getUserNo,
                PreStockApplyDO::getWarehouseCode, PreStockApplyDO::getPplNo, PreStockApplyDO::getProjectNo,
                PreStockApplyDO::getGroupCustomerNo, PreStockApplyDO::getStatus);
        applyQuery.eq(PreStockApplyDO::getId, applyId)
                .eq(PreStockApplyDO::getReplType, PreStockReplTypeEnum.AUTO.getCode())
                .eq(PreStockApplyDO::getStatus, PreStockApplyStatusEnum.processing.getCode());
        PreStockApplyDO applyInfo = preStockApplyMapper.selectOne(applyQuery);
        if (applyInfo == null) {
            return;
        }

        LambdaQueryWrapper<PreStockDetailDO> detailQuery = new LambdaQueryWrapper<>();
        detailQuery.select(PreStockDetailDO::getId, PreStockDetailDO::getItemNo, PreStockDetailDO::getApplyModelNo,
                PreStockDetailDO::getQtybin, PreStockDetailDO::getBincell, PreStockDetailDO::getPplNo, PreStockDetailDO::getProjectNo);
        detailQuery.eq(PreStockDetailDO::getApplyId, applyId)
                .gt(PreStockDetailDO::getQtybin, 0) // QtyBin > 0
                .eq(PreStockDetailDO::getDelFlag, 0);
        List<PreStockDetailDO> detailList = preStockDetailMapper.selectList(detailQuery);

        BindataVO dto;
        int stockType = 4; // 客户BIN
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? applyInfo.getCustomerNo() : applyInfo.getUserNo();
        Map<String, Integer> minPackUnitMap = new HashMap<>();
        ResultVo<Integer> minPackUnitResult;
        Map<String, String> supplierMap = new HashMap<>();
        ResultVo<String> result;
        Set<Integer> itemSet = new HashSet<>(detailList.size());

        for (PreStockDetailDO detail : detailList) {
            if (itemSet.contains(detail.getItemNo())) {
                continue;
            }
            if (!minPackUnitMap.containsKey(detail.getApplyModelNo())) {
                minPackUnitResult = productServiceFeignApi.getMinPackUnitByModelNo(detail.getApplyModelNo());
                if (!minPackUnitResult.isSuccess()) {
                    throw new BusinessException("自动周转失败, 无法获取最小包装数: " + detail.getApplyModelNo() + minPackUnitResult.getMessage());
                }
                minPackUnitMap.put(detail.getApplyModelNo(), Optional.ofNullable(minPackUnitResult.getData()).orElse(1));
            }
            if (!supplierMap.containsKey(detail.getApplyModelNo())) {
                result = productServiceFeignApi.getSupplierNoByModelNo(detail.getApplyModelNo());
                if (result.isSuccess()) {
                    supplierMap.put(detail.getApplyModelNo(), result.getData());
                }
            }

            dto = new BindataVO();
            dto.setStockType(stockType);
            dto.setCustomerNo(customerNo);
            dto.setWarehouseCode(applyInfo.getWarehouseCode());
            dto.setInventoryTypeCode(applyInfo.getStockType());
            dto.setPpl(StringUtils.isNotBlank(detail.getPplNo()) ? detail.getPplNo() : applyInfo.getPplNo());
            dto.setProjectNo(StringUtils.isNotBlank(detail.getProjectNo()) ? detail.getProjectNo() : applyInfo.getProjectNo());
            dto.setGroupCustomerNo(StringUtils.isNotBlank(detail.getGroupCustomerNo()) ? detail.getGroupCustomerNo() : applyInfo.getGroupCustomerNo());
            if (InventoryTypeEnum.GK_TY.getCode().equals(dto.getInventoryTypeCode())) {
                if (StringUtils.isNotBlank(dto.getPpl())) {
                    dto.setInventoryTypeCode(InventoryTypeEnum.GK_PPL.getCode());
                }
                if (StringUtils.isNotBlank(dto.getProjectNo())) {
                    dto.setInventoryTypeCode(InventoryTypeEnum.GK_PJ.getCode());
                }
            }
            dto.setModelNo(detail.getApplyModelNo());
            dto.setBinCell(detail.getBincell());
            dto.setQtyBin(detail.getQtybin());
            dto.setSupplierCode(supplierMap.get(detail.getApplyModelNo()));
            dto.setMinPackageQty(minPackUnitMap.get(detail.getApplyModelNo()));
            result = binServiceFeignApi.saveBindata(dto);
            //log.info("BindataVO = {}", JSON.toJSONString(dto));
            if (result.isSuccess()) {
                itemSet.add(detail.getItemNo());
                log.info("先行在库-自动周转处理成功: applyId = {}, itemNo={}", applyId, detail.getItemNo());
            } else {
                log.error("先行在库处理异常-更新自动周转申请QtyBin数值到BinData失败: result = {}, applyId = {}, itemNo ={}, {}", result, detail.getItemNo(), detail);
            }
        }
    }

    /**
     * 设置申请项的特殊出货标识
     *
     * @param detail    申请项信息
     * @param detailDTO 申请项信息
     */
    private void setDetailSpecExpType(PreStockDetailDO detail, PreStockDetailDTO detailDTO) {
        // ROHS10
        if (Optional.ofNullable(detailDTO.getRohs10()).orElse(Boolean.FALSE)) {
            detail.setSpecExpType(64);
        }
        // 组装标识
        if ("1".equals(detailDTO.getSpecMark())) {
            detail.setSpecMark("1");
        } else if ("2".equals(detailDTO.getSpecMark())) {
            detail.setSpecMark("2");
        } else {
            detail.setSpecMark("0");
        }
    }

    /**
     * @param specExpType 特殊出货标识
     * @return 1-是, 0-否
     */
    private Boolean getRohs10(Integer specExpType) {
        if (specExpType == null) {
            return Boolean.FALSE;
        } else {
            return (specExpType & 64) == 64;
        }
    }

    /**
     * 设置shikomi信息
     */
    private void handleShikomiApplyInfo(PreStockApplyDO applyInfo) {
        if (StringUtils.isBlank(applyInfo.getApplyPsnNo())) {
            throw new BusinessException("Shikomi申请担当不能为空.");
        }
        if (StringUtils.isBlank(applyInfo.getApproverNo())) {
            throw new BusinessException("Shikomi审批负责人不能为空.");
        }
        if (applyInfo.getApproveTime() == null) {
            throw new BusinessException("Shikomi最终审批时间不能为空.");
        }
        ResultVo<EmployeeVO> empInfoResult;
        // 获取申请人姓名与邮箱
        if (StringUtils.isBlank(applyInfo.getApplyPsn()) || StringUtils.isBlank(applyInfo.getApplyPsnMail())) {
            empInfoResult = commonServiceFeignApi.getEmployeeInfo(applyInfo.getApplyPsnNo());
            if (!empInfoResult.isSuccess()) {
                log.error("JP_Shikomi申请人信息获取失败: {}", empInfoResult);
                throw new BusinessException("Shikomi申请人信息获取失败");
            }
            if (StringUtils.isBlank(applyInfo.getApplyPsn())) {
                applyInfo.setApplyPsn(empInfoResult.getData().getName());
            }
            if (StringUtils.isBlank(applyInfo.getApplyPsnMail())) {
                applyInfo.setApplyPsnMail(empInfoResult.getData().getEmail());
            }
        }
        // 获取审批人姓名与邮箱
        if (StringUtils.isBlank(applyInfo.getApproverName()) || StringUtils.isBlank(applyInfo.getApproverMail())) {
            empInfoResult = commonServiceFeignApi.getEmployeeInfo(applyInfo.getApproverNo());
            if (!empInfoResult.isSuccess()) {
                log.error("JP_Shikomi负责人信息获取失败: {}", empInfoResult);
                throw new BusinessException("Shikomi负责人信息获取失败");
            }
            if (StringUtils.isBlank(applyInfo.getApproverName())) {
                applyInfo.setApproverName(empInfoResult.getData().getName());
            }
            if (StringUtils.isBlank(applyInfo.getApproverMail())) {
                applyInfo.setApproverMail(empInfoResult.getData().getEmail());
            }
        }
        // 设置shikomi申请客户英文名称
        if (StringUtils.isNotBlank(applyInfo.getCustomerNo())) {
            String customerEnName = commonServiceFeignApi.getCustomerENameByNo(applyInfo.getCustomerNo()).getData();
            applyInfo.setEnname(customerEnName);
        }
        if (applyInfo.getWarehouseCode() == null) {
            applyInfo.setWarehouseCode("");
        }
        if (StringUtils.isBlank(applyInfo.getReasonEn())) {
            applyInfo.setReasonEn(applyInfo.getReason());
        }
    }

    /**
     * 设置申请项的shikomi信息
     */
    private void handleShikomiApplyDetail(PreStockDetailDO detail) {
        // 设置shikomi申请型号的英文名称
        if (StringUtils.isBlank(detail.getModelName())) {
            ResultVo<String> modelNameResult = productServiceFeignApi.getProductEnglishName(detail.getModelNo());
            if (modelNameResult.isSuccess() && modelNameResult.getData() != null) {
                detail.setModelName(modelNameResult.getData());
            }
        }
    }

    @Override
    public ResultVo<String> exportJPShikomiFile(List<PreStockDetailDO> detailList, DataCodeVO mailInfo) {
        // 生成日本SHIKOMI文件
        Map<String, InputStream> attachment = this.createJPShikomiFile(detailList);
        return transactionTemplate.execute(action -> {
            try {
                // 更新日本shikomi处理状态
                this.updatePreStockShikomiStatus(detailList);
                log.info(">>>>>> 更新日本shikomi处理状态 >>>>>>");
                // 发送日本shikomi附件邮件
                String to = mailInfo.getExtNote1();
                String cc = mailInfo.getExtNote2();
                String bcc = Optional.ofNullable(mailInfo.getExtNote3()).orElse("");
                String subject = "SHIKOMI Reservation Data (CN)";
                String content = "SHIKOMI Reservation Data (CN)";
                boolean send = EmailUtil.send(javaMailSender, to, cc, bcc, subject, content, attachment);
                if (!send) {
                    action.setRollbackOnly();
                    return ResultVo.failure("邮件发送失败");
                }

                return ResultVo.success("日本SHIKOMI导出成功- " + mailInfo.getExtNote1() + ";");
            } catch (Exception e) {
                action.setRollbackOnly();
                log.error("日本SHIKOMI导出异常: {}", e.getMessage(), e);
                return ResultVo.failure("日本SHIKOMI导出失败:" + e.getMessage());
            }
        });
    }

    /**
     * 生成日本Shikomi文件
     *
     * @param detailList 日本shikomi申请项信息
     * @return rowNum 行数
     */
    private Map<String, InputStream> createJPShikomiFile(List<PreStockDetailDO> detailList) {
        if (detailList == null || detailList.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, InputStream> attachment = new LinkedHashMap<>(2); // 邮件附件

        // 按申请分组
        Map<Long, List<PreStockDetailDO>> applyMap = detailList.stream()
                .collect(Collectors.groupingBy(PreStockDetailDO::getApplyId));
        PreStockApplyDO applyInfo;

        String zero = "0";
        String blank = " ";
        Map<String, List<PreStockDetailDO>> modelMap;
        BigDecimal price; // 单价
        String dlvDate;
        String[] demandMonth; // 需求月份
        int[] demandQty; // 需求月份对应需求数量

        String modelNo;
        String category;
        String cell_6 = "6900";
        String smcSubSidaryCode = "95012";
        String countryCode = "CN";
        String currency = "RMB";
        String rohs10;
        int total; // 总数量
        String generalManagerName = "MaQingHai"; // 总经理

        File jpShikomiFile;
        try {
            jpShikomiFile = File.createTempFile("SHIKOMI", ".dat");
        } catch (IOException e) {
            throw new BusinessException("日本SHIKOMI文件创建失败", e);
        }

        try (FileOutputStream is = new FileOutputStream(jpShikomiFile);
             OutputStreamWriter ow = new OutputStreamWriter(is);
             BufferedWriter bw = new BufferedWriter(ow)) {

            // 按申请id分组
            for (Map.Entry<Long, List<PreStockDetailDO>> applyEntry : applyMap.entrySet()) {
                applyInfo = preStockApplyMapper.selectById(applyEntry.getKey());
                // 申请项按modelNo分组
                modelMap = applyEntry.getValue().stream().collect(Collectors.groupingBy(PreStockDetailDO::getModelNo));
                for (Map.Entry<String, List<PreStockDetailDO>> detailEntry : modelMap.entrySet()) {
                    // 申请型号
                    modelNo = detailEntry.getKey();
                    // 按期望发货日期排序
                    detailEntry.setValue(detailEntry.getValue().stream().sorted(Comparator.comparing(PreStockDetailDO::getDlvDateJp)).collect(Collectors.toList()));
                    // 获取单价
                    price = detailEntry.getValue().get(0).getPrice();
                    // 计算总数量
                    total = detailEntry.getValue().stream().mapToInt(PreStockDetailDO::getQuantity).sum();
                    // 日本shikomi类型
                    if (ShikomiClassCodeEnum.Global.getCode().equals(applyInfo.getShikomiClass())) {
                        category = ShikomiClassTypeEnum.C.getCode(); // 共享
                    } else {
                        category = ShikomiClassTypeEnum.A.getCode(); // 专享
                    }
                    // 统计需求月份及数量
                    Date minDlvDate = DateUtil.getMonthFirstDate(detailEntry.getValue().get(0).getDlvDateJp());
                    demandMonth = new String[12]; // 需求月份
                    demandQty = new int[12]; // 需求月份对应需求数量
                    for (int i = 0; i < demandMonth.length; i++) {
                        demandMonth[i] = DateUtil.getSimpleYearMonthCode(DateUtil.addMonth(minDlvDate, i));
                    }
                    for (PreStockDetailDO detail : detailEntry.getValue()) {
                        dlvDate = DateUtil.getSimpleYearMonthCode(detail.getDlvDateJp());
                        for (int i = 0; i < demandMonth.length; i++) {
                            if (dlvDate.equals(demandMonth[i])) {
                                demandQty[i] += detail.getQuantity();
                            }
                        }
                    }
                    // ROHS10
                    rohs10 = this.getRohs10(detailEntry.getValue().get(0).getSpecExpType()) ? "ROHS10" : "";

                    bw.write(fillValue((13 + 2), blank, "")); // 1~2
                    bw.write(fillValue(1, blank, category)); // 3
                    bw.write(fillValue(25, blank, applyInfo.getApplyNo())); // 4
                    bw.write(fillValue(2, blank, "")); // 5
                    bw.write(fillValue(4, blank, cell_6)); // 6
                    bw.write(fillValue(5, blank, smcSubSidaryCode)); // 7
                    bw.write(fillValue(2, blank, countryCode)); // 8
                    bw.write(fillValue(2, blank, "")); // 9
                    bw.write(fillValue(20, blank, PinyinUtil.getPinyin(applyInfo.getApplyPsn(), ""))); // 10 申请人
                    bw.write(fillValue(30, blank, this.convertStr(applyInfo.getEnname()))); // 11 客户名称
                    bw.write(fillValue(30, blank, modelNo)); // 12 型号
                    bw.write(fillValue(7, zero, Integer.toString(total))); // 13 总数量
                    bw.write(handleAmount(price)); // 14 单价
                    bw.write(fillValue(3, zero, currency)); // 15 货币类型
                    // 16~27 Shikomi Plan Year/Month 需求月份
                    for (String month : demandMonth) {
                        bw.write(fillValue(4, zero, month));
                    }
                    // 28~39 Request Qty 需求数量
                    for (int quantity : demandQty) {
                        bw.write(fillValue(5, zero, String.valueOf(quantity)));
                    }
                    // 40 ROHS10
                    bw.write(fillValue(72, blank, rohs10));
                    bw.write(fillValue((72 * 6), blank, this.convertStr(applyInfo.getReasonEn()))); // 41~45 note1~6 申请原因
                    bw.write(fillValue(30, blank, detailEntry.getValue().get(0).getModelName())); // 46 型号英文名称
                    bw.write(fillValue(50, blank, this.convertStr(applyInfo.getApplyPsnMail()))); // 47 担当者1 邮箱
                    bw.write(fillValue(50, blank, "")); // 48 担当者2 邮箱
                    bw.write(fillValue(50, blank, applyInfo.getApproverMail())); // 49 管理者1 邮箱
                    bw.write(fillValue(50, blank, "")); // 50 管理者2 邮箱
                    bw.write(fillValue(40, blank, PinyinUtil.getPinyin(applyInfo.getApplyPsn(), ""))); // 51 当担者1
                    bw.write(fillValue(40, blank, "")); // 52 当担者2
                    bw.write(fillValue(40, blank, PinyinUtil.getPinyin(applyInfo.getApproverName(), ""))); // 53 管理者1
                    bw.write(fillValue(40, blank, "")); // 54 管理者2
                    bw.write(fillValue(40, blank, generalManagerName)); // 55 总经理名称
                    bw.write(fillValue(8, blank, DateUtil.getYearMonthDay(applyInfo.getApproveTime()))); // 56 审批日期
                    bw.newLine();
                }
            }
            bw.flush();
            // SHIKOMI --> SKM1SCN
            attachment.put("SKM1SCN.dat", new FileInputStream(jpShikomiFile));
        } catch (Exception e) {
            throw new BusinessException("日本shikomi生成失败: " + e.getMessage(), e);
        } finally {
            if (jpShikomiFile.exists()) {
                jpShikomiFile.delete();
            }
        }
        return attachment;
    }

    @Override
    public ResultVo<String> exportCNShikomiFile(List<PreStockDetailDO> detailList, DataCodeVO mailInfo) {

        ExcelHelper excel;
        Map<String, InputStream> attachment = new LinkedHashMap<>(detailList.size());

        Map<Long, List<PreStockDetailDO>> applyMap = detailList.stream().collect(Collectors.groupingBy(PreStockDetailDO::getApplyId));
        PreStockApplyDO applyInfo;
        Map<String, List<PreStockDetailDO>> modelMap;
        String no;
        String noFormat = "%s-%02d";
        String modelNo; // 型号
        int quantity; // 数量
        String rohs10;
        String customerName;
        Map<String, String> nameMap = new HashMap<>();
        ResultVo<String> resultVo;
        ResultVo<EmployeeVO> indMgrInfo; // 行业负责人信息
        Map<String, EmployeeVO> indMgrMap = new HashMap<>();
        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" cellpadding=\"3\" style=\"border-collapse:collapse; width:80%;\" >")
                .append("<thead style=\"font-weight: bold;color: #000000;background-color: #B8CCE4;\" >")
                .append("<tr><td width=\"15%\" >申请号</td><td width=\"10%\" >申请日期</td><td width=\"10%\" >客户代码</td><td width=\"15%\" >客户名称</td><td width=\"20%\" >申请型号</td><td width=\"10%\" >申请数量</td><td width=\"5%\" >ROHS10</td><td width=\"15%\" >总经理审批时间</td></tr>")
                .append("</thead><tbody>");

        // 按申请分组
        for (Map.Entry<Long, List<PreStockDetailDO>> applyEntry : applyMap.entrySet()) {
            applyInfo = preStockApplyMapper.selectById(applyEntry.getKey());
            modelMap = applyEntry.getValue().stream().collect(Collectors.groupingBy(PreStockDetailDO::getModelNo));

            // 行业申请-获取行业负责人
            if (StringUtils.isNotBlank(applyInfo.getIndCode())) {
                if (!indMgrMap.containsKey(applyInfo.getIndCode())) {
                    resultVo = commonServiceFeignApi.getParentNumberByDeptNo(applyInfo.getDeptNo());
                    if (!resultVo.isSuccess() || resultVo.getData() == null) {
                        throw new BusinessException("获取行业部门失败");
                    }
                    if (!"235000".equals(resultVo.getData())) {
                        indMgrInfo = commonServiceFeignApi.getIndManageInfoByDeptNo(resultVo.getData());
                        if (!indMgrInfo.isSuccess() || indMgrInfo.getData() == null) {
                            throw new BusinessException("获取行业负责人失败");
                        }
                        indMgrMap.put(applyInfo.getIndCode(), indMgrInfo.getData());
                    }
                }
                if (indMgrMap.get(applyInfo.getIndCode()) != null) {
                    applyInfo.setApproverNo(indMgrMap.get(applyInfo.getIndCode()).getId());
                    applyInfo.setApproverName(indMgrMap.get(applyInfo.getIndCode()).getName());
                }
            }
            // 获取申请部门名称
            if (!nameMap.containsKey(applyInfo.getDeptNo())) {
                if (StringUtils.isNotBlank(applyInfo.getIndCode())) {
                    nameMap.put(applyInfo.getDeptNo(), opsCommonService.getDeptNameByNo(applyInfo.getDeptNo()));
                } else {
                    nameMap.put(applyInfo.getDeptNo(), opsCommonService.getDeptNameByNo(this.converDeptCode(applyInfo.getDeptNo())));
                }
                if (!nameMap.containsKey(applyInfo.getDeptNo())) {
                    throw new BusinessException("获取SHIKOMI申请营业所名称失败");
                }
            }

            // 按型号分组
            for (Map.Entry<String, List<PreStockDetailDO>> detailEntry : modelMap.entrySet()) {
                // 申请编号
                no = String.format(noFormat, applyInfo.getApplyNo(), detailEntry.getValue().get(0).getItemNo());
                // 型号
                modelNo = detailEntry.getKey();
                // 数量
                quantity = detailEntry.getValue().stream().mapToInt(PreStockDetailDO::getQuantity).sum();
                // 客户名称
                if (StringUtils.isNotBlank(applyInfo.getCustomerNo()) && !nameMap.containsKey(applyInfo.getCustomerNo())) {
                    nameMap.put(applyInfo.getCustomerNo(), opsCommonService.getCustomerNameByNo(applyInfo.getCustomerNo()));
                    if (!nameMap.containsKey(applyInfo.getCustomerNo())) {
                        throw new BusinessException("获取中国SHIKOMI客户名称失败");
                    }
                }
                // rohs10
                rohs10 = this.getRohs10(detailEntry.getValue().get(0).getSpecExpType()) ? "是" : "否";
                // 客户代码
                customerName = nameMap.getOrDefault(applyInfo.getCustomerNo(), "");
                if (StringUtils.isBlank(customerName) && StringUtils.isNotBlank(applyInfo.getIndCode())) {
                    customerName = applyInfo.getIndCode() + "行业";
                }

                // 生成邮件正文内容
                content.append("<tr>")
                        .append("<td>").append(no).append("</td>") // 申请号
                        .append("<td>").append(DateUtil.dateToString(applyInfo.getApplyTime())).append("</td>") // 申请日期
                        .append("<td>").append(applyInfo.getCustomerNo()).append("</td>") // 客户代码
                        .append("<td>").append(customerName).append("</td>") // 客户名称
                        .append("<td>").append(modelNo).append("</td>") // 申请型号
                        .append("<td>").append(quantity).append("</td>") // 申请数量
                        .append("<td>").append(rohs10).append("</td>") // ROHS
                        .append("<td>").append(DateUtil.dateToString(applyInfo.getApproveTime())).append("</td>") // 总经理审批时间
                        .append("</tr>");

                // 生成《仕込依頼書》
                excel = new ExcelHelper(FileUtil.getTemplate("template/仕込依頼書.xlsx"));
                this.createCNShikomiApplyFile(excel, applyInfo, detailEntry.getValue(), no, customerName, nameMap.get(applyInfo.getDeptNo()));
                attachment.put("仕込依頼書" + no + ".xlsx", excel.convertTo());
            }
        }
        content.append("</tbody></table></br>");

        String supplierName = opsCommonService.getSupplierNameByCode(mailInfo.getCode());
        // 手动开启事务
        return transactionTemplate.execute(action -> {
            try {
                // 更新日本shikomi处理状态
                this.updatePreStockShikomiStatus(detailList);
                log.info(">>>>>> 更新{}shikomi处理状态 >>>>>>", supplierName);
                // 发送日本shikomi附件邮件
                String to = mailInfo.getExtNote1();
                String cc = mailInfo.getExtNote2();
                String bcc = Optional.ofNullable(mailInfo.getExtNote3()).orElse("");
                String subject = "先行在库" + supplierName + "SHIKOMI文件";
                boolean send = EmailUtil.send(javaMailSender, to, cc, bcc, subject, content.toString(), attachment);
                if (!send) {
                    action.setRollbackOnly();
                    return ResultVo.failure(supplierName + "邮件发送失败");
                }

                return ResultVo.success(supplierName + "SHIKOMI导出成功-" + mailInfo.getExtNote1() + ";");
            } catch (Exception e) {
                action.setRollbackOnly();
                log.error("{}SHIKOMI导出异常: {}", supplierName, e.getMessage(), e);
                return ResultVo.failure(supplierName + "SHIKOMI导出失败: " + e.getMessage());
            }
        });
    }

    @Override
    @Transactional
    public ResultVo<String> resetShikomiProcess(PreStockResetProcessDTO dto) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        if (CollectionUtils.isEmpty(dto.getDetailIds())) {
            return ResultVo.failure("申请项id不能为空");
        }
        long detailId = dto.getDetailIds().get(0);

        if ("1".equals(dto.getResetType())) {
            // 修改
            LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
            detailUpdateWrapper.set(PreStockDetailDO::getStatus, 5) // 处理中
                    .set(PreStockDetailDO::getQuantity, dto.getQuantity())
                    .set(PreStockDetailDO::getDlvDateJp, dto.getDlvDateJP())
                    .set(PreStockDetailDO::getUpdateUser, userDTO.getUserNo());
            detailUpdateWrapper.eq(PreStockDetailDO::getId, detailId)
                    .eq(PreStockDetailDO::getDelFlag, 0);
            preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper);
        } else if ("2".equals(dto.getResetType())) {
            // 删除
            LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
            detailUpdateWrapper.set(PreStockDetailDO::getDelFlag, 1)
                    .set(PreStockDetailDO::getUpdateUser, userDTO.getUserNo());
            detailUpdateWrapper.eq(PreStockDetailDO::getId, detailId)
                    .eq(PreStockDetailDO::getDelFlag, 0);
            preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper);
        } else if ("3".equals(dto.getResetType())) {
            // 增加
            PreStockDetailDO detailDO = preStockDetailMapper.selectById(detailId);
            detailDO.setId(null);
            detailDO.setStatus("5");
            detailDO.setQuantity(dto.getQuantity());
            detailDO.setDlvDateJp(dto.getDlvDateJP());
            preStockDetailMapper.insert(detailDO);
        } else {
            ResultVo.failure("未知的处理类型");
        }

        return ResultVo.success();
    }

    @Override
    @Transactional
    public ResultVo<String> resetApplyProcessStatus(PreStockResetProcessDTO dto) {
        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        if (CollectionUtils.isEmpty(dto.getDetailIds())) {
            return ResultVo.failure("申请项id不能为空");
        }

        // 修改申请项状态
        LambdaUpdateWrapper<PreStockDetailDO> detailUpdateWrapper = new LambdaUpdateWrapper<>();
        detailUpdateWrapper.set(PreStockDetailDO::getStatus, 5) // 处理中
                .set(PreStockDetailDO::getUpdateUser, userDTO.getUserNo());
        detailUpdateWrapper.in(PreStockDetailDO::getId, dto.getDetailIds())
                .eq(PreStockDetailDO::getDelFlag, 0);
        preStockDetailMapper.update(new PreStockDetailDO(), detailUpdateWrapper);
        // 更新申请处理状态
        LambdaUpdateWrapper<PreStockApplyDO> masterUpdateWrapper = new LambdaUpdateWrapper<>();
        masterUpdateWrapper.set(PreStockApplyDO::getStatus, 5) // 待处理状态
                .set(PreStockApplyDO::getUpdateUser, userDTO.getUserNo());
        masterUpdateWrapper.eq(PreStockApplyDO::getId, dto.getApplyId());
        preStockApplyMapper.update(new PreStockApplyDO(), masterUpdateWrapper);
        return ResultVo.success("重置成功");
    }

    /**
     * 生成仕込依頼書
     */
    private void createCNShikomiApplyFile(ExcelHelper excelHelper, PreStockApplyDO
            applyInfo, List<PreStockDetailDO> detailList, String no, String CustomerName, String deptName) {

        SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) excelHelper.getWorkBook();
        sxssfWorkbook.setSheetName(sxssfWorkbook.getActiveSheetIndex(), no);

        String modelNo = detailList.get(0).getModelNo();
        String checkFlag = "☑";
        String unCheckFlag = "□";
        String supplierId = "CN";
        String country = "SMC-CN";
        String generalManagerName = "马清海"; // 总经理
        ResultVo<ProductInfoVO> productInfo;
        String[] demandMonth;
        int[] demandQty;
        int total;
        String dlvDate;

        // 预处理值
        productInfo = productServiceFeignApi.getProductInfoByModelNo(modelNo);
        // bug-11611 2023/7/27
        if (!productInfo.isSuccess() || productInfo.getData() == null) {
            log.error("SHIKOMI生成文件,获取产品信息失败: {} {}", applyInfo.getApplyNo(), productInfo);
            throw new BusinessException("获取产品信息失败");
        }
        detailList = detailList.stream().sorted(Comparator.comparing(PreStockDetailDO::getDlvDateJp)).collect(Collectors.toList());
        // 统计需求月份与数量
        Date minDlvDate = DateUtil.getMonthFirstDate(detailList.get(0).getDlvDateJp());
        demandMonth = new String[12];
        demandQty = new int[12];
        for (int i = 0; i < demandMonth.length; i++) {
            demandMonth[i] = DateUtil.getYearMonth(DateUtil.addMonth(minDlvDate, i));
        }
        for (PreStockDetailDO detail : detailList) {
            dlvDate = DateUtil.getYearMonth(detail.getDlvDateJp());
            for (int i = 0; i < demandMonth.length; i++) {
                if (dlvDate.equals(demandMonth[i])) {
                    demandQty[i] += detail.getQuantity();
                }
            }
        }
        // 总数量
        total = Arrays.stream(demandQty).sum();

        // 写入SHIKOMI申请项信息
        // 供应商
        excelHelper.setCellValue(2, excelHelper.getColNum("BT"), supplierId);
        // 申请日期
        excelHelper.setCellValue(2, excelHelper.getColNum("CV"), applyInfo.getApplyTime());
        // 申请号
        int applyNoIndex = excelHelper.getColNum("CA");
        int splitNum = 3;
        for (char c : no.toCharArray()) {
            excelHelper.setCellValue(4, applyNoIndex, String.valueOf(c));
            if (c == '-') {
                splitNum = 2;
            }
            applyNoIndex += splitNum;
        }
        // newFlag 新 or 继续
        if ("1".equals(detailList.get(0).getNewFlag())) {
            excelHelper.setCellValue(6, excelHelper.getColNum("B"), checkFlag);
            excelHelper.setCellValue(6, excelHelper.getColNum("K"), unCheckFlag);
        } else {
            excelHelper.setCellValue(6, excelHelper.getColNum("B"), unCheckFlag);
            excelHelper.setCellValue(6, excelHelper.getColNum("K"), checkFlag);
        }
        // Country
        excelHelper.setCellValue(7, excelHelper.getColNum("C"), country);
        // 营业所
        excelHelper.setCellValue(9, excelHelper.getColNum("C"), deptName);
        // 总经理
        excelHelper.setCellValue(10, excelHelper.getColNum("CF"), generalManagerName);
        // 所属长
        excelHelper.setCellValue(10, excelHelper.getColNum("DH"), applyInfo.getApproverName());
        // ECode
        excelHelper.setCellValue(13, excelHelper.getColNum("B"), productInfo.getData().getECode());
        // 型号
        excelHelper.setCellValue(13, excelHelper.getColNum("O"), modelNo);
        // 数量
        excelHelper.setCellValue(13, excelHelper.getColNum("BR"), total);
        // shikomiClass
        if ("0".equals(applyInfo.getShikomiClass())) {
            excelHelper.setCellValue(15, excelHelper.getColNum("B"), unCheckFlag);
            excelHelper.setCellValue(15, excelHelper.getColNum("AP"), checkFlag);
        } else {
            excelHelper.setCellValue(15, excelHelper.getColNum("B"), checkFlag);
            excelHelper.setCellValue(15, excelHelper.getColNum("AP"), unCheckFlag);
        }
        // 客户名称
        excelHelper.setCellValue(15, excelHelper.getColNum("CF"), CustomerName);
        // 信息来源（申请原因）
        excelHelper.setCellValue(18, excelHelper.getColNum("D"), applyInfo.getReason());
        // 需求计划
        int demandIndex = excelHelper.getColNum("D");
        String[] demandYearAndMonth;
        splitNum = 9;
        for (int i = 0; i < demandMonth.length; i++) {
            demandYearAndMonth = demandMonth[i].split("-");
            excelHelper.setCellValue(20, demandIndex, demandYearAndMonth[0]); // 需求年份
            excelHelper.setCellValue(21, demandIndex, demandYearAndMonth[1]); // 需求月份
            excelHelper.setCellValue(22, demandIndex, demandQty[i]); // 客户需求数量
            excelHelper.setCellValue(24, demandIndex, demandQty[i]); // 日本计划出货数量
            demandIndex += splitNum;
        }
        // 合计
        int totalQtyIndex = excelHelper.getColNum("DH");
        excelHelper.setCellValue(22, totalQtyIndex, total);
        excelHelper.setCellValue(24, totalQtyIndex, total);
        // 审批通过时间
        excelHelper.setCellValue(33, excelHelper.getColNum("CM"), DateUtil.dateToDateTimeString(applyInfo.getApproveTime()));
    }

    /**
     * 转换为固定长度格式的字符串
     *
     * @param length 目标字符串长度
     * @param fill   补位字符
     * @param value  原字符串
     * @return 已处理字符串
     */
    private String fillValue(int length, String fill, String value) {
        String zero = "0";
        StringBuilder temp = Optional.ofNullable(value).map(StringBuilder::new).orElse(new StringBuilder());
        int diff = length - temp.length();
        if (zero.equals(fill)) {
            for (int i = 0; i < diff; i++) {
                temp.insert(0, fill);
            }
        } else {
            for (int i = 0; i < diff; i++) {
                temp.insert(temp.length(), fill);
            }
        }
        return temp.toString();
    }

    /**
     * 金额格式化
     *
     * @param amount 金额
     * @return string
     */
    private String handleAmount(BigDecimal amount) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }
        DecimalFormat format = new DecimalFormat("000000000.00");
        String str = format.format(amount);
        return str.replaceAll("\\.", "");
    }

    /**
     * 根据仓库类型较验申请信息
     *
     * @param applyInfo 申请信息
     * @return 较验结果
     */
    private ResultVo<String> checkInventoryType(PreStockApplyDO applyInfo) {
        if (PreStockApplyTypeEnum.SHIKOMI.getCode().equals(applyInfo.getApplyType())) {
            return ResultVo.success("SHIKOMI申请,无需检查备库仓库信息");
        }
        if (StringUtils.isEmpty(applyInfo.getStockType())) {
            return ResultVo.failure("备库类型不能为空");
        }
        if ("TY".equals(applyInfo.getStockType())) {
            return ResultVo.success("通用库存类型");
        }
        StringBuilder errorMsg = new StringBuilder();
        if (applyInfo.getStockType().startsWith("GK") && StringUtils.isEmpty(applyInfo.getCustomerNo())) {
            errorMsg.append("客户代码不能为空.");
        }
        if (applyInfo.getStockType().endsWith("PPL") && StringUtils.isEmpty(applyInfo.getPplNo())) {
            errorMsg.append("PPL不能为空.");
        }
        if (applyInfo.getStockType().endsWith("PJ") && StringUtils.isEmpty(applyInfo.getProjectNo())) {
            errorMsg.append("项目代码不能为空.");
        }
        if ((applyInfo.getStockType().endsWith("JT") || applyInfo.getStockType().endsWith("HY")) && StringUtils.isEmpty(applyInfo.getGroupCustomerNo())) {
            errorMsg.append("集团编号不能为空.");
        }
        return errorMsg.length() > 0 ? ResultVo.failure(errorMsg.toString()) : ResultVo.success("备库类型较验通过");
    }

    /**
     * 设置申请项的供应商信息
     *
     * @param detail      申请项信息
     * @param supplierMap Map<modelNo, supplierNo>
     */
    private void setDetailSupplier(PreStockDetailDO detail, Map<String, String> supplierMap) {
        String supplierNo;
        if (supplierMap != null && supplierMap.containsKey(detail.getModelNo())) {
            supplierNo = supplierMap.get(detail.getModelNo());
        } else {
            // 根据型号查询供应商
            ResultVo<String> supplierResult = productServiceFeignApi.getSupplierNoByModelNo(detail.getModelNo());
            if (!supplierResult.isSuccess() || StringUtils.isBlank(supplierResult.getData())) {
                log.error("申请型号供应商信息获取失败: {}", supplierResult);
                return;
                // throw new BusinessException("申请型号的供应商信息获取失败: " + detail.getModelNo() + supplierResult.getMessage());
            }
            supplierNo = supplierResult.getData().trim();
            if (supplierMap != null) {
                supplierMap.put(detail.getModelNo(), supplierNo);
            }
        }
        detail.setSupplierCode(supplierNo);
    }

    /**
     * 转换营业所代码
     */
    private String converDeptCode(String deptNo) {
        ResultVo<String> resultVo = opsCommonService.getDeptNoByHRSalesDeptNo(deptNo);
        if (!resultVo.isSuccess()) {
            throw new BusinessException("获取营业所代码失败");
        }
        return resultVo.getData();
    }

    /**
     * 检查采购数量是否满足最小包装倍数，如果不满足做数量调整
     */
    // bug-10503 采购&调拨要符合偶数订货标识
    private void converUnPackPoQty(PreStockApplyDO applyInfo, PreStockDetailDO detail,
                                   List<PreStockResultDTO> transferList, List<PreStockResultDTO> moveList, List<PreStockResultDTO> purchaseList,
                                   ProductRemark productRemarkInfo) {
        if (CollectionUtils.isEmpty(purchaseList) || ((CollectionUtils.isEmpty(transferList)) && (CollectionUtils.isEmpty(moveList)))) {
            return; // 无调整项,跳过处理
        }
        int minPacUnit = Optional.ofNullable(productRemarkInfo.getMinPackUnit()).orElse(1);
        boolean isEven = "1".equals(productRemarkInfo.getIsEven());

        // 统计采购数量
        int poQty = purchaseList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
        int newPoQty = this.calcCorrectPoQty(poQty, minPacUnit, isEven);
        // 计算需要调整的数量
        int diff = newPoQty - poQty;
        if (diff == 0) {
            return; // 当前采购数量符合要求（最小包装倍数、是否偶数订货）
        }
        int transQty = transferList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
        int moveQty = moveList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
        //    <!--add by WuWeiDong 20230529  bug 10925  采购数据满足补库，就不必做调库了 -->
        if (transQty + moveQty < diff) { //调拨数量小于调整数量，无法完补库。
            log.info("{}-{} 无法调整{}个调库/预占数量>>>采购数量,使得采购数量满足最小包装数的倍数", applyInfo.getApplyNo(), detail.getId(), diff);
            return;
        }
        log.info("{}-{} 调整{}个预占数量>>>采购数量,使得采购数量满足最小包装数的倍数", applyInfo.getApplyNo(), detail.getId(), diff);
        // 转换预占数量
        int movediff = this.convertTransferQtyToPurchase(detail, moveList, diff);
        if (movediff > 0) {

            log.info("{}-{} 调整{}个调库数量>>>采购数量,使得采购数量满足最小包装数的倍数", applyInfo.getApplyNo(), detail.getId(), diff);
            // 转换调库数量
            this.convertTransferQtyToPurchase(detail, transferList, movediff);
        }
        // 重新生成采购处理项
        //detail.setPoQty(detail.getPoQty() - poQty);
        detail.setQuantity(detail.getQuantity() + poQty);
        purchaseList.removeIf(item -> item.getOrderQty() > 0);
        purchaseList.addAll(this.createPurchaseProcessResult(applyInfo, detail));
    }

    /**
     * 过滤被拦截的处理项
     *
     * @param applyInfo    申请信息
     * @param detail       申请项
     * @param transferList 调库处理项
     * @param moveList     预占处理项
     * @param purchaseList 采购处理项
     * @param isIntercept  是否拦截（true-自动拦截, false-人工放行）
     * @return 拦截后的处理项
     */
    public List<PreStockResultDTO> filterInterceptedProcessingItems(PreStockApplyDO applyInfo, PreStockDetailDO detail,
                                                                    List<PreStockResultDTO> transferList, List<PreStockResultDTO> moveList,
                                                                    List<PreStockResultDTO> purchaseList,
                                                                    boolean isIntercept) {
        // 统计采购数量&出在库数量
        int poQty = purchaseList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
        int stockQty = transferList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
        int moveQty = moveList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();


        ProductInterceptRuleDTO ruleDTO = new ProductInterceptRuleDTO();
        ruleDTO.setApplyType(1);
        //ruleDTO.setId(detail.getInterceptId()); // 当前申请项拦截id
        ruleDTO.setModelNo(detail.getModelNo()); // 备库型号
        ruleDTO.setQuantity(poQty); // 备库采购数量
        ruleDTO.setSupplierId(detail.getSupplierCode()); // 备库型号供应商
        ruleDTO.setWarehouseCode(applyInfo.getWarehouseCode()); // 备库仓库
        ruleDTO.setCustomerNo(StringUtils.isBlank(applyInfo.getUserNo()) ? applyInfo.getCustomerNo() : applyInfo.getUserNo()); // 备库客户代码
        ResultVo<List<ProductInterceptRuleDTO>> checkResult = productServiceFeignApi.checkProductInterceptRule(ruleDTO);
        if (!checkResult.isSuccess()) {
            log.error("特殊产品拦截规则较验失败: {} >>> {}", ruleDTO, checkResult);
            throw new BusinessException("特殊产品拦截规则较验失败: " + ruleDTO.getModelNo() + checkResult.getMessage());
        }

        List<PreStockResultDTO> resultList = new ArrayList<>();
        // 过滤拦截规则
        List<ProductInterceptRuleDTO> ruleList = this.filterInterceptRule(checkResult.getData(), detail.getInterceptId(), isIntercept);

        // 根据拦截类型分组,每次匹配一个拦截规则
        Map<Integer, List<ProductInterceptRuleDTO>> ruleMap = ruleList.stream().collect(Collectors.groupingBy(ProductInterceptRuleDTO::getActionType));
        ProductInterceptRuleDTO rule;

        // 3-自动退回 > 1-拦截转人工 > 4-转等待处理 > 2-自动变更
        // 自动退回处理: 自动取消处理退回
        if (ruleMap.containsKey(3)) {
            rule = ruleMap.get(3).get(0);
            log.info("先行在库异常拦截处理: {}-{} {}", applyInfo.getApplyNo(), detail.getId(), rule.getRemark());
            detail.setInterceptId(rule.getId());
            detail.setProcessText(rule.getRemark());
            detail.setStatus("9"); // 驳回
            detail.setQuantity(detail.getQuantity() + stockQty + moveQty + poQty);
            detail.setStockQty(null);
            detail.setPoQty(null);

            transferList.removeIf(item -> item.getOrderQty() > 0);
            moveList.removeIf(item -> item.getOrderQty() > 0);
            purchaseList.removeIf(item -> item.getOrderQty() > 0);

            // 返回不备库处理项
            PreStockResultDTO result = new PreStockResultDTO();
            result.setModelNo(detail.getModelNo());
            result.setApplyModelNo(detail.getApplyModelNo());
            result.setOrderQty(detail.getQuantity());
            result.setDlvDateJp(detail.getDlvDate());
            result.setRemark(rule.getRemark());
            result.setProcessType("9");
            result.setItemNo(detail.getItemNo());
            result.setFromId(detail.getId());
            result.setApplyId(applyInfo.getId());
            resultList.add(result);
            return resultList;
        }
        // 拦截转人工处理
        if (ruleMap.containsKey(1)) {
            rule = ruleMap.get(1).get(0);
            log.info("先行在库异常拦截处理: {}-{} {}", applyInfo.getApplyNo(), detail.getId(), rule.getRemark());
            detail.setInterceptId(rule.getId());
            detail.setProcessText(rule.getRemark());
            detail.setStatus("7"); // 异常拦截
            if (rule.getMinQty() == null && rule.getMaxQty() == null) {
                detail.setQuantity(detail.getQuantity() + stockQty + moveQty + poQty);
                transferList.removeIf(item -> item.getOrderQty() > 0);
                moveList.removeIf(item -> item.getOrderQty() > 0);
                purchaseList.removeIf(item -> item.getOrderQty() > 0);

                poQty = 0;
                stockQty = 0;
//                if ("不可订货客户拦截，让营业所联络产品课追加后再放行".equals(rule.getRemark())) {
//                if (StringUtils.isNotBlank(rule.getRemark()) && (rule.getRemark().contains("订货") || rule.getRemark().contains("采购"))) {
//                    if (poQty > 0) {
//                        //detail.setPoQty(detail.getPoQty() - poQty);
//                        detail.setQuantity(detail.getQuantity() + poQty);
//                        purchaseList.removeIf(item -> item.getOrderQty() > 0);
//                        poQty = 0;
//                    }
//                } else {
//                    detail.setQuantity(detail.getQuantity() + stockQty + poQty);
//                    //detail.setStockQty(null);
//                    //detail.setPoQty(null);
//                    transferList.removeIf(item -> item.getOrderQty() > 0);
//                    purchaseList.removeIf(item -> item.getOrderQty() > 0);
//                    poQty = 0;
//                    stockQty = 0;
//                }
            } else {
                // 拦截采购-低于最小采购数量
                if (rule.getMinQty() != null && poQty < rule.getMinQty() && (poQty + moveQty + stockQty) >= rule.getMinQty()) {
                    // 最新包装数量、偶数订货标识 bug-10503
                    ResultVo<ProductRemark> productRemarkInfo = productServiceFeignApi.getProductRemarkByModelNo(detail.getModelNo());
                    if (!productRemarkInfo.isSuccess()) {
                        throw new BusinessException("处理失败,无法获取最小包装数: " + detail.getModelNo() + productRemarkInfo.getMessage());
                    }
                    if (productRemarkInfo.getData() == null) {
                        throw new BusinessException("处理失败,无法获取最小包装数: " + detail.getModelNo() + "型号不存在");
                    }

                    int minPacUnit = Optional.ofNullable(productRemarkInfo.getData().getMinPackUnit()).orElse(1);
                    boolean isEven = "1".equals(productRemarkInfo.getData().getIsEven());

                    int newPoQty = this.calcCorrectPoQty(rule.getMinQty(), minPacUnit, isEven);
                    int diff = newPoQty - poQty;
                    // 转换预占数量
                    int moveDiff = this.convertTransferQtyToPurchase(detail, moveList, diff);
                    if (moveDiff > 0) {
                        // 转换调库数量
                        this.convertTransferQtyToPurchase(detail, moveList, moveDiff);
                    }
                    // 重新生成采购处理
                    if (poQty > 0) {
                        //detail.setPoQty(detail.getPoQty() - poQty);
                        detail.setQuantity(detail.getQuantity() + poQty);
                        purchaseList.removeIf(item -> item.getOrderQty() > 0);
                        poQty = 0;
                    }
                    purchaseList.addAll(this.createPurchaseProcessResult(applyInfo, detail));
                    stockQty = transferList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
                    poQty = purchaseList.stream().mapToInt(PreStockResultDTO::getOrderQty).sum();
                }
                // 拦截采购-高于最大采购数量
                if (rule.getMaxQty() != null && poQty > rule.getMaxQty()) {
                    Iterator<PreStockResultDTO> iterator = purchaseList.iterator();
                    PreStockResultDTO result;
                    int diff = poQty - rule.getMaxQty();
                    while (iterator.hasNext() && diff > 0) {
                        result = iterator.next();
                        if (result.getOrderQty() > diff) {
                            result.setOrderQty(result.getOrderQty() - diff);
                            //detail.setPoQty(detail.getPoQty() - diff);
                            poQty -= diff;
                            detail.setQuantity(detail.getQuantity() + diff);
                            diff = 0;
                        } else {
                            diff -= result.getOrderQty();
                            //detail.setPoQty(detail.getPoQty() - result.getOrderQty());
                            poQty -= result.getOrderQty();
                            detail.setQuantity(detail.getQuantity() + result.getOrderQty());
                            result.setOrderQty(0);
                        }

                        if (result.getOrderQty() == 0) {
                            iterator.remove();
                        } else {
                            result.setRemark("采购" + result.getOrderQty());
                        }
                    }
                }
            }
            resultList.addAll(transferList);
            resultList.addAll(moveList);
            resultList.addAll(purchaseList);
            if (resultList.size() > 0 && ruleMap.containsKey(2)) {
                this.handleAutoUpdateInterceptRule(detail, resultList, ruleMap.get(2));
            }
            return Collections.emptyList();
        }
        // 转等待自动处理
        if (ruleMap.containsKey(4)) {
            rule = ruleMap.get(4).get(0);
            log.info("先行在库异常拦截处理: {}-{} {}", applyInfo.getApplyNo(), detail.getId(), rule.getRemark());
            // 拦截采购-等待大库补库到货后调库抽取（每天自动判断能否调库抽取）
            // if ("等待大库补库到货后调库抽取（每天自动判断能否调库抽取）".equals(rule.getRemark())) {
            // 重置申请项处理数量
            detail.setProcessText(rule.getRemark());
            detail.setInterceptId(rule.getId());
            if (poQty > 0) {
                //detail.setPoQty(detail.getPoQty() - poQty);
                detail.setQuantity(detail.getQuantity() + poQty);
                purchaseList.removeIf(item -> item.getOrderQty() > 0);
                poQty = 0;
                //  }
            }
            if (detail.getQuantity() > 0) {
                detail.setStatus("10"); // 暂不处理
            }

            resultList.addAll(transferList);
            resultList.addAll(moveList);
            resultList.addAll(purchaseList);
            if (resultList.size() > 0 && ruleMap.containsKey(2)) {
                this.handleAutoUpdateInterceptRule(detail, resultList, ruleMap.get(2));
            }

            return Collections.emptyList();
        }
        // 自动变更拦截处理
        if (ruleMap.containsKey(2)) {
            log.info("先行在库异常拦截处理: {}-{} {}", applyInfo.getApplyNo(), detail.getId(), ruleMap.get(2));
            resultList.addAll(transferList);
            resultList.addAll(moveList);
            resultList.addAll(purchaseList);
            this.handleAutoUpdateInterceptRule(detail, resultList, ruleMap.get(2));
        }
        return Collections.emptyList();
    }


    /**
     * 自动变更拦截处理
     *
     * @param detail     申请项信息
     * @param resultList 自动变更拦截规则
     * @param ruleList   申请项处理
     */
    private void handleAutoUpdateInterceptRule(PreStockDetailDO
                                                       detail, List<PreStockResultDTO> resultList, List<ProductInterceptRuleDTO> ruleList) {
        for (ProductInterceptRuleDTO rule : ruleList) {
            for (PreStockResultDTO result : resultList) {
                // 变更备库仓库
                if (StringUtils.isNotBlank(rule.getToWarehouseCode())) {
                    result.setToWarehouseCode(rule.getToWarehouseCode());
                }
                if ("1".equals(result.getProcessType()) || "2".equals(result.getProcessType())) {
                    // 变更供应商
                    if (StringUtils.isNotBlank(rule.getToSupplierId())) {
                        result.setOrderStock(rule.getToSupplierId());
                    }
                }
            }
            if ("2".equals(detail.getStatus())) {
                detail.setInterceptId(rule.getId());
                detail.setProcessText(rule.getRemark());
            } else {
                detail.setProcessText(detail.getProcessText() + "; " + rule.getRemark());
            }
        }
    }

    /**
     * @param ruleList    拦截规则列表
     * @param interceptId 当前拦截规则id
     * @param isIntercept 是否放行当前拦截规则
     * @return 过滤后的拦截规则列表
     */
    private List<ProductInterceptRuleDTO> filterInterceptRule(List<ProductInterceptRuleDTO> ruleList, Integer
            interceptId, boolean isIntercept) {
        if (interceptId != null) {
            int index = 0;
            for (int i = 0; i < ruleList.size(); i++) {
                if (ruleList.get(i).getActionType() == 1 || ruleList.get(i).getActionType() == 4) {
                    if (ruleList.get(i).getId().compareTo(interceptId) == 0) { // 匹配当前拦截规则
                        if (!isIntercept) {
                            // 如果手动操作放行，则放行当前的拦截规则
                            index = ++i;
                            break;
                        }
                    }
                }
            }
            if (index > 0 && index <= ruleList.size()) {
                // 截取放行后未做拦截处理的规则
                ruleList = ruleList.subList(index, ruleList.size());
            }
        }
        return ruleList;
    }

    private ResultVo<String> createPreStockApplyNo() {
        return commonServiceFeignApi.generatorBillNo(PRE_STOCK_APPLY_BILLTYPE);
    }

    private void setSubTransferWarehouseConfig(List<String> warehouseConfig) {
        List<String> masterWarehouseCode = dictCommonService.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode()).getData();
        List<String> subtract = masterWarehouseCode.stream().filter(item -> !warehouseConfig.contains(item)).collect(Collectors.toList());
        warehouseConfig.addAll(subtract);
    }

    private PreStockResultDO convertPreStockResultDtoToDo(PreStockResultDTO dto) {
        PreStockResultDO resultDO = new PreStockResultDO();
        resultDO.setApplyId(dto.getApplyId());
        resultDO.setModelNo(dto.getModelNo());
        resultDO.setOrderNo(dto.getOrderNo());
        resultDO.setOrderQty(dto.getOrderQty());
        resultDO.setDlvDateJp(dto.getDlvDateJp());
        resultDO.setOrderStock(dto.getOrderStock());
        resultDO.setFromId(dto.getFromId());
        resultDO.setApplyModelNo(dto.getApplyModelNo());
        resultDO.setOptStatus(dto.getOptStatus());
        resultDO.setRemark(dto.getRemark());
        resultDO.setItemNo(dto.getItemNo());
        resultDO.setTransType(dto.getTransType());
        return resultDO;
    }

    private void setDetailStockType(PreStockApplyDO applyInfo, PreStockDetailDO detail) {
        detail.setStockType(applyInfo.getStockType());
        detail.setPplNo(applyInfo.getPplNo());
        detail.setProjectNo(applyInfo.getProjectNo());
        detail.setGroupCustomerNo(applyInfo.getGroupCustomerNo());
        /*if (StringUtils.isNotBlank(detail.getPplNo())) {
            detail.setStockType(InventoryTypeEnum.GK_PPL.getCode());
        }
        if (StringUtils.isNotBlank(detail.getProjectNo())) {
            detail.setStockType(InventoryTypeEnum.GK_PJ.getCode());
        }
        if (StringUtils.isNotBlank(detail.getGroupCustomerNo())) {
            detail.setStockType(InventoryTypeEnum.ZL_JT.getCode());
        }*/
    }

    private void setDetailInventoryPropertyId(List<PreStockDetailDO> detailInfoList, PreStockApplyDO applyInfo) {
        Map<String, Long> propertyIdMap = new HashMap<>();
        StringBuilder prepertyKeySb = new StringBuilder(35);
        String prepertyKey;
        String inventoryType;
        String customerNo = StringUtils.isBlank(applyInfo.getUserNo()) ? Optional.ofNullable(applyInfo.getCustomerNo()).orElse("") : applyInfo.getUserNo();
        String pplNo;
        String projectNo;
        String groupCustomerNo;
        String separate = ",";

        for (PreStockDetailDO detailInfo : detailInfoList) {
            inventoryType = StringUtils.isBlank(detailInfo.getStockType()) ? applyInfo.getStockType() : detailInfo.getStockType();
            pplNo = StringUtils.isBlank(detailInfo.getPplNo()) ? Optional.ofNullable(applyInfo.getPplNo()).orElse("") : detailInfo.getPplNo();
            projectNo = StringUtils.isBlank(detailInfo.getProjectNo()) ? Optional.ofNullable(applyInfo.getProjectNo()).orElse("") : detailInfo.getProjectNo();
            groupCustomerNo = StringUtils.isBlank(applyInfo.getGroupCustomerNo()) ? "" : applyInfo.getGroupCustomerNo();
            prepertyKeySb.append(inventoryType).append(separate).append(customerNo).append(separate).append(pplNo).append(separate).append(projectNo).append(separate).append(groupCustomerNo);
            prepertyKey = prepertyKeySb.toString();
            if (!propertyIdMap.containsKey(prepertyKey)) {
                propertyIdMap.put(prepertyKey, this.getOpsInventoryPropertyId(inventoryType, customerNo, groupCustomerNo, pplNo, projectNo));
            }
            detailInfo.setInventoryPropertyId(propertyIdMap.get(prepertyKey));
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PreStockDetailDO getDetailInfoByWrapper(Wrapper<PreStockDetailDO> queryWrapper) {
        return preStockDetailMapper.selectOne(queryWrapper);
    }

    private String convertStr(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        // 中文标点转换/全角转半角/清除中文字符
        return ChineseUtil.chinesePunctuationToEnglish(Convert.toDBC(str.replaceAll("[\u4e00-\u9fa5]", "")));
    }


    /**
     * 计算正确的采购数量
     *
     * @param poQty       预计采购数量
     * @param minPackUnit 最新单位数量
     * @param isEven      偶数标识
     * @return 正确采购数量
     */
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

    private ResultVo<Integer> getMinPackageQty(String modelNO) {
        SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        ResultVo<ProductRemark> productRemarkInfo = productServiceFeignApi.getProductRemarkByModelNo(modelNO);
        ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
        if (!productRemarkInfo.isSuccess()) {
            return ResultVo.failure("处理失败,无法获取最小包装数: " + modelNO + productRemarkInfo.getMessage());
        }
        if (Objects.isNull(productRemarkInfo.getData())) {
            return ResultVo.success(null);
        }
        return ResultVo.success(productRemarkInfo.getData().getMinPackUnit());
    }


    /**
     * 仓库属性查出在库数。
     * 代码解析  =>GK-TY:MASTER:OTHER：GK-TY库存类型，MASTER物流中心，SUB,分库；OTHER异地仓库
     *
     * @param applyInfo
     * @param detailDO
     * @param subCode
     * @param masterCode
     * @return
     * @paramc calculationSequence  计算顺序Arrays.asList("GK-PJ:MASTER","GK-PPL:MASTER","GK-TY:MASTER","TY:SUB","ZL-PJ:SUB","ZL-PJ:MASTER","TY:MASTER")
     */
    private List<PreModelStockInfo> setPreModelStockInfo(PreStockApplyDO applyInfo, PreStockDetailDO detailDO, String
            subCode, String masterCode, List<String> calculationSequence) {

        List<PreModelStockInfo> rtnData = new ArrayList<>();

        List<String> warehouseConfig = this.getWarehouseConfig(applyInfo).getData();
        if (CollectionUtils.isEmpty(warehouseConfig)) {
            List<String> list = Arrays.asList(subCode, masterCode).stream().filter(s -> StringUtils.isNotBlank(s))
                    .distinct().collect(Collectors.toList());
            warehouseConfig.addAll(list);
        }
        List<String> masterWarehouses = dictCommonService.getWarehouseCodeByWarehouseType(WarehouseTypeEnum.RDC.getHouseTypeCode()).getData();
        if (CollectionUtils.isEmpty(masterWarehouses) && StringUtils.isNotBlank(masterCode)) {
            if (!subCode.equalsIgnoreCase(masterCode)) {
                masterWarehouses.add(masterCode);
            }
        }
        for (String str : calculationSequence) {
            String[] code = str.split(":");
            Boolean isMasterWarehouse = "MASTER".equalsIgnoreCase(code[1]);
            String warehouseCode = isMasterWarehouse ? masterCode : subCode;
            String inventoryTypeCode = code[0];

            if ("SUB".equalsIgnoreCase(code[1]) && masterCode.equalsIgnoreCase(subCode)) {
                continue;
            }
            Boolean isOtherWarehouse = false;

            if (code.length >= 3) {
                if ("OTHER".equalsIgnoreCase(code[code.length - 1])) {
                    isOtherWarehouse = true;
                }
            }
            ModelWarehouseStockRequest stockRequest = new ModelWarehouseStockRequest();
            stockRequest.setModelNo(detailDO.getModelNo());
            stockRequest.setInventoryTypeCode(inventoryTypeCode);
            stockRequest.setWarehouseCode(warehouseCode);
            if (inventoryTypeCode.contains("GK-")) {
                stockRequest.setCustomerNo(applyInfo.getCustomerNo());
            }
            if (inventoryTypeCode.contains("PJ")) {
                stockRequest.setProjectCode(StringUtils.isNotBlank(detailDO.getProjectNo()) ? detailDO.getProjectNo() : applyInfo.getProjectNo());
            }
            if (inventoryTypeCode.contains("PPL")) {
                stockRequest.setPpl(StringUtils.isNotBlank(detailDO.getPplNo()) ? detailDO.getPplNo() : applyInfo.getPplNo());
            }
            if ("GK-PJ".equalsIgnoreCase(inventoryTypeCode) || "GK-PPL".equalsIgnoreCase(inventoryTypeCode)) {

                List<OpsInventoryPropertyVO> propertyVOS = preStockApplyMapper.getOpsInventoryProperty(inventoryTypeCode, stockRequest.getCustomerNo());
                if (CollectionUtils.isEmpty(propertyVOS)) {
                    break;
                }
                ModelWarehouseStockRequest stockPJPPL = new ModelWarehouseStockRequest();
                stockPJPPL.setModelNo(stockRequest.getModelNo());
                stockPJPPL.setInventoryTypeCode(stockRequest.getInventoryTypeCode());

                stockPJPPL.setCustomerNo(stockRequest.getCustomerNo());

                List<String> keyWord = new ArrayList<>();

                stockPJPPL.setWarehouseCode(stockRequest.getWarehouseCode());
                if ("GK-PJ".equalsIgnoreCase(inventoryTypeCode)) {
                    keyWord = propertyVOS.stream().filter(i -> StringUtils.isNotBlank(i.getProjectCode())).map(OpsInventoryPropertyVO::getProjectCode).distinct().collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(keyWord)) {
                        break;
                    }
                    for (String project : keyWord) {

                        stockPJPPL.setProjectCode(project);

                        if (isOtherWarehouse) {
                            //    <!--add by WuWeiDong 20240327  bug 13800  异地仓库处理（非物流中心通用在库） -->
                            ModelWarehouseStockRequest warehouseStockRequest = new ModelWarehouseStockRequest();
                            warehouseStockRequest.setModelNos(Arrays.asList(stockPJPPL.getModelNo()));
                            warehouseStockRequest.setInventoryTypeCode(stockPJPPL.getInventoryTypeCode());
                            warehouseStockRequest.setCustomerNo(stockPJPPL.getCustomerNo());
                            warehouseStockRequest.setProjectCode(stockPJPPL.getProjectCode());
                            List<PreModelStockInfo> infos = this.setPreModelStockInfoByOtherMaster(warehouseStockRequest, stockRequest.getWarehouseCode(), masterWarehouses);
                            if (CollectionUtils.isNotEmpty(infos)) {
                                rtnData.addAll(infos);
                            }

                        } else {
                            PreModelStockInfo stockInfo = this.setPreModelStockInfo(stockPJPPL);
                            if (Objects.nonNull(stockInfo)) {
                                rtnData.add(stockInfo);
                            }
                        }

                    }
                } else if ("GK-PPL".equalsIgnoreCase(inventoryTypeCode)) {
                    keyWord = propertyVOS.stream().filter(i -> StringUtils.isNotBlank(i.getPpl())).map(OpsInventoryPropertyVO::getPpl).distinct().collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(keyWord)) {
                        break;
                    }
                    for (String ppl : keyWord) {

                        stockPJPPL.setPpl(ppl);

                        if (isOtherWarehouse) {
                            //    <!--add by WuWeiDong 20240327  bug 13800  异地仓库处理（非物流中心通用在库） -->
                            ModelWarehouseStockRequest warehouseStockRequest = new ModelWarehouseStockRequest();
                            warehouseStockRequest.setModelNos(Arrays.asList(stockPJPPL.getModelNo()));
                            warehouseStockRequest.setInventoryTypeCode(stockPJPPL.getInventoryTypeCode());
                            warehouseStockRequest.setCustomerNo(stockPJPPL.getCustomerNo());
                            warehouseStockRequest.setPpl(stockPJPPL.getPpl());
                            List<PreModelStockInfo> infos = this.setPreModelStockInfoByOtherMaster(warehouseStockRequest, stockRequest.getWarehouseCode(), masterWarehouses);
                            if (CollectionUtils.isNotEmpty(infos)) {
                                rtnData.addAll(infos);
                            }

                        } else {
                            PreModelStockInfo stockInfo = this.setPreModelStockInfo(stockPJPPL);
                            if (Objects.nonNull(stockInfo)) {
                                rtnData.add(stockInfo);
                            }
                        }
                    }

                }
            } else {

                if (isOtherWarehouse) {
                    //    <!--add by WuWeiDong 20240327  bug 13800  异地仓库处理（非物流中心通用在库） -->

                    ModelWarehouseStockRequest warehouseStockRequest = new ModelWarehouseStockRequest();
                    warehouseStockRequest.setModelNos(Arrays.asList(stockRequest.getModelNo()));
                    warehouseStockRequest.setInventoryTypeCode(stockRequest.getInventoryTypeCode());
                    warehouseStockRequest.setCustomerNo(stockRequest.getCustomerNo());
                    warehouseStockRequest.setProjectCode(stockRequest.getProjectCode());
                    warehouseStockRequest.setPpl(stockRequest.getPpl());
                    List<PreModelStockInfo> infos = this.setPreModelStockInfoByOtherMaster(warehouseStockRequest, stockRequest.getWarehouseCode(), masterWarehouses);
                    if (CollectionUtils.isNotEmpty(infos)) {
                        rtnData.addAll(infos);
                    }
                } else {
                    PreModelStockInfo stockInfo = this.setPreModelStockInfo(stockRequest);
                    if (Objects.nonNull(stockInfo)) {
                        rtnData.add(stockInfo);
                    }
                }
            }

        }
        return rtnData;
    }

    /**
     * 异地物流中心处理
     *
     * @param stockRequest
     * @param warehouseCode    本地物流中心
     * @param masterWarehouses 物流中心集合
     * @return
     */
    private List<PreModelStockInfo> setPreModelStockInfoByOtherMaster(ModelWarehouseStockRequest stockRequest, String warehouseCode, List<String> masterWarehouses) {

        List<PreModelStockInfo> rtnData = new ArrayList<>();
        ResultVo<List<SpecStockVO>> resultVo = inventoryServiceFeignApi.listCustomerSpecStock(stockRequest);
        if (!resultVo.isSuccess() || CollectionUtils.isEmpty(resultVo.getData())) {
            return rtnData;
        }
        List<SpecStockVO> stockVOList = resultVo.getData();

        //去掉当前仓库
        List<String> warehouseList = stockVOList.stream().filter(i -> !i.getWarehouseCode().equalsIgnoreCase(warehouseCode))
                .map(SpecStockVO::getWarehouseCode).distinct().collect(Collectors.toList());
        String modelNo = stockRequest.getModelNos().get(0);
        stockRequest.setModelNos(null);
        stockRequest.setModelNo(modelNo);

        log.info("异地物流中心处理");
        List<String> newWarehouseList = warehouseList.stream().filter(s -> masterWarehouses.contains(s)).collect(Collectors.toList());
        for (String otherCode : newWarehouseList) {
            stockRequest.setWarehouseCode(otherCode);
            PreModelStockInfo stockInfo = this.setPreModelStockInfo(stockRequest);
            if (Objects.nonNull(stockInfo)) {
                rtnData.add(stockInfo);
            }
        }
        return rtnData;

    }

    /**
     * 查询仓库清单，查出通用在库数
     *
     * @param modelNo
     * @param warehouseCodes
     * @return
     */
    private List<PreModelStockInfo> setPreModelStockInfo(String modelNo, List<String> warehouseCodes) {
        List<PreModelStockInfo> rtnData = new ArrayList<>();

        for (String code : warehouseCodes) {
            ModelWarehouseStockRequest stockRequest = new ModelWarehouseStockRequest();
            stockRequest.setModelNo(modelNo);
            stockRequest.setInventoryTypeCode(InventoryTypeEnum.TY.getCode());
            stockRequest.setWarehouseCode(code);
            PreModelStockInfo stockInfo = this.setPreModelStockInfo(stockRequest);
            if (Objects.nonNull(stockInfo)) {
                rtnData.add(stockInfo);
            }
        }
        return rtnData;
    }

    private PreModelStockInfo setPreModelStockInfo(ModelWarehouseStockRequest stockRequest) {

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            Future<ResultVo<Integer>> stockFuture = executorService.submit(new Callable<ResultVo<Integer>>() {
                @Override
                public ResultVo<Integer> call() throws Exception {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    ResultVo<Integer> resultVo = inventoryServiceFeignApi.getModelWarehouseStock(stockRequest);
                    ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    if (resultVo.isSuccess()) {
                        return resultVo;
                    } else {
                        log.info("调用取在库数失败: " + resultVo.getMessage());
                        return ResultVo.success(0);
                    }

                }
            });

            BindataRequest bindataRequest = new BindataRequest();
            bindataRequest.setModelNos(Arrays.asList(stockRequest.getModelNo()));
            bindataRequest.setWarehouseCode(stockRequest.getWarehouseCode());
            bindataRequest.setCustomerNo(stockRequest.getCustomerNo());
            Future<ResultVo<BindataVO[]>> binDataFuture = executorService.submit(new Callable<ResultVo<BindataVO[]>>() {
                @Override
                public ResultVo<BindataVO[]> call() throws Exception {
                    SMCApp.setGrantType(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    ResultVo<BindataVO[]> resultBinDataVo = binServiceFeignApi.listModelBinData(bindataRequest);
                    ThreadLocalMapUtil.remove(GlobalConstant.GrantType.CLIANT_CREDENTIALS);
                    if (resultBinDataVo.isSuccess()) {
                        return resultBinDataVo;
                    } else {
                        log.info("调用取Bin数据失败: " + resultBinDataVo.getMessage());
                        return ResultVo.success(null);
                    }

                }
            });
            executorService.shutdown();
            while (true) {
                if (executorService.isTerminated()) {
                    break;
                }
                Thread.sleep(100);
            }

            Integer avaQty = Optional.ofNullable(stockFuture.get().getData()).orElse(0);
            if (avaQty.compareTo(0) <= 0) {
                return null;
            }
            PreModelStockInfo rtnData = BeanCopyUtil.copy(stockRequest, PreModelStockInfo.class);

            if (stockRequest.getInventoryTypeCode().equalsIgnoreCase(InventoryTypeEnum.TY.getCode())) {
                rtnData.setAvaQty_ty(avaQty);
            } else {
                rtnData.setAvaQty_zy(avaQty);
            }
            rtnData.setAvaQty(avaQty);

            ResultVo<BindataVO[]> BinData = binDataFuture.get();
            if (Objects.nonNull(BinData.getData()) && BinData.getData().length > 0) {
                BindataVO bindataVO = BinData.getData()[0];
                rtnData.setStockType(bindataVO.getStockType());
                rtnData.setQtyBin(Optional.ofNullable(bindataVO.getQtyBin()).orElse(0));
                rtnData.setBinCell(Optional.ofNullable(bindataVO.getBinCell()).orElse(0));
                rtnData.setFreq(Optional.ofNullable(bindataVO.getFreq()).orElse(0));
                rtnData.setMean(Optional.ofNullable(bindataVO.getMean()).orElse(0));
                rtnData.setSafeQty(Optional.ofNullable(bindataVO.getSafeQty()).orElse(0));

            }
            rtnData.setMonths(rtnData.getMean() == 0 ? 0 : rtnData.getAvaQty() / rtnData.getMean());
            //通在库才计算剩余数量
            if (stockRequest.getInventoryTypeCode().equalsIgnoreCase(InventoryTypeEnum.TY.getCode())) {
                // 计算剩余数量
                int excessQty = rtnData.getAvaQty() - (rtnData.getQtyBin() * (1 + rtnData.getBinCell()));
                rtnData.setExcessQty(excessQty < 0 ? 0 : excessQty);
                // BIN类型: 可用库存 = 通用在库有效库存-安全库存(2倍月用量)
                if (rtnData.getStockType() != null && rtnData.getStockType() == 1) {
                    int canUseQty = rtnData.getAvaQty() - rtnData.getSafeQty();
                    rtnData.setAvaQty(canUseQty);
                }
            } else {
                rtnData.setExcessQty(rtnData.getAvaQty());
            }
            if (rtnData.getAvaQty() > 0) {
                return rtnData;
            }
            return null;
        } catch (Exception ex) {
            log.error("setPreModelStockInfor 执行错误: " + ex);
            throw new BusinessException("setPreModelStockInfor 执行错误: " + ex.getMessage());
        }
    }

    /**
     * 有客户的，按照客户所属的第一物流中心；无客户的，按照所选备库仓的第一物流中心。
     *
     * @param applyInfo
     * @return
     */
    private String getWarehouseMasterCode(PreStockApplyDO applyInfo) {
        String masterCode = applyInfo.getWarehouseCode();
        if (StringUtils.isNotBlank(applyInfo.getCustomerNo())) {
            List<String> warehouseConfig = new ArrayList<>();
            CustomerVO customerInfo = opsCommonService.getCustomerByCustomerNo(applyInfo.getCustomerNo());
            if (customerInfo == null) {
                log.error("出库规则获取失败-获取客户信息失败: {}", applyInfo.getCustomerNo());
                throw new BusinessException("出库规则获取失败-获取客户信息失败");

            }
            warehouseConfig.addAll(opsWarehouseService.getOpsWarehouseSalesBranchConfig(customerInfo.getHRUnitId()));
            if (CollectionUtils.isEmpty(warehouseConfig)) {
                throw new BusinessException("出库规则获取失败-请检查客户营业所是否已设置出库规则");
            }
            ResultVo<List<WarehouseVO>> resultVo = commonServiceFeignApi.getWarehouseByType(WarehouseTypeEnum.RDC.getHouseTypeCode());
            if (resultVo.isSuccess() && CollectionUtils.isNotEmpty(resultVo.getData())) {
                List<String> masterCodes = resultVo.getData().stream().map(WarehouseVO::getWarehouseCode).distinct().collect(Collectors.toList());
                warehouseConfig.retainAll(masterCodes);
                if (CollectionUtils.isNotEmpty(warehouseConfig)) {
                    masterCode = warehouseConfig.get(0);
                }
            }

        } else {
            masterCode = commonServiceFeignApi.getWarehouseParentCode(masterCode).getData();
        }
        return masterCode;
    }
}
