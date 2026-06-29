package com.smc.smccloud.controller.rpc;

import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.core.utils.PublicUtil;
import com.smc.smccloud.service.OrganServiceApi;
import com.smc.smccloud.service.employeeAndOrgan.OrganService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrganServiceClient implements OrganServiceApi {
    @Resource
    private OrganService organService;

    @Override
    public List<DeptTreeNode> findDeptsToTreeNode() {

        List<DeptTreeNode> allDeptToTree = organService.findAllDeptToTree();
        if (PublicUtil.isEmpty(allDeptToTree)) {
            return null;
        }
        return allDeptToTree;
    }
}
