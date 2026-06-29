package com.smc.ops.delivery.service.branch;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/3 14:53
 */
public interface BranchZHService {
    void handleBranch();

    //queryDelivyerCheckMoreCompany
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    Boolean queryDelivyerCheckMoreCompany(Long applyId);
}
