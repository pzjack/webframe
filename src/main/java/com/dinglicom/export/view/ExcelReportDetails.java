/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dinglicom.export.view;

import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.reportnum.demain.ReportDetailRespItem;
import java.net.URLEncoder;
import java.util.Date;
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
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author panzhen
 */
public class ExcelReportDetails extends AbstractExcelView {

    private final static short ROW_HEIGT = 300;
    private final static short CLOUMN_WIDTH = 1000;
    
    private String getDateStr(Date d) {
        if(null != d) {
            return DateUtil.formatToDay(d);
        } else {
            return "";
        }
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String dt = DateUtil.formatToDay(new Date());
        String excelName = "报量明细" + getDateStr((Date)model.get("reportdate")) + ".xls";
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
        List<ReportDetailRespItem> data = null;
        if (null != model.get("reportnumberdetails")) {
            data = (List<ReportDetailRespItem>) model.get("reportnumberdetails");
        }

        // 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        HSSFCellStyle ccs = createContentStyle(workbook);
        HSSFCellStyle cts = createHeadStyle(workbook);

        HSSFRow header1 = sheet.createRow(0);// 第1行
        header1.setHeight((short) (ROW_HEIGT * 2));
        creatTextCell(header1, 0, "奶站名称", cts);
        creatTextCell(header1, 1, "商品类型", cts);
        creatTextCell(header1, 2, "商品编号", cts);
        creatTextCell(header1, 3, "商品名称", cts);
        creatTextCell(header1, 4, "商品单价", cts);
        creatTextCell(header1, 5, "商品报量", cts);
        creatTextCell(header1, 6, "商品金额", cts);
        sheet.setColumnWidth(0, CLOUMN_WIDTH * 10);
        sheet.setColumnWidth(1, CLOUMN_WIDTH * 4);
        sheet.setColumnWidth(2, CLOUMN_WIDTH * 4);
        sheet.setColumnWidth(3, CLOUMN_WIDTH * 4);
        sheet.setColumnWidth(4, CLOUMN_WIDTH * 4);
        sheet.setColumnWidth(5, CLOUMN_WIDTH * 4);
        sheet.setColumnWidth(6, CLOUMN_WIDTH * 4);
        if (null != data) {
            int rownum = 1;
            for (ReportDetailRespItem d : data) {
                HSSFRow row = sheet.createRow(rownum);
                row.setHeight((short) (ROW_HEIGT * 2));
                creatTextCell(row, 0, d.getOrg_name(), ccs);
                if(null != d.getPtype() && 1 == d.getPtype()) {
                    creatTextCell(row, 1, "瓶装奶", ccs);
                } else {
                    creatTextCell(row, 1, "酸奶", ccs);
                }
                creatTextCell(row, 2, "" + d.getPid(), ccs);
                creatTextCell(row, 3, d.getPname(), ccs);
                creatTextCell(row, 4, "" + d.getPrice(), ccs);
                creatTextCell(row, 5, "" + d.getPnum(), ccs);
                creatTextCell(row, 6, "" + d.getTotal_price(), ccs);
                
                rownum++;
            }
        }
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
        font.setFontHeightInPoints((short) 12);

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

}
