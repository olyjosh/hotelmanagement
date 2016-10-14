package hotels.views.component.fxml.restaurant.controller;

import hotels.Hotels;
import hotels.util.Navigator2;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class Sample implements Initializable {

    @FXML private Text data;

    
    private Hotels app;
    private Navigator2 nav;
    private FoodModel parsedData;

    public void setParsedData(FoodModel parsedData) {
        this.parsedData = parsedData;
    }
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public Sample(Hotels app) {
        this.app =app;
        nav = new Navigator2(getApp().getMain());
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        data.setText(parsedData.getName()+" "+parsedData.getDesc());
        System.out.println(parsedData);
    }    
    
    


}
