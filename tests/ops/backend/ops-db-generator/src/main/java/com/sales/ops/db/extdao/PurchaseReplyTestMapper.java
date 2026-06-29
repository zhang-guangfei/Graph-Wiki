package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PurchaseReplyTestMapper {

    @Select("select top 100 * from ops_purchaseInvoice where replyExportDate is not null  order by sendTime desc")
    List<OpsPurchaseinvoice> getData();


}
