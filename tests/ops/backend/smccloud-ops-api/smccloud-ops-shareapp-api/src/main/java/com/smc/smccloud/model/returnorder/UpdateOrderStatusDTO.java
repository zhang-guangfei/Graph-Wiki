package com.smc.smccloud.model.returnorder;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/9/20 9:51
 * @Descripton TODO
 */
@Data
public class UpdateOrderStatusDTO {

    // 申请号集合
    private String applyNo;


    // 是否通过审批
    // 审批结果: 1:已通过、2、已否决、3、已作废
    private int applyResult;


}
