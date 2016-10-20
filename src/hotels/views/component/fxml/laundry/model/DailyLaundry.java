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
    
    private String id;
    private String linen;
    private String date;
    private String user;
    private String status;
    private String returns;
    private String remark;
    
    private String item;
    private String laundryService;
    private String hotelService;
    private String returnDate;
    private String totalBill;
    private String paid;
    private String balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLaundryService() {
        return laundryService;
    }

    public void setLaundryService(String laundryService) {
        this.laundryService = laundryService;
    }

    public String getHotelService() {
        return hotelService;
    }

    public void setHotelService(String hotelService) {
        this.hotelService = hotelService;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "DailyLaundry{" + "id=" + id + ", linen=" + linen + ", date=" + date + ", user=" + user + ", status=" + status + ", returns=" + returns + ", remark=" + remark + ", item=" + item + ", laundryService=" + laundryService + ", hotelService=" + hotelService + ", returnDate=" + returnDate + ", totalBill=" + totalBill + ", paid=" + paid + ", balance=" + balance + '}';
    }
    
}
