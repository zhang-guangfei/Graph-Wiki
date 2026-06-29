package com.smc.smccloud.web.rpc;

import com.sales.ops.enums.InventoryTypeEnum;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.log.annotation.SysLog;
import com.smc.smccloud.model.stock.AdapterPreStockDTO;
import com.smc.smccloud.model.stock.InventoryCondition;
import com.smc.smccloud.model.stock.InventoryListDto;
import com.smc.smccloud.service.InventoryService;
import com.smc.smccloud.service.SMSStockServiceFeignApi;
import com.smc.smccloud.service.StockService;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: B90034
 * Date: 2022-07-12 18:25
 * Description:
 */
@Slf4j
@RestController
public class SMSStockServiceFeignClient implements SMSStockServiceFeignApi {

    @Resource
    private StockService stockService;
    @Resource
    private InventoryService inventoryService;


    @Override
    public ResultVo<Boolean> sendPreStockDetailStatusMessage(AdapterPreStockDTO dto) {
        return stockService.sendPreStockDetailStatusMessage(dto);
    }

    @SysLog("门户库存查询")
    @RequestMapping(value = "/adapter/inventory/find", method = RequestMethod.POST)
    public ResultVo<List<InventoryListDto>> findInventory(@RequestBody InventoryCondition condition) {
        try {
            if (condition.getModelNoList() == null) {
                condition.setModelNoList(new ArrayList<>());
            }
            if (!Collections.isEmpty(condition.getModelNoList()) && condition.getModelNoList().size() > 500) {
                return ResultVo.success(null, "只允许单次查询最多500个型号");
            }
            //如果有型号
            else {
                //去掉空型号
                List<String> collect = condition.getModelNoList().stream().filter(item -> StringUtils.isNotBlank(item)).collect(Collectors.toList());
                condition.setModelNoList(collect);
            }
            //如果无型号
            if (Collections.isEmpty(condition.getModelNoList())) {
                if (!condition.hasCustomerNo()) {
                    return ResultVo.success(null, "型号和客户代码至少输入一项");
                }
                //如果有客户代码且 库存类型不是GK-TY,GK-PPL,GK-PJ和空这四种，就不查询了
                //直接返回提示信息：请输入该客户的库存类型GK-TY,GK-PPL,GK-PJ
                if (condition.hasCustomerNo()) {
                    List<String> invTypeArr = Arrays.asList(InventoryTypeEnum.GKTY.getType(), InventoryTypeEnum.GKPPL.getType(), InventoryTypeEnum.GKPJ.getType());
                    if (StringUtils.isNotBlank(condition.getInventoryType()) && !invTypeArr.contains(condition.getInventoryType())) {
                        return ResultVo.success(null, "请输入该客户的库存类型GK-TY,GK-PPL,GK-PJ");
                    }
                }
            }
            List<InventoryListDto> dtoList = inventoryService.searchInventory(condition);
            ResultVo<List<InventoryListDto>> resultVo = ResultVo.success(dtoList);
            //log.info(JSONUtil.toJsonPrettyStr(resultVo));
            return resultVo;
        } catch (NullPointerException ex) {
            log.error("空指针异常：", ex);
            return ResultVo.failure("空指针异常：" + StringUtils.substring(ex.getStackTrace().toString(), 0, 1000));
        } catch (Exception e) {
            log.error("", e);
            return ResultVo.failure(e.getMessage());
        }
    }

    @RequestMapping(value = "/adapter/inventory/find/random", method = RequestMethod.GET)
    public ResultVo<List<InventoryListDto>> findInventory(@RequestParam(defaultValue = "500") Integer num, @RequestParam(required = false) String customerNo) {
        try {
            InventoryCondition condition = new InventoryCondition();
            List<String> modelnoList = inventoryService.randomModelno(num);
            condition.setModelNoList(modelnoList);
            condition.setCustomerNo(customerNo);
            List<InventoryListDto> dtoList = inventoryService.searchInventory(condition);
            ResultVo<List<InventoryListDto>> resultVo = ResultVo.success(dtoList);
            return resultVo;
        } catch (Exception e) {
            log.error("", e);
            return ResultVo.failure(e.getMessage());
        }
    }

}
