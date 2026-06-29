package com.smc.ops.delivery.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.sales.ops.dto.expdetail.SampleOrderlogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * ops 日志记录表，记录数据
 */
@DS("opslog")
@Mapper
public interface OpslogDao {

    /**
     *
     * @param sampleOrderlogDto
     * @return
     */
    @Insert(" <script> insert into order_log (orderno,opt_type,description,opt_time,opt_user_name,opt_user_id,create_time) \n" +
            " values(#{orderNo},#{optType},#{description},#{optTime},#{optUserName},#{optUserId},#{createTime}) </script> ")
    public int insertOrderlog(SampleOrderlogDto sampleOrderlogDto);

}
