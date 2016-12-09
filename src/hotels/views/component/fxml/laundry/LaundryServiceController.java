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
import hotels.views.component.fxml.front.model.Reserve;
import hotels.views.component.fxml.laundry.model.LaundryService;
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
        
        onLoad();
        
        
       
    }    
    
    private void onLoad(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("servive", "laundry"));

                    laundry = nav.fetchLaundryService(param);
                    laundryArray = laundry.getJSONArray("message");

                    getLaundryService();
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
            
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            charge.setCellValueFactory(new PropertyValueFactory<>("charge"));
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));

            table.getColumns().setAll(name, charge, desc);
    }
    
    private void getLaundryService(){
        try {
            for(int i = 0; i < laundryArray.length(); i++){
                ls = new LaundryService();
                JSONObject oj = laundryArray.getJSONObject(i);
                
                ls.setId(oj.getString("_id"));
                //ls.setAlias(oj.getString("alias"));
                ls.setCharge(oj.getString("extraCharge"));
                ls.setDesc(oj.getString("desc"));
                ls.setName(oj.getString("name"));
                
                service.addAll(ls);
                System.out.println("Printing Service before table : " + service);
                table.setItems(service);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("Printing service : " +service );
    }
    
    @FXML 
    private void showNewLaundryService(ActionEvent e) throws IOException{
        NewLaundryServiceController controller = new NewLaundryServiceController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newLaundryService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditLaundryService(ActionEvent e) throws IOException{
        
        LaundryService data = table.getSelectionModel().getSelectedItem();
        
        NewLaundryServiceController controller = new NewLaundryServiceController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(true);
        controller.setData(data);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newLaundryService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML
    private void deleteLaundryService(){
        LaundryService item = table.getSelectionModel().getSelectedItem();
        System.out.println(item.getId());
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    //param.add(new BasicNameValuePair("servive", "laundry"));
                    JSONObject response = nav.deleteLaundryService(param);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Laundry Service Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Laundry Service Failed to Delete", Pos.CENTER);
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
