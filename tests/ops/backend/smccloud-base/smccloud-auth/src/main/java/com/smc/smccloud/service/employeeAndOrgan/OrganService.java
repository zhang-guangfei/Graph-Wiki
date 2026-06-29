package com.smc.smccloud.service.employeeAndOrgan;

import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.model.Organ.DeptDictDTO;
import com.smc.smccloud.model.authority.OrgPositionCondition;
import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.TreeInfo;

import java.util.List;

public interface OrganService
{
    /**
     * 获取所有组织机构
     * @return
     */
    public List<TreeInfo> findAllOrganizations();

    /**
     * 将部门转为tree
     * @return
     */
    List<DeptTreeNode> findAllDeptToTree();

    List<DeptTreeNode> findDeptByParentIdsToTree(String ids);

    /**
     * 获取岗位信息
     */
    List<SaleOrgPositionBean> queryOrgPosition(OrgPositionCondition condition);

    List<String> listOrganIdByPid(String ids);

    List<TreeInfo> findAfterFiltrationByBusinessOffice();
    List<TreeInfo> findAfterFiltrationByBusinessOffice2();

    List<DeptDictDTO> findDeptAsDict();
}
