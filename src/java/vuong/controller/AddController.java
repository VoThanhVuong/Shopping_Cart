/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import vuong.daos.ProductDAO;
import vuong.dtos.CartDTO;
import vuong.dtos.DetailDTO;
import vuong.dtos.ProductDTO;

/**
 *
 * @author vuong
 */
public class AddController extends HttpServlet {

    private static final String SUCCESS = "index.jsp";
    private static final String ERROR = "index.jsp";

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
            int productID = Integer.parseInt(request.getParameter("productID"));
            int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
            HttpSession session = request.getSession();
            ProductDAO dao = new ProductDAO();
            DetailDTO detail = null;
            ProductDTO dto = null;
            CartDTO cart = ((CartDTO) session.getAttribute("CART"));
            if (cart == null) {
                cart = new CartDTO("", null);
            } else {
                detail = cart.getCart().get(productID + "");
            }
            if (detail != null) {
                detail.setQuantity(detail.getQuantity() + quantity);
                dto = dao.checkProduct(detail.getProductID(), detail.getQuantity());
            } else {
                dto = dao.checkProduct(productID, quantity);
            }
            if (dto != null) {
                url = SUCCESS;
                if(detail==null){
                    detail = new DetailDTO(productID, quantity);
                }
                cart.add(detail);
                session.setAttribute("CART", cart);
                request.setAttribute("NOTIFY", "Your order " + quantity + " "+ dto.getProductName() + " success!");

            } else {
                request.setAttribute("NOTIFY", "This product is not enough quantity!");
            }
        } catch (Exception e) {
            Logger.getLogger(AddController.class).error("Error " + e.toString() +" at AddController");
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
