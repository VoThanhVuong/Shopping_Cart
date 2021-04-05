/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import vuong.daos.NoteDAO;
import vuong.daos.ProductDAO;
import vuong.dtos.CartDTO;
import vuong.dtos.DetailDTO;
import vuong.dtos.NoteDTO;
import vuong.dtos.ProductDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class UpdateController extends HttpServlet {

    private static final String SUCCESS = "manage.jsp";
    private static final String ERROR = "invalid.html";
    private static final String USER = "cart.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user == null || user.getRoleID().equals("US")) {
                int id = Integer.parseInt(request.getParameter("productID"));
                int quan = Integer.parseInt(request.getParameter("txtQuantity"));
                ProductDAO dao = new ProductDAO();
                ProductDTO dto = dao.checkProduct(id, quan);
                if (dto != null) {
                    CartDTO cart = (CartDTO) session.getAttribute("CART");
                    DetailDTO detail = new DetailDTO(id, quan);
                    cart.update(detail);
                    session.setAttribute("CART", cart);
                    session.setAttribute("NOTIFY", "Your update is success");
                    url = USER;
                }
            } else if (user.getRoleID().equals("AD")) {
                String productID = request.getParameter("txtProductID");
                String productName = request.getParameter("txtProductName");
                String typeID = request.getParameter("cbbType");
                String price = request.getParameter("txtPrice");
                String describe = request.getParameter("txtDescribe");
                String image = request.getParameter("fileImage");
                String quantity = request.getParameter("txtQuantity");
                String availability = request.getParameter("cbbAvailability");
                if (image == null || image == "") {
                    image = request.getParameter("image");
                } else {
                    image = "Lab\\" + image;
                }
                ProductDAO pDao = new ProductDAO();
                java.util.Date date = new java.util.Date();
                NoteDTO note = new NoteDTO(0, Integer.parseInt(productID), user.getUserID(), date, "Update");
                NoteDAO nDao = new NoteDAO();
                ProductDTO dto = new ProductDTO(Integer.parseInt(productID), typeID, productName, Float.parseFloat(price),
                        null, describe, image, Integer.parseInt(quantity), Boolean.valueOf(availability));
                pDao.updateProduct(dto, user.getUserID());
                nDao.createNote(note);
                List<ProductDTO> listProduct = (List<ProductDTO>) session.getAttribute("SEARCH_MANAGE");
                int page = (int) session.getAttribute("PAGE_AD");
                if (listProduct == null) {
                    listProduct = pDao.getAllProduct(page);
                    session.setAttribute("LIST_MANAGE", listProduct);
                } else {
                    ProductDTO dtoSearch = (ProductDTO) session.getAttribute("SEARCH_INFO");
                    listProduct = pDao.searchProduct(dtoSearch, page);
                    session.setAttribute("SEARCH_MANAGE", listProduct);
                }
                url = SUCCESS;
            }
            request.setAttribute("NOTIFY", "Update product success!");
        } catch (Exception e) {
            Logger.getLogger(UpdateController.class).error("Error " + e.toString() +" at UpdateController");
            request.setAttribute("NOTIFY", "Update product failse!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
