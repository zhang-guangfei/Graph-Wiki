package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.expdetail.DataCodeDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 查询字典翻译
 */
@DS("opscmm")
@Mapper
public interface DictdataDao {


    /**
     * 查询字典数据
     * @return
     */
    @Select("<script>  SELECT code,code_name codeName,ext_note1 extNote1,ext_note2 extNote2,ext_note3 extNote3,sort \n" +
            "FROM tbl_datatype \n" +
            "where  class_code = #{class_code}  and status = '1' and parent_code = ''\n" +
            "order by code  </script>")
    public List<DataCodeDto> getTbldatatype(@Param("class_code") String class_code);



}
