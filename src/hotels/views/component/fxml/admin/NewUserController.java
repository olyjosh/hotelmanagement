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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
    @FXML
    private TextField password;
    @FXML
    private TextField confirm;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private ComboBox role;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField country;
    @FXML
    private ComboBox sex;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton staff;
    @FXML
    private RadioButton nonStaff;
    

    private Hotels app;
    private boolean editMode;
    private Users data;

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

    public Users getData() {
        return data;
    }

    public void setData(Users data) {
        this.data = data;
    }
    
    public NewUserController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private Navigator nav;
    private JSONObject response;
    private static String [] userRole = {"USER_SUPER_ADMIN","USER_ADMIN","USER_ADMIN_2","USER_FRONT","USER_HOUSEKEEP",
            "USER_LAUNDRY","USER_KITCHEN","USER_MINIBAR","USER_MAINTENANCE"};
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        
         onLoad();
    }    
    
    
    
    private void onLoad(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void popEdit(){
        if(data != null){
            username.setText(data.getUsername());
            phone.setText(data.getPhone());
            email.setText(data.getEmail());
            role.getSelectionModel().select(data.getRole());
            firstName.setText(data.getFirstname());
            lastName.setText(data.getLastname());
            country.setText(data.getCountry());
            sex.getSelectionModel().select(data.getSex());
            dob.getEditor().setText(Util.stripDate(data.getDob()));
            
            if(data.getStaff().equalsIgnoreCase("true")){
                staff.setSelected(true);
            }else{
                staff.setSelected(false);
            }
        }
        
    }

    @FXML
    private void newUser(ActionEvent event) {
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("username", username.getText()));
        param.add(new BasicNameValuePair("password", password.getText()));
        param.add(new BasicNameValuePair("privilege", String.valueOf(role.getSelectionModel().getSelectedIndex())));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("email", email.getText()));
        
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("sex", sex.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("dob", dob.getEditor().getText()));
        param.add(new BasicNameValuePair("country", country.getText()));
        
        boolean status;
        if(staff.isSelected()){
            status = true;
        }else{
            status = false;
        }
        
        param.add(new BasicNameValuePair("isStaff", String.valueOf(status)));
                          
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.registerUser(param);
                        System.out.println("Registering User : " + response);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "New User Account Created", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "User Account Failed to Create", Pos.CENTER);
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
                    response = nav.editUser(param);
                        if(response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "User Account Updated", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "User Account Failed to Update", Pos.CENTER);
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
