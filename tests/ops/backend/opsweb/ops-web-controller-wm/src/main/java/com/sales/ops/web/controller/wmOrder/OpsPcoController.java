package com.sales.ops.web.controller.wmOrder;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.OpsPco;
import com.sales.ops.db.entity.OpsPcoItem;
import com.sales.ops.dto.inventory.WmPCOConfirmDto;
import com.sales.ops.dto.order.OpsPcoAddItemDto;
import com.sales.ops.dto.order.OpsWarehousePcoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.redisson.OpsRedissonLock;
import com.sales.ops.service.wmOrder.OpsPcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：加工指令
 * @date ：Created in 2021/9/28 12:05
 */
@CrossOrigin
@RestController
@RequestMapping("/warehouseManage/pco")
public class OpsPcoController {
    @Autowired
    private OpsPcoService opsPcoService;

    @Autowired
    private OpsRedissonLock opsRedissonLock;

    /**
     * 条件查询
     * @param pageModel
     * @return
     */
    @PostMapping("/search")
    public CommonResult search(@RequestBody PageModel<OpsWarehousePcoDto> pageModel){
        PageInfo<OpsPco> result= opsPcoService.findByExample(pageModel);
        return CollectionUtils.isEmpty(result.getList()) ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }

    /**
     * 查询明细
     * @param id
     * @return
     */
    @PostMapping("/search_item")
    public CommonResult searchItem(@RequestBody String id){
        List<OpsPcoItem> result= opsPcoService.selectItemBypcoId(id);
        return CollectionUtils.isEmpty(result) ?
                CommonResult.success("没有记录") : CommonResult.success(result);
    }


    /**
     * ops提交wmsPco和Pcoitem
     * @param pcoId
     * @return
     */
    @PostMapping("/findPcoToWms")
    public CommonResult<OpsPcoAddItemDto> findPcoToWms(@RequestBody String pcoId){
        OpsPcoAddItemDto result = opsPcoService.findPcoToWms(pcoId);
        return result == null ?
                CommonResult.failure( "没有记录") : CommonResult.success(result);
    }

    /**
     * 5.6 组装去人回传 富勒
     * @param param
     * @return
     */
    @PostMapping("/wmPCOConfirm")
    public CommonResult wmsPCOConfirm(@RequestBody WmPCOConfirmDto param) {
        Boolean lock = opsRedissonLock.addLock("ops:wmPCOConfirm:"+param.getPcoOrderCode());

        try {
            opsPcoService.wmPCOConfirm(param);
            return CommonResult.success("成功");
        } catch (OpsException e) {
            return CommonResult.failure(e);
        } finally {
            opsRedissonLock.releaseLock("ops:wmPCOConfirm:"+param.getPcoOrderCode());

        }
    }
}
