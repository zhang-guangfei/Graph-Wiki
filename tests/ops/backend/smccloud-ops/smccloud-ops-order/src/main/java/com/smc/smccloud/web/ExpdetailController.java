package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.expdetail.ExpdetailRequest;
import com.smc.smccloud.model.expdetail.ExpdetailVO;
import com.smc.smccloud.service.ExpdetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lyc
 * @Date 2022/8/25 14:04
 * @Descripton TODO
 */

@RestController
@Slf4j
@RequestMapping("/order/expdetail")
public class ExpdetailController {

    @Resource
    private ExpdetailService expdetailService;

    /**
     * 发货查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/listExpdetail", method = RequestMethod.POST)
    public ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomerView(@RequestBody ExpdetailRequest request) {
        return expdetailService.listExpdetailWithCustomer(request);
    }






//    /**
//     * 发货查询
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/listExpdetail", method = RequestMethod.POST)
//    public ResultVo<PageInfo<ExpdetailVO>> listExpdetailWithCustomerView(@RequestBody ExpdetailRequest request) {
//        return expdetailService.listExpdetail(request);
//    }


}
