package com.smc.smccloud.mapper.purchase;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.purchase.SMCOrderDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author B91717
 * @since 2021-11-03
 * CTC订单写入
 */
@DS("ctcdb")
@Mapper
public interface SmcOrderToCTCMapper extends BaseMapper<SMCOrderDO> {

    @Insert("<script> INSERT INTO SMC_Order (OrderNo,Prjno,ModelNo,qty,Remarks,Deliverydate,orderdate,dbname,status,ProducePlace) values  " +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.orderNo},#{item.prjno},#{item.modelNo},#{item.qty},#{item.remarks},#{item.deliverydate},#{item.orderdate},#{item.dbname},#{item.status},#{item.producePlace}) " +
            " </foreach> </script> ")
    public Boolean insertSmcOrder(@Param("list") List<SMCOrderDO> list);


    @Update("<script>  update a set a.status='8-订单取消'  from SMC_Order a where a.status IN ('2-图纸已承认','3-图纸未发布','4-图纸设变中','5-图纸未查到')" +
            " and a.orderno = #{orderno}  " +
            "</script> ")
    public int updateSmcOrderCancel(@Param("orderno") String orderno);

    @Update("<script>  update a set a.status='7-订单完成',a.StoreDate = #{date}  from SMC_Order a where a.status IN ('2-图纸已承认','3-图纸未发布','4-图纸设变中','5-图纸未查到')" +
            " and a.orderno = #{orderno}  " +
            "</script> ")
    public Boolean updateSmcOrderFinish(@Param("orderno") String orderno,@Param("date") Date date);
}
