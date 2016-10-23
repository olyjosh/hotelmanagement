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
import hotels.views.component.fxml.tools.model.LostFound;
import hotels.views.component.fxml.tools.model.Reminder;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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
public class ReminderListController implements Initializable {

    
    @FXML TableView<Reminder> table;
    @FXML TableColumn startDate;
    @FXML TableColumn name;
    @FXML TableColumn message;
    @FXML TableColumn priority;
    @FXML TableColumn status;
    
              
    private Hotels app;
    private Navigator nav;
    
    private static JSONObject reminder;
    private static JSONArray reminderArray;
    private Reminder ls;
    private ObservableList<Reminder> service = FXCollections.observableArrayList();
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public ReminderListController(Hotels app) {
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
                    reminder = nav.fetchReminder();
                    reminderArray = reminder.getJSONArray("message");
                    getReminderList();
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
           
            
            startDate.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            message.setCellValueFactory(new PropertyValueFactory<>("message"));
            priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            
            
            table.getColumns().setAll(startDate, name, message, priority, status);
       
    }
    
    private void getReminderList(){
        try {
            for(int i = 0; i < reminderArray.length(); i++){
                ls = new Reminder();
                JSONObject oj = reminderArray.getJSONObject(i);
                
                ls.setId(oj.getString("_id"));
                ls.setName(oj.getString("name"));
                ls.setInterval(oj.getString("interval"));
                ls.setMessage(oj.getString("message"));
                ls.setPriority(oj.getString("priority"));
                
                ls.setRecievers(oj.get("receivers").toString());//receivers
                ls.setStartTime(oj.getString("startTime"));
                
                ls.setStatus(oj.getString("stopAfter"));
                
                
                
                service.addAll(ls);
                
                System.out.println("printing Loaded Reminders : " + ls.toString());
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewReminder(ActionEvent e) throws IOException{
      
        NewReminderController controller = new NewReminderController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newReminder.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditLostFound(ActionEvent e) throws IOException{
       
        Reminder item = table.getSelectionModel().getSelectedItem();
        EditReminderController controller = new EditReminderController(this.getApp());
        controller.setApp(app);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newReminder.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteReminder(){
        Reminder item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
               
                List <NameValuePair> param = new ArrayList<>();
                param.add(new BasicNameValuePair("id", item.getId()));
                JSONObject response = nav.deleteRemider(param);
                if(response != null){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_SUCCESS, "A Reminder has been Deleted", Pos.CENTER);
                            service.remove(item);
                        }
                    });
                }else{

                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            Util.notify(State.NOTIFY_ERROR, "Reminder Failed to Delete", Pos.CENTER);
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
