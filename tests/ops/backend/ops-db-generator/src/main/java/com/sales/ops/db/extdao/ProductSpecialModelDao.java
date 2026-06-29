package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: $
 * @description：特殊型号KLS
 * @date ：Created in 2025/2/12 11:16
 */
public interface ProductSpecialModelDao {

//    @Select("SELECT count(ModelNo) from product_special_model where ModelNo = #{modelNo} and is_deleted =0" )
//    int countSpecialModelNo(@Param("modelNo") String modelNo);


//    @Select("SELECT top 1 warehouse_code  from ops_fixed_intensive_config where exp_dlv_type = #{expDlv} and delflag =0" )
//    String getFixedWarehouseCode(@Param("expDlv") Integer expDlv);
}
