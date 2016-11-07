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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class MiscSaleListController implements Initializable {

    
              
    
    @FXML
    private TableView<PaySale> table;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn paidFor;
    @FXML
    private TableColumn payType;
    @FXML
    private TableColumn roomNo;
    @FXML
    private TableColumn receiptNo;
    @FXML
    private TableColumn amount;
    @FXML
    private Label grandTotal;
    
    
    private Hotels app;
    private Navigator nav;
    private static JSONObject sale;
    private static JSONArray saleArray;
    private PaySale ls;
    private ObservableList<PaySale> service = FXCollections.observableArrayList();
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public MiscSaleListController(Hotels app) {
        this.app =app;
        nav = new Navigator(getApp().getMain());
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
        
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    sale = nav.fetchMisc();
                    saleArray = sale.getJSONArray("message");
                    
                    getMiscList();
                    
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        };
        // Run the task in a background thread
            Thread back = new Thread(task);
            back.setPriority(Thread.MAX_PRIORITY);
            back.setDaemon(true);
            back.start();
           
            
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            paidFor.setCellValueFactory(new PropertyValueFactory<>("paidFor"));
            roomNo.setCellValueFactory(new PropertyValueFactory<>("room"));
            receiptNo.setCellValueFactory(new PropertyValueFactory<>("voucher"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            
            table.getColumns().setAll(date, paidFor, roomNo, receiptNo, amount);
       
    }
    
    private void getMiscList(){
        try {
            for(int i = 0; i < saleArray.length(); i++){
                ls = new PaySale();
                JSONObject oj = saleArray.getJSONObject(i);
                ls.setId(oj.getString("_id"));
                ls.setCharge(oj.getString("extraCharge"));
                ls.setAdj(String.valueOf(oj.get("adjustment")));
                ls.setAmount(oj.getString("amount"));
                ls.setAmountPaid(String.valueOf(oj.get("amountPaid")));
                //ls.setCat(oj.getString("category"));
                ls.setDate(Util.stripDate(oj.getString("creattedAt")));
                ls.setDiscount(oj.getString("discount"));
                ls.setPaidTo(oj.getString("receivedFor"));
                ls.setQty(oj.getString("qty"));
                ls.setRemark(oj.getString("remarks"));
                ls.setRoomNo(oj.getString("roomNo"));
                ls.setTax(String.valueOf(oj.get("tax")));
                ls.setTotal(String.valueOf(oj.get("total")));
                ls.setVoucher(oj.getString("receiptNo"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    @FXML 
    private void showNewMisc(ActionEvent e) throws IOException{
        NewMiscController controller = new NewMiscController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newMisc.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditMisc(ActionEvent e) throws IOException{
       
        PaySale item = table.getSelectionModel().getSelectedItem();
        NewMiscController controller = new NewMiscController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newMisc.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root)); 

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteMisc(){
        PaySale item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteMisc(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Miscellaneous Sale Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Miscellaneous Sale Failed to Delete", Pos.CENTER);
                            }
                        });
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(HotelServiceListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread back = new Thread(task);
        back.setPriority(Thread.MAX_PRIORITY);
        back.setDaemon(true);
        back.start();
    }
}
