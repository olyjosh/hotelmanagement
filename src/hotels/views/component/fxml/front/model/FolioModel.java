package hotels.views.component.fxml.front.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Olyjosh
 */
public class FolioModel {
    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleDoubleProperty balance = new SimpleDoubleProperty(0);
    private final SimpleStringProperty guestId = new SimpleStringProperty("");
    private final SimpleStringProperty phone =new SimpleStringProperty("");

    public void setPhone(String x) {
        phone.set(x);
    }
    
    public String getPhone() {
        return phone.get();
    }
   

    public void setId(String x) {
        id.set(x);
    }

    public String getId() {
        return id.get();
    }
   
    public void setName(String x){
        name.set(x);
    }
    
    public String getName(){
        return name.get();
    }

    public void setGuestId(String x){
        guestId.set(x);
    }
    
    public String getGuestId(){
        return guestId.get();
    }
    
    public double getBalance(){
        return balance.get();
    }
    
    public void setBalance(double x){
        balance.set(x);
    }
    
}
