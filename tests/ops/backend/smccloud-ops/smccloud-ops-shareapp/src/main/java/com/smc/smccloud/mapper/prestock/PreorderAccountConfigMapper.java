package com.smc.smccloud.mapper.prestock;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.prestock.PreOrderAccountConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wuweidong
 * @create 2024/1/2 14:56
 * @description
 */
@DS("sharedb")
@Mapper
public interface PreorderAccountConfigMapper  extends BaseMapper<PreOrderAccountConfigDO> {
    /**
     * 最大175=2100/ 12
     * @param list
     * @return
     */
    @Select("<script> " +
            " insert  into preorder_account_config (priority,inventory_type_code,customer_no,ppl,project_code,group_customer_no ," +
            " isdelay,delay_max_day,delflag,create_time,creator,modify_time,modifier) VALUES" +
            " <if test='list !=null and list.size() &gt; 0'>" +
            "  <foreach collection='list' item='item' index='index'  separator=',' > " +
            "   (#{item.priority},#{item.inventoryTypeCode}, #{item.customerNo}, #{item.ppl},#{item.projectCode},#{item.groupCustomerNo}, #{item.isDelay}, #{item.delayMaxDay}," +
            "   #{item.delFlag},#{item.createTime}, #{item.creator}, #{item.modifyTime},#{item.modifier} ) " +
            "  </foreach>" +
            " </if>" +
            "</script>")
    Integer insertByBatch(@Param("list") List<PreOrderAccountConfigDO> list);
}
