package hotels.views.component.fxml.housekeep.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.State;
import hotels.util.Storage;
import hotels.util.Util;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class ScheduleManagement implements Initializable {

   @FXML private DatePicker date, date1;
   @FXML private ChoiceBox hour, min, am_pm, room,interval;
   @FXML private TextField desc, reminder, intervalF;
   @FXML private GridPane grid;
   @FXML private StackPane createPane,tableStackPane;
   @FXML private TitledPane createTitledPane;
   @FXML private HBox roomHBox,maidsHBox;
   @FXML private Text error; 
   @FXML private TableColumn descC,suiteC,dateC,intC,remC;
   @FXML private TableView<ScheduleModel> table;
   @FXML private Button scheduleButton;
   @FXML private Hyperlink exitEdit;
   
   private ProgressIndicator pi,pi2;
   private CheckComboBox maids;
   private ObservableList<ScheduleModel> list;
   private SimpleBooleanProperty newOrEdit = new SimpleBooleanProperty(true);
   
    private Hotels app;
    private Navigator2 nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public ScheduleManagement(Hotels app) {
        this.app =app;
        nav = new Navigator2(getApp().getMain());
        //defaults();
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
         maids = new CheckComboBox();
         //maids.getItems().addAll("Please wait...","Please wait2...","Please wait3...","Please wait4...","Please wait5...","Please wait6...");
        maidsHBox.getChildren().add(maids);
        HBox.setHgrow(maids, Priority.ALWAYS);
        HBox.setHgrow(room, Priority.ALWAYS);
        error.setVisible(false);
        am_pm.getItems().addAll("AM","PM");
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
       
         intervalF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
             if(!newValue.matches("^(0?[0-9]?[0-9]|[1-2][0-9][0-9]|3[0-5][0-9]|36[0-6])$"))intervalF.setText("0");
         });
       
        reminder.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(!newValue.matches("^[0-5]?[0-9]$"))reminder.setText("0");
        });
       
//
       tables();
       newOrEdit.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
         if(!newValue)setEditMode();
         else setNewMode();
       });
       datePicker();
       
       
       preloadsTaskIt();
    }
    
    private void datePicker() {
        date.setValue(LocalDate.now());
        Util.formatDatePicker(date);
        Util.formatDatePicker(date1);
        date.getEditor().textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            
            if(date1.getValue()!=null){
                if(date1.getValue().isBefore(date.getValue()))date1.setValue(date.getValue().plusDays(1));
            }
        });
        
        final Callback<DatePicker, DateCell> callbk1
                = (final DatePicker datePicker) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                    
                };
  
            
        final Callback<DatePicker, DateCell> callbk2
                = (final DatePicker datePicker) -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(date.getValue().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }

                    }
                };
        date.setDayCellFactory(callbk1);
        date1.setDayCellFactory(callbk2);
    }   
    
    
    private void tables() {

        descC.setCellValueFactory(new PropertyValueFactory<>("descC"));
        suiteC.setCellValueFactory(new PropertyValueFactory<>("suiteC"));
        dateC.setCellValueFactory(new PropertyValueFactory<>("dateC"));
        intC.setCellValueFactory(new PropertyValueFactory<>("intC"));
        remC.setCellValueFactory(new PropertyValueFactory<>("remC"));
        if (list == null) {
            list = FXCollections.observableArrayList();
        }
        table.setItems(list);

        table.setRowFactory(new Callback<TableView<ScheduleModel>, TableRow<ScheduleModel>>() {
            @Override
            public TableRow<ScheduleModel> call(TableView<ScheduleModel> tableView) {
                final TableRow<ScheduleModel> row = new TableRow<>();
                row.setOnMouseEntered((MouseEvent event) -> {
                    ScheduleModel item = row.getItem();
                    if(item!=null){
                        String text = "";
                        System.out.println(item.getEndDateC());
                        if(!item.getEndDateC().isEmpty())text = "This ends on "+fromISO(item.getEndDateC())+"\n";
                            text+="To be done at "+item.getSuiteC()+"\n"
                            +"by : ";
                        row.setTooltip(new Tooltip(text));
                    }
                });
                MenuItem delete = new MenuItem("delete", GlyphsDude.createIcon(FontAwesomeIcons.REMOVE)),
                        edit = new MenuItem("edit", GlyphsDude.createIcon(FontAwesomeIcons.PENCIL)),
                        detail = new MenuItem("detail", GlyphsDude.createIcon(FontAwesomeIcons.DASHBOARD));
//        
                ContextMenu con = new ContextMenu(detail, edit, delete);
                delete.setOnAction((ActionEvent event) -> {
                    table.getItems().remove(row.getItem());
                });
                edit.setOnAction((ActionEvent event) -> {
                    newOrEdit.set(false);
                });
                detail.setOnAction((ActionEvent event) -> {
                });
                
                // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(con)
                );
                return row;
            }
        });
        
    }
    
    private void populatetable(){
        
       try {
           JSONObject fh = nav.fetchHouseKeepTasks();
           
           if (fh != null) {
               table.getItems().clear();
               JSONArray ja = fh.getJSONArray("message");
               int len = ja.length();
               if (len < 1) {
                   //Do set empty placeHolder
               } else {
                   
                   for (int i = 0; i < len; i++) {
                       JSONObject o = ja.getJSONObject(i);
                       add1ToTable(o);
                   }
               }
//               Anim.seqencetialEffect(table);
           }

       } catch (JSONException ex) {
           Logger.getLogger(ScheduleManagement.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    private void add1ToTable(JSONObject o) throws JSONException{
        ScheduleModel tm = new ScheduleModel();
        tm.setDateC(o.getString("date"));
        tm.setDescC(o.getString("desc"));
        tm.setIntC(""+o.getInt("interval"));
        tm.setRemC(""+o.getInt("reminder"));
        
        JSONObject oo = o.getJSONObject("room");
        tm.setSuiteC(oo.getString("alias"));
        list.add(tm);
    }
    
    @FXML private void refresh(){
        preloadsTaskIt();
    }
    
    private void preloadsTaskIt(){
        if(pi==null){
            pi=new ProgressIndicator();
            pi2=new ProgressIndicator();
            pi.setProgress(-1);
            pi2.setProgress(-1);
            
        }
        roomHBox.getChildren().add(pi);
        maidsHBox.getChildren().add(pi2);
        Runnable task = new Runnable() {
            public void run() {
                fetchStaffAndRoom();
                populatetable();
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
    
    private void fetchStaffAndRoom(){
       JSONObject fetchRoom = nav.fetchRoom();
//       JSONObject fetchStaff = nav.fetchStaff(State.USER_MAID);
              
       JSONObject fetchStaff = nav.fetchStaffAll();  // Change this when you fix privilege issue

       
       if(fetchRoom!=null){
           try {
               JSONArray ja = fetchRoom.getJSONArray("message");
               for (int i = 0; i < ja.length(); i++) {
                   JSONObject p = ja.getJSONObject(i);
                   room.getItems().add(new SimpleIdTitle(p.getString("_id"), p.getString("alias")));
               }
           } catch (JSONException ex) {
               Logger.getLogger(ScheduleManagement.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       if(fetchStaff!=null){
           try {
               JSONArray ja = fetchStaff.getJSONArray("message");
               for (int i = 0; i < ja.length(); i++) {
                   JSONObject p = ja.getJSONObject(i);
                   maids.getItems().add(new SimpleIdTitle(p.getString("_id"), p.getJSONObject("name").getString("firstName")+" "+p.getJSONObject("name").getString("lastName")));
               }
           } catch (JSONException ex) {
               Logger.getLogger(ScheduleManagement.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        Platform.runLater(new Runnable() {
           @Override
           public void run() {
               roomHBox.getChildren().remove(pi);
               maidsHBox.getChildren().remove(pi2);
               
           }
       });
    }
    
    @FXML private void shedule(){
        if(date.getValue()==null || date1.getValue()==null){
            error.setVisible(true);
            return;
        }
        Object selectedItem = room.getSelectionModel().getSelectedItem();
        if(selectedItem==null || maids.getCheckModel().isEmpty()){
            error.setVisible(true);
            return;
        }
        String datetime = date.getValue().toString()+"T"+
                convertTO24(hour.getSelectionModel().getSelectedItem()+":"+"00",//min.getSelectionModel().getSelectedItem().toString(), 
                am_pm.getSelectionModel().getSelectedItem().toString());
        String endDAte = date1.getValue().toString();
        String descr = desc.getText();
        String suite = ((SimpleIdTitle)selectedItem).getId();
        String inter = intervalF.getText();
        String remin = reminder.getText();
        String maid = null ;
        ObservableList c = maids.getCheckModel().getCheckedItems();
        for (int i = 0; i < c.size(); i++) {
            if(maid==null)maid="";
            SimpleIdTitle get = (SimpleIdTitle) c.get(i);
            maid=get.getId()+",";
        }
        maid=maid.substring(0, maid.length()-2);
        if(valids(datetime,endDAte,descr, suite,inter,remin,maid)){
            error.setVisible(false);
            JSONObject hkt = nav.createHouseKeepTasks(datetime,endDAte,descr, suite,inter,remin,maid,Storage.getId());
            if(hkt!=null){
                System.out.println(hkt);
            }
        }else{
            error.setVisible(true);
        }
        
    }
    
    private boolean valids(String ...x){
        for (int i = 0; i < x.length; i++) {
            if(x[i]==null)
                return false;
        }
        return true;
    }
    
    private String convertTO24(String ti, String am_PM){                  
           String result = LocalTime.parse(ti+" "+am_PM, DateTimeFormatter.ofPattern("h:mm a")).format(DateTimeFormatter.ofPattern("HH:mm"));
           return result;
    }
    
    private String convertInterval(){
        return null;
    }
    
    private String toISOTime(){
        return null;
    }
    
    private LocalDate fromISO(String t){
        return LocalDate.parse(t, DateTimeFormatter.ISO_DATE_TIME);
    }
    
    private void setEditMode() {
        scheduleButton.setText("Save");
        exitEdit.setVisible(true);
        createTitledPane.setText("Edit Schedule");
        fadeAndStroke(createPane);
        ScheduleModel s = table.getSelectionModel().getSelectedItem();
        date.setValue(fromISO(s.getDateC()));
        date1.setValue(fromISO(s.getEndDateC()));
        desc.setText(s.getDescC());
    }
    
    private void setNewMode() {
        scheduleButton.setText("Create New Schedule Task");
        exitEdit.setVisible(false);
        createTitledPane.setText("Create New Schedule Task");
        stopFadeAndStroke();
    }
       
    @FXML private void exitEditMode(){
        newOrEdit.set(true);
    }
    
    FadeTransition fadeTransition;
    private void fadeAndStroke(StackPane p) {
        Rectangle rect = new Rectangle(0, 0, createTitledPane.getPrefWidth() + 2, createTitledPane.getPrefHeight() + 2);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        rect.setFill(null);
        rect.setStroke(Color.DODGERBLUE);
        rect.setStrokeWidth(10);
        p.getChildren().add(rect);
        fadeTransition = new FadeTransition(Duration.seconds(3), rect);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0.3);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        
        fadeTransition.play();
    }

    private void stopFadeAndStroke(){
        fadeTransition.stop();
        createPane.getChildren().remove(1);
        fadeTransition=null;
    }
    
    @FXML private void print() {

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if(printerJob!=null){
            if (printerJob.showPrintDialog(app.getStage()) && printerJob.printPage(table)) 
                printerJob.endJob();
        }else{
            //No printer found
        }
        
    }
    
}


class SimpleIdTitle{
    
    private String id;
    private String name;

    public SimpleIdTitle(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
   
    public String toString(){
        return this.name;
    }
    
    
}

