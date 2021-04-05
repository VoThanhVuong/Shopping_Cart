/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuong.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import vuong.dtos.OrderDTO;
import vuong.dtos.ProductDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class SearchController extends HttpServlet {

    private static final String AD = "manage.jsp";
    private static final String SUCCESS = "index.jsp";
    private static final String HISTORY = "history.jsp";

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
        String url = SUCCESS;
        try {
            String productName = request.getParameter("txtNamePro");
            if (productName == null) {
                productName = "";
            }
            String typeID = request.getParameter("cbbType");
            String price = request.getParameter("rangePrice");
            HttpSession session = request.getSession();
            ProductDAO dao = new ProductDAO();
            if (price != null) {
                ProductDTO dto = new ProductDTO(typeID, productName, Float.parseFloat(price));
                List<ProductDTO> list = dao.getProductAvailability(dto, 1);
                url = SUCCESS;
                if (list != null) {
                    session.setAttribute("LIST", list);
                    session.setAttribute("PAGE", 1);
                } else {
                    list = new ArrayList<>();
                    session.setAttribute("LIST", list);
                    session.setAttribute("PAGE", 1);
                }
                session.setAttribute("SEARCH_INFO", dto);
            } else {
                String date = request.getParameter("cbbDate");
                if (date == null) {
                    price = "100000";
                    ProductDTO dto = new ProductDTO(typeID, productName, Float.parseFloat(price));
                    List<ProductDTO> list = dao.searchProduct(dto, 1);
                    url = AD;
                    if (list != null) {
                        session.setAttribute("SEARCH_MANAGE", list);
                        session.setAttribute("PAGE_AD", 1);
                    } else {
                        list = new ArrayList<>();
                        session.setAttribute("SEARCH_MANAGE", list);
                        session.setAttribute("PAGE_AD", 1);
                    }
                    session.setAttribute("SEARCH_INFO", dto);
                } else {
                    url = HISTORY;
                    CartDTO cart = ((CartDTO) session.getAttribute("CART"));
                    if (cart == null) {
                        cart = new CartDTO("", null);
                    }
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    DetailDAO dDao = new DetailDAO();
                    List<DetailDTO> listDetail = dDao.searchHistory(productName, user.getUserID(), date);
                    if (listDetail != null) {
                        for (DetailDTO detail : listDetail) {
                            cart.addHis(detail);
                        }
                    }
                    session.setAttribute("LIST_HISTORY", cart);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(SearchController.class).error("Error " + e.toString() + " at SearchController");
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
