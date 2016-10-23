package hotels.views.component.fxml.tools.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class Payment implements Initializable {

    @FXML
    private HBox progressHbox;
    @FXML
    private Label name;
    @FXML
    private Label room;
    @FXML
    private Label roomType;
    @FXML
    private Label arrival;
    @FXML
    private Label departure;
    @FXML
    private Label rate;
    @FXML
    private Button cancel;
    @FXML
    private Button checkOut;
    @FXML
    private Button retry;
    @FXML
    private Label total;
    @FXML
    private Label bal;
    @FXML
    private Label desc;
    @FXML
    private RadioButton cash;
    @FXML
    private ToggleGroup channel;
    @FXML
    private RadioButton pos;
    @FXML
    private RadioButton transfer;
    @FXML
    private TextField posTransId;
    @FXML
    private TextField bankTransId;
    @FXML
    private ComboBox<?> bankList;
    @FXML
    private TextField current;

    private double amount; 
//    private String descrr, 
//            refNo, 
//            payFor,
//            orderId, 
//            dept, 
//            guestId,
//            guestName, 
//            guestPhone; 
//    boolean isCoperate;
    
    public void setFields(double amount, String desc, String channel, 
            String refNo, String payFor, String orderId, int dept, String guestId,
            String guestName,String guestPhone, boolean isCoperate){
        
        this.amount = amount;
        
        this.bal.setText(""+0);
        this.current.setText(""+amount);
        this.total.setText(""+amount);
        
    }
    
    private Hotels app;
    private Navigator2 nav;
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public Payment(Hotels app) {
        this.app=app;
        nav=new Navigator2(app.getMain());
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
        bal.textProperty().bind(current.textProperty());
    }
    
    
    
    
}