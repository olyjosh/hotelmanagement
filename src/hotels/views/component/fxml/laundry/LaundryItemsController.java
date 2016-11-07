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
import hotels.views.component.fxml.laundry.model.LaundryItem;
import hotels.views.component.fxml.tools.HotelServiceListController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class LaundryItemsController implements Initializable {

    @FXML private TextField nameTxt;
    @FXML private ComboBox catCombo;
    @FXML private RadioButton allRadio;
    @FXML private RadioButton guestRadio;
    @FXML private RadioButton hotelRadio;
    
    @FXML private TableColumn desc;
    @FXML private TableColumn name;
    @FXML private TableColumn cat;
    @FXML private TableColumn vis;
    @FXML private TableView <LaundryItem> table;
      
    private Hotels app;
    private Navigator nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LaundryItemsController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    private static JSONObject item;
    private static JSONArray itemArray;
    private LaundryItem ls;
    private ObservableList<LaundryItem> service = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
        
        ToggleGroup tg = new ToggleGroup();
        guestRadio.setToggleGroup(tg);
        hotelRadio.setToggleGroup(tg);
        allRadio.setToggleGroup(tg);
        
        defaults();
        catCombo.getItems().addAll("Show All", "Fabric", "Linen", "Native", "Wool");
        
    }    
    
    private void onLoad(){
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    item = nav.fetchLaundryItems();
                    itemArray = item.getJSONArray("message");
                    
                    getLaundryItem();
                    
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
           
            
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            cat.setCellValueFactory(new PropertyValueFactory<>("cat"));
            vis.setCellValueFactory(new PropertyValueFactory<>("vis"));
            
            table.getColumns().setAll(desc, name, cat, vis);
    }
    
    private void defaults(){
        
        nameTxt.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(nameTxt.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryItem> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryItem, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(nameTxt.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        catCombo.getEditor().textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(catCombo.getEditor().textProperty().get().isEmpty() || catCombo.getEditor().textProperty().get().equals("Show All")) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryItem> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryItem, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(catCombo.getEditor().textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        guestRadio.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(guestRadio.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryItem> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryItem, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(guestRadio.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        hotelRadio.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(hotelRadio.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryItem> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryItem, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(hotelRadio.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        allRadio.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(allRadio.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LaundryItem> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LaundryItem, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(allRadio.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
    }
    
    private void getLaundryItem(){
        try {
            for(int i = 0; i < itemArray.length(); i++){
                
                ls = new LaundryItem();
                JSONObject oj = itemArray.getJSONObject(i);
                ls.setId(oj.getString("_id"));
                ls.setCat(oj.getString("category"));
                ls.setName(oj.getString("name"));
                
                if(oj.get("visibility").equals("true")){
                    ls.setVis("Hotel");
                }else{
                    ls.setVis("Guest");
                }
                
                ls.setDesc(oj.getString("desc"));
                
                service.addAll(ls);
                
            }
           table.setItems(service);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewItem(ActionEvent e) throws IOException{
        NewItemController controller = new NewItemController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newItem.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditItem(ActionEvent e) throws IOException{
       
        LaundryItem item = table.getSelectionModel().getSelectedItem();
        NewItemController controller = new NewItemController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newItem.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteItem(){
        LaundryItem item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteLaundryItem(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Laundry Item Deleted", Pos.CENTER);
                                service.remove(item);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Laundry Item Failed to Delete", Pos.CENTER);
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
