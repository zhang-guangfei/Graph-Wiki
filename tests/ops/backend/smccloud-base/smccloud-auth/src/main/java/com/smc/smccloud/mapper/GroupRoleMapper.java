package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.group.GroupRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@DS("opscmm")
public interface GroupRoleMapper extends BaseMapper<GroupRole> {
    // 根据用户组ID删除
    @Delete("DELETE FROM sales_sys_group_role WHERE GROUP_ID = #{groupId}")
    void deleteByGroupId(@Param("groupId") String groupId);
}
