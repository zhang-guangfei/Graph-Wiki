package com.sales.ops.service.invoice;

import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.po.invoice.ErrorTypeDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorParamDto;
import com.sales.ops.dto.util.PageModel;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/2/4 13:33
 */
public abstract class InvoiceErrorService {
    public abstract PageInfo<InvoiceErrorDto> getInvoiceErrorListForPage(PageModel<InvoiceErrorParamDto> pageModel) throws OpsException;

    public abstract List<InvoiceErrorDto> getInvoiceErrorList(InvoiceErrorParamDto param) throws OpsException;

    public abstract List<ErrorTypeDto> getInvoiceCheckRule() throws OpsException;
}
