

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.Account;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
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
public class NewBookingController implements Initializable {

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewBookingController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private Navigator nav;
    private JSONObject response;
    private static JSONObject roomType;
    private static JSONObject rooms;
    private ObservableList suiteList = FXCollections.observableArrayList();
    private ObservableList roomList = FXCollections.observableArrayList();
    private List rateList = new ArrayList();
    private List roomID = new ArrayList();
    private static String roomTypeId = "";
    private static String roomid = "";
    
    @FXML
    private DatePicker checkIn;
    @FXML
    private DatePicker checkOut;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ComboBox suite;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private ComboBox room;
    @FXML
    private TextField amount, amount1;
    @FXML
    private ComboBox isBooking, bookType;
    @FXML
    private CheckBox checkinNow;
    @FXML private Label dayLabel;
    @FXML private TextField totalBill;
    @FXML private TextField amountPaid; 
    @FXML private TextField balance; 
    @FXML private TextField discount;
    
    
    @FXML private ChoiceBox copChoiceBox;
    @FXML private CheckBox copCheckBox;
    @FXML private ProgressIndicator progress;
    
    
    private static int days = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Load default room settings
        onLoad();
        checkinNow.setDisable(true);
        
        ObservableList booking = FXCollections.observableArrayList();
        booking.add(State.RM_BOOKED);
        booking.add(State.RM_RESERVED);
        isBooking.setItems(booking);
        
        Util.formatDatePicker(checkIn);
        Util.formatDatePicker(checkOut);
                
        bookType.getItems().setAll("Corporate Booking", "Individual Booking", "Family Booking", "Group Booking");
        
        isBooking.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = isBooking.getSelectionModel().getSelectedIndex();
                    System.out.println("Selected Index : " + index);
                    if(index == 0){
                        checkinNow.setDisable(false);
                    }else{
                        checkinNow.setDisable(true);
                        checkinNow.setSelected(false);
                    }
                }
           });
        
        suite.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = suite.getSelectionModel().getSelectedIndex();
                    amount.setText(rateList.get(index).toString());
                    days = (int) ChronoUnit.DAYS.between(checkIn.getValue(), checkOut.getValue());
                    System.out.println("Days : "+ days);
                    dayLabel.setText("for "+days+" days");
                    amount1.setText(String.valueOf((int)rateList.get(index) * days));
                    balance.setText(amount1.getText());
                    getRoomTypeId();
                    roomList.clear();   
                    fillRooms();
                     
                }
           });
        
        room.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = room.getSelectionModel().getSelectedIndex();
                    System.out.println("Selected Index : " + index);
                    roomid = roomID.get(index).toString();
                                        
                }
           });
        
        checkIn.getEditor().textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
                LocalDate date = checkIn.getValue();
                if(date.isEqual(LocalDate.now())){
                    isBooking.getSelectionModel().selectFirst();
                    isBooking.setDisable(true);
                    
                    checkinNow.setSelected(true);
                    checkinNow.setDisable(true);
                }else{
                    isBooking.getSelectionModel().clearSelection();
                    isBooking.setDisable(false);
                    
                    checkinNow.setSelected(false);
                }
            }
        });
        
        amountPaid.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    double val = Double.parseDouble(totalBill.getText()) - Double.parseDouble(newValue);
                    balance.setText(String.valueOf(val));
                }
           });
        
        final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(
                                    checkIn.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }
                            int p = (int) ChronoUnit.DAYS.between(checkIn.getValue(), item);
                            setTooltip(new Tooltip(
                                "You're about to stay for " + p + " days")
                            );
                            days = p;
                            dayLabel.setText("for " + days + " days");
                    }
                };
            }
        };
        checkOut.setDayCellFactory(dayCellFactory);
        defaults();
    }
    
    private void onLoad(){
        
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    roomType = nav.fetchRoomType();
                    rooms = nav.fetchRoom();
                    if(roomType != null && rooms != null ){
                        JSONArray roomTypeArray = roomType.getJSONArray("message");
                        System.out.println("Printing RoomType Array : " +  roomTypeArray);

                        for(int i = 0; i < roomTypeArray.length(); i++){
                            JSONObject oj = roomTypeArray.getJSONObject(i);
                            suiteList.add(oj.getString("name"));

                            JSONObject oj2 = oj.getJSONObject("rate");
                            rateList.add(oj2.get("rate"));

                        }
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Error Fetching Rooms and Room Type", Pos.CENTER);
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
         
        suite.setItems(suiteList);
        
    } 
    
    private void getRoomTypeId(){
        try {
            
            JSONArray roomTypeArray = roomType.getJSONArray("message");
            
            for(int i = 0; i < roomTypeArray.length(); i++){
                JSONObject oj = roomTypeArray.getJSONObject(i);
                if(suite.getSelectionModel().getSelectedItem().toString().equals(oj.getString("name"))){
                    roomTypeId = oj.getString("_id");
                    System.out.println("Printing Selected ROom TYpe ID : " + roomTypeId);break;
                }
            }
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    private void fillRooms(){
        
        JSONArray roomArray;
        try {
            roomArray = rooms.getJSONArray("message");
            System.out.println("Printing Room Array: " +  roomArray);
            
            for(int i = 0; i < roomArray.length(); i++){
                JSONObject oj = roomArray.getJSONObject(i);
                if(oj.get("roomType") != null){
                    if(oj.getJSONObject("roomType").getString("_id").equals(roomTypeId)){
                    
                        roomList.add(oj.getString("name"));
                        roomID.add(oj.get("_id"));
                    }
                }
                
            }
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        System.out.println("Printing room List : " + roomList);
        room.setItems(roomList);
    }
    
    @FXML
    private void bookMe(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("checkIn", checkIn.getValue().toString()));
        param.add(new BasicNameValuePair("checkOut", checkOut.getValue().toString()));
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("email", email.getText()));
        param.add(new BasicNameValuePair("address", address.getText()));
        param.add(new BasicNameValuePair("room", roomid));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));//Storage.getId()));
        param.add(new BasicNameValuePair("amount", amount.getText()));
        param.add(new BasicNameValuePair("status", isBooking.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("channel", State.channel_FRONT));
        
        if(checkinNow.isSelected()){
            param.add(new BasicNameValuePair("isCheckIn", "true"));
        }else{
            param.add(new BasicNameValuePair("isCheckIn", "false"));
        }
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.createBooking(param);
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify( State.NOTIFY_BOOKING, firstName.getText() + " " +
                                    lastName.getText() + "is Successfully Booked on " + 
                                    room.getSelectionModel().getSelectedItem().toString(), Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Booking Operation Failed", Pos.CENTER);
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
    
    
    private void defaults(){
        copChoiceBox.disableProperty().bind(copCheckBox.selectedProperty().not());                            
        populateCoperate();
    }
    
    
    
    private ObservableList<Account> coperates;
    private void populateCoperate() {
        if(coperates == null)coperates = FXCollections.observableArrayList();
        copChoiceBox.setItems(coperates);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject account = nav.fetchAccount();
                    if (account !=null){
                        JSONArray accountArray = account.getJSONArray("message");
                        if(accountArray!=null)getAccountList(accountArray);
                    } 


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
 
     private void getAccountList(JSONArray accountArray){
        
        try {
            Account ls; 
            for(int i = 0; i < accountArray.length(); i++){
                ls = new Account(){
                    @Override
                    public String toString() {
                        return getAccountName() + "     ("+getCompanyName()+")";
                    }
                    
                };
                JSONObject oj = accountArray.getJSONObject(i);
                System.out.println("Printing Hotel Service : " + oj);
                ls.setId(oj.getString("_id"));
                ls.setAccountName(oj.getString("accountName"));
                ls.setAccountNo(oj.getJSONObject("cred").getString("accountNo"));
                ls.setAdd1(oj.getJSONObject("address").getString("one"));
                ls.setAdd2(oj.getJSONObject("address").getString("two"));
                ls.setBalance(String.valueOf(oj.getJSONObject("cred").get("openBalance")));
                ls.setCity(oj.getString("city"));
                ls.setCompanyName(oj.getString("alis"));
                //ls.setContact(oj.getString("contact"));
                ls.setCountry(oj.getString("country"));
                ls.setCredit(String.valueOf(oj.getJSONObject("cred").get("creditLimit")));
                ls.setEmail(oj.getString("email"));
                ls.setFirstName(oj.getString("firstName"));
                ls.setLastName(oj.getString("lastName"));
                //ls.setPhone(oj.getString("phone"));
                ls.setRep(oj.getString("rep"));
                ls.setState(oj.getString("state"));
                ls.setTerm(oj.getJSONObject("cred").getString("paymentTerm"));
                ls.setWeb(oj.getString("website"));
                ls.setZip(oj.getString("zip"));
                
                coperates.add(ls);
                
//                service.addAll(ls);
//                table.setItems(service);
                
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
}

