/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools;

import hotels.Hotels;
import hotels.util.Navigator;
import hotels.util.State;
import hotels.util.Util;
import hotels.views.component.fxml.tools.model.Reminder;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.controlsfx.control.CheckComboBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class EditReminderController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox priority;
    @FXML
    private TextArea message;
    @FXML
    private CheckBox stopCheck;
    @FXML
    private DatePicker stopDate;
    @FXML
    private Button button;
    @FXML
    private ChoiceBox hour;
    @FXML
    private ChoiceBox min;
    @FXML
    private ChoiceBox am_pm;
    @FXML
    private TextField intervalF;
    @FXML
    private ChoiceBox  interval;
    @FXML
    private Pane userPane;

    
    private CheckComboBox users;
    private Hotels app;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public EditReminderController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    private Reminder data;

    public Reminder getData() {
        return data;
    }

    public void setData(Reminder data) {
        this.data = data;
    }
    
    
    private Navigator nav;
    private JSONObject response;
    private static ObservableList user = FXCollections.observableArrayList();
    private static ObservableList<String> userID = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        onLoad();
        popEdit();
    }    
    
    private void onLoad(){
        Util.formatDatePicker(stopDate);
        Util.formatDatePicker(startDate);
        stopDate.setDisable(true);
        
        users = new CheckComboBox();
        userPane.getChildren().add(users);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                fetchUser();
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        am_pm.getItems().addAll("AM","PM");
        priority.getItems().addAll("High","Medium","Low");
        priority.getSelectionModel().select(0);
        for (int i = 0; i < 60; i++) {
            String oo = i<10?"0"+i:""+i;
            min.getItems().add(oo);
            if(i<13)hour.getItems().add(""+i);
        }
        //min.getSelectionModel().select(0);
       Date time = Calendar.getInstance().getTime();
       LocalDateTime now = LocalDateTime.now();
       if(now.getHour()>12){
           hour.getSelectionModel().select(""+(now.getHour()-12));
           am_pm.getSelectionModel().select(1);
       }else{
           hour.getSelectionModel().select(""+(now.getHour()));
           am_pm.getSelectionModel().select(0);
       }
       min.getSelectionModel().select(now.getMonth());
       interval.getItems().addAll("days","weeks","months");
       interval.getSelectionModel().select(0);
       
       final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(
                                    startDate.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                            }
                            
                    }
                };
            }
        };
        stopDate.setDayCellFactory(dayCellFactory);
        
        stopCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(stopCheck.isSelected()){
                    stopDate.setDisable(false);
                }else{
                    stopDate.setDisable(true);
                }
            }
        });
        
        button.setOnAction((e) ->{
            editReminder();
        });
    }
    
    private void popEdit(){
        name.setText(data.getName());
        message.setText(data.getMessage());
        interval.getSelectionModel().select(data.getInterval());
        priority.getSelectionModel().select(data.getPriority());
        
    }
    
    private void fetchUser(){
        
        try {
            response = nav.fetchUsers();
            System.out.println("Printing Users : " + response);
            if(response != null){
                JSONArray array = response.getJSONArray("message");
                for(int i = 0; i < array.length(); i++){
                    JSONObject p = array.getJSONObject(i);
                    users.getItems().add(p.getJSONObject("name").getString("firstName")+" "+p.getJSONObject("name").getString("lastName"));
                    userID.add(p.getString("_id"));
                }
            }else{
                Util.notify(State.NOTIFY_ERROR, "Error Fetching System Users", Pos.CENTER);
            }
        } catch (JSONException ex) {
            Logger.getLogger(NewReminderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editReminder(){
        
        String datetime = startDate.getValue().toString()+"T"+
                convertTO24(hour.getSelectionModel().getSelectedItem()+":"+"00",//min.getSelectionModel().getSelectedItem().toString(), 
                am_pm.getSelectionModel().getSelectedItem().toString());
        
        String user ="";
        ObservableList c = users.getCheckModel().getCheckedItems();
        for (int i = 0; i < c.size(); i++) {
            if(users==null)user="";
            String id = userID.get(i);
            
            user += id;//"2382762uebv273672632"+","+ "4d33dse322322273832"+"
            user+=",";
        }
        System.out.println("Printing selected users : " + user);
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("id", data.getId()));
        param.add(new BasicNameValuePair("name", name.getText()));
        param.add(new BasicNameValuePair("startTime", datetime));
        param.add(new BasicNameValuePair("priority", priority.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("message", message.getText()));
        param.add(new BasicNameValuePair("interval", intervalF.getText() +","+interval.getSelectionModel().getSelectedItem().toString()));
        param.add(new BasicNameValuePair("receivers", user));
        //param.add(new BasicNameValuePair("remark", remark.getText()));
        
        if(stopDate.getEditor().getText().isEmpty()){
            param.add(new BasicNameValuePair("stopAfter", ""));
        }else{
            param.add(new BasicNameValuePair("stopAfter", stopDate.getValue().toString()));
        }
        
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                
                    response = nav.editReminder(param);
                    System.out.println("Printing Created Reminder : " +response);
                    if(response != null){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Reminder has been Updated", Pos.CENTER);
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Reminder Failed to Update", Pos.CENTER);
                            }
                        });
                    }   
                
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private String convertTO24(String ti, String am_PM){                  
           String result = LocalTime.parse(ti+" "+am_PM, DateTimeFormatter.ofPattern("h:mm a")).format(DateTimeFormatter.ofPattern("HH:mm"));
           return result;
    }
}
