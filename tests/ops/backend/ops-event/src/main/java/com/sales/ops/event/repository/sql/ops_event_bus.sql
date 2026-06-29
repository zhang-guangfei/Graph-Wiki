USE [ops_core]
GO

/****** Object:  Table [dbo].[ops_event_bus]    Script Date: 2024/4/22 8:27:17 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ops_event_bus](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [varchar](16) NOT NULL,
	[order_item] [varchar](16) NOT NULL,
	[split_no] [int] NULL,
	[event_code] [varchar](255) NOT NULL,
	[params] [ntext] NULL,
	[deal_flag] [int] NOT NULL,
	[cre_time] [datetime] NOT NULL,
	[creator] [varchar](32) NULL,
	[modify_time] [datetime] NOT NULL,
	[modifier] [varchar](32) NULL,
	[remark] [nvarchar](2000) NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [PK_ops_event_bus_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

ALTER TABLE [dbo].[ops_event_bus] ADD  CONSTRAINT [DF__ops_event__deal___66E1BA57]  DEFAULT ((0)) FOR [deal_flag]
GO

ALTER TABLE [dbo].[ops_event_bus] ADD  CONSTRAINT [DF__ops_event__cre_t__67D5DE90]  DEFAULT (getdate()) FOR [cre_time]
GO

ALTER TABLE [dbo].[ops_event_bus] ADD  CONSTRAINT [DF__ops_event__modif__68CA02C9]  DEFAULT (getdate()) FOR [modify_time]
GO

ALTER TABLE [dbo].[ops_event_bus] ADD  CONSTRAINT [DF__ops_event__versi__69BE2702]  DEFAULT ((0)) FOR [version]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'order_id'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户单行号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'order_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'拆分序号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'split_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单状态变更事件 枚举类' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'event_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'参数Json格式' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'params'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否已处理0： 未处理 ，1：已处理' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'deal_flag'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'cre_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'creator'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'modify_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改人' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'modifier'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'执行失败原因' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ops_event_bus', @level2type=N'COLUMN',@level2name=N'remark'
GO


