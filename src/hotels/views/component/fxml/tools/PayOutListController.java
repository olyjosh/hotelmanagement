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
import hotels.views.component.fxml.tools.model.HotelService;
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
public class PayOutListController implements Initializable {

    
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
    private TableColumn voucher;
    @FXML
    private TableColumn amount;
    
    
    private Hotels app;
    private Navigator nav;
    private static JSONObject pay;
    private static JSONArray payArray;
    private PaySale ls;
    private ObservableList<PaySale> service = FXCollections.observableArrayList();
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public PayOutListController(Hotels app) {
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
                    pay = nav.fetchPayout();
                    payArray = pay.getJSONArray("message");
                    
                    getPayList();
                    
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
            paidFor.setCellValueFactory(new PropertyValueFactory<>("paidTo"));
            payType.setCellValueFactory(new PropertyValueFactory<>("cat"));
            roomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            voucher.setCellValueFactory(new PropertyValueFactory<>("voucher"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            
            table.getColumns().setAll(date, paidFor, payType, roomNo, voucher, amount);
       
    }
    
    private void getPayList(){
        try {
            for(int i = 0; i < payArray.length(); i++){
                ls = new PaySale();
                JSONObject oj = payArray.getJSONObject(i);
                System.out.println("Printing Hotel Service : " + oj);
                ls.setId(oj.getString("_id"));
                ls.setCharge(oj.getString("extraCharge"));
                ls.setAdj(String.valueOf(oj.get("adjustment")));
                ls.setAmount(oj.getString("amount"));
                ls.setAmountPaid(String.valueOf(oj.get("amountPaid")));
                ls.setCat(oj.getString("category"));
                ls.setDate(Util.stripDate(oj.getString("createdAt")));
                ls.setDiscount(oj.getString("discount"));
                ls.setPaidTo(oj.getString("paidTo"));
                ls.setQty(String.valueOf(oj.get("qty")));
                ls.setRemark(oj.getString("remarks"));
                ls.setRoomNo(oj.getString("roomNO"));
                ls.setTax(String.valueOf(oj.get("tax")));
                ls.setTotal(String.valueOf(oj.get("total")));
                ls.setVoucher(oj.getString("voucherNo"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    
    @FXML 
    private void showNewPayOut(ActionEvent e) throws IOException{
        NewPayOutController controller = new NewPayOutController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newPayOut.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void showEditPayOut(ActionEvent e) throws IOException{
       
        PaySale item = table.getSelectionModel().getSelectedItem();
        NewPayOutController controller = new NewPayOutController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newPayOut.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deletePayOut(){
        PaySale item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteHotelService(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Pay Out Information Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Pay Out Failed to Delete", Pos.CENTER);
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
