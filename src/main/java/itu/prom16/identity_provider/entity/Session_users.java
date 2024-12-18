package itu.prom16.identity_provider.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "session_users")
public class Session_users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_session_user", nullable = false)
    private int id_session_user;

    @Column(name = "nom" , nullable = false)
    private String nom;

    @Column(name = "prenom" , nullable = false)
    private String prenom;

    @Column(name = "date_naissance" , nullable = false)
    private LocalDate date_naissance;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "date_demande" , nullable = false)
    private LocalDate date_demande;


    public int getId_session_user() {
        return id_session_user;
    }

    void setId_session_user(int id_session_user) {
        this.id_session_user = id_session_user;
    }

    public void setId_session_user(String id_session_user){
        setId_session_user(Integer.parseInt(id_session_user));
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

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setDate_naissance(String date_naissance){
        setDate_naissance(LocalDate.parse(date_naissance));
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
    
    public Session_users() {

    }

    public Session_users(String nom, String prenom, String password, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
    }

    public LocalDate getDate_demande() {
        return date_demande;
    }

    void setDate_demande(LocalDate date_demande) {
        this.date_demande = date_demande;
    }

    public void setDate_demande(String date_demande) {
        setDate_demande(LocalDate.parse(date_demande));
    }
}