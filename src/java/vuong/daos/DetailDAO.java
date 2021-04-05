/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vuong.dtos.DetailDTO;
import vuong.utils.DBIUtils;


/**
 *
 * @author vuong
 */
public class DetailDAO {

    public void createDetail(DetailDTO dto) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Insert into tblOrderDetail (OrderID, ProductID, Quantity) VALUES(?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getOrderID());
                stm.setInt(2, dto.getProductID());
                stm.setInt(3, dto.getQuantity());
                stm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    public List<DetailDTO> getListDetailByOrderID(String orderID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<DetailDTO> result = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select DetailID, OrderID, ProductID, Quantity from tblOrderDetail where OrderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    int detailID = rs.getInt("DetailID");
                    int productID = rs.getInt("ProductID");
                    int quantity = rs.getInt("Quantity");
                    result.add(new DetailDTO(detailID, orderID, productID, quantity));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    public List<DetailDTO> searchHistory(String name, String userID, String date) throws SQLException{
        List<DetailDTO> result = null;
        Connection conn = null; 
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if(conn!=null){
                String sql = "select DetailID, OrderID, ProductID, Quantity from tblOrderDetail where ProductID in "
                        + "(select ProductID from tblProduct where ProductName like ? ) and OrderID in "
                        + "(select OrderID from tblOrder where UserID = ? and Date like ?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%"+name+"%");
                stm.setString(2, userID);
                stm.setString(3, date);
                rs = stm.executeQuery();
                while(rs.next()){
                    if(result==null){
                        result = new ArrayList<>();
                    }
                    int detailID = rs.getInt("DetailID");
                    String orderID = rs.getString("OrderID");
                    int productID = rs.getInt("ProductID");
                    int quantity = rs.getInt("Quantity");
                    result.add(new DetailDTO(detailID, orderID, productID, quantity));
                }
            }
        } catch (Exception e) {
        } finally{
            if(rs!=null){
                rs.close();
            }
            if(stm!=null){
                stm.close();
            }
            if(conn!=null){
                conn.close();
            }
        }
        return result;
    }
    public String getOrderIDByProductID(String productID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select top 1 OrderID from tblOrderDetail where ProductID = ? Order by OrderID DESC";
                stm = conn.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if(rs.next()){
                    String orderID = rs.getString("OrderID");
                    result = orderID;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
