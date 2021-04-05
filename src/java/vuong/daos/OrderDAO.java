/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vuong.dtos.OrderDTO;
import vuong.utils.DBIUtils;

/**
 *
 * @author vuong
 */
public class OrderDAO {

    public String convertDateToString(java.util.Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);
    }
    
    public void createOrder(OrderDTO order) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Insert into tblOrder (OrderID, UserID, PayID, Date, StatusPayment) VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, order.getOrderID());
                stm.setString(2, order.getUserID());
                stm.setString(3, order.getPayID());
                stm.setString(4, convertDateToString(order.getDate()));
                Boolean b = order.isStatusPayment();
                stm.setBoolean(5, order.isStatusPayment());
                stm.executeUpdate();
            }
        } catch (Exception e) {
        }finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public List<OrderDTO> getOrderByUserID(String id, String da) throws SQLException {
        List<OrderDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select OrderID, Date, PayID from tblOrder"
                        + " where UserID = ? and Date like ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, id);
                stm.setString(2, da);
                rs = stm.executeQuery();
                 while (rs.next()) {
                    String OrderID = rs.getString("OrderID");
                    Date date = rs.getDate("Date");
                    String PayID = rs.getString("PayID");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new OrderDTO(OrderID, id, PayID, date, true));
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
    
    public Date getDateByID(String id) throws SQLException {
        Date result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select Date from tblOrder"
                        + " where OrderID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    Date date = rs.getDate("Date");
                    result = date;
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
}
