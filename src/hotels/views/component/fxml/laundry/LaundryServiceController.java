/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.views.component.fxml.front.model.Reserve;
import hotels.views.component.fxml.laundry.model.LaundryService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class LaundryServiceController implements Initializable {

    
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn charge;
    @FXML
    private TableColumn desc;
    @FXML
    private TableView<LaundryService> table;
    
    private Hotels app;
    private Navigator nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LaundryServiceController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private static JSONObject laundry;
    private static JSONArray laundryArray;
    private LaundryService ls;
    private ObservableList<LaundryService> service = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            List <NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("servive", "laundry"));
            
            laundry = nav.fetchLaundryService(param);
            laundryArray = laundry.getJSONArray("message");
            
            getLaundryService();
            System.out.println("Printing Service before table : " + service);
            table.setItems(service);
            
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            charge.setCellValueFactory(new PropertyValueFactory<>("charge"));
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            
            table.getColumns().setAll(name, charge, desc);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }    
    
    private void getLaundryService(){
        try {
            for(int i = 0; i < laundryArray.length(); i++){
                ls = new LaundryService();
                JSONObject oj = laundryArray.getJSONObject(i);
                
                ls.setCharge(oj.getString("extraCharge"));
                ls.setDesc(oj.getString("desc"));
                ls.setName(oj.getString("name"));
                
                service.addAll(ls);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("Printing service : " +service );
    }
}
