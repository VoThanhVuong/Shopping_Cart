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
public class ProductDTO {
    private int productID;
    private String typeID;
    private String productName;
    private float price;
    private Date date;
    private String describe;
    private String image;
    private int quantity;
    private boolean availability;

    public ProductDTO() {
    }

    public ProductDTO(String typeID, String productName, float price) {
        this.typeID = typeID;
        this.productName = productName;
        this.price = price;
    }

    public ProductDTO(String typeID, String productName, float price, Date date, String describe, String image, int quantity) {
        this.typeID = typeID;
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.describe = describe;
        this.image = image;
        this.quantity = quantity;
    }
    
    
    public ProductDTO(int productID, String typeID, String productName, float price, Date date, String describe, String image, int quantity, boolean availability) {
        this.productID = productID;
        this.typeID = typeID;
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.describe = describe;
        this.image = image;
        this.quantity = quantity;
        this.availability = availability;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

}
