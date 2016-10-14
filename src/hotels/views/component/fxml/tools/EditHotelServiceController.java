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
import hotels.views.component.fxml.tools.model.HotelService;
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
import javafx.scene.control.Button;
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
public class EditHotelServiceController implements Initializable {

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public EditHotelServiceController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private HotelService data;

    public HotelService getData() {
        return data;
    }

    public void setData(HotelService data) {
        this.data = data;
    }
    
    
    private Navigator nav;
    private JSONObject response;
    
    
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
    @FXML private Button button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        System.out.println("New Hotel Service Controller Invoked");
        popEdit();
        
        button.setOnAction((e) ->{
            editHotelService();
        });
    }    
    
    private void popEdit(){
        alias.setText(data.getAlias());
        name.setText(data.getName());
        desc.setText(data.getDesc());
        charge.setText(data.getCharge());
        //image.set
    }
    
    private void editHotelService(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("_id", data.getId()));
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("extraCharge", charge.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("image", "image"));
        param.add(new BasicNameValuePair("servive", "hotel"));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
              
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.editHotelService(param);
                    if(response != null && response.getInt("status") == 200){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Hotel Service has been Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Hotel Service Failed to Update", Pos.CENTER);
                            }
                        });
                    }   
                } catch (JSONException ex) {
                    Logger.getLogger(EditHotelServiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
}
