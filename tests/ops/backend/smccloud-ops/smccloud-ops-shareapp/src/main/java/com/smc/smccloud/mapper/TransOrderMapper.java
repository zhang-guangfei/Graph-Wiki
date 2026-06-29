package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author C18117
 * @title: TransOrderMapper
 * @date 2023/01/18 10:20
 */
@Mapper
@DS("opsdb")
public interface TransOrderMapper {

    @Update("update trans_order set status=#{status} where trans_no = #{orderNo} and item_no = #{itemNo}")
    Integer updateStatusByOrderNo(@Param("orderNo") String orderNo,@Param("itemNo") Integer itemNo,@Param("status") Integer status);


    @Update("update ops_core.dbo.trans_order set status= '9' where from_id = #{fromId} and trans_no = #{transNo} and item_no = #{itemNo} ")
    @DS("opsdb")
    int updateStatusWithCancel(@Param("fromId") String fromId, @Param("transNo") String transNo, @Param("itemNo") Integer itemNo);
}
