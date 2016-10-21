/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.laundry.model.DailyLaundry;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
public class DailyLaundryController implements Initializable {

    @FXML
    private TextField linenText;
    @FXML
    private ComboBox userCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox statusCombo;
    @FXML
    private TableView<DailyLaundry> table;
    @FXML
    private TableColumn linen;
    @FXML
    private TableColumn user;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn returned;
    @FXML
    private TableColumn remark;
    
    private JSONObject booking;
    private JSONArray bookArray;
    
    private Hotels app;
    private Navigator nav;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public DailyLaundryController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private static JSONObject daily;
    private static JSONArray dailyArray;
    private DailyLaundry ls;
    private ObservableList<DailyLaundry> service = FXCollections.observableArrayList();
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        onLoad();
    }    
    
    private void onLoad(){
       
            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    daily = nav.fetchDailyLaundry();
                    System.out.println("printing daily : " + daily);
                    dailyArray = daily.getJSONArray("message");
                    getDailyLaundry();
            
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };
        // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
            
            linen.setCellValueFactory(new PropertyValueFactory<>("item"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            user.setCellValueFactory(new PropertyValueFactory<>("user"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            returned.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            
            table.getColumns().setAll(linen, date, user, status, returned, remark );
       
    }
    
    private void getDailyLaundry(){
        try {
            for(int i = 0; i < dailyArray.length(); i++){
                ls = new DailyLaundry();
                JSONObject oj = dailyArray.getJSONObject(i);
                
                ls.setDate(Util.stripDate(oj.getString("date")));
                ls.setLinen(String.valueOf(oj.get("sn")));
                ls.setRemark(oj.getString("remark"));
               //ls.setStatus(oj.getString("status"));
                ls.setUser(oj.getString("user"));
                ls.setTotalBill(String.valueOf(oj.get("bill")));
                ls.setPaid(String.valueOf(oj.get("amonunt")));
                ls.setBalance(String.valueOf(oj.get("balance")));
                ls.setItem(oj.getString("item"));
                ls.setLaundryService(oj.getString("laundryService"));
                ls.setHotelService(oj.getString("hotelService"));
                ls.setReturns(oj.getString("returnIn"));
                ls.setReturnDate(oj.getString("returned"));
                
                service.addAll(ls);
                table.setItems(service);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewDailyLaundry(ActionEvent e) throws IOException{
      
        NewDailyController controller = new NewDailyController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newDaily.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditDaily(ActionEvent e) throws IOException{
       
        DailyLaundry item = table.getSelectionModel().getSelectedItem();
        NewDailyController controller = new NewDailyController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newDaily.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteLaundry(){
        DailyLaundry item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
               
                List <NameValuePair> param = new ArrayList<>();
                param.add(new BasicNameValuePair("id", item.getId()));
                JSONObject response = nav.deleteDailyLaundry(param);
                if(response != null){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_SUCCESS, "A Laundry has been Deleted", Pos.CENTER);
                            service.remove(item);
                        }
                    });
                }else{

                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_ERROR, "Laundry Failed to Delete", Pos.CENTER);
                        }
                    });
                }
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
}
