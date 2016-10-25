/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotels.views.component.fxml.tools.model;

/**
 *
 * @author NOVA
 */
public class PaySale {
    
    private String id;
    private String voucher;
    private String paidTo;
    private String cat;
    private String charge;
    private String roomNo;
    private String remark;
    private String amount;
    private String discount;
    private String tax;
    private String qty;
    private String adj;
    private String amountPaid;
    private String total;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAdj() {
        return adj;
    }

    public void setAdj(String adj) {
        this.adj = adj;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaySale{" + "id=" + id + ", voucher=" + voucher + ", paidTo=" + paidTo + ", cat=" + cat + ", charge=" + charge + ", roomNo=" + roomNo + ", remark=" + remark + ", amount=" + amount + ", discount=" + discount + ", tax=" + tax + ", qty=" + qty + ", adj=" + adj + ", amountPaid=" + amountPaid + ", total=" + total + ", date=" + date + '}';
    }
    
}
