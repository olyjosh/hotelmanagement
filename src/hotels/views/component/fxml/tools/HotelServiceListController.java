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
import hotels.views.component.fxml.tools.model.HotelService;
import hotels.views.component.fxml.tools.model.LostFound;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class HotelServiceListController implements Initializable {

    
              
    private Hotels app;
    private Navigator nav;
    @FXML
    private TableColumn alias;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn charge;
    @FXML
    private TableColumn desc;
    @FXML
    private TableView<HotelService> table;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public HotelServiceListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    
    private static JSONObject hotel;
    private static JSONArray hotelArray;
    private HotelService ls;
    private ObservableList<HotelService> service = FXCollections.observableArrayList();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        onLoad();
    }    
    
    
    private void onLoad(){
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("servive", "hotel"));
                    hotel = nav.fetchHotelService(param);
                    hotelArray = hotel.getJSONArray("message");
                    
                    getLostList();
                    
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
           
            
            alias.setCellValueFactory(new PropertyValueFactory<>("alias"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            charge.setCellValueFactory(new PropertyValueFactory<>("charge"));
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            
            table.getColumns().setAll(alias, name, charge, desc);
       
        
//        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
//            //Check whether item is selected and print value of selected item
//            if (table.getSelectionModel().getSelectedItem() != null) {
//                System.out.println("Printing Selected value ID : "+ newValue.getItemName());
//                selectedID = newValue.getId();
//            }
//        });
    }
    
    private void getLostList(){
        try {
            for(int i = 0; i < hotelArray.length(); i++){
                ls = new HotelService();
                JSONObject oj = hotelArray.getJSONObject(i);
                System.out.println("Printing Hotel Service : " + oj);
                ls.setId(oj.getString("_id"));
                ls.setCharge(oj.getString("extraCharge"));
                ls.setDesc(oj.getString("desc"));
                ls.setImage(oj.getString("image"));
                ls.setName(oj.getString("name"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewHotelService(ActionEvent e) throws IOException{
        NewHotelServiceController controller = new NewHotelServiceController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newHotelService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditHotelService(ActionEvent e) throws IOException{
       
        HotelService item = table.getSelectionModel().getSelectedItem();
        EditHotelServiceController controller = new EditHotelServiceController(this.getApp());
        controller.setApp(app);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newHotelService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteHotelService(){
        HotelService item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteHotelService(param);
                    System.out.println("Deleting Lost & Found : " + response);
                    
                    if(response != null && response.getInt("status") == 200){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Hotel Service Information Deleted", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Hotel Service Failed to Delete", Pos.CENTER);
                            }
                        });
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(HotelServiceListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
}
