USE [ops_core]
GO

/****** Object:  Table [dbo].[order_status_item]    Script Date: 2024/4/22 8:29:11 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[order_status_item](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [varchar](16) NOT NULL,
	[order_item] [int] NOT NULL,
	[split_no] [int] NOT NULL,
	[qty] [int] NOT NULL,
	[qty_in] [int] NOT NULL,
	[qty_out] [int] NOT NULL,
	[inventory_id] [bigint] NULL,
	[inventory_table] [varchar](32) NOT NULL,
	[status] [varchar](32) NOT NULL,
	[status_desc] [varchar](32) NOT NULL,
	[status_info] [varchar](512) NULL,
	[inventory_type] [varchar](16) NULL,
	[invoice_no] [varchar](100) NULL,
	[associate_no] [varchar](128) NULL,
	[cre_time] [datetime] NOT NULL,
	[creator] [varchar](128) NULL,
	[modify_time] [datetime] NOT NULL,
	[modifier] [varchar](128) NULL,
 CONSTRAINT [PK__ops_orde_status_copy1_copy1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[order_status_item] ADD  DEFAULT ((0)) FOR [qty]
GO

ALTER TABLE [dbo].[order_status_item] ADD  DEFAULT ((0)) FOR [inventory_table]
GO

ALTER TABLE [dbo].[order_status_item] ADD  DEFAULT (getdate()) FOR [cre_time]
GO

ALTER TABLE [dbo].[order_status_item] ADD  DEFAULT (getdate()) FOR [modify_time]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'order_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单行号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'order_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'拆分序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'split_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'inventory_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'在库数' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'inventory_table'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态-4305' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'status'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态描述-4304' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'status_desc'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存地点' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'status_info'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存类别：顾客在库，战略在库、通用在库;出库区分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'inventory_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发票号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'invoice_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'关联单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'associate_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'cre_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'creator'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'modify_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item', @level2type=N'COLUMN',@level2name=N'modifier'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接单查询需要的实时查询状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_status_item'
GO


