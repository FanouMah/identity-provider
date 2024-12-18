package itu.prom16.identity_provider.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_users", nullable = false)
    private int Id_users;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_inscription" , nullable = false)
    private LocalDateTime date_inscription;

    @Column(name = "date_naissance" , nullable = false)
    private LocalDate date_naissance;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre_tentative", nullable = false)
    private int nombre_tentative;

    public int getId_users() {
        return Id_users;
    }

    void setId_users(int id_users) {
        Id_users = id_users;
    }

    public void setId_users(String id_users){
        this.Id_users=Integer.parseInt(id_users);
    }

    public String getNom() {
        return nom;
    }

    void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getDate_inscription() {
        return date_inscription;
    }

    void setDate_inscription(LocalDateTime date_inscription) {
        this.date_inscription = date_inscription;
    }

    public void setDate_inscription(String date_inscription) {
        this.date_inscription = LocalDateTime.parse(date_inscription);
    }
    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setDate_naissance(String date_naissance){
        this.date_naissance=LocalDate.parse(date_naissance);
    }

    public String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public Users(){

    }

    public Users(String nom, String prenom, String email, String password){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.password=password;
    }

    public int getNombre_tentative() {
        return nombre_tentative;
    }

    void setNombre_tentative(int nombre_tentative) {
        this.nombre_tentative = nombre_tentative;
    }

    public void setNombre_tentative(String nombre_tentative){
        this.nombre_tentative=Integer.parseInt(nombre_tentative);
    }
}
