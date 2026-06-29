package com.sales.ops.web.controller;

import com.sales.ops.dto.order.ExpressInfoDto;
import com.sales.ops.service.tms.ExpressInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author C12961
 * @date 2023/1/3 9:24
 */
@Slf4j
@CrossOrigin
@RestController
public class ExpressInfoController {

    @Autowired
    private ExpressInfoService expressInfoService;

    @GetMapping("tms/expressByNo")
    public ExpressInfoDto findExpressByCourierNo(@RequestParam String expressNo, @RequestParam String type) {
        //大田物流做转换
        if ("DTW".equals(type)) {
            type = "DTWL";
        }
        ExpressInfoDto result = expressInfoService.getExpressInfo(expressNo, type);
        if (result != null && "OK".equals(result.getCode())) {
            return result;
        } else {
            return null;
        }
    }

}
