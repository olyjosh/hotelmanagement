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
import hotels.views.component.fxml.tools.model.Phone;
import hotels.views.component.fxml.tools.model.Reminder;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class PhoneListController implements Initializable {

    
              
    private Hotels app;
    private Navigator nav;
    private JSONObject response, oj;
    private JSONArray array;
   
    @FXML
    private TextField title;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ComboBox ref;
    @FXML
    private ComboBox gender;
    @FXML
    private TextField mobile;
    @FXML
    private TextField work;
    @FXML
    private TextField residence;
    @FXML
    private TextField email;
    @FXML
    private TextArea address;
    @FXML
    private TextField city;
    @FXML
    private TextField state;
    @FXML
    private TextField zip;
    @FXML
    private TextField country;
    @FXML
    private TextArea remark;
    @FXML
    private ListView<Phone> phoneList;
    
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public PhoneListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private ObservableList service = FXCollections.observableArrayList();
    private ObservableList phoneService = FXCollections.observableArrayList();
    private Phone ls;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
        defaults();
    }    
    
    private void onLoad(){
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    response = nav.fetchPhone();
                    if(response != null){
                        array = response.getJSONArray("message");
                        System.out.println("Printing phone : " + array);
                        for(int i = 0; i < array.length(); i++){
                            ls = new Phone();
                            oj = array.getJSONObject(i);
                            String name = oj.getString("firstName") + "  " + oj.getString("lastName");
                            
                            ls.setAddress(oj.getString("contact_address"));
                            ls.setCity(oj.getString("contact_city"));
                            ls.setCountry(oj.getString("contact_country"));
                            ls.setEmail(oj.getString("contact.email"));
                            ls.setFirstName(oj.getString("firstName"));
                            ls.setGender(oj.getString("lastName"));
                            ls.setId(oj.getString("_id"));
                            ls.setLastName(oj.getString("lastName"));
                            ls.setMobile(oj.getString("contact_mobile"));
                            ls.setRef(oj.getString("ref"));
                            ls.setRemark(oj.getString("remark"));
                            ls.setResidence(oj.getString("contact_residence"));
                            ls.setState(oj.getString("contact_state"));
                            ls.setTitle(oj.getString("title"));
                            ls.setWork(oj.getString("contact_work"));
                            ls.setZip(oj.getString("contact_zip"));
                            
                            service.add(name);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                phoneList.getItems().addAll(service);
            }
        };
        // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
            
        phoneList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
                String name = phoneList.getSelectionModel().getSelectedItem().toString();
                System.out.println("ListView Selection Changed (selected: " + name);
                int index = phoneList.getSelectionModel().getSelectedIndex();
                try {
                    response = array.getJSONObject(index);
                    oj = response.getJSONObject("name");

                    System.out.println("index at : "+ index);
                    
                    Phone data = phoneList.getSelectionModel().getSelectedItem();

                    ref.getSelectionModel().select(data.getRef());
                    title.setText(data.getTitle());
                    firstName.setText(data.getFirstName());
                    lastName.setText(data.getLastName());
                    gender.getSelectionModel().select(data.getGender());
                    mobile.setText(data.getMobile());
                    work.setText(data.getWork());

                    residence.setText(data.getResidence());
                    email.setText(data.getEmail());
                    address.setText(data.getAddress());
                    city.setText(data.getCity());
                    state.setText(data.getState());
                    zip.setText(data.getZip());
                    country.setText(data.getCountry());
                    remark.setText(data.getRemark());

                } catch (JSONException ex) {
                    ex.printStackTrace();
                } 
            }
        });    
           
    }
    
    private void defaults(){
        
        ref.getItems().addAll("Friends", "Newspaper", "TV Advert", "Google Search");
        gender.getItems().addAll("Male", "Female");
    }
    
    private void clear(){
        ref.getSelectionModel().select(0);
        title.setText("");
        firstName.setText("");
        lastName.setText("");
        gender.getSelectionModel().select(0);
        mobile.setText("");
        work.setText("");
        
        residence.setText("");
        email.setText("");
        address.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        country.setText("");
        remark.setText("");
    }
    
    @FXML private void newPhone(){

        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("ref", ref.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("title", title.getText()));
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("gender", gender.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("contact_mobile", mobile.getText()));
        param.add(new BasicNameValuePair("contact_workPhone", work.getText()));
        
        param.add(new BasicNameValuePair("contact_residence", residence.getText()));
        param.add(new BasicNameValuePair("contact.email", email.getText()));
        param.add(new BasicNameValuePair("contact_address", address.getText()));
        param.add(new BasicNameValuePair("contact_city", city.getText()));
        param.add(new BasicNameValuePair("contact_state", state.getText()));
        param.add(new BasicNameValuePair("contact_zip", zip.getText()));
        param.add(new BasicNameValuePair("contact_country", country.getText()));
        param.add(new BasicNameValuePair("remarks", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                
                    response = nav.createNewPhone(param);
                    System.out.println("Printing Created Phone : " +response);
                    if(response != null){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "A New Phone Directory is Created", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "New Phone Directory Failed", Pos.CENTER);
                            }
                        });
                    }   
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        //=========Refresh List View and UI=========
        service.clear();
        onLoad();
        clear();
        
    }
    
    @FXML private void editPhone(){
        
        Phone data = phoneList.getSelectionModel().getSelectedItem();

        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("_id", data.getId()));
        param.add(new BasicNameValuePair("ref", ref.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("title", title.getText()));
        param.add(new BasicNameValuePair("firstName", firstName.getText()));
        param.add(new BasicNameValuePair("lastName", lastName.getText()));
        param.add(new BasicNameValuePair("gender", gender.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("contact_mobile", mobile.getText()));
        param.add(new BasicNameValuePair("contact_workPhone", work.getText()));
        
        param.add(new BasicNameValuePair("contact_residence", residence.getText()));
        param.add(new BasicNameValuePair("contact.email", email.getText()));
        param.add(new BasicNameValuePair("contact_address", address.getText()));
        param.add(new BasicNameValuePair("contact_city", city.getText()));
        param.add(new BasicNameValuePair("contact_state", state.getText()));
        param.add(new BasicNameValuePair("contact_zip", zip.getText()));
        param.add(new BasicNameValuePair("contact_country", country.getText()));
        param.add(new BasicNameValuePair("remarks", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                
                    response = nav.editPhone(param);
                    if(response != null){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Phone Directory is Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Phone Directory Failed to Update", Pos.CENTER);
                            }
                        });
                    }   
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        //=========Refresh List View and UI=========
        service.clear();
        onLoad();
        clear();
    }
    
    @FXML private void deletePhone(){
        Phone item = phoneList.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
               
                List <NameValuePair> param = new ArrayList<>();
                param.add(new BasicNameValuePair("id", item.getId()));
                JSONObject response = nav.deleteRemider(param);
                if(response != null){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_SUCCESS, "A Phone Directory has been Deleted", Pos.CENTER);
                        }
                    });
                }else{

                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_ERROR, "Phone Directory Failed to Delete", Pos.CENTER);
                        }
                    });
                }
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        //=========Refresh List View and UI=========
        service.clear();
        onLoad();
        clear();
    }
}
