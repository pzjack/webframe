/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.export.view;

import com.dinglicom.reportform.domain.LineProductCountNoTotal;
import com.dinglicom.reportform.domain.LineProductTypeCount;
import com.dinglicom.reportform.domain.ProductCount;
import com.dinglicom.reportform.domain.StationData;
import com.dinglicom.reportform.domain.StationProductType;
import com.dinglicom.reportform.domain.StationResp;
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
public class ExcelStationExportView extends AbstractExcelView {

    private final static short ROW_HEIGT = 300;
    private final static short CLOUMN_WIDTH = 1000;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String dt = DateUtil.formatToDay(new Date());
        String excelName = "常规要货量" + model.get("reportdate") + ".xls";
        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开  
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
        StationResp data = null;
        if (null != model.get("reportlist")) {
            data = (StationResp) model.get("reportlist");
        }

        // 产生Excel表头  
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        HSSFCellStyle ccs = createContentStyle(workbook);
        HSSFCellStyle cts = createHeadStyle(workbook);

        HSSFRow header1 = sheet.createRow(0);// 第1行
        header1.setHeight((short) (ROW_HEIGT * 2));
        creatTextCell(header1, 0, model.get("reportdate") + " 常规要货量", cts);// 产生标题列
        if (null != data) {
            HSSFRow header2 = sheet.createRow(1); //产生标题列  第2行
            header2.setHeight((short) (ROW_HEIGT * 2));
            HSSFRow header3 = sheet.createRow(2); //产生标题列  第3行
            header3.setHeight((short) (ROW_HEIGT * 2));
            
            creatTextCell(header2, 0, "序号", cts);
            creatTextCell(header3, 0, "", cts);
            sheet.addMergedRegion(new Region(1, (short)0, 2, (short)0));//指定合并区域
            sheet.setColumnWidth(0, CLOUMN_WIDTH * 3);
            
            creatTextCell(header2, 1, "客户", cts);
            creatTextCell(header3, 1, "", cts);
            sheet.addMergedRegion(new Region(1, (short)1, 2, (short)1));//指定合并区域
            sheet.setColumnWidth(1, CLOUMN_WIDTH * 10);
            
            List<StationProductType> products = data.getProducts();
            if(null != products && products.size() > 0) {
                int p = 2;
                for(StationProductType pt : products) {
                    int p1 = p;
                    List<LineProductItem> detail = pt.getDetail();
                    for(int i = 0; i < detail.size(); i++) {
                        creatTextCell(header2, p + i, "", cts);
                        sheet.setColumnWidth(p + i, CLOUMN_WIDTH * 3);
                        creatTextCell(header3, p + i, detail.get(i).getPname(), cts);
                    }
                    creatTextCell(header2, p, pt.getCname(), cts);
                    p += detail.size() - 1;
                    sheet.addMergedRegion(new Region(1, (short)p1, 1, (short)(p)));//指定合并区域
                    p++;
                }
                creatTextCell(header2, p, "合计", cts);
                creatTextCell(header3, p, "", cts);
                sheet.addMergedRegion(new Region(1, (short) p, 2, (short) p));//指定合并区域
                sheet.setColumnWidth(p, CLOUMN_WIDTH * 3);
                
                sheet.addMergedRegion(new Region(0, (short) 0, 0, (short)p));//指定合并区域
            }
            
            List<StationData> dtm = data.getData();
            int rownum = 3;
            for(StationData ld : dtm) {
                HSSFRow row = sheet.createRow(rownum);
                row.setHeight((short) (ROW_HEIGT * 2));
                creatTextCell(row, 0, "" + (rownum - 2), ccs);//第一列,序号
                creatTextCell(row, 1, ld.getStation(), ccs);//第一列,序号
                List cat = ld.getCat();
                Map<Integer,Map<Long,Long>> ptm = new HashMap<Integer,Map<Long,Long>>();
                for(Object o : cat) {
                    if(o instanceof LineProductTypeCount) {
                        LineProductTypeCount pn = (LineProductTypeCount)o;
                        Map<Long, Long> pm = ptm.get(pn.getCid());
                        if(null == pm) {
                            pm = new HashMap<Long, Long>();
                            ptm.put(pn.getCid(), pm);
                        }
                        pm.put(0L, pn.getTotal_num());
                        List<ProductCount> plist = pn.getDetail();
                        for(ProductCount pc : plist) {
                            pm.put(pc.getPid(), pc.getNum());
                        }
                    } else if(o instanceof LineProductCountNoTotal) {
                        LineProductCountNoTotal pn = (LineProductCountNoTotal)o;
                        Map<Long, Long> pm = ptm.get(pn.getCid());
                        if(null == pm) {
                            pm = new HashMap<Long, Long>();
                            ptm.put(pn.getCid(), pm);
                        }
                        List<ProductCount> plist = pn.getDetail();
                        for(ProductCount pc : plist) {
                            pm.put(pc.getPid(), pc.getNum());
                        }
                    }
                }
                
                int p = 2;
                for(StationProductType pt : products) {
                    Map<Long, Long> pm = ptm.get(pt.getCid());
                    List<LineProductItem> detail = pt.getDetail();
                    for(int i = 0; i < detail.size(); i++) {
                        if(null != pm) 
                            creatTextCell(row, p + i, "" + (pm.get(detail.get(i).getPid())), ccs);
                        else
                            creatTextCell(row, p + i, "", ccs);
                    }
                    p += detail.size() - 1;
                    p++;
                }
                creatTextCell(row, p, "" + ld.getTotal_num(), ccs);
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
