package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsWmOrderTask;
import com.sales.ops.db.entity.ProductBom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：
 * @date ：Created in 2021/10/28 12:05
 */
public interface OpsPostWmsBomDao {

    /**
     * 4.2 bom组件下发
     * @param limit
     * @return
     */
    @Select("select top ${limit} bomId,ModelNo ,Priority_Complete,Priority_level ,UpdateTime ,IsValid ,is_wms from product_bom where is_wms = 0 ORDER BY UpdateTime ")
    List<ProductBom> getBomByLimit(@Param("limit") Integer limit);


}
