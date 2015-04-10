/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.export.view;

import com.dinglicom.reportform.domain.LineProductCountNoTotal;
import com.dinglicom.reportform.domain.LineProductTypeCount;
import com.dinglicom.reportform.domain.ProductCount;
import com.dinglicom.reportform.domain.SaleData;
import com.dinglicom.reportform.domain.SaleStation;
import com.dinglicom.reportform.domain.SalemanResp;
import com.dinglicom.reportform.domain.StationData;
import com.dinglicom.reportform.domain.StationProductType;
import com.dinglicom.reportnum.demain.LineProductItem;
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
public class ExcelSalesmanExportView extends AbstractExcelView {

    private final static short ROW_HEIGT = 300;
    private final static short CLOUMN_WIDTH = 1000;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String dt = DateUtil.formatToDay(new Date());
        String excelName = "日均任务及出库指标" + model.get("reportdate") + ".xls";
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
        SalemanResp data = null;
        if (null != model.get("reportlist")) {
            data = (SalemanResp) model.get("reportlist");
        }

        // 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        HSSFCellStyle ccs = createContentStyle(workbook);
        HSSFCellStyle cts = createHeadStyle(workbook);

        HSSFRow header1 = sheet.createRow(0);// 第1行
        header1.setHeight((short) (ROW_HEIGT * 2));
        creatTextCell(header1, 0, model.get("reportdate") + " 日均任务及出库指标", cts);// 产生标题列
        if (null != data) {
            HSSFRow header2 = sheet.createRow(1); //产生标题列  第2行
            header2.setHeight((short) (ROW_HEIGT * 2));
            
            creatTextCell(header2, 0, "区域经理", cts);
            sheet.setColumnWidth(0, CLOUMN_WIDTH * 3);
            
            creatTextCell(header2, 1, "客户名称", cts);
            sheet.setColumnWidth(1, CLOUMN_WIDTH * 10);
            
            List<LineProductItem> detail = data.getProducts();
            for (int i = 0; i < detail.size(); i++) {
                creatTextCell(header2, 2 + i, detail.get(i).getPname(), cts);
                sheet.setColumnWidth(2 + i, CLOUMN_WIDTH * 3);
            }

            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short)(1 + detail.size())));//指定合并区域

            List<SaleData> dtm = data.getData();
            int rownum = 2;
            for(SaleData ld : dtm) {
                int p = rownum;
                HSSFRow row = sheet.createRow(rownum);
                row.setHeight((short) (ROW_HEIGT * 2));
                creatTextCell(row, 0, ld.getSalesman(), ccs);//第一列,销售员
                List<SaleStation> stations = ld.getDetail();
                boolean firstline = true;
                for(SaleStation station : stations) {
                    if(!firstline) {
                        row = sheet.createRow(rownum);
                        row.setHeight((short) (ROW_HEIGT * 2));
                    }
                    firstline = false;
                    creatTextCell(row, 1, station.getStation(), ccs);//第二列,奶站
                    List<ProductCount> plist = station.getProducts();
                    Map<Long,Long> pm = new HashMap<Long,Long>();
                    for(ProductCount pc : plist) {
                        pm.put(pc.getPid(), pc.getNum());
                    }
                    
                    for (int i = 0; i < detail.size(); i++) {
                        creatTextCell(row, 2 + i, "" + pm.get(detail.get(i).getPid()), ccs);
                        row.setHeight((short) (ROW_HEIGT * 2));
                    }
                    rownum++;
                }
                sheet.addMergedRegion(new Region(p, (short) 0, rownum - 1, (short)0));//指定合并区域
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
