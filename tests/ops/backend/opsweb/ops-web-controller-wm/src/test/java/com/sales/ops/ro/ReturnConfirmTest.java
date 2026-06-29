package com.sales.ops.ro;

import cn.hutool.core.util.RandomUtil;
import com.sales.ops.BaseTest;
import com.sales.ops.common.opsexception.OpsException;
import com.sales.ops.dto.inventory.CreInvMoveForReturnOrderDto;
import com.sales.ops.serviceimpl.dispatch.rodispatch.service.ReturnConfirmHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Slf4j
public class ReturnConfirmTest extends BaseTest {


    @Autowired
    private ReturnConfirmHandler returnConfirmHandler;



    @Test
    public void test1(){
        try {
            CreInvMoveForReturnOrderDto dto = new CreInvMoveForReturnOrderDto();
            dto.setSplitItemNo(0);
            dto.setOrderNo("11270060");
            dto.setOrderItem(1);
            dto.setApplyNo("TH"+ RandomUtil.randomString(6));
            dto.setItemNo(1);
            dto.setModelNo("CDJ2E16-200AZ-B");
            dto.setQty(6);
            dto.setWarehouseCode("KGZ");
            dto.setPsnNo("TestC12961");// 创建人
            dto.setToUserStock(false);  // true :GKTY, false:TY
            dto.setCustomerNo("C12961");
            returnConfirmHandler.returnConfirm(Collections.singletonList(dto));
        } catch (OpsException e) {
            throw new RuntimeException(e);
        }


    }


}
