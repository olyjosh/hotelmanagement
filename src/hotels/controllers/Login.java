package hotels.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Region;
import hotels.Hotels;
import hotels.views.component.WindowTop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.controlsfx.control.NotificationPane;

/**
 *
 * @author olyjosh
 */
public class Login implements Initializable{

    @FXML private TextField id;
    @FXML private PasswordField pass;
    @FXML private ToolBar toolBar;
    @FXML private Region spacer1;
    private Hotels app;
    
    public Hotels getApp() {
        return app;
    }


    public void setApp(Hotels app){
        this.app = app;
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Stage s=(Stage)toolBar.getScene().getWindow();
        WindowTop win = new WindowTop(
                app.getStage(),
                toolBar, false, true,spacer1);
    }
    


    @FXML
    private void login(ActionEvent e) {
        
//        MongoDBInc m = app.getDbInstance();
//        ClientM login = m.login(id.getText(), pass.getText());
//        if (login!=null) {
//            try {
//                app.setStaff(login);
//                app.startHomePage();
//            } catch (IOException ex) {
//                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }else{
//            //Invalid login reporting here
//            System.out.println("Wrong password");
//            notifica();
//        }
    }
    
    private void notifica(){
       // NotificationPane ntp = new NotificationPane();
//        toolBar.getItems().add(ntp);
//        ntp.showFromTopProperty().setValue(true);
//        ntp.show("Invalid login detail");
//        ntp.show();
        
    }
    
}