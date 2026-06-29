package com.sales.ops.serviceimpl.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Author: Denghui Date: 2021-06-09 09:32 Description:
 */
public class JasperHelper {

	private final static Logger logger = LoggerFactory.getLogger(JasperHelper.class);

	/**
	 * @param fileName
	 *            文件名称
	 * @param inputStream
	 *            模板
	 * @param response
	 *            HttpServletResponse
	 * @param params
	 *            Parameters
	 * @param beanCollection
	 *            Fields
	 * @throws JRException
	 * @throws IOException
	 */
	public static void showPdf(String fileName, InputStream inputStream, HttpServletResponse response,
			Map<String, Object> params, Collection<?> beanCollection) throws JRException, IOException {
		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
			writerToResponse(fileName, jasperPrint, response);
		} catch (Exception ex) {
			System.out.println("showPdf.....");
			System.out.println(ex.toString());
		}

	}

	private static void writerToResponse(String fileName, JasperPrint jasperPrint, HttpServletResponse response)
			throws IOException, JRException {
		OutputStream out = response.getOutputStream();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		out.flush();
	}

	/**
	 * @param inputStream
	 *            模板
	 * @param localPath
	 *            localPath
	 * @param params
	 *            Parameters
	 * @param beanCollection
	 *            Fields
	 * @throws JRException
	 * @throws IOException
	 */
	public static void savePdf(InputStream inputStream, String localPath, Map<String, Object> params,
			Collection<?> beanCollection, String localName) throws JRException, IOException {

		JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
		saveOnLocal(jasperPrint, localPath, localName);
	}

	private static void saveOnLocal(JasperPrint jasperPrint, String localPath, String localName)
			throws IOException, JRException {
		File dir = new File(localPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// bug14381 等待pdf文件完全生成后再返回
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outstream);
		FileOutputStream out = new FileOutputStream(new File(localPath + localName));
		outstream.writeTo(out);
		out.flush();
		out.close();
		logger.info("po pdf end");
	}

	/**
	 * 保存为EXCEL文件
	 * 
	 * @param inputStream
	 * @param localPath
	 * @param params
	 * @param beanCollection
	 * @param localName
	 * @throws JRException
	 * @throws IOException
	 */
	public static void saveExcel(InputStream inputStream, String localPath, Map<String, Object> params,
			Collection<?> beanCollection, String localName) throws JRException, IOException {
		File dir = new File(localPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
		SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration();
		conf.setWhitePageBackground(true);
		JRXlsxExporter export = new JRXlsxExporter();
		export.setConfiguration(conf);
		ExporterInput input = new SimpleExporterInput(jasperPrint);
		export.setExporterInput(input);
		OutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(localPath + localName);
		export.setExporterOutput(output);
		export.exportReport();
	}

	/**
	 * @param inputStream
	 *            模板
	 * @param params
	 *            Parameters
	 * @param beanCollection
	 *            Fields
	 * @return pdf InputStream
	 * @throws JRException
	 */
	public static InputStream savePdfToInputStrem(InputStream inputStream, Map<String, Object> params,
			Collection<?> beanCollection) throws JRException {

		JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
		return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
	}

}