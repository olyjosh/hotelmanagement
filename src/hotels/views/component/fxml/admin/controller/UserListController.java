package hotels.views.component.fxml.admin.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator;
import hotels.views.component.fxml.admin.model.UserModel;
import hotels.views.component.fxml.front.controller.Dashboard;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author olyjosh
 */
public class UserListController implements Initializable {

    @FXML private TableView table;  
    @FXML private TableColumn usernameCol,nameCol,staffIdCol, roleCol, deptCol;
    private ObservableList<UserModel> tableList;
    
    
    private Hotels app;
    private Navigator nav;
    
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
        tableDeafaults();
        loadUsersTask();
    }    

    private void tableDeafaults() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        staffIdCol.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("priviledge"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        tableList = FXCollections.observableArrayList();
        table.setItems(tableList);

        table.setRowFactory(new Callback<TableView<UserModel>, TableRow<UserModel>>() {
            @Override
            public TableRow<UserModel> call(TableView<UserModel> param) {
                final TableRow<UserModel> row = new TableRow<>();
                MenuItem view = new MenuItem("Detail", GlyphsDude.createIcon(FontAwesomeIcons.EYE)),
                        message = new MenuItem("Message", GlyphsDude.createIcon(FontAwesomeIcons.PAYPAL));
                ContextMenu con = new ContextMenu(view);
                view.setOnAction((ActionEvent event) -> {
                    UserModel item = row.getItem();
                });

//                message.setOnAction((ActionEvent event) -> {
//
//                    try {
//                        FolioModel item = row.getItem();
//                        double amount = item.getBalance();
//                        String desc = amount <0 ? "Paying up for bill" : "Funding Folio Whallet";
//                        String payFor = desc;
//                        String orderId = item.getId();
//                        int dept = State.DEPT_FRONT;
//                        String guestId =item.getGuestId();
//                        String name = item.getName();
//                        String phone = item.getPhone();
//                        
//                        app.getMain().showPayment(amount, desc, payFor, orderId, dept, guestId, name,phone, true);
//
//                    } catch (IOException ex) {
//                        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                });
//                row.setStyle("-fx-background-color :#6382ff");
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(con)
                );

                row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                    }
                });
                return row;
            }
        });

//         balanceCol.setCellFactory(column -> {
//             return new TableCell<FolioModel,Double>(){
//                 @Override
//                 protected void updateItem(Double item, boolean empty) {
//                     super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
//                
//                    if (item == null || empty) { //If the cell is empty
//                        setText(null);
//                        setStyle("");
//                    } else { //If the cell is not empty
//
//                        setText(""+item); //Put the String data in the cell
////                        setStyle("-fx-background-color: yellow");
//
//                        if(item<0){
//                            setTextFill(Color.RED);
//                        }else if(item >0){
//                            setTextFill(Color.BLUE);
//                        }else{
//                            setTextFill(Color.BLACK);
//                        }
//                        
////                        Person auxPerson = getTableView().getItems().get(getIndex());
//
//                    }
//
//                }
//
//            };
//        });
//
//         
    }

    
    
        
    private void loadUsersTask(){
        
        Runnable task = new Runnable() {
            public void run() {
                users();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    
    
    private void users(){
        JSONObject foods =nav.fetchUsers();
        if(foods!=null){
            try {
                JSONArray a = foods.getJSONArray("message");
                tableList.clear();
                for (int i = 0; i < a.length(); i++) {
                    JSONObject o = a.getJSONObject(i);
                    
                    add1ToTable(o);
                    
                }
//                if(folioMp!=null)folioMp.setVisible(false);
            } catch (JSONException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void add1ToTable(JSONObject o){
        try {
            UserModel m = new UserModel();
            
            m.setId((String) o.get("_id"));
            JSONObject name = o.getJSONObject("name");
            m.setFirstName(name.getString("firstName")+" "+name.getString("lastName"));
            m.setLastName(name.getString("lastName"));
            m.setUsername(name.getString("username"));
            m.setCountry(o.getString("country"));
            m.setDob(o.getString("dob"));
            m.setEmail(o.getString("email"));
            m.setIsStaff(o.getJSONObject("staff").getBoolean("isStaff"));
            m.setPhone(o.getString("phone"));
            m.setPriviledge(Integer.valueOf((String)o.getJSONObject("staff").get("privilege")));
            m.setSex(o.getString("sex"));
            m.setStaffId(o.getInt("staffId"));
            
            
//            m.set


            
//            m.setBalance(o.getDouble("balance"));
//            m.setGuestId(o.getString("guestId"));
//            JSONObject oo = o.getJSONObject("guest");
//            m.setName(oo.getJSONObject("name").getString("firstName")+" "+oo.getJSONObject("name").getString("lastName"));
//            m.setPhone(oo.getString("phone"));
            
            tableList.add(m);
        } catch (JSONException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @FXML 
    private void showNewUser(ActionEvent e) throws IOException{
        NewUserController controller = new NewUserController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newUser.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    
    
}
