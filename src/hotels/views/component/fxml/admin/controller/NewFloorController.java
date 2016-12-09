/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.admin.controller;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.admin.model.Floor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewFloorController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private TextField desc;

    private Navigator nav;
    private JSONObject response;
    
    private Hotels app;
    private boolean editMode;
    private Floor data;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewFloorController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(editMode)editDefault();
    }    

    @FXML
    private void newFloor(ActionEvent event) throws JSONException {
        if(!validat())return;
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        response = editMode? nav.editFloor(param) : nav.createFloor(param);
        
        if(response!=null){
            String t = editMode? "updated" : "created";
            if(response.getInt("status")==1){
                
                Util.notify_SUCCESS(State.NOTIFY_SUCCESS, name.getText() + " has been "+t, Pos.CENTER);
                if(!editMode)clearFieds();
            }else{
                Util.notify_ERROR(State.NOTIFY_ERROR,  "Error while creating "+name.getText() , Pos.CENTER);
            }
        }else{
            Util.notify_ERROR(State.NOTIFY_ERROR,  "Error while creating "+name.getText() , Pos.CENTER);
        }
        
    }
    
    @FXML
    private void close(ActionEvent event){
        ((Stage)this.alias.getScene().getWindow()).close();
        
    }
    
    private boolean validat(){
        return true;
    }
    
        
    
    private void clearFieds(){
        alias.clear();
        name.clear();
        desc.clear();
    }
        
        
    public void setData(Floor data) {
        this.data = data;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    
    private void editDefault(){
        alias.setText(this.data.getAlias());
        name.setText(this.data.getName());
        desc.setText(this.data.getDesc());
    }
    
}
