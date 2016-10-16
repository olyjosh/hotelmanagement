package hotels.views.component.fxml.bar.controller;

import hotels.views.component.fxml.restaurant.controller.*;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class OnlineOderModel {
    
    private final SimpleStringProperty guestName = new SimpleStringProperty("");
    private final SimpleStringProperty phone = new SimpleStringProperty("");
    private final SimpleStringProperty orderId = new SimpleStringProperty("");
    private final SimpleStringProperty amount =new SimpleStringProperty("");
    private final SimpleStringProperty isPaid = new SimpleStringProperty("");
    private final SimpleStringProperty status = new SimpleStringProperty("");
    private final SimpleStringProperty room = new SimpleStringProperty("");
    private JSONArray array; 

    private void setGuestName(String x){
    
    }
    
    
    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }
    
    
    
}
