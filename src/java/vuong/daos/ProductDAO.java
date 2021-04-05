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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import vuong.dtos.ProductDTO;
import vuong.utils.DBIUtils;

/**
 *
 * @author vuong
 */
public class ProductDAO {

    public String convertDateToString(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d);
    }

    public List<ProductDTO> getProductAvailability(int page) throws SQLException {
        List<ProductDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT  ProductID, ProductName, TypeID, Price, Describe, Image, Quantity "
                        + "FROM(SELECT ROW_NUMBER() OVER (ORDER BY Date) AS RowNum, ProductID, ProductName, TypeID, Price, Describe, Image, Quantity "
                        + "FROM tblProduct WHERE Availability = 'true' and Quantity > 0 ) AS RowConstrainedResult "
                        + "WHERE RowNum >= ? and RowNum <= ? ORDER BY RowNum";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, (page - 1) * 3 + 1);
                stm.setInt(2, page * 3);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("ProductId");
                    String productName = rs.getString("ProductName");
                    String typeID = rs.getString("TypeID");
                    float price = Float.parseFloat(rs.getString("Price"));
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ProductDTO(productId, typeID, productName, price, null, describe, image, quantity, true));
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

    public List<ProductDTO> getProductAvailability(ProductDTO dto, int page) throws SQLException {
        List<ProductDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT  ProductID, ProductName, TypeID, Price, Describe, Image, Quantity "
                        + "FROM(SELECT ROW_NUMBER() OVER (ORDER BY Date) AS RowNum, ProductID, ProductName, TypeID, Price, Describe, Image, Quantity "
                        + "FROM tblProduct WHERE Availability = 'true' and Quantity > 0 and ProductName like ? and TypeID like ? and Price <= ?) AS RowConstrainedResult "
                        + "WHERE RowNum >= ? and RowNum <= ? ORDER BY RowNum";
                stm = conn.prepareStatement(sql);
                stm.setString(1, '%' + dto.getProductName() + '%');
                stm.setString(2, dto.getTypeID());
                stm.setFloat(3, dto.getPrice());
                stm.setInt(4, (page - 1) * 3 + 1);
                stm.setInt(5, page * 3);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("ProductId");
                    String productName = rs.getString("ProductName");
                    String typeID = rs.getString("TypeID");
                    float price = Float.parseFloat(rs.getString("Price"));
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ProductDTO(productId, typeID, productName, price, null, describe, image, quantity, true));
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

    public List<ProductDTO> getAllProduct(int page) throws SQLException {
        List<ProductDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT  ProductID, ProductName, TypeID, Price, Date, Describe, Image, Quantity, Availability "
                        + "FROM(SELECT ROW_NUMBER() OVER (ORDER BY Date) AS RowNum, ProductID, ProductName, TypeID, Price, Date, Describe, Image, Quantity, Availability "
                        + "FROM tblProduct ) AS RowConstrainedResult "
                        + "WHERE RowNum >= ? and RowNum <= ? ORDER BY RowNum";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, (page - 1) * 3 + 1);
                stm.setInt(2, page * 3);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("ProductId");
                    String productName = rs.getString("ProductName");
                    String typeID = rs.getString("TypeID");
                    float price = Float.parseFloat(rs.getString("Price"));
                    Date date = rs.getDate("Date");
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    Boolean availability = rs.getBoolean("Availability");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ProductDTO(productId, typeID, productName, price, date, describe, image, quantity, availability));
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

    public List<ProductDTO> searchProduct(ProductDTO dto, int page) throws SQLException {
        List<ProductDTO> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "SELECT  ProductID, ProductName, TypeID, Price, Date, Describe, Image, Quantity, Availability "
                        + "FROM(SELECT ROW_NUMBER() OVER (ORDER BY Date) AS RowNum, ProductID, ProductName, TypeID, Price, Date, Describe, Image, Quantity, Availability "
                        + "FROM tblProduct WHERE ProductName like ? and TypeID like ? ) AS RowConstrainedResult "
                        + "WHERE RowNum >= ? and RowNum <= ? ORDER BY RowNum";
                stm = conn.prepareStatement(sql);
                stm.setString(1, '%' + dto.getProductName() + '%');
                stm.setString(2, dto.getTypeID());
                stm.setInt(3, (page - 1) * 3 + 1);
                stm.setInt(4, page * 3);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("ProductId");
                    String productName = rs.getString("ProductName");
                    String typeID = rs.getString("TypeID");
                    float price = Float.parseFloat(rs.getString("Price"));
                    Date date = rs.getDate("Date");
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    Boolean availability = rs.getBoolean("Availability");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new ProductDTO(productId, typeID, productName, price, date, describe, image, quantity, availability));
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

    public ProductDTO getProduct(ProductDTO dto) throws SQLException {
        ProductDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select ProductID, TypeID, ProductName, Price, Describe, Image, Quantity from tblProduct"
                        + " where TypeID = ? and ProductName = ? and Price = ? and Date = ? and Describe = ? and Image = ? and Quantity = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getTypeID());
                stm.setString(2, dto.getProductName());
                stm.setFloat(3, dto.getPrice());
                stm.setString(4, convertDateToString(dto.getDate()));
                stm.setString(5, dto.getDescribe());
                stm.setString(6, dto.getImage());
                stm.setInt(7, dto.getQuantity());
                rs = stm.executeQuery();
                if (rs.next()) {
                    int productID = rs.getInt("ProductID");
                    String typeID = rs.getString("TypeID");
                    String productName = rs.getString("ProductName");
                    float price = Float.parseFloat(rs.getString("Price"));
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    result = new ProductDTO(productID, typeID, productName, price, null, describe, image, quantity, true);
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
    
    public ProductDTO checkProduct(int id, int quantity) throws SQLException {
        ProductDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {   
                String sql = "select ProductID, TypeID, ProductName, Price, Image, Quantity from tblProduct"
                        + " where ProductID = ? and Availability = 'true' and Quantity >= ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, id);
                stm.setInt(2, quantity);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String typeID = rs.getString("TypeID");
                    String productName = rs.getString("ProductName");
                    float price = Float.parseFloat(rs.getString("Price"));
                    String image = rs.getString("Image");
                    result = new ProductDTO(id, typeID, productName, price, null, "", image, quantity, true);
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


    public void updateProduct(ProductDTO dto, String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Update tblProduct "
                        + "set TypeID = ?, ProductName = ?, Price = ?, Describe = ?, Image = ?, Quantity = ?, Availability = ? "
                        + "where ProductID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getTypeID());
                stm.setString(2, dto.getProductName());
                stm.setString(3, Float.toString(dto.getPrice()));
                stm.setString(4, dto.getDescribe());
                stm.setString(5, dto.getImage());
                stm.setString(6, Integer.toString(dto.getQuantity()));
                stm.setBoolean(7, dto.isAvailability());
                stm.setString(8, Integer.toString(dto.getProductID()));
                stm.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    public void deleteProduct(String productID, String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Update tblProduct "
                        + "set Availability = 'false' "
                        + "where ProductID = ?";
                stm = conn.prepareStatement(sql);
                Date d = new Date();
                stm.setString(1, productID);
                stm.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void createProduct(ProductDTO dto, String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Insert into tblProduct (typeID, productName, price, date, describe, image, quantity, availability) VALUES(?,?,?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, dto.getTypeID());
                stm.setString(2, dto.getProductName());
                stm.setFloat(3, dto.getPrice());
                stm.setString(4, convertDateToString(dto.getDate()));
                stm.setString(5, dto.getDescribe());
                stm.setString(6, dto.getImage());
                stm.setInt(7, dto.getQuantity());
                stm.setBoolean(8, true);    
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
    
    public ProductDTO getProductByID(int id) throws SQLException {
        ProductDTO result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select ProductID, TypeID, ProductName, Price, Describe, Image, Quantity from tblProduct"
                        + " where ProductID = ? and Availability = 'true'";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, id);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String typeID = rs.getString("TypeID");
                    String productName = rs.getString("ProductName");
                    float price = Float.parseFloat(rs.getString("Price"));
                    String describe = rs.getString("Describe");
                    String image = rs.getString("Image");
                    int quantity = rs.getInt("Quantity");
                    result = new ProductDTO(id, typeID, productName, price, null, describe, image, quantity, true);
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

    public void refresh(int productID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "Update tblProduct "
                        + "set Quantity = Quantity - ? "
                        + "where ProductID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setInt(2, productID);
                stm.executeUpdate();
            }
        } catch (Exception e) {
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public List<String> getProductID(String name) throws SQLException {
        List<String> result = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBIUtils.getConnection();
            if (conn != null) {
                String sql = "select ProductID from tblProduct where ProductName like ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%"+name+"%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("ProductId");
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(Integer.toString(productId));
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
