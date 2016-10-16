package hotels.views.component.fxml.bar.controller;

import hotels.views.component.fxml.restaurant.controller.*;
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
public class NewOrder implements Initializable {

    @FXML private Text data;

    
    private Hotels app;
    private Navigator2 nav;
    private DrinkModel parsedData;

    public void setParsedData(DrinkModel parsedData) {
        this.parsedData = parsedData;
    }
    
    public Hotels getApp() {
        return app;
    }

    public void setApp(Hotels app) {
        this.app = app;
    }

    public NewOrder(Hotels app) {
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
