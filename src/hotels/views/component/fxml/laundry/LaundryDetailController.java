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
import hotels.views.component.fxml.laundry.model.DailyLaundry;
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
import javafx.scene.control.TextArea;
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
public class LaundryDetailController implements Initializable {

    
         
    @FXML
    private TextField linenNo;
    @FXML
    private TextField guestName;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox room;
    @FXML
    private TableView<DailyLaundry> serviceTable;
    @FXML
    private TableColumn service;
    @FXML
    private TableColumn desc;
    @FXML
    private TableColumn qty;
    @FXML
    private TableColumn servicePrice;
    @FXML
    private TableView<DailyLaundry> detailTable;
    @FXML
    private TableColumn detailName;
    @FXML
    private TableColumn detailPrice;
    @FXML
    private TextField totalItems;
    @FXML
    private TextField totalBill;
    @FXML
    private TextField amountPaid;
    @FXML
    private TextField amountPosted;
    @FXML
    private TextField balance;
    @FXML
    private TextArea remark;
    
    
    
    private Hotels app;
    private Navigator nav;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LaundryDetailController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private static ObservableList<DailyLaundry> serviceList;
    private static ObservableList<DailyLaundry> detailList;
    private static JSONObject daily;
    private static JSONArray dailyArray;
    private DailyLaundry dl;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        serviceList = FXCollections.observableArrayList();
        detailList = FXCollections.observableArrayList();
        
        defaults();
        onLoad();
    }    
    
    private void defaults(){
        
        Util.formatDatePicker(date);

        service.setCellValueFactory(new PropertyValueFactory<>(""));
        desc.setCellValueFactory(new PropertyValueFactory<>(""));
        qty.setCellValueFactory(new PropertyValueFactory<>(""));
        servicePrice.setCellValueFactory(new PropertyValueFactory<>(""));
        detailName.setCellValueFactory(new PropertyValueFactory<>(""));
        detailPrice.setCellValueFactory(new PropertyValueFactory<>(""));
            
        serviceTable.getColumns().setAll(service, desc, qty, servicePrice);
        detailTable.getColumns().setAll(detailName, detailPrice);
    }

    private void onLoad(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    daily = nav.fetchDailyLaundry();
                    System.out.println("printing daily : " + daily);
                    dailyArray = daily.getJSONArray("message");
                    
                    getLaundryDetail();
                    
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
    
    private void getLaundryDetail(){
        try {
            for(int i = 0; i < dailyArray.length(); i++){
                dl = new DailyLaundry();
                JSONObject oj = dailyArray.getJSONObject(i);
                System.out.println("printing daily array : " + dailyArray);
//                dl.setRoom(oj.getString("room"));//here
//                dl.setGuest(String.valueOf(oj.get("guest")));//here
                dl.setDate(Util.stripDate(oj.getString("date")));
                dl.setItem(oj.getString("item"));
                dl.setTotalBill(String.valueOf(oj.get("bill")));
                //ls.setPosted(oj.getString("posted"));
                dl.setBalance(String.valueOf(oj.get("balance")));
//                dl.setStatus(oj.getString("status"));//here
                dl.setUser(oj.getString("user"));
                
                System.out.println("printing Daily Laundry Object : " + dl);
                serviceList.addAll(dl);
                
             
                serviceTable.setItems(serviceList);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
