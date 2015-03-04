/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.export.view;

import com.dinglicom.reportnum.demain.LineDataItem;
import com.dinglicom.reportnum.demain.LineProductItem;
import com.dinglicom.reportnum.demain.LineProductNum;
import com.dinglicom.reportnum.demain.LineResp;
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
public class LineExcelView extends AbstractExcelView {

    private final static short ROW_HEIGT = 300;
    private final static short CLOUMN_WIDTH = 1000;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String dt = DateUtil.formatToDay(new Date());
        String excelName = "线路送货明细" + model.get("reportdate") + ".xls";
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        LineResp data = null;
        if (null != model.get("linereportlist")) {
            data = (LineResp) model.get("linereportlist");
        }

        // 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        HSSFCellStyle ccs = createContentStyle(workbook);
        HSSFCellStyle cts = createHeadStyle(workbook);

        HSSFRow header1 = sheet.createRow(0);// 第1行
        header1.setHeight((short)(ROW_HEIGT * 2));
        creatTextCell(header1, 0, model.get("reportdate") + " 销售明细", cts);// 产生标题列
        if (null != data) {
            HSSFRow header2 = sheet.createRow(1); //产生标题列  第2行
            header2.setHeight((short)(ROW_HEIGT * 2));
            creatTextCell(header2, 0, "序号", cts);
            creatTextCell(header2, 1, "", cts);
            sheet.setColumnWidth(0, CLOUMN_WIDTH * 3);
            sheet.setColumnWidth(1, CLOUMN_WIDTH * 10);
            int gl = 0;
            if (null != data.getGeneral_type() && data.getGeneral_type().size() > 0) {
                gl = data.getGeneral_type().size();
                creatTextCell(header2, 2, "瓶装奶", cts);
                for (int j = 3; j < 1 + gl; j++) {
                    creatTextCell(header2, j, "", cts);
                }
                sheet.addMergedRegion(new Region(1, (short) 2, 1, (short) (1 + gl)));//指定合并区域
                creatTextCell(header2, 2 + gl, "", cts);
            }
            int ll = 0;
            if (null != data.getLow_type() && data.getLow_type().size() > 0) {
                creatTextCell(header2, (gl > 0 ? gl + 1 : 0) + 2, "酸奶", cts);
                ll = data.getLow_type().size();
                for (int j = 3 + (gl > 0 ? gl + 1 : 0); j < 2 + (gl > 0 ? gl + 1 : 0) + ll; j++) {
                    creatTextCell(header2, j, "", cts);
                }
                sheet.addMergedRegion(new Region(1, (short) (2 + (gl > 0 ? gl + 1 : 0)), 1, (short) (1 + (gl > 0 ? gl + 1 : 0) + ll)));//指定合并区域
            }
            creatTextCell(header2, 2 + (gl > 0 ? gl + 1 : 0) + ll, "线路", cts);
            sheet.setColumnWidth(2 + (gl > 0 ? gl + 1 : 0) + ll, CLOUMN_WIDTH * 4);
            creatTextCell(header2, 3 + (gl > 0 ? gl + 1 : 0) + ll, "收款价格", cts);
            sheet.setColumnWidth(3 + (gl > 0 ? gl + 1 : 0) + ll, CLOUMN_WIDTH * 4);
            creatTextCell(header2, 4 + (gl > 0 ? gl + 1 : 0) + ll, "业务区域", cts);
            sheet.setColumnWidth(4 + (gl > 0 ? gl + 1 : 0) + ll, CLOUMN_WIDTH * 4);
            creatTextCell(header2, 5 + (gl > 0 ? gl + 1 : 0) + ll, "外阜", cts);
            sheet.setColumnWidth(5 + (gl > 0 ? gl + 1 : 0) + ll, CLOUMN_WIDTH * 4);
            creatTextCell(header2, 6 + (gl > 0 ? gl + 1 : 0) + ll, "报量区域", cts);
            sheet.setColumnWidth(6 + (gl > 0 ? gl + 1 : 0) + ll, CLOUMN_WIDTH * 4);

            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (6 + (gl > 0 ? gl + 1 : 0) + ll)));//指定合并区域

            HSSFRow header3 = sheet.createRow(2); //产生标题列  第3行
            header3.setHeight((short)(ROW_HEIGT * 2));
            creatTextCell(header3, 0, "序号", cts);
            creatTextCell(header3, 1, "店名", cts);

            int i = 2;
            if (null != data.getGeneral_type()) {
                for (LineProductItem it : data.getGeneral_type()) {
                    creatTextCell(header3, i, it.getPname(), cts);
                    sheet.setColumnWidth(i, CLOUMN_WIDTH * 3);
                    i++;
                }

                creatTextCell(header3, i, "合计", cts);
                sheet.setColumnWidth(i, CLOUMN_WIDTH * 3);
                i++;
            }
            if (null != data.getLow_type()) {
                for (LineProductItem it : data.getLow_type()) {
                    creatTextCell(header3, i, it.getPname(), cts);
                    sheet.setColumnWidth(i, CLOUMN_WIDTH * 3);
                    i++;
                }
            }
            List<LineDataItem> list = data.getData();

            if (null != list && list.size() > 0) {
                int d = 3;
                for (LineDataItem item : list) {
                    HSSFRow dline = sheet.createRow(d);
                    dline.setHeight((short)(ROW_HEIGT * 2));
                    creatTextCell(dline, 0, "" + item.getOrg_id(), ccs);
                    creatTextCell(dline, 1, item.getOrg_name(), ccs);
                    List<LineProductNum> pls = item.getGeneral_type();
                    i = 2;
                    Map<Long, LineProductNum> m = new HashMap<Long, LineProductNum>();
                    if (null != pls) {
                        for (LineProductNum im : pls) {
                            m.put(im.getPid(), im);
                        }
                    }
                    long ct = 0;
                    for (LineProductItem it : data.getGeneral_type()) {
                        LineProductNum im = m.get(it.getPid());
                        if (null != im) {
                            ct += (null == im.getPnum() ? 0 : im.getPnum());
                            creatTextCell(dline, i, null == im.getPnum() ? "" : im.getPnum().toString(), ccs);
                        } else {
                            creatTextCell(dline, i, "", ccs);
                        }
                        i++;
                    }
                    if(ct != 0) {
                        creatTextCell(dline, i, "" + ct, ccs);
                    } else {
                        creatTextCell(dline, i, "", ccs);
                    }
                    i++;

                    m.clear();
                    pls = item.getLow_type();
                    if (null != pls) {
                        for (LineProductNum im : pls) {
                            m.put(im.getPid(), im);
                        }
                    }
                    for (LineProductItem it : data.getLow_type()) {
                        LineProductNum im = m.get(it.getPid());
                        if (null != im) {
                            creatTextCell(dline, i, null == im.getPnum() ? "" : im.getPnum().toString(), ccs);
                        } else {
                            creatTextCell(dline, i, "", ccs);
                        }
                        i++;
                    }
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
