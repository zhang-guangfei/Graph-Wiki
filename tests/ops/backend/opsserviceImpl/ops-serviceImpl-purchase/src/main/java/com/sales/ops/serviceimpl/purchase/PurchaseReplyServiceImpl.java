package com.sales.ops.serviceimpl.purchase;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.sales.ops.db.entity.OpsPurchaseorder;
import com.sales.ops.db.extdao.PurchaseReplyDao;
import com.sales.ops.dto.purchase.PoReplyInfoDto;
import com.sales.ops.dto.util.CommonResult;
import com.sales.ops.dto.util.MailDto;
import com.sales.ops.dto.util.OrderNoInfo;
import com.sales.ops.enums.PoReplyDateStrEnum;
import com.sales.ops.event.publisher.enums.EventSourceEnum;
import com.sales.ops.feign.OpsMailApi;
import com.sales.ops.service.purchase.PurchaseReplyService;
import com.sales.ops.serviceimpl.event.v3.PurchaseEventPublisher;
import com.smc.smccloud.core.model.ResultVo.ResultVo;
import com.smc.smccloud.model.DataCodeVO;
import com.smc.smccloud.service.DictDataServiceFeignApi;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PurchaseReplyServiceImpl implements PurchaseReplyService {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseReplyServiceImpl.class);

	@Autowired
	private PurchaseReplyDao purchaseReplyDao;

	@Autowired
	private OpsMailApi opsMailApi;

	@Autowired
	private DictDataServiceFeignApi dictDataServiceFeignApi;
	@Autowired
	private PurchaseEventPublisher purchaseEventPublisher;

	// bug13395返信延期提醒
	@Override
	public Integer poDelayEvent() {
		List<OpsPurchaseorder> list = purchaseReplyDao.getDelayPurchase();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<OpsPurchaseorder> mailfile = new ArrayList<OpsPurchaseorder>();
		for (OpsPurchaseorder item : list) {
			if (item.getDlvmoddate() == null) {
				continue;
			}
			String dateStr = PoReplyDateStrEnum.getDescByCode(item.getDlvmoddate()).getCode();
			if (StringUtils.isBlank(dateStr)) {
				continue;
			}
			if (dateStr.endsWith("99")) {
				int year = Integer.parseInt(dateStr.substring(0, 4));
				int month = Integer.parseInt(dateStr.substring(5, 7));
				int y = LocalDate.now().getYear();
				int m = LocalDate.now().getMonthOfYear();
				if (y < year || (y == year && m <= month)) {
					continue;
				}
			}
			PoReplyInfoDto delay = new PoReplyInfoDto();
			delay.setPoOrderNo(item.getOrderno());
			delay.setPoItemNo(item.getItemno());
			if (item.getSplititemno() != null)
				delay.setPoSplitItemNo(item.getSplititemno());
			OrderNoInfo orderNoInfo = new OrderNoInfo(item.getOrderno(), item.getItemno(), item.getSplititemno());
			String fullNo = orderNoInfo.getFullNo();
			delay.setPoOrderFNo(fullNo);
			delay.setSupplierId(item.getSupplierid());
			delay.setHopeExportDate(item.getHopeexportdate());
			delay.setModelNo(item.getModelno());
			delay.setQuantity(item.getQuantity());
			delay.setReplyDateMod(dateStr);
			delay.setSupplierOrderNo(item.getReplyorderno());
			//delay.setReplyDateModBefore(item.);
			delay.setCustomerNo(item.getCustomerno());
			delay.setUserNo(item.getUserno());
			//delay.setEndUser();
			delay.setDeptNoCustomer(item.getDeptno());
			delay.setOrderType(item.getOrdtype());
			delay.setPurchaseType(item.getPurchasetype());
			delay.setPurchaseStateCode(item.getStatecode());
			delay.setPurchaseDate(item.getPurchasedate());

			if(org.apache.commons.lang3.StringUtils.isNotBlank(item.getOrdtype())){
				delay.setOrderType(item.getOrdtype());
				String orderTypeName = com.smc.smccloud.core.model.enums.OrderTypeEnum.getCodeName(delay.getOrderType().toString());
				delay.setOrderTypeName(orderTypeName);
			}
			purchaseEventPublisher.purchaseOrderEvent(EventSourceEnum.PURCHASE_ORDER_DELAY_DATE, delay);
			mailfile.add(item);
		}
		// bug13481生成excel文件，邮件发送给业务
		String path;
		try {
			path = generateExcel(mailfile);
			sendEmail(path);
		} catch (Exception e) {
			logger.error("货期延期提醒邮件发送失败！", e);
		}

		return mailfile.size();
	}

	public String generateExcel(List<OpsPurchaseorder> list) throws Exception {
		String template = "/ops/files/purchase/";
		// String template = "C:\\Users\\SMC892N\\Desktop\\";
		String fileName = "DelayPurchaseOrder";
		// 需要修改为 服务器生成文件路径
		String filePath = template + new DateTime().getYear() + "/delay";
		File file = createNewFile(template, fileName, filePath);
		String newPath = file.getPath();
		String fileEmail = file.getPath();
		FileInputStream fis = new FileInputStream(newPath);
		// xslx
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");

		int i = 0;
		for (OpsPurchaseorder item : list) {
			i = i + 1;
			cellView1(sheet, i, 0, item.getOrderno(), wb);
			cellView1(sheet, i, 1, item.getItemno().toString(), wb);
			if (item.getSplititemno() != null)
				cellView1(sheet, i, 2, item.getSplititemno().toString(), wb);
			cellView1(sheet, i, 3, item.getModelno(), wb);
			cellView1(sheet, i, 4, item.getQuantity().toString(), wb);
			cellView1(sheet, i, 5, item.getDeptno(), wb);
			if (StringUtils.isNotBlank(item.getCustomerno()))
				cellView1(sheet, i, 6, item.getCustomerno(), wb);
			if (StringUtils.isNotBlank(item.getUserno()))
				cellView1(sheet, i, 7, item.getCustomerno(), wb);
			cellView1(sheet, i, 8, new SimpleDateFormat("yyyy-MM-dd").format(item.getDlvmoddate()), wb);
			cellView1(sheet, i, 9, item.getSupplierid(), wb);
			cellView1(sheet, i, 10, item.getOrdtype(), wb);
			cellView1(sheet, i, 11, item.getPurchasetype(), wb);
			cellView1(sheet, i, 12, item.getStatecode(), wb);
			cellView1(sheet, i, 13, item.getReplyorderno(), wb);
			cellView1(sheet, i, 14, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getPurchasedate()), wb);
		}
		// 填充完毕,写文件
		FileOutputStream ops = new FileOutputStream(newPath);
		wb.write(ops);
		wb.close();
		ops.close();
		fis.close();
		return fileEmail;
	}

	private void cellView1(XSSFSheet sheet, int rn, int cn, String val, XSSFWorkbook wb) {

		if (StringUtils.isBlank(val))
			return;

		XSSFRow row = sheet.getRow(rn);
		if (row == null) {
			row = sheet.createRow(rn); // 该行无数据，创建行对象
		}
		XSSFCell cell = row.getCell(cn);
		if (cell == null) {
			cell = row.createCell(cn);
		}
		cell.setCellValue(val);

		// 此处由于数据较多时会报错说超过最大量，因此取消样式设置
		// XSSFCellStyle cellStyle = wb.createCellStyle();
		// cellStyle.setWrapText(true);

		// 设置边框
		// cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		// cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		// cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
	}

	public static File createNewFile(String template, String fileName, String filePath) throws Exception {
		// 读取模板，并赋值到新文件
		// 文件模板路径
		String path = (template + fileName + ".xlsx");
		File file = new File(path);

		String newFileName = fileName + "-" + (new SimpleDateFormat("yyMMddHHmmss")).format(new Date()) + ".xlsx";
		// 判断路径是否存在
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 写入到新的excel
		File newFile = new File(filePath, newFileName);

		newFile.createNewFile();
		// 复制模板到新文件
		fileChannelCopy(file, newFile);

		return newFile;
	}

	public static void fileChannelCopy(File s, File t) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(s), 1024);
				out = new BufferedOutputStream(new FileOutputStream(t), 1024);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(String path) {
		// 生成邮件实体内容
		MailDto mail = new MailDto();
		mail.setSubject("制造返信延期清单");
		// 发邮件给业务
		String to = null;
		String cc = null;
		ResultVo<List<DataCodeVO>> mailTo = dictDataServiceFeignApi.getDataCodes("3006");
		if (mailTo.isSuccess() && mailTo.getData() != null) {
			for (DataCodeVO v : mailTo.getData()) {
				if (StringUtils.equals("to", v.getCode())) {
					to = v.getCodeName();
					if (StringUtils.isNotBlank(v.getExtNote1())) {
						to = to + "," + v.getExtNote1();
					}
					if (StringUtils.isNotBlank(v.getExtNote2())) {
						to = to + "," + v.getExtNote2();
					}
					if (StringUtils.isNotBlank(v.getExtNote3())) {
						to = to + "," + v.getExtNote3();
					}
				}
				if (StringUtils.equals("cc", v.getCode())) {
					cc = v.getCodeName();
					if (StringUtils.isNotBlank(v.getExtNote1())) {
						cc = cc + "," + v.getExtNote1();
					}
					if (StringUtils.isNotBlank(v.getExtNote2())) {
						cc = cc + "," + v.getExtNote2();
					}
					if (StringUtils.isNotBlank(v.getExtNote3())) {
						cc = cc + "," + v.getExtNote3();
					}
				}
			}
		}
		if (StringUtils.isNotBlank(to)) {
			mail.setTo(to);
		} else
			mail.setTo("mateng@smc.com.cn");
		if (StringUtils.isNotBlank(cc)) {
			mail.setCc(cc);
		} else
			mail.setCc("mateng@smc.com.cn,hesai@smc.com.cn,dimiao@smc.com.cn,zhangjing@smc.com.cn");
		mail.setText("附件为制造返信延期清单，请查收！");
		// 需要发邮件的附件实体
		mail.setAttachments(path);
		CommonResult<String> result = opsMailApi.sendMailToDb(mail);
		if (!result.isSuccess()) {
			// 保存失败
			logger.error("制造返信延期邮件实体保存数据库失败！" + result.getMessage());

		}
	}

}
