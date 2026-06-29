USE [ops_core]
GO

/****** Object:  Table [dbo].[order_status]    Script Date: 2024/4/22 8:28:58 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[order_status](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [varchar](16) NOT NULL,
	[order_item] [int] NOT NULL,
	[split_no] [int] NOT NULL,
	[split_type] [char](1) NOT NULL,
	[warehouse_code] [varchar](16) NOT NULL,
	[modelno] [varchar](128) NOT NULL,
	[wm_order_id] [varchar](128) NULL,
	[qty] [int] NOT NULL,
	[qty_in] [int] NOT NULL,
	[qty_out] [int] NOT NULL,
	[wl_date] [date] NULL,
	[cre_time] [datetime] NOT NULL,
	[creator] [varchar](256) NULL,
	[modify_time] [datetime] NOT NULL,
	[modifier] [varchar](256) NULL,
 CONSTRAINT [PK__ops_orde_status_copy1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[order_status] ADD  DEFAULT ((0)) FOR [qty]
GO

ALTER TABLE [dbo].[order_status] ADD  DEFAULT ((0)) FOR [qty_in]
GO

ALTER TABLE [dbo].[order_status] ADD  DEFAULT ((0)) FOR [qty_out]
GO

ALTER TABLE [dbo].[order_status] ADD  DEFAULT (getdate()) FOR [cre_time]
GO

ALTER TABLE [dbo].[order_status] ADD  DEFAULT (getdate()) FOR [modify_time]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'order_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单项' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'order_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'拆分序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'split_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'拆分类型
0：整出,
1：数量拆分,
2：型号拆分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'split_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'集约仓' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'warehouse_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'型号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'modelno'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交易出库物流指令' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'wm_order_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'在库数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'qty_in'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已发数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'qty_out'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'指定物流发货期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'wl_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'cre_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'creator'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'modify_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status', @level2type=N'COLUMN',@level2name=N'modifier'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接单查询需要的实时查询状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status'
GO


