package hotels.views.component.fxml.admin.model;

import hotels.util.State;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Olyjosh
 */
public class UserModel {
    
    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleIntegerProperty staffId = new SimpleIntegerProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();

    private final SimpleStringProperty username = new SimpleStringProperty();
    private final SimpleStringProperty phone =new SimpleStringProperty();
    private final SimpleStringProperty email =new SimpleStringProperty();
    private final SimpleBooleanProperty isStaff =new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty priviledge = new SimpleIntegerProperty(State.USER_FRONT);
    private final SimpleStringProperty dob = new SimpleStringProperty();
    private final SimpleStringProperty sex = new SimpleStringProperty();
    private final SimpleStringProperty country =new SimpleStringProperty();
     
    
        
    public void setStaffId(int x) {
        staffId.set(x);
    }
    
    public int getStaffId() {
        return staffId.get();
    }
    
    
    public void setSex(String x) {
        sex.set(x);
    }
    
    public String getSex() {
        return sex.get();
    }
    
       
    public void setCountry(String x) {
        country.set(x);
    }
    
    public String getCountry() {
        return country.get();
    }
    
        
    public void setPriviledge(int x) {
        priviledge.set(x);
    }
    
    public String getPriviledge() {
        return privilege(priviledge.get());
    }
    
    public void setDob(String x){
        dob.set(x);
    }
    
    public String getDob(){
        return dob.get();
    }
    
    
    public void setEmail(String x) {
        email.set(x);
    }
    
    public String getEmail() {
        return email.get();
    }
       
    public void setIsStaff(boolean x) {
        isStaff.set(x);
    }
    
    public boolean isStaff() {
        return isStaff.get();
    }

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
   
    public void setFirstName(String x){
        firstName.set(x);
    }
    
    public String getFistName(){
        return firstName.get();
    }

     public void setLastName(String x){
        lastName.set(x);
    }
    
    public String getLastName(){
        return lastName.get();
    }
    
    
    public void setUsername(String x){
        username.set(x);
    }
    
    public String getUserName(){
        return username.get();
    }
    

    
    public static String privilege(int p){
        switch (p) {
            case State.USER_ADMIN : return "ADMIN";
            case State.USER_ADMIN_2 : return "ADMIN 2";
            case State.USER_SUPER_ADMIN : return "SUPER ADMINISTRATOR";
            case State.USER_BARTENDER : return "BARTENDER";
            case State.USER_FRONT : return "FRONT OFFICE";
            case State.USER_HOUSEKEEP : return "HOUSE KEEPING";
            case State.USER_KITCHEN : return "KITCHEN";
            case State.USER_LAUNDRY : return "LAUNDRY";
            case State.USER_MAID : return "MAID";
            case State.USER_MAINTENANCE : return "MMAINTENANCE";
            case State.USER_MINIBAR : return "MINIBAR";
        }
        return "Unknow";
    }
    
}
