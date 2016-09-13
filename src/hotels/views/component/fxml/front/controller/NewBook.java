package hotels.views.component.fxml.front.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class NewBook implements Initializable {

    /**
     * Initializes the controller class.
     */
    private DatePicker checkIn;
    private TextField firstName,
            lastName;
    private ChoiceBox suitType;
            
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
