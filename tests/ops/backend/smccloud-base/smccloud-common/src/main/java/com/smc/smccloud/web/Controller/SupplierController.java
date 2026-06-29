package com.smc.smccloud.web.Controller;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.supplier.SupplierVo;
import com.smc.smccloud.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/6/9 10:09
 * @Descripton TODO
 */

@Slf4j
@RestController
@RequestMapping(value = "/common/supplier")
public class SupplierController {
    @Resource
    private SupplierService supplierService;

    @PostMapping(value = "/addSupplier")
    public ResultVo<String> addSupplier(@RequestBody SupplierVo supplierVo) {
        return supplierService.addSupplier(supplierVo);
    }

    @PostMapping(value = "/editSupplier")
    public ResultVo<String> editSupplier(@RequestBody SupplierVo supplierVo) {
        return supplierService.editSupplier(supplierVo);
    }

    @PostMapping(value = "/deleteSupplierById")
    public ResultVo<String> deleteSupplierById(@RequestBody List<String> id) {
        return supplierService.deleteSupplierById(id);
    }

}
