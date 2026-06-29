package com.smc.smccloud.model.csstock;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CsApplyDTO {
    @NotNull
    private  CsApplyVO master;

    @Valid
    @NotEmpty
    private List<CsApplyDetailVO> items;

    /**
     * SMC提案-门户必须从保存时就提交给门户，正式审批通过重新提交，取消时也要提交
     * 1-门户保存时间提交
     * 2-正式申请时提交
     * 9-取消申请
     */
    private  Integer applyStatus;

    /**
     * 是否变更备库设置数量
     * 是必须设置项的initTotalQty和initUnitQty
     */
    @NotNull
    private  Boolean isChangeInitQty;

    // 重点项目标识 true 重点项目 false 非重点项目
    private Boolean vipProjectFlag;
}
