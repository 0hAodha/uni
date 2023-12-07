/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import artist.Artist;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author andrew   
 */
@WebServlet(name = "AddArtistServlet", urlPatterns = {"/AddArtistServlet"})
public class AddArtist extends HttpServlet {

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
       
        // getting reference to the seeion object
        HttpSession session = request.getSession();

        // attempting to get a reference to the artistList in the session object (may be null)
        ArrayList<Artist> artistList = (ArrayList<Artist>) session.getAttribute("artistList");

        // if the artistList hasn't been initialised yet, create one
        if (artistList == null) {
            artistList = new ArrayList<>(); 
        }
          

        // adding artist to list
        artistList.add(new Artist(
                request.getParameter("surname"),
                request.getParameter("firstname"),
                request.getParameter("nationality"),
                request.getParameter("birthYear"),
                request.getParameter("deathYear"),
                request.getParameter("biography"),
                request.getParameter("photoUrl")
        ));
        
        // adding artistList to session as attribute
        session.setAttribute("artistList", artistList);

        // redirecting back to the ArtistList JSP page
        response.sendRedirect("displayArtists.jsp");
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
