package com.smc.smccloud.service;

import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.service.hystrix.OrganServiceApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 发布时请去掉
 * url= "10.116.192.236:8100"
 */
@FeignClient(name = "auth-service", contextId = "organ",fallbackFactory = OrganServiceApiHystrix.class)
public interface OrganServiceApi {

    @RequestMapping(value = "/api/organ/findDeptsToTreeNode", method = RequestMethod.GET)
     List<DeptTreeNode> findDeptsToTreeNode();

}
