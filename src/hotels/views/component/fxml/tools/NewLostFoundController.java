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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
    
    
    private Hotels app;

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
    
    
    
    private Navigator nav;
    private JSONObject response;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void newLostFound(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("on", lostOn.getValue().toString()));
        param.add(new BasicNameValuePair("name", itemName.getText()));
        param.add(new BasicNameValuePair("color", color.getText()));
        param.add(new BasicNameValuePair("location", location.getText()));
        param.add(new BasicNameValuePair("roomNo", room.getSelectionModel().getSelectedItem().toString()));
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
        param.add(new BasicNameValuePair("reso_returnDate", returnDate.getValue().toString()));
        param.add(new BasicNameValuePair("reso_discardDate", discardDate.getValue().toString()));
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));

        System.out.println("New Lost & Found Event Fired");
        response = nav.createLostFound(param);
        System.out.println("Creating Lost & Found : " + response);
        
        Util.notify(State.NOTIFY_SUCCESS, "A Lost Item has been Rgistered", Pos.CENTER);
    }
}
