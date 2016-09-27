/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front;

import hotels.util.Navigator;
import hotels.util.State;
import hotels.views.component.fxml.front.model.Reserve;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class ReserveListController implements Initializable {

    
    private Navigator nav = new Navigator();
    private ObservableList<Reserve> reserv = FXCollections.observableArrayList();
    private ObservableList cons = FXCollections.observableArrayList();
    private JSONObject booking;
    private JSONArray bookArray;
    private Reserve rs;
    
    
    @FXML
    private TextField searchField;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView <Reserve>table;
    @FXML
    private TableColumn col1;
    @FXML
    private TableColumn col2;
    @FXML
    private TableColumn col3;
    @FXML
    private TableColumn col4;
    @FXML
    private TableColumn col5;
    @FXML
    private TableColumn col6;
    @FXML
    private TableColumn col7;
    @FXML
    private Button newBtn;
    @FXML
    private ComboBox statusCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            booking = nav.fetchBooking();
            bookArray = booking.getJSONArray("message");
            
            cons.add("book");
            cons.add("Reservations");
            statusCombo.setItems(cons);
            
            getReservation();
            table.setItems(reserv);

            col1.setCellValueFactory(new PropertyValueFactory<>("id"));
            col2.setCellValueFactory(new PropertyValueFactory<>("room"));
            col3.setCellValueFactory(new PropertyValueFactory<>("guestName"));
            col4.setCellValueFactory(new PropertyValueFactory<>("arrival"));
            col5.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
            col6.setCellValueFactory(new PropertyValueFactory<>("channel"));
            col7.setCellValueFactory(new PropertyValueFactory<>("status"));
            
            table.getColumns().setAll(col1, col2, col3, col4, col5, col6, col7);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        searchField.textProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(searchField.textProperty().get().isEmpty()) {
                table.setItems(reserv);
                return;
            }
            ObservableList<Reserve> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Reserve, ?>> cols = table.getColumns();
            for(int i=0; i<reserv.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(reserv.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(searchField.textProperty().get().toLowerCase())) {
                        tableItems.add(reserv.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        statusCombo.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {

        @Override
        public void invalidated(Observable observable) {
            if(statusCombo.getSelectionModel().selectedItemProperty().get().toString().isEmpty()) {
                table.setItems(reserv);
                return;
            }
            ObservableList<Reserve> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Reserve, ?>> cols = table.getColumns();
            for(int i=0; i<reserv.size(); i++) {

                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(reserv.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(statusCombo.getSelectionModel().selectedItemProperty().get().toString().toLowerCase())) {
                        tableItems.add(reserv.get(i));
                        break;
                    }                        
                }
            }
            table.setItems(tableItems);
        }
    });
        
        
    }    
    
    private void getReservation(){
        try {
            for(int i = 0; i < bookArray.length(); i++){
                rs = new Reserve();
                JSONObject oj = bookArray.getJSONObject(i);
                rs.setId(oj.getString("_id"));
                rs.setArrival(nav.stripDate(oj.getString("arrival")));
                rs.setCreatedAt(nav.stripDate(oj.getString("createdAt")));
                rs.setChannel(oj.getString("channel"));
                rs.setStatus(oj.getString("status"));
                
                JSONObject oj2 = oj.getJSONObject("room");
                rs.setRoom(oj2.getString("name"));
                                
                JSONObject oj3 = oj.getJSONObject("guest");
                rs.setGuestName(oj3.getString("firstName") + "  " + oj3.getString("lastName"));
             
                reserv.addAll(rs);
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void tableCellToolTip(){
            col2.setCellFactory(param -> {  
                TableCell<Reserve, String> cell = new TableCell<Reserve, String>() {  
                    @Override  
                    protected void updateItem(String item, boolean empty) {  
                        // calling super here is very important - don't  
                        // skip this!  
                        super.updateItem(item, empty);  
                        super.updateItem(item, empty);  
                        if (empty) {  
                            setGraphic(null);  
                            setTooltip(null);  
                        } else {  
                            //Label color = new Label(item.getColore());  
                            //color.setStyle("-fx-background-color:" + item.getColore() + "; -fx-padding:0 ");  
                            //setGraphic(color);  

                            if (getTableRow() != null) {  
                                Reserve rs = getTableView().getItems().get(getTableRow().getIndex());  
                                Tooltip tooltip = new Tooltip("xkggjgbjfbfjhbfhjasbf");  
                                tooltip.setWrapText(true);  
                                setTooltip(tooltip);  
                            }  
                        }  
                    }        
                };
                return null;
            });
    }
    
    @FXML 
    private void showCheckIn(ActionEvent e) throws IOException{
        //Login controller = new Login();
        //controller.setApp(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/newBooking.fxml"));
        //loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
   
}
