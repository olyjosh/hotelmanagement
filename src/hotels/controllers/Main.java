package hotels.controllers;

import hotel.reports.Reporter;
import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.views.component.fxml.admin.NewUserController;
import hotels.views.component.fxml.admin.controller.Summary;
import hotels.views.component.fxml.admin.RoomListController;
import hotels.views.component.fxml.bar.controller.DrinkOrder;
import hotels.views.component.fxml.bar.controller.NewDrink;
import hotels.views.component.fxml.bar.controller.OnlineDrinkOrder;
import hotels.views.component.fxml.front.controller.ReserveListController;
import hotels.views.component.fxml.front.controller.Dashboard;
import hotels.views.component.fxml.front.controller.GuestListController;
import hotels.views.component.fxml.front.controller.GuestMessage;
import hotels.views.component.fxml.front.controller.NewBookingController;
import hotels.views.component.fxml.front.controller.RoomStayView;
import hotels.views.component.fxml.front.controller.RoomsPane;
import hotels.views.component.fxml.housekeep.controller.HouseKeep;
import hotels.views.component.fxml.housekeep.controller.MaidManagement;
import hotels.views.component.fxml.housekeep.controller.ScheduleManagement;
import hotels.views.component.fxml.laundry.DailyLaundryController;
import hotels.views.component.fxml.laundry.LaundryDetailController;
import hotels.views.component.fxml.laundry.LaundryItemsController;
import hotels.views.component.fxml.laundry.LaundryListController;
import hotels.views.component.fxml.laundry.LaundryServiceController;
import hotels.views.component.fxml.laundry.NewItemController;
import hotels.views.component.fxml.laundry.NewLaundryServiceController;
import hotels.views.component.fxml.laundry.ReturnInController;
import hotels.views.component.fxml.restaurant.controller.FoodOrder;
import hotels.views.component.fxml.restaurant.controller.NewFood;
import hotels.views.component.fxml.restaurant.controller.OnlineFoodOrder;
import hotels.views.component.fxml.tools.AccountListController;
import hotels.views.component.fxml.tools.BusinessSourceController;
import hotels.views.component.fxml.tools.HotelServiceListController;
import hotels.views.component.fxml.tools.LostFoundController;
import hotels.views.component.fxml.tools.MiscSaleListController;
import hotels.views.component.fxml.tools.NewHotelServiceController;
import hotels.views.component.fxml.tools.PayOutListController;
import hotels.views.component.fxml.tools.PhoneListController;
import hotels.views.component.fxml.tools.ReminderListController;
import hotels.views.component.fxml.tools.WorkOrderController;
import hotels.views.component.fxml.tools.controller.Payment;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.controlsfx.control.PopOver;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class Main implements Initializable{

    @FXML public StackPane frontContentStack, laundryContentStack,
            houseContentStack,toolContentStack,restaurantContentStack,barContentStack,adminContentStack;
    @FXML private HBox progHbox;
    @FXML private ProgressIndicator prog;
    @FXML private Label userButton, progLabel;
    PopOver p ;
    
    private Hotels app;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDashBoard();
        userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button b = new Button("Logout");
                
                if(p==null){
                    p = new PopOver(b);
                    p.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
                }
                p.show(userButton);
            }
        });
        
        
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
    
    @FXML private void showDashBoard(){
        try {
                    
            Dashboard controller = new Dashboard(this.getApp());
            controller.setApp(getApp());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/Dashboard.fxml"));
            loader.setController(controller);
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
        RoomStayView controller = new RoomStayView(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomStayView.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    @FXML
    private void showbookingCardLayoutView() throws IOException{
        RoomsPane controller = new RoomsPane(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/roomsPane.fxml"));
        loader.setController(controller);
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
    
    //House keeping
    @FXML
    private void showDailyLaundry() throws IOException{
        DailyLaundryController controller = new DailyLaundryController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/dailyLaundry.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = laundryContentStack.getChildren();
        if(children.size()>0)laundryContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    

    @FXML
    private void showLaundryDetail() throws IOException{
        LaundryDetailController controller = new LaundryDetailController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/laundryDetail.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = laundryContentStack.getChildren();
        if(children.size()>0)laundryContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
            
    @FXML
    private void showLaundryItem() throws IOException{
                
        LaundryItemsController controller = new LaundryItemsController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/laundryItems.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = laundryContentStack.getChildren();
        if(children.size()>0)laundryContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
            
    @FXML
    private void showLaundryList() throws IOException{
                
        LaundryListController controller = new LaundryListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/laundryList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = laundryContentStack.getChildren();
        if(children.size()>0)laundryContentStack.getChildren().remove(0, children.size());
        children.add(content);
    }

    @FXML
    private void showLaundryService() throws IOException {
        LaundryServiceController controller = new LaundryServiceController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/laundryService.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = laundryContentStack.getChildren();
        if (children.size() > 0) {
            laundryContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);

    }

    @FXML 
    private void showNewLaundryService(ActionEvent e) throws IOException{
      
        NewLaundryServiceController controller = new NewLaundryServiceController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newLaundryService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showNewReturnIn(ActionEvent e) throws IOException{
      
        ReturnInController controller = new ReturnInController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/returnIn.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showNewLaundryItem(ActionEvent e) throws IOException{
      
        NewItemController controller = new NewItemController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/laundry/newItem.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showNewHotelService(ActionEvent e) throws IOException{
      
        NewHotelServiceController controller = new NewHotelServiceController(this.getApp());
        controller.setApp(getApp());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newHotelService.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    
    
    //houseContentStack
    @FXML
    private void showHouseKeep() throws IOException {
        HouseKeep controller = new HouseKeep(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/housekeep/houseKeep.fxml"));
        loader.setController(controller);
        HBox content = (HBox) loader.load();
        ObservableList<Node> children = houseContentStack.getChildren();
        if (children.size() > 0) {
            houseContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);

    }
    
    @FXML
     private void showMaidList() throws IOException {
        MaidManagement controller = new MaidManagement(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/housekeep/maidManagement.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = houseContentStack.getChildren();
        if (children.size() > 0) {
            houseContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);

    }
    
     @FXML
     private void showSchedule() throws IOException {
         ScheduleManagement controller = new ScheduleManagement(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/housekeep/scheduleManagement.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = houseContentStack.getChildren();
        if (children.size() > 0) {
            houseContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);

    }

     
     //Tool and Utility
         
     @FXML
     private void showAccountList() throws IOException {
         AccountListController controller = new AccountListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/accountList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
    
              
     @FXML
     private void showBusiness() throws IOException {
         BusinessSourceController controller = new BusinessSourceController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/businessSource.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showMisc() throws IOException {
         MiscSaleListController controller = new MiscSaleListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/miscSaleList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     
     @FXML
     private void showLostFound() throws IOException {
        LostFoundController controller = new LostFoundController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/lostFound.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showHotelService() throws IOException {
        HotelServiceListController controller = new HotelServiceListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/hotelServiceList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showPhoneList() throws IOException {
        PhoneListController controller = new PhoneListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/phoneList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     
      @FXML
     private void showPayOutList() throws IOException {
          PayOutListController controller = new PayOutListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/payOutList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showReminderList() throws IOException {
        ReminderListController controller = new ReminderListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/reminderList.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
      @FXML
     private void showWorkOrder() throws IOException {
        WorkOrderController controller = new WorkOrderController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/workOrder.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = toolContentStack.getChildren();
        if (children.size() > 0) {
            toolContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     //Restaurant 
     @FXML
     private void showFoodOrder() throws IOException {
        FoodOrder controller = new FoodOrder(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/restaurant/foodOrder.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = restaurantContentStack.getChildren();
        if (children.size() > 0) {
            restaurantContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
         
     @FXML
     private void showOnlineFoodOrder() throws IOException {
        OnlineFoodOrder controller = new OnlineFoodOrder(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/restaurant/onlineFoodOrder.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = restaurantContentStack.getChildren();
        if (children.size() > 0) {
            restaurantContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showNewFood() throws IOException {
        NewFood controller = new NewFood(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/restaurant/newFood.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = restaurantContentStack.getChildren();
        if (children.size() > 0) {
            restaurantContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     

     //Bar 
     @FXML
     private void showDrinkOrder() throws IOException {
        DrinkOrder controller = new DrinkOrder(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/bar/drinkOrder.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = barContentStack.getChildren();
        if (children.size() > 0) {
            barContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
         
     @FXML
     private void showOnlineDrinkOrder() throws IOException {
        OnlineDrinkOrder controller = new OnlineDrinkOrder(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/bar/onlineDrinkOrder.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = barContentStack.getChildren();
        if (children.size() > 0) {
            barContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showNewDrink() throws IOException {
        NewDrink controller = new NewDrink(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/bar/newDrink.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = barContentStack.getChildren();
        if (children.size() > 0) {
            barContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
      
     @FXML
     public void showPayment(double amount, String desc, String payFor, String orderId, int dept, String guestId,
        String guestName,String guestPhone, boolean isCoperate) throws IOException {

        Payment controller = new Payment(this.getApp());
        controller.setApp(app);
        controller.setFields(amount, desc, payFor, orderId, dept, guestId, guestName, guestPhone, isCoperate);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/payment.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(content));
        stage.show();
    }
     
     
     
     
     
//Admin 
     
     @FXML
     private void showRoomList() throws IOException {
        RoomListController controller = new RoomListController(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/roomAmenityList.fxml"));
//        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = adminContentStack.getChildren();
        if (children.size() > 0) {
            adminContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showTypeList() throws IOException {
        NewDrink controller = new NewDrink(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/roomTypeList.fxml"));
//        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = adminContentStack.getChildren();
        if (children.size() > 0) {
            adminContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     
      @FXML
     private void showFloorList() throws IOException {
        NewDrink controller = new NewDrink(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/floorList.fxml"));
//        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = adminContentStack.getChildren();
        if (children.size() > 0) {
            adminContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
        
     @FXML
     private void showSeasonList() throws IOException {
        NewDrink controller = new NewDrink(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/seasonList.fxml"));
//        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = adminContentStack.getChildren();
        if (children.size() > 0) {
            adminContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
     @FXML
     private void showUserList() throws IOException {
        NewDrink controller = new NewDrink(this.getApp());
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/userList.fxml"));
//        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        ObservableList<Node> children = adminContentStack.getChildren();
        if (children.size() > 0) {
            adminContentStack.getChildren().remove(0, children.size());
        }
        children.add(content);
    }
     
    
     @FXML
     private void showSummary() throws IOException {
         Summary controller = new Summary(this.getApp());
         
         
        controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/summary.fxml"));
        loader.setController(controller);
        AnchorPane content = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(content));
        stage.show();

    }
     
     
     @FXML
     private void showNight() throws IOException {
         Navigator2 nav = new Navigator2(this);
        JSONObject fetchNight = nav.fetchNight();
        
        if (fetchNight!=null){
            new Reporter().buildNight(fetchNight);
        };
//        Summary controller = new Summary(this.getApp());
//        controller.setApp(app);
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/admin/summary.fxml"));
//        loader.setController(controller);
//        AnchorPane content = (AnchorPane) loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(content));
//        stage.show();

    }
     
     
     //Testing Codes down here.
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
    
    // Administrative confirmation
    public String[]  showAdminComfirmation() throws IOException {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Permission Required");
        dialog.setHeaderText("Asdministrative authorisation is required to complete this transaction");

//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        
// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        String [] x = new String[2];
        final Pair<String, String> pair;
        result.ifPresent((Pair<String, String> usernamePassword) -> {
            x[0]=usernamePassword.getKey();
            x[1]=usernamePassword.getValue();
        });
        return x;
    }
    
}
