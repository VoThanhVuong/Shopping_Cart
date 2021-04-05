/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import vuong.dtos.NoteDTO;
import vuong.utils.DBIUtils;

/**
 *
 * @author vuong
 */
public class NoteDAO {
    public String convertDateToString(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);
    }
    public void createNote(NoteDTO dto) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Insert into tblNote (ProductID, UserID, Date, Status)"
                        + " VALUES(?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, dto.getProductID());
                stm.setString(2, dto.getUserID());
                stm.setString(3, convertDateToString(dto.getDate()));
                stm.setString(4, dto.getStatus());
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
