package com.sales.ops;

import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.service.wmOrder.OpsDoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class MoveToOrderTest extends BaseTest{

    @Autowired
    private OpsDoService opsDoService;

    @Test
    public void test1() throws OpsException {
        List<Rcvdetail> rcvdetails = opsDoService.findRcvDetailsByMove(2307230L);
        for (Rcvdetail rcvdetail : rcvdetails) {
            log.info("{}", rcvdetail.getRorderFno());
        }
    }
}
