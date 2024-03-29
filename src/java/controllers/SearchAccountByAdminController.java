/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchAccountByAdminController", urlPatterns = {"/SearchAccountByAdminController"})
public class SearchAccountByAdminController extends HttpServlet {

    private static final String ERROR = "accountManagement.jsp";
    private static final String SUCCESS = "accountManagement.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String search = request.getParameter("searchAccountByAdmin");
            UserDAO dao = new UserDAO();
            String indexPage = request.getParameter("index");
            if(indexPage == null)
                indexPage="1";
            int index = Integer.parseInt(indexPage);
            int count = dao.getTotalAccount();
            int endPage = count / 10;
            if (count % 10 != 0) {
                endPage++;
            }
            
            List<User> listUser = dao.searchAccountByNameForAdmin(search, index);
                
            if (listUser.size() > 0) {
                request.setAttribute("VIEW_ACCOUNT", listUser);
                request.setAttribute("END_PAGE_EACH", endPage);
                url = SUCCESS;
            } else 
                request.setAttribute("SEARCH_FAILED", "KHÔNG TÌM THẤY USER");
        } catch (Exception e) {
            log("Error at SearchAccountByAdminController" + e.toString());
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
