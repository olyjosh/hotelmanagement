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

        
    public void setId(String x) {
        id.set(x);
    }

    public String getId() {
        return id.get();
    }
   
    
    public void setGuestName(String x){
        guestName.set(x);
    }
    
    public String getGuestName(){
        return guestName.get();
    }
    
    public void setPhone(String x){
        phone.set(x);
    }
    
    public String getPhone(){
        return phone.get();
    }
    
    public void setOrderId(String x){
        orderId.set(x);
    }
    
    public String getorderId(){
        return orderId.get();
    }
    
    public void setAmount(String x){
        amount.set(x);
    }
    
    public String getAmount(){
        return amount.get();
    }
    
    public void setIsPaid(String x){
        isPaid.set(x);
    }
    
    public String getIsPaid(){
        return isPaid.get();
    }
 
    public void setStatus(String x) {
        status.set(x);
    }

    public String getStatus() {
        return status.get();
    }

     
    public void setRoom(String x) {
        room.set(x);
    }

    public String getRoom() {
        return room.get();
    }

    
    
    public JSONArray getOrdersArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }
    
    
    
}
