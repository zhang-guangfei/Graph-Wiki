package com.smc.ops.delivery.mapper.inqb;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.inqb.OpsInqbHandle;
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
@DS("poadapterdb")
public interface InqbHandlelogMapper {

    @Select(" <script>  SELECT * FROM ops_inqb_handle_log where id &gt; #{id} and (status = #{status} or  (status ='1' and reply_no is not null))  </script> ")
    public List<OpsInqbHandle> getReplyHandleData(@Param("id") Long id,@Param("status") int status);

}