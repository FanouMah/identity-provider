package itu.prom16.identity_provider.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "session_users")
public class Session_users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_session_user", nullable = false)
    private int idSessionUser;

    @Column(name = "nom" , nullable = false)
    private String nom;

    @Column(name = "prenom" , nullable = false)
    private String prenom;

    @Column(name = "date_naissance" , nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "email" , nullable = false, unique = true)
    private String email;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "date_demande" , nullable = false)
    private LocalDate dateDemande;


    public int getidSessionUser() {
        return idSessionUser;
    }

    void setidSessionUser(int idSessionUser) {
        this.idSessionUser = idSessionUser;
    }

    public void setidSessionUser(String idSessionUser){
        setidSessionUser(Integer.parseInt(idSessionUser));
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

    public LocalDate getdateNaissance() {
        return dateNaissance;
    }

    void setdateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setdateNaissance(String dateNaissance){
        setdateNaissance(LocalDate.parse(dateNaissance));
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

     public void setPassword(String password) {
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

    public LocalDate getdateDemande() {
        return dateDemande;
    }

    public void setdateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public void setdateDemande(String dateDemande) {
        setdateDemande(LocalDate.parse(dateDemande));
    }
}