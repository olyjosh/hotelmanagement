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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class NewLostFoundController implements Initializable {

    @FXML
    private DatePicker lostOn;
    @FXML
    private TextField color;
    @FXML
    private TextField location;
    @FXML
    private ComboBox<?> room;
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
    
    private Navigator nav;
    private JSONObject response;
    private static JSONObject rooms;
    private ObservableList roomList = FXCollections.observableArrayList();
    private List roomID = new ArrayList();
    private ObservableList <LostFound> service;
    private String id;
    private Hotels app;
    
    public ObservableList<LostFound> getService() {
        return service;
    }

    public void setService(ObservableList<LostFound> service) {
        this.service = service;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    
    public NewLostFoundController(Hotels app, ObservableList <LostFound> service, String id) {
        this.app = app;
        this.setService(service);
        nav  = new Navigator(getApp().getMain());
        
        System.out.println("baba : " + id);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
        
//        for(int i = 0; i < service.size(); i++){
//            if(service.get(i).getId().equals(getId())){
//                lostOn.getEditor().setText(service.get(i).getEntryDate());
//                itemName.setText(service.get(i).getItemName());
//                color.setText(service.get(i).getItemColour());
//                location.setText(service.get(i).getWhereLost());
//                //room.setSelectionModel();
//                founder.setText(service.get(i).getFounder());
//                remark.setText(service.get(i).getRemark());
//                name.setText(service.get(i).getName());
//                address.setText(service.get(i).getAddress());
//                city.setText(service.get(i).getCity());
//                state.setText(service.get(i).getState());
//                zip.setText(service.get(i).getZip());
//                country.setText(service.get(i).getCountry());
//                phone.setText(service.get(i).getPhone());
//                returnBy.setText(service.get(i).getReturnBy());
//                returnDate.getEditor().setText(service.get(i).getReturnDate());
//                discardBy.setText(service.get(i).getDiscardBy());
//                discardDate.getEditor().setText(service.get(i).getDiscardDate());
//
//            }
//        }
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
    @FXML
    private void newLostFound(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("on", lostOn.getValue().toString()));
        param.add(new BasicNameValuePair("name", itemName.getText()));
        param.add(new BasicNameValuePair("color", color.getText()));
        param.add(new BasicNameValuePair("location", location.getText()));
        
        if(room.getSelectionModel().getSelectedItem() == null){
            param.add(new BasicNameValuePair("roomNo", ""));
        }else{
            param.add(new BasicNameValuePair("roomNo", room.getSelectionModel().getSelectedItem().toString()));//Storage.getId()));
        }
        
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
            param.add(new BasicNameValuePair("reso_returnDate", returnDate.getValue().toString()));
        }
        
        if(discardDate.getValue() == null){
            param.add(new BasicNameValuePair("reso_discardDate", ""));
        }else{
            param.add(new BasicNameValuePair("reso_discardDate", discardDate.getValue().toString()));
        }
        
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));

        System.out.println("New Lost & Found Event Fired");
        response = nav.createLostFound(param);
        System.out.println("Creating Lost & Found : " + response);
        
        Util.notify(State.NOTIFY_SUCCESS, "A Lost Item has been Rgistered", Pos.CENTER);
    }
    
    @FXML
    private void editLostFound(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("on", lostOn.getValue().toString()));
        param.add(new BasicNameValuePair("name", itemName.getText()));
        param.add(new BasicNameValuePair("color", color.getText()));
        param.add(new BasicNameValuePair("location", location.getText()));
        
        if(room.getSelectionModel().getSelectedItem() == null){
            param.add(new BasicNameValuePair("roomNo", ""));
        }else{
            param.add(new BasicNameValuePair("roomNo", room.getSelectionModel().getSelectedItem().toString()));//Storage.getId()));
        }
        
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
            param.add(new BasicNameValuePair("reso_returnDate", returnDate.getValue().toString()));
        }
        
        if(discardDate.getValue() == null){
            param.add(new BasicNameValuePair("reso_discardDate", ""));
        }else{
            param.add(new BasicNameValuePair("reso_discardDate", discardDate.getValue().toString()));
        }
        
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Edit Lost & Found Event Fired");
                response = nav.editLostFound(param);
                System.out.println("Editing Lost & Found : " + response);

                Util.notify(State.NOTIFY_SUCCESS, "A Lost Item Info has been Edited", Pos.CENTER);
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
    }
    
    
}
