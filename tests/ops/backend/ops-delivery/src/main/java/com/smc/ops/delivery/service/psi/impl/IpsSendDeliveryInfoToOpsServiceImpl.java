package com.smc.ops.delivery.service.psi.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sales.ops.dto.ips.IpsSendDeliveryInfoToOpsDto;
import com.smc.ops.delivery.mapper.psi.IpsSendDeliveryInfoToOpsMapper;
import com.smc.ops.delivery.model.DataTypeVO;
import com.smc.ops.delivery.model.ips.IpsSendDeliveryInfoToOpsDO;
import com.smc.ops.delivery.service.commonservice.DictCommonService;
import com.smc.ops.delivery.service.psi.IpsSendDeliveryInfoToOpsService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.BeanCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IpsSendDeliveryInfoToOpsServiceImpl implements IpsSendDeliveryInfoToOpsService {

    @Resource
    private DictCommonService dictCommonService;

    @Resource
    private IpsSendDeliveryInfoToOpsMapper ipsSendDeliveryInfoToOpsMapper;

    @Override
    public ResultVo<List<IpsSendDeliveryInfoToOpsDto>> queryIpsSendDeliveryInfoToOpsList() {

        /**
         * 获取上次读取的最大id
         */
        DataTypeVO dataCodesInfo = dictCommonService.getDataCodesInfo("9002", "32");
        if (dataCodesInfo == null) {
            return ResultVo.failure("获取上次读取的最大ID失败,字典配置9002->32");
        }
        LambdaQueryWrapper<IpsSendDeliveryInfoToOpsDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(IpsSendDeliveryInfoToOpsDO::getId, dataCodesInfo.getExtNote1());
        queryWrapper.likeRight(IpsSendDeliveryInfoToOpsDO::getOrderNo, "77");
//        queryWrapper.orderByDesc(IpsSendDeliveryInfoToOpsDO::getId);
        List<IpsSendDeliveryInfoToOpsDO> ipsSendDeliveryInfoToOpsDOList = ipsSendDeliveryInfoToOpsMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(ipsSendDeliveryInfoToOpsDOList)) {
            return ResultVo.success(new ArrayList<>());
        }
        List<IpsSendDeliveryInfoToOpsDto> ipsSendDeliveryInfoToOpsDtos = BeanCopyUtil.copyList(ipsSendDeliveryInfoToOpsDOList, IpsSendDeliveryInfoToOpsDto.class);


        /**
         * 记录最大ID
         */
        Long id = ipsSendDeliveryInfoToOpsDOList.get(ipsSendDeliveryInfoToOpsDOList.size() - 1).getId();
        dictCommonService.updateExtNote1(dataCodesInfo.getId(),String.valueOf(id),dataCodesInfo.getExtNote1());


        return ResultVo.success(ipsSendDeliveryInfoToOpsDtos);
    }
}
