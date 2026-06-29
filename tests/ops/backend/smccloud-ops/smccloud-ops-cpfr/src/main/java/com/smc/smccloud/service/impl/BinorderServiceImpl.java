package com.smc.smccloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.exception.BusinessException;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.redis.redisson.RedissonUtil;
import com.smc.smccloud.core.utils.*;
import com.smc.smccloud.mapper.BinOrderCalcMapper;
import com.smc.smccloud.mapper.BinOrderDetailOrdingInfoMapper;
import com.smc.smccloud.mapper.BinOrderDetailPreInfoMapper;
import com.smc.smccloud.mapper.BindataMapper;
import com.smc.smccloud.mapper.binorder.*;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.bindata.BindataDO;
import com.smc.smccloud.model.bindata.BindataExcelVO;
import com.smc.smccloud.model.bindata.BindataRequest;
import com.smc.smccloud.model.bindata.BindataVO;
import com.smc.smccloud.model.binorder.*;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author edp02 @Date 2021/10/25 10:54
 */
@Service
@Slf4j
public class BinorderServiceImpl implements BinorderService {

    @Resource
    private ModelExpFreqMapper modelExpFreqMapper;
    @Resource
    private ModelExpDetailMapper modelExpDetailMapper;
    @Resource
    private BinOrderCalcMapper binOrderCalcMapper;
    @Resource
    private BindataMapper bindataMapper;
    @Resource
    private BinOrderDetailSplitMapper binOrderDetailSplitMapper;
    @Resource
    private BinOrderAppMapper binOrderAppMapper;
    @Resource
    private BinOrderDetailMapper binOrderDetailMapper;

    @Resource
    private BinOrderCalcService binOrderCalcService;
    @Resource
    private BindataService bindataService;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CommonServiceFeignApi commonServiceFeignApi;
    @Resource
    private RedisManager redisUtil;
    @Resource
    private AuthServiceApi authServiceApi;
    @Resource
    private RedissonUtil redissonUtil;
    @Resource
    private BinorderCreateService binorderCreateService;
    @Resource
    private PrepareOrderViewMapper prepareOrderViewMapper;

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private OpsCommonService opsCommonService;
    @Resource
    private NewBinOrderCalcServiceImpl newBinOrderCalcService;
    @Resource
    private BinOrderDetailOrdingInfoMapper binOrderDetailOrdingInfoMapper;
    @Resource
    private BinOrderDetailPreInfoMapper binOrderDetailPreInfoMapper;

    /**
     * 获取销售数据
     */
    @Override
    public PageInfo<ModelExpFreqVO> listModelExpFreq(ModelExpFreqRequest request) {
        QueryWrapper<ModelExpFreqDO> query = new QueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(request.getStockcode()), "Stockcode", request.getStockcode())
                .eq(PublicUtil.isNotEmpty(request.getStockcode()), "ModelType", request.getModelTYpe())
                .eq(PublicUtil.isNotEmpty(request.getStockType()), "StockType", request.getStockType());
//                .like(PublicUtil.isNotEmpty(request.getModelNo()), "ModelNo", request.getModelNo());
        if (StringUtils.isNotBlank(request.getModelNo())) {
            if (request.getModelNo().contains("%")) {
                query.apply(" ModelNo like '" + request.getModelNo() + "'");
            } else {
                query.eq("ModelNo", request.getModelNo());
            }
        }
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ModelExpFreqDO> list = modelExpFreqMapper.selectList(query);
        PageInfo<ModelExpFreqDO> pageInfo = PageInfo.of(list);
        return BeanCopyUtil.pageDto2Vo(pageInfo, ModelExpFreqVO.class);
    }

    @Override
    public List<ModelExpDetailVO> listModeldetail(ModelExpFreqRequest request) {
        Date monthdate = new Date();
        if (DateUtil.getDay(monthdate) >= 28) {
            request.setMonthdate(DateUtil.getMonthFirstDate(DateUtil.addMonth(monthdate, -36)));
        } else {
            request.setMonthdate(DateUtil.getMonthFirstDate(DateUtil.addMonth(monthdate, -37)));
        }

//        QueryWrapper<ModelExpDetailDO> query = new QueryWrapper<>();
//        query.eq(PublicUtil.isNotEmpty(request.getStockcode()),"model_no",request.getModelNo())
//                .eq(PublicUtil.isNotEmpty(request.getModelNo()),"warehouse_code",request.getStockcode())
//        .ge("month_date",monthdate);
        List<ModelExpDetailDO> list = modelExpDetailMapper.listModeldetail(request);
        return BeanCopyUtil.copyList(list, ModelExpDetailVO.class);
    }

    @Override
    public List<ModelExpDetailVO> listModeldetailByJob(ModelExpFreqRequest request) {
        Date monthdate = new Date();
        if (DateUtil.getDay(monthdate) >= 28) {
            request.setMonthdate(DateUtil.getMonthFirstDate(DateUtil.addMonth(monthdate, -36)));
        } else {
            request.setMonthdate(DateUtil.getMonthFirstDate(DateUtil.addMonth(monthdate, -37)));
        }
        List<ModelExpDetailDO> list = modelExpDetailMapper.listModeldetailByJob(request);
        return BeanCopyUtil.copyList(list, ModelExpDetailVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<BinOrderCalcVO> newBinOrderCalcId(BinOrderCalcRequestDTO dto) {
        String lockKey = Constants.REDIS_KEY_BIN_NEW_CALCID_LOCK;
        try {
            if (!redissonUtil.tryLock(lockKey, 20, 60, TimeUnit.SECONDS)) {
                return ResultVo.failure("不要重复点击");
            }
            //1.没有结束的不能生成新的计算号
            LambdaQueryWrapper<BinOrderCalcDO> query = new LambdaQueryWrapper<>();
            query.eq(BinOrderCalcDO::getCalcType, dto.getCalcType())
                    .eq(BinOrderCalcDO::getWarehouseCode, dto.getWarehouseCode())
                    .eq(BinOrderCalcDO::getStatus, 1);
            if (binOrderCalcMapper.selectCount(query) > 0) {
                return ResultVo.failure("还存在该类型库房没有结束申请的计算单号！");
            }
            dto.setOptUser(SMCApp.getLoginAuthDto().getUserNo());
            BinOrderCalcDO binOrderCalcDO = BeanCopyUtil.copy(dto, BinOrderCalcDO.class);
            binOrderCalcDO.setStatus(1);
            //2.仅查看报表的，查询上次用的计算号
            if (dto.getOnlyCaculate()) {
                LambdaQueryWrapper<BinOrderCalcDO> onlyquery = new LambdaQueryWrapper<>();
                onlyquery.eq(BinOrderCalcDO::getCalcType, dto.getCalcType())
                        .eq(BinOrderCalcDO::getWarehouseCode, dto.getWarehouseCode())
                        .eq(BinOrderCalcDO::getStatus, 4);
                BinOrderCalcDO calcDO = binOrderCalcMapper.selectOne(onlyquery);
                if (calcDO != null) {
                    dto.setCalcId(calcDO.getId());
                    calcDO.setStatus(4);
                    calcDO.setCalcPsn(dto.getOptUser());
                    binOrderCalcMapper.updateById(calcDO);
                    BinOrderCalcVO binOrderCalcVO = BeanCopyUtil.copy(calcDO, BinOrderCalcVO.class);
                    return ResultVo.success(binOrderCalcVO);
                }
                binOrderCalcDO.setStatus(4);
            }
            //3新增计算号数据
            binOrderCalcDO.setCalcPsn(SMCApp.getLoginAuthDto().getUserNo());
            binOrderCalcDO.setCalcType(dto.getCalcType());
            binOrderCalcDO.setWarehouseCode(dto.getWarehouseCode());
            int addcount = binOrderCalcMapper.insert(binOrderCalcDO);
            if (addcount > 0) {
                BinOrderCalcVO binOrderCalcVO = BeanCopyUtil.copy(binOrderCalcDO, BinOrderCalcVO.class);
                return ResultVo.success(binOrderCalcVO);
            } else {
                return ResultVo.failure("新增失败！");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        } finally {
            redissonUtil.unlock(lockKey);
        }
    }

    /**
     * 计算BIN订货数量
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public ResultVo<BinOrderCalcVO> calcBinOrder(BinOrderCalcRequestDTO dto) {
        if (PublicUtil.isEmpty(dto.getCalcId()) || dto.getCalcId() == 0) {
            return ResultVo.failure("计算号不能为0或空");
        }
        //限制同时计算
        if (!redissonUtil.tryLock(Constants.REDIS_KEY_BIN_CALC_LOCK, 1, 60, TimeUnit.SECONDS)) {
            return ResultVo.failure("计算中请稍后");
        }
        try {
            BinOrderCalcDO calcDO = binOrderCalcMapper.selectById(dto.getCalcId());
            if (calcDO == null) {
                return ResultVo.failure("该计算号不存在" + dto.getCalcId());
            }
            //已结束
            if (calcDO.getStatus() == 6) {
                return ResultVo.failure("该计算号已结束申请，不能再计算！" + dto.getCalcId());
            }
            // 限制bin类别和仓库不一致问题 bug8745
            if (dto.getCalcType().compareTo(calcDO.getCalcType()) != 0) {
                return ResultVo.failure("计算时Bin类别不能选择与new生成时的不一致，请重新选择Bin类别" + dto.getCalcId());
            }
            if (!dto.getWarehouseCode().equalsIgnoreCase(calcDO.getWarehouseCode())) {
                return ResultVo.failure("计算时仓库不能选择与new生成时的不一致，请重新选择物流中心或仓库" + dto.getCalcId());
            } //end bug8745
            LambdaQueryWrapper<BinOrderDetailDO> detailQuery = new LambdaQueryWrapper<>();
            detailQuery.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                    .ge(BinOrderDetailDO::getStatus, 2);
            if (binOrderDetailMapper.selectCount(detailQuery) > 0) {
                return ResultVo.failure("该计算号已经存在申请订货，不能再计算！");
            }
            //0未计算，1:开始计算中，2已计算完成
            //查询当前redis状态，如果是1计算中或2计算完成，则退出
            RedisMessageVO messageVo = binOrderCalcService.getCalcState(dto.getCalcId());
            if (messageVo.getStatus() == 1 || messageVo.getStatus() == 2) {
                return ResultVo.failure(messageVo.getContent());
            }
            dto.setOptUser(SMCApp.getLoginAuthDto().getUserNo());
            //如果不是计算中或计算完成
            RedisMessageVO messageVO = new RedisMessageVO();
            messageVO.setNo(dto.getCalcId().toString());
            messageVO.setStatus(1);//开始计算中
            messageVO.setContent("开始计算中");
            messageVO.setUserNo(dto.getOptUser());
            binOrderCalcService.saveCalcState(messageVO);


            //异步计算补货数,替换新计算程序
            //binOrderCalcService.asycalcBinOrder(dto);
            newBinOrderCalcService.calcBinOrder(dto);

            RedisMessageVO stateMsg = binOrderCalcService.getCalcState(dto.getCalcId());
            if (stateMsg.getStatus() == 2) {
                BinOrderCalcVO binOrderCalcVO = BeanCopyUtil.copy(calcDO, BinOrderCalcVO.class);
                return ResultVo.success(binOrderCalcVO);
            } else {
                return ResultVo.failure(stateMsg.getContent());
            }
        } finally {
            redissonUtil.unlock(Constants.REDIS_KEY_BIN_CALC_LOCK);
        }
    }


    @Override
    public ResultVo<BinOrderCalcVO> calcBinOrderSync(BinOrderCalcRequestDTO dto) {
        try {
            if (PublicUtil.isEmpty(dto.getCalcId()) || dto.getCalcId() == 0) {
                return ResultVo.failure("计算号不能为0或空");
            }
            //限制同时计算
            if (!redissonUtil.tryLock(Constants.REDIS_KEY_BIN_CALC_LOCK, 1, 60, TimeUnit.SECONDS)) {
                return ResultVo.failure("计算中请稍后");
            }
            BinOrderCalcDO calcDO = binOrderCalcMapper.selectById(dto.getCalcId());
            if (calcDO == null) {
                return ResultVo.failure("该计算号不存在" + dto.getCalcId());
            }
            //已结束
            if (calcDO.getStatus() == 6) {
                return ResultVo.failure("该计算号已结束申请，不能再计算！" + dto.getCalcId());
            }
            // 限制bin类别和仓库不一致问题 bug8745
            if (dto.getCalcType().compareTo(calcDO.getCalcType()) != 0) {
                return ResultVo.failure("计算时Bin类别不能选择与new生成时的不一致，请重新选择Bin类别" + dto.getCalcId());
            }
            if (!dto.getWarehouseCode().equalsIgnoreCase(calcDO.getWarehouseCode())) {
                return ResultVo.failure("计算时仓库不能选择与new生成时的不一致，请重新选择物流中心或仓库" + dto.getCalcId());
            } //end bug8745
            LambdaQueryWrapper<BinOrderDetailDO> detailQuery = new LambdaQueryWrapper<>();
            detailQuery.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                    .ge(BinOrderDetailDO::getStatus, 2);
            if (binOrderDetailMapper.selectCount(detailQuery) > 0) {
                return ResultVo.failure("该计算号已经存在申请订货，不能再计算！");
            }
            //0未计算，1:开始计算中，2已计算完成
            //查询当前redis状态，如果是1计算中或2计算完成，则退出
            RedisMessageVO messageVo = binOrderCalcService.getCalcState(dto.getCalcId());
            if (messageVo.getStatus() == 1 || messageVo.getStatus() == 2) {
                return ResultVo.failure(messageVo.getContent());
            }
            dto.setOptUser(SMCApp.getLoginAuthDto().getUserNo());
            //如果不是计算中或计算完成
            RedisMessageVO messageVO = new RedisMessageVO();
            messageVO.setNo(dto.getCalcId().toString());
            messageVO.setStatus(1);//开始计算中
            messageVO.setContent("开始计算中");
            messageVO.setUserNo(dto.getOptUser());
            binOrderCalcService.saveCalcState(messageVO);
            ResultVo<String> resultVO = newBinOrderCalcService.calcBinOrderSync(dto);
            if (!resultVO.isSuccess()) {
                return ResultVo.failure(resultVO.getMessage());
            }
            BinOrderCalcVO binOrderCalcVO = BeanCopyUtil.copy(calcDO, BinOrderCalcVO.class);
            return ResultVo.success(binOrderCalcVO);
        } finally {
            redissonUtil.unlock(Constants.REDIS_KEY_BIN_CALC_LOCK);
        }
    }

    @Override
    public ResultVo<PageInfo<BinOrderAppVO>> listBinOrderApp(BinOrderAppRequestDTO dto) {
        LambdaQueryWrapper<BinOrderAppDO> query = new LambdaQueryWrapper<>();
        if (dto.getTodate() != null) {
            dto.setTodate(DateUtil.addDay(dto.getTodate(), 1));
        }

        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        if (PublicUtil.isNotEmpty(dto.getAppId()) && dto.getAppId() > 0) {
            query.eq(BinOrderAppDO::getAppId, dto.getAppId());
        } else if (PublicUtil.isNotEmpty(dto.getCalcId()) && dto.getCalcId() > 0) {
            query.eq(BinOrderAppDO::getCalcId, dto.getCalcId());
        } else {
            query.eq(PublicUtil.isNotEmpty(dto.getApplyType()) && dto.getApplyType() > 0, BinOrderAppDO::getStockType, dto.getApplyType())
                    .eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), BinOrderAppDO::getWarehouseCode, dto.getWarehouseCode())
                    .ge(dto.getFromdate() != null, BinOrderAppDO::getApplyTime, dto.getFromdate())
                    .lt(dto.getTodate() != null, BinOrderAppDO::getApplyTime, dto.getTodate())
                    .eq(PublicUtil.isNotEmpty(dto.getStatus()), BinOrderAppDO::getStatus, dto.getStatus());
        }

//                .eq(PublicUtil.isNotEmpty(dto.getCustomerNo()), BinOrderAppDO::getCustomerNo, dto.getCustomerNo())
//                .eq(PublicUtil.isNotEmpty(dto.getPropertyID()) && dto.getPropertyID() > 0, BinOrderAppDO::getPropertyId, dto.getPropertyID());
        List<BinOrderAppDO> list = binOrderAppMapper.selectList(query);
        PageInfo<BinOrderAppDO> pageInfo = PageInfo.of(list);
        PageInfo<BinOrderAppVO> page = BeanCopyUtil.pageDto2Vo(pageInfo, BinOrderAppVO.class);
        return ResultVo.success(page);
    }

    /**
     * BIN审批
     */
    @Override
    public ResultVo<String> approveBinOrder(BinOrderApproveRequestDTO dto) {
        log.info(dto.toString());
        LambdaUpdateWrapper<BinOrderAppDO> updateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<BinOrderDetailDO> updateDetailWrapper = new LambdaUpdateWrapper<>();
        int count = 0;
        StringBuilder msg = new StringBuilder();
        for (Long appid : dto.getAppIds()) {
            if (!existBinOrderByStatus(appid, 2)) {
                msg.append(appid).append("状态不能审批；");
                continue;
            }

            updateWrapper.clear();
            updateWrapper.set(BinOrderAppDO::getApproveText, dto.getApproveText())
                    .set(BinOrderAppDO::getStatus, dto.getPass() ? 3 : 4)
                    .set(BinOrderAppDO::getApproveUser, SMCApp.getLoginAuthDto().getUserNo())
                    .set(BinOrderAppDO::getApproveTime, DateUtil.getNow());
            updateWrapper.eq(BinOrderAppDO::getAppId, appid);
            binOrderAppMapper.update(null, updateWrapper);  //更新审批状态

            updateDetailWrapper.clear();
            updateDetailWrapper.set(BinOrderDetailDO::getStatus, dto.getPass() ? 3 : 4)
                    .eq(BinOrderDetailDO::getAppId, appid);      //更新计算明细状态
            binOrderDetailMapper.update(new BinOrderDetailDO(), updateDetailWrapper);
            count++;
        }
        if (count > 0) {
            return ResultVo.success("共审批成功" + count + "条数据！" + msg);
        } else {
            return ResultVo.failure("审批失败！" + msg);
        }
    }

    /**
     * 是否存在审批数据
     */
    private boolean existBinOrderByStatus(Long appid, int staus) {
        LambdaQueryWrapper<BinOrderAppDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderAppDO::getAppId, appid)
                .eq(BinOrderAppDO::getStatus, staus);
        return binOrderAppMapper.selectCount(query) > 0;
    }

    public void saveCreateOrderState(RedisMessageVO vo) {
        String json = JSON.toJSONString(vo);
        redisUtil.set(vo.getKey(), json);
        redisUtil.expire(vo.getKey(), 60 * 20);
    }

    /**
     * 生成订单
     *
     * @param dto 订单数据
     */
    @Override
    public ResultVo<String> createOrder(BinOrderApproveRequestDTO dto) {
        Long appId = dto.getAppIds()[0];
        BinOrderAppDO appDO = binOrderAppMapper.selectById(appId);
        if (appDO == null) {
            return ResultVo.failure("该申请不存在" + appId);
        }
        if (appDO.getStatus() != 3 && appDO.getStatus() != 7) {
            return ResultVo.failure("当前状态不能生成订单" + appDO.getStatus());
        }
        updateBinOrderAppStatus(appId, 7);
        binorderCreateService.createOrderByAppId(appId);
        return ResultVo.success("后台正在生成订单，请稍后再查询，生成完成会更新状态");
    }

    /**
     * 推送给门户补库
     */
    @Override
    public ResultVo<String> sendToInStock(BinOrderApproveRequestDTO dto) {
        StringBuilder msg = new StringBuilder();
        int count = 0;
        for (Long appid : dto.getAppIds()) {
            BinOrderAppDO appDO = binOrderAppMapper.selectById(appid);
            if (appDO.getStatus() != 3) {
                msg.append(appid).append("状态不对");
                continue;
            }
            if (appDO.getStockType() != 4) {
                msg.append(appid).append("不是客户bin类型，不能推送给门户");
                continue;
            }
            updateBinDataReplQty(appid);  //更改bindata补货数量
            updateBinOrderAppStatus(appid, 5);
            updatebinOrderDetailStatus(appid);
            count++;
        }
        if (count > 0) {
            return ResultVo.success("推送给门户补库" + count + "条数据成功！" + msg);
        }
        return ResultVo.failure("推送给门户补库失败！" + msg);
    }

    /**
     * 更改bindata补货数量
     */
    private void updateBinDataReplQty(Long appid) {
        List<BinOrderDetailVO> items = listBinOrderDetailForCreateOrder(appid);
        LambdaUpdateWrapper<BindataDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (BinOrderDetailVO info : items) {
            if (info.getConfirmQty() > 0) {
                updateWrapper.clear();
                updateWrapper.set(BindataDO::getReplQty, info.getConfirmQty());
                updateWrapper.eq(BindataDO::getStockType, info.getCalcType())
                        .eq(BindataDO::getWarehouseCode, info.getWarehouseCode())
                        .eq(BindataDO::getCustomerNo, info.getCustomerNo())
                        .eq(BindataDO::getModelNo, info.getModelNo())
                        .eq(BindataDO::getPropertyID, info.getPropertyId());
                bindataMapper.update(new BindataDO(), updateWrapper);
            }
        }
    }

    private void updateBinOrderAppStatus(Long appid, Integer status) {
        BinOrderAppDO updDO = new BinOrderAppDO();
        updDO.setStatus(status);
        updDO.setAppId(appid);
        binOrderAppMapper.updateById(updDO);  //更新状态
    }

    private void updatebinOrderDetailStatus(Long appid) {
        LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BinOrderDetailDO::getStatus, 5);
        updateWrapper.eq(BinOrderDetailDO::getAppId, appid)
                .eq(BinOrderDetailDO::getStatus, 3);
        binOrderDetailMapper.update(null, updateWrapper);  //更新状态
    }

    @Override
    public ResultVo<List<BinOrderDetailVO>> listBinOrderDetailSplit(Long fromid) {
        LambdaQueryWrapper<BinOrderDetailSplitDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderDetailSplitDO::getFromId, fromid)
                .ne(BinOrderDetailSplitDO::getDelFlag, 1);
        List<BinOrderDetailSplitDO> list = binOrderDetailSplitMapper.selectList(query);
        List<BinOrderDetailVO> listVO = BeanCopyUtil.copyList(list, BinOrderDetailVO.class);
        return ResultVo.success(listVO);
    }

    private List<BinOrderDetailVO> listBinOrderDetailForCreateOrder(Long appid) {
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderDetailDO::getAppId, appid)
                .eq(BinOrderDetailDO::getStatus, 3);
        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(query);
        return BeanCopyUtil.copyList(list, BinOrderDetailVO.class);
    }

    /**
     * BIN申请
     */
    @Override
    @Transactional
    public ResultVo<String> applyBinOrder(BinOrderApplyRequestDTO dto) {

        Object obj = redisUtil.get("ops:cpfr:binord:" + dto.getCalcId());
        if (obj != null) {
            RedisMessageVO messageVo = JSON.parseObject(obj.toString(), RedisMessageVO.class);
            if (messageVo.getStatus() == 1) {
                return ResultVo.failure("正在计算中：" + messageVo.getContent());
            }
        }

        if (dto.getSelectType() == 1 && (dto.getIds() == null || dto.getIds().size() == 0)) {
            return ResultVo.failure("请选择要申请的型号！");
        }
        /**
         * 1.检查状态
         */
        BinOrderCalcDO calcDo = binOrderCalcMapper.selectById(dto.getCalcId());
        if (calcDo.getStatus() == 4) {
            return ResultVo.failure("仅查看计算结果的不能申请订货！");
        }
        if (PublicUtil.isEmpty(calcDo.getCalcFinishTime())) {
            return ResultVo.failure("还没计算完成不能申请订货！");
        }
        if (calcDo.getStatus() == 6) {
            return ResultVo.failure("已结束申请，不能再申请！");
        }

        //2.先查询所有型号要申请的型号
        List<BinOrderDetailDO> items = listBinOrderDetailForApply(dto);

        if (items == null || items.size() == 0) {
            return ResultVo.failure("无申请项！");
        }

        Boolean haspersion = authServiceApi.hasPermission("/stock/applybindata");
        if (!haspersion) {
            return ResultVo.failure("无申请订货权限！");
        }
        for (BinOrderDetailDO item : items) {
            if (item.getQtybin() != null && item.getQtybin() > 0 && item.getBincell() > 0 &&
                    item.getConfirmQty() > BigDecimalUtil.mul(item.getQtybin(), BigDecimalUtil.add(item.getBincell(), "0.5")).intValue()) {
                return ResultVo.failure("申请数量错误，不能超过BIN倍数+0.5" + item.getModelNo());
            }
            if (item.getOrdingQty() != null && item.getOrdingQty() < 0) {
                return ResultVo.failure("数量错误，订货中数量为负数可能导致，计算补库不准，需修正" + item.getModelNo());
            }

            if (calcDo.getCalcType() == 1 && PublicUtil.isEmpty(item.getSupplierCode())) {
                return ResultVo.failure("请设置供应商" + item.getModelNo());
            }

            /**
             * lyc  bug 16125
             *
             * 1.当order_type=A、B、K（采购单）时，supplier_code字段应该为供应商JP、CM、CN等
             *
             * 2.当order_type=1（调拨单）时，supplier_code字段应该为仓库代码
             *
             * 3.当order_type=9（拆分单）时,supplier_code字段应该为仓库代码
             *
             */
            if ("A".equals(item.getOrderType()) || "B".equals(item.getOrderType()) || "K".equals(item.getOrderType())) {
                boolean b = opsCommonService.existSupplierCode(item.getSupplierCode());
                if (!b) {
                    return ResultVo.failure(item.getModelNo() + "型号供应商与订单类型不匹配");
                }
            }

            if ("1".equals(item.getOrderType())) {
                boolean b = opsCommonService.existWarehouseCode(item.getSupplierCode());
                if (!b) {
                    return ResultVo.failure(item.getModelNo() + "型号供应商与订单类型不匹配");
                }
            }
            if ( "9".equals(item.getOrderType())) {
                boolean b = opsCommonService.existWarehouseCode(item.getSupplierCode());
                if (!b) {
                    b = opsCommonService.existSupplierCode(item.getSupplierCode());
                    if (!b) {
                        return ResultVo.failure(item.getModelNo() + "型号供应商与订单类型不匹配");
                    }
                }
            }
           /*
            if (PublicUtil.isEmpty(item.getDlvDate())) {
               return ResultVo.failure("请设置交货期" + item.getModelNo());
            }*/
        }

//        Long appid=Long.parseLong(binOrderCalcMapper.getMaxCode("BINAPP"))+1;
        String billNo = commonServiceFeignApi.generatorBillNo("10").getData();
        if (PublicUtil.isEmpty(billNo)) {
            return ResultVo.failure("生成申请单号异常");
        }
        Long appid = Long.parseLong(billNo.trim());

        BinOrderAppDO appDO = new BinOrderAppDO();
        appDO.setAppId(appid);
        appDO.setCalcId(dto.getCalcId());
        appDO.setApplyText(dto.getApplyText());
        appDO.setStatus(2);
        appDO.setApplyTime(DateUtil.getNow());
        appDO.setAppUser(SMCApp.getLoginAuthDto().getUserNo());
        BinOrderCalcDO calcDO = binOrderCalcMapper.selectById(dto.getCalcId());
        if (calcDO != null) {
//            appDO.setGss(calcDO.getGss());
            appDO.setStockType(calcDO.getCalcType());
            appDO.setWarehouseCode(calcDO.getWarehouseCode());
//            appDO.setCustomerNo(calcDO.getCustoemrNo());
//            appDO.setPropertyId(calcDO.getPropertyId());
        }

        binOrderAppMapper.insert(appDO);

        LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        for (BinOrderDetailDO item : items) {
            updateWrapper.clear();
            updateWrapper.set(BinOrderDetailDO::getAppId, appid)
                    .set(BinOrderDetailDO::getStatus, 2);

            updateWrapper.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                    .eq(BinOrderDetailDO::getId, item.getId());
//                    .eq(BinOrderDetailDO::getModelNo, item.getModelNo());
            binOrderDetailMapper.update(null, updateWrapper);
        }

        binOrderAppMapper.updateSumApplyQty(appid);

        return ResultVo.success("申请成功，申请号" + appid + ",项数" + items.size());
    }

    private List<BinOrderDetailDO> listBinOrderDetailForApply(BinOrderApplyRequestDTO dto) {
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
//        query.select(BinOrderDetailDO::getId, BinOrderDetailDO::getQtybin, BinOrderDetailDO::getBincell, BinOrderDetailDO::getConfirmQty,
//                BinOrderDetailDO::getOrdingQty, BinOrderDetailDO::getModelNo, BinOrderDetailDO::getSupplierCode, BinOrderDetailDO::getCalcId, BinOrderDetailDO::getCalcType);
        if (dto.getSelectType() == 3) {
            query.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                    .gt(BinOrderDetailDO::getConfirmQty, 0)
                    .like(PublicUtil.isNotEmpty(dto.getQueryDTO().getModelNo()), BinOrderDetailDO::getModelNo, dto.getQueryDTO().getModelNo())
                    .eq(dto.getQueryDTO().getAppId() != null && dto.getQueryDTO().getAppId() > 0, BinOrderDetailDO::getAppId, dto.getQueryDTO().getAppId())
                    .like(PublicUtil.isNotEmpty(dto.getQueryDTO().getOrderNo()), BinOrderDetailDO::getOrderNo, dto.getQueryDTO().getOrderNo())
                    .eq(PublicUtil.isNotEmpty(dto.getQueryDTO().getWarehouseCode()), BinOrderDetailDO::getWarehouseCode, dto.getQueryDTO().getWarehouseCode())
                    .eq(PublicUtil.isNotEmpty(dto.getQueryDTO().getCalcType()), BinOrderDetailDO::getCalcType, dto.getQueryDTO().getCalcType());
            query.and(i -> i.eq(BinOrderDetailDO::getStatus, 1).or().eq(BinOrderDetailDO::getStatus, 4));
            if (PublicUtil.isNotEmpty(dto.getQueryDTO().getMoreContent())) {
                query.apply(dto.getQueryDTO().getMoreContent());
            }
        } else {
            query.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                    .gt(BinOrderDetailDO::getConfirmQty, 0)
                    .and(i -> i.eq(BinOrderDetailDO::getStatus, 1).or().eq(BinOrderDetailDO::getStatus, 4))
                    .in(dto.getIds() != null && dto.getIds().size() > 0, BinOrderDetailDO::getId, dto.getIds());
        }
        return binOrderDetailMapper.selectList(query);
    }

    @Override
    public ResultVo<List<BinOrderCalcVO>> listBinOrderCalc() {
//        LambdaQueryWrapper<BinOrderCalcDO> query = new LambdaQueryWrapper<>();
        List<BinOrderCalcDO> list = binOrderCalcMapper.listBinOrderCalc();
//        list.sort(Comparator.comparing(BinOrderCalcDO::getId).reversed());
        List<BinOrderCalcVO> listVO = BeanCopyUtil.copyList(list, BinOrderCalcVO.class);
        return ResultVo.success(listVO);
    }

    @Override
    public ResultVo<List<BinOrderCalcVO>> listBinOrderCalcByWarehouseCode(String warehouseCode) {
        List<BinOrderCalcDO> list = binOrderCalcMapper.listBinOrderCalcByWarehouseCode(warehouseCode);
        List<BinOrderCalcVO> listVO = BeanCopyUtil.copyList(list, BinOrderCalcVO.class);
        return ResultVo.success(listVO);
    }

    /**
     * 设置交货期
     */
    @Override
    public ResultVo<String> updateBinOrderDlvDate(BinOrderUpdateDlvDateRequestDTO dto) {
//        int upNo = binOrderCalcMapper.GetMaxUpdNo(dto.getCalcId());
        //    <!--edit by WuWeiDong 20230630  bug 10582  校验设置交货期，是当前及以后的日期 -->
        if (dto.getKdlvDate().before(DateUtil.getCurrentDate())) {
            return ResultVo.failure("交货期设置错误，为当天之前日期，请重新设置。");
        }

        LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(BinOrderDetailDO::getDlvDate, dto.getKdlvDate());
        if (PublicUtil.isNotEmpty(dto.getTransType())) {
            updateWrapper.set(BinOrderDetailDO::getTransType, dto.getTransType());
        }
        updateWrapper.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                .isNull(BinOrderDetailDO::getAppId)
                .and(i -> i.eq(BinOrderDetailDO::getStatus, 1).or().eq(BinOrderDetailDO::getStatus, 4))
                .in(dto.getIds() != null && dto.getIds().size() > 0, BinOrderDetailDO::getId, dto.getIds());
//                .in(dto.getModelNos() != null && dto.getModelNos().size() > 0, BinOrderDetailDO::getModelNo, dto.getModelNos());
        binOrderDetailMapper.update(null, updateWrapper);
//        updateDlvDate(0, dto.getKdlvDate(), upNo, dto.getCalcId());
//        updateDlvDate("B",adapter.getBdlvDate(),upNo,adapter.getCalcId());
//        updateDlvDate("U",adapter.getUdlvDate(),upNo,adapter.getCalcId());
//        updateDlvDate("X",adapter.getXdlvDate(),upNo,adapter.getCalcId());
        return ResultVo.success("交货期修改成功！");
    }

    //Add by A78027 2023-10-08 for bug-12214
    @Override
    public ResultVo<String> batchUpdateBinOrder(@RequestBody BinOrderBatchUpdateDTO dto) {
        if (dto.getCalcId() == null) {
            return ResultVo.failure("计算号不能为空");
        }

        Object obj = redisUtil.get("ops:cpfr:binord:" + dto.getCalcId());
        if (obj != null) {
            RedisMessageVO messageVo = JSON.parseObject(obj.toString(), RedisMessageVO.class);
            if (messageVo.getStatus() == 1) {
                return ResultVo.failure("正在计算中：" + messageVo.getContent());
            }
        }

        if (StringUtils.isNotBlank(dto.getOrderType())
                && StringUtils.isNotBlank(dto.getTransType())
                && dto.getPoQty() == null && dto.getTransQty() == null
                && dto.getDlvDate() == null
                && StringUtils.isNotBlank(dto.getSupplierCode())
        ) {
            return ResultVo.failure("请输入要修改的字段");
        }
        if (dto.getDlvDate() != null && dto.getDlvDate().before(DateUtil.getCurrentDate())) {
            return ResultVo.failure("交货期设置错误,不能早于今日，请重新设置。");
        }

        if (StringUtils.isNotBlank(dto.getOrderType()) && !"AKUB".contains(dto.getOrderType())) {
            return ResultVo.failure("只可以在AKBU订单类型之间变更");
        }

        LambdaQueryWrapper<BinOrderDetailDO> queryWrapper = new LambdaQueryWrapper<>();

        //设置查询条件
        queryWrapper.select(BinOrderDetailDO::getId, BinOrderDetailDO::getCalcId,
                        BinOrderDetailDO::getModelNo, BinOrderDetailDO::getStatus,
                        BinOrderDetailDO::getOrderType, BinOrderDetailDO::getTransType, BinOrderDetailDO::getSupplierCode,
                        BinOrderDetailDO::getConfirmQty, BinOrderDetailDO::getPoQty, BinOrderDetailDO::getTransQty)
                .eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                .isNull(BinOrderDetailDO::getAppId)
                .in(BinOrderDetailDO::getStatus, 1)
                .gt(BinOrderDetailDO::getConfirmQty, 0)
                .eq(dto.getStatus() != null, BinOrderDetailDO::getStatus, dto.getStatus())
                .in(dto.getIds() != null && !dto.getIds().isEmpty(), BinOrderDetailDO::getId, dto.getIds());
        if (StringUtils.isNotBlank(dto.getMoreWhere())) {
            queryWrapper.apply(dto.getMoreWhere());
        }

        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            return ResultVo.failure("没有查到要变更的项");
        }

        List<BinOrderDetailDO> updateDetailList = new ArrayList<>();
        List<BinOrderDetailSplitDO> updateSplitList = new ArrayList<>();

        for (BinOrderDetailDO binOrderDetailDO : list) {
            boolean isUpdated = false;
            BinOrderDetailSplitDO splitTransDO = null;
            BinOrderDetailSplitDO splitPoDO = null;

            boolean isUpdatePO = false;
            boolean isUpdateTrans = false;

            if (StringUtils.isNotBlank(dto.getOrderType())
                    && !dto.getOrderType().equalsIgnoreCase(binOrderDetailDO.getOrderType())) {
                //不能将调拨改成其他
                if (StringUtils.equalsIgnoreCase("1", binOrderDetailDO.getOrderType()) && !StringUtils.equalsIgnoreCase("1", dto.getOrderType())) {
                    return ResultVo.failure("订单类型调拨单不能批量改为采购单");
                }
                isUpdated = true;
                isUpdatePO = true;
                if (!"9".equalsIgnoreCase(binOrderDetailDO.getOrderType())) {
                    binOrderDetailDO.setOrderType(dto.getOrderType());
                }
            }
            if (dto.getDlvDate() != null && !dto.getDlvDate().equals(binOrderDetailDO.getDlvDate())) {
                isUpdated = true;
                isUpdatePO = true;
                binOrderDetailDO.setDlvDate(dto.getDlvDate());
            }

            if (dto.getTransQty() != null && dto.getTransQty().compareTo(binOrderDetailDO.getTransQty()) != 0) {
                isUpdated = true;
                binOrderDetailDO.setTransQty(dto.getTransQty());
                binOrderDetailDO.setConfirmQty(binOrderDetailDO.getPoQty() + dto.getTransQty());
                isUpdateTrans = true;
            }

            if (dto.getPoQty() != null && dto.getPoQty().compareTo(binOrderDetailDO.getPoQty()) != 0) {
                isUpdated = true;
                binOrderDetailDO.setPoQty(dto.getPoQty());
                binOrderDetailDO.setConfirmQty(dto.getPoQty() + binOrderDetailDO.getTransQty());
                isUpdatePO = true;
            }
            if (StringUtils.isNotBlank(dto.getTransType()) && !dto.getTransType().equalsIgnoreCase(binOrderDetailDO.getTransType())) {
                isUpdated = true;
                isUpdatePO = true;
                binOrderDetailDO.setTransType(dto.getTransType());
            }
            if (StringUtils.isNotBlank(dto.getSupplierCode())) {
                isUpdated = true;
                isUpdatePO = true;
                binOrderDetailDO.setSupplierCode(dto.getSupplierCode());
            }

            if (isUpdateTrans) {
                splitTransDO = new BinOrderDetailSplitDO();
                splitTransDO.setFromId(binOrderDetailDO.getId());
                splitTransDO.setCalcId(binOrderDetailDO.getCalcId());
                splitTransDO.setConfirmQty(dto.getTransQty());
                splitTransDO.setOrderType("1");
                splitTransDO.setDlvDate(dto.getDlvDate());
                updateSplitList.add(splitTransDO);
            }

            //采购
            if (isUpdatePO) {
                splitPoDO = new BinOrderDetailSplitDO();
                splitPoDO.setFromId(binOrderDetailDO.getId());
                splitPoDO.setCalcId(binOrderDetailDO.getCalcId());
                splitPoDO.setOrderType(dto.getOrderType());
                splitPoDO.setTransType(dto.getTransType());
                splitPoDO.setDlvDate(dto.getDlvDate());
                splitPoDO.setConfirmQty(dto.getPoQty());
                splitPoDO.setSupplierCode(dto.getSupplierCode());
                updateSplitList.add(splitPoDO);
            }


            if (isUpdated) {
                updateDetailList.add(binOrderDetailDO);
            }
        }

        Date now = new Date();
        try {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);//纺
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            Boolean success = transactionTemplate.execute(transactionStatus -> {
                try {
                    for (BinOrderDetailDO binOrderDetailDO : updateDetailList) {
                        binOrderDetailDO.setUpdateTime(now);
                        binOrderDetailMapper.updateById(binOrderDetailDO);
                    }

                    for (BinOrderDetailSplitDO splitDO : updateSplitList) {
                        //订单类型 供应商，采购数量，运输方式 交货期
                        LambdaUpdateWrapper<BinOrderDetailSplitDO> updateSplitWrapper = new LambdaUpdateWrapper<>();
                        updateSplitWrapper.eq(BinOrderDetailSplitDO::getFromId, splitDO.getFromId())
                                .eq(BinOrderDetailSplitDO::getCalcId, splitDO.getCalcId());
                        //订单类型
                        if ("1".equalsIgnoreCase(splitDO.getOrderType())) {
                            updateSplitWrapper.eq(BinOrderDetailSplitDO::getOrderType, splitDO.getOrderType());
                        } else {
                            updateSplitWrapper.ne(BinOrderDetailSplitDO::getOrderType, "1");
                            updateSplitWrapper.set(StringUtils.isNotBlank(dto.getOrderType()),
                                    BinOrderDetailSplitDO::getOrderType, dto.getOrderType());
                        }
                        //供应商
                        if (StringUtils.isNotBlank(splitDO.getSupplierCode())) {
                            updateSplitWrapper.set(StringUtils.isNotBlank(dto.getSupplierCode()),
                                    BinOrderDetailSplitDO::getSupplierCode, splitDO.getSupplierCode());
                        }
                        //todo 逻辑有问题
                        if (splitDO.getConfirmQty() != null) {
                            updateSplitWrapper.set(BinOrderDetailSplitDO::getConfirmQty, splitDO.getConfirmQty());
                        }
                        //运输方式
                        if (StringUtils.isNotBlank(splitDO.getTransType())) {
                            updateSplitWrapper.set(BinOrderDetailSplitDO::getTransType, splitDO.getTransType());
                        }
                        //交货期
                        updateSplitWrapper.set(dto.getDlvDate() != null,
                                BinOrderDetailSplitDO::getDlvDate, dto.getDlvDate());
                        updateSplitWrapper.set(BinOrderDetailSplitDO::getUpdateTime, now);
                        binOrderDetailSplitMapper.update(null, updateSplitWrapper);
                    }
                    return true;
                } catch (Exception ex) {
                    log.error(ex.toString());
                    transactionStatus.setRollbackOnly(); // 手动回滚
                    return false;
                }
            });

            if (success != null && success) {
                return ResultVo.successMsg("修改成功，项数" + updateDetailList.size());
            }
        } catch (NullPointerException ex) {
            log.error(Thread.currentThread().getName() + "->错误NullPointerException：" + ex);
            return ResultVo.failure(ex.getMessage());
        } catch (Exception ex) {
            log.error(Thread.currentThread().getName() + "->错误Exception：" + ex);
            return ResultVo.failure(ex.getMessage());
        }
        return ResultVo.successMsg("修改完成");
    }


    //Add by A78027 2023-10-08 for bug-12214
    @Override
    @Transactional
    public ResultVo<String> clearTransQty(Long calcId) {
        binOrderCalcMapper.cancelTransQty(calcId);
        return ResultVo.successMsg("处理完成，请重新查询");
    }


    /**
     * 修改项的补货数量
     */
    @Override
    public ResultVo<String> updateBinOrderDetail(BinOrderDetailUpdateDTO dto) {
        if (dto.getItems() == null) {
            return ResultVo.failure("请输入修改内容");
        }
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
        query.eq(BinOrderDetailDO::getId, dto.getId())
                .and(i -> i.eq(BinOrderDetailDO::getStatus, 1).or().eq(BinOrderDetailDO::getStatus, 3));
        BinOrderDetailDO detailDO = binOrderDetailMapper.selectOne(query);

//        BinOrderDetailDO detailDO = binOrderDetailMapper.selectById(dto.getId());
        if (detailDO == null) {
            return ResultVo.failure("修改项不存在或状态不对" + dto.getId());
        }
        /**
         * 1）订单类型为【调拨】（1）：验证是仓库代码。
         * 2）订单类型为【XX订单】（A,B,K,U）：验证供应商代码是否正确。
         */
        StringBuilder errMsg = new StringBuilder();
        //1）订单类型为【调拨】（1）：验证是仓库代码。
        List<BinOrderDetailVO> detailVOS = dto.getItems().stream().filter(i -> "1".equalsIgnoreCase(i.getOrderType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(detailVOS)) {
            List<String> warehouseCodes = detailVOS.stream().map(BinOrderDetailVO::getSupplierCode).distinct().collect(Collectors.toList());
            ResultVo<WarehouseVO> resultWarehouse;
            for (String warehouseCode : warehouseCodes) {
                resultWarehouse = commonServiceFeignApi.getWarehouseInfoByCode(warehouseCode);
                if (!resultWarehouse.isSuccess()) {
                    errMsg.append(warehouseCode).append(":").append(resultWarehouse.getMessage()).append(" ");
                }
            }
        }
        // 2）订单类型为【XX订单】（A,B,K,U）：验证供应商代码是否正确.
        detailVOS = dto.getItems().stream().filter(i -> !"1".equalsIgnoreCase(i.getOrderType())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(detailVOS)) {
            List<String> supplierCodes = detailVOS.stream().map(BinOrderDetailVO::getSupplierCode).distinct().collect(Collectors.toList());
            ResultVo<String> resultSupplier;
            for (String supplierCode : supplierCodes) {
                resultSupplier = commonServiceFeignApi.getSupplierName(supplierCode);
                if (!resultSupplier.isSuccess()
                        || (resultSupplier.isSuccess() && StringUtils.isBlank(resultSupplier.getData()))) {
                    errMsg.append(supplierCode).append(":").append("供应商不正确。").append(" ");
                } else if (supplierCode.equalsIgnoreCase(resultSupplier.getData())) {
                    //若没有查到供应商代码，redis key，value都写入查询代码。
                    errMsg.append(supplierCode).append(":").append("供应商不正确。").append(" ");
                }

            }
        }
        if (StringUtils.isNotBlank(errMsg.toString())) {
            return ResultVo.failure(errMsg.toString() + dto.getId());
        }

        if (PublicUtil.isEmpty(detailDO.getQtybin())) {
            detailDO.setQtybin(0);
        }
        if (PublicUtil.isEmpty(detailDO.getBincell())) {
            detailDO.setBincell(0);
        }
        Integer qtybin = detailDO.getQtybin();
        //1.创建split和detail的更新实体
        BinOrderDetailDO updDetailDO = new BinOrderDetailDO();  //主项修改
        // 调拨数量
        // 采购数量
        List<BinOrderDetailSplitDO> splitItems = getSplitItems(dto, updDetailDO, qtybin, 0);
        if (splitItems == null) {
            //不更新字段
            return ResultVo.failure("K订单必须为Bin的倍数");
        }
        //更新主项的订单类型//拆分项改变后主项跟着改变
        if (dto.getItems().size() == 1) {
            //如果只有一条拆分项，则主项的类型为拆分项的类型
            updDetailDO.setOrderType(dto.getItems().get(0).getOrderType());
        } else if (dto.getItems().size() > 1) {
            //如果拆分项条数大于1，则类型为
            updDetailDO.setOrderType("9");
        }
        //如果不是调拨，更新主项为第一个采购单的运输方式，交货期，供应商
        if (!dto.getItems().isEmpty()) {
            List<BinOrderDetailVO> items = dto.getItems();
            for (BinOrderDetailVO item : items) {
                if (!"1".equalsIgnoreCase(item.getOrderType())) {
                    if(StringUtils.isNotBlank(item.getTransType())){
                        updDetailDO.setTransType(item.getTransType());
                    }
                    updDetailDO.setDlvDate(item.getDlvDate());
                    updDetailDO.setSupplierCode(item.getSupplierCode());
                }
            }
        }
        updDetailDO.setId(dto.getId());
        updDetailDO.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
        updDetailDO.setUpdateTime(DateUtil.getNow());
        //2.将已有的拆分取消
        LambdaUpdateWrapper<BinOrderDetailSplitDO> updateSplitDetailWrapper = new LambdaUpdateWrapper<>();
        updateSplitDetailWrapper.eq(BinOrderDetailSplitDO::getFromId, dto.getId());
        updateSplitDetailWrapper.set(BinOrderDetailSplitDO::getDelFlag, 1);
        binOrderDetailSplitMapper.update(new BinOrderDetailSplitDO(), updateSplitDetailWrapper);
        //3.更新明细表
        LambdaUpdateWrapper<BinOrderDetailDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(BinOrderDetailDO::getId, dto.getId());
        binOrderDetailMapper.update(updDetailDO, updateWrapper);
        //4.写入拆分表
        Date now = new Date();
        for (BinOrderDetailSplitDO splitDO : splitItems) {
            splitDO.setStatus(detailDO.getStatus());
            splitDO.setCalcId(detailDO.getCalcId());
            if (splitDO.getId() != null && splitDO.getId() > 0) {
                splitDO.setUpdateUser(SMCApp.getLoginAuthDto().getUserNo());
                splitDO.setUpdateTime(now);
                binOrderDetailSplitMapper.updateById(splitDO);
            } else {
                splitDO.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
                splitDO.setCreateTime(now);
                binOrderDetailSplitMapper.insert(splitDO);
            }
        }
        return ResultVo.successMsg("提交成功");
    }

    private List<BinOrderDetailSplitDO> getSplitItems(BinOrderDetailUpdateDTO dto, BinOrderDetailDO updDetailDO, Integer qtybin, int type) {
        int poQty = 0;
        int transQty = 0;
        List<BinOrderDetailSplitDO> list = new ArrayList<>(dto.getItems().size());
        BinOrderDetailSplitDO splitDO;
        for (BinOrderDetailVO info : dto.getItems()) {
            splitDO = new BinOrderDetailSplitDO();
            splitDO.setFromId(dto.getId());
            splitDO.setModelNo(dto.getModelNo());
            if (type == 0) {  //防止获取到主项的Id
                splitDO.setId(info.getId());
            }
            splitDO.setOrderType(info.getOrderType());
            if (StringUtils.isBlank(splitDO.getOrderType())) {
                splitDO.setOrderType("K");
            }
            splitDO.setConfirmQty(info.getConfirmQty());
            if ("K".equalsIgnoreCase(splitDO.getOrderType()) && splitDO.getConfirmQty() % qtybin > 0) {
                //返回空，然后返回异常信息
                //return ResultVo.successMsg("K订单必须为Bin的倍数");
                return null;
            }
            splitDO.setWarehouseCode(info.getWarehouseCode());
            splitDO.setTransType(info.getTransType());
            splitDO.setSupplierCode(info.getSupplierCode());
            splitDO.setDlvDate(info.getDlvDate());
            if (PublicUtil.isEmpty(info.getConfirmQty()) || info.getConfirmQty() == 0) {
                splitDO.setDelFlag(1);
            } else {
                splitDO.setDelFlag(0);
            }
            if ("1".equalsIgnoreCase(info.getOrderType())) {
                transQty += info.getConfirmQty();
            } else {
                poQty += info.getConfirmQty();
            }
            list.add(splitDO);
        }
        updDetailDO.setTransQty(transQty);
        updDetailDO.setPoQty(poQty);
        updDetailDO.setConfirmQty(transQty + poQty);
        return list;
    }

    /**
     * 批量添加bin订货明细
     *
     * @param list
     * @return
     */
    @Override
    public ResultVo<String> saveBinOrderDetails(List<BinOrderDetailVO> list) {
        if (list == null || list.size() <= 0) {
            return ResultVo.failure("没有录入数据！");
        }
        Long calcId = list.get(0).getCalcId();
        if (PublicUtil.isEmpty(calcId) || calcId == 0) {
            return ResultVo.failure("计算号不能为0或空！");
        }
        BinOrderCalcDO binOrderCalcDO = binOrderCalcMapper.selectById(calcId);

//            //限制不能同时存在两个补货的计算号
//            LambdaQueryWrapper<BinOrderCalcDO> query = new LambdaQueryWrapper<>();
//            query.eq(BinOrderCalcDO::getCalcType, list.get(0).getCalcType())
//                    .eq(BinOrderCalcDO::getWarehouseCode, list.get(0).getWarehouseCode())
//                    .eq(BinOrderCalcDO::getStatus, 1);
//            if (binOrderCalcMapper.selectCount(query) > 0) {
//                return ResultVo.failure("还存在该类型库房没有结束申请的计算单号！");
//            }
////            String billNo = commonServiceFeignApi.generatorBillNo("9").getData();
////            if (PublicUtil.isEmpty(billNo)) {
////                return ResultVo.failure("生成计算单号异常");
////            }
////            calcId = Long.parseLong(billNo.trim());
//            BinOrderCalcDO calcDO=new BinOrderCalcDO();
//            calcDO.setId(calcId);
//            calcDO.setCalcTime(DateUtil.getNow());
//            calcDO.setCalcPsn(SMCApp.getLoginAuthDto().getUserNo());
//            calcDO.setStatus(1);
//            calcDO.setCalcType(Integer.valueOf(list.get(0).getCalcType()));
//            calcDO.setWarehouseCode(list.get(0).getWarehouseCode());
//            binOrderCalcMapper.insert(calcDO);
//        }

        List<BinOrderDetailDO> listDO = BeanCopyUtil.copyList(list, BinOrderDetailDO.class);
        int count = 0;
        StringBuilder msg = new StringBuilder();
//        Long calcId = binOrderDetailMapper.getMaxCalcId();   //取当前计算号
        for (BinOrderDetailDO info : listDO) {
            info.setCreateUser(SMCApp.getLoginAuthDto().getUserNo());
            info.setCreateTime(DateUtil.getNow());
            //去掉不能重复添加的校验，改成允许重复添加型号、数量
            //LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
            //query.eq(BinOrderDetailDO::getCalcId, calcId)
            //        .eq(BinOrderDetailDO::getModelNo, info.getModelNo());
            ////该型号已存在该计算号中的不能重复添加
            //if (binOrderDetailMapper.selectCount(query) > 0) {
            //    msg.append(";计算号").append(calcId).append("已经存在型号").append(info.getModelNo());
            //    continue;
            //}
            // 15753bug 根据型号以及仓库获取直采标识
            LambdaQueryWrapper<BindataDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BindataDO::getWarehouseCode, info.getWarehouseCode())
                    .eq(BindataDO::getModelNo, info.getModelNo())
                    .eq(BindataDO::getDelFlag, 0);
            if (binOrderCalcDO != null && binOrderCalcDO.getCalcType() != null) {
                queryWrapper.eq(BindataDO::getStockType, binOrderCalcDO.getCalcType());
            }
            List<BindataDO> bindataDO = bindataMapper.selectList(queryWrapper);
            if (CollectionUtils.isNotEmpty(bindataDO)) {
                info.setDirectpurchase(bindataDO.get(0).getDirectPurchase());
                info.setCenterFlag(bindataDO.get(0).getCentreFlag());
            }else{
                info.setCenterFlag(0);
            }
            info.setOrderQty(info.getConfirmQty());
            info.setOrderType("K");
            info.setCalcId(calcId);
            int addcount = binOrderDetailMapper.insert(info);
            if (addcount > 0) {
                count++;
            }
        }
        if (count > 0) {
            return ResultVo.success(calcId.toString());
        } else {
            return ResultVo.failure("批量添加失败" + msg);
        }
    }

    @Override
    public ResultVo<String> calcmodelExpFreq(int type) {
//        modelExpFreqMapper.calcmodelExpFreq();
        log.info("calcmodelExpFreq执行：{}。", type);
        //    <!--add by WuWeiDong 20231109  bug 12559  12个小时内不可以重复计算 -->
        String key = Constants.REDIS_KEY_BIN_CALC_LOCK + type;
        Boolean isLock = redissonUtil.tryLock(key, 0, 12 * 60 * 60, TimeUnit.SECONDS);//12个小时内不可以重复计算
        if (!isLock) {
            log.info("正在执行中，或在12小时重复计算。:{}", type);
            // testing
            return ResultVo.success("正在执行中，或在12小时重复计算。");
        }
        try {
            switch (type) {
                case 1:
                    modelExpFreqMapper.calimpRcvOrder();
                    modelExpFreqMapper.calcmodelExpFreq();
                    break;
                case 2:
                    binOrderCalcService.addModelExpFreqCsv();  //异步执行，异步方法里再发返回消息提示
                    break;
                case 90:
                    modelExpFreqMapper.calcModelExpFreqByWhole(1);
                    modelExpFreqMapper.calcModelExpFreqByWhole(2);
                    break;
                case 91:
                    modelExpFreqMapper.calcModelExpFreqByWareHouse(1);
                    modelExpFreqMapper.calcModelExpFreqByWareHouse(2);
                    break;
                case 92:
                    modelExpFreqMapper.calcModelExpFreqBySubWarehouse(1);
                    modelExpFreqMapper.calcModelExpFreqBySubWarehouse(2);
                    break;
                case 93:
                    modelExpFreqMapper.calcModelExpFreqByCustomer(1);
                    modelExpFreqMapper.calcModelExpFreqByCustomer(2);
                    break;
                case 95:
                    modelExpFreqMapper.calcModelExpFreqByCustomGroupCode(1);
                    modelExpFreqMapper.calcModelExpFreqByCustomGroupCode(2);
                    break;
                case 99:
                    modelExpFreqMapper.calcModelExpFreqUpdateModelInfo();
                    break;
                default:
                    modelExpFreqMapper.calimpRcvOrder();
                    modelExpFreqMapper.calcmodelExpFreq();
                    binOrderCalcService.addModelExpFreqCsv();  //异步执行，异步方法里再发返回消息提示
                    break;
            }
            return ResultVo.success();
        } catch (Exception e) {
            log.error("calcmodelExpFreq[{}]错误:{}", type, e);
            return ResultVo.failure(StringUtils.join("执行失败：", e));
        }
    }

    @Override
    public ResultVo<PageInfo<BindataVO>> listCstmBindata(BindataRequest request) {
        QueryWrapper<BindataDO> query = new QueryWrapper<>();
        System.out.println(request);
        query.select("distinct warehouse_code", "CustomerNo", "Property_ID");
        query.eq(request.getStockType() > 0, "stockType", request.getStockType())
                .eq("delFlag", 0)
                .eq(PublicUtil.isNotEmpty(request.getCustomerNo()), "CustomerNo", request.getCustomerNo());
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<BindataDO> list = bindataMapper.selectList(query);
        System.out.println(list);
        PageInfo<BindataDO> pageInfo = PageInfo.of(list);
        PageInfo<BindataVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, BindataVO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public ResultVo<String> importBindata(MultipartFile file) {
        String userNo = SMCApp.getLoginAuthDto().getUserNo();
        String rediskey = "ops:binimp:" + userNo;
        String updkey = "ops:binimp:";

        try {
            // 检查是否有其他导入任务在运行
            String existingValue = (String) redisUtil.get(updkey);
            if (PublicUtil.isNotEmpty(existingValue)) {
                return ResultVo.failure(existingValue);
            }

            // 验证文件格式
            validateFileFormat(file.getOriginalFilename());
            // 设置导入锁
            setImportLock(userNo);
            redisUtil.set(rediskey, "正在开始导入");

            // 使用EasyExcel读取并处理数据
            try (InputStream inputStream = file.getInputStream()) {
                List<BindataExcelVO> allDataList = new ArrayList<>();

                EasyExcel.read(inputStream, BindataExcelVO.class, new AnalysisEventListener<BindataExcelVO>() {
                    @Override
                    public void invoke(BindataExcelVO data, AnalysisContext context) {
                        allDataList.add(data);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        // 所有数据读取完成后的处理
                    }
                }).sheet().doRead();
                // 一次性处理所有数据
                bindataService.asyupdAndAddBinDataV2(allDataList, rediskey);
                return ResultVo.success("后台正在自动导入，请刷新查看录入进度！");
            }
        } catch (IOException e) {
            log.error("导入文件时发生IO异常: {}", e.getMessage(), e);
            cleanupImportLock(updkey, rediskey);
            return ResultVo.failure("文件读取失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("导入文件时发生异常: {}", e.getMessage(), e);
            cleanupImportLock(updkey, rediskey);
            return ResultVo.failure("导入失败: " + e.getMessage());
        }
    }

    private void validateFileFormat(String filename) {
        if (!filename.matches("^.+\\.(?i)(xlsx)$") && !filename.matches("^.+\\.(?i)(xls)$")) {
            throw new BusinessException("文件格式错误");
        }
    }

    private void setImportLock(String userNo) {
        String updkey = "ops:binimp:";
        String lockValue = userNo + "-正在导入中,请稍等再导入";
        redisUtil.set(updkey, lockValue);
        redisUtil.expire(updkey, 1800); // 30分钟后失效
    }
    private void cleanupImportLock(String updkey, String rediskey) {
        redisUtil.delete(updkey);
        redisUtil.delete(rediskey);
    }

    @Override
    public ResultVo<String> finishbinordercalc(Long id) {
//        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();
//        query.ge(BinOrderDetailDO::getStatus, 2);
//        if (binOrderDetailMapper.selectCount(query) <= 0) {
//            return ResultVo.failure("还未生成申请，不能结束");
//        }
        BinOrderCalcDO calcDO = new BinOrderCalcDO();
        calcDO.setId(id);
        calcDO.setStatus(6);
        return ResultVo.success(binOrderCalcMapper.updateById(calcDO) == 1 ? "结束申请成功" : "结束申请失败");
    }

    @Override
    public ResultVo<String> checkImpBindataStatus(String key) {
        key = "ops:binimp:" + SMCApp.getLoginAuthDto().getUserNo();
        String valus = "";
        if (PublicUtil.isNotEmpty(redisUtil.get(key))) {
            valus = String.valueOf(redisUtil.get(key));
        }
        return ResultVo.success(valus);
    }

    @Override
    public ResultVo<List<RedisMessageVO>> listBinRedisMessage() {
        List<RedisMessageVO> list = new ArrayList<>();
        Object obj = redisUtil.getHashKeyValue("ops:cpfr:binCalc");
        if (obj != null) {
            Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj), HashMap.class);
            RedisMessageVO vo;
            if (map.containsKey(SMCApp.getLoginAuthDto().getUserNo())) {
                vo = JSON.parseObject(map.get(SMCApp.getLoginAuthDto().getUserNo()).toString(), RedisMessageVO.class);
                list.add(vo);
            }
        }
        return ResultVo.success(list);
    }

    @Override
    public void exportCalcBinOrderData(BinOrderCalcQueryDTO dto) {
        LambdaQueryWrapper<BinOrderDetailDO> query = new LambdaQueryWrapper<>();

        query.eq(BinOrderDetailDO::getCalcId, dto.getCalcId())
                .gt(dto.getOnlyHasOrderQty(), BinOrderDetailDO::getConfirmQty, 0)
                .like(PublicUtil.isNotEmpty(dto.getModelNo()), BinOrderDetailDO::getModelNo, dto.getModelNo())
                .eq(dto.getAppId() != null && dto.getAppId() > 0, BinOrderDetailDO::getAppId, dto.getAppId())
                .like(PublicUtil.isNotEmpty(dto.getOrderNo()), BinOrderDetailDO::getOrderNo, dto.getOrderNo())
                .eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), BinOrderDetailDO::getWarehouseCode, dto.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(dto.getCalcType()), BinOrderDetailDO::getCalcType, dto.getCalcType());

        if (dto.getStatus() != null && dto.getStatus() == 1) {
            query.le(BinOrderDetailDO::getStatus, dto.getStatus());
        } else if (dto.getStatus() != null && dto.getStatus() == 2) {
            query.ge(BinOrderDetailDO::getStatus, dto.getStatus());
        }
        if (PublicUtil.isNotEmpty(dto.getMoreContent())) {
            query.apply(dto.getMoreContent());
        }
        List<BinOrderDetailDO> list = binOrderDetailMapper.selectList(query);

        if (list == null) {
            return;
        }
        String path = "templates/binOrderData.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);

        ExcelUtil excel = new ExcelUtil(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (BinOrderDetailDO detailDO : list) {
            cel = 0;
            excel.setCellValue(row, cel++, detailDO.getModelNo());
            excel.setCellValue(row, cel++, detailDO.getConfirmQty());
            excel.setCellValue(row, cel++, detailDO.getMonths());
            excel.setCellValue(row, cel++, detailDO.getMean());
            excel.setCellValue(row, cel++, detailDO.getFreq());
            excel.setCellValue(row, cel++, detailDO.getReplenishmentMethod());

            excel.setCellValue(row, cel++, detailDO.getOrderType());
            excel.setCellValue(row, cel++, detailDO.getSupplierCode());
            excel.setCellValue(row, cel++, detailDO.getTransType());
            excel.setCellValue(row, cel++, detailDO.getDlvDate());
            excel.setCellValue(row, cel++, detailDO.getPoQty());
            excel.setCellValue(row, cel++, detailDO.getTransQty());
            excel.setCellValue(row, cel++, detailDO.getWarehouseCode());
            excel.setCellValue(row, cel++, detailDO.getPoType());

            excel.setCellValue(row, cel++, detailDO.getPropertyId());
            excel.setCellValue(row, cel++, detailDO.getCustomerNo());
            excel.setCellValue(row, cel++, detailDO.getPpl());
            excel.setCellValue(row, cel++, detailDO.getProjectNo());
            excel.setCellValue(row, cel++, detailDO.getInventoryTypeCode());
            excel.setCellValue(row, cel++, detailDO.getGroupCustomerNo());
            excel.setCellValue(row, cel++, detailDO.getStockQty());
            excel.setCellValue(row, cel++, detailDO.getOrdingQty());
            excel.setCellValue(row, cel++, detailDO.getPreQty());

            excel.setCellValue(row, cel++, detailDO.getDirectpurchase());
            excel.setCellValue(row, cel++, detailDO.getStockMonths());
            excel.setCellValue(row, cel++, detailDO.getBinClass());
            excel.setCellValue(row, cel++, detailDO.getQtybin());
            excel.setCellValue(row, cel++, detailDO.getBincell());
            excel.setCellValue(row, cel++, detailDO.getEprice());
            excel.setCellValue(row, cel++, detailDO.getStatus());

            excel.setCellValue(row, cel++, detailDO.getSpecStockQty());
            excel.setCellValue(row, cel++, detailDO.getSpecOrdingQty());
            excel.setCellValue(row, cel++, detailDO.getLastDlvdate());
            excel.setCellValue(row, cel++, detailDO.getModelClass());
            excel.setCellValue(row, cel++, detailDO.getBoxQty());
            excel.setCellValue(row, cel++, detailDO.getBoxNo());
            //excel.setCellValue(row, cel++, detailDO.getMinOrderQty());
            //excel.setCellValue(row, cel++, detailDO.getMaxStockQty());
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, "");
            excel.setCellValue(row, cel++, detailDO.getRemark());
            excel.setCellValue(row, cel++, detailDO.getOrderNo());
            excel.setCellValue(row, cel++, detailDO.getAppId());
            excel.setCellValue(row, cel++, detailDO.getStockQtyAll());
            excel.setCellValue(row, cel++, detailDO.getMeanAll());
            excel.setCellValue(row, cel, detailDO.getStockMonthsAll());
            row++;
        }
        excel.writeToResponse(response, "binOrderData.xlsx");
    }

    @Override
    public ResultVo<PageInfo<BinOrderDetailSplitVO>> listBinDetailSplit(BinOrderDetailSplitRequestDTO dto) {

        LambdaQueryWrapper<BinOrderDetailSplitDO> query = getBinDetailSplitWrapper(dto);
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<BinOrderDetailSplitDO> list = binOrderDetailSplitMapper.selectList(query);
        PageInfo<BinOrderDetailSplitDO> pageInfo = PageInfo.of(list);
        PageInfo<BinOrderDetailSplitVO> page = BeanCopyUtil.pageDto2Vo(pageInfo, BinOrderDetailSplitVO.class);
        return ResultVo.success(page);
    }

    private LambdaQueryWrapper<BinOrderDetailSplitDO> getBinDetailSplitWrapper(BinOrderDetailSplitRequestDTO dto) {
        LambdaQueryWrapper<BinOrderDetailSplitDO> query = new LambdaQueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(dto.getAppId()), BinOrderDetailSplitDO::getAppId, dto.getAppId())
                .eq(PublicUtil.isNotEmpty(dto.getModelNo()), BinOrderDetailSplitDO::getModelNo, dto.getModelNo())
                .eq(PublicUtil.isNotEmpty(dto.getSupplierCode()), BinOrderDetailSplitDO::getSupplierCode, dto.getSupplierCode())
                .eq(PublicUtil.isNotEmpty(dto.getOrderType()), BinOrderDetailSplitDO::getOrderType, dto.getOrderType())
                .and(i -> i.eq(BinOrderDetailSplitDO::getStatus, 5).or().eq(BinOrderDetailSplitDO::getStatus, 3))
                .ne(BinOrderDetailSplitDO::getDelFlag, 1);
        if (StringUtils.isNotBlank(dto.getOrderNo())) {
            if (dto.getOrderNo().contains("-")) {
                String[] splits = dto.getOrderNo().split("-");
                query.eq(BinOrderDetailSplitDO::getOrderNo, splits[0]);
                query.eq(BinOrderDetailSplitDO::getItemNo, splits[1]);
            } else {
                query.eq(BinOrderDetailSplitDO::getOrderNo, dto.getOrderNo());
            }
        }
        return query;
    }

    @Override
    public void exportBinDetailSplit(BinOrderDetailSplitRequestDTO dto) {

        LambdaQueryWrapper<BinOrderDetailSplitDO> query = getBinDetailSplitWrapper(dto);
        List<BinOrderDetailSplitDO> list = binOrderDetailSplitMapper.selectList(query);
        if (list == null) {
            return;
        }
        String path = "templates/binOrderDetailSplit.xlsx";
        InputStream inputStream = FileUtil.getTemplate(path);
        ExcelHelper excel = new ExcelHelper(inputStream);
        excel.openSheet(0);

        // 向模板中写入数据
        int row = 1;
        int cel;
        for (BinOrderDetailSplitDO detailDO : list) {
            cel = 0;
            excel.setCellValue(row, cel++, detailDO.getAppId());
            excel.setCellValue(row, cel++, detailDO.getOrderNo());
            excel.setCellValue(row, cel++, detailDO.getItemNo());
            excel.setCellValue(row, cel++, detailDO.getStatus());
            excel.setCellValue(row, cel++, detailDO.getOrderType());
            excel.setCellValue(row, cel++, detailDO.getModelNo());
            excel.setCellValue(row, cel++, detailDO.getConfirmQty());
            excel.setCellValue(row, cel++, detailDO.getSupplierCode());
            excel.setCellValue(row, cel++, detailDO.getDlvDate());
            excel.setCellValue(row, cel++, detailDO.getTransType());
            excel.setCellValue(row, cel, detailDO.getUpdateTime());
            row++;
        }
        excel.writeToResponse(response, "binOrderDetailSplit.xlsx");
    }

    @Override
    public ResultVo<List<OrdingOrderVO>> listOrdingOrder(String modelNo, String warehouseCode, Long id) {
        if (id == null) {
            //List<String> warehouseCodes = dictCommonService.getWarehouseCodesByWarehouseCodeForMasterAndSub(warehouseCode).getData();
            //if (PublicUtil.isEmpty(warehouseCodes)) {
            //    warehouseCodes.add(warehouseCode);
            //}
            //List<OrdingOrderVO> list = binOrderDetailMapper.listOrdingOrder(modelNo, warehouseCodes);
            return ResultVo.success(new ArrayList<>());
        }
        LambdaQueryWrapper<BinOrderDetailOrdingInfoDO> qw = new LambdaQueryWrapper<>();
        qw.eq(BinOrderDetailOrdingInfoDO::getBinDetailId, id)
                .eq(BinOrderDetailOrdingInfoDO::getDelFlag, 0)
        ;
        List<BinOrderDetailOrdingInfoDO> ordingInfos = binOrderDetailOrdingInfoMapper.selectList(qw);
        List<OrdingOrderVO> list = new ArrayList<>();
        for (BinOrderDetailOrdingInfoDO ording : ordingInfos) {
            OrdingOrderVO ordingOrderVO = new OrdingOrderVO();
            BeanUtil.copyProperties(ording, ordingOrderVO);
            ordingOrderVO.setOrderFno(ording.getOrderFno());
            list.add(ordingOrderVO);
        }
        return ResultVo.success(list);
    }

    @Override
    public ResultVo<ModelExpFreqMainVO> getModelExpFreqMain() {
        ModelExpFreqMainVO infoVO = binOrderDetailMapper.getModelExpFreqMain();
        return ResultVo.success(infoVO);
    }

    @Override
    public ResultVo<List<PrepareOrderVO>> listPrepareOrderView(String modelNo, String warehouseCode, Long id) {
//        LambdaQueryWrapper<PrepareOrderViewDO> query = new LambdaQueryWrapper<>();
//        query.eq(PrepareOrderViewDO::getModelNo, modelNo)
//                .eq(PrepareOrderViewDO::getRequestWarehouseId, warehouseCode);
        if (id == null) {
            return ResultVo.success(new ArrayList<>());
        }
        LambdaQueryWrapper<BinOrderDetailPreInfoDO> qw = new LambdaQueryWrapper<>();
        qw.eq(BinOrderDetailPreInfoDO::getBinDetailId, id)
                .eq(BinOrderDetailPreInfoDO::getDelFlag, 0)
        ;
        List<BinOrderDetailPreInfoDO> preInfos = binOrderDetailPreInfoMapper.selectList(qw);
        List<PrepareOrderVO> list = new ArrayList<>();
        for (BinOrderDetailPreInfoDO pre : preInfos) {
            PrepareOrderVO ordingOrderVO = new PrepareOrderVO();
            BeanUtil.copyProperties(pre, ordingOrderVO);
            list.add(ordingOrderVO);
        }
        return ResultVo.success(list);
    }

}
