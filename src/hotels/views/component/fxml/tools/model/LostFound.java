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
public class LostFound {
    
    private String entryDate;
    private String itemName;
    private String whereLost;
    private String itemColour;
    private String roomNo;
    private String returnDate;
    private String returnBy;
    private String discardDate;
    private String discardBy;
    private String id;

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWhereLost() {
        return whereLost;
    }

    public void setWhereLost(String whereLost) {
        this.whereLost = whereLost;
    }

    public String getItemColour() {
        return itemColour;
    }

    public void setItemColour(String itemColour) {
        this.itemColour = itemColour;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnBy() {
        return returnBy;
    }

    public void setReturnBy(String returnBy) {
        this.returnBy = returnBy;
    }

    public String getDiscardDate() {
        return discardDate;
    }

    public void setDiscardDate(String discardDate) {
        this.discardDate = discardDate;
    }

    public String getDiscardBy() {
        return discardBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public void setDiscardBy(String discardBy) {
        this.discardBy = discardBy;
    }

    @Override
    public String toString() {
        return "LostFound{" + "entryDate=" + entryDate + ", itemName=" + itemName + ", whereLost=" + whereLost + ", itemColour=" + itemColour + ", roomNo=" + roomNo + ", returnDate=" + returnDate + ", returnBy=" + returnBy + ", discardDate=" + discardDate + ", discardBy=" + discardBy + ", id=" + id + '}';
    }

   
}
