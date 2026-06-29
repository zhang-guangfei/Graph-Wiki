package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.dto.purchase.BaseDataDto;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.model.SupplierDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opsdb")
public interface SupplierMapper extends BaseMapper<SupplierDO> {

    @Select("select id as code , name as codeName from supplier order by sort asc")
    List<BaseDataDto> selectAllSupplier();

}
