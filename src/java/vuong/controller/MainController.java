/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author vuong
 */
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String LOGIN = "LoginController";
    private static final String ERROR = "invalid.html";
    private static final String LOGOUT = "LogoutController";
    private static final String SEARCH = "SearchController";
    private static final String DELETE = "DeleteController";
    private static final String UPDATE = "UpdateController";
    private static final String CREATE = "CreateController";
    private static final String CREATE_PAGE = "create.jsp";
    private static final String CART = "cart.jsp";
    private static final String ADD = "AddController";
    private static final String CONFIRM = "CartController";
    private static final String CHECK = "CheckController";


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
        RequestDispatcher r = null;
        String url = ERROR;
        try {
            String action = request.getParameter("btnAction");
            if (action.equals("Login")) {
                url = LOGIN;
            } else if (action.equals("login page")) {
                url = LOGIN_PAGE;
            } else if (action.equals("logout")) {
                url = LOGOUT;
            } else if (action.equals("search")) {
                url = SEARCH;
            } else if (action.equals("Search")) {
                url = SEARCH;
            } else if (action.equals("delete")) {
                url = DELETE;
            } else if (action.equals("Update")) {
                url = UPDATE;
            } else if (action.equals("create")) {
                url = CREATE_PAGE;
            } else if (action.equals("Create")) {
                url = CREATE;
            } else if (action.equals("cart")) {
                url = CART;
            } else if (action.equals("ADD TO CART")) {
                url = ADD;
            } else if (action.equals("ADD")) {
                url = ADD;
            } else if (action.equals("CONFIRM AND CASH PAYMENT")) {
                url = CONFIRM;
            } else if (action.equals("CONFIRM AND PAYMENT")) {
                url = CHECK;
            } 

        } catch (Exception e) {
            Logger.getLogger(MainController.class).error("Error " + e.toString() +" at MainController");
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
