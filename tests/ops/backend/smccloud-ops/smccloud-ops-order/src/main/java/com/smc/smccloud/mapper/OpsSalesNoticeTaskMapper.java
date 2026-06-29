package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.notice.OpsSalesNoticeTaskDO;
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
 * @since 2023-07-12
 */
@Mapper
@DS("sharedb")
public interface OpsSalesNoticeTaskMapper extends BaseMapper<OpsSalesNoticeTaskDO> {


    @Select("<script>" +
            " select * from ops_sales_notice_task " +
            " where ((handle_status = 4 and error_hand_count &lt; 10) or handle_status = 0) " +
            " <if test = 'listCode.size() &gt; 0  '> " +
            " and business_code in " +
            " <foreach collection='listCode' item='item' index='index' open='(' separator=',' close=')'> " +
            "   #{item} " +
            " </foreach> " +
            "</if>" +
            " order by id asc " +
            "</script>")
    List<OpsSalesNoticeTaskDO> getCanHandleList(@Param("listCode") List<String> listCode);

}
