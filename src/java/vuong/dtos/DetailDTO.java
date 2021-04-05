/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.dtos;

import java.sql.SQLException;
import java.util.Date;
import vuong.daos.OrderDAO;
import vuong.daos.ProductDAO;

/**
 *
 * @author vuong
 */
public class DetailDTO {

    private int detailID;
    private String orderID;
    private int productID;
    private int quantity;

    public DetailDTO() {
    }

    public DetailDTO(int productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }
    
    public DetailDTO(int detailID, String orderID, int productID, int quantity) {
        this.detailID = detailID;
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Date getDate() throws SQLException{
        OrderDAO dao = new OrderDAO();
        return dao.getDateByID(orderID);
    }
    
    public float getIntoMoney() throws SQLException{
        ProductDAO dao = new ProductDAO();
        ProductDTO dto = dao.checkProduct(productID, quantity);
        return dto.getPrice()*quantity;
    }
    
    public ProductDTO getProduct(int id)throws SQLException{
        ProductDAO dao = new ProductDAO();
        ProductDTO dto = dao.getProductByID(id);
        return dto;
    }
}
