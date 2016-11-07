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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
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
public class NewRoomTypeController implements Initializable {

    @FXML
    private TextField alias;
    @FXML
    private TextField name;
    @FXML
    private TextArea desc;
    @FXML
    private TextField rate;
    @FXML
    private TextField adultRate;
    @FXML
    private TextField childRate;
    @FXML
    private TextField obp;
    @FXML
    private Spinner<Integer> bapax;
    @FXML
    private Spinner<Integer> mapax;
    @FXML
    private Spinner<Integer> bcpax;
    @FXML
    private Spinner<Integer> mcpax;

    private Hotels app;
    private boolean editMode;
    private RoomType data;

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

    public RoomType getData() {
        return data;
    }

    public void setData(RoomType data) {
        this.data = data;
    }
    

    public NewRoomTypeController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    
    
    private Navigator nav;
    private JSONObject response;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bapax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        mapax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        bcpax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
        mcpax.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
    }   
    
    @FXML
    private void newRoomType(ActionEvent event) {
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("rate_rate", rate.getText()));
        param.add(new BasicNameValuePair("rate_adult", adultRate.getText()));
        param.add(new BasicNameValuePair("rate_child", childRate.getText()));
        param.add(new BasicNameValuePair("rate_overBookingPercentage", obp.getText()));
        param.add(new BasicNameValuePair("pax_baseAdult", bapax.getEditor().getText()));
        param.add(new BasicNameValuePair("pax_baseChild", bcpax.getEditor().getText()));//Storage.getId()));"57deca5d35fb9a487bdeb70f"
        param.add(new BasicNameValuePair("pax_maxAdult", mapax.getEditor().getText()));
        param.add(new BasicNameValuePair("pax_maxChild", mcpax.getEditor().getText()));
        param.add(new BasicNameValuePair("color", "0000FF"));
        
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.createRoomType(param);
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, name.getText() +" Room Type Created", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Room Type Failed to Create", Pos.CENTER);
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
                response = nav.editRoomType(param);
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
