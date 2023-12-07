/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 *
 * @author andrew
 */
@Entity
@Table(name="todo")
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;
    private String description;
    private int priority;

    // empty constructor
    public Todo() {}

    // constructor with all fields
    public Todo(Long id, String category, String description, int priority) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.priority = priority;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category= category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description= description; }
}
