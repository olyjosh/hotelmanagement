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
import hotels.views.component.fxml.laundry.model.LaundryService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
public class NewLaundryServiceController implements Initializable {

    private Navigator nav;
    private JSONObject response;
    
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewLaundryServiceController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private TextArea desc;
    @FXML
    private ImageView image;
    @FXML
    private TextField charge;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
    }    
    
    private boolean editMode;
    private LaundryService data;

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public LaundryService getData() {
        return data;
    }

    public void setData(LaundryService data) {
        this.data = data;
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
            charge.setText(data.getCharge());
            desc.setText(data.getDesc());
        }
        
    }
    
    @FXML
    private void newLaundryService(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("extraCharge", charge.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("image", "image"));
        param.add(new BasicNameValuePair("servive", "laundry"));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
        
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.createLaundryService(param);
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Laundry Service "+name.getText()+" Created and Saved", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Laundry Service Failed to Create", Pos.CENTER);
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
                response = nav.editLaundryService(param);
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Laundry Service "+name.getText()+" Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Lost Item Failed to Update", Pos.CENTER);
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
