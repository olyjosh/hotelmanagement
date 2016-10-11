/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.views.component.fxml.tools.model.LostFound;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private static String selectedID;
       
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            // TODO
            lost = nav.fetchLostFound();
            System.out.println("printing lost Items : " + lost);
            lostArray = lost.getJSONArray("message");
            
            getLostList();
            
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
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            //Check whether item is selected and print value of selected item
            if (table.getSelectionModel().getSelectedItem() != null) {
                System.out.println("Printing Selected value ID : "+ newValue.getId());
                selectedID = newValue.getId();
            }
        });
    }    
    
    private void getLostList(){
        try {
            for(int i = 0; i < lostArray.length(); i++){
                ls = new LostFound();
                JSONObject oj = lostArray.getJSONObject(i);
                JSONObject oj2 = oj.getJSONObject("reso");
                System.out.println("printing lost Array : " + lostArray);
                
                ls.setId(oj.getString("_id"));
                ls.setDiscardBy(oj2.getString("discardBy"));
                ls.setDiscardDate(String.valueOf(oj2.get("discardDate")));
                ls.setEntryDate(oj.getString("onDate"));
                ls.setItemColour(oj.getString("color"));
                ls.setItemName(oj.getString("name"));
                ls.setReturnBy(oj2.getString("returnBy"));
                ls.setReturnDate(String.valueOf(oj2.get("returnDate")));
                ls.setRoomNo(oj.getString("roomNo"));
                ls.setWhereLost(oj.getString("location"));
                
                System.out.println("printing LostFound Object : " + ls);
                service.addAll(ls);
                
                System.out.println("printing service : " + service);
                table.setItems(service);
                ///controller.setService(service);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewLostFound(ActionEvent e) throws IOException{
        NewLostFoundController controller = new NewLostFoundController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newLostFound.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditLostFound(ActionEvent e) throws IOException{
       
        NewLostFoundController controller = new NewLostFoundController(this.getApp(), service, selectedID);
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newLostFound.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
}
