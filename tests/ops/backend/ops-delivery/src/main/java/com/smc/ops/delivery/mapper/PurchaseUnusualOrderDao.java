package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsPoUnusualOrderLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
@DS("poadapterdb")
public interface PurchaseUnusualOrderDao {

    @Select("select top ${num} change.supplier_code,log.* from gps.dbo.ops_po_unusual_order_log log " +
            "left join gps.dbo.ops_po_changed_log_from_supplier change on log.source_id=change.id " +
            "where log.create_time >=#{date} and log.id >#{maxId} and change.supplier_code in ('JP0','CNC') order by log.id"
    )
    List<OpsPoUnusualOrderLog> getUnusualOrderLogs(Integer num, Long maxId, Date date);

    @Select("select * from gps.dbo.ops_po_unusual_order_log where id =#{id} ")
    OpsPoUnusualOrderLog getUnusualOrderLogById(Long id);
}
