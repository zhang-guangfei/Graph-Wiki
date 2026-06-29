package com.smc.ops.delivery.mapper.inqb;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInqbApply;
import com.sales.ops.dto.inqb.InqbQueryRequest;
import com.sales.ops.dto.inqb.InqbQueryRequestParam;
import com.sales.ops.dto.inqb.InqbSalesApplyAddParam;
import com.sales.ops.dto.inqb.OpsInqbQueryInfo;
import com.smc.ops.delivery.model.inqb.OpsInqbApplyDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-06-27
 */
@Mapper
@DS("opsdb")
public interface InqbApplyMapper extends BaseMapper<OpsInqbApplyDO> {

    @Select(" <script>  SELECT *  FROM ops_inqb_apply where end_user = #{enduser} and model_no = #{modelno}  and  DATEDIFF(DAY,inqb_date,GETDATE())<![CDATA[ <= ]]> #{diffDay} and inqb_status in ('1','2','5') </script> ")
    public List<OpsInqbApplyDO> selectThreeDaysApply(@Param("modelno") String modelno, @Param("enduser") String enduser, @Param("diffDay") String diffDay);

    @Select(" <script> select Max(inqb_apply_no) from ops_inqb_apply where SUBSTRING(inqb_apply_no, 3,8) = #{localDate} </script>")
    public String getMaxApplyNo(@Param("localDate") String localDate);

    @Select(" <script>  select inqb_apply_no from ops_inqb_apply where inqb_status in ('5')  and model_no = #{modelNo} and quantity <![CDATA[ >= ]]> #{quantity}\n" +
            "and (\n" +
            "end_user = #{customerNo} \n" +
            "<if test='endUser != null and endUser != \"\" '> or end_user = #{endUser}  </if> \n" +
            "<if test='userNo != null and userNo != \"\" '> or end_user = #{userNo}  </if> \n" +
            ")  order by reply_time desc  </script> ")
    public List<String> getAvailableApplyNo(InqbQueryRequestParam inqbQueryRequestParam);


    @Select(" <script>  SELECT * FROM ops_inqb_apply   \n" +
            " where inqb_apply_no =  #{applyno}  </script> ")
    public List<OpsInqbApply> getApplyByApplyNo(@Param("applyno") String applyno);


    @Select(" <script>  SELECT *  FROM ops_inqb_apply where  sources_apply_no = #{sourcesApplyNo} and batch_no = #{batchNo}  </script> ")
    public List<OpsInqbApply> getApplyBySourceApplyNo(InqbSalesApplyAddParam inqbSalesApplyAddParam);


        @Select(" <script> select distinct a.* from ops_inqb_apply a inner join ops_inqb_detail b on a.inqb_apply_no = b.inqb_apply_no  where 1=1 " +
            " <if test=\"inqbApplyNo != null  and inqbApplyNo != ''\">and  a.inqb_apply_no like concat('%', #{inqbApplyNo}, '%') </if> " +
            " <if test=\"sourcesApplyNo != null  and sourcesApplyNo != ''\">and  a.sources_apply_no =#{sourcesApplyNo} </if>" +
            " <if test=\"modelNo != null  and modelNo != ''\">and a.model_no like concat('%', #{modelNo}, '%')  </if>" +
            " <if test=\"endUser != null  and endUser != ''\">and  a.end_user =#{endUser} </if>" +
            " <if test=\"inqbStatus != null \">and  a.inqb_status =#{inqbStatus} </if>" +
            " <if test=\"inqbValidity != null  and inqbValidity != ''\">and  a.inqb_validity =#{inqbValidity} </if>" +
            " <if test=\"inqbUser != null  and inqbUser != ''\">and (a.inqb_user =#{inqbUser} or a.inqb_user_name =#{inqbUser}) </if>" +
            " <if test=\"supplierCode != null  and supplierCode != ''\">and  b.supplier_code =#{supplierCode}  </if>" +
            " <if test=\"supplierCodeList != null and supplierCodeList.size() > 0\">\n" +
            "      and b.supplier_code in\n" +
            "      <foreach collection=\"supplierCodeList\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item}\n" +
            "      </foreach>\n" +
            " </if>" +
            " <if test=\"startDate != null\">and a.inqb_date between #{startDate} and #{endDate} </if>" +
            " <if test=\"inqbDeptList != null and inqbDeptList.size() > 0\">\n" +
            "      and a.inqb_dept in\n" +
            "      <foreach collection=\"inqbDeptList\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">\n" +
            "        #{item}\n" +
            "      </foreach>\n" +
            "    </if>" +
            "</script> ")
    List<OpsInqbQueryInfo> queryInqbInfo(InqbQueryRequest inqbQueryRequest);

}
