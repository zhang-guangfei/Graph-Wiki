package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2023/9/4 8:21
 */
public interface TempWmsApiDao {

    @Select("SELECT qty from temp_wms_api where do_id = #{doId}")
    public Integer getTempWmsApi(@Param("doId") String doId);
}
