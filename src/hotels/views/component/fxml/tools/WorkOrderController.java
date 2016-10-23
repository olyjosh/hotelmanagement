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
import hotels.views.component.fxml.tools.model.WorkOrder;
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
import javafx.scene.control.DatePicker;
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
 * @author NOVA
 */
public class WorkOrderController implements Initializable {

    @FXML
    private ComboBox statusCombo;
    @FXML
    private DatePicker dateSearch;
    @FXML
    private ComboBox roomCombo;
    @FXML
    private TableColumn orderNo;
    @FXML
    private TableColumn dateIssued;
    @FXML
    private TableColumn workType;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn room;
    @FXML
    private TableView<WorkOrder> table;

    
    private Hotels app;
    private Navigator nav;
    private static JSONObject work;
    private static JSONArray workArray;
    private WorkOrder ls;
    private ObservableList<WorkOrder> service = FXCollections.observableArrayList();
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public WorkOrderController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
        
        onLoad();
    }    
    
    private void defaults(){
        
        statusCombo.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(statusCombo.getSelectionModel().selectedItemProperty().get().equals("")) {
                table.setItems(service);
                return;
            }
            ObservableList<WorkOrder> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<WorkOrder, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(statusCombo.getSelectionModel().selectedItemProperty().get().toString().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        roomCombo.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(roomCombo.getSelectionModel().selectedItemProperty().get().equals("")) {
                table.setItems(service);
                return;
            }
            ObservableList<WorkOrder> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<WorkOrder, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(roomCombo.getSelectionModel().selectedItemProperty().get().toString().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        dateSearch.getEditor().textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(dateSearch.getEditor().textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<WorkOrder> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<WorkOrder, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(dateSearch.getEditor().textProperty().get().toLowerCase())) {
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
                    List <NameValuePair> param = new ArrayList<>();
                    work = nav.fetchHotelService(param);
                    workArray = work.getJSONArray("message");
                    
                    getWorkOrder();
                    
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
           
            
            orderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
            dateIssued.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));
            workType.setCellValueFactory(new PropertyValueFactory<>("workType"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            room.setCellValueFactory(new PropertyValueFactory<>("room"));
            
            table.getColumns().setAll(orderNo, dateIssued, workType, status, room);
       
    }
    
    private void getWorkOrder(){
        try {
            for(int i = 0; i < workArray.length(); i++){
                ls = new WorkOrder();
                JSONObject oj = workArray.getJSONObject(i);
                ls.setId(oj.getString("_id"));
                ls.setDateIssued(oj.getString("date"));
                ls.setWorkType(oj.getString("type"));
                ls.setOrderNo(oj.getString("workOrderNo"));
                ls.setDes(oj.getString("desc"));
                ls.setAssignedTo(oj.getString("assignedTo"));
                ls.setRoom(oj.getString("room"));
                ls.setStatus(oj.getString("status"));
                ls.setDueDate(oj.getString("dueDate"));
                ls.setRemark(oj.getString("remark"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewWorkOrder(ActionEvent e) throws IOException{
        NewWorkOrderController controller = new NewWorkOrderController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newWorkOrder.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditWorkOrder(ActionEvent e) throws IOException{
       
        WorkOrder item = table.getSelectionModel().getSelectedItem();
        NewWorkOrderController controller = new NewWorkOrderController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newWorkOrder.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteWorkOrder(){
        WorkOrder item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteWorkOrder(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Work Order Information Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Work Order Failed to Delete", Pos.CENTER);
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
