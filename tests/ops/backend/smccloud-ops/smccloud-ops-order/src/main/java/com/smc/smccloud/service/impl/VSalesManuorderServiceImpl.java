package com.smc.smccloud.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.mapper.VSalesManuorderMapper;
import com.smc.smccloud.model.OrderSales.OrderSalesDO;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderDO;
import com.smc.smccloud.model.VSalesManuorder.VSalesManuorderParams;
import com.smc.smccloud.service.VSalesManuorderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smc
 * @since 2022-02-11
 */
@Service
public class VSalesManuorderServiceImpl extends ServiceImpl<VSalesManuorderMapper, VSalesManuorderDO> implements VSalesManuorderService {

    @Resource
    private VSalesManuorderMapper vSalesManuorderMapper;

    @Override
    @DS("cmdb")
    public PageInfo<VSalesManuorderDO> findAll(VSalesManuorderParams params, Page page) {

        QueryWrapper<VSalesManuorderDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("1",1);
        queryWrapper.eq(PublicUtil.isNotEmpty(params.getOptStatus()),"optStatus",params.getOptStatus());


        return PageHelper.startPage(page.getPageNumber(),page.getPageSize())
                .doSelectPageInfo(() -> vSalesManuorderMapper.selectList(queryWrapper));
    }

    @Override
    @DS("cmdb")
    public int update(VSalesManuorderDO vSalesManuorderDO) {
        if (vSalesManuorderDO == null) {
            return 0;
        }
        try {
            LambdaUpdateWrapper<VSalesManuorderDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper
                    .eq(VSalesManuorderDO::getId,vSalesManuorderDO.getId())
                    .set(StringUtils.isNotBlank(vSalesManuorderDO.getExpInvCode()),VSalesManuorderDO::getExpInvCode,vSalesManuorderDO.getExpInvCode())
                    .set(null != vSalesManuorderDO.getSalesDeliveryTime(),VSalesManuorderDO::getSalesDeliveryTime,vSalesManuorderDO.getSalesDeliveryTime())
                    .set(null != vSalesManuorderDO.getTaxRate(),VSalesManuorderDO::getTaxRate,vSalesManuorderDO.getTaxRate())
                    .set(null != vSalesManuorderDO.getSalesStatus(),VSalesManuorderDO::getSalesStatus,vSalesManuorderDO.getSalesStatus());
            return vSalesManuorderMapper.update(null,lambdaUpdateWrapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
