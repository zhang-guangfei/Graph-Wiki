package com.sales.ops.db.batchdao;


import com.sales.ops.db.entity.OpsWmOrderTask;
import java.util.List;

public interface WmOrderTaskBatchDao {

    void wmOrderTaskbatchInsert(List<OpsWmOrderTask> deptList);
}