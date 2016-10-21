 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.front.controller.NewBookingController;
import hotels.views.component.fxml.front.model.Booking;
import hotels.views.component.fxml.front.model.Reserve;
import hotels.views.component.fxml.tools.HotelServiceListController;
import hotels.views.component.fxml.tools.model.HotelService;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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
public class ReserveListController implements Initializable {

     private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public ReserveListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
    }
    
    
    
    private Navigator nav;
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
        
            // TODO
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        booking = nav.fetchBooking();
                        if(booking != null){
                            bookArray = booking.getJSONArray("message");
                            getReservation();
                        }else{
                            Util.notify(State.NOTIFY_ERROR, "Error Fetching Reservations and Booking", Pos.CENTER);
                        }
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
            
            
            cons.add("Show All");
            cons.add(State.RM_BOOKED);
            cons.add(State.RM_RESERVED);
            statusCombo.setItems(cons);
            
            
            table.setItems(reserv);

            col1.setCellValueFactory(new PropertyValueFactory<>("id"));
            col2.setCellValueFactory(new PropertyValueFactory<>("room"));
            col3.setCellValueFactory(new PropertyValueFactory<>("guestName"));
            col4.setCellValueFactory(new PropertyValueFactory<>("arrival"));
            col5.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
            col6.setCellValueFactory(new PropertyValueFactory<>("channel"));
            col7.setCellValueFactory(new PropertyValueFactory<>("status"));
            
            table.getColumns().setAll(col1, col2, col3, col4, col5, col6, col7);
         
        
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
            if(statusCombo.getSelectionModel().getSelectedItem().toString().equals("Show All")){
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
        
        table.setRowFactory(new Callback<TableView<Reserve>, TableRow<Reserve>>() {
            @Override
            public TableRow<Reserve> call(TableView<Reserve> param) {
                final TableRow<Reserve> row = new TableRow<>();
               
                row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Reserve item = row.getItem();
                        if(item !=null){
                            row.setTooltip(new Tooltip("Guest Name : "+item.getGuestName() + "\n" 
                                    +"Room No : " + item.getRoom() + "\n" 
                                    +"Phone No : " + item.getPhone() + "\n" 
                                    +"Guest Status : " + item.getStatus()));
                        }
                    }
                });
                return row;
                
            }
        });
    }    
    
    private void getReservation(){
        try {
            for(int i = 0; i < bookArray.length(); i++){
                rs = new Reserve();
                JSONObject oj = bookArray.getJSONObject(i);
                rs.setId(oj.getString("_id"));
                rs.setArrival(Util.stripDate(oj.getString("arrival")));
                rs.setCreatedAt(Util.stripDate(oj.getString("createdAt")));
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
    
  
    
    @FXML 
    private void newBooking(ActionEvent e) throws IOException{
      
        NewBookingController controller = new NewBookingController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/newBooking.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void editBooking(ActionEvent e) throws IOException{
      
        NewBookingController controller = new NewBookingController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/newBooking.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML
    private void deleteBooking(){
        Reserve item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteHotelService(param);
                    
                    if(response != null && response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Reservation Information Deleted", Pos.CENTER);
                                reserv.remove(item);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Reservation Failed to Delete", Pos.CENTER);
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
