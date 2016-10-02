/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewHotelServiceController implements Initializable {

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewHotelServiceController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        System.out.println("New Laundry Service Controller Invoked");
    }    
    
    @FXML
    private void newHotelService(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alias", alias.getText()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("extraCharge", charge.getText()));
        param.add(new BasicNameValuePair("desc", desc.getText()));
        param.add(new BasicNameValuePair("image", "image"));
        param.add(new BasicNameValuePair("service", "hotel"));
        param.add(new BasicNameValuePair("performedBy", "id"));
      
        System.out.println("New Hotel Service Event Fired");
        response = nav.createHotelService(param);
        System.out.println("Creating Hotel Service : " + response);
        
        nav.notify((Stage) alias.getScene().getWindow(), Pos.CENTER, State.NOTIFY_SUCCESS, "Hotel Service Created and Saved", 100,300);
    }
    
}
