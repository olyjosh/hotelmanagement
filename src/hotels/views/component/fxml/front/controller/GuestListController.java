/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.controller;

import hotels.util.Navigator;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        
        guest.setItems(guestList);
        
        guest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
            int index = guest.getSelectionModel().getSelectedIndex();
            try {
                oj = guestArray.getJSONObject(index);
                oj2 = oj.getJSONObject("name");

                fname.setText("   "+ oj2.getString("firstName"));
                lname.setText("   "+ oj2.getString("lastName"));
                phone.setText("   "+ oj.getString("phone"));
                email.setText("   "+ oj.getString("email"));
                address.setText("   "+ oj.getString("address"));

            } catch (JSONException ex) {
                ex.printStackTrace();
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void filter() throws ParseException{
        guest.setItems(guestList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = sdf.parse(fdate.getValue().toString());
        Date date2 = sdf.parse(tdate.getValue().toString());
        
        try{
            guestArray = guests.getJSONArray("message");
            for(int i = 0; i < guestArray.length(); i++){
                oj = guestArray.getJSONObject(i);
                
                Date createDate = sdf.parse(oj.getString("createdAt"));
                if(date1.before(createDate)  || date2.after(createDate)){
                    oj2 = oj.getJSONObject("name");
                    String name = oj2.getString("firstName") + "  " + oj2.getString("lastName");
                    System.out.println("Created Date :" + createDate);
                    //guestList.clear();
                    guestList.remove(i);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
