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
import java.text.SimpleDateFormat;
import java.util.Date;
import vuong.dtos.UserDTO;
import vuong.utils.DBIUtils;

/**
 *
 * @author vuong
 */
public class UserDAO {

    public String convertDateToString(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);
    }

    public UserDTO checkLorgin(String userID, String password) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select UserName, RoleID, Email from tblUser "
                        + " where userID=? and password=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String userName = rs.getString("UserName");
                    String roleID = rs.getString("RoleID");
                    String email = rs.getString("Email");
                    result = new UserDTO(userID, userName, "", "", email, "", null, null, "", roleID);
                }
            }
        } catch (Exception e) {
        }finally {
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
    
    public UserDTO checkLorgin(String userID) throws SQLException {
        UserDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select UserName, RoleID, Email from tblUser "
                        + " where userID=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String userName = rs.getString("Email");
                    String roleID = rs.getString("RoleID");
                    result = new UserDTO(userID, userName, "", "", "", "", null, null, "", roleID);
                }
            }
        } catch (Exception e) {
        }finally {
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

    public void createUser(UserDTO dto) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Insert into tblUser (userID, userName, email, dateOfCreate, roleID)"
                        + " VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getUserID());
                stm.setString(2, dto.getUserName());
                stm.setString(3, dto.getEmail());
                Date date = new Date();
                stm.setString(4, convertDateToString(date));
                stm.setString(5, "US");
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
}
