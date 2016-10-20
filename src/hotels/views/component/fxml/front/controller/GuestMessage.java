package hotels.views.component.fxml.front.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.Storage;
import hotels.views.component.chatBubble.BubbleSpec;
import hotels.views.component.chatBubble.BubbledLabel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class GuestMessage implements Initializable {

    @FXML
    private ScrollPane sc;
    @FXML
    private VBox msg_pane;
    @FXML
    private TextArea txt_field;
    @FXML
    private Button send_btn;
    private HBox msgBox;

    
    @FXML 
    private ListView list;
    
    private Navigator2 nav;
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public GuestMessage(Hotels app) {
        this.app=app;
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
        
    }    
    
    private void defaults(){
        Insets in = new Insets(20,30,10,20);
        msg_pane.setSpacing(10);
        msg_pane.setPadding(in);
        msg_pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sc.setVvalue(sc.getVmax()); 
            }
        });
        sc.setFitToHeight(true);
        sc.setFitToWidth(true);
        
        nav = new Navigator2(getApp().getMain());
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                ;
//            }
//        });
        tasking();

        //dodo();
        addListAction();
    }
    
        
    private void tasking() {
        Task task = new Task<Object>(){
            @Override
            protected Object call() throws Exception {
                dodo();
                return null;
            }
        
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
    
    
    @FXML 
    private void postMessage(){// sender 1 = staff, sender 0 = guest
        
        String msg = "efjshdksa";
        
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_LEFT_CENTER);
        bl2.relocate(310, 100);
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msgBox.getChildren().add(bl2);
        msg_pane.getChildren().add(msgBox);
        
        //===================================OJORO DOWN HERE================
        
        
    }
    
    @FXML private void sendMessage(){
        
        String msg = txt_field.getText();
        txt_field.clear();
        if(msg.isEmpty()){
            return;
        }
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_LEFT_CENTER);
        bl2.relocate(310, 100);
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msgBox.getChildren().add(bl2);
        Tooltip.install(msgBox, new Tooltip("Pending "));
        msg_pane.getChildren().add(msgBox);
        JSONObject sendMessage = nav.sendMessage(phone, Storage.getId() , msg);
        
        try {
            if (sendMessage != null) {
                String string = sendMessage.getJSONObject("message").getString("createdAt");
                Tooltip.install(msgBox, new Tooltip("Sent\n"+string));
            } else {
                Tooltip.install(msgBox, new Tooltip("Not Sent"));
                Text retry = GlyphsDude.createIcon(FontAwesomeIcons.REFRESH, "o.3");
                retry.addEventHandler(EventType.ROOT, (Event event) -> {
                    
                });
                msgBox.getChildren().add(retry);
            }
        } catch (JSONException ex) {
            Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void myMessageBubble(String text){
        String msg = text;
        
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_LEFT_CENTER);
        bl2.relocate(310, 100);
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msgBox.getChildren().add(bl2);
        Tooltip.install(msgBox, new Tooltip("sent \n 2016/12/12"));
        msg_pane.getChildren().add(msgBox);
    }
    
    private void guestMessageBubble(String text){
        
        String msg = text;
        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_RIGHT_CENTER);
        bl2.relocate(310, 100);
        bl2.setStyle("-fx-text-fill: white");
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,
                        null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgBox.getChildren().add(bl2);
        msg_pane.getChildren().add(msgBox);
    }

    private void addListAction(){
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                msg_pane.getChildren().clear();

                listTask(newValue);

            }
        });
    }
    
    String phone;
    
    private void listTask(Object newValue){
//        Task task = new Task<Object>(){
//            @Override
//            protected Object call() throws Exception {
                        HBox h = (HBox) newValue;
                        phone = (String) h.getProperties().get("phone");
                        JSONObject fetchMessage = nav.fetchMessage(phone);
                        if (fetchMessage != null) {
                            //fetchMessage.get("status")
                            h = (HBox) list.getSelectionModel().getSelectedItem();
                            if (phone.equals((String) h.getProperties().get("phone"))) {
                                try {
                                    JSONArray jsonArray = fetchMessage.getJSONArray("message");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject j = jsonArray.getJSONObject(i);
                                        String msg = j.getString("message");
                                        if (j.getString("to").equals(phone)) {
                                            //my meesage
                                            myMessageBubble(msg);
                                        } else {
                                            guestMessageBubble(msg);
                                            
                                        }
                                    }
                                } catch (JSONException ex) {
                                    Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
//                return null;
//            }
//        
//        };
//        Thread th = new Thread(task);
//        th.setDaemon(true);
//        th.start();
    }
    
    private ObservableList<HBox> guestData;
    
    private void dodo(){
    
//        de.jensd.fx.glyphs.GlyphsDude d = GlyphsDude
        
        guestData = FXCollections.observableArrayList();
        JSONObject fetchCustomers = nav.fetchCustomers();
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
        
        list.setCellFactory(new Callback<ListView<HBox>, 
            ListCell<HBox>>() {
                @Override 
                public ListCell<HBox> call(ListView<HBox> list) {
                    return new GuestListCell();
                }
            }
        );
    }
    
    
    private HBox creatGuestItem(JSONObject j){
        HBox  h = new HBox();
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
            j= j.getJSONObject("name");
            Text name = new Text(j.getString("firstName")+" "+j.getString("lastName"));
            
            VBox v = new VBox(name,l);
            
            v.setAlignment(Pos.CENTER_LEFT);
            h.setAlignment(Pos.CENTER_LEFT);
            h.setSpacing(10);
            h.getChildren().addAll(new StackPane(circle,userIcon),v);
            
        } catch (JSONException ex) {
            Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;
    }

        
    static class GuestListCell extends ListCell<HBox> {
        @Override
        public void updateItem(HBox item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
//                rect.setFill(Color.web(item));
                setGraphic(item);
            }
        }
    }
   
    
}