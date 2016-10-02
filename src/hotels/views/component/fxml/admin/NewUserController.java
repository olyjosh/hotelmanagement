/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.admin;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
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
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewUserController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirm;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<?> role;

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewUserController(Hotels app) {
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
        ObservableList roles = FXCollections.observableArrayList();
        roles.add("Admin");
        roles.add("Frontdesk");
        roles.add("Laundry");
        roles.add("Mini-Bar");
        
        role.setItems(roles);
    }    

    @FXML
    private void newUser(ActionEvent event) {
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("username", username.getText()));
        param.add(new BasicNameValuePair("password", password.getText()));
        param.add(new BasicNameValuePair("role", role.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("email", email.getText()));
                             
        response = nav.registerUser(param);
        System.out.println("Registering User : " + response);
        
        nav.notify((Stage) role.getScene().getWindow(), Pos.CENTER, State.NOTIFY_BOOKING, "New User Created", 100,500);
    }
    
}
