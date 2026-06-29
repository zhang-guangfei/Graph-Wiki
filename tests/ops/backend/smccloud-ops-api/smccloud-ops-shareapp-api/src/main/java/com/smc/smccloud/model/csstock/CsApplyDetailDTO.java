package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.io.Serializable;

/**
 * 委托在库新增子项
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/21 11:34
 */
@Data
public class CsApplyDetailDTO implements Serializable {

    private Long applyId;
    private Long id;
    private String modelNo;
    private Integer quantity;
    private String remark;

}