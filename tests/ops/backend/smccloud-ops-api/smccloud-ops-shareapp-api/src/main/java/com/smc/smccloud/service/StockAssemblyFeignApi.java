package com.smc.smccloud.service;

import com.github.pagehelper.PageInfo;
import com.sales.ops.dto.inventory.InventoryForAdjustDto;
import com.smc.smccloud.core.config.feign.AuthFeignAutoConfiguration;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.stockassembly.StockAssemblyApplyDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyItemDTO;
import com.smc.smccloud.model.stockassembly.StockAssemblyRequest;
import com.smc.smccloud.model.stockassembly.TransferResult;
import com.smc.smccloud.service.hystrix.StockAssemblyFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Author: B90034
 * Date: 2021-09-27 13:54
 * Description:
 */
@FeignClient(name = "shareapp-service",
        contextId = "stock-assembly",
        configuration = AuthFeignAutoConfiguration.class,
        fallbackFactory = StockAssemblyFeignApiHystrix.class)
public interface StockAssemblyFeignApi {

    /**
     * 创建组换调库申请
     *
     * @param createDto 组换调库申请信息
     * @return 申请Id applyId
     */
    @RequestMapping(value = "/shareapp/stockAssembly/createApply", method = RequestMethod.POST)
    ResultVo<String> createStockAssemblyApply(@RequestBody @Validated StockAssemblyApplyDTO createDto);

    /**
     * 处理门户组换调库申请
     *
     * @param createDto 组换调库申请信息
     * @return string
     */
    @RequestMapping(value = "/shareapp/stockAssembly/handleSMSInStockApply", method = RequestMethod.POST)
    ResultVo<List<TransferResult>> handleSMSInStockApply(@RequestBody @Validated StockAssemblyApplyDTO createDto);

//    /**
//     * 处理门户组换调库申请
//     *
//     * @param createDto 组换调库申请信息
//     * @return string
//     */
//    @RequestMapping(value = "/shareapp/stockAssembly/handleSMSInStockApply", method = RequestMethod.POST)
//    ResultVo<String> handleSMSInStockApply(@RequestBody @Validated StockAssemblyApplyDTO createDto);

    /**
     * 录入组换申请成本数据
     *
     * @return 录入处理结果
     */
    @RequestMapping(value = "/shareapp/stockAssembly/importAssemblyCostData", method = RequestMethod.GET)
    ResultVo<String> importAssemblyCostData();

    /**
     * 更新组换中的申请状态
     *
     * @param applyNo 组换申请号
     * @param result  组换结果 (true-组换成功; false-组换失败)
     * @return string
     */
    @RequestMapping(value = "/shareapp/stockAssembly/updateAssemblyStatus", method = RequestMethod.GET)
    ResultVo<String> updateAssemblyStatus(@RequestParam("applyNo") String applyNo, @RequestParam("result") Boolean result);

    /**
     *   <!--add by WuWeiDong 20240111  bug 12889 组换流程优化 根据申请号查询  -->
     *  根据申请号，申请ID，型号集合 查询非取消明细,
     * @param request
     * @return
     */
    @RequestMapping(value = "/shareapp/stockAssembly/listStockAssemblyApplyDetail", method = RequestMethod.POST)
    ResultVo<PageInfo<StockAssemblyItemDTO>> listStockAssemblyApplyDetail(@RequestBody @Validated  StockAssemblyRequest request);

    @RequestMapping(value = "/shareapp/stockAssembly/getAssemblyInDataForWMS", method = RequestMethod.POST)
    ResultVo<List<InventoryForAdjustDto>> getAssemblyInDataForWMS(@RequestParam("applyNo") String applyNo );
}
