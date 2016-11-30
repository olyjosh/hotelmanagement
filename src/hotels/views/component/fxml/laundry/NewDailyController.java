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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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


    private Navigator nav;
    private JSONObject response;
    private static ObservableList itemList, userList, serviceList, returnList, hotelServiceList;
    private static double total;
    private ObservableMap userId;
    
    private Hotels app;
    private boolean editMode;
    private DailyLaundry data;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public DailyLaundry getData() {
        return data;
    }

    public void setData(DailyLaundry data) {
        this.data = data;
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
      
        itemList = FXCollections.observableArrayList();
        userList = FXCollections.observableArrayList();
        serviceList = FXCollections.observableArrayList();
        returnList = FXCollections.observableArrayList();
        hotelServiceList = FXCollections.observableArrayList();
        userId = FXCollections.observableHashMap();
        serialText.setEditable(false);
        
        Util.formatDatePicker(dateIssued);
        Util.formatDatePicker(returnDate);
        
        onLoad();
        defaults();
        
        
    }    
    
    private void defaults(){
        System.out.println("New daily Controller coming up");
        //Setting Laundry status
        ObservableList status = FXCollections.observableArrayList();
        status.add(State.STATUS_C);
        status.add(State.STATUS_P);
        status.add(State.STATUS_R);
        status.add(State.STATUS_N);
        laundryStatus.setItems(status);
        
        laundryService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1].replace("N", ""));
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        hotelService.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     //total -= Integer.parseInt(oldValue);
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1].replace("N", ""));
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        returnIn.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                     String [] lserv = Util.splitter(newValue, 'N');
                     total += Integer.parseInt(lserv[1].replace("N", ""));
                     totalBill.setText(String.valueOf(total));
                }
           });
        
        amountPaid.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    if(newValue.isEmpty()){
                        totalBalance.setText(totalBill.getText());
                    }else{
                        totalBalance.setText(String.valueOf(Double.parseDouble(totalBill.getText()) - Double.parseDouble(newValue)));
                    }
                }
           });
        
    }
    
    private void onLoad(){
        
        if(isEditMode()){
            popEdit();
        }else{
            serialText.setText(Util.randomString(6));
        }
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
               
                loadLaundryItems();
                loadLaundryService();
                loadReturnIn();
                loadHotelService();
                loadUsers();
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        
    }
    
    private void popEdit(){
        serialText.setText(data.getLinen());
        dateIssued.getEditor().setText(data.getDate());
        laundryItem.getSelectionModel().select(data.getItem());
        laundryUser.getSelectionModel().select(data.getUser());
        laundryStatus.getSelectionModel().select(data.getStatus());
        laundryService.getSelectionModel().select(data.getLaundryService());
        hotelService.getSelectionModel().select(data.getHotelService());
        returnIn.getSelectionModel().select(data.getReturns());
        returnDate.getEditor().setText(data.getReturnDate());
        totalBill.setText(data.getTotalBill());
        amountPaid.setText(data.getPaid());
        totalBalance.setText(data.getBalance());
        remark.setText(data.getRemark());
    }
    
    private void loadLaundryItems(){
        try {
            JSONObject item = nav.fetchLaundryItems();
            JSONArray itemArray = item.getJSONArray("message");
            System.out.println("Printing item array : "+ itemArray);
            
            for(int i = 0; i < itemArray.length(); i++){
                JSONObject items = itemArray.getJSONObject(i);
                String name = items.getString("name");
                itemList.add(name);
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
    
    @FXML private void newDailyLaundry(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("sn", serialText.getText()));
        param.add(new BasicNameValuePair("date", dateIssued.getEditor().getText()));
        param.add(new BasicNameValuePair("item", laundryItem.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("user", laundryUser.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("status", laundryStatus.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("laundryService", laundryService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("hotelService", hotelService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("returnIn", returnIn.getSelectionModel().getSelectedItem().toString()));
        
        if(returnDate.getValue() == null){
            param.add(new BasicNameValuePair("returned", ""));
        }else{
            param.add(new BasicNameValuePair("returned", returnDate.getEditor().getText()));//Storage.getId()));
        }
               
        param.add(new BasicNameValuePair("bill", totalBill.getText()));
        param.add(new BasicNameValuePair("amonunt", amountPaid.getText()));
        param.add(new BasicNameValuePair("balance", totalBalance.getText()));
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", userId.get(laundryUser.getSelectionModel().getSelectedIndex()).toString()));
                
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.createDailyLaundry(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "A New Laundry Operation Has been Registered", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Laundry Failed to Register", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }else{

            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    param.add(new BasicNameValuePair("id", data.getId()));
                    response = nav.editDailyLaundry(param);
                        if(response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Laundry Registered has been Updated", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Laundry Register Failed to Update", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }
    }
}
