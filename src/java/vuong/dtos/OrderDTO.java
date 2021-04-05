/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.dtos;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import vuong.daos.DetailDAO;

/**
 *
 * @author vuong
 */
public class OrderDTO {
    private String orderID;
    private String userID;
    private String payID;
    private Date date;
    private boolean statusPayment;

    public OrderDTO() {
    }
   
    public OrderDTO(String orderID, String userID, String payID, Date date, boolean statusPayment) {
        this.orderID = orderID;
        this.userID = userID;
        this.payID = payID;
        this.date = date;
        this.statusPayment = statusPayment;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(boolean statusPayment) {
        this.statusPayment = statusPayment;
    }

    public float getTotalPrice() throws SQLException {
        DetailDAO dao = new DetailDAO();
        List<DetailDTO> list = dao.getListDetailByOrderID(orderID);
        float total=0;
        for(DetailDTO dto : list){
            total+=dto.getIntoMoney();
        }
        return  total;
    }

}
