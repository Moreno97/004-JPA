/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import JPA.Amusers;
import Services.AmusersJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antonio
 */
public class RegisterServlet extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("004-JPAPU");

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        /**
         * Recibimos los parámetros del Nickname y del Password, además las encriptamos.
         */
        try {
            Sha1Encrypter sha1 = new Sha1Encrypter();
            String pass1 = sha1.sha1(request.getParameter("inputPassword"));
            String pass2 = sha1.sha1(request.getParameter("inputPass2"));

            Amusers uGame = new Amusers();
            AmusersJpaController uGameController = new AmusersJpaController(emf);

            /**
             * Si el primer campo del password coincide con el segundo, entonces añadimos las variables
             * a los objetos. En caso contrario, informamos de que las contraseñas no coinciden.
             */
            if (pass1.equals(pass2)) {
                uGame.setNameUser(request.getParameter("inputUser").toLowerCase());
                uGame.setPasswordUser(pass1);
                uGame.setEmail(request.getParameter("inputEmail"));
                
                // Creamos el objeto.
                uGameController.create(uGame);

                // Redirigimos a una página de que el proceso ha sido realizado correctamente.
                RequestDispatcher rDispatcher = request.getRequestDispatcher("registrationOK.html");
                rDispatcher.forward(request, response);
            } else {
                // Redirigimos a una web de información.
                response.sendRedirect("register.jsp");
            }
        } catch (IOException | ServletException | NoSuchAlgorithmException e) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Register";
    }// </editor-fold>

}
