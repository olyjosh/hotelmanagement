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
import hotels.views.component.fxml.tools.model.PaySale;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author NOVA
 */
public class NewMiscController implements Initializable {

    @FXML
    private TextField voucher;
    @FXML
    private TextField paidTo;
    @FXML
    private TextField cat;
    @FXML
    private TextField charge;
    @FXML
    private TextField room;
    @FXML
    private TextField amount;
    @FXML
    private TextField discount;
    @FXML
    private TextField tax;
    @FXML
    private TextField qty;
    @FXML
    private TextField adj;
    @FXML
    private TextField amountPaid;
    @FXML
    private TextArea remark;
    @FXML
    private TextField total;

    
    private Hotels app;
    private boolean editMode;
    private PaySale data;
    private Navigator nav;
    private JSONObject response;

    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public PaySale getData() {
        return data;
    }

    public void setData(PaySale data) {
        this.data = data;
    }
    
    public NewMiscController(Hotels app) {
        this.app = app;
        nav  = new Navigator(getApp().getMain());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       onLoad();
    }    
    
    
    
    private void onLoad(){
        if(isEditMode()){
            popEdit();
        }
    }
    
    private void popEdit(){
        if(data != null){
            voucher.setText(data.getVoucher());
            paidTo.setText(data.getPaidTo());
            charge.setText(data.getCharge());
            cat.setText(data.getCat());
            room.setText(data.getRoomNo());
            amount.setText(data.getAmount());
            discount.setText(data.getDiscount());
            tax.setText(data.getTax());
            qty.setText(data.getQty());
            adj.setText(data.getAdj());
            amountPaid.setText(data.getAmountPaid());
            total.setText(data.getTotal());
            remark.setText(data.getRemark());
        }
        
    }    
    
    @FXML private void newMisc(){
        
        List <NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("voucherNo", voucher.getText()));
        param.add(new BasicNameValuePair("paidTo", paidTo.getText()));
        param.add(new BasicNameValuePair("category", cat.getText()));
        param.add(new BasicNameValuePair("extraCharge", charge.getText()));
        param.add(new BasicNameValuePair("roomNO", room.getText()));
        param.add(new BasicNameValuePair("amount", amount.getText()));
        param.add(new BasicNameValuePair("discount", discount.getText()));
        param.add(new BasicNameValuePair("tax", tax.getText()));
        param.add(new BasicNameValuePair("qty", qty.getText()));
        param.add(new BasicNameValuePair("adjustment", adj.getText()));
        param.add(new BasicNameValuePair("amountPaid", amountPaid.getText()));
        param.add(new BasicNameValuePair("total", total.getText()));
        param.add(new BasicNameValuePair("remarks", remark.getText()));
        
        param.add(new BasicNameValuePair("performedBy", "57deca5d35fb9a487bdeb70f"));
      
        if(!isEditMode()){
            
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try {
                        response = nav.createMisc(param);
                        if(response != null && response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "New Miscellaneous Sale Registered", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Miscellaneous Sale Failed to Create", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }else{

            Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    param.add(new BasicNameValuePair("id", data.getId()));
                    response = nav.editMisc(param);
                        if(response.getInt("status") == 1){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_SUCCESS, "Miscellaneous Sale Data Updated", Pos.CENTER);
                                }
                            });
                        }else{

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Util.notify(State.NOTIFY_ERROR, "Miscellaneous Sale Data Failed to Update", Pos.CENTER);
                                }
                            });
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
        }
    }
    
}