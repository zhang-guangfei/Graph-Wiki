USE [ops_core]
GO

/****** Object:  Table [dbo].[rcvdetail_copy]    Script Date: 2024/4/22 8:21:50 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[rcvdetail_copy](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[rorder_no] [varchar](20) NOT NULL,
	[rorder_item] [int] NOT NULL,
	[rorder_fno] [varchar](30) NOT NULL,
	[model_no] [varchar](40) NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [money] NULL,
	[price_enduser] [money] NULL,
	[eprice] [decimal](18, 2) NULL,
	[tax_rate] [decimal](5, 3) NULL,
	[ntax_pice] [money] NULL,
	[ntax_amount] [money] NULL,
	[tax_amount] [money] NULL,
	[amount] [money] NULL,
	[discount] [decimal](18, 3) NULL,
	[dlv_date] [datetime] NULL,
	[cdlv_date] [datetime] NULL,
	[wms_dlv_date] [date] NULL,
	[spec_offer_no] [varchar](50) NULL,
	[source_type] [char](1) NULL,
	[cproduct_no] [nvarchar](100) NULL,
	[spec_mark] [char](1) NULL,
	[remark] [nvarchar](1000) NULL,
	[product_name] [nvarchar](35) NULL,
	[opponent] [varchar](5) NULL,
	[ppl_no] [varchar](30) NULL,
	[project_no] [varchar](30) NULL,
	[group_customer_no] [varchar](200) NULL,
	[shikomi_no] [varchar](30) NULL,
	[sales_info_no] [varchar](40) NULL,
	[pre_sales_order_no] [varchar](20) NULL,
	[corder_no] [nvarchar](100) NULL,
	[custom_code] [varchar](50) NULL,
	[order_type] [smallint] NULL,
	[status] [smallint] NULL,
	[delete_status] [smallint] NULL,
	[stock_code] [varchar](10) NULL,
	[stock_type] [varchar](2) NULL,
	[inventory_type_code] [varchar](16) NULL,
	[prod_flag] [char](1) NULL,
	[allot_time] [datetime] NULL,
	[ready_time] [datetime] NULL,
	[exp_time] [datetime] NULL,
	[ship_time] [datetime] NULL,
	[ready_qty] [int] NULL,
	[exp_qty] [int] NULL,
	[returned_qty] [int] NULL,
	[address_no] [varchar](200) NULL,
	[process_date] [datetime] NULL,
	[borrow_no] [varchar](30) NULL,
	[po_qty] [int] NULL,
	[exp_msg] [varchar](256) NULL,
	[exp_dlv_type] [int] NULL,
	[exp_link_no] [varchar](120) NULL,
	[invoiceGroupKey] [nvarchar](16) NULL,
	[invoice_qty] [int] NULL,
	[invoice_time] [datetime] NULL,
	[carrierId] [varchar](10) NULL,
	[expressNo] [varchar](50) NULL,
	[handover_time] [datetime] NULL,
	[version] [int] NULL,
	[create_time] [datetime] NULL,
	[update_time] [datetime] NULL,
	[update_user] [varchar](50) NULL,
	[create_user] [varchar](50) NULL,
	[price_user] [money] NULL,
	[intercept] [bit] NULL,
	[intercept_time] [datetime] NULL,
	[industryId] [varchar](20) NULL,
	[customerType] [varchar](20) NULL,
	[expected_delivery_time] [datetime] NULL,
 CONSTRAINT [PK__rcvdetail_copy] PRIMARY KEY NONCLUSTERED 
(
	[rorder_no] ASC,
	[rorder_item] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [quantity]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [price]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [price_enduser]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ('') FOR [product_name]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [status]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [delete_status]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [ready_qty]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [exp_qty]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [returned_qty]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT (NULL) FOR [exp_dlv_type]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [invoice_qty]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [version]
GO

ALTER TABLE [dbo].[rcvdetail_copy] ADD  DEFAULT ((0)) FOR [intercept]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'接单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'rorder_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'rorder_item'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'完整订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'rorder_fno'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'型号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'model_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'quantity'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'price'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最终用户单价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'price_enduser'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'e价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'eprice'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'税率' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'tax_rate'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'不含税单价' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ntax_pice'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'不含税金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ntax_amount'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'税额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'tax_amount'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'含税金额' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'amount'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'折扣率' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'discount'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'交货日期(客户端)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'dlv_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'原交货期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'cdlv_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'指定物流出库日' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'wms_dlv_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'特价号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'spec_offer_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单来源' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'source_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户产品代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'cproduct_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'阀与汇流板标识（0:正常订货;1:底板;2:组装原件）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'spec_mark'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'备注' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'remark'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户产品名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'product_name'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'竞争对手' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'opponent'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'ppl号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ppl_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项目号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'project_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户群代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'group_customer_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'shikomi号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'shikomi_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'营业情报号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'sales_info_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'前端与OPS交互单号，如果是门户上的订单则是发注单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'pre_sales_order_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户订单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'corder_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户自定义出货代码(打印在标签上)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'custom_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'订单类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'order_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'处理状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'status'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'删除状态-0未删除,1已删除,2删除中,3退货中' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'delete_status'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'出库区分代码（采购供应商，仓库）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'stock_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'出库区分类别（N：在库 ，T：在途 ，CG： 采购）' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'stock_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'出库区分的库存类别：顾客在库，战略在库、通用在库;出库区分' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'inventory_type_code'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'prod_flag'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分配时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'allot_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'货齐时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ready_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'完成出货时间【物流出库时间】' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'exp_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'完成发货日期【承运商揽收时间】' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ship_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'在库货齐数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'ready_qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已发出货指令数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'exp_qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'已退货数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'returned_qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'发货地址 项地址变更时存OrderDlvData的id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'address_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'最后的接单处理时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'process_date'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'借库号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'borrow_no'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'采购数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'po_qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'异常信息' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'exp_msg'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'特殊标识' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'exp_dlv_type'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'开票数量' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'invoice_qty'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'承运商id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'carrierId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'运单号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'expressNo'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'承运商交接时间(出库时间)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'handover_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新版本号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'version'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'处理日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'update_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否信用拦截' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'intercept'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'信用拦截状态更新时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'intercept_time'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'行业代码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'industryId'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'客户类型' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'customerType'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'预计出库日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'rcvdetail_copy', @level2type=N'COLUMN',@level2name=N'expected_delivery_time'
GO


