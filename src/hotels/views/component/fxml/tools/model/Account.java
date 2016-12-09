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
public class Account {
    
    private String id;
    private String companyName;
    private String accountName;
    private String firstName;
    private String lastName;
    private String phone;
    private String add1;
    private String add2;
    private String city;
    private String zip;
    private String state;
    private String country;
    private String email;
    private String web;
    private String rep;
    private String contact;
//    private String accountNo;

    private String credit;
    private String balance;
    private String term;
    private String discount;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
//
//    public String getAccountNo() {
//        return accountNo;
//    }
//
//    public void setAccountNo(String accountNo) {
//        this.accountNo = accountNo;
//    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    
    
    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", companyName=" + companyName + ", accountName=" + accountName + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + ", add1=" + add1 + ", add2=" + add2 + ", city=" + city + ", zip=" + zip + ", state=" + state + ", country=" + country + ", email=" + email + ", web=" + web + ", rep=" + rep + ", contact=" + contact + ", credit=" + credit + ", balance=" + balance + ", term=" + term + '}';
    }
    
}
