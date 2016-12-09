/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.admin.controller;

import hotels.Hotels;
import hotels.util.CountryModel;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class NewUserController implements Initializable {

    @FXML
    private TextField username;
//    @FXML
//    private TextField password;
//    @FXML
//    private TextField confirm;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<?> role;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ChoiceBox<CountryModel> country;
    @FXML
    private ComboBox<?> sex;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton staff;
    @FXML
    private RadioButton nonStaff;
    

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
    private static String [] userRole = {"SUPERADMIN","ADMIN","ADMIN2","FRONT","HOUSEKEEP",
            "LAUNDRY","KITCHEN","MINIBAR","MAINTENANCE"};
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("New User Controller Loaded");
        ObservableList roles = FXCollections.observableArrayList();
        for(int i = 0; i<userRole.length; i++){
            roles.add(userRole[i]);
        }
        role.setItems(roles);
        
        ObservableList gender = FXCollections.observableArrayList();
        gender.add("Male");
        gender.add("Female");
        sex.setItems(gender);
        
        ToggleGroup tg = new ToggleGroup();
        staff.setToggleGroup(tg);
        nonStaff.setToggleGroup(tg);
        country.setItems(Util.getCountries());
        country.getSelectionModel().select(Util.getCountry("NG"));
    }    

    @FXML
    private void newUser(ActionEvent event) {
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("username", username.getText()));
//        param.add(new BasicNameValuePair("password", password.getText()));
        param.add(new BasicNameValuePair("privilege", String.valueOf(role.getSelectionModel().getSelectedIndex())));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("email", email.getText()));
        
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("sex", sex.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("dob", dob.getValue().toString()));
//        param.add(new BasicNameValuePair("country", country.getText()));
        param.add(new BasicNameValuePair("country", country.getSelectionModel().getSelectedItem().getCode()));
        
        boolean status;
        if(staff.isSelected()){
            status = true;
        }else{
            status = false;
        }
        
//        param.add(new BasicNameValuePair("country", String.valueOf(status)));
                            
        response = nav.registerUser(param);       
        try {
            if (response != null) {
                if (response.getInt("status") == 1) {
                    Util.notify_SUCCESS(State.NOTIFY_SUCCESS, "New User " + firstName.getText() + " " + lastName.getText() + " Created", Pos.CENTER);
                } else if (response.getInt("status") == 0) {
                    Util.notify_ERROR(State.NOTIFY_ERROR, response.getString("message"), Pos.CENTER);
                } else {
                    Util.notify_ERROR(State.NOTIFY_ERROR, "Unexpected things do happen!!\n Please try again", Pos.CENTER);
                }
            }else{
                Util.notify_ERROR(State.NOTIFY_ERROR, "Unexpected things do happen!!\n Please try again", Pos.CENTER);
            }
        } catch (JSONException ex) {
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
