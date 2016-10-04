
package hotels.views.component.fxml.front.controller;

import hotels.Hotels;
import hotels.util.Codes;
import hotels.util.Navigator;
import hotels.util.Navigator2;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class RoomStayView implements Initializable {

    @FXML private Label all,vacant,occupied,dirty,outOfOrder,reserved,dueOut;
    @FXML
    private ScrollPane roomStayContainer;
    
    
//    private String headers[];
    
     Navigator2 nav;
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public RoomStayView(Hotels app) {
        this.app=app;
        nav = new Navigator2(app.getMain());
        
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dooData();
        
        
    }    
    
    private void dooData(){
        String[] rowHeaders = rowHeaders();
        if(rowHeaders!=null){
            JSONObject fetchRoomStay = nav.fetchRoomStay();
            if(fetchRoomStay!=null){
                try {
                    JSONArray jsonArray = fetchRoomStay.getJSONArray("message");
                    SpreadsheetView spreadsheetView = spreadsheetView(rowHeaders, jsonArray);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            roomStayContainer.setContent(spreadsheetView);
                            roomStayContainer.setFitToHeight(true);
                            roomStayContainer.setFitToWidth(true);
                        }
                    });
                } catch (JSONException ex) {
                    Logger.getLogger(RoomStayView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private String[] rowHeaders(){
        String headers[] = null;
        JSONObject fetchRoom = nav.fetchRoom();
        if(fetchRoom!=null){
            try {
                JSONArray jsonArray = fetchRoom.getJSONArray("message");
                headers = new String[jsonArray.length()];
                for (int i = 0; i < headers.length; i++) {
                    headers[i] = jsonArray.getJSONObject(i).getString("alias");
                }
            } catch (JSONException ex) {
                Logger.getLogger(RoomStayView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return  headers;
    }
    
    
    
        
    private SpreadsheetView spreadsheetView(String[] headers , JSONArray a) {
        rowHeaders();
        int rowCount =headers.length;
        int columnCount = 30;
        GridBase grid = new GridBase(rowCount, columnCount);
        grid.getColumnHeaders().addAll(columnHeaders(9,2016));
        
        grid.getRowHeaders().addAll(headers);
        
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, 1, 1);
                        ViewItem t = new ViewItem("John Doe Dosa ");
                        //t.setStyle("-fx-background-color : "+Codes.COLOR_RESERVED);
                        cell.setGraphic(t);
                        
                        list.add(cell);
            }
            rows.add(list);
        }

        grid.setRows(rows);
        
        return new SpreadsheetView(grid);
    }
    
    private String[] columnHeaders(int month,int year){
        return  daysOfMonth(month, year);
    }
//    
//    private String[] rowHeaders(String json){
//        
//        // you can retrive a url to list this
//        String a[] = {"101","102","101","102","101","102","101","102","101","102","101","102"};
//        return a;
//    }
         
    private static String[] daysOfMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        
        cal.set(year, month-1, 0);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String [] datesss = new String[maxDay];
        SimpleDateFormat df = new SimpleDateFormat("EE, dd");
        for (int i = 1; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            datesss[i]=df.format(cal.getTime());
        }
        return datesss;
    }

}


class ViewItem extends VBox{

    public ViewItem(String name) {
        //getStyleClass().add("arrow_box");
        //setId("arrow_box");
//                Button closeBtn = new Button();
//        closeBtn.setId("window-close");
        getChildren().addAll(new Label(name));
    }
    
}
