/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import vuong.daos.DetailDAO;
import vuong.daos.OrderDAO;
import vuong.daos.ProductDAO;
import vuong.dtos.CartDTO;
import vuong.dtos.DetailDTO;
import vuong.dtos.OrderDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class CartController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS = "history.jsp";

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
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            ProductDAO rDao = new ProductDAO();
            Date date = new Date();
            OrderDTO order = new OrderDTO(user.getUserID() + "-" + date, user.getUserID(), "C", date, true);
            OrderDAO oDao = new OrderDAO();
            DetailDAO dDao = new DetailDAO();
            oDao.createOrder(order);
            CartDTO history = ((CartDTO) session.getAttribute("HISTORY"));
            if (history == null) {
                history = new CartDTO("", null);
            }
            for (DetailDTO detail : cart.getCart().values()) {
                detail.setOrderID(order.getOrderID());
                dDao.createDetail(detail);
                rDao.refresh(detail.getProductID(), detail.getQuantity());
                history.add(detail);
            }
            session.setAttribute("CART", null);
            session.setAttribute("HISTORY", history);
            request.setAttribute("NOTIFY", "Your bought product success!");
            url = SUCCESS;
        } catch (Exception e) {
            Logger.getLogger(CartController.class).error("Error " + e.toString() +" at CartController");
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
