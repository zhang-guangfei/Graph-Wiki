package com.smc.smccloud.model.csstock;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CsApplyDetailRequest  extends BaseQuery {

    private Long applyId;



}
