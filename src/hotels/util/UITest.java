/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.util;

import hotels.Hotels;
import hotels.controllers.Main;
import hotels.views.component.fxml.laundry.NewDailyController;
import hotels.views.component.fxml.tools.NewHotelServiceController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;

/**
 *
 * @author NOVA
 */
public class UITest {
//    
//  
//    @Override
//    public void start(Stage stage) throws Exception {
//          
//        //NewHotelServiceController controller = new NewHotelServiceController();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("hotels/views/component/fxml/tools/newHotelService.fxml"));
//       //loader.setController(controller);
//        Parent root = (Parent)loader.load();
//        stage = new Stage(StageStyle.UNIFIED);
//        stage.setScene(new Scene(root));
//        
//        stage.showAndWait();
//    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        //launch(args);
        Navigator nav = new Navigator(Main.class.newInstance());
        JSONObject res = nav.fetchRoomType();
        System.out.println(res);
    }
}
