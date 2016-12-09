package hotels.views.component.fxml.admin.model;

import java.util.List;

/**
 *
 * @author NOVA
 */
public class RoomType {
    
    private String id;
    private String alias;
    private String name;
    private String desc;
    private double rate,adultRate, childRate;
    private int overBooking;
    private int ba,ma,bc,mc;
    private List<String> images;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getAdultRate() {
        return adultRate;
    }

    public void setAdultRate(double adultRate) {
        this.adultRate = adultRate;
    }

    public double getChildRate() {
        return childRate;
    }

    public void setChildRate(double childRate) {
        this.childRate = childRate;
    }

    public int getOverBooking() {
        return overBooking;
    }

    public void setOverBooking(int overBooking) {
        this.overBooking = overBooking;
    }

    public int getBa() {
        return ba;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public int getBc() {
        return bc;
    }

    public void setBc(int bc) {
        this.bc = bc;
    }

    public int getMc() {
        return mc;
    }

    public void setMc(int mc) {
        this.mc = mc;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
    

    @Override
    public String toString() {
        return alias+" ("+name+")";
    }
    
    
}
