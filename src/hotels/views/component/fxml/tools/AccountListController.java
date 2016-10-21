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
import hotels.views.component.fxml.tools.model.Account;
import hotels.views.component.fxml.tools.model.HotelService;
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
import javafx.scene.control.TextField;
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
public class AccountListController implements Initializable {

    
            
    
    @FXML
    private TextField companyTxt;
    @FXML
    private TextField contactTxt;
    @FXML
    private TableColumn company;
    @FXML
    private TableColumn accountName;
    @FXML
    private TableColumn contact;
    @FXML
    private TableColumn email;
    @FXML
    private TableColumn credit;
    @FXML
    private TableColumn balance;
    @FXML
    private TableView<Account> table;
    
    
    private Hotels app;
    private Navigator nav;
    private static JSONObject account;
    private static JSONArray accountArray;
    private Account ls;
    private ObservableList<Account> service = FXCollections.observableArrayList();
    
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public AccountListController(Hotels app) {
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
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("servive", "hotel"));
                    account = nav.fetchHotelService(param);
                    accountArray = account.getJSONArray("message");
                    
                    getLostList();
                    
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
           
            
            company.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            accountName.setCellValueFactory(new PropertyValueFactory<>("accountName"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            
            table.getColumns().setAll(company, accountName, contact, email, credit, balance);
       
    }
    
    private void getLostList(){
        try {
            for(int i = 0; i < accountArray.length(); i++){
                ls = new Account();
                JSONObject oj = accountArray.getJSONObject(i);
                System.out.println("Printing Hotel Service : " + oj);
                ls.setId(oj.getString("_id"));
                ls.setAccountName(oj.getString("accountName"));
                ls.setAccountNo(oj.getString("cred_accountNo"));
                ls.setAdd1(oj.getString("add1"));
                ls.setAdd2(oj.getString("add2"));
                ls.setBalance(oj.getString("cred_openBalance"));
                ls.setCity(oj.getString("city"));
                ls.setCompanyName(oj.getString("alis"));
                ls.setContact(oj.getString("contact"));
                ls.setCountry(oj.getString("country"));
                ls.setCredit(oj.getString("cred_creditLimit"));
                ls.setEmail(oj.getString("email"));
                ls.setFirstName(oj.getString("firstName"));
                ls.setLastName(oj.getString("lastName"));
                ls.setPhone(oj.getString("phone"));
                ls.setRep(oj.getString("rep"));
                ls.setState(oj.getString("state"));
                ls.setTerm(oj.getString("cred_paymentTerm"));
                ls.setWeb(oj.getString("website"));
                ls.setZip(oj.getString("zip"));
                
                service.addAll(ls);
                table.setItems(service);
                
            }
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void showNewAccount(ActionEvent e) throws IOException{
        NewAccountController controller = new NewAccountController(this.getApp());
        controller.setApp(getApp());
        controller.setEditMode(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newAccount.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        
        stage.showAndWait();
    }
    
    @FXML 
    private void editAccount(ActionEvent e) throws IOException{
       
        Account item = table.getSelectionModel().getSelectedItem();
        NewAccountController controller = new NewAccountController(this.getApp());
        controller.setApp(app);
        controller.setEditMode(true);
        controller.setData(item);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newAccount.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));

        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteAccount(){
        Account item = table.getSelectionModel().getSelectedItem();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    List <NameValuePair> param = new ArrayList<>();
                    param.add(new BasicNameValuePair("id", item.getId()));
                    JSONObject response = nav.deleteAccount(param);
                    
                    if(response.getInt("status") == 1){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_SUCCESS, "Account Information Deleted", Pos.CENTER);
                                service.clear();
                                onLoad();
                            }
                        });
                    }else{
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                Util.notify(State.NOTIFY_ERROR, "Account Failed to Delete", Pos.CENTER);
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
