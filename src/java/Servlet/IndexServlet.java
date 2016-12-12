/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import JPA.Amregistry;
import JPA.Amusers;
import Services.AmregistryJpaController;
import Services.AmusersJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Antonio
 */
public class IndexServlet extends HttpServlet {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("004-JPAPU");
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet indexServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet indexServlet at " + request.getContextPath() + "</h1>");
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
        AmusersJpaController u = new AmusersJpaController(emf);
        String username = null, password = null;
        Cookie[] c = request.getCookies();

        /**
         * Al iniciar el servlet (por defecto es IndexServlet), realiza una serie de comprobaciones.
         * Éstas se basan en comprobar si existen cookies. Si existen y coinciden con los datos
         * de los usuarios, inicia de sesión y accede a la página principal.
         * En caso de no existir, el Servlet redirige a la página de LOGIN/REGISTRO.
         */
        if (c != null) {
            for (int i = 0; i < u.getUsers().size(); i++) {

                for (Cookie c1 : c) {
                    if (c1.getValue().equals(u.getUsers().get(i).getNameUser().toLowerCase())) {
                        username = c1.getValue();
                        idUSER = u.getUsers().get(i).getIdUser();
                    }
                    if (c1.getValue().equals(u.getUsers().get(i).getPasswordUser())) {
                        password = c1.getValue();
                    }
                }
            }

            // Si existen, redirigimos al usuario a la página principal del juego.
            if (username != null && password != null) {
                response.sendRedirect("indexGAME.jsp");
            } else {
                response.sendRedirect("index2.jsp");
            }
            // En caso de que no existan cookies, redirigimos a la página principal.
        } else {
            response.sendRedirect("index2.jsp");
        }

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
        doGet(request, response);
        try {
            // Obtenemos los parámetros y lo pasamos a la base de datos.
            String dInicial = request.getParameter("dInicio");
            String dFinal = request.getParameter("dFinal");
            float speed = Float.parseFloat(request.getParameter("speed"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es_ES"));
            
            // Parseamos a fechas
            Date fInicial = sdf.parse(dInicial);
            Date fFinal = sdf.parse(dFinal);
            
            // Añadimos el registro
            addRegistry(fInicial, fFinal, speed);
        } catch (ParseException ex) {
            Logger.getLogger(IndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Añadimos el registro a la base de datos.
     * @param dInicial
     * @param dFinal
     * @param speed 
     */
    private void addRegistry(Date dInicial, Date dFinal, Float speed) {
        AmregistryJpaController rjc = new AmregistryJpaController(emf);
        Amregistry r = new Amregistry();
        Amusers u = new Amusers();
        
        // Añadimos el registro a la base de datos.
        u.setIdUser(idUSER);
        r.setIdUser(u);
        r.setStartGame(dInicial);
        r.setEndGame(dFinal);
        r.setSpeed(speed);
        rjc.create(r);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "IndexServlet";
    }// </editor-fold>

}
