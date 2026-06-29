package com.smc.smccloud.model.BuInterface;

import lombok.Data;

import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-25 14:41
 * Description:
 */
@Data
public class BuInvoiceVO {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 总条数
     */
    private Integer totalSize;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 发票数据
     */
    private List<BuInvoice> content;
}
