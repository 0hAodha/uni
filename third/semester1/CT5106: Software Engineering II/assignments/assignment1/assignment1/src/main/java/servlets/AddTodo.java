/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;
import todo.*;

/**
 *
 * @author andrew
 */
@WebServlet(name = "AddTodo", urlPatterns = {"/AddTodo"})
public class AddTodo extends HttpServlet {

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
        
        // getting the subject and details parameters from the request
        String subject = request.getParameter("subject");
        String details = request.getParameter("details");
        
        // getting reference to the seeion object
        HttpSession session = request.getSession();
        
        // attempting to get a reference to the taskList in the session object (may be null)
        ArrayList<ToDo> taskList = (ArrayList<ToDo>) session.getAttribute("taskList");

        // if the tasklist hasn't been initialised yet, create one
        if (taskList == null) {
            taskList = new ArrayList<>(); 
        }
        
        // adding the task to the list and displaying it, if it's valid
        if (subject != null && !subject.isEmpty() && details != null && !details.isEmpty()) {
            // adding ToDo task to list
            taskList.add(new ToDo(subject, details));

            // adding taskList to session as attribute
            session.setAttribute("taskList", taskList);

            // redirecting back to the GetTodoList servlet
            // request.getRequestDispatcher("GetTodoList").forward(request, response);
            response.sendRedirect("GetTodoList");
        }
        // if the task is not valid, redirecting to addTodo.html 
        else {
            response.sendRedirect("addTodo.html");
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
