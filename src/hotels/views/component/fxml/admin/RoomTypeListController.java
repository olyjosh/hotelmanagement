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
import hotels.views.component.fxml.tools.HotelServiceListController;
import hotels.views.component.fxml.tools.NewHotelServiceController;
import hotels.views.component.fxml.tools.model.HotelService;
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
 * @author NOVA
 */
public class RoomTypeListController implements Initializable {

    @FXML
    private TextField type;
    @FXML
    private TableView<RoomType> table;
    @FXML
    private TableColumn alias;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn ba;
    @FXML
    private TableColumn bc;
    @FXML
    private TableColumn ma;
    @FXML
    private TableColumn mc;
    @FXML
    private TableColumn desc;
    
    
    private Hotels app;
    private Navigator nav;
    private static JSONObject roomtype;
    private static JSONArray roomtypeArray;
    private RoomType ls;
    private ObservableList<RoomType> service = FXCollections.observableArrayList();
    
     public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public RoomTypeListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }

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
                    roomtype = nav.fetchRoomType();
                    roomtypeArray = roomtype.getJSONArray("message");
                    
                    getRoomTypeList();
                    
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
            ba.setCellValueFactory(new PropertyValueFactory<>("ba"));
            bc.setCellValueFactory(new PropertyValueFactory<>("bc"));
            ma.setCellValueFactory(new PropertyValueFactory<>("ma"));
            mc.setCellValueFactory(new PropertyValueFactory<>("mc"));
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            
            table.getColumns().setAll(alias, name, ba, bc, ma, mc, desc);
       
    }
    
    private void getRoomTypeList(){
        try {
            for(int i = 0; i < roomtypeArray.length(); i++){
                ls = new RoomType();
                JSONObject oj = roomtypeArray.getJSONObject(i);
                ls.setId(oj.getString("_id"));
                ls.setBa(String.valueOf(oj.getJSONObject("pax").get("baseAdult")));
                ls.setDesc(oj.getString("desc"));
                ls.setBc(String.valueOf(oj.getJSONObject("pax").get("baseChild")));
                ls.setMa(String.valueOf(oj.getJSONObject("pax").get("maxAdult")));
                ls.setMc(String.valueOf(oj.getJSONObject("pax").get("maxChild")));
                ls.setAlias(oj.getString("alias"));
                ls.setName(oj.getString("name"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    
    @FXML 
    private void showNewRoomType(ActionEvent e) throws IOException{
        NewRoomTypeController controller = new NewRoomTypeController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoomType.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditRoomType(ActionEvent e) throws IOException{
       
        RoomType item = table.getSelectionModel().getSelectedItem();
        NewRoomTypeController controller = new NewRoomTypeController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoomType.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteRoomType(){
        RoomType item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteRoomType(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Room Type Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Room Type Failed to Delete", Pos.CENTER);
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
