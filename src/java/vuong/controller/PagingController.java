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
import vuong.daos.ProductDAO;
import vuong.dtos.ProductDTO;

/**
 *
 * @author vuong
 */
public class PagingController extends HttpServlet {

    private static final String PAGE = "index.jsp";
    private static final String MANAGE = "manage.jsp";
    private static final String ERROR = "invalid.html";

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
            String status = (String) request.getParameter("page");
            int page = 1;
            ProductDAO dao = new ProductDAO();
            List<ProductDTO> listProduct = (List<ProductDTO>) session.getAttribute("LIST_MANAGE");
            if (listProduct == null) {
                url = PAGE;
                page = (int) session.getAttribute("PAGE");
                listProduct = (List<ProductDTO>) session.getAttribute("LIST");
                if (listProduct == null) {
                    if (status.equalsIgnoreCase(">")) {
                        listProduct = dao.getProductAvailability(page + 1);
                        if (listProduct != null) {
                            page += 1;
                        } else {
                            listProduct = dao.getProductAvailability(page);
                        }
                        session.setAttribute("LIST_PRODUCT", listProduct);
                    } else {
                        listProduct = dao.getProductAvailability(page - 1);
                        if (listProduct != null) {
                            page -= 1;
                        } else {
                            listProduct = dao.getProductAvailability(page);
                        }
                        session.setAttribute("LIST_PRODUCT", listProduct);
                    }
                    session.setAttribute("PAGE", page);
                } else {
                    ProductDTO dto = (ProductDTO) session.getAttribute("SEARCH_INFO");
                    if (status.equalsIgnoreCase(">")) {
                        listProduct = dao.getProductAvailability(dto, page + 1);
                        if (listProduct != null) {
                            page += 1;
                        } else {
                            listProduct = dao.getProductAvailability(dto, page);
                        }
                        session.setAttribute("LIST", listProduct);
                    } else {
                        listProduct = dao.getProductAvailability(dto, page - 1);
                        if (listProduct != null) {
                            page -= 1;
                        } else {
                            listProduct = dao.getProductAvailability(dto, page);
                        }
                        session.setAttribute("LIST", listProduct);
                    }
                    session.setAttribute("PAGE", page);
                }
            } else {
                url = MANAGE;
                listProduct = null;
                listProduct = (List<ProductDTO>) session.getAttribute("SEARCH_MANAGE");
                page = (int) session.getAttribute("PAGE_AD");
                if (listProduct == null) {
                    if (status.equalsIgnoreCase(">")) {
                        listProduct = dao.getAllProduct(page+1);
                        if (listProduct != null) {
                            page += 1;
                        } else {
                            listProduct = dao.getAllProduct(page);
                        }
                        session.setAttribute("LIST_MANAGE", listProduct);
                    } else {
                        listProduct = dao.getAllProduct(page-1);
                        if (listProduct != null) {
                            page -= 1;
                        } else {
                            listProduct = dao.getAllProduct(page);
                        }
                        session.setAttribute("LIST_MANAGE", listProduct);
                    }
                    session.setAttribute("PAGE_AD", page);
                } else {
                    ProductDTO dto = (ProductDTO) session.getAttribute("SEARCH_INFO");
                    if (status.equalsIgnoreCase(">")) {
                        listProduct = dao.searchProduct(dto, page+1);
                        if (listProduct != null) {
                            page += 1;
                        } else {
                            listProduct = dao.searchProduct(dto, page);
                        }
                        session.setAttribute("SEARCH_MANAGE", listProduct);
                    } else {
                        listProduct = dao.searchProduct(dto, page-1);
                        if (listProduct != null) {
                            page -= 1;
                        } else {
                            listProduct = dao.searchProduct(dto, page);
                        }
                        session.setAttribute("SEARCH_MANAGE", listProduct);
                    }
                    session.setAttribute("PAGE_AD", page);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(PagingController.class).error("Error " + e.toString() +" at PagingController");
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
