package hotels.views.component.fxml.front.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.admin.NewRoomController;
import hotels.views.component.fxml.admin.NewRoomTypeController;
import hotels.views.component.fxml.front.model.FolioModel;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class Dashboard implements Initializable {

    @FXML
    private Label namr;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label source;
    @FXML
    private Label type;
    @FXML
    private Label arrivDate;
    @FXML
    private Label deptDate;
    @FXML
    private Label market;
    @FXML
    private Label totalCharge;
    @FXML
    private Label payment;
    @FXML
    private Label balance;
    @FXML
    private TextField search;
    @FXML
    private ListView<BorderPane> list;
    @FXML
    private FontAwesomeIcon occu;
    @FXML
    private FontAwesomeIcon outOf;
    @FXML
    private Label all;
    @FXML
    private FontAwesomeIcon dirty;
    @FXML
    private Label dueOut;
    @FXML
    private FontAwesomeIcon rese;
    @FXML
    private TableView<FolioModel> table;
    @FXML
    private TableColumn<FolioModel, String> folioCol;
    @FXML
    private TableColumn<FolioModel, String> guestNameCol;
    @FXML
    private TableColumn<FolioModel, Double> balanceCol;
    private ObservableList<BorderPane> guestData;
    private ObservableList<BorderPane> searchGuestData;
    
    private ObservableList<FolioModel> tableList;
    @FXML private GridPane detailGrid;
    
    private Hotels app;
    private Navigator2 nav;

    
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public Dashboard(Hotels app) {
        this.app=app;
        nav=new Navigator2(app.getMain());
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defaults();
        
    }    
    
//    private ObservableList<BorderPane> duplicate;
    private void defaults(){
        
        
        
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                if(newValue.isEmpty()){
//                duplicate = list.getItems();
                   list.setItems(guestData);
//                searchGuestData.clear();
return;
                }   JSONObject g,b;
                searchGuestData.clear();
                for (int i = 0; i < guestData.size(); i++) {
                    try {
                        BorderPane get = guestData.get(i);
                        g=(JSONObject)get.getProperties().get("guest");
                        JSONObject j = g.getJSONObject("name");
                        if(j.getString("firstName").toLowerCase().contains(newValue.toLowerCase())
                                || j.getString("lastName").toLowerCase().contains(newValue.toLowerCase())){
                            searchGuestData.add(get);
//                        break;
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }   list.setItems(searchGuestData);
            }
        });
        
        
        
        listDeafaults();
        tableDeafaults();
    }
    
    
    
    
    private void listDeafaults(){
    
        list.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends BorderPane> observable, BorderPane oldValue, BorderPane newValue) -> {
            JSONObject g=(JSONObject)newValue.getProperties().get("guest");
            JSONObject b=(JSONObject)newValue.getProperties().get("booking");
            if(g==null){
                detailGrid.setVisible(false);
                return;
            }
            try {
                namr.setText(g.getJSONObject("name").getString("firstName")+" "+g.getJSONObject("name").getString("lastName"));
                phone.setText(g.getString("phone"));
                email.setText(g.getString("email"));
                address.setText(g.getString("address"));
                address.setWrapText(true);
                type.setText("Individual");  // must patch for cooperate
                arrivDate.setText(Util.formatDate(b.getString("arrival"))); //arrival  //checkIn //checkOut
                deptDate.setText(b.getString("checkOut"));
                source.setText(b.getString("channel"));
                market.setText("");
                totalCharge.setText("");
                payment.setText("");
                balance.setText("");
                if(!detailGrid.isVisible()){detailGrid.setVisible(true);}
            } catch (JSONException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }            
        });
        
        searchGuestData = FXCollections.observableArrayList();
        
        
//        list.setCellFactory(new Callback<ListView<HBox>, ListCell<HBox>>() {
//            @Override
//            public ListCell<HBox> call(ListView<HBox> param) {
//                
//                return call(param);
//            }
//        });

        loadCustomers();
    }
    
    private void tableDeafaults(){
         folioCol.setCellValueFactory(new PropertyValueFactory<>("id"));
         guestNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
         balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        
        tableList= FXCollections.observableArrayList();
        table.setItems(tableList);
        
         table.setRowFactory(new Callback<TableView<FolioModel>, TableRow<FolioModel>>() {
            @Override
            public TableRow<FolioModel> call(TableView<FolioModel> param) {
                final TableRow<FolioModel> row = new TableRow<>();
                MenuItem transac = new MenuItem("View Foilio transactions", GlyphsDude.createIcon(FontAwesomeIcons.EYE)),
                        pay = new MenuItem("Make Payment", GlyphsDude.createIcon(FontAwesomeIcons.PAYPAL));
                ContextMenu con = new ContextMenu(pay, transac);
                transac.setOnAction((ActionEvent event) -> {

                });
                
                pay.setOnAction((ActionEvent event) -> {

                    try {
                        FolioModel item = row.getItem();
                        double amount = item.getBalance();
                        String desc = amount <0 ? "Paying up for bill" : "Funding Folio Whallet";
                        String payFor = desc;
                        String orderId = item.getId();
                        int dept = State.DEPT_FRONT;
                        String guestId =item.getGuestId();
                        String name = item.getName();
                        String phone = item.getPhone();
                        
                        app.getMain().showPayment(amount, desc, payFor, orderId, dept, guestId, name,phone, true);

                    } catch (IOException ex) {
                        Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
                        
                    }
                });
                return row;
                
            }
        });
         
         balanceCol.setCellFactory(column -> {
             return new TableCell<FolioModel,Double>(){
                 @Override
                 protected void updateItem(Double item, boolean empty) {
                     super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                
                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        setText(""+item); //Put the String data in the cell
//                        setStyle("-fx-background-color: yellow");

                        if(item<0){
                            setTextFill(Color.RED);
                        }else if(item >0){
                            setTextFill(Color.BLUE);
                        }else{
                            setTextFill(Color.BLACK);
                        }
                        
//                        Person auxPerson = getTableView().getItems().get(getIndex());

                    }

                }

            };
        });

         
         
         
    }
    
//    private String createGuest(String first, String last, String phone){
//        HashMap hashMap = new HashMap();
//        hasmap
//            hashMap.put("price", i);
//            hashMap.put("qty", 3);
//            hashMap.put("food", aa[i]);
//            new JSONObject(hashMap);
//            
//            
//    }
//    
    
     @FXML private void loadFolioTask(){
        
        Runnable task = new Runnable() {
            public void run() {
                folios();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void folios(){
        JSONObject foods = nav.fetchFolio(null, null);
        if(foods!=null){
            try {
                JSONArray a = foods.getJSONArray("message");
                tableList.clear();
                for (int i = 0; i < a.length(); i++) {
                    JSONObject o = a.getJSONObject(i);
                    
                    add1ToTable(o);
                    
                }
            } catch (JSONException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void add1ToTable(JSONObject o){
        System.out.println("THE OBJECT :::::"+o);
        try {
            FolioModel m = new FolioModel();
            
            m.setId(""+o.getInt("_id"));
            m.setBalance(o.getDouble("balance"));
            m.setGuestId(o.getString("guestId"));
            JSONObject oo = o.getJSONObject("guest");
            m.setName(oo.getJSONObject("name").getString("firstName")+" "+oo.getJSONObject("name").getString("lastName"));
            m.setPhone(oo.getString("phone"));
            tableList.add(m);
        } catch (JSONException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadCustomers() {
        Runnable task = () -> {
            dodo();
            loadFolioTask();
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void dodo(){

        guestData = FXCollections.observableArrayList();
        JSONObject fetchCustomers = nav.fetchCutomersDetails();
        if(fetchCustomers!=null){
            try {
                JSONArray jsonArray = fetchCustomers.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    guestData.add(creatGuestItem(jsonArray.getJSONObject(i)));

                }
            } catch (JSONException ex) {
                Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        list.setItems(guestData);
        
        list.setCellFactory(new Callback<ListView<BorderPane>, 
            ListCell<BorderPane>>() {
                @Override 
                public ListCell<BorderPane> call(ListView<BorderPane> list) {
                    
                    
////                final TableRow<FoodOderModel> row = new TableRow<>();
//                MenuItem cancle = new MenuItem("Cancel", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE)),
//                        done = new MenuItem("Report Food is Ready", GlyphsDude.createIcon(FontAwesomeIcons.CHECK)),
//                        approvePrint = new MenuItem("Approve and Print", GlyphsDude.createIcon(FontAwesomeIcons.PRINT));//        
//                ContextMenu con = new ContextMenu();
//                CheckMenuItem checkMenuItem = new CheckMenuItem("");
//                con.getItems().add(checkMenuItem);
//                    
                    return new GuestListCell();
                }
            }
        );
    }
    
    
    private BorderPane creatGuestItem(JSONObject j){
        BorderPane  h = new BorderPane();
        BorderPane bp;
        try {
            Text userIcon = GlyphsDude.createIcon(FontAwesomeIcons.USER,"1.8em");
            
            Text circle = GlyphsDude.createIcon(FontAwesomeIcons.CIRCLE,"2.5em");
//            circle.setStyle("-fx-background-color :#6382ff");
//            userIcon.setStyle("-fx-background-color :#ffffff");
            circle.setFill(Paint.valueOf("#6382ff"));
            userIcon.setFill(Paint.valueOf("#ffffff"));
            
            Label l = new Label();
            String phone = j.getString("phone");
            h.getProperties().put("phone", phone);
            
            
            l.setText(phone);
            JSONObject jn = j.getJSONObject("name");
            Text name = new Text(jn.getString("firstName")+" "+jn.getString("lastName"));
            h.getProperties().put("name", name.getText());
            
            VBox v = new VBox(name,l);
            Text stat = GlyphsDude.createIcon(FontAwesomeIcons.CIRCLE,"1em");
            HBox right = new HBox(stat);
            
            right.setAlignment(Pos.CENTER_RIGHT);
            v.setAlignment(Pos.CENTER_LEFT);
            v.setPadding(new Insets(1, 2, 0, 4));
            h.setCenter(v);
            h.setRight(right);
            h.setLeft(new StackPane(circle,userIcon));
//            h.setAlignment(Pos.CENTER_LEFT);
//            h.setSpacing(10);
//            h = new BorderPane(v, null, right, null, new StackPane(circle,userIcon));
                        
//            h.getChildren().add(bp);

//            h.getChildren().addAll(new StackPane(circle,userIcon),v,right);
            //System.out.println(j.getJSONArray("bookings"));
            
            
            
            JSONArray jsonArray = j.getJSONArray("bookings");
            if(jsonArray.length()<1){return h;}
            JSONObject booking = jsonArray.getJSONObject(jsonArray.length()-1);
            booking.getString("status");
            h.getProperties().put("booking", booking);
            j.remove("bookings");
            h.getProperties().put("guest", j);
        } catch (JSONException ex) {
            Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }

    
        
    static class GuestListCell extends ListCell<BorderPane> {
        @Override
        public void updateItem(BorderPane item, boolean empty) {
            super.updateItem(item, empty);
            
            if (item != null) {
//                rect.setFill(Color.web(item));
                setGraphic(item);
            }
        }
    }
    
   
    //Testing
    
//    @FXML 
//    private void newRoomType(ActionEvent e) throws IOException{
//      
//        NewRoomTypeController controller = new NewRoomTypeController(this.getApp());
//        controller.setApp(getApp());
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoomType.fxml"));
//        loader.setController(controller);
//        Parent root = (Parent)loader.load();
//        Stage stage = new Stage(StageStyle.UNIFIED);
//        stage.setScene(new Scene(root));
//        
//        stage.showAndWait();
//    }
//    
//    @FXML 
//    private void newRoom(ActionEvent e) throws IOException{
//      
//        NewRoomController controller = new NewRoomController(this.getApp());
//        controller.setApp(getApp());
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/newRoom.fxml"));
//        loader.setController(controller);
//        Parent root = (Parent)loader.load();
//        Stage stage = new Stage(StageStyle.UNIFIED);
//        stage.setScene(new Scene(root));
//        
//        stage.showAndWait();
//    }
}
