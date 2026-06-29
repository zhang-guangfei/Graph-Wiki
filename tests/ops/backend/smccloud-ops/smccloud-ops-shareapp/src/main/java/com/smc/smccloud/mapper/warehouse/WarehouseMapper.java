package com.smc.smccloud.mapper.warehouse;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.csstock.WarehouseDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@DS("opsdb")
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehouseDO> {

    @Select("SELECT Name FROM ops_ui.dbo.hr_organization WHERE Id = " +
            "(SELECT SUBSTRING(unitCode,15,6) as a FROM ops_ui.dbo.hr_organization WHERE Id =#{deptNo})")
    String getDeptByNo(@Param("deptNo") String deptNo);

    @Select("SELECT Name FROM ops_ui.dbo.hr_organization WHERE Id = " +
            "(SELECT SUBSTRING(unitCode,22,6) as a FROM ops_ui.dbo.hr_organization WHERE Id =#{deptNo})")
    String getDeptNameByNo(@Param("deptNo") String deptNo);

    @Select("SELECT Name FROM ops_ui.dbo.hr_organization WHERE Id = #{deptNo}")
    String getDeptNameByNo2(@Param("deptNo") String deptNo);

    @Select("SELECT ECode FROM product WHERE modelno=#{modelNo}")
    Integer getEcode(@Param("modelNo") String modelNo);
}
