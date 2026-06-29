package com.smc.smccloud.model.orderlog;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

/**
 * @Author lyc
 * @Date 2021/12/24 16:34
 * @Descripton TODO
 */
@Data
public class OrderLogRequest {

    private String orderNo;


    private Page page;

}
