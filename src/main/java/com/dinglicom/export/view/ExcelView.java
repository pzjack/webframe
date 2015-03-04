/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.export.view;

import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.reportnum.demain.WebReportlistDetailItem;
import com.dinglicom.reportnum.demain.WebReportlistItemResp;
import com.dinglicom.reportnum.demain.WebReportlistResp;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author panzhen
 */
public class ExcelView extends AbstractExcelView {

    private final static short ROW_HEIGT = 300;
    private final static short CLOUMN_WIDTH = 1000;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String dt = DateUtil.formatToDay(new Date());
        String excelName = "日报表" + model.get("reportdate") + ".xls";
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        WebReportlistResp data = null;
        if (null != model.get("reportlist")) {
            data = (WebReportlistResp) model.get("reportlist");
        }

        // 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        HSSFCellStyle ccs = createContentStyle(workbook);
        HSSFCellStyle cts = createHeadStyle(workbook);
//        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow header1 = sheet.createRow(0);// 第1行
        header1.setHeight(ROW_HEIGT);
        creatTextCell(header1, 0, "日报表:" + model.get("reportdate"), cts);// 产生标题列 
        if (null != data) {
            HSSFRow header2 = sheet.createRow(1); //产生标题列  第2行
            header2.setHeight(ROW_HEIGT);
            if (null != data.getProducts()) {
                sheet.setColumnWidth(0, CLOUMN_WIDTH * 3);
                sheet.setColumnWidth(1, CLOUMN_WIDTH * 10);
                creatTextCell(header2, 0, "序号", cts);
                creatTextCell(header2, 1, "奶站", cts);
                int i = 2;
                for (ProductItem it : data.getProducts()) {
                    creatTextCell(header2, i, it.getPname(), cts);
                    sheet.setColumnWidth(i, CLOUMN_WIDTH * 3);
                    i++;
                }
                sheet.setColumnWidth(i, CLOUMN_WIDTH * 5);
                creatTextCell(header2, i, "总计", cts);
                sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (2 + data.getProducts().size())));//指定合并区域
            }
            List<WebReportlistItemResp> list = data.getData();

            if (null != list && list.size() > 0) {
                int d = 2;
                for (WebReportlistItemResp item : list) {
                    HSSFRow dline = sheet.createRow(d);
                    dline.setHeight(ROW_HEIGT);
                    creatTextCell(dline, 0, "" + (d - 1), ccs);
                    creatTextCell(dline, 1, item.getOrg_name(), ccs);
                    List<WebReportlistDetailItem> pls = item.getDetail();
                    if (null != pls) {
                        Map<Long, WebReportlistDetailItem> m = new HashMap<Long, WebReportlistDetailItem>();
                        for (WebReportlistDetailItem im : pls) {
                            m.put(im.getPid(), im);
                        }
                        int i = 2;
                        for (ProductItem it : data.getProducts()) {
                            WebReportlistDetailItem im = m.get(it.getPid());
                            if (null != im) {
                                creatTextCell(dline, i, null == im.getReport_num() ? "" : im.getReport_num().toString(), ccs);
                            } else {
                                creatTextCell(dline, i, "", ccs);
                            }
                            i++;
                        }
                    }
                    creatTextCell(dline, data.getProducts().size() + 2, null == item.getTotal_amount() ? "" : item.getTotal_amount().toString(), ccs);
                    d++;
                }
            }
        }
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));
//        cell = getCell(sheet, 0, 0);
//        setText(cell, "Spring-Excel 测试");
        // 填充数据  
//        int rowNum = 1;
//        for (Iterator iter = stuList.iterator(); iter.hasNext();) {  
//            Student element = (Student) iter.next();  
//            HSSFRow row = sheet.createRow(rowNum++);  
//            row.createCell((short) 0)  
//                    .setCellValue(element.getName().toString());  
//            row.createCell((short) 1).setCellValue(element.getSex().toString());  
//            row.createCell((short) 2)  
//                    .setCellValue(element.getDate().toString());  
//            row.getCell((short) 2).setCellStyle(cellStyle);  
//            row.createCell((short) 3).setCellValue(element.getCount());  
//        }  

        // 列总和计算  
//        HSSFRow row = sheet.createRow(rowNum);
//        row.createCell((short) 0).setCellValue("TOTAL:");
//        if (rowNum > 1) {
//            String formual = "SUM(D2:D" + rowNum + ")"; // D2到D[rowNum]单元格起(count数据)  
//            row.createCell((short) 3).setCellFormula(formual);
//        }
    }

    private void creatTextCell(HSSFRow row, int column, String text, HSSFCellStyle style) {
        HSSFCell hc1 = row.createCell(column);
        hc1.setCellStyle(style);
        hc1.setCellValue(text);
    }

    private static HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        style.setFont(font);
        return style;
    }

    private static HSSFCellStyle createHeadStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        style.setFont(font);
        return style;
    }

//    private void creatDateCell(HSSFRow row, int column, Date d) {
//        HSSFCell hc1 = row.createCell(column);
//        hc1.setCellValue(text);
//    }
}
