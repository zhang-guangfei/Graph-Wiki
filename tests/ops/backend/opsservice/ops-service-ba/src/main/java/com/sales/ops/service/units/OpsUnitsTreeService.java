package com.sales.ops.service.units;

import com.smc.base.TreeInfo;

import java.util.List;

public interface OpsUnitsTreeService {

    List<TreeInfo> findAfterFiltrationDeptInfo();

    List<TreeInfo> findAfterFiltrationByBusinessOffice();
}
