package com.smc.ops.delivery.mapper.inqb;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInqbCodeConfig;
import com.smc.ops.delivery.model.inqb.OpsInqbCodeConfigDO;
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
public interface InqbCodeConfigMapper extends BaseMapper<OpsInqbCodeConfigDO> {


    @Select(" <script>  SELECT *  FROM ops_inqb_code_config where is_deleted ='0' and code_type = #{codeType} </script> ")
    public List<OpsInqbCodeConfig> selectOpsInqbCodeConfig(@Param("codeType") String codeType);

}
