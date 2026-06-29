package com.smc.smccloud.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smc.smccloud.model.StockAssemblyDetailViewDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]	Begin DesignProperties = 	   Begin PaneConfigurations = 	      Begin PaneConfiguration = 0	         NumPanes = 4	         Configuration = "(H (1[48] 4[29] 2[12] 3) )"	      End	      Begin PaneConfiguration = 1	         NumPanes = 3	         Configuration = "(H (1 [50] 4 [25] 3))"	      End	      Begin PaneConfiguration = 2	         NumPanes = 3	         Configuration = "(H (1 [50] 2 [25] 3))"	      End	      Begin PaneConfiguration = 3 Mapper 接口
 * </p>
 *
 * @author smc
 * @since 2024-11-19
 */
@Mapper
@DS("sharedb")
public interface StockAssemblyDetailViewMapper extends BaseMapper<StockAssemblyDetailViewDO> {

}
