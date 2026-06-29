package com.smc.ops.delivery.inquiry.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.smc.ops.delivery.inquiry.mapper.InquiryManuMidMapper;
import com.smc.ops.delivery.inquiry.service.InquiryManuDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: B91717
 * @time: 2024/1/19 9:22
 */
@Service
public class InquiryManuDataServiceImpl implements InquiryManuDataService {

    @Resource
    private InquiryManuMidMapper inquiryManuMidMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("cmdb")
    public String getCheckRemark(String pono,String lineitem) {
        return inquiryManuMidMapper.getCheckRemark(pono,lineitem);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("cmdb")
    public List<Date> getCountByNo(String orderFno) {
        return  inquiryManuMidMapper.getCountByNo(orderFno);
    }
}
