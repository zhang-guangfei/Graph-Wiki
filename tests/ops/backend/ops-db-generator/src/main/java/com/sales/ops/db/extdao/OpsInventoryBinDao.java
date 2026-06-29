package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

public interface OpsInventoryBinDao {


    @Select("<script>"
            + "select count(*) from ( "
            + " SELECT ModelNo FROM Bindata where StockType = 1 and delFlag = 0 \n"
            + " <if test='customerNo != null'>" +
            "   union all\n"
            + " SELECT ModelNo FROM Bindata where StockType = 4 and delFlag = 0 and CustomerNo =#{ customerNo } \n"
            + " </if> ) t "
            + "where t.ModelNo =#{modelno} "
            + "</script>")
    Integer isBinModelno(String modelno, String customerNo);


}
