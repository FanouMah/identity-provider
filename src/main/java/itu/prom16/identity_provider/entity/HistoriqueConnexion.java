/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *
 * @author Fanou
 */
@Entity
@Table(name = "historique_connexion")
public class HistoriqueConnexion{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historique_connexion")
    private Integer idHistoriqueConnexion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_connexion")
    private LocalDateTime dateConnexion;
    
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    @ManyToOne(optional = false)
    private Users idUsers;

    public HistoriqueConnexion() {
    }

    public HistoriqueConnexion(Integer idHistoriqueConnexion) {
        this.idHistoriqueConnexion = idHistoriqueConnexion;
    }

    public HistoriqueConnexion(Integer idHistoriqueConnexion, LocalDateTime dateConnexion) {
        this.idHistoriqueConnexion = idHistoriqueConnexion;
        this.dateConnexion = dateConnexion;
    }

    public HistoriqueConnexion(LocalDateTime dateConnexion, Users idUsers) {
        this.dateConnexion = dateConnexion;
        this.idUsers = idUsers;
    }

    
    public Integer getIdHistoriqueConnexion() {
        return idHistoriqueConnexion;
    }

    public void setIdHistoriqueConnexion(Integer idHistoriqueConnexion) {
        this.idHistoriqueConnexion = idHistoriqueConnexion;
    }

    public LocalDateTime getDateConnexion() {
        return dateConnexion;
    }

    public void setDateConnexion(LocalDateTime dateConnexion) {
        this.dateConnexion = dateConnexion;
    }

    public Users getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Users idUsers) {
        this.idUsers = idUsers;
    }
    
}
