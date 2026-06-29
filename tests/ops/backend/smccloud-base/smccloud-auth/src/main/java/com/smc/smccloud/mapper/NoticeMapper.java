package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.notice.Notice;
import com.smc.smccloud.model.notice.NoticeCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opscmm")
public interface NoticeMapper extends BaseMapper<Notice>
{

    // 带着用户可读权限查询
    @Select("<script>" +
            " SELECT \n" +
            " NR.READ_STATUS, N.ID, HEADING, READED_NUM, TOTAL_NOTICE, CREATE_NAME, READEDABLE_USER,\n" +
            " NEWSDATE, EDITSTATUS, NOTICE_CONTENT\n" +
            " FROM \n" +
            " sales_sys_notice_READER NR RIGHT JOIN sales_sys_notice N\n" +
            " ON NR.NOTICE_ID = N.ID\n" +
            " WHERE \n" +
            " NR.USERNAME = #{userName} AND N.EDITSTATUS = 'published'\n" +
            " ORDER BY NEWSDATE DESC " +
            "</script>")
    List<Notice> queryByReadPower(@Param("userName") String userName);

   // 获取阅读公告的人数
    @Select("select \n" +
            " sn.ID ,snr.USERNAME ,count(*) as READED_NUM \n" +
            " from sales_sys_notice sn left join sales_sys_notice_reader snr on sn.ID =snr.NOTICE_ID \n" +
            " where READ_STATUS ='readed' \n" +
            " group by snr.USERNAME ,sn.ID")
    List<String> getReaderNum();
}
