package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsExceptionHandle;
import com.sales.ops.db.entity.TDocOrderTrackingOpsWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：异常表
 * @date ：Created in 2022/11/17 10:03
 */
public interface OpsExceptionHandleDao {

    /**
     * do 异常查询
     * @param limit
     * @param today
     * @return
     */
    @Select("select top ${limit} * from ops_exception_handle where  (update_time < #{today} OR update_time IS NULL) and " +
            "(parameter_9 < 10  or parameter_9 is null) and business_type = 'Do' AND err_type = 8 AND status = 0 order by update_time ")
    List<OpsExceptionHandle> findExceptionHandle(@Param("limit") Integer limit, @Param("today") String today);

    /**
     * do 异常查询
     * @return
     */
    @Select("select * from ops_exception_handle where business_type = 'Do' AND err_type = 8 AND status = 1  ")
    List<OpsExceptionHandle> findExceptionHandleNewAddInventoryLog();

    /**
     * do 异常查询
     * @return
     */
    @Select("select * from ops_exception_handle where business_type = 'Do' AND err_type = 8 AND status = 0  ")
    List<OpsExceptionHandle> findExceptionHandleNew();
}
