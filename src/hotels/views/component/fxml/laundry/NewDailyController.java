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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewDailyController implements Initializable {

    @FXML
    private TextField serialText;
    @FXML
    private DatePicker dateIssued;
    @FXML
    private ComboBox laundryStatus;
    @FXML
    private ComboBox returnIn;
    @FXML
    private TextArea remark;
    @FXML
    private ComboBox laundryUser;
    @FXML
    private ComboBox laundryService;
    @FXML
    private ComboBox hotelService;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ComboBox laundryItem;
    @FXML
    private TextField totalBill;
    @FXML
    private TextField amountPaid;
    @FXML
    private TextField totalBalance;
    @FXML
    private Button addBtn;

    private Navigator nav;
    private JSONObject response;
    private static ObservableList itemList, userList, serviceList, returnList, hotelServiceList;
    private static double total;
    private ObservableMap userId;
    
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewDailyController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        System.out.println("New daily Controller coming up");
        //Setting Laundry status
        ObservableList status = FXCollections.observableArrayList();
        status.add(State.STATUS_C);
        status.add(State.STATUS_P);
        status.add(State.STATUS_R);
        status.add(State.STATUS_N);
        laundryStatus.setItems(status);
        
        itemList = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        serviceList = FXCollections.observableArrayList();
        returnList = FXCollections.observableArrayList();
        hotelServiceList = FXCollections.observableArrayList();
        
        userId = FXCollections.observableHashMap();
        
        onLoad();
        
        
        laundryService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1]);
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        hotelService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     //total -= Integer.parseInt(oldValue);
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1]);
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        returnIn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1]);
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        amountPaid.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     totalBalance.setText(String.valueOf(Double.parseDouble(totalBill.getText()) - Double.parseDouble(newValue)));
                }
           });
        
    }    
    
    private void onLoad(){
        
        loadLaundryItems();
        loadLaundryService();
        loadReturnIn();
        loadHotelService();
        loadUsers();
    }
    
    @FXML
    private void newDailyLaundry(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("sn", serialText.getText()));
        param.add(new BasicNameValuePair("date", dateIssued.getValue().toString()));
        param.add(new BasicNameValuePair("item", laundryItem.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("user", laundryUser.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("status", laundryStatus.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("laundryService", laundryService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("hotelService", hotelService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("returnIn", returnIn.getSelectionModel().getSelectedItem().toString()));
        
        if(returnDate.getValue() == null){
            param.add(new BasicNameValuePair("returned", ""));
        }else{
            param.add(new BasicNameValuePair("returned", returnDate.getValue().toString()));//Storage.getId()));
        }
               
        param.add(new BasicNameValuePair("bill", totalBill.getText()));
        param.add(new BasicNameValuePair("amonunt", amountPaid.getText()));
        param.add(new BasicNameValuePair("balance", totalBalance.getText()));
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", userId.get(laundryUser.getSelectionModel().getSelectedIndex()).toString()));
           
        System.out.println("Invoking new Daily Laundry Event");
        response = nav.createDailyLaundry(param);
        System.out.println("Making Laundry Sales : " + response);
        
        if(response != null){
            Util.notify(State.NOTIFY_SUCCESS, "A New Laundry Operation Has been Registered", Pos.CENTER);
        }
    }
    
    private void loadLaundryItems(){
        try {
            JSONObject item = nav.fetchLaundryItems();
            JSONArray itemArray = item.getJSONArray("message");
            System.out.println("Printing item array : "+ itemArray);
            
            for(int i = 0; i < itemArray.length(); i++){
                JSONObject items = itemArray.getJSONObject(i);
                itemList.add(items.getString("name"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        laundryItem.setItems(itemList);
        System.out.println("Printing Laundry Items : " + itemList);
    }
    
    private void loadUsers(){
        try {
            JSONObject user = nav.fetchUsers();
            JSONArray userArray = user.getJSONArray("message");
            System.out.println("Printing User array : "+ userArray);
            for(int i = 0; i < userArray.length(); i++){
                
                JSONObject users = userArray.getJSONObject(i);
                JSONObject j = users.getJSONObject("name");
                userList.add(j.getString("firstName") + "  " + j.getString("lastName"));
                
                //JSONObject p = users.getJSONObject("user");
                userId.put(i, users.get("_id"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        laundryUser.setItems(userList);
        System.out.println("Printing Laundry Users : " + userList);
    }
    
    private void loadLaundryService(){
        try {
            
            List <NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("servive", "laundry"));
            
            JSONObject service = nav.fetchLaundryService(param);
            
            JSONArray serviceArray = service.getJSONArray("message");
            System.out.println("Printing Laundry service array : "+ serviceArray);
            
            for(int i = 0; i < serviceArray.length(); i++){
                JSONObject items = serviceArray.getJSONObject(i);
                serviceList.add(items.getString("name")  + " "+ "N"+items.getString("extraCharge"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        laundryService.setItems(serviceList);
        System.out.println("Printing Laundry Service : " + serviceList);
    }
    
    private void loadReturnIn(){
        try {
            JSONObject returnIn = nav.fetchReturnIn();
            JSONArray returnInArray = returnIn.getJSONArray("message");
            System.out.println("Printing ReturnIn array : "+ returnInArray);
            
            for(int i = 0; i < returnInArray.length(); i++){
                JSONObject items = returnInArray.getJSONObject(i);
                returnList.add(items.getString("name")  + " "+ "N"+items.getString("extraCharge"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        returnIn.setItems(returnList);
        System.out.println("Printing ReturnIn : " + returnList);
    }
    
    private void loadHotelService(){
        try {
            
            List <NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("servive", "hotel"));
            
            JSONObject service = nav.fetchHotelService(param);
            
            JSONArray serviceArray = service.getJSONArray("message");
            System.out.println("Printing Hotel Service array : "+ serviceArray);
            
            for(int i = 0; i < serviceArray.length(); i++){
                JSONObject items = serviceArray.getJSONObject(i);
                hotelServiceList.add(items.getString("name") + " "+ "N"+items.getString("extraCharge"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        hotelService.setItems(hotelServiceList);
        System.out.println("Printing Hotel Service : " + hotelServiceList);
    }
}
