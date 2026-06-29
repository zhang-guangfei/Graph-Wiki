package com.smc.ops.delivery.service.branch;

import com.smc.ops.delivery.model.branch.BranchInvTransDo;
import com.smc.ops.delivery.model.branch.BranchSumDo;
import com.smc.ops.delivery.model.branch.ExpdetailInfoDo;
import com.smc.ops.delivery.model.branch.ExpdetailPropertyDo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/12/29 11:02
 */
public interface BranchService {
    void handleBranch();

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<ExpdetailInfoDo> queryExpdetailInfo();

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    List<BranchSumDo> queryBranchSumDo(String modelNo);

    @Transactional(rollbackFor = Exception.class)
    void insertBranchInvTrans(BranchInvTransDo obj);

    @Transactional(rollbackFor = Exception.class)
    void insertExpdetailProperty(ExpdetailPropertyDo obj);

    @Transactional(rollbackFor = Exception.class)
    void updateExp(Integer branchFlag, Long id);
}
