/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.front.model;

/**
 *
 * @author NOVA
 */
public class Reserve {
    
    private String _id;
    private String updatedAt;
    private String createdAt;
    private String checkOut;
    private String checkIn;
    private int amount;
    private String performedBy;
    private String channel;
    private String room;
    private String status;
    private String arrival;
    private boolean isCancel;
    private boolean isCheckIn;
    private String v;
    private String phone;
    private String lastName;
    private String firstName;
    private String guestName;

    public String getGuestName() {
        return guestName;// = getFirstName() + "  " + getLastName();
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
    

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public boolean isIsCancel() {
        return isCancel;
    }

    public void setIsCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public boolean isIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(boolean isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
    
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reserve{" + "_id=" + _id + ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + ", checkOut=" + checkOut + ", checkIn=" + checkIn + ", amount=" + amount + ", performedBy=" + performedBy + ", channel=" + channel + ", room=" + room + ", status=" + status + ", arrival=" + arrival + ", isCancel=" + isCancel + ", isCheckIn=" + isCheckIn + ", v=" + v + ", phone=" + phone + ", lastName=" + lastName + ", firstName=" + firstName + ", guestName=" + guestName + '}';
    }
    
    
}
