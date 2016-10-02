/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
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
public class NewDailyController implements Initializable {

    @FXML
    private TextField serialText;
    @FXML
    private DatePicker dateIssued;
    @FXML
    private ComboBox<?> laundryStatus;
    @FXML
    private ComboBox<?> returnIn;
    @FXML
    private TextArea remark;
    @FXML
    private ComboBox<?> laundryUser;
    @FXML
    private ComboBox<?> laundryService;
    @FXML
    private ComboBox<?> hotelService;
    @FXML
    private DatePicker returnDate;
    @FXML
    private ComboBox<?> laundryItem;
    @FXML
    private TextField totalBill;
    @FXML
    private TextField amountPaid;
    @FXML
    private TextField totalBalance;
    @FXML
    private Button addBtn;

    private Navigator nav;
    private JSONObject response;
    
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewDailyController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        System.out.println("New daily Controller coming up");
        
       
    }    
    
    private void onLoad(){
        
    }
    
    @FXML
    private void newDailyLaundry(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("sn", serialText.getText()));
        param.add(new BasicNameValuePair("date", dateIssued.getValue().toString()));
        param.add(new BasicNameValuePair("item", laundryItem.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("user", laundryUser.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("status", laundryStatus.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("laundryService", laundryService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("hotelService", hotelService.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("returnIn", returnIn.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("returned", returnDate.getValue().toString()));//Storage.getId()));
        param.add(new BasicNameValuePair("bill", totalBill.getText()));
        param.add(new BasicNameValuePair("amonunt", amountPaid.getText()));
        param.add(new BasicNameValuePair("balance", totalBalance.getText()));
        param.add(new BasicNameValuePair("remark", remark.getText()));
        param.add(new BasicNameValuePair("performedBy", laundryUser.getSelectionModel().getSelectedItem().toString()));
           
        System.out.println("Invoking new Daily Laundry Event");
        response = nav.createDailyLaundry(param);
        System.out.println("Booking a Room : " + response);
        
        nav.notify((Stage) serialText.getScene().getWindow(), Pos.CENTER, State.NOTIFY_SUCCESS, "New Laundry Record Saved", 100,500);
    }
}
