package com.sales.ops.db.extdao;

import com.sales.ops.dto.units.OpsUnitsTree;

import java.util.List;

public interface OpsUnitsTreeDao {

    List<OpsUnitsTree> findAfterFiltrationDeptInfo();
}
