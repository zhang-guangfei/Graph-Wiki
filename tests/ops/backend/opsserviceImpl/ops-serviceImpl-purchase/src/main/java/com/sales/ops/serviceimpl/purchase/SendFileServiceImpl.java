package com.sales.ops.serviceimpl.purchase;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sales.ops.db.entity.OpsCustomer;
import com.sales.ops.db.entity.OpsPoAutosendRemarkConfig;
import com.sales.ops.db.entity.OpsPurchaseinvoice;
import com.sales.ops.db.entity.OpsWarehouse;
import com.sales.ops.dto.purchase.PdfTableData;
import com.sales.ops.dto.purchase.PurchaseSendPdfDto;
import com.sales.ops.service.purchase.SendFileService;
import com.sales.ops.serviceimpl.utils.JasperHelper;

@Service
public class SendFileServiceImpl implements SendFileService {

	@Value("${pofiles.basepath}")
	private String poBasePath; // 采购发单文件生成目录

	// bug13409 采购发单增加pdf文件
	@Override
	public String savePdf(String warehouse, String supplier, List<PurchaseSendPdfDto> list, Map<String, Object> map)
			throws Exception {
		// String template = "D:\\";
		String template = "/ops/Sys_Report/";
		// bug 18642 OPS向日本及海外发单时，将备份文件夹的位置，配置到nacos中，方便进行环境的切换
		if (StringUtils.isNotBlank(poBasePath)) {
			template = poBasePath;
		}
		map.put("img", template + "smc.png");
		String localname = URLEncoder.encode(supplier, "utf-8") + "-" + warehouse + "-"
				+ (new SimpleDateFormat("yyMMddHHmmssSSS")).format(new Date()) + ".pdf";
		List<PdfTableData> info = new ArrayList<PdfTableData>();
		PdfTableData temp = new PdfTableData();
		temp.setTableData(list);
		info.add(temp);
		InputStream inputStream = new FileInputStream(template + "sendInfo.jasper");
		String path = template + "pdf/" + new DateTime().getYear() + "/";
		JasperHelper.savePdf(inputStream, path, map, info, localname);
		// JasperHelper.saveExcel(inputStream, path, map, info, localname);
		return path + localname;
	}

	@Override
	public String exportPdfFile(String warehouseid, String supplierNo, List<OpsPurchaseinvoice> list, String fullname,
			Integer payment, OpsWarehouse opswarehouse, Map<String, Boolean> resMap,
			Map<String, OpsCustomer> customerMap, List<OpsPoAutosendRemarkConfig> configList) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断是否是AP，若不是则隐藏最后一列APNO
		if (!StringUtils.equals("AP", supplierNo)) {
			map.put("apnohide", false);
		} else {
			map.put("apnohide", true);
		}
		// 20220905意大利隐藏TNT列
		if (StringUtils.equals("IT", supplierNo)) {
			map.put("exnohide", true);
		} else {
			map.put("exnohide", false);
		}
		map.put("origin", fullname);
		map.put("name", "Name: " + opswarehouse.getEnglishLinkman() + "    " + "Phone:" + opswarehouse.getLinkPhone());
		map.put("address", "Address: " + opswarehouse.getEnglishAddress());
		map.put("payment", payment.toString());
		map.put("orderdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		List<PurchaseSendPdfDto> dtoList = new ArrayList<PurchaseSendPdfDto>();
		list.forEach(a -> {
			PurchaseSendPdfDto dto = new PurchaseSendPdfDto();
			dto.setOrderno(a.getPono());
			dto.setModelno(a.getModelno());
			dto.setQty(a.getQuantity().toString());
			dto.setDelivery(new SimpleDateFormat("yyyy-MM-dd").format(a.getHopeexportdate()));
			dto.setShipment("Air");
			dto.setExno("774046836");
			if (StringUtils.isNotBlank(a.getShikomianswerno()))
				dto.setShikomi(a.getShikomianswerno());
			if (StringUtils.equals("US", supplierNo)) {
				if (a.getImportfobpriceoriginal() != null) {
					dto.setPrice(a.getImportfobpriceoriginal().toString());
				}
			}

			// bug11473自动发单贩卖限制品备注增加客户英文名
			// bug 20054,用end_user替换userno
			String remark = StringUtils.isNotBlank(a.getServerremark()) ? a.getServerremark() : "";
			if (resMap.containsKey(a.getModelno())) {
				if (StringUtils.isBlank(a.getEndUser()) && StringUtils.isNotBlank(a.getCustomerno())) {
					if (StringUtils.isNotBlank(customerMap.get(a.getCustomerno()).getEname()))
						remark = remark + " " + customerMap.get(a.getCustomerno()).getEname();
					else if (StringUtils.isNotBlank(customerMap.get(a.getCustomerno()).getAliasEname())) {
						remark = remark + " " + customerMap.get(a.getCustomerno()).getAliasEname();
					}
				} else if (StringUtils.isNotBlank(a.getEndUser())) {
					if (StringUtils.isNotBlank(customerMap.get(a.getEndUser()).getEname()))
						remark = remark + " " + customerMap.get(a.getEndUser()).getEname();
					else if (StringUtils.isNotBlank(customerMap.get(a.getEndUser()).getAliasEname())) {
						remark = remark + " " + customerMap.get(a.getEndUser()).getAliasEname();
					}
				}
			}
			// bug11473物料号
			List<OpsPoAutosendRemarkConfig> temp = configList.stream()
					.filter(s -> StringUtils.equals(a.getModelno(), s.getModelno())
							&& (StringUtils.equals(a.getDeptno(), s.getDeptno()) || StringUtils.isBlank(s.getDeptno())))
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(temp)) {
				for (OpsPoAutosendRemarkConfig o : temp) {
					remark = remark + " " + o.getRemark();
				}
			}
			dto.setRemark(remark);

			if (StringUtils.equals("AP", supplierNo)) {
				dto.setApno(a.getSupplierpartno());
			}
			dtoList.add(dto);
		});

		return savePdf(warehouseid, supplierNo, dtoList, map);
	}

}
