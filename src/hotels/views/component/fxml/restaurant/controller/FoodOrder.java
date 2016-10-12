package hotels.views.component.fxml.restaurant.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

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
            
            
}
