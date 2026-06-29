package com.smc.smccloud.web;

import cn.hutool.json.JSONArray;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.service.SmcOrderToCTCService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author B91717
 * @date 2022/5/9
 * @apiNote
 */
@RestController
@RequestMapping(value = "/shareapp/Ctcsmcorder")
public class SmcOrderToCTCController {
    @Resource
    private SmcOrderToCTCService smcOrderToCTCService;

    /**
     * 写入CTCsmcOrder
     */
    @PostMapping("/insertCtc")
    public ResultVo<String> insertSmcOrder(@RequestBody JSONArray jsonArray) {
       return smcOrderToCTCService.insertToCtc(jsonArray);
    }

    @PostMapping("/updatedel")
    public void updateSmcOrderDel(@RequestBody List<String> list) {
        smcOrderToCTCService.updateDel(list);
    }

    @PostMapping("/updatefinish")
    public ResultVo<String> updateSmcOrderFinish(@RequestParam String orderno, @RequestParam Date finishdate) {
        return  smcOrderToCTCService.updateFinish(orderno,finishdate);
    }

}
