package com.smc.smccloud.web.Controller;


import com.github.pagehelper.PageInfo;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.Page;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.Department.DepartmentDTO;
import com.smc.smccloud.model.Department.DepartmentVO;
import com.smc.smccloud.model.DepartmentDO;
import com.smc.smccloud.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/common/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @PostMapping(value = "/findDepartmentData")
    public PageInfo<DepartmentDO> findDepartmentData(@RequestBody DepartmentDTO info, Page page) {
        return departmentService.findDepartmentData(info, page);
    }

    /**
     * 添加或根据ID修改部门信息
     *
     * @param info 部门信息
     * @return string
     */
    @PostMapping(value = "/saveDepartmentInfo")
    public ResultVo<String> saveOrUpdateDeptInfo(@RequestBody DepartmentVO info) {
        return departmentService.saveOrUpdateDeptInfo(info);
    }

    @PostMapping(value = "/deleteDeptInfoById")
    public ResultVo<String> deleteDeptInfoById(@RequestParam(value = "id") Integer id) {
        return departmentService.deleteDeptInfoById(id);
    }

    @PostMapping(value = "/findDepartments")
    public ResultVo<List<DataCodeVO>> findDepartments() {
        return departmentService.findDepartments();
    }

}
