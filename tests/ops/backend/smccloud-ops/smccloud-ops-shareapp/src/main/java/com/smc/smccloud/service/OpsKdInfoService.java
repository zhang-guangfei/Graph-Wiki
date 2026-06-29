package com.smc.smccloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.OpsKdInfoDO;
import com.smc.smccloud.model.kd.KdInfoParams;
import com.smc.smccloud.model.orderstate.OrderStateDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smc
 * @since 2024-10-22
 */
public interface OpsKdInfoService extends IService<OpsKdInfoDO> {


    ResultVo<PageInfo<OpsKdInfoDO>> listKdInfos(KdInfoParams params);

    void exportKdInfoData(KdInfoParams params);
}
