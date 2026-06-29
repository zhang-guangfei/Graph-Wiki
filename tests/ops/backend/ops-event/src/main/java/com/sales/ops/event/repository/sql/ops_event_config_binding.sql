USE [ops_core]
GO

/****** Object:  Table [dbo].[ops_event_config_binding]    Script Date: 2024/4/22 8:27:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ops_event_config_binding](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[queue_name] [varchar](255) NULL,
	[event_code] [varchar](255) NULL,
	[status] [int] NULL,
 CONSTRAINT [ops_event_config_binding_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ops_event_config_binding] ADD  DEFAULT ((1)) FOR [status]
GO


SET IDENTITY_INSERT [dbo].[ops_event_config_binding] ON
GO

INSERT INTO [dbo].[ops_event_config_binding] ([id], [queue_name], [event_code], [status]) VALUES (N'1', N'ops_event_order_status', N'CUSTOMER_STATUS_UPDATE', N'1')
GO

INSERT INTO [dbo].[ops_event_config_binding] ([id], [queue_name], [event_code], [status]) VALUES (N'2', N'ops_event_stock_assign', N'CUSTOMER_STOCK_ASSIGN', N'1')
GO

INSERT INTO [dbo].[ops_event_config_binding] ([id], [queue_name], [event_code], [status]) VALUES (N'3', N'ops_event_delivery_plan', N'CUSTOMER_DELIVERY_PLAN', N'1')
GO

SET IDENTITY_INSERT [dbo].[ops_event_config_binding] OFF
GO


