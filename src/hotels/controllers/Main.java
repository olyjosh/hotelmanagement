package hotels.controllers;

import hotels.Hotels;
import hotels.views.component.fxml.admin.NewFloorController;
import hotels.views.component.fxml.admin.NewRoomController;
import hotels.views.component.fxml.admin.NewRoomTypeController;
import hotels.views.component.fxml.admin.NewUserController;
import hotels.views.component.fxml.front.ReserveListController;
import hotels.views.component.fxml.front.controller.GuestListController;
import hotels.views.component.fxml.front.controller.GuestMessage;
import hotels.views.component.fxml.front.controller.NewBookingController;
import hotels.views.component.fxml.laundry.NewDailyController;
import hotels.views.component.fxml.laundry.NewItemController;
import hotels.views.component.fxml.laundry.NewLaundryServiceController;
import hotels.views.component.fxml.laundry.ReturnInController;
import hotels.views.component.fxml.tools.NewHotelServiceController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Olyjosh
 */
public class Main implements Initializable{

    @FXML private StackPane frontContentStack;
    @FXML private HBox progHbox;
    @FXML private ProgressIndicator prog;
    @FXML private Label progLabel;
    
    private Hotels app;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDashBoard();
        getApp().setMain(this);
    }

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public HBox getProgHbox() {
        return progHbox;
    }

    public void setProgHbox(HBox progHbox) {
        this.progHbox = progHbox;
    }

    public ProgressIndicator getProg() {
        return prog;
    }

    public void setProg(ProgressIndicator prog) {
        this.prog = prog;
    }

    public Label getProgLabel() {
        return progLabel;
    }

    public void setProgLabel(Label progLabel) {
        this.progLabel = progLabel;
    }
    
    public Label responseInfo(String message){
        message = message==null? "Having a good business I guess!" : message;
        progLabel.setText(message);
        progLabel.setStyle("-fx-text-fill : #ffffff;");//#85ffaf;
        progLabel.setVisible(true);
        prog.setVisible(false);
        return progLabel;
    }
    
    public Label responseWarning(String message){
        message = message==null? "that operation is error prone" : message;
        progLabel.setText(message);
        progLabel.setStyle("-fx-text-fill-color : #ffc487;");//#85ffaf;
        progLabel.setTextFill(Paint.valueOf("#ffa1b5"));
        progLabel.setVisible(true);
        prog.setVisible(false);
        return progLabel;
    }
    
    public Label responseError(String message){
        message = message==null? "Oops! Unexpected error" : message;
        progLabel.setText(message);
        progLabel.setStyle("-fx-text-fill : #ffa1b5;");//#85ffaf;
//        progLabel.setTextFill(Paint.valueOf("#ffa1b5"));
        progLabel.setVisible(true);
        prog.setVisible(false);
        return progLabel;
    } 
    
    public Label responseProcessing(String message){
        message = message==null? "processing request..." : message;
        progLabel.setText(message);
        progLabel.setStyle("-fx-text-fill: #85ffaf;");//#85ffaf;
        progLabel.setVisible(true);
        prog.setVisible(true);
        return progLabel;
    }

    @FXML 
    private void showCheckIn(ActionEvent e) throws IOException{
      
        NewBookingController controller = new NewBookingController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/newBooking.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    private void showDashBoard(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/Dashboard.fxml"));
            //loader.setController(controller);
            AnchorPane dashBoard = (AnchorPane)loader.load();
            ObservableList<Node> children = frontContentStack.getChildren();
            if (children.size() > 0) {
                frontContentStack.getChildren().remove(0, children.size());
            }
            children.add(dashBoard);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void addContentPane() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/content.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    @FXML
    private void showRoomStayView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomStayView.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    @FXML
    private void showbookingCardLayoutView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomsPane.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    
    @FXML
    private void showCustomerLedger() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/customerLedger.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    @FXML
    private void showGuestlist() throws IOException{
        GuestListController controller = new GuestListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/guestList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    
    @FXML
    private void showGuestMessages() throws IOException{
        
        GuestMessage controller = new GuestMessage(this.getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/guestMessage.fxml"));
        loader.setController(controller);
//        loader.setApp(this);
//        loader.setStaff(getStaff());
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    @FXML
    private void showOutOfOrder() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/outOfOrder.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
        
    @FXML
    private void showReserve() throws IOException{
        ReserveListController controller = new ReserveListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/reserveList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    
    @FXML 
    private void showLaundry(ActionEvent e) throws IOException{
      
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
