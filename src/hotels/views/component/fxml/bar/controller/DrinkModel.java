package hotels.views.component.fxml.bar.controller;

import hotels.views.component.fxml.restaurant.controller.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Olyjosh
 */
public class DrinkModel {
    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty desc = new SimpleStringProperty("");
    private final SimpleStringProperty price = new SimpleStringProperty("");
    private final SimpleStringProperty image =new SimpleStringProperty("");
    private final SimpleBooleanProperty avail =new SimpleBooleanProperty(true);

    public void setAvail(boolean x) {
        avail.set(x);
    }

    public String getAvail() {
        return id.get();
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
    
    public void setDesc(String x){
        desc.set(x);
    }
    
    public String getDesc(){
        return desc.get();
    }
    
    public void setPrice(String x){
        price.set(x);
    }
    
    public String getPrice(){
        return price.get();
    }
    
    public void setImage(String x){
        image.set(x);
    }
    
    public String getImage(){
        return image.get();
    }
    

    
}
