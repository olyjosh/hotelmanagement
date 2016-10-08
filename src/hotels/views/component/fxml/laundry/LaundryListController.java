/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.views.component.fxml.laundry.model.DailyLaundry;
import hotels.views.component.fxml.laundry.model.LaundryList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class LaundryListController implements Initializable {

    
    private TableView<LaundryList> table;
    private TableColumn room;
    private TableColumn guest;
    private TableColumn date;
    private TableColumn items;
    private TableColumn total;
    private TableColumn posted;
    private TableColumn balance;
    private TableColumn status;
    private TableColumn user;
      
    private Hotels app;
    private Navigator nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LaundryListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
       
    private static JSONObject daily;
    private static JSONArray dailyArray;
    private LaundryList ls;
    private ObservableList<LaundryList> service = FXCollections.observableArrayList();
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            
            daily = nav.fetchDailyLaundry();
            System.out.println("printing daily : " + daily);
            dailyArray = daily.getJSONArray("message");
            
            getLaundryList();
            System.out.println("printing service : " + service);
            table.setItems(service);
            
            room.setCellValueFactory(new PropertyValueFactory<>("room"));
            guest.setCellValueFactory(new PropertyValueFactory<>("guest"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            items.setCellValueFactory(new PropertyValueFactory<>("items"));
            total.setCellValueFactory(new PropertyValueFactory<>("total"));
            posted.setCellValueFactory(new PropertyValueFactory<>("posted"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));
            
            table.getColumns().setAll(room, guest, date, items, total, posted, balance, status, user);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
    private void getLaundryList(){
        try {
            for(int i = 0; i < dailyArray.length(); i++){
                ls = new LaundryList();
                JSONObject oj = dailyArray.getJSONObject(i);
                
                //ls.setRoom(oj.getString("room"));
                //ls.setGuest(String.valueOf(oj.get("guest")));
                ls.setDate(oj.getString("date"));
                ls.setItems(oj.getString("item"));
                ls.setTotal(String.valueOf(oj.get("bill")));
                //ls.setPosted(oj.getString("posted"));
                ls.setBalance(String.valueOf(oj.get("balance")));
                //ls.setStatus(oj.getString("status"));
                ls.setUser(oj.getString("user"));
                
                service.addAll(ls);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
}
