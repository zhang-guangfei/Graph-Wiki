package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import java.util.List;

/**
 * @Author lyc
 * @Date 2023/2/13 14:59
 * @Descripton TODO
 */
@Data
public class ZlzsExportPdfVO {

    private List<ZlszPdfTableVo> tabledatas;

    private List<ZlszPdfTableVo> tabledataWithZlzs;

}
