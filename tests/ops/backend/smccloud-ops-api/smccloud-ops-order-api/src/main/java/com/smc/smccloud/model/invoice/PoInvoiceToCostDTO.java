package com.smc.smccloud.model.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/12/23 15:59
 */
@Data
public class PoInvoiceToCostDTO {

    private String[] invoiceNos;

    private Long[] invoiceIds;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date costDate;

    private List<String> overseaNoList;

    private List<String> orderNolist;
}
