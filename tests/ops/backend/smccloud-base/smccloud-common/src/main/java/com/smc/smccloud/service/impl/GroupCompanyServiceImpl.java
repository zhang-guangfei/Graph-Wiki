package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.GroupCompanyMapper;
import com.smc.smccloud.model.Customer.GroupCompanyDO;
import com.smc.smccloud.service.GroupCompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-04-14
 */
@Service
public class GroupCompanyServiceImpl extends ServiceImpl<GroupCompanyMapper, GroupCompanyDO> implements GroupCompanyService {

    @Resource
    private GroupCompanyMapper groupCompanyMapper;

    @Override
    public ResultVo<String> findcustGroupIdByName(String custName) {

        LambdaQueryWrapper<GroupCompanyDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupCompanyDO::getName,custName);
        GroupCompanyDO groupCompanyDO = groupCompanyMapper.selectOne(queryWrapper);
        if (groupCompanyDO == null || groupCompanyDO.getId() == null) {
            return ResultVo.failure("暂无数据");
        }
        return ResultVo.success(groupCompanyDO.getId());
    }
}
