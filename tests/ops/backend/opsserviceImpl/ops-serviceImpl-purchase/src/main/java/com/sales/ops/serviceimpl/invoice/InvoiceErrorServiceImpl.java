package com.sales.ops.serviceimpl.invoice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.extdao.PurchaseInvoiceDao;
import com.sales.ops.dto.po.invoice.ErrorTypeDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorDto;
import com.sales.ops.dto.po.invoice.InvoiceErrorParamDto;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.service.invoice.InvoiceErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2026/2/4 13:33
 */
@Service
public class InvoiceErrorServiceImpl extends InvoiceErrorService {

    @Autowired
    private PurchaseInvoiceDao purchaseInvoiceDao;

    /**
     * 获取发票错误列表     *
     *
     * @param pageModel
     * @return
     * @throws OpsException
     */
    @Override
    public PageInfo<InvoiceErrorDto> getInvoiceErrorListForPage(PageModel<InvoiceErrorParamDto> pageModel) throws OpsException {
        PageHelper.startPage(pageModel.getPageNumber(), pageModel.getPageSize(),pageModel.getOrderBy());
        return new PageInfo<>(purchaseInvoiceDao.getInvoiceError(pageModel.getCondition()));
    }

    @Override
    public List<InvoiceErrorDto> getInvoiceErrorList(InvoiceErrorParamDto param) throws OpsException {
        return purchaseInvoiceDao.getInvoiceError(param);
    }

    @Override
    public List<ErrorTypeDto> getInvoiceCheckRule() throws OpsException {
        return purchaseInvoiceDao.getInvoiceCheckRule();
    }
}

    /*@Override
    public void exportInvoiceErrorData(InvoiceErrorParamDto invoiceErrorParamDto, HttpServletResponse response) throws OpsException{
        List<InvoiceErrorDto> invoiceError = purchaseInvoiceDao.getInvoiceError(invoiceErrorParamDto);

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("数据导出", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), OpsInquiryApplyExcelVO.class)
                .sheet("数据导出")
                .doWrite(excelList);
    }*/
