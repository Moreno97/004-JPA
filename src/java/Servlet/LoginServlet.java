/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import JPA.Amusers;
import Services.AmusersJpaController;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antonio
 */
public class LoginServlet extends HttpServlet {
    Integer idUSER;
    
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
        RequestDispatcher rDispatcher = request.getRequestDispatcher("login.html");
        rDispatcher.forward(request, response);
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
        boolean userIdentificado = false;
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("004-JPAPU");
            AmusersJpaController u = new AmusersJpaController(emf);
            Sha1Encrypter sha1 = new Sha1Encrypter();
            String username = request.getParameter("username").toLowerCase();
            String password = sha1.sha1(request.getParameter("password"));

            /**
             * Recorremos la lista de usuarios y comprobamos que el usuario y la contraseña
             * coinciden en la base de datos.
             */
            for (Amusers user : u.getUsers()) {
                if (user.getNameUser().equalsIgnoreCase(username) && user.getPasswordUser().equals(password)) {
                    try {
                        // Creamos las cookies de inicio de sesión, que se mantendrán durante un tiempo determinado.
                        Cookie cNAME = new Cookie("cNAME", username);
                        Cookie cPASSWORD = new Cookie("cPASSWORD", password);
                        idUSER = user.getIdUser();
                        
                        // El usuario ha sido identificado y logueado.
                        userIdentificado = true;
                        
                        // Fecha de expiración después de 30 min para ambas cookies.
                        cNAME.setMaxAge(60 * 30);
                        cPASSWORD.setMaxAge(60 * 30);

                        // Añadimos ambas cookies a la cabecera del HTTP
                        response.addCookie(cNAME);
                        response.addCookie(cPASSWORD);

                        // Redirigimos a un HTML informativo para que el usuario sepa que el login ha sido satisfactorio.
                        response.sendRedirect("loginOK.html");
                    } catch (IOException ex) {
                        Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {}
        
        // Si los datos de inicio de sesión no son válidos, informamos al usuario.
        if (!userIdentificado) {
            response.sendRedirect("loginFAILED.jsp");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }// </editor-fold>

}
