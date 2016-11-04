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
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class LostFoundController implements Initializable {

    
    @FXML
    private TextField itemText;
    @FXML
    private TextField whereText;
    @FXML
    private TextField roomText;
    @FXML
    private DatePicker dateText;
    @FXML
    private TableColumn entryDate;
    @FXML
    private TableColumn itemName;
    @FXML
    private TableColumn whereLost;
    @FXML
    private TableColumn itemColour;
    @FXML
    private TableColumn roomNo;
    @FXML
    private TableColumn returnDate;
    @FXML
    private TableColumn returnBy;
    @FXML
    private TableColumn discardDate;
    @FXML
    private TableColumn discardBy;
    @FXML
    private TableView<LostFound> table;
    
    
    private Hotels app;
    private Navigator nav;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public LostFoundController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    
    private static JSONObject lost;
    private static JSONArray lostArray;
    private LostFound ls;
    private ObservableList<LostFound> service = FXCollections.observableArrayList();
       
    
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
        
        Util.formatDatePicker(dateText);
        
        itemText.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(itemText.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LostFound> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LostFound, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(itemText.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        whereText.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(whereText.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LostFound> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LostFound, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(whereText.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        roomText.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(roomText.textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LostFound> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LostFound, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(roomText.textProperty().get().toLowerCase())) {
                        tableItems.add(service.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        dateText.getEditor().textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(dateText.getEditor().textProperty().get().isEmpty()) {
                table.setItems(service);
                return;
            }
            ObservableList<LostFound> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<LostFound, ?>> cols = table.getColumns();
            for(int i=0; i<service.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(service.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(dateText.getEditor().textProperty().get().toLowerCase())) {
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
                    lost = nav.fetchLostFound();
                    lostArray = lost.getJSONArray("message");
                    System.out.println(lostArray);
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
           
            
            entryDate.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
            itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            whereLost.setCellValueFactory(new PropertyValueFactory<>("whereLost"));
            itemColour.setCellValueFactory(new PropertyValueFactory<>("itemColour"));
            roomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
            returnBy.setCellValueFactory(new PropertyValueFactory<>("discardBy"));
            discardDate.setCellValueFactory(new PropertyValueFactory<>("discardDate"));
            discardBy.setCellValueFactory(new PropertyValueFactory<>("discardBy"));
            
            table.getColumns().setAll(entryDate, itemName, whereLost, itemColour, roomNo, returnDate, returnBy, discardDate, discardBy);
       
        
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
            for(int i = 0; i < lostArray.length(); i++){
                ls = new LostFound();
                JSONObject oj = lostArray.getJSONObject(i);
                
                ls.setId(oj.getString("_id"));
                
                if(oj.getJSONObject("reso").getString("discardBy") == null){
                    ls.setDiscardBy("");
                }else{
                    ls.setDiscardBy(oj.getJSONObject("reso").getString("discardBy"));
                }
                
                if(oj.getJSONObject("reso").get("discardDate") == null){
                    ls.setDiscardDate("");
                }else{
                    ls.setDiscardDate(Util.stripDate(String.valueOf(oj.getJSONObject("reso").get("discardDate"))));
                }
//                
//                
                ls.setEntryDate(Util.stripDate(oj.getString("onDate")));
                ls.setItemColour(oj.getString("color"));
                ls.setItemName(oj.getString("name"));
                
                if(oj.getJSONObject("reso").get("returnBy") == null){
                    ls.setReturnBy("");
                }else{
                    ls.setReturnBy(oj.getJSONObject("reso").getString("returnBy")); 
                }
                if(oj.getJSONObject("reso").get("returnDate") == null){
                    ls.setReturnDate("");
                }else{
                    ls.setReturnDate(Util.stripDate(String.valueOf(oj.getJSONObject("reso").get("returnDate"))));
                }
                
                ls.setRoomNo(oj.getString("roomNo"));
                ls.setWhereLost(oj.getString("location"));
                ls.setFounder(oj.getString("founder"));
                ls.setRemark(oj.getString("remark"));
                
                
                if(oj.getJSONObject("comp").getString("name") == null){
                    ls.setName(""); 
                }else{
                    ls.setName(oj.getJSONObject("comp").getString("name")); 
                }
                if(oj.getJSONObject("comp").getString("address") == null){
                    ls.setAddress("");
                }else{
                    ls.setAddress(oj.getJSONObject("comp").getString("address"));
                }
                if(oj.getJSONObject("comp").getString("city") == null){
                    ls.setCity("");
                }else{
                    ls.setCity(oj.getJSONObject("comp").getString("city"));
                }
                if(oj.getJSONObject("comp").getString("state") == null){
                    ls.setState("");
                }else{
                    ls.setState(oj.getJSONObject("comp").getString("state"));
                }
                if(oj.getJSONObject("comp").getString("zip") == null){
                    ls.setZip("");
                }else{
                    ls.setZip(oj.getJSONObject("comp").getString("zip"));
                }
                if(oj.getJSONObject("comp").getString("country") == null){
                    ls.setCountry("");
                }else{
                    ls.setCountry(oj.getJSONObject("comp").getString("country"));
                }
                if(oj.getJSONObject("comp").getString("phone") == null){
                    ls.setPhone("");
                }else{
                    ls.setPhone(oj.getJSONObject("comp").getString("phone"));
                }
                
                service.addAll(ls);
                
            }
           table.setItems(service);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewLostFound(ActionEvent e) throws IOException{
        NewLostFoundController controller = new NewLostFoundController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newLostFound.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditLostFound(ActionEvent e) throws IOException{
       
        LostFound item = table.getSelectionModel().getSelectedItem();
        NewLostFoundController controller = new NewLostFoundController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newLostFound.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteLostFound(){
        LostFound item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteLostFound(param);
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Lost Item Information Deleted", Pos.CENTER);
                                service.remove(item);
                                
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Lost Item Failed to Delete", Pos.CENTER);
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
    
    @FXML
    private void print(){
        Printer printer = Printer.getDefaultPrinter();
            //Stage dialogStage = new Stage(StageStyle.DECORATED);            
            PrinterJob job = PrinterJob.createPrinterJob(printer);
                if (job != null) {                    
                    //boolean showDialog = job.showPageSetupDialog(dialogStage);
                   //if (showDialog) {                        
                        table.setScaleX(0.40);//60
                        table.setScaleY(0.60);//60
                        table.setTranslateX(-120);//220
                        table.setTranslateY(-30);//70
                    boolean success = job.printPage(table);
                        if (success) {
                             job.endJob(); 
                        } 
                        table.setTranslateX(0);
                        table.setTranslateY(0);               
                        table.setScaleX(1.0);
                        table.setScaleY(1.0);                                              
                                    }    
                                
//    ContextMenu menu = new ContextMenu();
//    menu.getItems().addAll(cmItem1, cmItem2);
//    table.setContextMenu(menu);
       
    }
}
