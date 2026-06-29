package com.smc.smccloud.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smc.smccloud.mapper.OpsPurchaseInvoiceMapper;
import com.smc.smccloud.model.Purchase.OpsPurchaseInvoiceDO;
import com.smc.smccloud.model.order.OrderNoInfo;
import com.smc.smccloud.service.PurchaseConvertService;

@Service
public class PurchaseConvertServiceImpl implements PurchaseConvertService {

	private static Logger log = LoggerFactory.getLogger(PurchaseConvertServiceImpl.class);

	@Autowired
	private OpsPurchaseInvoiceMapper opsPurchaseInvoiceMapper;

	@Override
	public OrderNoInfo convertPoNoFormPurchase(String no, String item) {
		OrderNoInfo info = new OrderNoInfo();
		if (StringUtils.isBlank(no)) {
			info.setOrderType(99);
			info.setError(true);
			info.setOrderNo("");
			if (StringUtils.isNumeric(item)) {
				info.setItemNo(1);
			}
			return info;
		}
		// 是否为DR/CR订单
		if (no.endsWith("-DR") || no.endsWith("-CR")) {
			String endStr = no.substring(no.length() - 3);
			no = no.substring(0, no.length() - 3);
			if (endStr.equalsIgnoreCase("-DR")) {
				info.setOrderType(24);
			} else {
				info.setOrderType(25);
			}
		}
		// 去掉前面的采购类型前缀
		if (info.getPURCHASE_TYPES().contains(no.substring(0, 1)) && !no.startsWith("AP")) {
			// 采购类型
			info.setPurchaseType(no.substring(0, 1));
			no = no.substring(1);
		}
		// 20230901 旧订单号去掉前面的CN,SH,按后7位截取
		if (no.length() == 9
				&& (no.startsWith("CN") || no.startsWith("SH") || no.startsWith("OT") || no.startsWith("ST"))) {
			no = no.substring(2);
		}
		// 海外老订单
		if (!no.contains("-") && no.length() == 10 && ("0".equalsIgnoreCase(item) || StringUtils.isBlank(item))) {
			info.setFullOrderNo(no);
			// 判断结尾是否为数字
			// bug15958 采购发票数据导入时，特殊订单号的解析报错,增加数字的校验
			if (StringUtils.isNumeric(no.substring(7))) {
				info.setOrderNo(no.substring(0, 7));
				info.setStrItemNo(no.substring(7, 10));
				info.setItemNo(Integer.parseInt(info.getStrItemNo()));
			} else {
				info.setOrderNo(no);
				info.setStrItemNo("1");
				info.setItemNo(1);
			}
			info.setFullOrderNo(no);
			info.setOrFullOrderNo(no);
			info.setPoNo(info.getOrderNo());
			info.setPoItemNo(info.getItemNo());

			// G1234567812 拆成 项号81 拆分项2
			if (!no.startsWith("99")) {
				if (!no.endsWith("0")) {
					info.setSplitItem(info.getItemNo() % 10);
				}
				info.setItemNo((info.getItemNo() - info.getItemNo() % 10) / 10);
			}
			// bug14066 增加对ordertype字段赋值，老订单直接赋值99，后续不更新
			info.setOrderType(99);
			return info;
		}
		// 从采购获取
		Integer lineitem;
		if (StringUtils.isBlank(item) || StringUtils.equals("0", item)) {
			lineitem = 1;
		} else {
			lineitem = Integer.parseInt(item);
		}
		info.setPoNo(no);
		info.setPoItemNo(lineitem);
		OpsPurchaseInvoiceDO invoice = opsPurchaseInvoiceMapper.getByPono(no, lineitem);
		if (invoice == null) {
			// bug14441若purchaseinvoice表没查到，为删单来货，硬解析pono
			if (no.contains("-")) {
				info = splitBySeparator(no);
			} else {
				info = splitByPos(no, item);
			}
			// bug14066 增加对ordertype字段赋值
			info.setOrderType(99);
			log.error("convertPoNoFormPurchase---采购单号转换未找到,pono:" + no + ";item:" + item);
			return info;
		}
		info.setOrderNo(invoice.getOrderNo());
		info.setItemNo(invoice.getItemNo());
		info.setSplitItem(invoice.getSplitItemNo());
		// bug14066 增加对ordertype字段赋值
		if (StringUtils.isNotBlank(invoice.getOrdType()))
			info.setOrderType(Integer.parseInt(invoice.getOrdType()));
		else
			info.setOrderType(99);
		if (no.length() > 7) {
			info.setFullOrderNo(invoice.getOrderNo() + "-" + invoice.getItemNo().toString()
					+ (invoice.getSplitItemNo() == null ? "" : "-" + invoice.getSplitItemNo().toString()));
		} else {
			info.setFullOrderNo(invoice.getOrderNo()
					+ (invoice.getItemNo() > 10 ? invoice.getItemNo().toString() : "0" + invoice.getItemNo().toString())
					+ (invoice.getSplitItemNo() == null ? "0" : invoice.getSplitItemNo().toString()));
		}
		info.setOrFullOrderNo(info.getFullOrderNo());

		return info;
	}

	private OrderNoInfo splitByPos(String no, String item) {
		OrderNoInfo info = new OrderNoInfo();
		if (no.length() == 10) {
			// bug15958 采购发票数据导入时，特殊订单号的解析报错,增加数字的校验
			if (StringUtils.isNumeric(no.substring(7))) {
				info.setOrderNo(no.substring(0, 7));
				info.setItemNo(Integer.parseInt(no.substring(7, 10)));
			} else {
				info.setOrderNo(no);
				info.setItemNo(1);
			}
			// 旧单号不加-
			info.setFullOrderNo(no);
			info.setPoNo(info.getOrderNo());
			info.setPoItemNo(info.getItemNo());
			// G1234567812 拆成 项号81 拆分项2
			if (!info.getOrderNo().startsWith("99")) {
				if (!no.endsWith("0")) {
					info.setSplitItem(info.getItemNo() % 10);
				}
				info.setItemNo(info.getItemNo() / 10);
			}
		} else if (no.length() == 7) {
			info.setError(true);
			info.setOrderNo(no);
			info.setOrderType(99);
			info.setFullOrderNo(no + item);
			info.setPoNo(no);
			if (StringUtils.isNotBlank(item)) {
				info.setPoItemNo(Integer.parseInt(item));
				info.setItemNo(Integer.parseInt(item) / 10);
				if (!item.endsWith("0")) {
					info.setSplitItem(Integer.parseInt(item) % 10);
				}
			}
		} else {
			info.setError(true);
			info.setOrderNo(no);
			info.setOrderType(99);
			info.setFullOrderNo(no);
			info.setPoNo(no);
			if (StringUtils.isNotBlank(item)) {
				info.setPoItemNo(Integer.parseInt(item));
				info.setItemNo(Integer.parseInt(item));
			}
		}
		return info;
	}

	/**
	 * 按分隔符分隔
	 *
	 * @param poNo
	 */
	private OrderNoInfo splitBySeparator(String poNo) {
		OrderNoInfo info = new OrderNoInfo();
		String[] arrs = poNo.split("-");

		if (arrs.length == 2) {
			if (StringUtils.isNotBlank(arrs[1])) {
				if (StringUtils.isNumeric(arrs[1])) {
					info.setOrderNo(arrs[0]);
					info.setItemNo(Integer.parseInt(arrs[1]));
					info.setFullOrderNo(info.getOrderNo() + "-" + info.getItemNo().toString());
					info.setPoNo(info.getFullOrderNo());
					info.setPoItemNo(1);
				} else {
					info.setError(true);
				}
			}
		}
		if (arrs.length == 3) {
			if (arrs[0].length() >= 7) {
				if (StringUtils.isNumeric(arrs[1]) && StringUtils.isNumeric(arrs[2])) {
					info.setOrderNo(arrs[0]);
					info.setItemNo(Integer.parseInt(arrs[1]));
					info.setSplitItem(Integer.parseInt(arrs[2]));
					info.setFullOrderNo(info.getOrderNo() + "-" + info.getItemNo().toString() + "-"
							+ info.getSplitItem().toString());
					info.setPoNo(info.getFullOrderNo());
					info.setPoItemNo(1);
				} else {
					info.setError(true);
				}
			} else {
				if (StringUtils.isNumeric(arrs[2])) {
					info.setOrderNo(arrs[0] + "-" + arrs[1]);
					info.setItemNo(Integer.parseInt(arrs[2]));
					info.setFullOrderNo(info.getOrderNo() + "-" + info.getItemNo());
					info.setPoItemNo(1);
					info.setPoNo(info.getFullOrderNo());
				} else {
					info.setError(true);
				}
			}
		}

		if (arrs.length == 1 || arrs.length > 3 || info.isError()) {
			info.setError(true);
			info.setOrderNo(poNo);
			info.setItemNo(0);
			info.setOrderType(99);
			info.setFullOrderNo(poNo);
			info.setPoNo(poNo);
			info.setPoItemNo(1);
		}
		return info;
	}

}
