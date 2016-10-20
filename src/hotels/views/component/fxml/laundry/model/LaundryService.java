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
    
    private String id;
    private String alias;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "LaundryService{" + "id=" + id + ", alias=" + alias + ", name=" + name + ", charge=" + charge + ", desc=" + desc + '}';
    }
    
}
