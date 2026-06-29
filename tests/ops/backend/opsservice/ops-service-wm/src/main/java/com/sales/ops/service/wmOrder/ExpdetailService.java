package com.sales.ops.service.wmOrder;

import com.sales.ops.db.entity.Expdetail;

import java.util.List;

public interface ExpdetailService {

    List<Expdetail> getExpdetails(String doId);


    String getExpressNoByDoId(String doId);

    Expdetail getSihpTimeByOrderFno(String orderFno);

    int countExpdetailByOrderFnoAndInvoiceFlag(String orderFno);
}
