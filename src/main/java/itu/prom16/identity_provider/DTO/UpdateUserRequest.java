/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.DTO;

import java.time.LocalDate;

/**
 *
 * @author Fanou
 */

public class UpdateUserRequest {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;  
// Getters et Setters
public String getNom() {
    return nom;
}

public void setNom(String nom) {
    this.nom = nom;
}

public String getPrenom() {
    return prenom;
}

public void setPrenom(String prenom) {
    this.prenom = prenom;
}

public LocalDate getDateNaissance() {
    return dateNaissance;
}

public void setDateNaissance(LocalDate dateNaissance) {
    this.dateNaissance = dateNaissance;
}
}
