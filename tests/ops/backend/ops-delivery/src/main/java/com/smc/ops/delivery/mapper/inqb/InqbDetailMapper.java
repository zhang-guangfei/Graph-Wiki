package com.smc.ops.delivery.mapper.inqb;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sales.ops.db.entity.OpsInqbDetail;
import com.smc.ops.delivery.model.inqb.OpsInqbDetailDO;
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
public interface InqbDetailMapper extends BaseMapper<OpsInqbDetailDO> {

    @Select(" <script> select * from ops_inqb_detail  where inqb_apply_no in \n" +
            "(select inqb_apply_no from ops_inqb_detail where task_no = #{taskNo}) </script> ")
    List<OpsInqbDetail> getInqbDetailByTaskNo(@Param("taskNo") String taskNo);

    @Select(" <script> select * from ops_inqb_detail where inqb_apply_no = #{applyno}  </script> ")
    List<OpsInqbDetail> getInqbDetailByApplyNo(@Param("applyno") String applyno);

    @Select(" <script> select * from ops_inqb_detail where inqb_apply_no = #{applyno}  </script> ")
    List<OpsInqbDetailDO> findInqbDetailDOByApplyNo(@Param("applyno") String applyno);
}
