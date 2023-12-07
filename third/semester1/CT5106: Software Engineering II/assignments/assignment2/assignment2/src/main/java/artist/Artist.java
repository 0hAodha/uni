/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package artist;

/**
 *
 * @author andrew
 */

public class Artist {
    private String surname;
    private String firstname;
    private String nationality;
    private String birthYear;    // since no arithmetic is performed on birth & death years, easier to leave them as Strings
    private String deathYear;
    private String biography;
    private String photoUrl;
    
    // constructor for artist 
    public Artist(String surname, String firstname, String nationality, String birthYear, String deathYear, String biography, String photoUrl) {
        this.surname = surname;
        this.firstname = firstname;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.deathYear = deathYear; // doesn't matter if death year is not entered - will just be an empty string
        this.biography = biography;
        this.photoUrl = photoUrl;
    }
    
    // getters 
    public String getSurname() { return surname; }

    public String getFirstname() { return firstname; }

    public String getNationality() { return nationality; }

    public String getBirthYear() { return birthYear; }

    public String getDeathYear() { return deathYear; }

    public String getBiography() { return biography; }

    public String getPhotoUrl() { return photoUrl; }

    // setters
    public void setSurname(String surname) { this.surname =  surname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public void setNationality(String nationality) { this.nationality = nationality; }

    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public void setDeathYear(String deathYear) { this.deathYear = deathYear; }

    public void setBiography(String biography) { this.biography = biography; }

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

}


    
