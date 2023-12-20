/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import data.Todo;
import services.TodoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("todoListBean")
@RequestScoped
public class TodoList implements Serializable {
    // inner class to represent table layout
    static public class ColumnModel implements Serializable {
        private String header;
        private String property;

        // columnmodel constructor
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        // getters and setters
        public String getHeader() { return header; }
        public void setHeader(String header) { this.header = header; }

        public String getProperty() { return property; }
        public void setProperty(String property) { this.property = property; }
    }

    @EJB
    private TodoFacade todoFacade;
    private List<Todo> todoList;
    private List<ColumnModel> columns;

    @PostConstruct
    public void postConstruct() {
        columns = new ArrayList<>();
        columns.add(new ColumnModel("Category", "category"));
        columns.add(new ColumnModel("Description", "description"));
        columns.add(new ColumnModel("Priority", "priority"));

        todoList = todoFacade.findAll();
    }


    public List<Todo> getTodoList() { return todoList; }
    public void setTodoList(List<Todo> todoList) { this.todoList = todoList; }

    public List<ColumnModel> getColumnModel() { return columns; }
    public void setColumnModel(List<ColumnModel> columns) { this.columns = columns; }
}
