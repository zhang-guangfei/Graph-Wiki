package com.sales.ops.db.extdao;

import com.sales.ops.db.entity.ImpInvoiceEventLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * 发票导入日志记录
 * 用于调用接口请求、响应结果时间检测排查异常
 *
 * @author wsf
 * @version 1.0
 * @date 2022/6/16 9:20
 */
public interface ImpInvoiceEventLogDao {

    @Insert(" insert into imp_invoice_event_log(op_type,request_param,return_data,op_status,op_start_time,op_end_time,duration,create_time)" +
            "values " +
            "(#{opType}, #{requestParam}, #{returnData},#{opStatus}, #{opStartTime}, #{opEndTime}, #{duration} , getdate())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertImpInvoiceEventLog(ImpInvoiceEventLog eventLog);


}
