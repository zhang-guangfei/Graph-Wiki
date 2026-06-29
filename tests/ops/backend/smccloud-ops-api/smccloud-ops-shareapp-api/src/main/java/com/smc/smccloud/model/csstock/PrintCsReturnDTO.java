package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author wsf
 * @version 1.0
 * @date 2022/2/10 10:48
 */
@Data
public class PrintCsReturnDTO {
    private String applyId;
    private String  returnCompany;
    private Date printTime;
    private String  contactPsn;
    private String  contactTelno;
    private String warehouseName;
    private String adress;
    private String linkman;
    private String smcPhone;
    private List<CsReturnPrintDTO> csReturnData;

}
