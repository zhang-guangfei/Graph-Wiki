package com.sales.ops.web.controller.ba;

import cn.hutool.core.util.ObjectUtil;
import com.sales.ops.dto.units.ElementUITree;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.service.ba.OpsNationalAreaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author C12961
 * @date 2022/6/7 15:55
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/area")
public class OpsNationalAreaController {


    @Resource
    private OpsNationalAreaService opsNationalAreaService;

    @GetMapping(value = "/tree")
    public CommonResult getAreaTree() {
        Object obj = opsNationalAreaService.getAreaTree();
        return ObjectUtil.isNull(obj) ?
                CommonResult.failure("没有记录") : CommonResult.success(obj);
    }


    @GetMapping(value = "/put")
    public CommonResult createAreaTree() {
        List<ElementUITree> areaTree = Collections.emptyList();
        try {
            areaTree=opsNationalAreaService.createAreaTree();
            return areaTree.size() > 0 ? CommonResult.success(areaTree) : CommonResult.failure("没有记录");
        } catch (Exception e) {
            return CommonResult.failure(e.getMessage());
        }
    }


}
