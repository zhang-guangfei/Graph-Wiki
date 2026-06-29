package com.sales.ops.db.extdao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 交货期客户清单
 *
 * @author B28029
 * @version 1.0
 * @date 2022/7/22 18:23
 */
public interface OpsDlvCustomerDao {

    @Select("select customer_no FROM ops_customer_wldate with(nolock) where  is_wldate = 1 and del_flag=0")
    List<String> listDlvCustomers();

}
