/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hotels.views.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
/**
 *
 * @author Olyjosh
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) {
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });

        StackPane root = new StackPane();
//        ScrollPane sc =new ScrollPane(spreadsheetView());
        
        root.getChildren().add(spreadsheetView());

        Scene scene = new Scene(root, 960, 500);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    
    private SpreadsheetView spreadsheetView() {
        int rowCount = 15;
        int columnCount = 3;
        GridBase grid = new GridBase(rowCount, columnCount);
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, 1, 1);
                        cell.setGraphic(new Button("Test Button"));
                        list.add(cell);
            }
            rows.add(list);
        }
//        ObservableList<SpreadsheetCell> observableArrayList = FXCollections.observableArrayList(
//                SpreadsheetCellType.STRING.createCell(rowCount-5, columnCount-5, 4, 3, "Alin subery"));
//        rows.add(observableArrayList);
        grid.setRows(rows);
        
        return new SpreadsheetView(grid);
    }
    
    
    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
