package com.smc.smccloud.model.invoice;

import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;

import java.util.List;

/**
 * @author wuweidong
 * @create 2022/12/2 10:28
 * @description
 */
@Data
public class PoInvoiceRequest  {
    private static final long serialVersionUID = 1L;
    private List<PoInvoiceDTO>  poInvoiceDTOS;
    private Integer doType=-1;
    private String endUser;
}
