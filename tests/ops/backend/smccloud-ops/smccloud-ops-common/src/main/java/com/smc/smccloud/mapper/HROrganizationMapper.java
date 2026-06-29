package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.HROrganizationDO;
import com.smc.smccloud.model.HrOrganizationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author: lyc
 * Date: 2023-01-13 16:11
 * Description:
 */
@DS("opsdb")
@Mapper
public interface HROrganizationMapper extends BaseMapper<HROrganizationDO> {


    @Select("WITH SplitUnitCode AS (\n" +
            "    SELECT \n" +
            "        Id,\n" +
            "        ParentId,\n" +
            "        Name,\n" +
            "        FullName,\n" +
            "        Level,\n" +
            "        Status,\n" +
            "        SalesCustomized,\n" +
            "        CompanyCode,\n" +
            "        CompanyName,\n" +
            "        unitCode,\n" +
            "        -- 将 unitCode 按 ! 分割为数组\n" +
            "        value AS split_part,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY Id ORDER BY CHARINDEX('!' + value + '!', '!' + unitCode + '!')) AS part_num,\n" +
            "        COUNT(*) OVER (PARTITION BY Id) AS total_parts\n" +
            "    FROM hr_organization\n" +
            "    CROSS APPLY STRING_SPLIT(unitCode, '!')\n" +
            "    WHERE unitCode IS NOT NULL AND unitCode != '' and id like '2%'\n" +
            "),\n" +
            "EndDeptNoCTE AS (\n" +
            "    SELECT \n" +
            "        s.Id,\n" +
            "        s.ParentId,\n" +
            "        s.Name,\n" +
            "        s.Level,\n" +
            "        -- 根据不同的 Level 和条件计算 endDeptNo\n" +
            "        CASE \n" +
            "            -- 1. 课(营业所)\n" +
            "            WHEN s.Level = '课(营业所)' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts)\n" +
            "            \n" +
            "            -- 2. 课内机构(HL)\n" +
            "            WHEN s.Level = '课内机构(HL)' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts - 1)\n" +
            "            \n" +
            "            -- 3. 课内机构(驻在所) 且 name 不包含'行业'\n" +
            "            WHEN s.Level = '课内机构(驻在所)' AND s.Name NOT LIKE '%行业%' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts - 1)\n" +
            "            \n" +
            "            -- 4. 课内机构(驻在所) 且 name 包含'行业'\n" +
            "            WHEN s.Level = '课内机构(驻在所)' AND s.Name LIKE '%行业%' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts)\n" +
            "            \n" +
            "            -- 5. 驻在所HL\n" +
            "            WHEN s.Level = '驻在所HL' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts - 2)\n" +
            "            \n" +
            "            -- 6. 本部\n" +
            "            WHEN s.Level = '本部' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts)\n" +
            "            \n" +
            "            -- 7. 二级部\n" +
            "            WHEN s.Level = '二级部' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts)\n" +
            "            \n" +
            "            -- 8. 公司 (如果表中有这个 Level)\n" +
            "            WHEN s.Level = '公司' THEN \n" +
            "                (SELECT split_part FROM SplitUnitCode WHERE Id = s.Id AND part_num = s.total_parts)\n" +
            "            \n" +
            "            -- 默认情况\n" +
            "            ELSE NULL\n" +
            "        END AS endDeptNo,\n" +
            "        s.unitCode,\n" +
            "        s.total_parts\n" +
            "    FROM SplitUnitCode s\n" +
            "    WHERE s.part_num = 1 -- 只需要一行记录\n" +
            ")\n" +
            "SELECT \n" +
            "    Id,\n" +
            "    ParentId,\n" +
            "    endDeptNo,\n" +
            "    Name,\n" +
            "    Level\n" +
            "FROM EndDeptNoCTE\n" +
            "WHERE endDeptNo IS NOT NULL and id like '2%'\n" +
            "ORDER BY Id;")
    List<HrOrganizationDto> getAllOrganization();

}
