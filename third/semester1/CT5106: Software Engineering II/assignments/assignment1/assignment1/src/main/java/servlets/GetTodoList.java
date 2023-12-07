package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
@WebServlet(urlPatterns = {"/GetTodoList"})
public class GetTodoList extends HttpServlet {

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
        
        // getting reference to session
        HttpSession session = request.getSession(); 
        
        // attempting to get a reference to the taskList in the session object (may be null)
        ArrayList<ToDo> taskList = (ArrayList<ToDo>) session.getAttribute("taskList");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetTodoList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetTodoList at " + request.getContextPath() + "</h1>");
            
            // if taskList doesn't exist yet, don't try to print taskList
            if (taskList == null) {
                out.println("<a href='./addTodo.html'>Add Task</a>");
            }
            // else, if taskList does exist, print it in table form
            else {
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Subject</th>");
                out.println("<th>Details</th>");
                out.println("</tr>");

                // iterating over the tasklist and creating a row in the table for each task
                for (ToDo task : taskList) {
                    out.println("<tr><td>" + task.getSubject() + "</td>");
                    out.println("<td>" + task.getDetails() + "</td></tr>");                    
                }
                
                out.println("</table>");
                out.println("<a href='./addTodo.html'>Add Task</a>");
            }
            
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
