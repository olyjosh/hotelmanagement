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
import hotels.views.component.fxml.tools.LostFoundController;
import hotels.views.component.fxml.tools.NewLostFoundController;
import hotels.views.component.fxml.tools.model.LostFound;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
public class RoomListController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private TableView<Room> table;
    @FXML
    private TableColumn alias;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn floor;
    
    
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public RoomListController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private Navigator nav;
    private JSONObject response;
    private static JSONObject room;
    private static JSONArray roomArray;
    private Room ls;
    private ObservableList<Room> service = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        onLoad();
        defaults();
    }    
    
    private void defaults(){
        
        search.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(search.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<Room> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Room, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(search.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
   
    }
    
    private void onLoad(){
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    room = nav.fetchRoom();
                    roomArray = room.getJSONArray("message");
                    System.out.println(roomArray);
                    getRoomList();
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
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
            
            table.getColumns().setAll(alias, name, type, floor);
     
    }
    
    private void getRoomList(){
        try {
            for(int i = 0; i < roomArray.length(); i++){
                ls = new Room();
                JSONObject oj = roomArray.getJSONObject(i);
                
                ls.setId(oj.getString("_id"));
                
//                if(oj.getJSONObject("floor").getString("name") == null){
//                    ls.setFloor("");
//                }else{
//                    ls.setFloor(oj.getJSONObject("floor").getString("name"));
//                }
                
//                if(oj.getJSONObject("roomType").get("name") == null){
//                    ls.setType("");
//                }else{
//                    ls.setType(oj.getJSONObject("roomType").getString("name"));
//                }
              
                ls.setDesc(oj.getString("desc"));
                ls.setName(oj.getString("name"));
                
                ls.setAlias(oj.getString("alias"));
                
                service.addAll(ls);
                
                System.out.println("printing Loaded Rooms : " + ls.toString());
                
                
            }
           table.setItems(service);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewRoom(ActionEvent e) throws IOException{
        NewRoomController controller = new NewRoomController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoom.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditRoom(ActionEvent e) throws IOException{
       
        Room item = table.getSelectionModel().getSelectedItem();
        NewRoomController controller = new NewRoomController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoom.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteRoom(){
        Room item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteRoom(param);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, item.getName() + "has been Deleted", Pos.CENTER);
                                service.remove(item);
                                
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, item.getName() + "has been Deleted", Pos.CENTER);
                            }
                        });
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(LostFoundController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
}
