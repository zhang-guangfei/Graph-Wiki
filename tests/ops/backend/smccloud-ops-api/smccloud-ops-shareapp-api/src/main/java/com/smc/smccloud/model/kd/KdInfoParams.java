package com.smc.smccloud.model.kd;

import com.smc.smccloud.core.model.page.Page;
import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2024/10/22 16:32
 * @Descripton TODO
 */
@Data
public class KdInfoParams {

    private List<String> modelNos;

    private List<String> modelSorts;

    private List<String> prodFactorys;

    private List<String> states;

    private String updateTimeStart;

    private String updateTimeEnd;

    private String batchProdStartTimeStart;

    private String batchProdStartTimeEnd;

    private Page page;

}
