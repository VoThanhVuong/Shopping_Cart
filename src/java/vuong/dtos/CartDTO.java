/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.dtos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vuong
 */
public class CartDTO {

    String key;
    Map<String, DetailDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String key, Map<String, DetailDTO> cart) {
        this.key = key;
        this.cart = cart;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, DetailDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, DetailDTO> cart) {
        this.cart = cart;
    }

    public void add(DetailDTO dto) {
        if (cart == null) {
            this.cart = new HashMap<String, DetailDTO>();
        }

        cart.put(dto.getProductID()+"", dto);
    }
    public void addHis(DetailDTO dto) {
        if (cart == null) {
            this.cart = new HashMap<String, DetailDTO>();
        }

        cart.put(Integer.toString(dto.getDetailID()), dto);
    }

    public void delete(String id) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }

    public void update(DetailDTO dto) {
        if (this.cart != null) {
            if (this.cart.containsKey(dto.getProductID()+"")) {
                this.cart.replace(dto.getProductID()+"", dto);
            }
        }
    }
}
