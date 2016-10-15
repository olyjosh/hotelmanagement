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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    
    private Navigator nav;
    private JSONObject response;
    private ObservableList roomTypeList = FXCollections.observableArrayList();
    private ObservableList roomTypeId = FXCollections.observableArrayList();
    private ObservableList floorList = FXCollections.observableArrayList();
    private ObservableList floorId = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        onLoad();
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
                             
        response = nav.createRoom(param);
        System.out.println("Creating a Room : " + response);
        
        Util.notify(State.NOTIFY_SUCCESS, "New Room has been Created", Pos.CENTER);
    }
    
}
