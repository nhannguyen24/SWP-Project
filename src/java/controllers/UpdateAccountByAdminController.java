package controllers;

import dao.DistrictDAO;
import dao.RoleDAO;
import dao.UserDAO;
import dto.District;
import dto.Role;
import dto.User;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateAccountByAdminController", urlPatterns = {"/UpdateAccountByAdminController"})
public class UpdateAccountByAdminController extends HttpServlet {

    public static final String ERROR = "AccountEditorController";
    public static final String SUCCESS = "AccountEditorController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            boolean check = true;
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            fullName = URLEncoder.encode(fullName, "ISO-8859-1");
            fullName = URLDecoder.decode(fullName, "UTF-8");
            String address = request.getParameter("address");
            address = URLEncoder.encode(address, "ISO-8859-1");
            address = URLDecoder.decode(address, "UTF-8");
            String id_of_district = request.getParameter("districtId");
            DistrictDAO districtDAO = new DistrictDAO();
            District district = districtDAO.getDistrictByID(id_of_district);
            String birthday = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String accName = request.getParameter("accName");
            accName = URLEncoder.encode(accName, "ISO-8859-1");
            accName = URLDecoder.decode(accName, "UTF-8");
            String password = request.getParameter("password");
            
            String id_of_role = request.getParameter("roleId");
            RoleDAO role = new RoleDAO();
            Role roleID = role.getRole(id_of_role);
            String status = request.getParameter("status");
            UserDAO dao = new UserDAO();
            
            if (fullName.trim().length() <= 0 || fullName.length() >= 30) {
                request.setAttribute("FULL_NAME_ERROR", "Tên không được bỏ trống hay vượt quá 30 kí tự");
                check = false;
            }
            
            if (address.trim().length() <= 0) {
                request.setAttribute("ADDRESS_ERROR", "Địa chỉ không được bỏ trống");
                check = false;
            }
            
            if (email.trim().length() <= 0) {
                request.setAttribute("EMAIL_ERROR", "Địa chỉ email không được bỏ trống");
                check = false;
            }
            
            if (phone.trim().length() <= 0) {
                request.setAttribute("PHONE_ERROR", "Số điện thoại không được bỏ trống");
                check = false;
            }
            
            if (password.trim().length() <= 0) {
                request.setAttribute("PASSWORD_ERROR", "Mật khẩu không được bỏ trống");
                check = false;
            }
            
            if (id_of_role.trim().length() <= 0) {
                request.setAttribute("ROLE_ERROR", "Role không được bỏ trống");
                check = false;
            }
            
            if (birthday.isEmpty()) {
                request.setAttribute("BIRTH_ERROR", "Ngày sinh không được để trống");
                check = false;
            }
            
            if (accName.trim().length() <= 0) {
                request.setAttribute("ACCOUNT_NAME_ERROR", "Tên account không được bỏ trống");
                check = false;
            }
            
            if (id_of_district.trim().length() <= 0) {
                request.setAttribute("DISTRICT_ERROR", "Địa chỉ không được bỏ trống");
                check = false;
            }
            
            User user = new User(userID, fullName, address, district, birthday, phone, email, accName, password, roleID, status);
            boolean checkUpdate = dao.updateUser(user);
            if (checkUpdate & check) {                
                url = SUCCESS;
                request.setAttribute("UPDATE_SUCCESS", "Cập nhật thành công!!!");
            } else {
                request.setAttribute("UPDATE_FAILED", "Cập nhật thất bại!!!");
            }
        } catch (Exception e) {
            log("Error at UpdateAccountByAdminController: " + e.toString());
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
