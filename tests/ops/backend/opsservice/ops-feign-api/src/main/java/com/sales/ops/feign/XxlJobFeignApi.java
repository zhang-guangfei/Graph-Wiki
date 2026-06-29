package com.sales.ops.feign;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ReturnXxlJobResult;
import com.sales.ops.feign.config.XxlJobApiConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：调用xxljob接口用
 * @date ：Created in 2021/11/26 16:54
 */
@FeignClient(name = "ops-job-admin",path = "ops-job-admin" , contextId="ops-job-admin",configuration = XxlJobApiConfig.class)
public interface XxlJobFeignApi {

    /**
     *
     * 通过id看状态 id=19 订单执行 content=1 是开启状态 0 是停止状态
    * @return
     */
    @RequestMapping(value = "/jobinfo/status",method = RequestMethod.GET)
    ReturnXxlJobResult getStatusById(@RequestParam("id") int id);
    //通过id开始任务
    @RequestMapping(value = "/jobinfo/start",method = RequestMethod.GET)
    ReturnXxlJobResult startById(@RequestParam("id") int id);
    //通过id停止任务
    @RequestMapping(value = "/jobinfo/stop",method = RequestMethod.GET)
    ReturnXxlJobResult stopById(@RequestParam("id") int id);


}
