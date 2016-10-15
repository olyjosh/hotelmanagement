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
public class DailyLaundry {
    
    private String linen;
    private String date;
    private String user;
    private String status;
    private String returns;
    private String remark;

    public String getLinen() {
        return linen;
    }

    public void setLinen(String linen) {
        this.linen = linen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "DailyLaundry{" + "linen=" + linen + ", date=" + date + ", user=" + user + ", status=" + status + ", returns=" + returns + ", remark=" + remark + '}';
    }
    
    
}
