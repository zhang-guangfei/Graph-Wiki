package com.smc.smccloud.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.mapper.purchase.SmcOrderToCTCMapper;
import com.smc.smccloud.model.purchase.SMCOrderDO;
import com.smc.smccloud.service.SmcOrderToCTCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@Slf4j
@Service
public class SmcOrderToCTCServiceImpl implements SmcOrderToCTCService {

    @Resource
    private SmcOrderToCTCMapper smcOrderToCTCMapper;


    @Override
    public ResultVo<String> insertToCtc(JSONArray jsonArray) {
        List<SMCOrderDO> list = JSONUtil.toList(jsonArray, SMCOrderDO.class);
        List<SMCOrderDO> temp;
        int i = 0;
        try {
            while (i < list.size()) {
                if (i == 0 && list.size() < 201) {
                    temp = list;
                } else {
                    if (list.size() > (i + 200)) {
                        temp = list.subList(i, i + 200);
                    } else {
                        temp = list.subList(i, list.size());
                    }
                }
                i += temp.size();

                Boolean result = smcOrderToCTCMapper.insertSmcOrder(temp);
                if (!result) {
                    return ResultVo.failure("写入CTC失败");
                }
            }
        } catch (Exception e) {
            log.error("写入CTC失败: {}", e.getMessage(), e);
            return ResultVo.failure("写入CTC失败"+e.getMessage());
        }
        return ResultVo.success("写入CTC成功");
    }

    /**
     * CTC订单状态更新，已完成/删除时，更新ctc订单状态
     *
     * @return
     */
    @Override
    public ResultVo<String> updateFinish(String orderno, Date date) {
        Boolean result = smcOrderToCTCMapper.updateSmcOrderFinish(orderno, date);
        if (result) {
            return ResultVo.success("更新CTC成功");
        }
        return ResultVo.failure("更新CTC失败");
    }

    /**
     * CTC订单状态更新，已完成/删除时，更新ctc订单状态
     *
     * @param list
     * @return
     */
    @Override
    public void updateDel(List<String> list) {
        list.forEach(orderno -> {
            smcOrderToCTCMapper.updateSmcOrderCancel(orderno);
        });
    }
}
