/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;

import hotels.Hotels;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author NOVA
 */
public class UITest extends Application{
    
    private static Stage window;
    private Hotels app;
    @Override
    public void start(Stage stage) throws Exception {
        stage = new Stage(StageStyle.UNDECORATED);
        window = stage;
        
        //NewHotelServiceController controller = new NewHotelServiceController();
        //controller.setApp(app);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/tools/newHotelService.fxml"));
        //loader.setController(controller);
        Parent root = (Parent)loader.load();
//        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
