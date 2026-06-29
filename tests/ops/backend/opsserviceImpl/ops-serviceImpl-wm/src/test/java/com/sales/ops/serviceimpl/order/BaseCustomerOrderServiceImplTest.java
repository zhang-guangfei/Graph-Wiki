package com.sales.ops.serviceimpl.order;

import com.sales.ops.db.dao.RcvViewMapper;
import com.sales.ops.db.entity.RcvViewExample;
import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.dto.order.ReceiveCondition;
import com.sales.ops.dto.util.PageModel;
import com.sales.ops.enums.RcvOrderStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaseCustomerOrderServiceImplTest {

    @Spy
    @InjectMocks
    private BaseCustomerOrderServiceImpl service;

    @Mock
    private RcvViewMapper rcvViewMapper;

    @Captor
    private ArgumentCaptor<RcvViewExample> rcvViewExampleCaptor;

    @Captor
    private ArgumentCaptor<Rcvdetail> rcvdetailCaptor;

    @Test
    public void searchByPage_shouldUseIndependentInterceptFilterForRcvView() throws Exception {
        ReceiveCondition condition = new ReceiveCondition();
        condition.setIntercept(true);

        PageModel<ReceiveCondition> pageModel = new PageModel<>();
        pageModel.setCondition(condition);
        pageModel.setPageNumber(1);
        pageModel.setPageSize(20);
        pageModel.setOrderBy("rorddate desc, rorder_item asc");

        when(rcvViewMapper.selectByExample(any(RcvViewExample.class))).thenReturn(Collections.emptyList());

        service.searchByPage(pageModel);

        verify(rcvViewMapper).selectByExample(rcvViewExampleCaptor.capture());
        List<RcvViewExample.Criterion> criteria = rcvViewExampleCaptor.getValue().getOredCriteria().get(0).getAllCriteria();

        assertEquals(Boolean.TRUE, findCriterion(criteria, "intercept =").getValue());
        assertEquals(
                Arrays.asList(
                        RcvOrderStatusEnum.INIT.getType(),
                        RcvOrderStatusEnum.CGING.getType(),
                        RcvOrderStatusEnum.INTCP.getType(),
                        RcvOrderStatusEnum.DBING.getType(),
                        RcvOrderStatusEnum.WAITCK.getType(),
                        RcvOrderStatusEnum.CKING.getType()
                ),
                findCriterion(criteria, "status in").getValue()
        );
    }

    @Test
    public void updateStatusToCancel_shouldClearInterceptFlagWhenOrderWasIntercepted() throws Exception {
        Rcvdetail current = new Rcvdetail();
        current.setStatus(RcvOrderStatusEnum.CGING.getType());
        current.setIntercept(true);

        doReturn(current).when(service).findRcvDetailByNo("RO123", "1");
        doReturn(1).when(service).updateRcvDetail(eq("RO123"), eq(1), rcvdetailCaptor.capture());

        int result = service.updateStatusToCancel("RO123", "1");

        assertEquals(1, result);
        assertEquals(RcvOrderStatusEnum.CANCEL.getType(), rcvdetailCaptor.getValue().getStatus());
        assertEquals(Short.valueOf((short) 1), rcvdetailCaptor.getValue().getDeleteStatus());
        assertEquals(Boolean.FALSE, rcvdetailCaptor.getValue().getIntercept());
    }

    private RcvViewExample.Criterion findCriterion(List<RcvViewExample.Criterion> criteria, String condition) {
        for (RcvViewExample.Criterion criterion : criteria) {
            if (condition.equals(criterion.getCondition())) {
                return criterion;
            }
        }
        assertNotNull("Missing criterion: " + condition, null);
        return null;
    }
}
