package com.smc.smccloud.service;

import cn.hutool.json.JSONArray;
import com.smc.smccloud.core.model.ResultVo.ResultVo;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
public interface SmcOrderToCTCService {

    /**
     * Ctc订单写入，发单后写入
     *
     * @return
     */
    ResultVo<String> insertToCtc(JSONArray jsonArray);

    /**
     * CTC订单状态更新，已完成/删除时，更新ctc订单状态
     * @return
     */
    ResultVo<String> updateFinish(String orderno, Date date);

    /**
     * CTC订单状态更新，已完成/删除时，更新ctc订单状态
     * @return
     */
    void updateDel(List<String> list);

}
