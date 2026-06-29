package com.smc.smccloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.borrowstock.BrwMasterDO;
import com.smc.smccloud.model.borrowstock.BrwMasterDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BrwMasterMapper extends BaseMapper<BrwMasterDO> {

    @Select("<script>" +"SELECT b.brw_no,a.opt_status,a.quantity,a.return_qty,b.customer_name,b.user_name,b.brw_psn,b.brw_psn_tel,b.sales_psn,b.sales_psn_tel" +
            ",a.model_no,b.purpose,b.es_return_date,b.dept_no FROM brw_detail a inner join brw_master b\n" +
            "ON a.brw_id = b.id where 1= 1"+
            "<if test = 'brwNo != null and brwNo != \"\" ' >" +
            " and b.brw_no = #{brwNo}" +
            "</if>" +
            "<if test = 'modelNo != null and modelNo != \"\" ' >" +
            " and a.model_no like CONCAT('%',#{modelNo},'%') " +
            "</if>" +
            "</script>")
    List<BrwMasterDTO> listNotReturnDTO(@Param("brwNo") String brwNo, @Param("modelNo") String modelNo);
}
