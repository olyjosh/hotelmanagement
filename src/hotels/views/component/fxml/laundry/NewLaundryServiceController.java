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
        
        System.out.println("New Laundry Service Controller Invoked");
        //System.out.println(nav.fetchRoomType());
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
      
        System.out.println("New Laundry Service Event Fired");
        response = nav.createLaundryService(param);
        System.out.println("Creating Laundry Service : " + response);
        
        Util.notify(State.NOTIFY_SUCCESS, "Laundry Service "+name.getText()+" Created and Saved", Pos.CENTER);
    }
}
