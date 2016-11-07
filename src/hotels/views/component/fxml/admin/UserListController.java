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
import javafx.scene.control.ComboBox;
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
public class UserListController implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private ComboBox roleCombo;
    @FXML
    private TableView<Users> table;
    @FXML
    private TableColumn username;
    @FXML
    private TableColumn role;
    @FXML
    private TableColumn dep;

    
    private Hotels app;
    private Navigator nav;
    private static JSONObject users;
    private static JSONArray usersArray;
    private Users ls;
    private ObservableList<Users> service = FXCollections.observableArrayList();
    
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public UserListController(Hotels app) {
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
                    users = nav.fetchUsers();
                    usersArray = users.getJSONArray("message");
                    
                    getUserList();
                    
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
           
            
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            dep.setCellValueFactory(new PropertyValueFactory<>("dep"));
            
            table.getColumns().setAll(username, role, dep);
       
    }
    
    private void getUserList(){
        try {
            for(int i = 0; i < usersArray.length(); i++){
                ls = new Users();
                JSONObject oj = usersArray.getJSONObject(i);
                System.out.println("Printing Hotel Service : " + oj);
                ls.setId(oj.getString("_id"));
                ls.setUsername(oj.getJSONObject("name").getString("username"));
                ls.setFirstname(oj.getJSONObject("name").getString("firstName"));
                ls.setLastname(oj.getJSONObject("name").getString("lastName"));
                ls.setPhone(oj.getString("phone"));
                //ls.setRole(oj.getString("role"));
                //ls.setDep(oj.getString("dep"));
                ls.setCountry(oj.getString("country"));
                ls.setDob(oj.getString("dob"));
                ls.setEmail(oj.getString("email"));
                ls.setSex(oj.getString("sex"));
                ls.setStaff(String.valueOf(oj.getJSONObject("staff").get("isStaff")));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    @FXML 
    private void showNewUser(ActionEvent e) throws IOException{
        NewUserController controller = new NewUserController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newUser.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditUser(ActionEvent e) throws IOException{
       
        Users item = table.getSelectionModel().getSelectedItem();
        NewUserController controller = new NewUserController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newUser.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteUser(){
        Users item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteHotelService(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "User Account has been Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "User Account Failed to Delete", Pos.CENTER);
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
