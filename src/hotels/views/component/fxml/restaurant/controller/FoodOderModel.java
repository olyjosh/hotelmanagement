package hotels.views.component.fxml.restaurant.controller;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Olyjosh
 */
public class FoodOderModel {
    
        
    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty guestName = new SimpleStringProperty("");
    private final SimpleStringProperty phone = new SimpleStringProperty("");
    private final SimpleStringProperty orderId = new SimpleStringProperty("");
    private final SimpleStringProperty amount =new SimpleStringProperty("");
    private final SimpleStringProperty isPaid = new SimpleStringProperty("");
    private final SimpleStringProperty status = new SimpleStringProperty("");
    private final SimpleStringProperty room = new SimpleStringProperty("");
    private JSONArray array; 

        
    private void setId(String x) {
        id.set(x);
    }

    private String getId() {
        return id.get();
    }
   
    
    private void setGuestName(String x){
        guestName.set(x);
    }
    
    private String getGuestName(){
        return guestName.get();
    }
    
    private void setPhone(String x){
        phone.set(x);
    }
    
    private String getPhone(){
        return phone.get();
    }
    
    private void setOrderId(String x){
        orderId.set(x);
    }
    
    private String getorderId(){
        return orderId.get();
    }
    
    private void setAmount(String x){
        amount.set(x);
    }
    
    private String getAmount(){
        return amount.get();
    }
    
    private void setIsPaid(String x){
        isPaid.set(x);
    }
    
    private String getIsPaid(){
        return isPaid.get();
    }
 
    private void setStatus(String x) {
        status.set(x);
    }

    private String getStatus() {
        return status.get();
    }

     
    private void setRoom(String x) {
        room.set(x);
    }

    private String getRoom() {
        return room.get();
    }

    
    
    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }
    
    
    
}
