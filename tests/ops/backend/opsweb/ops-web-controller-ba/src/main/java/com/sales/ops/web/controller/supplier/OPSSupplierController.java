package com.sales.ops.web.controller.supplier;

import com.sales.ops.db.entity.Supplier;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.supplier.OPSSupplierService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/12/21 15:06
 */
@CrossOrigin
@RestController
@RequestMapping("/supplier")
public class OPSSupplierController {

    @Autowired
    private OPSSupplierService opsSupplierService;


    @RequestMapping("/info")
    public CommonResult<Supplier> getSupplierInfo(@RequestParam String id) {

        try {
            Supplier result = opsSupplierService.findSupplierInfoById(id);
            return Objects.isNull(result) ? CommonResult.failure("无记录") : CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }

    /**
     * 刷新缓存
     * @param mi 分钟
     * @return
     */
    @RequestMapping("/refresh")
    public CommonResult<List<String>> refreshSupplierData(@RequestParam String mi) {
        try {
            List list = opsSupplierService.refreshSupplierData(mi);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
}
