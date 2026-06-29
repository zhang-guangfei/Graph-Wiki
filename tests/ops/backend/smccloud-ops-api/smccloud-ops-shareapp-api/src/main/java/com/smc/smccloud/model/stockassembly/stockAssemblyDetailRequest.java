package com.smc.smccloud.model.stockassembly;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Author: B90034
 * Date: 2022-01-18 15:22
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class stockAssemblyDetailRequest extends BaseQuery {

    /**
     * 申请id
     */
    private String applyNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 申请部门
     */
    private String deptNo;

    /**
     * 申请状态
     */
    private String status;

    /**
     * 申请目的
     */
    private String assembleType;

    /**
     * 仓库代码
     */
    private String warehouseCode;

    /**
     * 申请人
     */
    private String applyPsn;

    /**
     * 时间类型 1-申请时间; 2-审批时间; 3-调库时间; 4-接收时间
     */
    private Integer dateType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;

    private  Boolean isExport=false;
}
