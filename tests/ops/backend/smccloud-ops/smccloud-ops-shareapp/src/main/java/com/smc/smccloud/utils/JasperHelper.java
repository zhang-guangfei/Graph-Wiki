package com.smc.smccloud.utils;

import com.smc.smccloud.core.model.constants.GlobalConstant;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Author: Denghui
 * Date: 2021-06-09 09:32
 * Description:
 */
public class JasperHelper {

    /**
     * @param fileName       文件名称
     * @param inputStream    模板
     * @param response       HttpServletResponse
     * @param params         Parameters
     * @param beanCollection Fields
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

    private static void writerToResponse(String fileName, JasperPrint jasperPrint, HttpServletResponse response) throws IOException, JRException {
        OutputStream out = response.getOutputStream();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        out.flush();
    }

    /**
     * @param inputStream    模板
     * @param localPath      localPath
     * @param params         Parameters
     * @param beanCollection Fields
     * @throws JRException
     * @throws IOException
     */
    public static void savePdf(InputStream inputStream, String localPath,
                               Map<String, Object> params, Collection<?> beanCollection) throws JRException, IOException {

        JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        saveOnLocal(jasperPrint, localPath);
    }

    private static void saveOnLocal(JasperPrint jasperPrint, String localPath) throws IOException, JRException {
        FileOutputStream out = new FileOutputStream(new File(localPath));
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
        out.flush();
    }


    /**
     * @param inputStream    模板
     * @param params         Parameters
     * @param beanCollection Fields
     * @return pdf InputStream
     * @throws JRException
     */
    public static InputStream savePdfToInputStrem(InputStream inputStream, Map<String, Object> params, Collection<?> beanCollection) throws JRException {

        JRDataSource dataSource = new JRBeanCollectionDataSource(beanCollection);
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
    }

}