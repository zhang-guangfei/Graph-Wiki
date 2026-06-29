package com.smc.smccloud.model.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2022-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductEosVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据来源（JP:日本,CN:制造,YW:业务,ZL:战略）
     */
    private String dataSource;

    /**
     * 开始警告日期
     */
    private String warningDate;

    /**
     * 收敛开始日期，停止接单日期
     */
    private String eosDate;

    /**
     * 推荐型号
     */
    private String modelnoRecommend;

    /**
     * 优先级
     */
    private String priorityLevel;

    private String stopDate;

    private String ModelNo;

    private String eosType;

    private String updateTime;

    private String describe;


}
