package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.enums.WarehouseTypeEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.warehouse.OpsWarehouseSalesBranchConfigMapper;
import com.smc.smccloud.mapper.warehouse.WarehouseMapper;
import com.smc.smccloud.model.csstock.CsWarehouseRequest;
import com.smc.smccloud.model.csstock.CsWarehouseVO;
import com.smc.smccloud.model.csstock.WarehouseDO;
import com.smc.smccloud.model.csstock.WarehouseStockCodeVO;
import com.smc.smccloud.model.prestock.OpsWarehouseSalesbranchConfigDO;
import com.smc.smccloud.service.OpsWarehouseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-01-11 10:58
 * Description:
 */
@Service
public class OpsWarehouseServiceImpl implements OpsWarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Resource
    private OpsWarehouseSalesBranchConfigMapper opsWarehouseSalesBranchConfigMapper;

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public WarehouseDO getWarehouseInfoByCode(String warehouseCode) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WarehouseDO::getWarehouseCode, warehouseCode);
        WarehouseDO warehouseDO = warehouseMapper.selectOne(queryWrapper);
        if (PublicUtil.isEmpty(warehouseDO.getReturnableFlag())) {
            warehouseDO.setReturnableFlag(0);
        }
        if (PublicUtil.isEmpty(warehouseDO.getTransferFlag())) {
            warehouseDO.setTransferFlag(0);
        }
        if (PublicUtil.isEmpty(warehouseDO.getPrestockFlag())) {
            warehouseDO.setPrestockFlag(0);
        }
        return warehouseDO;
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<String> getOpsWarehouseSalesBranchConfig(String salesBranchId) {
        List<OpsWarehouseSalesbranchConfigDO> configList = opsWarehouseSalesBranchConfigMapper.findOpsWarehouseSalesBranchConfigBySalesBranchId(salesBranchId);
        return configList.stream()
                // 按优先级排序
                .sorted(Comparator.comparing(OpsWarehouseSalesbranchConfigDO::getPriority))
                // 提取可出在库仓库代码
                .map(OpsWarehouseSalesbranchConfigDO::getWarehouseCode)
                .collect(Collectors.toList());
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public String getWarehouseDeptNoByWarehouseCode(String warehouseCode) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WarehouseDO::getWarehouseCode, WarehouseDO::getDeptNo);
        queryWrapper.eq(WarehouseDO::getWarehouseCode, warehouseCode)
                .eq(WarehouseDO::getDelflag, 0);
        WarehouseDO warehouseInfo = warehouseMapper.selectOne(queryWrapper);
        if (warehouseInfo == null) {
            return null;
        }
        return warehouseInfo.getDeptNo();
    }

    @Override
    public ResultVo<PageInfo<CsWarehouseVO>> listCsWarehouse(CsWarehouseRequest request) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getAgentNo()), WarehouseDO::getCustomerNo, request.getAgentNo());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), WarehouseDO::getWarehouseCode, request.getWarehouseCode());
        queryWrapper.eq(PublicUtil.isNotEmpty(request.getWarehouseType()), WarehouseDO::getWarehouseType, request.getWarehouseType());
        // 20230322 BUG10087 WuJiaWen
        queryWrapper.eq(StringUtils.isBlank(request.getWarehouseType()) && StringUtils.isBlank(request.getWarehouseCode()), WarehouseDO::getDisableFlag, 0);

        PageInfo<WarehouseDO> pageInfo = PageHelper.startPage(request.getPageNum(), request.getPageSize())
                .doSelectPageInfo(() -> warehouseMapper.selectList(queryWrapper.orderByDesc(WarehouseDO::getCustomerNo)));

        PageInfo<CsWarehouseVO> voPageInfo = BeanCopyUtil.pageDto2Vo(pageInfo, CsWarehouseVO.class);
        return ResultVo.success(voPageInfo);
    }

    @Override
    public List<WarehouseDO> listWarehouse(CsWarehouseRequest request) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .eq(PublicUtil.isNotEmpty(request.getAgentNo()), WarehouseDO::getCustomerNo, request.getAgentNo())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseCode()), WarehouseDO::getWarehouseCode, request.getWarehouseCode())
                .eq(PublicUtil.isNotEmpty(request.getWarehouseType()), WarehouseDO::getWarehouseType, request.getWarehouseType());
        return warehouseMapper.selectList(queryWrapper);
    }

    /**
     * 委托在库库房-名称
     *
     * @param warehouseCode
     * @param warehouseType
     * @return
     */
    @Override
    public ResultVo<List<WarehouseStockCodeVO>> listWarehouseStockCode(String warehouseCode, String warehouseType) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName, WarehouseDO::getWarehouseType, WarehouseDO::getCustomerNo);
        queryWrapper.like(PublicUtil.isNotEmpty(warehouseCode), WarehouseDO::getWarehouseCode, warehouseCode);
        queryWrapper.like(PublicUtil.isNotEmpty(warehouseType), WarehouseDO::getWarehouseType, warehouseType);
        List<WarehouseDO> list = warehouseMapper.selectList(queryWrapper);
        List<WarehouseStockCodeVO> warehouseVOs = new ArrayList<>(list.size());
        WarehouseStockCodeVO warehouseVO;
        for (WarehouseDO warehouseDO : list) {
            warehouseVO = new WarehouseStockCodeVO();
            warehouseVO.setWarehouseCode(warehouseDO.getWarehouseCode());
            warehouseVO.setWarehouseName(warehouseDO.getWarehouseName());
            warehouseVO.setWarehouseType(warehouseDO.getWarehouseType());
            warehouseVO.setCustomerNo(warehouseDO.getCustomerNo());
            warehouseVOs.add(warehouseVO);
        }
        return ResultVo.success(warehouseVOs);
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public WarehouseDO getCsWarehouseDO(String agentNo, String warehouseCode) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(WarehouseDO::getCustomerNo, agentNo)
                .eq(WarehouseDO::getWarehouseCode, warehouseCode)
                .eq(WarehouseDO::getWarehouseType, WarehouseTypeEnum.WT.getHouseTypeCode());
        return warehouseMapper.selectOne(queryWrapper);
    }

    @Override
    public ResultVo<String> saveCsWarehouse(WarehouseDO warehourseDO) {
        System.out.println(warehourseDO);
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(PublicUtil.isNotEmpty(warehourseDO.getWarehouseCode()), WarehouseDO::getWarehouseCode, warehourseDO.getWarehouseCode());
        WarehouseDO csWarehouse = warehouseMapper.selectOne(queryWrapper);
        int result;
        if (!PublicUtil.isEmpty(csWarehouse)) {
            LambdaQueryWrapper<WarehouseDO> updateWrapper = Wrappers.lambdaQuery();
            updateWrapper.eq(WarehouseDO::getWarehouseCode, warehourseDO.getWarehouseCode());
            //更新项总数
            result = warehouseMapper.update(warehourseDO, updateWrapper);
            if (result == 0) {
                return ResultVo.failure("修改错误");
            }
        } else {
            result = warehouseMapper.insert(warehourseDO);
            if (result == 0) {
                return ResultVo.failure("新增错误");
            }
        }
        return ResultVo.success("保存成功");
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<String> listAllWTWarehouseCode() {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WarehouseDO::getWarehouseCode);
        queryWrapper.eq(WarehouseDO::getWarehouseType, WarehouseTypeEnum.WT.getHouseTypeCode());
        List<WarehouseDO> warehouseDOList = warehouseMapper.selectList(queryWrapper);
        return warehouseDOList.stream().map(WarehouseDO::getWarehouseCode).collect(Collectors.toList());
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public WarehouseDO getWareHouseInfoByCode(String wareHouseCode) {
        if (StringUtils.isBlank(wareHouseCode)) {
            return null;
        }
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WarehouseDO::getWarehouseCode, WarehouseDO::getWarehouseName, WarehouseDO::getRcvLinkman, WarehouseDO::getRcvLinkTel, WarehouseDO::getRcvLinkEmail,
                WarehouseDO::getReturnableFlag);
        queryWrapper.eq(WarehouseDO::getWarehouseCode, wareHouseCode);
        return warehouseMapper.selectOne(queryWrapper);
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public String getWarehouseType(String warehouseCode) {
        LambdaQueryWrapper<WarehouseDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.select(WarehouseDO::getWarehouseType);
        queryWrapper.eq(WarehouseDO::getWarehouseCode, warehouseCode);
        WarehouseDO warehouseDO = warehouseMapper.selectOne(queryWrapper);
        if (warehouseDO == null) {
            return "";
        }
        return warehouseDO.getWarehouseType();
    }

    @Override
    @DS("opsdb")
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public boolean isMasterWarehouse(String warehouseCode) {
        String warehouseType = this.getWarehouseType(warehouseCode);
        return WarehouseTypeEnum.RDC.getHouseTypeCode().equals(warehouseType);
    }
}
