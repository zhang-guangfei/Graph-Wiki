package com.sales.ops.web.controller.wmOrder;

import com.alibaba.fastjson.JSONArray;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.csstock.CsExpdetailVO;
import com.smc.smccloud.service.ConsignmentStockFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：中转站
 * @date ：Created in 2022/4/12 9:26
 */

@CrossOrigin
@RestController
@RequestMapping("/middle")
@Slf4j
public class OpsMiddleController {

    @Autowired
    private ConsignmentStockFeignApi consignmentStockFeignApi;
    /**
     * 中转站 委托在库下发 避免opsApi直接调用401问题
     */
    @RequestMapping("/shareapp/cs/addExpData")
    public Boolean addExpData( @RequestBody JSONArray csExpDataDOS){
        try {
            List<CsExpdetailVO> list = csExpDataDOS.toJavaList(CsExpdetailVO.class);
            ResultVo<String> resultVo=  consignmentStockFeignApi.addExpData(list);
            if(resultVo.isSuccess()){
                return true;
            }else {
                log.error("返回结果错误："+resultVo.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("{}",e);
            return false;
        }
    }
}
