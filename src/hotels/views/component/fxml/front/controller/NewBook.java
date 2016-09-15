package hotels.views.component.fxml.front.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
    @FXML private DatePicker checkIn;
    @FXML private TextField firstName,lastName,phone,email,address;
    @FXML private ChoiceBox suitType;
            
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
