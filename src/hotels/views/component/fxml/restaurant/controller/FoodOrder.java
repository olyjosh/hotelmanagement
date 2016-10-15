package hotels.views.component.fxml.restaurant.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class FoodOrder implements Initializable {

    @FXML
    private GridPane foodlist;
   
    private Hotels app;
    private Navigator2 nav;
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public FoodOrder(Hotels app) {
        this.app =app;
        nav = new Navigator2(getApp().getMain());
        
    }
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        defaults();
    }    
    
    
    private void defaults(){
//        new GridPane();
//        for (int i = 1; i <= 5; i++) {
//            foodlist.add(new Label("item"), 0, i);
//            foodlist.add(new Label("item"), 1, i);
//            foodlist.add(new Label("item"), 2, i);
//        }
    }
      
    @FXML private void showSample() throws IOException{
        // Retrieve the seleted row that you want to pass to the new controller for editing
        FoodModel selectedItem = null ;
//                table.getSelectionModel().getSelectedItem();
        if (selectedItem!=null) {
            
            NewOrder controller = new NewOrder(this.getApp());
            controller.setApp(app);
//            controller.setParsedData(selectedItem);  // the additional line for parsing data to new controller. 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hotels/views/component/fxml/restaurant/newOrder.fxml"));
            loader.setController(controller);
            AnchorPane content = (AnchorPane) loader.load();
            Scene sc = new Scene(content);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
        }else {
            System.out.println("NO SELECTED ITEM");
        }

    }
    
    
}
