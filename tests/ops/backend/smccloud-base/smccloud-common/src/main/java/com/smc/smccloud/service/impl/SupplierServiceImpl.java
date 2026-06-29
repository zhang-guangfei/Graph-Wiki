package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.constants.Constants;
import com.smc.smccloud.core.model.dto.LoginUserDTO;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.core.utils.SMCApp;
import com.smc.smccloud.mapper.SupplierMapper;
import com.smc.smccloud.mapper.TblGroupcustomerMapper;
import com.smc.smccloud.model.Customer.TblGroupcustomerDO;
import com.smc.smccloud.model.SupplierDO;
import com.smc.smccloud.model.customer.TblGroupcustomerVO;
import com.smc.smccloud.model.supplier.SupplierRequest;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.SupplierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Author: C18117
 * Date: 2022-02-07
 * Description:
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierMapper supplierMapper;
    @Resource
    private RedisManager redisManager;
    @Resource
    private TblGroupcustomerMapper tblGroupcustomerMapper;

    @Override
    public ResultVo<List<SupplierVo>> findSupplierInfo() {
        QueryWrapper<SupplierDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("order by sort asc");

        List<SupplierDO> list = supplierMapper.selectList(queryWrapper);
        List<SupplierVo> voList = BeanCopyUtil.copyList(list, SupplierVo.class);

        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<List<SupplierVo>> findSupplierByIdOrName(String companyId, String name) {

        LambdaQueryWrapper<SupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PublicUtil.isNotEmpty(companyId), SupplierDO::getId, companyId)
                .eq(PublicUtil.isNotEmpty(name), SupplierDO::getName, name);
        queryWrapper.last("order by sort asc");

        List<SupplierDO> list = supplierMapper.selectList(queryWrapper);
        List<SupplierVo> voList = BeanCopyUtil.copyList(list, SupplierVo.class);

        return ResultVo.success(voList);
    }

    @Override
    public ResultVo<String> updateSupplierData(SupplierVo supplierVo) {

        QueryWrapper<SupplierDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", supplierVo.getId());

        LoginUserDTO userDTO = SMCApp.getLoginAuthDto();
        supplierVo.setUpdator(userDTO.getUserNo());
        SupplierDO supplierDO = BeanCopyUtil.copy(supplierVo, SupplierDO.class);
        int update = supplierMapper.update(supplierDO, queryWrapper);

        return update == 1 ? ResultVo.success("修改成功") : ResultVo.failure("修改失败");
    }

    @Override
    public ResultVo<String> getSupplierName(String supplierCode) {
        if (PublicUtil.isEmpty(supplierCode)) {
            return ResultVo.failure("未查询到供应商名称");
        }
        String supplierName = null;
        Object obj = redisManager.hGet(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode);

        if (obj != null) {
            supplierName = obj.toString();
        }

        if (obj == null) {
            QueryWrapper<SupplierDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", supplierCode);
            SupplierDO supplierDO = supplierMapper.selectOne(queryWrapper);

            if (PublicUtil.isNotEmpty(supplierDO)) {
                supplierName = supplierDO.getName();
                redisManager.hPut(Constants.REDIS_SUPPLIER_NAME_ALL, supplierCode, supplierName);
            }
        }
        return ResultVo.success(supplierName);
    }

    @Override
    public ResultVo<List<SupplierVo>> findChinaSuppliers() {
        QueryWrapper<SupplierDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stdDeliveryDay", 2);
        List<SupplierDO> supplierDOList = supplierMapper.selectList(queryWrapper);
        if (supplierDOList.isEmpty()) {
            return ResultVo.failure("无数据.");
        }
        List<SupplierVo> supplierVos = BeanCopyUtil.copyList(supplierDOList, SupplierVo.class);
        return ResultVo.success(supplierVos);
    }

    @Override
    public ResultVo<TblGroupcustomerVO> getTblGroupCustInfo(String customerNo) {
        if (StringUtils.isBlank(customerNo)) {
            return ResultVo.failure("参数不可为空");
        }
        LambdaQueryWrapper<TblGroupcustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblGroupcustomerDO::getCustomerNo, customerNo);
        TblGroupcustomerDO tblGroupcustomerDO = tblGroupcustomerMapper.selectOne(queryWrapper);
        if (tblGroupcustomerDO == null) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(BeanCopyUtil.copy(tblGroupcustomerDO, TblGroupcustomerVO.class));
    }

    @Override
    public ResultVo<PageInfo<SupplierVo>> findSupplierList(SupplierRequest request) {
        if (null == request) {
            return ResultVo.failure("参数不可为空");
        }
        LambdaQueryWrapper<SupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.isNotBlank(request.getId()), SupplierDO::getId, request.getId())
                .likeRight(StringUtils.isNotBlank(request.getName()), SupplierDO::getId, request.getName())
                .eq(StringUtils.isNotBlank(request.getId()), SupplierDO::getId, request.getId())
                .eq(StringUtils.isNotBlank(request.getFstTransTypeId()), SupplierDO::getFstTransTypeId, request.getFstTransTypeId())
                .last("order by sort " + request.getOrder());
        PageInfo<SupplierDO> supplierDOPageInfo = PageHelper.startPage(request.getPage().getPageNumber(), request.getPage().getPageSize())
                .doSelectPageInfo(() -> supplierMapper.selectList(queryWrapper));

        PageInfo<SupplierVo> supplierVoPageInfo = BeanCopyUtil.pageDto2Vo(supplierDOPageInfo, SupplierVo.class);
        return ResultVo.success(supplierVoPageInfo);
    }

    @Override
    public ResultVo<SupplierVo> findSupplierById(String id) {
        if (PublicUtil.isEmpty(id)) {
            return ResultVo.failure("请输入ID");
        }
        LambdaQueryWrapper<SupplierDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupplierDO::getId,id);
        SupplierDO supplierDO = supplierMapper.selectOne(queryWrapper);
        if (supplierDO == null) {
            return ResultVo.failure("未查询到供应商");
        }
        SupplierVo supplierVo = BeanCopyUtil.copy(supplierDO, SupplierVo.class);
        return ResultVo.success(supplierVo);
    }

    @Override
    public ResultVo<String> addSupplier(SupplierVo supplierVo) {
        if (null == supplierVo || StringUtils.isBlank(supplierVo.getId())
                || StringUtils.isBlank(supplierVo.getName())
                || StringUtils.isBlank(supplierVo.getFcostcode())
                || supplierVo.getIsAutoSend() == null) {
            return ResultVo.failure("供应商id/供应商名称/财务成本系统供应商分类id不可为空");
        }
        SupplierDO supplierDO = BeanCopyUtil.copy(supplierVo, SupplierDO.class);

        SupplierDO supplierObj = supplierMapper.selectById(supplierVo.getId());

        if (null != supplierObj) {
            return ResultVo.failure("该供应商已存在,不可重复添加");
        }

        int insert = supplierMapper.insert(supplierDO);
        if (insert > 0) {
            return ResultVo.success("新增成功");
        } else {
            return ResultVo.failure("新增失败");
        }
    }

    @Override
    public ResultVo<String> deleteSupplierById(List<String> id) {
        if (id.isEmpty()) {
            return ResultVo.failure("请选择要删除的供应商");
        }
        int i = supplierMapper.deleteBatchIds(id);
        if (i > 0) {
            return ResultVo.success("操作成功");
        } else {
            return ResultVo.success("操作失败");
        }
    }

    @Override
    public ResultVo<String> editSupplier(SupplierVo supplierVo) {
        if (null == supplierVo || StringUtils.isBlank(supplierVo.getId())
                || StringUtils.isBlank(supplierVo.getName())
                || StringUtils.isBlank(supplierVo.getFcostcode())) {
            return ResultVo.failure("供应商id/供应商名称/财务成本系统供应商分类id不可为空");
        }
        SupplierDO supplierDO = BeanCopyUtil.copy(supplierVo, SupplierDO.class);
        supplierDO.setUpdateTime(new Date());
        supplierDO.setUpdator(supplierVo.getUpdator());
        int update = supplierMapper.updateById(supplierDO);
        if (update > 0) {
            return ResultVo.success("编辑成功");
        } else {
            return ResultVo.failure("编辑失败");
        }
    }
}
