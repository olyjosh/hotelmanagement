package hotels.views.component.fxml.admin.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.admin.model.Floor;
import hotels.views.component.fxml.admin.model.RoomType;
import hotels.views.component.fxml.tools.LostFoundController;
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
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class FloorListController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private TableView<Floor> table;
    @FXML
    private TableColumn alias;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn desc;

    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public FloorListController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private Navigator nav;
    private JSONObject response;
    private static JSONObject room;
    private static JSONArray roomArray;
    private Floor ls;
    private ObservableList<Floor> service = FXCollections.observableArrayList();

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
            ObservableList<Floor> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Floor, ?>> cols = table.getColumns();
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
   
        
        
        table.setRowFactory(new Callback<TableView<Floor>, TableRow<Floor>>() {
            @Override
            public TableRow<Floor> call(TableView<Floor> param) {
                final TableRow<Floor> row = new TableRow<>();
                MenuItem edit = new MenuItem("Edit", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL)),
                        delete = new MenuItem("Delete", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE));
                ContextMenu con = new ContextMenu(edit,delete);
                edit.setOnAction((ActionEvent event) -> {
                    try {
                        Floor item = row.getItem();
                        showEditFloor(item);
                    } catch (IOException ex) {
                        Logger.getLogger(FloorListController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                delete.setOnAction((ActionEvent event) -> {
                    Floor item = row.getItem();
                    deleteFloor(item);
                });

//                row.setStyle("-fx-background-color :#6382ff");
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(con)
                );

                row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
//                        Floor item = row.getItem();
//                        String s ="Child Rate: "+item.getChildRate()+
//                                "Base Child: "+item.getBc()+
//                                "Over Book: "+item.getOverBooking();
//                        Tooltip.install(row, new Tooltip(s));
                    }
                });
                return row;
                
            }
        });
         
    }
    
    private void onLoad(){
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    
                    room = nav.fetchFloor();
                    roomArray = room.getJSONArray("message");
                    System.out.println(roomArray);
                    getRoomTypeList();
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
            desc.setCellValueFactory(new PropertyValueFactory<>("desc"));

//            table.getColumns().setAll(alias, name);
     
    }

    private void getRoomTypeList(){
        try {
            if(roomArray!=null)service.clear();
            for(int i = 0; i < roomArray.length(); i++){
                ls = new Floor();
                JSONObject oj = roomArray.getJSONObject(i);
                
                ls.setId(oj.getString("_id"));
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
    private void showNewFloor(ActionEvent e) throws IOException{
        NewFloorController controller = new NewFloorController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newFloor.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    private void showEditFloor(Floor item) throws IOException{
       
        
        NewFloorController controller = new NewFloorController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newFloor.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    

    private void deleteFloor(Floor item ){
        if(!app.getMain().
                showComfirmation("Are you sure you want to delete this record?"))
            return;
            
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteRoomType(param);
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
    
    
    @FXML private void refresh(ActionEvent e){
        onLoad();
    }

    
    
}
