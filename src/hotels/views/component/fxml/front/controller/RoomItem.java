package hotels.views.component.fxml.front.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author mac
 */
public class RoomItem implements Initializable {

    @FXML private Button roomNo;
    @FXML private Label roomType, roomOccupant;
    private JSONObject jsonData;

    public RoomItem(JSONObject jsonData) {

        this.jsonData = jsonData;
        setDisplay();
    }
    
    
    private void setDisplay(){
        
        try {
            
            roomNo.setText(jsonData.getString("alias"));//guest
            roomType.setText("STANDARD");//roomType
            roomOccupant.setText(jsonData.getJSONObject("guest").getString("firstName")+ " " + jsonData.getJSONObject("guest").getString("lastName"));
//            roomStatus.bookedStatus
        } catch (JSONException ex) {
            Logger.getLogger(RoomItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
