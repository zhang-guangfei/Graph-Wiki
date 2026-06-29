package com.smc.smccloud.model.shikomi;

import com.smc.smccloud.model.adapter.ShikomiInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author edp04
 * @title: ShikomiModelDTO
 * @date 2022/07/26 10:07
 */
@Data
public class ShikomiModelDTO {

    private List<ShikomiInfo> infoList;

    private Map<String,String> map = new HashMap<>();
}
