package com.smc.smccloud.controller;

import com.smc.smccloud.Model.DeptParam;
import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.model.Organ.DeptDictDTO;
import com.smc.smccloud.model.authority.OrgPositionCondition;
import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.TreeInfo;
import com.smc.smccloud.service.employeeAndOrgan.OrganService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/organ")
public class OrganController
{
    @Resource
    private OrganService organService;

    @RequestMapping(value = "/findDepts", method = RequestMethod.GET)
    public List<TreeInfo> findDepts() {
        return organService.findAllOrganizations();
    }

    /**
     *  tree for deptData
     * @return
     */
    @RequestMapping(value = "/findDeptsToTree", method = RequestMethod.GET)
    public ResultVo<List<DeptTreeNode>> findDeptsToTree() {
        List<DeptTreeNode> allDeptToTree = organService.findAllDeptToTree();
        if (PublicUtil.isEmpty(allDeptToTree)) {
            return ResultVo.failure("暂无数据！");
        }
        return ResultVo.success(allDeptToTree);
    }

    @RequestMapping(value = "/findDeptByParentIdsToTrees", method = RequestMethod.POST)
    public ResultVo<List<DeptTreeNode>> findDeptByParentIdsToTree(@RequestBody DeptParam deptParam) {
        if (PublicUtil.isEmpty(deptParam)) {
            return ResultVo.failure("暂无数据！");
        }
        List<DeptTreeNode> allDeptToTree = organService.findDeptByParentIdsToTree(deptParam.getIds());
        if (PublicUtil.isEmpty(allDeptToTree)) {
            return ResultVo.failure("暂无数据！");
        }
        return ResultVo.success(allDeptToTree);
    }

    @RequestMapping(value = "/listOrganIdByPid", method = RequestMethod.POST)
    public List<String> listOrganIdByPid(@RequestBody DeptParam deptParam) {
       return organService.listOrganIdByPid(deptParam.getIds());
    }



    /**
     * findUserByPostilName:(通过职级名称获取人员信息)
     * @return
     */
    @RequestMapping(value = "/queryOrgPosition")
    public List<SaleOrgPositionBean> queryOrgPosition(OrgPositionCondition condition) {
        return organService.queryOrgPosition(condition);
    }


    /**
     * @description 北京用于前端查询营业所树状菜单
     * @author C12961
     * @date 2022/3/16 8:57
     */
//    @RequestMapping("/departInfo")
//    public List<TreeInfo> findAfterFiltrationByBusinessOffice() {
//        return organService.findAfterFiltrationByBusinessOffice();
//    }


    /**
     * @description 北京用于前端查询营业所树状菜单2
     * @author C18023
     * @date 2022/5/15 12:57
     */
    @RequestMapping("/departInfo")
    public List<TreeInfo> findAfterFiltrationByBusinessOffice2() {
        return organService.findAfterFiltrationByBusinessOffice2();
    }

    /**
     * @description 北京用于前端查询营业所字典
     * @author C12961
     * @date 2022/3/16 8:57
     */
    @GetMapping("/departDict")
    public List<DeptDictDTO> getDeptDict() {
        return organService.findDeptAsDict();
    }


}
