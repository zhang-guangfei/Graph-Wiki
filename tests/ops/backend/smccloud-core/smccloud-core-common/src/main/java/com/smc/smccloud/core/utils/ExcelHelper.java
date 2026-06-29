package com.smc.smccloud.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author edp02 @Date 2021/10/19 11:31
 */
@Slf4j
public class ExcelHelper {

    private SXSSFWorkbook workBook;
    /**
     * XSSFSheet
     */
    private Sheet sheet;
    private int sheetIndex;
    private Font font;
    private Row openRow;

    public Workbook getWorkBook() {
        return this.workBook;
    }

    public void setWorkBook(SXSSFWorkbook workBook) {
        this.workBook = workBook;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public int getSheetIndex() {
        return this.sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public ExcelHelper() {
        this.workBook = new SXSSFWorkbook(1000);
        this.sheet = workBook.createSheet();
        this.sheetIndex = 0;
    }

    public ExcelHelper(String path) {
        try {
            // FileInputStream fis = new FileInputStream(new File(path));

            // this.workBook = new XSSFWorkbook(fis);
            // this.workBook = new SXSSFWorkbook(getXSSFWorkbook(path), 100);

            SXSSFWorkbook wb = new SXSSFWorkbook(getXSSFWorkbook(path), 100);
            wb.setCompressTempFiles(true);
            this.workBook = wb;
            this.sheet = workBook.getSheetAt(0);
            workBook.setForceFormulaRecalculation(true); // 计算公式
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelHelper(InputStream is) {
        try {
            // this.workBook = WorkbookFactory.create(is);
            XSSFWorkbook wb = new XSSFWorkbook(is);
            SXSSFWorkbook swb = new SXSSFWorkbook(wb, 100);
            swb.setCompressTempFiles(true);
            this.workBook = swb;
            // SXSSFSheet sxssfSheet = (SXSSFSheet) swb.getSheetAt(0);
            // sxssfSheet.setRandomAccessWindowSize(-1);
            this.sheet = swb.getXSSFWorkbook().getSheetAt(0);
            this.sheetIndex = 0;
            workBook.setForceFormulaRecalculation(true); // 计算公式
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static XSSFWorkbook getXSSFWorkbook(String filePath) {
        XSSFWorkbook workbook = null;
        // BufferedOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
//            File fileXlsxPath = new File(filePath);
//            inputStream = new FileInputStream(fileXlsxPath);
            // outputStream = new BufferedOutputStream(inputStream);
            inputStream = FileUtil.getTemplate(filePath);
            workbook = new XSSFWorkbook(inputStream);
            // workbook.createSheet("Sheet1");
            // workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    public Integer getCellInteger(Cell cell){
        String cellString = getCellString(cell).trim();
        if(StringUtils.isEmpty(cellString)){
            return 0;
        }else {
            return Integer.parseInt(cellString);
        }
    }

    public Long getCellLong(Cell cell){
        String cellString = getCellString(cell).trim();
        if(StringUtils.isEmpty(cellString)){
            return 0L;
        }else {
            return Long.parseLong(cellString);
        }
    }

    public BigDecimal getCellBigDecimal(Cell cell){
        String cellString = getCellString(cell).trim();
        if(StringUtils.isEmpty(cellString)){
            return BigDecimal.ZERO;
        }else {
            return new BigDecimal(cellString);
        }
    }

    public String getCellString(Cell cell) {
        if (cell == null)
            return "";
        String cellString;
        switch (cell.getCellType()) {
            case STRING:
                cellString = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) { // 日期型
                    cellString = DateUtil.dateToDateString(cell.getDateCellValue());
                } else {
                    cellString = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellString = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                try {
                    cellString = cell.getStringCellValue();
                } catch (Exception e) {
                    cellString = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case BLANK:
                cellString = "";
                break;
            case ERROR:
                cellString = "";
                break;
            default:
                cellString = "";
                break;
        }
        return cellString;
    }

    public void openSheet(Integer sheetIndex) {
        this.setSheet(workBook.getXSSFWorkbook().getSheetAt(sheetIndex));
        this.setSheetIndex(sheetIndex);
    }

    public void openRow(Integer rownum) {
        this.openRow = this.sheet.createRow(rownum);
    }

    public void createSheet(String sheetName) {
        Sheet sheet = workBook.getSheet(sheetName);
        if (sheet == null) {
            workBook.createSheet(sheetName);
        }
    }

    public void updsheetName(Integer sheetIndex, String sheetName) {
        workBook.setSheetName(sheetIndex, sheetName);
    }

    public Cell getCell(int rowIndex, int colIndex) {

        if (openRow == null) {
            Row hsRow = sheet.getRow(rowIndex);
            if (hsRow == null) {
                hsRow = sheet.createRow(rowIndex);
            }
            Cell hscell = hsRow.getCell(colIndex);
            if (hscell == null) {
                hscell = hsRow.createCell(colIndex);
            }
            return hscell;
        } else {
            Cell cell = openRow.getCell(colIndex);
            if (cell == null) {
                cell = openRow.createCell(colIndex);
            }
            return cell;
        }
    }

    public Cell getCell(int colIndex) {
        Cell cell = openRow.getCell(colIndex);
        if (cell == null) {
            cell = openRow.createCell(colIndex);
        }
        return cell;
    }

    public void setCellFont(int row, int col, Font font) {
        // 设置字体颜色 可参考 font.setColor(IndexedColors.RED.getIndex());
        Cell cell = getCell(row, col);
        // 解决单元格样式覆盖的问题
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    public void setCellBGColor(int row, int col, short bgColorIndex) {
        font = workBook.createFont();
        font.setFontHeightInPoints(Short.parseShort("9"));
        font.setFontName("宋体");
        Cell cell = getCell(row, col);
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(bgColorIndex); // 设置单元格背景颜色
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * @param row          行
     * @param col          列
     * @param font         字体
     * @param borderStyles 边框类型 [上,下,左,右]
     */
    public void setCellStyle(int row, int col, Font font, Short[] borderStyles) {
        Cell cell = getCell(row, col);
        CellStyle cellStyle = workBook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true); // 自动换行
        if (font != null) {
            cellStyle.setFont(font);
        }
        if (ArrayUtils.isNotEmpty(borderStyles)) {
            if (borderStyles[0] != null) {
                cellStyle.setBorderTop(BorderStyle.valueOf(borderStyles[0]));
            }
            if (borderStyles[1] != null) {
                cellStyle.setBorderBottom(BorderStyle.valueOf(borderStyles[1]));
            }
            if (borderStyles[2] != null) {
                cellStyle.setBorderLeft(BorderStyle.valueOf(borderStyles[2]));
            }
            if (borderStyles[3] != null) {
                cellStyle.setBorderRight(BorderStyle.valueOf(borderStyles[3]));
            }
        }
        cell.setCellStyle(cellStyle);
    }

    public void setCellDataFormat(int row, int col, String format) {
        if (!format.isEmpty()) {
            Cell cell = getCell(row, col);
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            DataFormat dataFormat = workBook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat(format));
            cell.setCellStyle(cellStyle);
        }
    }

    public void setCellValue(int rowIndex, int colIndex, String str) {
        Cell cell = getCell(rowIndex, colIndex);
        cell.setCellValue(str);
    }

    public void setCellValue(int rowIndex, int colIndex, Integer value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public void setCellValue(int colIndex, String str) {
        Cell cell = getCell(colIndex);
        cell.setCellValue(str);
    }

    public void setCellValue(int colIndex, Integer value) {
        Cell cell = getCell(colIndex);
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public void setCellValue(int rowIndex, int colIndex, Long value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            cell.setCellValue(value);
        }
    }

    public void setCellValue(int rowIndex, int colIndex, Double value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            // 约束数值精度
            DecimalFormat df = new DecimalFormat("#0.000");
            String val = df.format(value);
            cell.setCellValue(Double.parseDouble(val));
        }
    }

    public void setCellValue(int rowIndex, int colIndex, Float value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            // 约束数值精度
            DecimalFormat df = new DecimalFormat("#0.000");
            String val = df.format(value);
            cell.setCellValue(Double.parseDouble(val));
        }

    }

    public void setCellValue(int rowIndex, int colIndex, BigDecimal value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            // 约束数值精度
            DecimalFormat df = new DecimalFormat("#0.000");
            String val = df.format(value.doubleValue());
            cell.setCellValue(Double.parseDouble(val));
        }

    }

    public void setCellValue(int rowIndex, int colIndex, Date value) {
        Cell cell = getCell(rowIndex, colIndex);
        if (value != null) {
            cell.setCellValue(DateUtil.dateToDateString(value));
        }
    }

    public void setCellValue(int colIndex, Date value) {
        Cell cell = getCell(colIndex);
        if (value != null) {
            cell.setCellValue(DateUtil.dateToDateString(value));
        }
    }

    public void setCellFormula(int rowIndex, int colIndex, String formula) {
        Cell cell = getCell(rowIndex, colIndex);
        cell.setCellFormula(formula);
    }

    public void save(String path) throws IOException {
        // 写文件
        BufferedOutputStream fos = null;
        try {
            fos = new BufferedOutputStream(new FileOutputStream(path), 2048);
            workBook.write(fos);
        } finally {
            try {
                if (workBook != null) {
                    workBook.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void writeToResponse(HttpServletResponse response, String fileName) {
        OutputStream os = null;
        try {
            response.resetBuffer();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            os = response.getOutputStream();
            workBook.write(os);
        } catch (IOException e) {
            log.error("Excel writeToResponse failed: {}", e.getMessage(), e);
        } finally {
            try {
                if (workBook != null) {
                    workBook.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                log.error("Excel writeToResponse close IO failed: {}", e.getMessage(), e);
            }
        }
    }

    public InputStream convertTo() {
        return this.writeStream();
    }

    public InputStream writeStream() {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            workBook.write(baos);
            baos.flush();
            byte[] b = baos.toByteArray();
            is = new ByteArrayInputStream(b);
        } catch (IOException e) {
            log.error("Excel convertTo InputStream failed: {}", e.getMessage(), e);
        } finally {
            try {
                if (workBook != null) {
                    workBook.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return is;
    }

    public InputStream writeZipStream() {
        try {
            return getCompressed(writeStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getCompressed(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ZipOutputStream zos = new ZipOutputStream(bos);
        zos.putNextEntry(new ZipEntry("data.xlsx"));

        int count;
        byte data[] = new byte[2048];
        BufferedInputStream entryStream = new BufferedInputStream(is, 2048);
        while ((count = entryStream.read(data, 0, 2048)) != -1) {
            zos.write(data, 0, count);
        }
        entryStream.close();

        zos.closeEntry();
        zos.close();

        return new ByteArrayInputStream(bos.toByteArray());
    }

    public int getColNum(String col) {
        col = col.toUpperCase();
        int colNum = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            colNum += (cs[i] - 64) * Math.pow(26, (cs.length - i - 1));
        }
        return colNum;
    }

    /**
     * 列下标 --> 列编号 (0-->A, 26-->AA)
     *
     * @param colIndex
     * @return
     */
    public String getColNo(int colIndex) {
        String colNo = "";
        int quotient = colIndex / 26;
        int remainder = colIndex % 26;
        if (quotient > 0) {
            char c1 = (char) (quotient - 1 + 'A');
            colNo = String.valueOf(c1);
        }
        char c2 = (char) (remainder + 'A');
        colNo = colNo + String.valueOf(c2);
        return colNo;
    }
}