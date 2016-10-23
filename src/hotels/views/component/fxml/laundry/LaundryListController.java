/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.laundry.model.LaundryList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class LaundryListController implements Initializable {

    
    @FXML private TableView<LaundryList> table;
    @FXML private TableColumn room;
    @FXML private TableColumn guest;
    @FXML private TableColumn date;
    @FXML private TableColumn items;
    @FXML private TableColumn total;
    @FXML private TableColumn posted;
    @FXML private TableColumn balance;
    @FXML private TableColumn status;
    @FXML private TableColumn user;
    @FXML private TextField roomSearch;
    @FXML private TextField guestSearch;
    @FXML private ComboBox statusSearch;
    @FXML private ComboBox userSearch;
    @FXML private DatePicker dateSearch;
      
    private Hotels app;
    private Navigator nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LaundryListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
       
    private static JSONObject daily;
    private static JSONArray dailyArray;
    private LaundryList ls;
    private ObservableList<LaundryList> service = FXCollections.observableArrayList();
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        defaults();
        onLoad();
            
    }
    
    private void getLaundryList(){
        try {
            for(int i = 0; i < dailyArray.length(); i++){
                ls = new LaundryList();
                JSONObject oj = dailyArray.getJSONObject(i);
                System.out.println("printing daily array : " + dailyArray);
//                ls.setRoom(oj.getString("room"));//here
//                ls.setGuest(String.valueOf(oj.get("guest")));//here
                ls.setDate(Util.stripDate(oj.getString("date")));
                ls.setItems(oj.getString("item"));
                ls.setTotal(String.valueOf(oj.get("bill")));
                //ls.setPosted(oj.getString("posted"));
                ls.setBalance(String.valueOf(oj.get("balance")));
//                ls.setStatus(oj.getString("status"));//here
                ls.setUser(oj.getString("user"));
                
                System.out.println("printing Laundry List Object : " + ls);
                service.addAll(ls);
                
             
                table.setItems(service);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void defaults(){
        
        Util.formatDatePicker(dateSearch);
        
        statusSearch.getItems().addAll("Show All", State.STATUS_C, State.STATUS_N, State.STATUS_P, State.STATUS_R);
        
        roomSearch.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(roomSearch.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryList> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryList, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(roomSearch.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        guestSearch.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(guestSearch.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryList> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryList, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(guestSearch.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        statusSearch.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(statusSearch.getSelectionModel().selectedItemProperty().get().toString().isEmpty() || statusSearch.getSelectionModel().selectedItemProperty().get().toString().equals("Show All")) {
                table.setItems(service);
                return;
            }
            if(statusSearch.getSelectionModel().getSelectedItem().toString().equals("Show All")){
                table.setItems(service);
                return;
            }
            ObservableList<LaundryList> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryList, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(statusSearch.getSelectionModel().selectedItemProperty().get().toString().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        userSearch.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(userSearch.getSelectionModel().selectedItemProperty().get().toString().isEmpty()) {
                table.setItems(service);
                return;
            }
            if(userSearch.getSelectionModel().getSelectedItem().toString().equals("Show All")){
                table.setItems(service);
                return;
            }
            ObservableList<LaundryList> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryList, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(userSearch.getSelectionModel().selectedItemProperty().get().toString().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        dateSearch.getEditor().textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(dateSearch.getEditor().textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryList> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryList, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(dateSearch.getEditor().textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        guest.setCellValueFactory(new PropertyValueFactory<>("guest"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        items.setCellValueFactory(new PropertyValueFactory<>("items"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        posted.setCellValueFactory(new PropertyValueFactory<>("posted"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
            
        table.getColumns().setAll(room, guest, date, items, total, posted, balance, status, user);
    }

    private void onLoad(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    daily = nav.fetchDailyLaundry();
                    System.out.println("printing daily : " + daily);
                    dailyArray = daily.getJSONArray("message");
                    
                    getLaundryList();
                    
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };
        // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
    }
}
