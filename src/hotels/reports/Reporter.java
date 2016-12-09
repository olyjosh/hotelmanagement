/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.reports;

/**
 *
 * @author NOVA
 */
import hotels.util.State;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.dynamicreports.examples.Templates;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.data.DataSourceCollection;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Reporter {

    private CurrencyType currencyType;
    private StyleBuilder boldStyle;
    private StyleBuilder boldCenteredStyle;
    private StyleBuilder columnTitleStyle;
    private StyleBuilder titleStyle;
    
    public Reporter() {
        settings();
    }

    private void settings(){
        currencyType = new CurrencyType();
        boldStyle = stl.style().bold();
        boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
        columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.CYAN);
        titleStyle = stl.style(boldCenteredStyle)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setFontSize(15);
    }
    
    
    //Setting Report Template Components and their LAF
    public JasperViewer buildNight(JSONObject source) {
        //                                                           title,     field name     data type
        
        TextColumnBuilder<String> deptColumn = col.column("Departments", "dept", type.stringType()).setStyle(boldStyle);
        TextColumnBuilder<Integer> paymentsColumn = col.column("No of Payments", "payment", type.integerType());
        TextColumnBuilder<BigDecimal> amount = col.column("Total Sales Amount", "total", currencyType);

        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.")
                //sets the fixed width of a column, width = 2 * character width
                .setFixedColumns(2)
                .setHorizontalTextAlignment(HorizontalTextAlignment.JUSTIFIED);
//		Bar3DChartBuilder itemChart = cht.bar3DChart()
//		                                 .setTitle("Sales by item")
//		                                 .setCategory(itemColumn)
//		                                 .addSerie(
//		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
//		Bar3DChartBuilder itemChart2 = cht.bar3DChart()
//		                                 .setTitle("Sales by item")
//		                                 .setCategory(itemColumn)
//		                                 .setUseSeriesAsCategory(true)
//		                                 .addSerie(
//		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
        ColumnGroupBuilder itemGroup = grp.group(deptColumn);
        itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));

        try {
            JasperPrint jasperPrint = report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
                    .columns(//add columns
                            rowNumberColumn, deptColumn, paymentsColumn, amount)
                    //			  .columnGrid(
                    //			  	rowNumberColumn, paymentsColumn, amount)
                    .groupBy(itemGroup)
                    //			  .subtotalsAtSummary(
                    //			  	sbt.sum(amount), sbt.sum(priceColumn))
                    //			  .subtotalsAtFirstGroupFooter(
                    //			  	sbt.sum(amount), sbt.sum(priceColumn))
                    //			  .detailRowHighlighters(
                    //			  	condition1, condition2)
                    .title(//shows report title along with any image to serve as a logo.
                            cmp.horizontalList()
                                    .add(
                                            //This first line is supposed to display an image on the
                                            //title as a logo, but am still fighting with it.
                                            cmp.image(Reporter.class.getResource("hotel.png")).setFixedDimension(80, 80),
                                            cmp.text("BVS" + "\n" + "Hotels Limited").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                            cmp.text("Night Audit of" + "\n" + LocalDate.now().toString()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                                            cmp.text("All departments" + "\n" + "Reports").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
                                    .newRow()
                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    //			  .summary(
                    //			  	cmp.horizontalList(itemChart, itemChart2))
                    .setDataSource(NightDataSource(source)).toJasperPrint(); //set datasource
                    return new JasperViewer(jasperPrint);

        } catch (DRException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    private JRDataSource NightDataSource(JSONObject source) {
        DRDataSource dataSource = null;
        try {
            JSONObject j = source.getJSONObject("message");
            JSONArray ja = j.getJSONArray("depts");

            dataSource = new DRDataSource("dept", "payment", "total");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject o = ja.getJSONObject(i);
                dataSource.add(State.department(o.getInt("_id")), o.getInt("count"), new BigDecimal(o.getDouble("dept_total")));
            }
            return dataSource;
        } catch (JSONException ex) {
            Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dataSource;
    }

    
    
    //Setting Report Template Components and their LAF
    public JasperViewer folioDetail(JSONObject source) {


        //                                                           title,     field name     data type
        TextColumnBuilder<String> transIdCol = col.column("Transaction ID", "transId", type.stringType()).setStyle(boldStyle);
        TextColumnBuilder<String> descCol = col.column("Description", "desc", type.stringType());
        TextColumnBuilder<BigDecimal> amountCol = col.column("Amount", "amount", currencyType);
        TextColumnBuilder<String> channelCol = col.column("Payment Method", "channel", type.stringType());
        TextColumnBuilder<String> dateCol = col.column("Date", "date", type.stringType());
        TextColumnBuilder<String> staff = col.column("Staff", "staff", type.stringType());


        //price = unitPrice * quantity
//		TextColumnBuilder<BigDecimal> priceColumn     = amount.multiply(paymentsColumn).setTitle("Price")
//		                                                               .setDataType(currencyType);
//		PercentageColumnBuilder       pricePercColumn = col.percentageColumn("Price %", priceColumn);
        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.")
                //sets the fixed width of a column, width = 2 * character width
                .setFixedColumns(2)
                .setHorizontalTextAlignment(HorizontalTextAlignment.JUSTIFIED);
//		Bar3DChartBuilder itemChart = cht.bar3DChart()
//		                                 .setTitle("Sales by item")
//		                                 .setCategory(itemColumn)
//		                                 .addSerie(
//		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
//		Bar3DChartBuilder itemChart2 = cht.bar3DChart()
//		                                 .setTitle("Sales by item")
//		                                 .setCategory(itemColumn)
//		                                 .setUseSeriesAsCategory(true)
//		                                 .addSerie(
//		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
        ColumnGroupBuilder itemGroup = grp.group(transIdCol);
        itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));


        try {
            JasperPrint jasperPrint = report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
                    .columns(//add columns
                            rowNumberColumn,transIdCol,descCol,amountCol,channelCol,dateCol,staff)
                    //			  .columnGrid(
                    //			  	rowNumberColumn, paymentsColumn, amount)
                    .groupBy(itemGroup)
                    //			  .subtotalsAtSummary(
                    //			  	sbt.sum(amount), sbt.sum(priceColumn))
                    //			  .subtotalsAtFirstGroupFooter(
                    //			  	sbt.sum(amount), sbt.sum(priceColumn))
                    //			  .detailRowHighlighters(
                    //			  	condition1, condition2)
                    .title(//shows report title along with any image to serve as a logo.
                            cmp.horizontalList()
                                    .add(

                                            cmp.image(Reporter.class.getResource("hotel.png")).setFixedDimension(80, 80),
                                            cmp.text("BVS" + "\n" + "Hotels Limited").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                            cmp.text("Night Audit of" + "\n" + LocalDate.now().toString()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                                            cmp.text("All departments" + "\n" + "Reports").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
                                    .newRow()
                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    //			  .summary(
                    //			  	cmp.horizontalList(itemChart, itemChart2))
                    
                    .setDataSource(NightDataSource(source)).toJasperPrint(); //set datasource
//                    .show();//create and show report
                    return new JasperViewer(jasperPrint);

        } catch (DRException e) {
            e.printStackTrace();
        }
        return null;
    }



    
    
    
    
    
    //This is for setting N as a Naira Symbol and also to set Currency pattern
    private class CurrencyType extends BigDecimalType {

        private static final long serialVersionUID = 1L;

        @Override
        public String getPattern() {
            return "N #,###.00";
        }
    }
    
}
