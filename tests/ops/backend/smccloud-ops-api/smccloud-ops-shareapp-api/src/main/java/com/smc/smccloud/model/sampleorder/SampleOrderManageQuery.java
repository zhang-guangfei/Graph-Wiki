package com.smc.smccloud.model.sampleorder;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2022/8/2 11:44
 * @Descripton TODO
 */
@Data
public class SampleOrderManageQuery {

    private String orderNo;
    private String modelNo;
    private String shipDateStart;
    private String shipDateEnd;
    private String inDateStart;
    private String inDateEnd;
    private String creDateStart;
    private String creDateEnd;

    private List<String> deptNos;

    private Page page;
}
