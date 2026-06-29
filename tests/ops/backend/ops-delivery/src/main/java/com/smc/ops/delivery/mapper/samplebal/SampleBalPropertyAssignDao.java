package com.smc.ops.delivery.mapper.samplebal;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.db.entity.OpsOrderAssignResult;
import com.sales.ops.db.entity.SampleBalPropertyAssign;
import com.smc.ops.delivery.model.branch.ExpdetailPropertyDo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * sample_bal_property_assign 表相关的操作
 */
@DS("opsdb")
@Mapper
public interface SampleBalPropertyAssignDao {

    /**
     * 查询expdetail_property表出库的相关信息
     * @return
     */
    @Select("<script> select  expdetail_id,model_no,company_id,quantity,sum(property_quantity) property_quantity from expdetail_property \n  " +
            " where del_flag = 0  " +
            "and expdetail_id in " +
            "<foreach collection=\"list\" item=\"item\"  open=\"(\" separator=\",\" close=\")\">\n" +
            " #{item} " +
            "</foreach> " +
            "  group by expdetail_id,model_no,company_id,quantity " +
            "</script>")
    List<ExpdetailPropertyDo> getExpdetailPropertyData(@Param("list") List<Long> list);

    @Select(" <script>      SELECT b.* from rcvdetail a inner join  ops_order_assign_result b on a.rorder_no = b.order_no and a.rorder_item = b.order_item\n" +
            " where a.rorder_fno =  #{ROrderNo}  and delflag = '0' order by seqNo  </script> ")
    List<OpsOrderAssignResult> getOpsAssignResult(@Param("ROrderNo") String ROrderNo);

    @Insert(" <script> insert into sample_bal_property_assign (sample_bal_id,model_no,quantity,company_id,create_time,create_user,proportion,version ) values \n" +
            " <foreach collection='list' item='item' index='index' separator=','>  " +
            " (#{item.sampleBalId},#{item.modelNo},#{item.quantity},#{item.companyId},#{item.createTime},#{item.createUser},#{item.proportion},#{item.version}) " +
            " </foreach> </script> ")
    public int insertSampleBalPropertyAssign(List<SampleBalPropertyAssign> sampleBalPropertyAssignList); // 批量写入

}
