package com.sales.ops.serviceimpl.purchase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.sales.ops.service.purchase.PurchaseImportService;

public class PurchaseImportServiceImpl implements PurchaseImportService {

	@Override
	public void invoiceImportJP() throws Exception {
		String encoding = "GBK";
		File file = new File("D:\\invoiceJP\\");
		// 判断文件是否存在
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {

			}
			read.close();
		}
	}

}
