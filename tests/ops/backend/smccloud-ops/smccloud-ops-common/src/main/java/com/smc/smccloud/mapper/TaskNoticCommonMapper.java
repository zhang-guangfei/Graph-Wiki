package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lyc
 * @Date 2025/8/19 13:21
 * @Descripton TODO
 */
@Mapper
@DS("sharedb")
public interface TaskNoticCommonMapper {


    @Insert("insert into ops_sharedb.dbo.ops_sales_notice_task (batch_no,business_code,apply_no,order_fno,call_back_parameter,handle_status,return_status,create_user) " +
            " VALUES ( #{batch_no},#{business_code},#{apply_no},#{order_fno},#{call_back_parameter},#{handle_status},#{return_status},#{createUser} )")
    void insertTaskNotice(@Param("batch_no") String batch_no, @Param("business_code") String business_code,
                          @Param("apply_no") String apply_no, @Param("order_fno") String order_fno,
                          @Param("call_back_parameter") String call_back_parameter, @Param("handle_status") String handle_status,
                          @Param("return_status") String return_status,@Param("createUser") String createUser);

}
