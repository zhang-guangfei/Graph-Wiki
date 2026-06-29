package com.smc.ops.delivery.inquiry.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 查询制造数据库方法
 */
public interface InquiryManuDataService {


    // 采购催促，待处理清单发送
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("cmdb")
    public  String getCheckRemark(String pono, String lineitem);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @DS("cmdb")
    public  List<Date> getCountByNo(String orderFno);



}
