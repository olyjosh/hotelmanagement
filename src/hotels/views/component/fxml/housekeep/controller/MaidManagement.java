package hotels.views.component.fxml.housekeep.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class MaidManagement implements Initializable {

    @FXML private ListView maidList;
    @FXML private TableView recentMaidTaskTable;
    @FXML private Label firstName, lastName, email,phone;
    @FXML private TextArea comment;
    @FXML private VBox commentPane;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
