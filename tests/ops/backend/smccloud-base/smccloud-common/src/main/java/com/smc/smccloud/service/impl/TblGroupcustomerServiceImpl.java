package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.TblGroupcustomerMapper;

import com.smc.smccloud.model.Customer.TblGroupcustomerDO;
import com.smc.smccloud.service.TblGroupcustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-04-15
 */
@Service
public class TblGroupcustomerServiceImpl extends ServiceImpl<TblGroupcustomerMapper, TblGroupcustomerDO> implements TblGroupcustomerService {

    @Resource
    private TblGroupcustomerMapper tblGroupcustomerMapper;

    @Override
    public ResultVo<String> getDlvDeptNoByNo(String customerNo) {
        QueryWrapper<TblGroupcustomerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Customer", customerNo);

        TblGroupcustomerDO groupcustomerDO = tblGroupcustomerMapper.selectOne(queryWrapper);

        if (groupcustomerDO == null) {
            return ResultVo.failure("未查询到部门代码");
        }
        return ResultVo.success(groupcustomerDO.getDlvDeptNo());
    }
}
