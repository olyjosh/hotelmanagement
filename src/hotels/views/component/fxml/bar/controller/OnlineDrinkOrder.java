package hotels.views.component.fxml.bar.controller;

import hotels.views.component.fxml.bar.model.DrinkOderModel;
import hotels.views.component.fxml.restaurant.controller.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.State;
import hotels.util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.controlsfx.control.MaskerPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public final class OnlineDrinkOrder implements Initializable {

    @FXML private GridPane foodlist;
    @FXML private TableView<DrinkOderModel> table;
    @FXML private TableColumn<DrinkOderModel , String> nameCol,orderIdCol,amountCol,paymentCol,statusCol;
    @FXML private Label name;
    @FXML private Button approve, cancel;
    
    private ObservableList<DrinkOderModel> list;
    private final String PENDING = "PENDING";
    private final String PREPARING = "PREPARING";
    private final String CANCELED ="CANCELED";
    private final String DONE ="DONE";
    
    private Hotels app;
    private final Navigator2 nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public OnlineDrinkOrder(Hotels app) {
        this.app =app;
        nav = new Navigator2(getApp().getMain());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
        loadOrders();
    }    
    
    
    private void defaults(){

        list= FXCollections.observableArrayList();
        table.setItems(list);
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        
        table.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends DrinkOderModel> observable, DrinkOderModel oldValue, DrinkOderModel newValue) -> {
            if(newValue==null)return;
            name.setText(newValue.getGuestName());
            JSONArray arr = newValue.getOrdersArray();
            Insets insets = new Insets(2, 3, 2, 3);
            for (int i = 0; i < arr.length(); i++) {
                try {
                    JSONObject o = arr.getJSONObject(i);
                    Label l = new Label(o.getJSONObject("food").getString("name")),
                            amtL =new Label(""+(o.getDouble("price")*o.getInt("qty"))),
                            qtyL = new Label(""+o.getInt("qty")),
                            priceL = new Label(""+(o.getDouble("price")));
                    
                    amtL.setPadding(insets);
                    qtyL.setPadding(insets);
                    l.setPadding(insets);
                    priceL.setPadding(insets);
                    
                    double size = l.getFont().getSize();
                    Font font = new Font(size-2);
                    amtL.setFont(font);
                    qtyL.setFont(font);
                    l.setFont(font);
                    priceL.setFont(font);
                    l.setWrapText(true);
                    
                    foodlist.add(l, 0, i+1);
                    foodlist.add(priceL, 1, i+1);
                    foodlist.add(qtyL, 2, i+1);
                    foodlist.add(amtL, 3, i+1);
                } catch (JSONException ex) {
                    Logger.getLogger(OnlineDrinkOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            componentStates(newValue.getStatus());
        });
        
        
        table.setRowFactory(new Callback<TableView<DrinkOderModel>, TableRow<DrinkOderModel>>() {
            @Override
            public TableRow<DrinkOderModel> call(TableView<DrinkOderModel> param) {
                final TableRow<DrinkOderModel> row = new TableRow<>();
                MenuItem cancle = new MenuItem("Cancel", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE)),
                        done = new MenuItem("Report Food is Ready", GlyphsDude.createIcon(FontAwesomeIcons.CHECK)),
                        approvePrint = new MenuItem("Approve and Print", GlyphsDude.createIcon(FontAwesomeIcons.PRINT));//        
                ContextMenu con = new ContextMenu();
                CheckMenuItem checkMenuItem = new CheckMenuItem("");
                con.getItems().add(checkMenuItem);
       
                con.showingProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

                    if (newValue) {
                        DrinkOderModel item = row.getItem();
                        if (!item.getStatus().equalsIgnoreCase(CANCELED)) {
                            con.getItems().add(approvePrint);
                            if (!item.getStatus().equalsIgnoreCase(PENDING)) {
                                approvePrint.setText("PRINT");
                            }
                        }
                        if (!item.getStatus().equalsIgnoreCase(CANCELED)) {
                            con.getItems().add(done);
                        }
                        if (item.getStatus().equalsIgnoreCase(PENDING)) {
                            con.getItems().add(cancle);
                        }
                        con.getItems().remove(checkMenuItem);
                    }
                });

   
                approvePrint.setOnAction((ActionEvent)->{
                    approveAndPrint();
                });

                cancle.setOnAction((ActionEvent event) -> {
                    cancelOrder();
                });
                
                done.setOnAction((ActionEvent event) -> {
                    setDoneOrder();
                });
                
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(con)
                );

                
                
//                row.setOnMouseEntered(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent mouseEvent) {
//                        DrinkOderModel item = row.getItem();
//                        if(item !=null){
//                            if(Double.parseDouble(""+item.getPrice())==0)
//                            row.setTooltip(new Tooltip("Needs Admin's price approval"));
//                        }
//                    }
//                });
                return row;
                
            }
        });
        
    }

    @FXML
    private void loadOrders() {
        Runnable task = () -> {
            orders();
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void orders(){
        JSONObject foods = nav.fetchDrinksOrders(State.channel_Mobile, null, null);
        if(foods!=null){
            try {
                JSONArray a = foods.getJSONArray("message");
                list.clear();
                for (int i = 0; i < a.length(); i++) {
                    JSONObject o = a.getJSONObject(i);
                    add1ToTable(o);
                }
            } catch (JSONException ex) {
                Logger.getLogger(NewDrink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void add1ToTable(JSONObject o){
        try {
            DrinkOderModel m = new DrinkOderModel();
            m.setId(o.getString("_id"));
            m.setAmount(""+o.getDouble("amount"));
            JSONObject ob = o.getJSONObject("guest");
            m.setGuestName(ob.getString("firstName")+" "+ob.getString("lastName"));
            m.setPhone(ob.getString("phone"));
            m.setIsPaid(o.getBoolean("payment")?"Paid":"No payment");
            m.setOrderId(""+o.getInt("orderId"));
            System.out.println(o.getInt("orderId"));
            m.setArray(o.getJSONArray("orders"));
            String status = "";
            switch(o.getInt("status")){
                case State.ORDER_PENDING : status = PENDING;break;
                case State.ORDER_PREPARING : status = PREPARING;break;
                case State.ORDER_CANCEL : status = CANCELED;break;
                case State.ORDER_DONE : status = DONE;break;
            }
            m.setStatus(status);
//            m.setRoom(x);
            list.add(m);
        } catch (JSONException ex) {
            Logger.getLogger(NewDrink.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML private void approveAndPrint() {
        DrinkOderModel item = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();

        if (item.getStatus().equalsIgnoreCase(PENDING)) {
            Runnable task = () -> {
                try {
                    JSONObject o = nav.approveDrinksOrders(item.getId());
                    if(o.getInt("status")==1){
                        
                        item.setStatus(PREPARING);
                        list.set(index, item);
                        print();
                    }else{
                        
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(OnlineDrinkOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();

        } else {
            print();
        }
    }
    
    private void print(){
        
    }
    
    
    private int counter;
    @FXML private void cancelOrder() {
               String[] a = null;
        try {
            a = app.getMain().showAdminComfirmation();
            
            
        } catch (IOException ex) {
            Logger.getLogger(OnlineDrinkOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(a == null )return;
        if(a[0]==null || a[1]==null)return;
        
        final MaskerPane mp = Util.mp();
        mp.setText("please wait...");
        mp.setProgress(-1);
        app.getMain().restaurantContentStack.getChildren().add(mp);
        mp.setVisible(true);

        boolean admin = nav.verifyAdmin(a);
        if (admin) {
            DrinkOderModel item = table.getSelectionModel().getSelectedItem();
            int index = table.getSelectionModel().getSelectedIndex();
            mp.setText("Canceling order...");
            Runnable task = () -> {
                try {
                    JSONObject o = nav.cancelDrinksOrders(item.getId());
                    if (o.getInt("status") == 1) {
                        item.setStatus(CANCELED);
                        list.set(index, item);
                        print();
                        mp.setVisible(false);
                        app.getMain().restaurantContentStack.getChildren().remove(mp);
                    } else {
                        mp.setVisible(false);
                        app.getMain().restaurantContentStack.getChildren().remove(mp);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(OnlineDrinkOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();

        } else {
            mp.setVisible(false);
            app.getMain().restaurantContentStack.getChildren().remove(mp);
//            mp = null;
            Util.notify("Invalid Authentication", "Admin username or password not valid", Pos.CENTER);
            counter++;
            if(counter<=3)cancelOrder();
        }
    }

    @FXML private void setDoneOrder(){
        

            DrinkOderModel item = table.getSelectionModel().getSelectedItem();
            final int index = table.getSelectionModel().getSelectedIndex();
            Runnable task = () -> {
                try {
                    JSONObject o = nav.doneDrinksOrders(item.getId());
                    if (o.getInt("status") == 1) {
                        item.setStatus(DONE);
                        list.set(index, item);
                        print();
                    } else {

                    }
                } catch (JSONException ex) {
                    Logger.getLogger(OnlineDrinkOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();

    }
     
    private void componentStates(String status){
        if(status.equalsIgnoreCase(PENDING)){
                cancel.setVisible(true);
                approve.setVisible(true);
                approve.setText("Approve and Print");
            }else if(status.equalsIgnoreCase(PREPARING)){
                cancel.setVisible(false);
                approve.setVisible(true);
                approve.setText("Re-Print");
            }else if(status.equalsIgnoreCase(CANCELED)){
                cancel.setVisible(false);
                approve.setVisible(false);
            }else if(status.equalsIgnoreCase(DONE)){
                cancel.setVisible(false);
                approve.setText("Re-Print");
                approve.setVisible(true);
            }
    }
    

    
}
