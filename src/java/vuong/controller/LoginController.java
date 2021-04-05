/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import vuong.daos.DetailDAO;
import vuong.daos.OrderDAO;
import vuong.daos.ProductDAO;
import vuong.daos.UserDAO;
import vuong.dtos.CartDTO;
import vuong.dtos.DetailDTO;
import vuong.dtos.GooglePojo;
import vuong.dtos.OrderDTO;
import vuong.dtos.ProductDTO;
import vuong.dtos.UserDTO;
import vuong.utils.GoogleUtils;
import vuong.utils.VerifyUtils;

/**
 *
 * @author vuong
 */
public class LoginController extends HttpServlet {

    private static final String SUCCESS = "index.jsp";
    private static final String LOGIN = "login.jsp";
    private static final String AD = "manage.jsp";

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
        String url = LOGIN;
        Boolean valid = false;
        try {
            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            OrderDAO oDao = new OrderDAO();
            ProductDAO pDao = new ProductDAO();
            DetailDAO dDao = new DetailDAO();
            UserDTO user = dao.checkLorgin(userID, password);
            CartDTO cart = new CartDTO("", null);
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            valid = VerifyUtils.verify(gRecaptchaResponse);
            if (valid) {
                if (user != null) {
                    session.setAttribute("USER", user);
                    if (user.getRoleID().equals("AD")) {
                        url = AD;
                        int page = 1;
                        session.setAttribute("PAGE_AD", page);
                        List<ProductDTO> listProduct = pDao.getAllProduct(page);
                        session.setAttribute("LIST_MANAGE", listProduct);
                    }
                    if (user.getRoleID().equals("US")) {
                        url = SUCCESS;
                        List<OrderDTO> listOrder = oDao.getOrderByUserID(user.getUserID(), "%");
                        Map<Date, Date> listDate = new HashMap<>();
                        if (listOrder != null) {
                            for (OrderDTO order : listOrder) {
                                List<DetailDTO> listDetail = dDao.getListDetailByOrderID(order.getOrderID());
                                if (listDetail != null) {
                                    for (DetailDTO detail : listDetail) {
                                        cart.addHis(detail);
                                    }
                                    listDate.put(order.getDate(), order.getDate());
                                }
                            }
                        }
                        session.setAttribute("LIST_DATE", listDate);
                        session.setAttribute("HISTORY", cart);
                        session.setAttribute("ORDER", listOrder);
                    }
                } else {
                    session.setAttribute("MESSAGE", "Your account is not found");
                }
            } else {
                String code = request.getParameter("code");
                if (code == null || code.isEmpty()) {
                    url = LOGIN;
                    session.setAttribute("MESSAGE", "You are robot?");
                } else {
                    String accessToken = GoogleUtils.getToken(code);
                    GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                    userID = googlePojo.getId();
                    user = dao.checkLorgin(userID);
                    if (user == null) {
                        String email = googlePojo.getEmail();
                        String[] userName = googlePojo.getEmail().split("@");
                        user = new UserDTO(userID, userName[0], email);
                        dao.createUser(user);
                        user.setRoleID("US");
                    }
                    session.setAttribute("USER", user);
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(LoginController.class).error("Error " + e.toString() +" at LoginController");
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
