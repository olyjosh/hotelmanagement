package hotels.views.component.fxml.housekeep.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.Util;
import hotels.views.component.chatBubble.BubbleSpec;
import hotels.views.component.chatBubble.BubbledLabel;
import hotels.views.component.fxml.front.controller.GuestMessage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
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
 * @author mac
 */
public class MaidManagement implements Initializable {

    @FXML private ListView<HBox> maidList;
    @FXML private TableView<ScheduleModel> recentMaidTaskTable;
    @FXML private TableColumn<ScheduleModel, String> suite, time;
    @FXML private TableColumn<ScheduleModel, Label> status;
    @FXML private Label firstName, lastName, email,phone, sex;
    @FXML private TextArea comment;
    @FXML private VBox commentPane;
    @FXML private Button commentButton;
    @FXML private Text error;
    @FXML private ScrollPane sc;
    
    private ObservableList<ScheduleModel> list;
    private HBox msgBox;    
    private ObservableList<HBox> maid;
        
    private Hotels app;
    private Navigator2 nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public MaidManagement(Hotels app) {
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
        taskIt();
    }    

    private void defaults(){
        
        suite.setCellValueFactory(new PropertyValueFactory<>("suiteC"));
        time.setCellValueFactory(new PropertyValueFactory<>("dateC"));
        if(list==null){
            list = FXCollections.observableArrayList();
            recentMaidTaskTable.setItems(list);
        }
        
        maidList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends HBox> observable, HBox oldValue, HBox newValue) -> {
            HBox sel = newValue;
            ObservableMap<Object, Object> properties = sel.getProperties();
            this.phone.setText(properties.get("phone").toString());
            this.email.setText(properties.get("email").toString());
            this.sex.setText(properties.get("sex").toString().startsWith("M")?"MALE":"FEMALE");
            VBox v = (VBox)sel.getChildren().get(1);
            String name = ((Text)v.getChildren().get(0)).getText();
            firstName.setText(name.split(" ")[0]);
            lastName.setText(name.split(" ")[1]);
            String id = properties.get("id").toString();
            fetchCommentTask(id);
            fetchMaidTask(id);
            commentPane.getChildren().clear();
            
        });
        
        comment.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty())error.setVisible(true);
                else error.setVisible(false);
            }
        });
        
        Insets in = new Insets(20,30,10,20);
        commentPane.setSpacing(10);
        commentPane.setPadding(in);
        commentPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sc.setVvalue(sc.getVmax()); 
            }
        });
        sc.setFitToHeight(true);
        sc.setFitToWidth(true);
    }

    private void fetchCommentTask(String id){
        Runnable task = new Runnable() {
                public void run() {
                    HBox sel = maidList.getSelectionModel().getSelectedItem();
                    String id = sel.getProperties().get("id").toString();
                    JSONObject com = nav.fetchStaffCommnet(id);
                    if (com != null) {
                        try {
                            JSONArray ja = com.getJSONArray("message");
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject j = ja.getJSONObject(i);
                                maidList.getSelectionModel().getSelectedItem();
                                String ids = sel.getProperties().get("id").toString();
                                if (ids.equals(id)) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                myMessageBubble(j.getString("comment"));
                                            } catch (JSONException ex) {
                                                Logger.getLogger(MaidManagement.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    });
                                }

                            }
                            
                        } catch (JSONException ex) {
                            Logger.getLogger(MaidManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
            };
            // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
    }
    
    private void fetchMaidTask(String id) {
        System.out.println(id);
        Runnable task = new Runnable() {
            public void run() {
                JSONObject maidTasks = nav.maidTasks(id);
                System.out.println(maidTasks);
                if (maidTasks != null) {
                    try {
                        JSONArray ja = maidTasks.getJSONArray("message");
                        list.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            add1ToTable(ja.getJSONObject(i));
                        }
                    } catch (JSONException ex) {
                        Logger.getLogger(MaidManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
     
    }
    
    private void add1ToTable(JSONObject o) throws JSONException{
        System.out.println(o);
        ScheduleModel tm = new ScheduleModel();
        tm.setDateC(o.getString("date"));
        tm.setDescC(o.getString("desc"));
        tm.setIntC(""+o.getInt("interval"));
        tm.setRemC(""+o.getInt("reminder"));
        tm.setEndDateC(o.getString("endDate"));       
        JSONObject oo = o.getJSONObject("room");
        tm.setSuiteC(oo.getString("alias"));
        list.add(tm);
    }
    
    
    private void myMessageBubble(String text) {
        String msg = text;

        BubbledLabel bl2 = new BubbledLabel(BubbleSpec.FACE_LEFT_CENTER);
        bl2.relocate(310, 100);
        bl2.setText(msg);
        bl2.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK,
                null, null)));
        msgBox = new HBox();
        msgBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        msgBox.getChildren().add(bl2);
//        Tooltip.install(msgBox, new Tooltip("sent \n 2016/12/12"));
        commentPane.getChildren().add(msgBox);
    }
    
    @FXML private void postComent(){
        commentButton.setText("Posting...");
        String text = comment.getText();
        if (!text.isEmpty()) {
            Runnable task = new Runnable() {
                public void run() {
                    HBox sel = maidList.getSelectionModel().getSelectedItem();
                    String id = sel.getProperties().get("id").toString();
                    JSONObject po = nav.postStaffCommnet(id, text);
                    if(po!=null){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject msg = po.getJSONObject("message");
                                if (msg.getString("staff").equals(id)) {
                                    myMessageBubble(msg.getString("comment"));
                                }
                                commentButton.setText("Add Comment");
                                comment.clear();
                                error.setVisible(false);
                                Util.notify("Comment posted", "The comment has been posted!", Pos.CENTER);

                            } catch (JSONException ex) {
                                Logger.getLogger(MaidManagement.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    }else{
                        commentButton.setText("Add Comment");
                    }
                }
            };
            // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        } else {
            //Error reporting 
            error.setVisible(true);
        }
    }
    
    private void taskIt(){
        Runnable task = new Runnable() {
            public void run() {
                dodo();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void dodo(){
            
        maid = FXCollections.observableArrayList();
        JSONObject fetchStaff = nav.fetchStaffAll();
        if(fetchStaff!=null){
            try {
                JSONArray jsonArray = fetchStaff.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    maid.add(creatStaffItem(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException ex) {
                Logger.getLogger(GuestMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        maidList.setItems(maid);
        
        maidList.setCellFactory(new Callback<ListView<HBox>, 
            ListCell<HBox>>() {
                @Override 
                public ListCell<HBox> call(ListView<HBox> list) {
                    return new GuestListCell();
                }
            }
        );
    }
    
    
    private HBox creatStaffItem(JSONObject j){
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
            h.getProperties().put("id", j.getString("_id"));
            h.getProperties().put("email", j.getString("email"));
            h.getProperties().put("sex", j.getString("sex"));
            
            
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
                setGraphic(item);
            }
        }
    }
    
}
