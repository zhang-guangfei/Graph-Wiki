package com.smc.smccloud.model.product;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/10 13:25
 * @Descripton TODO
 */
@Data
public class ProductErrorVO {

    private static final long serialVersionUID = 1L;

    /**
     * 产品型号
     */
    private String ModelNo;

    /**
     * 产品来源（国别代码）
     */
    private String recommendModel;

    /**
     * 是否逻辑删除
     */
    private Boolean isDeleted;

    /**
     * 登陆日期
     */
    private String createdDate;

    /**
     * 创建者
     */
    private String createdUser;

    /**
     * 更新日期
     */
    private String updatedDate;

    /**
     * 更新者
     */
    private String updatedUser;

    private String desc;

    private String source;

    private String id;

}
