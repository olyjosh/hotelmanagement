/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.LostFound;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
public class NewLostFoundController implements Initializable {

    @FXML
    private DatePicker lostOn;
    @FXML
    private TextField color;
    @FXML
    private TextField location;
    @FXML
    private ComboBox room;
    @FXML
    private TextField founder;
    @FXML
    private TextField itemName;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField zip;
    @FXML
    private TextField country;
    @FXML
    private TextField phone;
    @FXML
    private TextField returnBy;
    @FXML
    private TextField discardBy;
    @FXML
    private DatePicker returnDate;
    @FXML
    private DatePicker discardDate;
    @FXML
    private TextArea remark;
    @FXML
    private Button button;
    
    private Navigator nav;
    private JSONObject response;
    private static JSONObject rooms;
    private ObservableList roomList = FXCollections.observableArrayList();
    private List roomID = new ArrayList();
    private Hotels app;
    private LostFound data;
    private boolean editMode;
    

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewLostFoundController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }

    public LostFound getData() {
        return data;
    }

    public void setData(LostFound data) {
        this.data = data;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Util.formatDatePicker(returnDate);
        Util.formatDatePicker(lostOn);
        Util.formatDatePicker(discardDate);
        
        onLoad();
        if(isEditMode()){
            popEdit();
        }
        
    }    
    
    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
        
    private void popEdit(){

        if(data != null){
            lostOn.getEditor().setText(data.getEntryDate());
            itemName.setText(data.getItemName());
            color.setText(data.getItemColour());
            location.setText(data.getWhereLost());
            room.getSelectionModel().select(data.getRoomNo());
            founder.setText(data.getFounder());
            remark.setText(data.getRemark());
            name.setText(data.getName());
            address.setText(data.getAddress());
            city.setText(data.getCity());
            state.setText(data.getState());
            zip.setText(data.getZip());
            country.setText(data.getCountry());
            phone.setText(data.getPhone());
            returnBy.setText(data.getReturnBy());
            returnDate.getEditor().setText(data.getReturnDate());
            discardBy.setText(data.getDiscardBy());
            discardDate.getEditor().setText(data.getDiscardDate());
        }
    }
    
    private void onLoad(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                rooms = nav.fetchRoom();
                JSONArray roomArray;
                try {
                    roomArray = rooms.getJSONArray("message");
                    System.out.println("Printing Array of Rooms : " +  roomArray);

                    for(int i = 0; i < roomArray.length(); i++){
                        JSONObject oj = roomArray.getJSONObject(i);
                        roomList.add(oj.getString("name"));
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                room.setItems(roomList);
            }
        };
        // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
    }
    
    @FXML private void newLostFound(){
        
        List <NameValuePair> param = new ArrayList<>();
        
        param.add(new BasicNameValuePair("on", lostOn.getEditor().getText()));
        param.add(new BasicNameValuePair("name", itemName.getText()));
        param.add(new BasicNameValuePair("color", color.getText()));
        param.add(new BasicNameValuePair("location", location.getText()));
        
        if(room.getSelectionModel().getSelectedItem() == null){
            param.add(new BasicNameValuePair("roomNo", ""));
        }else{
            param.add(new BasicNameValuePair("roomNo", room.getSelectionModel().getSelectedItem().toString()));//Storage.getId()));
        }
        
        //param.add(new BasicNameValuePair("current", ""));
        param.add(new BasicNameValuePair("founder", founder.getText()));
        param.add(new BasicNameValuePair("comp_name", name.getText()));
        param.add(new BasicNameValuePair("comp_address", address.getText()));
        param.add(new BasicNameValuePair("comp_city", city.getText()));
        param.add(new BasicNameValuePair("comp_state", state.getText()));
        param.add(new BasicNameValuePair("comp_zip", zip.getText()));
        param.add(new BasicNameValuePair("comp_country", country.getText()));
        param.add(new BasicNameValuePair("comp_phone", phone.getText()));
        param.add(new BasicNameValuePair("reso_returnBy", returnBy.getText()));
        param.add(new BasicNameValuePair("reso_discardBy", discardBy.getText()));
        
        if(returnDate.getValue() == null){
            param.add(new BasicNameValuePair("reso_returnDate", ""));
        }else{
            param.add(new BasicNameValuePair("reso_returnDate", returnDate.getEditor().getText()));
        }
        
        if(discardDate.getValue() == null){
            param.add(new BasicNameValuePair("reso_discardDate", ""));
        }else{
            param.add(new BasicNameValuePair("reso_discardDate", discardDate.getEditor().getText()));
        }
        
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));

        if(!isEditMode()){
            
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.createLostFound(param);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "A Lost Item has been Registered", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Lost Item Failed to Register", Pos.CENTER);
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
                response = nav.editLostFound(param);System.out.println("response : " + response);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "A Lost Item has been Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "A Lost Item Failed to Update", Pos.CENTER);
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
