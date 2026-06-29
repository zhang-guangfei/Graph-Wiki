package com.smc.smccloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.redis.RedisManager;
import com.smc.smccloud.mapper.IpsSendOrderToOpsMapper;
import com.smc.smccloud.model.Constants;
import com.smc.smccloud.model.DataTypeVO;
import com.smc.smccloud.model.VSalesManuorder.OpsVManuorderToSales;
import com.smc.smccloud.model.order.IpsSendOrderToOpsDO;
import com.smc.smccloud.service.DictCommonService;
import com.smc.smccloud.service.IpsFactoryOrderReceive;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * lyc
 *
 * @date 2024/12/30
 * /
 * /**
 * @Author lyc
 * @Date 2024/12/30 10:52
 * @Descripton TODO
 */
@Service
public class IpsFactoryOrderReceiveImpl implements IpsFactoryOrderReceive {

    @Resource
    private RedisManager redisManager;

    @Resource
    private IpsSendOrderToOpsMapper ipsSendOrderToOpsMapper;

    @Resource
    private DictCommonService dictCommonService;

    @Override
    public ResultVo<List<OpsVManuorderToSales>> getManuOrderFromIpsSendOrdertoOps() {

        String id = null;

        String[] split = Constants.ips_to_ops_max_id.split(";");
        // 从字典中获取上次读取的最大的id
        ResultVo<DataTypeVO> dataTypeCodesInfo = dictCommonService.getDataTypeCodesInfo(split[0], split[1]);
        if (!dataTypeCodesInfo.isSuccess()) {
            return ResultVo.failure("未能从字典中获取上次读取的最大的id");
        }
        id = dataTypeCodesInfo.getData().getExtNote1();
        List<IpsSendOrderToOpsDO> manuOrderFromIpstoOps = ipsSendOrderToOpsMapper.getManuOrderFromIpstoOps(id);
        return ResultVo.success();
    }




}
