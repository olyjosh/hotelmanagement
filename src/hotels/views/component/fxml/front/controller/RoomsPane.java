
package hotels.views.component.fxml.front.controller;

import hotels.Hotels;
import hotels.util.Codes;
import hotels.util.Navigator;
import hotels.util.State;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class RoomsPane implements Initializable {

    @FXML private Label all,vacant,occupied,dirty,outOfOrder,reserved,dueOut;
    @FXML private TabPane floorTab;
    @FXML private FlowPane cardHolder;
    private ScrollPane cardScrollPane;
    
    Navigator nav;
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public RoomsPane(Hotels app) {
        this.app=app;
        nav = new Navigator(app.getMain());
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nav = new Navigator(getApp().getMain());

        
        floorTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                
                if(newValue.getContent()==null){
                    addCardHolder(newValue);
                }
            }
        });
        
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                retrieveFloors();
//            }
//        });
        startTask();
        
    }    
    
    
    private void fillTabs(JSONArray rooms){
        for (int i = 0; i < rooms.length(); i++) {
            
        }
    }
    
    private void addCardHolder(Tab t){
        cardHolder = new FlowPane();
        cardScrollPane = new ScrollPane(cardHolder);
        cardScrollPane.setFitToHeight(true);
        cardScrollPane.setFitToWidth(true);
        cardHolder.setMinWidth(960);
//        cardScrollPane.setMinSize(t.getTabPane().getMinWidth(), t.getTabPane().getMinWidth());
        cardHolder.setPadding(new Insets(15));
        cardHolder.setVgap(5);
        cardHolder.setHgap(5);
        t.setContent(cardScrollPane);
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fetchResource(cardHolder);
            }
        }); 
        
    }
    
    private void fetchResource (FlowPane f){
        JSONObject fetchRoom = nav.fetchRoom();
        if(fetchRoom!=null){
            System.out.println(fetchRoom);
            try {
                JSONArray jsonArray = fetchRoom.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
//                    addRoomItem(jsonArray.getJSONObject(i), f);
//                    jsonArray.getJSONObject(i);
                    f.getChildren().add(roomItem(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException ex) {
                Logger.getLogger(RoomsPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void addRoomItem(JSONObject x, FlowPane parent) {
        try {
            RoomItem cont = new RoomItem(x);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomItem.fxml"));
            //Parent root = (Parent)fxmlLoader.load();
            fxmlLoader.setController(cont);
            Object load = fxmlLoader.load();
            parent.getChildren().add(0,(VBox) load);
        } catch (IOException ex) {
            Logger.getLogger(RoomsPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void retrieveFloors() {
        
        JSONObject fetchFloors = nav.fetchFloor();
        if (fetchFloors != null) {
            try {
                JSONArray jsonArray = fetchFloors.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject j = jsonArray.getJSONObject(i);
                    if(j.has("alias")){
                        addFloors(j.getString("alias"),j.getString("_id"));
                    }
                    //addFloors(j.getString("alias"),j.getString("_id") );
                }
            } catch (JSONException ex) {
                Logger.getLogger(RoomsPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void startTask() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {
                retrieveFloors();
            }
        };

        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setDaemon(true);
        back.start();
    }	
    
    private void addFloors(String floor, String floorId){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Tab tab = new Tab(floor);
                tab.getProperties().put("id", floorId);
                floorTab.getTabs().add(tab);
            }
        });
    }
    
    private void addFloors(String floor, Node content){
        floorTab.getTabs().add(new Tab(floor, content));
    }
    

    
    private VBox roomItem(JSONObject j){
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(5));
        v.setMinSize(100, 100);
        
        try {
            
            Label roomOccupant = new Label(), roomNo=new Label(), roomType= new Label();
            roomNo.setText(j.getString("alias"));//guest
            roomType.setText(j.getJSONObject("roomType").getString("name"));//roomType
            roomOccupant.setText("Joshua Aroke");
//            roomOccupant.setText(j.getJSONObject("guest").getString("firstName")+ " " + j.getJSONObject("guest").getString("lastName"));
            v.getChildren().addAll(roomNo, roomType, roomOccupant);
            String string = j.getJSONObject("roomStatus").getString("bookedStatus");
//            switch (string) {
//                default:{
//                    v.setStyle("-fx-background-color :"+Codes.COLOR_ALL);
//                }break;
//                case State.RM_RESERVED: {
//                    v.setStyle("-fx-background-color :" + Codes.COLOR_RESERVED);
//                    roomNo.setStyle("-fx-text-fill : #ffffff;");
//                    roomType.setStyle("-fx-text-fill : #ffffff;");
//                    roomOccupant.setStyle("-fx-text-fill : #ffffff;");
//                }break;
//                case State.RM_BOOKED: {
//                    v.setStyle("-fx-background-color :" + Codes.COLOR_RESERVED);
//                    roomNo.setStyle("-fx-text-fill : #ffffff;");
//                    roomType.setStyle("-fx-text-fill : #ffffff;");
//                    roomOccupant.setStyle("-fx-text-fill : #ffffff;");
//                }break;
//                case State.RM_DUEOUT : {
//                    v.setStyle("-fx-background-color :" + Codes.COLOR_DUE_OUT);
//                    roomNo.setStyle("-fx-text-fill : #ffffff;");
//                    roomType.setStyle("-fx-text-fill : #ffffff;");
//                    roomOccupant.setStyle("-fx-text-fill : #ffffff;");
//                }break;
//            }
            
        } catch (JSONException ex) {
            Logger.getLogger(RoomsPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
    
    
}
