package com.sales.ops.feign;

import com.alibaba.fastjson.JSONArray;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@FeignClient(name = "shareapp-service", contextId = "ctcsmcorder")
public interface CtcSmcOrderToFeignApi {

    /**
     * 写入CTCsmcOrder
     */
    @RequestMapping(value = "/shareapp/Ctcsmcorder/insertCtc", method = RequestMethod.POST)
    void insertSmcOrderCtc(@RequestBody JSONArray jsonArray);


    /**
     * 更新删除状态
     * @param list
     */
    @RequestMapping(value = "/shareapp/Ctcsmcorder/updatedel", method = RequestMethod.POST)
    void  updateSmcOrderDelCtc(@RequestBody List<String> list);


    /**
     * 更新完成状态
     * @param orderno
     * @param finishdate
     * @return
     */
    @RequestMapping(value = "/shareapp/Ctcsmcorder/updatefinish", method = RequestMethod.POST)
    void  updateSmcOrderFinishCtc(@RequestParam("orderno") String orderno, @RequestParam("finishdate") Date finishdate);


}
