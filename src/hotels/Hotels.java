package hotels;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import hotels.controllers.Login;
import hotels.controllers.Main;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
public class Hotels extends Application {
    
    private Stage stage;
    private Main m;
    public ExecutorService e = Executors.newFixedThreadPool(3);
    
    
    @Override
    public void start(Stage stage) throws IOException  {
        this.stage=stage;
        gotoLogin();
        //stage.setFullScreen(true);
        stage.show();
    }    

    public void startHomePage() throws IOException{
        this.stage.close();
        this.stage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/front/guestMessage.fxml"));
        Parent root = (Parent)loader.load();
//        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
//        controller.setApp(this);
//        controller.setStaff(getStaff());
        this.stage.setScene(new Scene(root));
        this.stage.setFullScreen(true);
        this.stage.show();
    }

    
    
    public Stage getStage() {
        return stage;
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

        Main m = new Main();
        m.setApp(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/Main.fxml"));
        loader.setController(m);
        //Main m =(Main)loader.getController();
        
        //setMain(m);
        //loader.setController(controller);  // Joshua this is use when u need to specify controller by your self. You must in this case remove the controller from fml file
        Parent root = (Parent)loader.load();
        System.out.println(stage);
        stage.setScene(new Scene(root));
    }

    public void setMain(Main m) {
        this.m = m;
    }

    public Main getMain() {
        return m;
    }
    
  
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
 
}