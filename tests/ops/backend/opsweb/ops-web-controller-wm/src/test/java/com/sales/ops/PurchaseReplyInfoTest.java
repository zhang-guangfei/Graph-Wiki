package com.sales.ops;

import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.extdao.PurchaseReplyTestMapper;
import com.sales.ops.dto.purchase.PurchaseReplyInfo;
import com.sales.ops.service.purchase.PurchaseApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchaseReplyInfoTest extends BaseTest {

    @Autowired
    public PurchaseApiService purchaseApiService;

    @Autowired
    private PurchaseReplyTestMapper purchaseReplyTestMapper;

    @Test
    public void test() throws Exception {
        List<OpsPurchaseinvoice> datas = purchaseReplyTestMapper.getData();
        for (OpsPurchaseinvoice data : datas) {
            List<PurchaseReplyInfo> list = new ArrayList<>();
            PurchaseReplyInfo i = new PurchaseReplyInfo();
            i.setOrderno(data.getOrderno());
            i.setItemno(data.getItemno());
            i.setSplitno(data.getSplititemno());
            i.setReplyexportdate(new Date());
            list.add(i);
            purchaseApiService.updateInvoice(list);
        }
    }

}
