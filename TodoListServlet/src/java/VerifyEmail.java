/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;


/**
 *
 * @author vedan
 */
@WebServlet(name = "VerifyEmail",urlPatterns = {"/VerifyEmail"})
public class VerifyEmail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String token=request.getParameter("emailToken");
            System.out.println(token);
            Connection conn;
            PreparedStatement ps;
            PreparedStatement ps2;

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/checkit?zeroDateTimeBehavior=CONVERT_TO_NULL ";
            String uname="root";
            String passwordDB="";
            conn =  DriverManager.getConnection(url,uname,passwordDB);
            
//          For Getting the name and email of user
            ps2=conn.prepareStatement("select name,email from users where emailToken=?");
            ps2.setString(1,token);
            ResultSet rs = ps2.executeQuery();
            if(!rs.next()){
                out.print("Email Token Does Not Exist. Please try to signup again or try to copy paste the verification Link in differnet browser.");
            }
            String name=rs.getString("name");
            String email=rs.getString("email");
            
//          For Updating the User 
            String query="update users set emailToken= NULL where emailToken = ? ";
            ps = conn.prepareStatement(query);
            ps.setString(1,token);
            ps.executeUpdate();
            
            
            request.setAttribute("name", name);
            request.setAttribute("email", email);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            out.close();
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
