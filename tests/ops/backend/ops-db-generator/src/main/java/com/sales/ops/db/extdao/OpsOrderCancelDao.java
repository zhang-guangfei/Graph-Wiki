package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsOrderCancel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author C12961
 * @date 2021/12/17 8:47
 */
public interface OpsOrderCancelDao {

    @Insert(" insert into ops_order_cancel " +
            "(orderid, order_item,order_type, reason, cre_time,creator)" +
            "values " +
            "(#{orderid}, #{orderItem}, #{orderType}, #{reason}, #{creTime}, #{creator})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCancelReturnId(OpsOrderCancel cancel);

}
