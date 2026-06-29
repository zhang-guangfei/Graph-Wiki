package com.smc.smccloud.model.invoice;

import cn.hutool.db.DaoTemplate;
import lombok.Data;

import java.util.Date;

@Data
public class ImpInvoiceToCostRequest {
    private  Long[] ids;
    private Date costDate;
}
