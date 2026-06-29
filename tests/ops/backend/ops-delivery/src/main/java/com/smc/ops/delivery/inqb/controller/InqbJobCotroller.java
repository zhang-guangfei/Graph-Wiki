package com.smc.ops.delivery.inqb.controller;

import com.smc.ops.delivery.inqb.service.InqbJobHandleService;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dxf
 * INQB相关的定时任务接口
 */

@RestController
@CrossOrigin
@RequestMapping("/inqb/job")
public class InqbJobCotroller {

    @Resource
    private InqbJobHandleService inqbJobHandleService;

    /**
     * 刷新INQ-B的有效状态
     * 整单是否可用；当该INQB整单在有效期内且有子项未被全数使用时，整单状态判定为可用，反之不可用（即当当前日期已大于INQB整单有效截止日期或者在有效期内所有子项均已全数量被使用时INQB整单）。
     * 当前日期与INQB整单有效截止日期的比较
     */
    @RequestMapping(value = "/refreshValidity", method = RequestMethod.POST)
    public ResultVo<String> refreshValidity() {
        try {
            return inqbJobHandleService.updateInqbValidity();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 获取催促供应商的回复信息
     * @return
     */
    @RequestMapping(value = "/inqbReply", method = RequestMethod.POST)
    public ResultVo<String> inqbReply() {
        try {
            return inqbJobHandleService.getInqbGroupReply();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 回调task表信息，回传门户的信息
     * @return
     */
    @RequestMapping(value = "/inqbCallbackSalesToTask", method = RequestMethod.POST)
    public ResultVo<String> inqbCallbackSalesToTask(@RequestBody List<String> applynoList) {
        try {
            return inqbJobHandleService.callbackSalesToTask(applynoList);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

}
