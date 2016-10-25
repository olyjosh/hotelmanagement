/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.reports;

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
import net.sf.jasperreports.view.JasperViewer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Reporter{


        //Setting Report Template Components and their LAF
	public void buildNight(JSONObject source) {
		CurrencyType currencyType = new CurrencyType();

		StyleBuilder boldStyle         = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
		                                    .setBorder(stl.pen1Point())
		                                    .setBackgroundColor(Color.GREEN);
		StyleBuilder titleStyle        = stl.style(boldCenteredStyle)
		                                    .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
		                                    .setFontSize(15);

		//                                                           title,     field name     data type
		TextColumnBuilder<String>     deptColumn      = col.column("Departments",       "dept",      type.stringType()).setStyle(boldStyle);
		TextColumnBuilder<Integer>    paymentsColumn  = col.column("No of Payments",   "payment",  type.integerType());
		TextColumnBuilder<BigDecimal> amount = col.column("Total Sales Amount", "total", currencyType);
		//price = unitPrice * quantity
//		TextColumnBuilder<BigDecimal> priceColumn     = amount.multiply(paymentsColumn).setTitle("Price")
//		                                                               .setDataType(currencyType);
//		PercentageColumnBuilder       pricePercColumn = col.percentageColumn("Price %", priceColumn);
		TextColumnBuilder<Integer>    rowNumberColumn = col.reportRowNumberColumn("No.")
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

//		ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(priceColumn, 150))
//		                                        .setBackgroundColor(new Color(210, 255, 210));
//		ConditionalStyleBuilder condition2 = stl.conditionalStyle(cnd.smaller(priceColumn, 30))
//		                                        .setBackgroundColor(new Color(255, 210, 210));
//		ConditionalStyleBuilder condition3 = stl.conditionalStyle(cnd.greater(priceColumn, 200))
//		                                        .setBackgroundColor(new Color(0, 190, 0))
//		                                        .bold();
//		ConditionalStyleBuilder condition4 = stl.conditionalStyle(cnd.smaller(priceColumn, 20))
//		                                        .setBackgroundColor(new Color(190, 0, 0))
//		                                        .bold();
//		StyleBuilder priceStyle = stl.style()
//		                             .conditionalStyles(
//		                              	condition3, condition4);
//		priceColumn.setStyle(priceStyle);
		
                //Lets start building the report template
                try {
			report()//create new report design
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
			  			cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(80, 80),
			  			cmp.text("BVS"+"\n"+"Hotels Limited").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                                cmp.text("Night Audit of"+"\n"+LocalDate.now().toString()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
			  			cmp.text("All departments"+"\n"+  "Reports").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
			  		.newRow()
			  		.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
			  .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
//			  .summary(
//			  	cmp.horizontalList(itemChart, itemChart2))
                                
			  .setDataSource(createDataSource(source))//set datasource
			  .show();//create and show report
                            
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

        //This is for setting N as a Naira Symbol and also to set Currency pattern
	private class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "N #,###.00";
		}
	}


	private JRDataSource createDataSource(JSONObject source) {
            DRDataSource dataSource =null;
            try {
                JSONObject j = source.getJSONObject("message");
                JSONArray ja = j.getJSONArray("depts");
                
                dataSource = new DRDataSource("dept", "payment","total");
                
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject o = ja.getJSONObject(i);
                    dataSource.add(State.department(o.getInt("_id")), o.getInt("count"),new BigDecimal(o.getDouble("dept_total")));
                    
                }                
                return dataSource;
            } catch (JSONException ex) {
                Logger.getLogger(Reporter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dataSource;
	}

        
	public static void main(String[] args) {
            
		new Reporter();
                
	}
        
        
}