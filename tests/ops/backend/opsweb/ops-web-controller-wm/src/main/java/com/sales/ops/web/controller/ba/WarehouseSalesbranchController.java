package com.sales.ops.web.controller.ba;


import com.github.pagehelper.PageInfo;
import com.sales.ops.db.entity.OpsWarehouseSalesbranchConfig;
import com.sales.ops.dto.query.WarehouseSalesbranchQO;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.dto.util.UserDto;
import com.sales.ops.service.ba.OpsWarehouseSalesbranchService;
import com.sales.ops.webutil.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/warehouseManage/house_branch")
public class WarehouseSalesbranchController extends BaseController {

    @Resource
    private OpsWarehouseSalesbranchService opsWarehouseSalesbranchService;

    @GetMapping(value = "/hello")
    public String Hello() {
        return "Hello House_Brach!!";
    }


    @GetMapping(value = "/list")
    public CommonResult getAll() {
        List<OpsWarehouseSalesbranchConfig> list = opsWarehouseSalesbranchService.findAll();
        CommonResult commonResult = list.isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(list);
        return commonResult;
    }

    @PostMapping(value = "/search")
    public CommonResult getByExample(@RequestBody PageModel<WarehouseSalesbranchQO> pageModel) {
        PageInfo<OpsWarehouseSalesbranchConfig> result = opsWarehouseSalesbranchService.findByPage(pageModel);
        CommonResult commonResult = result.getList().isEmpty() ?
                CommonResult.success("没有记录") : CommonResult.success(result);
        return commonResult;
    }

    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpsWarehouseSalesbranchConfig record) {
        UserDto userDto = getUserDto();
        if (!Objects.isNull(userDto)) {
            record.setCreator(userDto.getUserName());
        } else {
            record.setCreator(UserDto.ADMIN.getUserName());
        }
        record.setCreTime(new Date());
        Integer result = opsWarehouseSalesbranchService.add(record);
        CommonResult commonResult = result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",obj:" + record) :
                CommonResult.success("result:" + result + ",obj:" + record);
        return commonResult;
    }

    @PostMapping(value = "update")
    public CommonResult update(@RequestBody OpsWarehouseSalesbranchConfig record) {
        UserDto userDto = getUserDto();
        if (!Objects.isNull(userDto)) {
           record.setModifier(userDto.getUserName());
        } else {
            record.setModifier(UserDto.ADMIN.getUserName());
        }
        record.setModifyTime(new Date());
        Integer result = opsWarehouseSalesbranchService.modify(record);
        CommonResult commonResult = result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",id:" + record.getWarehouseCode()) :
                CommonResult.success("result:" + result + ",id:" + record.getWarehouseCode());
        return commonResult;
    }

    @GetMapping(value = "delete/{id}")
    public CommonResult deleteById(@PathVariable Integer id) {
        List list = new ArrayList();
        list.add(id);
        Integer result = opsWarehouseSalesbranchService.removeList(list);
        CommonResult commonResult = result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",id:" + id) :
                CommonResult.success("result:" + result + ",id:" + id);
        return commonResult;
    }

    @PostMapping(value = "deletes")
    public CommonResult deleteByCodes(@RequestBody List<Integer> list) {
        Integer result = opsWarehouseSalesbranchService.removeList(list);
        CommonResult commonResult = result.equals(0) || result == null ?
                CommonResult.failure("result:" + result + ",id:" + list) :
                CommonResult.success("result:" + result + ",id:" + list);
        return commonResult;
    }

}
