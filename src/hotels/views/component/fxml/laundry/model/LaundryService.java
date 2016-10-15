/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.laundry.model;

/**
 *
 * @author NOVA
 */
public class LaundryService {
    
    private String name;
    private String charge;
    private String desc;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "LaundryService{" + "name=" + name + ", charge=" + charge + ", desc=" + desc + '}';
    }
    
    
    
}
