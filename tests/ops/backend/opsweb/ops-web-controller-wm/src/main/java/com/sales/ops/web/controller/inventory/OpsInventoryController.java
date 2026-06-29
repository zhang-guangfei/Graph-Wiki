package com.sales.ops.web.controller.inventory;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.InventoryReport;
import com.sales.ops.db.entity.OpsInventoryProperty;
import com.sales.ops.db.entity.OpsInventoryType;
import com.sales.ops.db.extdao.InventoryRiskDao;
import com.sales.ops.dto.inventory.InventoryReportExcel;
import com.sales.ops.dto.query.InventoryQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.inventory.OpsInventoryPropertyService;
import com.sales.ops.service.inventory.OpsInventoryService;
import com.sales.ops.service.inventory.OpsInventoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/warehouseManage/inventory")
public class OpsInventoryController {

    @Resource
    private OpsInventoryService opsInventoryService;
    @Resource
    private OpsInventoryTypeService opsInventoryTypeService;

    @Resource
    private OpsInventoryPropertyService opsInventoryPropertyService;

    @Autowired
    private InventoryRiskDao inventoryRiskDao;

    @GetMapping(value = "/types")
    public CommonResult getTypes() {
        List<OpsInventoryType> types = opsInventoryTypeService.findAllTypes();
        CommonResult commonResult = types.size() == 0 ?
                CommonResult.success("没有记录") : CommonResult.success(types);
        return commonResult;
    }

    @GetMapping(value = "/property")
    public CommonResult getProperty(@RequestParam Long id) {
        OpsInventoryProperty property = opsInventoryPropertyService.findById(id);
        if (ObjectUtil.isNotNull(property)) {
            return CommonResult.success(property);
        }
        return CommonResult.failure();
    }

    @PostMapping(value = "/search")
    public CommonResult search(@RequestBody PageModel<InventoryQO> pageModel) {

        try {
            PageInfo<InventoryReport> result = opsInventoryService.searchByPage(pageModel);
            return result.getList().isEmpty() ?
                    CommonResult.success("没有记录") : CommonResult.success(result);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }


    @PostMapping("/download")
    public void download(@RequestBody InventoryQO condition, HttpServletResponse response) throws IOException, OpsException {
        List<InventoryReportExcel> list = opsInventoryService.download(condition);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("库存查询", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), InventoryReportExcel.class)
                .sheet("库存查询")
                .doWrite(list);
    }

    @GetMapping(value = "/salesInfos/exp")
    public CommonResult findExpireSalesInfo() {
        try {
            List<String> expireSalesInfoList = opsInventoryPropertyService.findExpireSalesInfo();
            return CommonResult.success(expireSalesInfoList);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }
    @GetMapping(value = "/salesInfos/exp/deleted")
    public CommonResult findExpireSalesInfoDeleted(@RequestParam Integer days) {
        try {
            List<String> expireSalesInfoList = opsInventoryPropertyService.findExpireSalesInfoDeleted(days);
            return CommonResult.success(expireSalesInfoList);
        } catch (Exception ex) {
            return CommonResult.failure(ex.getMessage());
        }
    }


}
