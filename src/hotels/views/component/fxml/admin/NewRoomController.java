/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.admin;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.HotelService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
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
public class NewRoomController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private ComboBox roomType;
    @FXML
    private ComboBox floor;
    @FXML
    private TextField desc;

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewRoomController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private boolean editMode;
    private Room data;
    private Navigator nav;
    private JSONObject response;
    private ObservableList roomTypeList = FXCollections.observableArrayList();
    private ObservableList roomTypeId = FXCollections.observableArrayList();
    private ObservableList floorList = FXCollections.observableArrayList();
    private ObservableList floorId = FXCollections.observableArrayList();

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Room getData() {
        return data;
    }

    public void setData(Room data) {
        this.data = data;
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
        onLoad();
    }    

    private void defaults(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void popEdit(){
        alias.setText(data.getAlias());
        name.setText(data.getName());
        roomType.getSelectionModel().select(data.getType());
        floor.getSelectionModel().select(data.getFloor());
        desc.setText(data.getDesc());
    }
    
    private void onLoad(){
        try {
            JSONObject roomType = nav.fetchRoomType();
            JSONObject floors = nav.fetchFloor();
        
            System.out.println(roomType);
            System.out.println(floors);
            
            JSONArray roomTypeArray = roomType.getJSONArray("message");
            System.out.println("Printing JSON Array : " +  roomTypeArray);
            
            JSONArray floorArray = floors.getJSONArray("message");
            System.out.println("Printing JSON Array : " +  floorArray);
            
            for(int i = 0; i < roomTypeArray.length(); i++){
                JSONObject oj = roomTypeArray.getJSONObject(i);
                roomTypeList.add(oj.getString("name"));
                roomTypeId.add(oj.getString("_id"));
            }
            
            for(int i = 0; i < floorArray.length(); i++){
                JSONObject oj = floorArray.getJSONObject(i);
                floorList.add(oj.getString("name"));
                floorId.add(oj.getString("_id"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        roomType.setItems(roomTypeList);
        floor.setItems(floorList);
    } 
    
    @FXML
    private void newRoom(ActionEvent event) {
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("roomType", roomTypeId.get(roomType.getSelectionModel().getSelectedIndex()).toString()));
        param.add(new BasicNameValuePair("floor", floorId.get(floor.getSelectionModel().getSelectedIndex()).toString()));
                   
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.createRoom(param);
                    System.out.println("Creating a Room : " + response);
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "New Room has been Created", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Room Failed to Create", Pos.CENTER);
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
                response = nav.editRoom(param);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, data.getName() + " has been Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, data.getName() +" Failed to Update", Pos.CENTER);
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
