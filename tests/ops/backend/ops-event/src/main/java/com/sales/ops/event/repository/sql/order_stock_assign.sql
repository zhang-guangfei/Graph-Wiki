USE [ops_core]
GO

/****** Object:  Table [dbo].[order_stock_assign]    Script Date: 2024/4/22 8:29:27 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[order_stock_assign](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_no] [varchar](64) NOT NULL,
	[order_item] [int] NOT NULL,
	[seqNo] [int] NOT NULL,
	[modelno] [varchar](64) NOT NULL,
	[quantity] [int] NOT NULL,
	[stock_type] [varchar](64) NULL,
	[stock_code] [varchar](64) NULL,
	[inventory_type_code] [varchar](64) NULL,
	[associate_no] [varchar](64) NULL,
	[associate_no_item] [int] NULL,
	[associate_no_split_no] [int] NULL,
	[supplierId] [varchar](64) NULL,
	[create_time] [datetime] NOT NULL,
	[create_user] [varchar](64) NULL,
	[update_time] [datetime] NOT NULL,
	[update_user] [varchar](64) NULL,
	[delflag] [int] NULL,
	[source_type] [int] NULL,
 CONSTRAINT [PK__ops_orde__3213E83F5F1D76DF_copy1_copy1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[order_stock_assign] ADD  DEFAULT ((0)) FOR [quantity]
GO

ALTER TABLE [dbo].[order_stock_assign] ADD  DEFAULT ((0)) FOR [delflag]
GO

ALTER TABLE [dbo].[order_stock_assign] ADD  DEFAULT ((0)) FOR [source_type]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'order_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单itemno' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'order_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分配顺序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'seqNo'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'型号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'modelno'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分配数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'quantity'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'N:在库；P:生产在途； T:采购在途；CG：采购' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'stock_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'在库 在途为库房代码，采购为供应商' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'stock_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'库存类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'inventory_type_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采购/在途为PO号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'associate_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'po_item号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'associate_no_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'在库为出库库房代码；
在途为在途表的SupplierId；
采购为供应商代码；' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'supplierId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'delflag'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'触发生成类别
0：allot原始分配;
1:reAllot还原;
2:转订;
n:xxx

' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'order_stock_assign', @level2type=N'COLUMN',@level2name=N'source_type'
GO


