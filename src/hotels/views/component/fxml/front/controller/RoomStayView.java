
package hotels.views.component.fxml.front.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;
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
    private SpreadsheetView spreadsheetView;
    
    
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
        if (spreadsheetView == null) {
            spreadsheetView = new SpreadsheetView();
        }

        roomStayContainer.setContent(spreadsheetView);
        roomStayContainer.setFitToHeight(true);
        roomStayContainer.setFitToWidth(true);
        doTaskIt();

    }   
    
    private void doTaskIt(){
        Runnable task = new Runnable() {
            public void run() {
                dooData();
            }
        };

        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setDaemon(true);
        back.start();
    }
    
    private void dooData(){
        Hashtable rowHeaders = rowHeaders();
        
        
        if(rowHeaders!=null){
            
            JSONObject fetchRoomStay = nav.fetchRoomStay("2016-09-22","2016-09-30");
            if(fetchRoomStay!=null){
                try {
                    System.out.println(fetchRoomStay);
                    JSONArray jsonArray = fetchRoomStay.getJSONArray("message");
                    GridBase grid = buildGrid(rowHeaders,jsonArray);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            roomStayContainer.setContent(new SpreadsheetView(grid));
        roomStayContainer.setFitToHeight(true);
        roomStayContainer.setFitToWidth(true);
//                            spreadsheetView.setGrid(grid);
                        }
                    });
                } catch (JSONException ex) {
                    Logger.getLogger(RoomStayView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private void getDateFrom(){}
    
    private Hashtable rowHeaders(){
        ArrayList<String> l = new ArrayList();
        Hashtable h = null;
        
        JSONObject fetchRoom = nav.fetchRoom();
        if(fetchRoom!=null){
            h=new Hashtable();
            try {
                JSONArray jsonArray = fetchRoom.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
//                    headers[i] = jsonArray.getJSONObject(i).getString("alias");
                    h.put(jsonArray.getJSONObject(i).getString("alias"), i);
                }
            } catch (JSONException ex) {
                Logger.getLogger(RoomStayView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return  h;
    }
    
    
    
        
    private GridBase buildGrid(Hashtable rowHeaders , JSONArray a) {
        String[] rowH = new String[rowHeaders.size()];
        rowHeaders.keySet().toArray(rowH);
        int rowCount =rowH.length;
        int columnCount = 30;
        GridBase grid = new GridBase(rowCount, columnCount);
        grid.getColumnHeaders().addAll(columnHeaders(9,2016));
        grid.getRowHeaders().addAll(rowH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
//        final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
        ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
        for (int i = 0; i < a.length(); i++) {
            
            try {
                JSONObject get = a.getJSONObject(i);
//                System.out.println(get);
                String string = get.getString("checkIn");
                String string1 = get.getString("checkOut");
//                LocalDateStringConverter con = new LocalDateStringConverter();
                LocalDate d1 = LocalDate.parse(string.split("T")[0], formatter);//con.fromString(string);
                LocalDate d2 = LocalDate.parse(string1.split("T")[0], formatter);//con.fromString(string1);
                long diff=ChronoUnit.DAYS.between(d1, d2);
//                retrieving the rows the room falls
                String room = get.getJSONObject("room").getString("alias");
                int row = (int) rowHeaders.get(room);
                if(rows.size()-1<row){
                    list = FXCollections.observableArrayList();
                    rows.add(list);
                }else{
                    list = rows.get(row);
                }
                
//                SpreadsheetCellBase cell = new SpreadsheetCellBase(row, d1.getDayOfMonth(), 1, (int) diff);
                                
                SpreadsheetCellBase cell = new SpreadsheetCellBase(d1.getDayOfMonth(), row, 1, 3);
                ViewItem t = new ViewItem("John Doe Dosa ");
                //t.setStyle("-fx-background-color : "+Codes.COLOR_RESERVED);
                cell.setGraphic(t);
                list.add(cell);
//                rows.add(list);
            } catch (JSONException ex) {
                Logger.getLogger(RoomStayView.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
//        for (int row = 0; row < grid.getRowCount(); ++row) {
//            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
//            for (int column = 0; column < grid.getColumnCount(); ++column) {
//                
//                SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, 1, 1);
//                ViewItem t = new ViewItem("John Doe Dosa ");
//                //t.setStyle("-fx-background-color : "+Codes.COLOR_RESERVED);
//                cell.setGraphic(t);
//                list.add(cell);
//            }
//            rows.add(list);
//        }

        grid.setRows(rows);
        
        return grid;
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
