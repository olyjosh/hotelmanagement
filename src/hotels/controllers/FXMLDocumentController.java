package hotels.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Popup;
import hotels.Hotels;
//import hotels.model.MongoDBInc;
import hotels.model.entity.Record;
//import hotels.model.user.ClientM;
//import hotels.views.component.fxml.controller.AddPatienceRecord;
//import hotels.views.component.fxml.controller.NewPatience;
//import hotels.views.component.fxml.controller.PatienceDetails;
//import hotels.views.component.fxml.controller.PatienceRecordPane;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
//import org.controlsfx.control.textfield.AutoCompletionBinding;
//import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author olyjosh
 */
public class FXMLDocumentController implements Initializable {

    @FXML private ToolBar toolBar;
    @FXML private Region spacer1,spacer2;
    @FXML private Button settings;
    @FXML private Pane bodyPane;
    @FXML private HBox bodypaneHBox;
    @FXML private StackPane change;
    @FXML private TextField patienceID;
    @FXML private ImageView imgView;
    @FXML private ProgressIndicator progress;
    
    

//    private Stack<Page> history = new Stack<Page>();
//    private Stack<Page> forwardHistory = new Stack<Page>();
    private TableView<Record> t;
    private boolean changingPage = false;
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    private Hotels app;
//    private MongoDBInc m;
//    private ClientM staff,patience;
//    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //m=app.getDbInstance();
        //stage = (Stage) toolBar.getParent().getScene().getWindow();
        init();
//        searchField();
    }

    
    public Hotels getApp() {
        return app;
    }


    public void setApp(Hotels app){
        this.app = app;
    }

//    public void setStaff(ClientM staff) {
//        this.staff = staff;
//    }

    private void init() {
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        
//        settings.setGraphic(new ImageView("/themefx/images/settings.png"));
//        settings.setTooltip(new Tooltip("Settings"));
//        WindowTop win = new WindowTop(app.getStage(), toolBar, false,false, spacer1);

//        final WindowButtons windowButtons = new WindowButtons(stage);
//        HBox h = new HBox(100, new Region(),windowButtons);
//        SearchBox searchBox = new SearchBox();
//        HBox.setMargin(searchBox, new Insets(0,5,0,0));
//        toolBar.getItems().add(new VBox(20, h,searchBox));
//        
//        // add window header double clicking
//        toolBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                stage = (Stage) toolBar.getParent().getScene().getWindow();
//                if (event.getClickCount() == 2) {
//                    windowButtons.toogleMaximized();
//                }
//            }
//        });
//        // add window dragging
//        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                mouseDragOffsetX = event.getSceneX();
//                mouseDragOffsetY = event.getSceneY();
//            }
//        });
//        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (!windowButtons.isMaximized()) {
//                    stage.setX(event.getScreenX() - mouseDragOffsetX);
//                    stage.setY(event.getScreenY() - mouseDragOffsetY);
//                }
//            }
//        });
        //bodyPane.getChildren().add(addModule());
        //table();
        imgView.setVisible(false);
        imgView.setOnMouseClicked((MouseEvent event) -> {
            //Show details of patience base on privilege
        });
    }
    
    public void showProgress(){
        progress.setVisible(true);
    }
    
    public void hideProgress(){
        progress.setVisible(false);
    }
    
//    private void findPatience(String id){
//        if(m==null)m=app.getDbInstance();
//        patience = m.patience(id);
//        if (patience != null) {
//            // Loadup interface base on staff type
//            short staffType = staff.getPost();
//            
//            Tooltip.install(imgView, new Tooltip(
//                    patience.getFirstName()+" "+patience.getLastName()+"\n"+
//                            patience.getSex()+"\n"+
//                            patience.getDob())
//            );
//            loadPatienceDetails();
//            switch (staffType) {
//                case ClientM.POST_DOCTOR:
//                    populatePatienceForDoc(patience);
//                case ClientM.POST_LABATTENDANT:
//                    populatePatienceForLabAtt(patience);
//                case ClientM.POST_NURSE:
//                    populatePatienceForNurse(patience);
//                case ClientM.POST_OPTICIAN:
//                    populatePatienceForOptician(patience);
//                case ClientM.POST_SURGEON:
//                    populatePatienceForSurgeon(patience);
//                default:
//                    populatePatienceForDoc(patience);
//                    //noPriviledge();
//            }
//        }else{
//            //report such patience not exist
//            System.out.println("report such patience not exist");
//            
//        }
//    }
//    
//    private void populatePatienceForDoc(ClientM pat){
//        imgView.setVisible(true);
//    }
//    
//    private void populatePatienceForLabAtt(ClientM pat){
//        imgView.setVisible(true);
//    }
//    
//    private void populatePatienceForNurse(ClientM pat){
//        imgView.setVisible(true);
//    }
//    
//    private void populatePatienceForPhamacy(ClientM pat){
//        imgView.setVisible(true);
//    }
//    
//    private void populatePatienceForOptician(ClientM pat){
//        imgView.setVisible(true);
//    }
//    
//    private void populatePatienceForSurgeon(ClientM pat){
//        imgView.setVisible(true);
//    }
    
    private void noPriviledge(){
    }
    
//    private void searchField(){
//        TextFields.bindAutoCompletion(patienceID, "","").
//                setOnAutoCompleted((AutoCompletionBinding.AutoCompletionEvent<String> event) -> {
//            findPatience(patienceID.getText());
//        });
//        
//        patienceID.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
//            if(event.getCode()==KeyCode.ENTER){
//                String text = patienceID.getText();
//                if(!text.isEmpty()){
//                    //Try doing stuff on reporting ivalid id type
//                    findPatience(text);
//                    
//                }
//            }
//        });
//        
//        
//    }
    
    @FXML
    private void setting(ActionEvent event) {
        Popup setup = new Popup();
        try {
            Button b = (Button)event.getSource();
            AnchorPane acp=(AnchorPane)FXMLLoader.load(getClass().getResource("/hotels/views/component/fxml/settings.fxml"));
//            acp.getStyleClass().add("search-info-box");
            acp.setId("search-info-box");
            setup.getContent().add(acp);
            Point2D bPos = b.localToScene(0, 0);
            setup.show(b, bPos.getX()-100, bPos.getY()+200);
            setup.setAutoHide(true);
            
//            setup.show(getScene().getWindow(),
//                    hBoxPos.getX() + contextMenu.getScene().getX() + contextMenu.getX() - infoBox.getPrefWidth() - 10,
//                    hBoxPos.getY() + contextMenu.getScene().getY() + contextMenu.getY() - 27
//            );
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void table(){
        t = new TableView<>();
        double d = bodypaneHBox.getPrefWidth()/5;
        TableColumn syptoms=new TableColumn(),
                descr= new TableColumn(),
                recom= new TableColumn(),
                date = new TableColumn();
        syptoms.setText("Symptoms");
        syptoms.setMinWidth(d);
        descr.setText("Descriptions");
        descr.setMinWidth(d);
        recom.setText("Recommendations");
        recom.setMinWidth(d);
        date.setText("Date");
        date.setMinWidth(d);
        
        t.getColumns().addAll(syptoms,descr,recom,date);
        HBox.setHgrow(t, Priority.ALWAYS);
        bodypaneHBox.getChildren().add(t);
    }
   
//    public void loadRegNewPatience() {
//        try {
//            NewPatience logCont = new NewPatience();
//            logCont.setApp(this.getApp());
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/NewPatience.fxml"));
//            //Parent root = (Parent)fxmlLoader.load();
//            fxmlLoader.setController(logCont);
//            Object load = fxmlLoader.load();
//            change.getChildren().setAll((AnchorPane) load);
//
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @FXML private void loadAddRecord() {
//        try {
//            AddPatienceRecord cont = new AddPatienceRecord();
//            cont.setApp(this.getApp());
//            cont.setPatience(patience);
//            cont.setHome(this);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/AddPatienceRecord.fxml"));
//            //Parent root = (Parent)fxmlLoader.load();
//            fxmlLoader.setController(cont);
//            Object load = fxmlLoader.load();
//            change.getChildren().setAll((AnchorPane) load);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    @FXML private void loadPatienceRecordPane() {
//        try {
//            PatienceRecordPane cont = new PatienceRecordPane();
//            cont.setPatience(patience);
//            cont.setHome(this);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/PatienceRecordPane.fxml"));
//            //Parent root = (Parent)fxmlLoader.load();
//            fxmlLoader.setController(cont);
//            Object load = fxmlLoader.load();
//            change.getChildren().setAll((ScrollPane) load);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @FXML private void loadPatienceDetails() {
//        try {
//            PatienceDetails cont = new PatienceDetails();
//            cont.setPatience(patience);
//            cont.setHome(this);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/PatienceDetails.fxml"));
//            //Parent root = (Parent)fxmlLoader.load();
//            fxmlLoader.setController(cont);
//            Object load = fxmlLoader.load();
//            change.getChildren().setAll((ScrollPane) load);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    
//    @FXML private void searchButton(){
//        findPatience(patienceID.getText());
//    }
//    
//    //To be use for registering new patient
//    @FXML private void change(ActionEvent event){
//        loadRegNewPatience();
//    }
//
//    private void logout(){
//        try {
//            app.setStaff(null);
//            app.gotoLogin();
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}