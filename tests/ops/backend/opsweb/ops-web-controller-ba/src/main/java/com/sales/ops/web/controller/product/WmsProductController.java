package com.sales.ops.web.controller.product;


import com.sales.ops.db.entity.*;
import com.sales.ops.dto.product.ProductBeanDTO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.ResultCode;
import com.sales.ops.service.product.OPSProductService;
import com.sales.ops.service.product.WmsProductService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：wms 产品
 * @date ：Created in 2021/10/7 13:24
 */
@CrossOrigin
@RestController
@RequestMapping("/wms/product/")
public class WmsProductController {

    @Autowired
    private WmsProductService wmsProductService;

    @RequestMapping("/getBomByLimit")
    public CommonResult<List<ProductBom>> getBomByLimit(String limit) {
        try {
            List<ProductBom> list = wmsProductService.getBomByLimit(limit);
            return CollectionUtils.isEmpty(list) ? CommonResult.failure("无记录") : CommonResult.success(list);
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }
}
