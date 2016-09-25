
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import hotels.util.Navigator;
import hotels.util.State;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
public class NewBookingController implements Initializable {

    private Navigator nav = new Navigator();
    private JSONObject response;
    private ObservableList suiteList = FXCollections.observableArrayList();
    private ObservableList roomList = FXCollections.observableArrayList();
    private List rateList = new ArrayList();
    private List roomID = new ArrayList();
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
    private TextField amount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Load default room settings
        this.onLoad();
        
        suite.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    int index = suite.getSelectionModel().getSelectedIndex();
                    System.out.println("Selected Index : " + index);
                    amount.setText(rateList.get(index).toString());
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
    }
    
    private void onLoad(){
        try {
            JSONObject roomType = nav.fetchRoomType();
            JSONObject rooms = nav.fetchRoom();
        
            System.out.println(roomType);
            System.out.println(rooms);
            
            JSONArray roomTypeArray = roomType.getJSONArray("message");
            System.out.println("Printing JSON Array : " +  roomTypeArray);
            
            JSONArray roomArray = rooms.getJSONArray("message");
            System.out.println("Printing JSON Array : " +  roomArray);
            
            for(int i = 0; i < roomTypeArray.length(); i++){
                JSONObject oj = roomTypeArray.getJSONObject(i);
                suiteList.add(oj.getString("name"));
                JSONObject oj2 = oj.getJSONObject("rate");
                rateList.add(oj2.get("rate"));
                
            }
            
            for(int i = 0; i < roomArray.length(); i++){
                JSONObject oj = roomArray.getJSONObject(i);
                roomList.add(oj.getString("name"));
                roomID.add(oj.get("_id"));
                System.out.println("Printing room ID : " + roomID);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        suite.setItems(suiteList);
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
        param.add(new BasicNameValuePair("status", State.RM_BOOKED));
        param.add(new BasicNameValuePair("channel", State.channel_FRONT));
        
        response = nav.booking(param);
        System.out.println("Booking a Room : " + response);
        
        nav.notify((Stage) room.getScene().getWindow(), Pos.CENTER, State.NOTIFY_BOOKING, State.NOTIFY_SUCCESS + firstName.getText() + " " +
                lastName.getText() + "is Successfully Booked on Room " + 
                room.getSelectionModel().getSelectedItem(), 100,450);
    }
    
    
}
