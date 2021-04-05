/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.dtos;

import java.util.Date;

/**
 *
 * @author vuong
 */
public class NoteDTO {
    private int note;
    private int productID;
    private String userID;
    private Date date;
    private String status;

    public NoteDTO() {
    }

    public NoteDTO(int note, int productID, String userID, Date date, String status) {
        this.note = note;
        this.productID = productID;
        this.userID = userID;
        this.date = date;
        this.status = status;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
