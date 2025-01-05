package itu.prom16.identity_provider.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import itu.prom16.identity_provider.DTO.LoginRequest;
import itu.prom16.identity_provider.DTO.UpdateUserRequest;
import itu.prom16.identity_provider.DTO.UserRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_users", nullable = false)
    private int idUsers;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_inscription" , nullable = false)
    private LocalDateTime dateInscription;

    @Column(name = "date_naissance" , nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre_tentative", nullable = false)
    private int nombreTentative;

    public int getidUsers() {
        return idUsers;
    }

    void setidUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public void setidUsers(String idUsers){
        this.idUsers=Integer.parseInt(idUsers);
    }

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

    public LocalDateTime getdateInscription() {
        return dateInscription;
    }

    public void setdateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setdateInscription(String dateInscription) {
        this.dateInscription = LocalDateTime.parse(dateInscription);
    }
    public LocalDate getdateNaissance() {
        return dateNaissance;
    }

    public void setdateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setdateNaissance(String dateNaissance){
        this.dateNaissance=LocalDate.parse(dateNaissance);
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
    
    public void nullPassword() {
        this.password = null;
    }

    public Users(){

    }

    public Users(String nom, String prenom, String email, String password){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.password=password;
    }
    public Users(LoginRequest request){
        this.email=request.getEmail();
        this.password=request.getPassword();
    }

    public Users(UserRequest request){
        this.nom = request.getNom();
        this.prenom = request.getPrenom();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.dateNaissance = request.getDateNaissance();
    }

    public Users(UpdateUserRequest request){
        this.nom = request.getNom();
        this.prenom = request.getPrenom();
        this.dateNaissance = request.getDateNaissance();
    }
    public int getnombreTentative() {
        return nombreTentative;
    }

    public void setnombreTentative(int nombreTentative) {
        this.nombreTentative = nombreTentative;
    }

    public void setnombreTentative(String nombreTentative){
        this.nombreTentative=Integer.parseInt(nombreTentative);
    }
}
