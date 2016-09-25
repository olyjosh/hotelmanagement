package hotels.controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Olyjosh
 */
public class Main implements Initializable{

    @FXML private StackPane frontContentStack;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDashBoard();
    }
    
    @FXML 
    private void showCheckIn(ActionEvent e) throws IOException{
        //Login controller = new Login();
        //controller.setApp(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/newBooking.fxml"));
        //loader.setController(controller);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/guestList.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    
    @FXML
    private void showGuestMessages() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/guestMessage.fxml"));
        //loader.setController(controller);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/reserveList.fxml"));
        //loader.setController(controller);
        AnchorPane content = (AnchorPane)loader.load();
        ObservableList<Node> children = frontContentStack.getChildren();
        if(children.size()>0)frontContentStack.getChildren().remove(0, children.size());
        children.add(content);
        
    }
    
    
}
