package hotels;

import hotels.controllers.FXMLDocumentController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import hotels.controllers.Login;
import hotels.controllers.RegStaff;
import hotels.model.MongoDBInc;
import hotels.model.user.ClientM;

/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
public class Hotels extends Application {
    
    private Stage stage;
    private MongoDBInc m;
    private ClientM staff;
    
    @Override
    public void start(Stage stage) throws IOException  {
        dbaseConnection();
        this.stage=stage;
        gotoLogin();
        //stage.setFullScreen(true);
        stage.show();
    }    

    public void startHomePage() throws IOException{
        this.stage.close();
        this.stage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+"FXMLDocument.fxml"));
        Parent root = (Parent)loader.load();
        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        controller.setApp(this);
        controller.setStaff(getStaff());
        this.stage.setScene(new Scene(root));
        this.stage.setFullScreen(true);
        this.stage.show();
    }
    
    
    public void dbaseConnection(){
        m=new MongoDBInc();
    }
    
    
    public Stage getStage() {
        return stage;
    }
    
    public MongoDBInc getDbInstance(){
        return m;
    }

    public ClientM getStaff() {
        return staff;
    }

    public void setStaff(ClientM staff) {
        this.staff = staff;
    }
    
    
    
    public static Scene loadFxml(String path){
        try {
            return new Scene(FXMLLoader.load(Hotels.class.getResource("views/"+path)));
        } catch (IOException ex) {
            Logger.getLogger(Hotels.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//    public void loadRegister(){
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cbt/views/Reg.fxml"));
//            Parent root = (Parent)fxmlLoader.load();
//            RegController c = fxmlLoader.<RegController>getController();
//            c.setApp(this);
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//        } catch (IOException ex) {
//            Logger.getLogger(CBTFace.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    public Scene loaderMethod(String path) throws IOException{
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+path));
//        Parent root = (Parent)loader.load();
//        Login controller = (Login)loader.getController();
//        controller.setApp(this);
//        return new Scene(root);
//    }
    
    
    
    public void gotoLogin() throws IOException{
        Login controller = new Login();
        controller.setApp(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+"Login.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        stage.setScene(new Scene(root));
    }
    
    public void gotoRegister() throws IOException{
        RegStaff controller = new RegStaff();
        controller.setApp(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/"+"RegStaff.fxml"));
        loader.setController(controller);
        Parent root = (Parent)loader.load();
        stage.setScene(new Scene(root));

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
}