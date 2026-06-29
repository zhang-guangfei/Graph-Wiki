package com.smc.smccloud.service.hystrix;

import com.smc.smccloud.Model.DeptTreeNode;
import com.smc.smccloud.service.OrganServiceApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganServiceApiHystrix implements FallbackFactory<OrganServiceApi> {
    @Override
    public OrganServiceApi create(Throwable throwable) {
        return new OrganServiceApi() {
            @Override
            public List<DeptTreeNode> findDeptsToTreeNode() {
                return null;
            }
        };

    }
}
