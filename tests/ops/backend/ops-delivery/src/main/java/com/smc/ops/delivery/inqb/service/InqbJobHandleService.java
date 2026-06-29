package com.smc.ops.delivery.inqb.service;

import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.List;

/**
 * 催促处理申请方法
 */
public interface InqbJobHandleService {


    /**
     * 刷新有效状态
     * 整单是否可用；当该INQB整单在有效期内且有子项未被全数使用时，整单状态判定为可用，反之不可用（即当当前日期已大于INQB整单有效截止日期或者在有效期内所有子项均已全数量被使用时INQB整单）。
     * 判断子项是否可用：回复当日开始可用，有效期为回复时间次日起的5个工作日均有效，第5个工作日的23:59:59失效；
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
    ResultVo<String> updateInqbValidity() throws Exception;


    /**
     * INQ-B 从集团采购系统获取回复信息
     * @return
     * @throws Exception
     */
    ResultVo<String> getInqbGroupReply() throws Exception;


    /**
     * 回传门户的task事件
     * @param applynoList
     * @return
     * @throws Exception
     */
    ResultVo<String> callbackSalesToTask(List<String> applynoList) throws Exception;



}
