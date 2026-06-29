package com.smc.smccloud.model.customer;

import com.smc.smccloud.core.model.page.BaseQuery;
import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/12/6 16:54
 * @Descripton TODO
 */
@Data
public class CustomerWldateRequest {

    private String customerNo;

    private Page page;

    // private List<String> customerNoList;
}
