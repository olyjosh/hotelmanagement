/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import hotels.util.Navigator;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class GuestListController implements Initializable {

    private static JSONObject oj, oj2, guests;
    private static JSONArray guestArray;
    private Navigator nav = new Navigator();
    private ObservableList guestList = FXCollections.observableArrayList();
    private ObservableList mirror = FXCollections.observableArrayList();
    private static ObservableMap<String, String> detail = FXCollections.observableHashMap();
    private static ObservableList<ObservableMap> data = FXCollections.observableArrayList();

    @FXML
    private ListView<?> guest;
    @FXML
    private Label fname;
    @FXML
    private Label lname;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label roomType;
    @FXML
    private Label roomNo;
    @FXML
    private DatePicker fdate;
    @FXML
    private DatePicker tdate;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        onLoad();
        mirror = guestList;
        guest.setItems(guestList);
        
//        guest.setOnMouseClicked(value);getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
 
//        });
        guest.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
                String name = guest.getSelectionModel().getSelectedItem().toString();
                System.out.println("ListView Selection Changed (selected: " + name);
                int index = guest.getSelectionModel().getSelectedIndex();
                try {
                    oj = guestArray.getJSONObject(index);
                    oj2 = oj.getJSONObject("name");

                    System.out.println("index at : "+ index);

                    fname.setText("   "+ oj2.getString("firstName"));
                    lname.setText("   "+ oj2.getString("lastName"));
                    phone.setText("   "+ oj.getString("phone"));
                    email.setText("   "+ oj.getString("email"));
                    address.setText("   "+ oj.getString("address"));

                } catch (JSONException ex) {
                    ex.printStackTrace();
                } 
            }
        });
        
        tdate.getEditor().textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    search();
                } catch (JSONException ex) {
                   ex.printStackTrace();
                }
            }
        });
    }    
    
    private void onLoad(){
        try{
            guests = nav.fetchGuest();
            guestArray = guests.getJSONArray("message");
            for(int i = 0; i < guestArray.length(); i++){
                oj = guestArray.getJSONObject(i);
                oj2 = oj.getJSONObject("name");
                String name = oj2.getString("firstName") + "  " + oj2.getString("lastName");
                guestList.add(name);
            }
            System.out.println("Guest list : " + guestList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void search() throws JSONException {
        
        LocalDate date1 = fdate.getValue();
        LocalDate date2 = tdate.getValue();
        
        if (date1 == null) {
            guest.setItems(guestList);
        }
        ObservableList newGuestList = FXCollections.observableArrayList();

        guestArray = guests.getJSONArray("message");
            for(int i = 0; i < guestArray.length(); i++){
                oj = guestArray.getJSONObject(i);
                String rawDate = oj.getString("createdAt");
                
                ZonedDateTime date = ZonedDateTime.parse(rawDate);
                LocalDate createDate = date.toLocalDate();
                System.out.println("created date : " + createDate);
                
                if((!createDate.isBefore(date1) || createDate.isEqual(date1))  && (createDate.isEqual(date2) || (!createDate.isAfter(date2)))){
                    oj2 = oj.getJSONObject("name");
                    String name = oj2.getString("firstName") + "  " + oj2.getString("lastName");
                    
                    newGuestList.add(name);
                }
            }
        guest.setItems(newGuestList);
      }
}
