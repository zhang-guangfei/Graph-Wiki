package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.util.PageModel;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.order.OrderRemindDto;
import com.smc.smccloud.model.order.OrderRemindPromptDto;
import com.smc.smccloud.service.OrderLimitPromptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: $
 * @description： bugid:18113
 *  1.excel导出
 *  2.页面查询
 *  3.查询缓冲期天数
 *  4.更新缓冲期天数
 *
 * @date ：Created in 2025/7/14 13:38
 */

@RestController
@Slf4j
@RequestMapping("/order/limitPrompt")
public class OrderLimitPromptController {

    @Autowired
    private OrderLimitPromptService orderLimitPromptService;

    /**
     * 1.excel 导出
     * @param condition
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ResultVo<List<OrderRemindDto>> exportExcelData(@RequestBody OrderRemindDto condition){
        try {
            return orderLimitPromptService.exportExcelData(condition);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 2.页面查询
     * @param pageModel
     * @return
     * @throws OpsException
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResultVo<PageInfo<OrderRemindDto>> searchStockTransferPlanByPage(@RequestBody PageModel<OrderRemindDto> pageModel) throws OpsException {
        try {
            PageInfo<OrderRemindDto> pageInfo = orderLimitPromptService.searchStockTransferPlanByPage(pageModel);
            return ResultVo.success(pageInfo);
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 3.查询缓冲期天数
     * @return
     */
    @RequestMapping(value = "/find/bufferDays", method = RequestMethod.GET)
    public ResultVo selectBufferDays(){
        try {
            return orderLimitPromptService.selectBufferDays();
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

    /**
     * 4.更新缓冲期天数
     * @param
     * @return
     */
    @RequestMapping(value = "/update/bufferDays", method = RequestMethod.POST)
    public ResultVo updateBufferDays(@RequestBody OrderRemindPromptDto prompt){
        try {
            if(Objects.isNull(prompt)){
                return ResultVo.failure("参数不可为空");
            }
            if(Objects.isNull(prompt.getBufferDays())){
                return ResultVo.failure("参数不可为空");
            }
            return orderLimitPromptService.updateBufferDays(prompt.getBufferDays().toString());
        } catch (Exception e) {
            return ResultVo.failure(e.getMessage());
        }
    }

}
