package com.smc.smccloud.model.shikomi;

import lombok.Data;

/**
 * @author edp04
 * @title: HrOrganizationDTO
 * @date 2022/06/30 14:38
 */
@Data
public class HrOrganizationDTO {

    private String level;
    private String parentId;
    private String name;
    private String unitCode;
}
