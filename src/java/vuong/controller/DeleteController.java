/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import java.util.Date;
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
import vuong.dtos.NoteDTO;
import vuong.dtos.ProductDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class DeleteController extends HttpServlet {

    private static final String SUCCESS = "manage.jsp";
    private static final String ERROR = "invalid.jsp";
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
            String productID = request.getParameter("productID");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if(user==null||user.getRoleID().equals("US")){
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                cart.delete(productID);
                url = USER;
                session.setAttribute("CART", cart);
            } else if (user.getRoleID().equals("AD")) {
                Date date = new Date();
                NoteDTO note = new NoteDTO(0, Integer.parseInt(productID), user.getUserID(), date, "Delete");
                ProductDAO pDao = new ProductDAO();
                NoteDAO nDao = new NoteDAO();
                pDao.deleteProduct(productID, user.getUserID());
                nDao.createNote(note);
                List<ProductDTO> listProduct = (List<ProductDTO>) session.getAttribute("SEARCH_MANAGE");
                int page = (int) session.getAttribute("PAGE_AD");
                if (listProduct == null) {
                    listProduct = pDao.getAllProduct(page);
                    session.setAttribute("LIST_MANAGE", listProduct);
                } else {
                    ProductDTO dto = (ProductDTO) session.getAttribute("SEARCH_INFO");
                    listProduct = pDao.searchProduct(dto, page);
                    session.setAttribute("SEARCH_MANAGE", listProduct);
                }
                url = SUCCESS;
            } 
            request.setAttribute("NOTIFY", "Delete product success!");

        } catch (Exception e) {
           Logger.getLogger(DeleteController.class).error("Error " + e.toString() +" at DeleteController");
           request.setAttribute("NOTIFY", "Delete product fail!");
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
