/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.dtos;

/**
 *
 * @author vuong
 */
public class ProductErrorDTO {
    private String typeIDError;
    private String productNameError;
    private String priceError;
    private String describeError;
    private String imageError;
    private String quantityError;

    public ProductErrorDTO() {
    }

    public ProductErrorDTO(String typeIDError, String productNameError, String priceError, String describeError, String imageError, String quantityError) {
        this.typeIDError = typeIDError;
        this.productNameError = productNameError;
        this.priceError = priceError;
        this.describeError = describeError;
        this.imageError = imageError;
        this.quantityError = quantityError;
    }

    public String getTypeIDError() {
        return typeIDError;
    }

    public void setTypeIDError(String typeIDError) {
        this.typeIDError = typeIDError;
    }

    public String getProductNameError() {
        return productNameError;
    }

    public void setProductNameError(String productNameError) {
        this.productNameError = productNameError;
    }

    public String getDescribeError() {
        return describeError;
    }

    public void setDescribeError(String describeError) {
        this.describeError = describeError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }
    
    
}
