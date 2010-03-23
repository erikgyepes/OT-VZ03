/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSetMetaData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dano
 */
@WebServlet(name = "Servlet01", urlPatterns = {"/Servlet01"})
public class Servlet01 extends HttpServlet {

    String URL = "jdbc:mysql://localhost/";
    String databaza = "firma";
    String user = "root";
    String pwd = "";
    Connection con;
    Statement stmt;
    ResultSet rs;

    @Override
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL + databaza, user, pwd);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet01.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet01.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet01</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet01 at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            //processRequest(request, response);

            String SQL = "SELECT * FROM slovnik WHERE slovensky='" + request.getParameter("slovo") + "';";
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet01</title>");
            out.println("</head>");
            out.println("<body>");

            rs.last();
            int pocet = rs.getRow();

            if(pocet==0) {
                out.println("Zadane slovo sa v databaze nenachadza!");
            }                 
                rs.beforeFirst();
                while (rs.next()) {
                    out.println("slovensky: " + rs.getString("slovensky") + "<br />");
                    if(rs.getString("anglicky") != null) {
                        out.println("anglicky: " + rs.getString("anglicky") + "<br />");
                    }
                }
            
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            Logger.getLogger(Servlet01.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
