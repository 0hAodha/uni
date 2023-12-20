/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package todo;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author andrew
 */
public class ToDo implements Serializable {
        
    private String subject;
    private String details;

    // empty constructor as is standard for java beans
    public ToDo() {
    }
    
    // polymorphic constructor with subject and details
    public ToDo(String subject, String details) {
        this.subject = subject; 
        this.details = details;
    }
    
    // getters and setters
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }            
}
