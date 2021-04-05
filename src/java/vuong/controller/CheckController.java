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
import vuong.daos.DetailDAO;
import vuong.daos.ProductDAO;
import vuong.dtos.CartDTO;
import vuong.dtos.DetailDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class CheckController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS = "pay.jsp";
    private static final String LOGIN = "login.jsp";

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
            ProductDAO rDao = new ProductDAO();
            DetailDAO dDao = new DetailDAO();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (user == null) {
                url = LOGIN;
            } else {
                Boolean check = true;
                for (DetailDTO detail : cart.getCart().values()) {
                    if (rDao.checkProduct(detail.getProductID(), detail.getQuantity()) == null) {
                        session.setAttribute("NOTIFY", "Payment isn't success! Your product is out of stock!");
                        check = false;
                    }
                }
                if (check) {
                    url = SUCCESS;
                    CartDTO recom = new CartDTO("", null);
                    request.setAttribute("total", request.getParameter("total"));
                    for (DetailDTO detail : cart.getCart().values()) {
                        recom.add(detail);
                        String orderID = dDao.getOrderIDByProductID(Integer.toString(detail.getProductID()));
                        List<DetailDTO> list = dDao.getListDetailByOrderID(orderID);
                        if (list != null) {
                            for (DetailDTO dto : list) {
                                recom.add(dto);
                            }
                        }
                    }
                    for (DetailDTO detail : cart.getCart().values()) {
                        recom.delete(detail.getProductID()+"");
                    }
                    session.setAttribute("RECOM", recom);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CheckController.class).error("Error " + e.toString() +" at CheckController");
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
