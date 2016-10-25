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
import hotels.views.component.fxml.tools.model.Business;
import hotels.views.component.fxml.tools.model.HotelService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewBizController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private TextField person;
    @FXML
    private TextField city;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField plan;
    @FXML
    private TextField value;
    
    
    private Hotels app;
    private boolean editMode;
    private Business data;
    private Navigator nav;
    private JSONObject response;

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

    public Business getData() {
        return data;
    }

    public void setData(Business data) {
        this.data = data;
    }
    
    public NewBizController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
    }    
    
    
    
    private void onLoad(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void popEdit(){
        if(data != null){
            alias.setText(data.getAlias());
            name.setText(data.getName());
            city.setText(data.getCity());
            phone.setText(data.getPhone());
            person.setText(data.getPerson());
            email.setText(data.getEmail());
            plan.setText(data.getPlan());
            value.setText(data.getValue());
        }
        
    }  
    
    @FXML private void newBusiness(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("compName", name.getText()));
        param.add(new BasicNameValuePair("contPerson", person.getText()));
        param.add(new BasicNameValuePair("city", city.getText()));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("email", email.getText()));
        param.add(new BasicNameValuePair("plan", plan.getText()));
        param.add(new BasicNameValuePair("planValue", value.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
      
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.createBiz(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Business Source Registered", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Business Failed to Register", Pos.CENTER);
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
                    response = nav.editBiz(param);
                        if(response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Business Source Updated", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Business Source Failed to Update", Pos.CENTER);
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
