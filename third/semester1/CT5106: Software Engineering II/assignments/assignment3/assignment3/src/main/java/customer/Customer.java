/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customer;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;

/**
 *
 * @author andrew
 */
@Entity
@Table(name="customers")
public class Customer implements Serializable {

    @Id
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="phone")
    private String phone;   // a String because phone numbers sometimes contain letters (although equivalent to numbers)
    @Column(name="email")
    private String email;
    @Column(name="country")
    private String country;
    @Column(name="postcode")
    private String postcode;
    @Column(name="creditLimit")
    private float creditLimit;
    
    public Customer() {
    }
    
    public Customer(long id, String name, String phone, String email, String country, String postcode, float creditLimit) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.postcode = postcode;
        this.creditLimit = creditLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {return name; }
    public void setName(String name) {this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    public float getCreditLimit() { return creditLimit; }
    public void setCreditLimit(float creditLimit) { this.creditLimit = creditLimit; }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "customer.Customer[ id=" + id + " ]";
    }
    
}
