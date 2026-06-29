package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.DataTypeDO;
import com.smc.smccloud.model.RegionBeanTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@DS("opscmm")
@Mapper
public interface DictDataMapper extends BaseMapper<DataTypeDO> {
//   int addDict(DataTypeDO dataTypeDO);

   @Select(" SELECT id, class_code, code, code_name from tbl_datatype where class_code = #{classCode} ORDER BY sort DESC ")
   List<RegionBeanTree> queryChildrenForOne(@Param("classCode") String classCode);

   @Select(" SELECT id, class_code, code, code_name from tbl_datatype where 1 = 1 ")
   List<RegionBeanTree> queryAll();

}
