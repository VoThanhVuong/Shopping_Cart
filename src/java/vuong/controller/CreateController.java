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
import vuong.dtos.NoteDTO;
import vuong.dtos.ProductDTO;
import vuong.dtos.ProductErrorDTO;
import vuong.dtos.UserDTO;

/**
 *
 * @author vuong
 */
public class CreateController extends HttpServlet {

    private static final String SUCCESS = "manage.jsp";
    private static final String ERROR = "create.jsp";

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
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("USER");
        ProductErrorDTO errorObject = new ProductErrorDTO("", "", "", "", "", "");
        ProductDTO dto = new ProductDTO();
        try {
            boolean check = true;
            String productName = request.getParameter("txtProductName");
            String typeID = request.getParameter("cbbType");
            String price = request.getParameter("txtPrice");
            String quantity = request.getParameter("txtQuantity");
            String describe = request.getParameter("txtDescribe");
            String image = request.getParameter("fileImage");
            ProductDAO dao = new ProductDAO();
            if (typeID.equalsIgnoreCase("%")) {
                check = false;
                errorObject.setTypeIDError("Please choose product's type!");
            }
            if (productName.isEmpty()) {
                check = false;
                errorObject.setProductNameError("Product's name is not empty!");
            }
            if (price.isEmpty()) {
                check = false;
                errorObject.setPriceError("Price is not empty!");
            } else if (!price.matches("[0-9]+[.][0-9]+") && !price.matches("[0-9]+")) {
                check = false;
                errorObject.setPriceError("Price is contain number greater than 0!");
            }
            if (describe.isEmpty()) {
                check = false;
                errorObject.setDescribeError("Decribe is not empty!");
            }
            if (image == null || image == "") {
                check = false;
                errorObject.setImageError("Please upload image!");
            }
            if (quantity.isEmpty()) {
                check = false;
                errorObject.setQuantityError("Quantity is not empty!");
            } else if (!quantity.matches("[0-9]+")) {
                check = false;
                errorObject.setQuantityError("Quantity is contain number greater than 0!");
            }
            if (check) {
                Date date = new Date();
                NoteDAO nDao = new NoteDAO();
                dto = new ProductDTO(typeID, productName, Float.parseFloat(price), date, describe, "Lab1\\" + image, Integer.parseInt(quantity));
                dao.createProduct(dto, user.getUserID());
                dto = dao.getProduct(dto);
                NoteDTO note = new NoteDTO(0, dto.getProductID(), user.getUserID(), date, "Create");
                nDao.createNote(note);
                List<ProductDTO> list = dao.getAllProduct(1);
                session.setAttribute("LIST_MANAGE", list);
                session.setAttribute("PAGE_AD", 1);
                request.setAttribute("NOTIFY", "Create success!");
                url = SUCCESS;
            } 
            request.setAttribute("ERROR", errorObject);
        } catch (Exception e) {
            Logger.getLogger(CreateController.class).error("Error " + e.toString() +" at CreateController");
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
