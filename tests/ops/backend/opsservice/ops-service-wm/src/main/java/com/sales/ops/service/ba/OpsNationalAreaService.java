package com.sales.ops.service.ba;

import com.sales.ops.db.entity.OpsNationalArea;
import com.sales.ops.dto.units.ElementUITree;

import java.util.List;

/**
 * @author C12961
 * @date 2022/6/7 15:08
 */
public interface OpsNationalAreaService {


    List<OpsNationalArea> selectRootArea();

    List<OpsNationalArea> selectByParentCode(String parentCode);

    Object getAreaTree();

    List<ElementUITree> createAreaTree();
}
