/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.CountryModel;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Storage;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.Account;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewAccountController implements Initializable {

    @FXML
    private TextField accountName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField add1;
    @FXML
    private TextField add2;
    @FXML
    private TextField city;
    @FXML
    private TextField zip;
    @FXML
    private TextField email;
    @FXML
    private TextField web;
//    @FXML
//    private TextField accountNo;
    @FXML
    private TextField credit;
    @FXML
    private TextField balance;
    @FXML
    private TextField term;
    @FXML 
    private TextField discount;
    @FXML
    private ComboBox rep;
    @FXML
    private ChoiceBox<CountryModel> country;
    @FXML
    private ComboBox state;
    @FXML
    private TextField contact;
    @FXML
    private TextField alias;
    
    
    private Hotels app;
    private Navigator nav;
    private JSONObject response;
    private boolean editMode;
    private Account data;
    private static ObservableList userList, userId;
    private static String id;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewAccountController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userList = FXCollections.observableArrayList();
        userId = FXCollections.observableArrayList();
        
        rep.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   int index = rep.getSelectionModel().getSelectedIndex();
                   id = String.valueOf(userId.get(index));
                   System.out.println("ID : " + id);
                }
           });
        country.setItems(Util.getCountries());
        country.getSelectionModel().select(164);
        onLoad();
    }    
    
    private void onLoad(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void loadUsers(){
      
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject user = nav.fetchUsers();
                    JSONArray userArray = user.getJSONArray("message");
                    for(int i = 0; i < userArray.length(); i++){
                        
                        JSONObject users = userArray.getJSONObject(i);
                        JSONObject j = users.getJSONObject("name");
                        userList.add(j.getString("firstName") + "  " + j.getString("lastName"));
                        userId.add(users.get("_id"));
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
         
        rep.setItems(userList);
    }
    
    private void popEdit(){
        alias.setText(data.getCompanyName());
        accountName.setText(data.getAccountName());
        firstName.setText(data.getFirstName());
        lastName.setText(data.getLastName());
        phone.setText(data.getPhone());
        add1.setText(data.getAdd1());
        add2.setText(data.getAdd2());
        city.setText(data.getCity());
        zip.setText(data.getZip());
        state.getSelectionModel().select(data.getState());
//        country.getSelectionModel().select(data.getCountry());
        email.setText(data.getEmail());
        web.setText(data.getWeb());
        rep.getSelectionModel().select(data.getRep());
        contact.setText(data.getContact());
        credit.setText(data.getCredit());
        balance.setText(data.getBalance());
        term.setText(data.getTerm());
        discount.setText(data.getDiscount());
    }
    
    @FXML private void newAccount(){

        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("alis", alias.getText()));
        param.add(new BasicNameValuePair("accountName", accountName.getText()));
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("address_one", add1.getText()));
        param.add(new BasicNameValuePair("address_two", add2.getText()));
        param.add(new BasicNameValuePair("city", city.getText()));
        param.add(new BasicNameValuePair("zip", zip.getText()));
        param.add(new BasicNameValuePair("state", state.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("country", country.getSelectionModel().getSelectedItem().getCode()));
        param.add(new BasicNameValuePair("email", email.getText()));
        param.add(new BasicNameValuePair("phone", phone.getText()));
        param.add(new BasicNameValuePair("website", web.getText()));
        param.add(new BasicNameValuePair("rep", rep.getEditor().getText()));//need to load user and get user ID
//        param.add(new BasicNameValuePair("cred_accountNo", accountNo.getText()));
        param.add(new BasicNameValuePair("cred_creditLimit", credit.getText()));
        param.add(new BasicNameValuePair("cred_openBalance", balance.getText()));
        param.add(new BasicNameValuePair("cred_paymentTerm", term.getText()));
        param.add(new BasicNameValuePair("discount", discount.getText()));
        param.add(new BasicNameValuePair("performedBy", Storage.getId()));
     
        
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.createAccount(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "New Account Created and Saved", Pos.CENTER);
                                }
                            });
                        }else if(response.getInt("status") ==0){

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        Log.warn(response);
                                        Util.notify_ERROR(State.NOTIFY_ERROR, response.getString("message"), Pos.CENTER);
                                    } catch (JSONException ex) {
                                        Logger.getLogger(NewAccountController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Account Failed to Create", Pos.CENTER);
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
                    response = nav.editAccount(param);
                        if(response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Account Updated", Pos.CENTER);
                                }
                            });
                        }else if(response.getInt("status") ==0){

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        Util.notify_ERROR(State.NOTIFY_ERROR, response.getString("message"), Pos.CENTER);
                                    } catch (JSONException ex) {
                                        Logger.getLogger(NewAccountController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                        }else{
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify_ERROR(State.NOTIFY_ERROR, "Account Failed to Update", Pos.CENTER);
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
