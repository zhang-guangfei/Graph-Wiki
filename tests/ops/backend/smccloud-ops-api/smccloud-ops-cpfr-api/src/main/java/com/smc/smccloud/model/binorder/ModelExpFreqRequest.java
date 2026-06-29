package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.core.model.page.BaseQuery;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * @Author edp02 @Date 2021/10/25 11:17
 */
@Data
public class ModelExpFreqRequest extends BaseQuery {

    private String modelNo;

    private String modelTYpe;

    private String stockcode;

    private Date monthdate;

    private Integer stockType;
}
