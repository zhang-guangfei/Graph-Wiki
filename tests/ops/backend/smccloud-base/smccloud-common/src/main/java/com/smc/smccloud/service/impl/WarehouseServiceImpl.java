package com.smc.smccloud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.HROrganizationMapper;
import com.smc.smccloud.mapper.OpsWarehouseSalesBranchConfigMapper;
import com.smc.smccloud.mapper.WarehouseMapper;
import com.smc.smccloud.model.*;
import com.smc.smccloud.model.warehouse.UpWarehouseDelflagVO;
import com.smc.smccloud.model.warehouse.WarehouseQueryDTO;
import com.smc.smccloud.model.warehouse.WarehouseVO;
import com.smc.smccloud.service.WarehouseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-01-22 12:01
 * Description:
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private OpsWarehouseSalesBranchConfigMapper opsWarehouseSalesBranchConfigMapper;
    @Resource
    private HROrganizationMapper hrOrganizationMapper;

    @Override
    public ResultVo<List<WarehouseVO>> listWarehouse(WarehouseQueryDTO dto) {
        LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
        query.eq(PublicUtil.isNotEmpty(dto.getWarehouseCode()), WarehouseDO::getWarehouseCode, dto.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(dto.getWarehouseType()), WarehouseDO::getWarehouseType, dto.getWarehouseType())
                .like(PublicUtil.isNotEmpty(dto.getKeywords()), WarehouseDO::getWarehouseName, dto.getKeywords());
        List<WarehouseDO> list = warehouseMapper.selectList(query);
        return ResultVo.success(BeanCopyUtil.copyList(list, WarehouseVO.class));
    }

    @Override
    public ResultVo<WarehouseVO> getWarehouseInfoByCode(String warehouseCode) {
        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> warehouseDOList = warehouseInfos.stream()
                .filter(f -> f.getWarehouseCode().equalsIgnoreCase(warehouseCode))
                .collect(Collectors.toList());
        if (PublicUtil.isNotEmpty(warehouseDOList) && warehouseDOList.size() > 0) {
            WarehouseDO warehouseDO = warehouseDOList.get(0);
            if (PublicUtil.isEmpty(warehouseDO.getReturnableFlag())) {
                warehouseDO.setReturnableFlag(0);
            }
            if (PublicUtil.isEmpty(warehouseDO.getTransferFlag())) {
                warehouseDO.setTransferFlag(0);
            }
            if (PublicUtil.isEmpty(warehouseDO.getPrestockFlag())) {
                warehouseDO.setPrestockFlag(0);
            }
            if (PublicUtil.isEmpty(warehouseDO.getPurchaseFlag())) {
                warehouseDO.setPurchaseFlag("0");
            }
            //purchaseFlag为null,核心原因是 BeanCopier 在不使用 Converter 的情况下（第三个参数 false），要求源属性和目标属性的类型完全一致，否则会静默跳过该字段。
            //WarehouseVO copy = BeanCopyUtil.copy(warehouseDO, WarehouseVO.class);
            WarehouseVO warehouseVO = new WarehouseVO();
            BeanUtil.copyProperties(warehouseDO, warehouseVO);
            return ResultVo.success(warehouseVO);
        } else {
            return ResultVo.failure("仓库代码不存在");
        }

    }

    @Override
    public ResultVo<String> getWarehouseName(String warehouseCode) {
        Object warehouseName = redisManager.hGet(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, warehouseCode);
        if (null != warehouseName) {
            return ResultVo.success(warehouseName.toString());
        }
        LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
        query.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName);
        query.eq(WarehouseDO::getWarehouseCode, warehouseCode);
        WarehouseDO info = warehouseMapper.selectOne(query);
        if (info == null) {
            return ResultVo.failure("仓库代码不存在");
        }
        redisManager.hPut(Constants.REDIS_WAREHOUSE_NAME_BY_CODE, warehouseCode, info.getWarehouseName());
        return ResultVo.success(info.getWarehouseName());
    }

    @Override
    public ResultVo<List<WarehouseVO>> listWarehouseNoWT() {
//        LambdaQueryWrapper<WarehouseDO> query = new LambdaQueryWrapper<>();
//        query.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName, WarehouseDO::getWarehouseType, WarehouseDO::getParentCode)
//                .ne(WarehouseDO::getWarehouseType, "WT")
//                .eq(WarehouseDO::getDelflag, "0");
//        List<WarehouseDO> list = warehouseMapper.selectList(query);
//        return ResultVo.success(BeanCopyUtil.copyList(list, WarehouseVO.class));

        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> list = warehouseInfos.stream()
                .filter(f -> f.getDelflag().equals("0") && !f.getWarehouseType().equalsIgnoreCase("WT"))
                .collect(Collectors.toList());
        List<WarehouseVO> warehouseList = new ArrayList<>(list.size());
        for (WarehouseDO warehouseDO : list) {
            WarehouseVO vo = new WarehouseVO();
            vo.setWarehouseCode(warehouseDO.getWarehouseCode());
            vo.setWarehouseName(warehouseDO.getWarehouseName());
            vo.setWarehouseType(warehouseDO.getWarehouseType());
            vo.setParentCode(warehouseDO.getParentCode());
            warehouseList.add(vo);
        }
        return ResultVo.success(warehouseList);
    }

    @Override
    public ResultVo<String> getWarehouseParentCode(String warehouseCode) {
        if (PublicUtil.isEmpty(warehouseCode)) {
            return ResultVo.failure();
        }

//        LambdaQueryWrapper<WarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(WarehouseDO::getWarehouseCode, WarehouseDO::getParentCode);
//        queryWrapper.eq(WarehouseDO::getWarehouseCode, warehouseCode);
//        WarehouseDO warehouseDO = warehouseMapper.selectOne(queryWrapper);
//
        WarehouseDO warehouseDO = new WarehouseDO();
        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> warehouseDOList = warehouseInfos.stream()
                .filter(f -> f.getWarehouseCode().equalsIgnoreCase(warehouseCode))
                .collect(Collectors.toList());
        if (PublicUtil.isNotEmpty(warehouseDOList) && warehouseDOList.size() > 0) {
            warehouseDO = warehouseDOList.get(0);
        }
        return ResultVo.success(Objects.isNull(warehouseDO.getParentCode()) ? "" : warehouseDO.getParentCode());
    }

    @Override
    public ResultVo<List<WarehouseVO>> getSubWarehouse(String warehouseCode) {
        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> warehouseDOList = warehouseInfos.stream()
                .filter(f -> f.getDelflag().equals("0") && StringUtils.equalsIgnoreCase(f.getParentCode(), warehouseCode))
                .collect(Collectors.toList());

        return ResultVo.success(BeanCopyUtil.copyList(warehouseDOList, WarehouseVO.class));
    }

    @Override
    public ResultVo<List<WarehouseVO>> getWarehouseByType(String wareHouseType) {
        List<WarehouseDO> warehouseInfos = this.getWarehouseInfo();
        List<WarehouseDO> warehouseDOList = warehouseInfos.stream()
                .filter(f -> f.getDelflag().equals("0") && f.getWarehouseType().equalsIgnoreCase(wareHouseType))
                .collect(Collectors.toList());
        return ResultVo.success(BeanCopyUtil.copyList(warehouseDOList, WarehouseVO.class));

    }

    @Override
    public ResultVo<String> getWarehouseType(String warehouseCode) {
        if (StringUtils.isBlank(warehouseCode)) {
            return ResultVo.failure("仓库代码不可为空.");
        }

        Object warehouse = redisManager.hGet(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode);
        if (warehouse != null) {
            return ResultVo.success(warehouse.toString());
        }

        LambdaQueryWrapper<WarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(WarehouseDO::getWarehouseType)
                .eq(WarehouseDO::getWarehouseCode, warehouseCode);
        WarehouseDO warehouseDO = warehouseMapper.selectOne(queryWrapper);
        if (warehouseDO == null) {
            return ResultVo.failure("未找到" + warehouseCode + "相关仓库信息.");
        }
        redisManager.hPut(Constants.REDIS_WAREHOUSE_TYPE_BY_CODE, warehouseCode, warehouseDO.getWarehouseType());
        return ResultVo.success(warehouseDO.getWarehouseType());
    }

    @Override
    public ResultVo<Integer> getTransDayByDeptNo(String deptNo) {
        if (StringUtils.isBlank(deptNo)) {
            return ResultVo.failure("部门编码不可为空");
        }
        Object o = redisManager.get(Constants.REDIS_TRANSPORTDAY_DEPTNO + deptNo);
        if (o != null) {
            return ResultVo.success(Integer.parseInt(o.toString()));
        }
        // 根据部门查询库房已经库房所需运输天数
        List<CalTransDayEntity> tranDaysByDeptNo = opsWarehouseSalesBranchConfigMapper.getTranDaysByDeptNo(deptNo);
        if (CollectionUtils.isEmpty(tranDaysByDeptNo)) {
            LambdaQueryWrapper<HROrganizationDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HROrganizationDO::getId, deptNo);
            HROrganizationDO hrOrganizationDO = hrOrganizationMapper.selectOne(queryWrapper);
            if (hrOrganizationDO != null) {
                tranDaysByDeptNo = opsWarehouseSalesBranchConfigMapper.getTranDaysByDeptNo(hrOrganizationDO.getParentId());
                if (CollectionUtils.isEmpty(tranDaysByDeptNo)) {
                    return ResultVo.failure("暂无数据");
                }
            } else {
                return ResultVo.failure("暂无数据");
            }
        }
        for (CalTransDayEntity item : tranDaysByDeptNo) {
            if ("MASTER".equals(item.getWarehouseType()) && item.getDeliveryDay() != null) {
                redisManager.set(Constants.REDIS_TRANSPORTDAY_DEPTNO + deptNo, item.getDeliveryDay(), 60 * 60 * 24 * 3);
                return ResultVo.success(item.getDeliveryDay());
            }
        }
        redisManager.set(Constants.REDIS_TRANSPORTDAY_DEPTNO + deptNo, tranDaysByDeptNo.get(0).getDeliveryDay(), 60 * 60 * 24 * 3);
        return ResultVo.success(tranDaysByDeptNo.get(0).getDeliveryDay());
    }

    @Override
    public Boolean judgeIsSubWareHouse(String wareHouse) {
        if (StringUtils.isBlank(wareHouse)) {
            return false;
        }
        LambdaQueryWrapper<WarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseDO::getWarehouseType, "SUB").eq(WarehouseDO::getWarehouseCode, wareHouse);
        Integer count = warehouseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public ResultVo<List<WarehouseDO>> findWareHouseByType(String wareHouseType) {
        if (StringUtils.isEmpty(wareHouseType)) {
            return ResultVo.success();
        }
        // 因为该方法定义为通用的,所以不指定查询列
        LambdaQueryWrapper<WarehouseDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseDO::getWarehouseType, wareHouseType);
        List<WarehouseDO> warehouseDOS = warehouseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(warehouseDOS)) {
            for (WarehouseDO item : warehouseDOS) {
                if (StringUtils.isNotBlank(item.getWarehouseCode())) {
                    item.setWarehouseName(item.getWarehouseName() + "[" + item.getWarehouseCode() + "]");
                }
            }
        }
        return ResultVo.success(warehouseDOS);
    }

    @Override
    public ResultVo<List<String>> getWarehouseCodeByWarehouseType(String warehouseType) {
        if (StringUtils.isBlank(warehouseType)) {
            return ResultVo.failure("仓库类型不可为空");
        }

        Object o = redisManager.get(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + warehouseType);
        if (o != null) {
            return ResultVo.success(JSON.parseArray(o.toString(), String.class));
        }
        List<String> warehouseCodes = warehouseMapper.getWarehouseCodesByWarehouseType(warehouseType);

        redisManager.set(Constants.REDIS_WAREHOUSE_CODES_BY_TYPE + warehouseType, JSON.toJSONString(warehouseCodes), 60 * 60 * 24 * 7);

        return ResultVo.success(warehouseCodes);
    }

    @Override
    public ResultVo<Boolean> isMasterWarehouse(String warehouseCode) {
        ResultVo<String> resultVo = this.getWarehouseType(warehouseCode);
        if (!resultVo.isSuccess()) {
            return ResultVo.failure(resultVo.getMessage());
        }
        boolean isMaster = "MASTER".equalsIgnoreCase(resultVo.getData());
        return ResultVo.success(isMaster);
    }

    @Override
    public ResultVo<List<WarehouseDO>> findWareHouseInfoWithLike(String code, String type) {
        if (StringUtils.isNotBlank(code)) {
            code = code + "%";
        }
        List<WarehouseDO> wareHouseInfoWithLike = warehouseMapper.findWareHouseInfoWithLike(code, type);
        return ResultVo.success(wareHouseInfoWithLike);
    }

    @Override
    public ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriority() {
        List<OpsWarehouseSalesbranchConfigDO> list = opsWarehouseSalesBranchConfigMapper.getWarehouseSalesBranchConfigForPriority();
        List<OpsWarehouseSalesbranchConfigVO> listVo = BeanCopyUtil.copyList(list, OpsWarehouseSalesbranchConfigVO.class);
        return ResultVo.success(listVo);
    }

    @Override
    public ResultVo<List<OpsWarehouseSalesbranchConfigVO>> getWarehouseSalesBranchConfigForPriorityByMaster() {
        List<OpsWarehouseSalesbranchConfigDO> list = opsWarehouseSalesBranchConfigMapper.getWarehouseSalesBranchConfigForPriorityByMaster();
        List<OpsWarehouseSalesbranchConfigVO> listVo = BeanCopyUtil.copyList(list, OpsWarehouseSalesbranchConfigVO.class);
        return ResultVo.success(listVo);
    }

    @Override
    public ResultVo<String> upWarehouseDelFlag(UpWarehouseDelflagVO upinfo) {
        if (upinfo == null || StringUtils.isBlank(upinfo.getWarehouseCode())) {
            return ResultVo.failure("仓库代码为空.");
        }
        LoginUserDTO loginAuthDto = new LoginUserDTO();
        try {
            loginAuthDto = SMCApp.getLoginAuthDto();
        } catch (Exception e) {
            loginAuthDto.setUserNo("ops");
        }

        LambdaUpdateWrapper<WarehouseDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .eq(WarehouseDO::getWarehouseCode,upinfo.getWarehouseCode())
                .set(WarehouseDO::getDelflag,upinfo.getDelflag())
                .set(WarehouseDO::getModifyTime,new Date())
                .set(WarehouseDO::getModifier,loginAuthDto.getUserNo());
        warehouseMapper.update(null, updateWrapper);
        return ResultVo.success("操作成功");
    }

    private List<WarehouseDO> getWarehouseInfo() {
        List<WarehouseDO> warehouseList;
        Object object = redisManager.get(Constants.REDIS_WAREHOUSE_INFO_ALL);
        if (Objects.nonNull(object)) {
            warehouseList = JSONArray.parseArray(object.toString(), WarehouseDO.class);
        } else {
            warehouseList = warehouseMapper.selectList(null);
            if (PublicUtil.isNotEmpty(warehouseList)) {
                redisManager.set(Constants.REDIS_WAREHOUSE_INFO_ALL, JSON.toJSONString(warehouseList), 60 * 60 * 12);
            }
        }
        return warehouseList;
    }
}
