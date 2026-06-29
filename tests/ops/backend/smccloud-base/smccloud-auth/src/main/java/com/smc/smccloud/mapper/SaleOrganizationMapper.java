package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.Organ.DeptDictDTO;
import com.smc.smccloud.model.Organ.OpsUnitsTree;
import com.smc.smccloud.model.authority.SaleOrgPositionBean;
import com.smc.smccloud.model.authority.SaleOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@DS("opscmm")
public interface SaleOrganizationMapper extends BaseMapper<SaleOrganization> {
    @Select("<script>" +
            " SELECT " +
            " pos.Id, pos.Name, organ.Id as UnitId, organ.Name as UnitName, organ.FullName, organ.ParentId " +
            " FROM HR_organization organ inner join HR_ORG_POSITION org " +
            " on organ.Id = org.org_id " +
            " inner join HR_position pos " +
            " on pos.Id = org.position_id " +
            " <trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\" >\n" +
            " <if test=\"id != null and id != ''\">\n" +
            " organ.Id =#{id}\n" +
            " </if>\n" +
            " <if test=\"positionName != null and positionName != ''\">\n" +
            " And organ.id like '2%' And pos.Name =#{positionName}\n" +
            " </if>\n" +
            " </trim> " +
            " </script>")
    List<SaleOrgPositionBean> getByUnitIdAndPositionName(@Param(value = "id") String id, @Param(value = "positionName") String positionName);

    @Select(" <script>" +
            " SELECT " +
            " pos.Id, pos.Name, organ.Id as UnitId, organ.Name as UnitName, organ.FullName, organ.ParentId " +
            " FROM HR_organization organ inner join HR_ORG_POSITION org " +
            " on organ.Id = org.org_id " +
            " inner join HR_position pos " +
            " on pos.Id = org.position_id " +
            " <trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\" >\n" +
            " <if test=\"id != null and id != ''\">\n" +
            " organ.Id =#{id}\n" +
            " </if>\n" +
            " <if test=\"positionName != null and positionName != ''\">\n" +
            " And organ.id like '2%' and pos.Name like '%'+ #{positionName} +'%'\n" +
            " </if>\t\t\n" +
            " </trim> " +
            " </script>")
    List<SaleOrgPositionBean> getByUnitIdAndPositionNameLike(@Param(value = "id") String id, @Param(value = "positionName") String positionName);

    @Select(" <script>" +
            " SELECT tion.*" +
            " FROM hr_organization tion" +
            " LEFT JOIN hr_holon holo ON tion.Id = holo.FNUMBER" +
            " WHERE holo.FNUMBER IS NULL" +
            " AND (SUBSTRING(tion.unitCode, 15, 6) = '220000'" +
            " OR SUBSTRING(tion.unitCode, 15, 6) = '240000'" +
            " OR SUBSTRING(tion.unitCode, 15, 6) = '260000'" +
            " OR SUBSTRING(tion.unitCode, 15, 6) = '235000')" +
            " AND LEN(SUBSTRING(tion.unitCode, 15, 6)) > 5" +
            " AND Id LIKE '2' + '%' AND Status = '0'" +
            " </script>")
    List<OpsUnitsTree> findAfterFiltrationDeptInfo();

    @Select(" <script>" +
            " SELECT tion.*" +
            " FROM hr_organization tion" +
            " LEFT JOIN hr_holon holo ON tion.Id = holo.FNUMBER" +
            " WHERE holo.FNUMBER IS NULL " +
            " <if test = 'list.size() &gt; 0  '> " +
            " and SUBSTRING(tion.unitCode, 15, 6) in " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            " </if>" +
            " AND LEN(SUBSTRING(tion.unitCode, 15, 6)) > 5" +
            " AND Id LIKE '2' + '%' AND Status = '0'" +
            " </script>")
    List<OpsUnitsTree> findAfterFiltrationDeptInfo3(@Param("list") List<String> list);


    @Select("<script>" +
            "SELECT " +
            " * " +
            "FROM" +
            " ops_core.dbo.hr_organization " +
            " WHERE " +
            " status = 0 " +
            " AND id LIKE '2%' " +
            // " AND SUBSTRING ( unitCode, 15, 6 ) IN ( '220000', '235000', '240000', '260000', '280000' ) " +
            " <if test = 'list.size() &gt; 0  '> " +
            " and SUBSTRING(unitCode, 15, 6) in " +
            "   <foreach collection = 'list' item = 'item' index='index' open='(' separator = ',' close=')' > " +
            "      #{item}" +
            "   </foreach>" +
            " </if>" +
            " AND LEN( " +
            " SUBSTRING ( unitCode, 15, 6 )) > 5 " +
            " and [Level] NOT IN ('课内机构(HL)', '驻在所HL')" +
            "</script>")
    List<OpsUnitsTree> findAfterFiltrationDeptInfo2(@Param("list") List<String> list);


    @Select(" <script>" +
            " SELECT Id as deptId,Name as deptName" +
            " FROM hr_organization " +
            " </script>")
    List<DeptDictDTO> findDeptAsDict();
}
