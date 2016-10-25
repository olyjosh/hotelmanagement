package hotels.views.component.fxml.tools.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import hotels.util.State;
import hotels.util.Util;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Button cancel, pay;
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
    private String descrr, refNo,
            payFor,
            orderId, 
            guestId,
            guestName,
            guestPhone; 
    private boolean isCoperate;
    private int dept ;
    
    
    
    public void setFields(double amount, String desc,String payFor, String orderId, int dept, String guestId,
            String guestName,String guestPhone, boolean isCoperate){
        
        
        this.amount = Math.abs(amount);
        this.descrr = desc;
        this.guestName = guestName;
        this.payFor = payFor;
        this.orderId = orderId;
        this.dept = dept;
        this.isCoperate = isCoperate;
        this.guestPhone = guestPhone;
        this.guestId = guestId;
        
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
    
    
    private void defaults() {
        this.bal.setText("" + 0);
        this.current.setText("" + amount);
        this.total.setText("" + amount);
        this.name.setText(guestName == null ? "#ANONYMOUS" : guestName);
        this.desc.setText(descrr);
        bal.textProperty().bind(current.textProperty());
        
        channel.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (pos.equals((RadioButton) newValue)) {
                    posTransId.setDisable(false);
                    posTransId.requestFocus();
                    bankTransId.setDisable(true);
                }else if(transfer.equals((RadioButton) newValue)) {
                    posTransId.setDisable(true);
                    bankTransId.setDisable(false);
                    bankTransId.requestFocus();
                }
                
            }
        });

    }

    @FXML private void pay(){
        
        Toggle selectedToggle = channel.getSelectedToggle();
        if(selectedToggle==null){
            Util.notify("Select Payment method", "Please select a payment channel", Pos.CENTER);
            return;
        }
        
        RadioButton s = (RadioButton)selectedToggle;
        String channels = s==cash? State.channel_FRONT : (s==pos? State.channel_POS:State.channel_BANK_TRANSFER);
        System.out.println(channels);
        refNo = channels.equals( State.channel_POS) ? posTransId.getText().trim() : bankTransId.getText().trim();
        if(!channels.equals(State.channel_FRONT) && refNo.isEmpty()){
            Util.notify("Empty Transaction ID", "Transaction ID is required for this operation", Pos.CENTER);
            return;
        }
        
        
        String g = guestName == null ? null : createGuestObject(this.guestName, this.guestPhone).toString();

        Runnable task = () -> {
            setVisible(false);
            JSONObject payee = nav.pay(amount, descrr, channels, refNo, payFor, orderId, dept, guestId, g);
            if(payee!=null){
                try {
                    int aInt = payee.getInt("status");
                    if(aInt==0){
                        Util.notify("Payment Error", "Server error while processing request", Pos.CENTER);
                        setVisible(true);
                        pay.setText("Retry");
                        return;
                    }
                    if(aInt==1){
                        
                        JSONObject j = payee.getJSONObject("message");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Util.notify_SUCCESS("Payment Successfully Recorded", "Payment was recorded successfully.\nPlease wait while receipt is been printed", Pos.CENTER);
                                Util.notify_ERROR("Printer not installed", "No instance of receipt printer found.\n Please install one", Pos.CENTER);

                            }
                        });                   
                    }
                    
                } catch (JSONException ex) {
                    Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                setVisible(true);
                pay.setText("Retry");
            }
        };
        // Run the task in a background thread
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
        
        
        

    }
//    
//    private paymentTask(){
//        
//    }
//    
    
    private JSONObject createGuestObject(String name, String phone){
        HashMap nameMap = new HashMap(), guestMap = new HashMap();
        String[] split = name.split(" ");
        nameMap.put("firstName", split[0]);
        nameMap.put("lastName", split[1]);
        
        guestMap.put("name", nameMap);
        guestMap.put("phone", phone);
        
        return new JSONObject(guestMap);
    }
    
    private void setVisible(boolean v){
        cancel.setVisible(v);
        pay.setVisible(v);
    }
    
}