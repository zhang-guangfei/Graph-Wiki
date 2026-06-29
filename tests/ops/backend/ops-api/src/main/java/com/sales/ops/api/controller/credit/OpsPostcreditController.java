package com.sales.ops.api.controller.credit;

import com.sales.ops.api.service.credit.OpsPostcreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author C02483
 * @version 1.0
 * @description: 信用拦截微服务
 * @date 2022/1/25 14:31
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/OpsPostApiTocredit")
public class OpsPostcreditController {


    @Autowired
    private OpsPostcreditService opsPostcreditService;

    @RequestMapping("/credit")
    Map<String, String> checkCredit(@RequestBody List<String> orderFnoList) throws Exception {
        try {
            return opsPostcreditService.checkCredit(orderFnoList);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
