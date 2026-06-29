package com.smc.smccloud.web;

import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.SpecOrder.SpecOrderDO;
import com.smc.smccloud.model.specorder.SpecOrderDTO;
import com.smc.smccloud.model.specorder.SpecOrderVO;
import com.smc.smccloud.service.SpecOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order/sepcorder")
public class SpecOrderController {

    @Resource
    private SpecOrderService specOrderService;


    @PostMapping("/saveSpecOrder")
    public ResultVo<SpecOrderDTO> saveSpecOrder(@RequestBody SpecOrderDTO dto){
        if (PublicUtil.isEmpty(dto.getOrderType())){
            return ResultVo.failure("请选择订单类型");
        }
        if (dto.getOrderType() == 11 || dto.getOrderType() == 12 || "1".equals(dto.getDlvEntire())) {
            if (PublicUtil.isEmpty(dto.getReceiverAddress()) || PublicUtil.isEmpty(dto.getReceiverCompany())
                    || PublicUtil.isEmpty(dto.getReceiverName()) || PublicUtil.isEmpty(dto.getReceiverPhone())){
                return ResultVo.failure("请填写收货信息！");
            }
        }

        return specOrderService.saveSpecOrder(dto);
    }

    @PostMapping("/listSpecOrder")
    public ResultVo<PageInfo<SpecOrderVO>> listSpecOrder(@RequestBody SpecOrderVO specOrderVO, Page page){

        return ResultVo.success(specOrderService.listSpecOrder(specOrderVO,page));
    }
    @PostMapping("/exportListSpecOrder")
    public void exportListSpecOrder(@RequestBody SpecOrderVO specOrderVO, HttpServletResponse response){
        specOrderService.exportListSpecOrder(specOrderVO,response);
    }

    @PostMapping("/addSpecOrder")
    public ResultVo<String> createSpecOrder(@RequestBody SpecOrderVO specOrderVO){

        return specOrderService.createSpecOrder(specOrderVO);
    }

    @PostMapping("/generateSpecOrder")
    public ResultVo<String> generateSpecOrder(@RequestBody List<SpecOrderVO> specOrderVOS){

        return specOrderService.generateSpecOrder(specOrderVOS);
    }

    @PostMapping("/findSpecOrder")
    public ResultVo<List<SpecOrderDO>> findSpecOrder(@RequestParam(value = "groupNo") String groupNo){

        return specOrderService.findSpecOrder(groupNo);
    }

    @PostMapping("/deleteSpecOrder")
    public ResultVo<String> deleteSpecOrder(@RequestParam(value = "id") Integer id){
        if (id == null){
            return ResultVo.failure("请选择要删除的数据~");
        }

        return specOrderService.deleteSpecOrder(id);
    }

//    @RequestMapping(value = "/exportSpecOrderExpDetailToExcel", method = RequestMethod.POST)
//    public void exportSpecOrderExpDetailToExcel() {
//        specOrderService.exportSpecOrderExpDetailToExcel();
//    }


}
