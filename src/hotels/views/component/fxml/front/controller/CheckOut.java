package hotels.views.component.fxml.front.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author mac
 */


public class CheckOut implements Initializable {

    @FXML private Label name, room,roomType,arrival,departure,rate;
    @FXML private Button checkOut,cancel,retry;
    @FXML private Hyperlink print;
    @FXML private HBox progressHbox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        retry.setVisible(false);
    }    
    
}
