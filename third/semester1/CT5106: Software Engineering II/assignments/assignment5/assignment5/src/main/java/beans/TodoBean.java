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
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 *
 * @author andrew
 */
@Named("todoBean")
@RequestScoped
public class TodoBean {
    @EJB
    private TodoFacade todoFacade;
    private String category;
    private String description;
    private int priority;
    private long id;
    private Todo todo;

    @PostConstruct
    public void postConstruct() {
        // get todoId
        String todoIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("todoId");

        // if the todoId exists
        if (todoIdParam != null) {
            // parse out id, and get a reference to the Todo object that id pertains to
            id = Long.parseLong(todoIdParam);
            todo = todoFacade.find(id);

            // if that todo exists, get fields
            if (todo != null) {
                category = todo.getCategory();
                description = todo.getDescription();
                priority = todo.getPriority();
            }
        }
    }
    
    // getters and sestters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    

    // method to add a new todo item
    public String add() {
        Todo newTodo = new Todo();
        newTodo.setCategory(category);
        newTodo.setDescription(description);
        newTodo.setPriority(priority);

        todoFacade.create(newTodo);
        return "success";
    }

    // method to update a todo item
    public String update() {
        todo.setCategory(category);
        todo.setDescription(description);
        todo.setPriority(priority);

        todoFacade.edit(todo);
        return "success";
    }
    
    // method to delete a todo item
    public String delete() {
        todoFacade.remove(todo);
        return "success";
    }
}
