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
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDate;

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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class Report{

	public Report() {
		build();
	}

        //Setting Report Template Components and their LAF
	private void build() {
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
		TextColumnBuilder<String>     itemColumn      = col.column("Item",       "item",      type.stringType()).setStyle(boldStyle);
		TextColumnBuilder<Integer>    quantityColumn  = col.column("Quantity",   "quantity",  type.integerType());
		TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("Unit price", "unitprice", currencyType);
		//price = unitPrice * quantity
		TextColumnBuilder<BigDecimal> priceColumn     = unitPriceColumn.multiply(quantityColumn).setTitle("Price")
		                                                               .setDataType(currencyType);
		PercentageColumnBuilder       pricePercColumn = col.percentageColumn("Price %", priceColumn);
		TextColumnBuilder<Integer>    rowNumberColumn = col.reportRowNumberColumn("No.")
		                                                    //sets the fixed width of a column, width = 2 * character width
		                                                   .setFixedColumns(2)
		                                                   .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		Bar3DChartBuilder itemChart = cht.bar3DChart()
		                                 .setTitle("Sales by item")
		                                 .setCategory(itemColumn)
		                                 .addSerie(
		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
		Bar3DChartBuilder itemChart2 = cht.bar3DChart()
		                                 .setTitle("Sales by item")
		                                 .setCategory(itemColumn)
		                                 .setUseSeriesAsCategory(true)
		                                 .addSerie(
		                                	 cht.serie(unitPriceColumn), cht.serie(priceColumn));
		ColumnGroupBuilder itemGroup = grp.group(itemColumn);
		itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));

		ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(priceColumn, 150))
		                                        .setBackgroundColor(new Color(210, 255, 210));
		ConditionalStyleBuilder condition2 = stl.conditionalStyle(cnd.smaller(priceColumn, 30))
		                                        .setBackgroundColor(new Color(255, 210, 210));
		ConditionalStyleBuilder condition3 = stl.conditionalStyle(cnd.greater(priceColumn, 200))
		                                        .setBackgroundColor(new Color(0, 190, 0))
		                                        .bold();
		ConditionalStyleBuilder condition4 = stl.conditionalStyle(cnd.smaller(priceColumn, 20))
		                                        .setBackgroundColor(new Color(190, 0, 0))
		                                        .bold();
		StyleBuilder priceStyle = stl.style()
		                             .conditionalStyles(
		                              	condition3, condition4);
		priceColumn.setStyle(priceStyle);
		
                //Lets start building the report template
                try {
                    JasperPrint toJasperPrint = report()//create new report design
                            .setColumnTitleStyle(columnTitleStyle)
                            .setSubtotalStyle(boldStyle)
                            .highlightDetailEvenRows()
                            .columns(//add columns
                                    rowNumberColumn, itemColumn, quantityColumn, unitPriceColumn, priceColumn, pricePercColumn)
                            .columnGrid(
                                    rowNumberColumn, quantityColumn, unitPriceColumn, grid.verticalColumnGridList(priceColumn, pricePercColumn))
                            .groupBy(itemGroup)
                            .subtotalsAtSummary(
                                    sbt.sum(unitPriceColumn), sbt.sum(priceColumn))
                            .subtotalsAtFirstGroupFooter(
                                    sbt.sum(unitPriceColumn), sbt.sum(priceColumn))
                            .detailRowHighlighters(
                                    condition1, condition2)
                            .title(//shows report title along with any image to serve as a logo.
                                    cmp.horizontalList()
                                            .add(
                                                    //This first line is supposed to display an image on the
                                                    //title as a logo, but am still fighting with it.
                                                    cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(80, 80),
                                                    cmp.text("Adavuruku"+"\n"+"Hotels Limited").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
                                                    cmp.text("Nightly Audit of"+"\n"+LocalDate.now().toString()).setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                                                    cmp.text("Miscellaneous Sales"+"\n"+  "Reports").setStyle(titleStyle).setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
                                            .newRow()
                                            .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                            .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                            .summary(
                                    cmp.horizontalList(itemChart, itemChart2))
                            .setDataSource(createDataSource())//set datasource
                            .toJasperPrint();
                                            JasperViewer jv = new JasperViewer(toJasperPrint);
                                            jv.setTitle("akldbkvgasvbudbyuvtdwhbceuih");
                                            jv.setVisible(true);

//			  .show();//create and show report
                            
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

        //This is where the data is populated or generated
        //Though they have a container component to connect to database as
        //as a datasource but i still never grab that one yet.
	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
		dataSource.add("Raji Zakariyya", 1, new BigDecimal(500));
		dataSource.add("Joshua Aroke", 5, new BigDecimal(30));
		dataSource.add("Sanni Ometere", 1, new BigDecimal(28));
		dataSource.add("Ojatuhwo Itopa", 5, new BigDecimal(32));
		dataSource.add("Demide Halimat", 3, new BigDecimal(11));
		dataSource.add("Adekola Elizabeth ", 1, new BigDecimal(15));
		dataSource.add("Jato Onubedo", 5, new BigDecimal(10));
		dataSource.add("Ogavuda Omeiza", 8, new BigDecimal(9));
		return dataSource;
	}

        
	public static void main(String[] args) {
            
		new Report();
                
	}
        
        
}