package hotels.views.component.fxml.laundry.controller;

import hotels.views.component.fxml.housekeep.controller.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class LaundrySettings implements Initializable {

    @FXML private TextArea foComment, hkComment,
            lastServiceRequest,remarks;
    @FXML private Label lastVisitedDate,lastMaid,serviceStatus,nextOn,outOfOrderReason,nextReservation;
    @FXML private TableView table; 
    @FXML private ChoiceBox floor,suiteType,room,roomStatus, maid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
