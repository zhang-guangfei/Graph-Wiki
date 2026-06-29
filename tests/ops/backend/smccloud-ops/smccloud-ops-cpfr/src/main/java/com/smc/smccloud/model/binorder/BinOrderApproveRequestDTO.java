package com.smc.smccloud.model.binorder;

import lombok.Data;

/**
 * @Author edp02 @Date 2021/11/13 10:48
 */
@Data
public class BinOrderApproveRequestDTO {

    private Long[] appIds;

    private Boolean pass;

    private String approveText;

}
