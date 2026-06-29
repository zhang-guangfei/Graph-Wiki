package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.TempExpressinfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author C12961
 * @date 2023/1/3 15:13
 */
public interface TempExpressInfoDao {


    @Select("select max(id) from expdetail")
    Long selectMaxIdForExpdetail();

    @Select("select do_id=e.delivery_no,order_fno=e.order_fno,order_id=e.order_no,order_item=e.item_no,split_no=do.num, " +
            "ship_date=e.ship_date,express_no=e.express_no,express_company=e.express_company " +
            "from expdetail e " +
            "inner join ops_do do " +
            "on e.order_no=do.order_id and e.item_no=do.order_item and do.delflag=0 and do.do_type='JYCK' " +
            "where e.id between #{start} and #{end} " +
            "order by e.id")
    List<TempExpressinfo> findExpressInfo(long start, long end);


    void saveBatch(List<TempExpressinfo> list);


}
