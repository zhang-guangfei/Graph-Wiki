package com.smc.smccloud.mapper.Menu;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.menu.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opscmm")
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * getChildsByPid:查询父节点下所有编码不为空的子节点,按编码倒叙排列
     */
    List<Menu> getChildsByPid(String pCode);

    /**
     * 通过CODE删除
     * @param code
     */
    @Delete("DELETE FROM sales_sys_menu WHERE CODE LIKE CONCAT(#{code},'%')")
    void deleteByCode(@Param("code") String code);

    /**
     * 根据用户ID获取用户对应的菜单
     * @return
     */
    @Select("SELECT DISTINCT M.* FROM sales_sys_user_role UR,sales_sys_role R,sales_sys_role_AUTHORITY RA,sales_sys_authority A," +
            " sales_sys_authority_MENU AM, sales_sys_menu M WHERE UR.ROLE_ID = R.ID AND R.ID=RA.ROLE_ID " +
            " AND RA.AUTHORITY_ID = A.ID AND A.ID=AM.AUTHORITY_ID AND AM.MENU_ID = M.ID " +
            " AND UR.USER_ID = #{userId} ORDER BY M.SORT ASC")
    List<Menu> getByUserId(@Param("userId") String userId);

    /**
     * 根据用户组获取用户对应的菜单
     * @return
     */
    List<Menu> getByGroupName(List<String> groupNames);

    /**
     * 根据用户组ID获取用户对应的菜单
     * @return
     */
    @Select("<script>" +
            "SELECT DISTINCT M.* FROM sales_sys_group_role SGR, sales_sys_role R, " +
            " sales_sys_role_AUTHORITY RA,sales_sys_authority A, " +
            " sales_sys_authority_MENU AM, sales_sys_menu M  " +
            " WHERE SGR.ROLE_ID = R.ID AND R.ID=RA.ROLE_ID  " +
            " AND RA.AUTHORITY_ID = A.ID AND A.ID=AM.AUTHORITY_ID AND AM.MENU_ID = M.ID " +
            " <foreach collection='groupIds' item='item' index='index' open='AND (' close=')' separator='or'> " +
            "   SGR.GROUP_ID = #{item} " +
            " </foreach> " +
            "  ORDER BY M.SORT ASC" +
            "</script>")
    List<Menu> getByGroupId(@Param("groupIds")  List<String> groupIds);

}
