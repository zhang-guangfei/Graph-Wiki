package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.TBilBillingHeaderOps;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author C12961
 * @date 2022/6/15 8:33
 */
public interface TBilBillingDao {


    @Select("select * from ops_share.dbo.T_BIL_BILLING_HEADER_OPS")
    List<TBilBillingHeaderOps> getTBilBillingHeaderOps();

}
