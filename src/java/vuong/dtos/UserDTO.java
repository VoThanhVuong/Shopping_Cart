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
public class UserDTO {
    private String userID;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String address;
    private Date birthday;
    private Date dateOfCreate;
    private String numberIDCard;
    private String roleID;

    public UserDTO() {
    }

    public UserDTO(String userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    
    
    public UserDTO(String userID, String userName, String password, String phone, String email, String address, Date birthday, Date dateOfCreate, String numberIDCard, String roleID) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.dateOfCreate = dateOfCreate;
        this.numberIDCard = numberIDCard;
        this.roleID = roleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getNumberIDCard() {
        return numberIDCard;
    }

    public void setNumberIDCard(String numberIDCard) {
        this.numberIDCard = numberIDCard;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    
}
