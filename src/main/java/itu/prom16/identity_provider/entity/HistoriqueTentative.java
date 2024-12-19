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
@Table(name = "historique_tentative")
public class HistoriqueTentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_historique_tentative")
    private Integer idHistoriqueTentative;

    @Basic(optional = false)
    @NotNull
    @Column(name = "date_tentative")
    private LocalDateTime dateTentative;
    
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    @ManyToOne(optional = false)
    private Users idUsers;


    public HistoriqueTentative() {
    }

    public HistoriqueTentative(Integer idHistoriqueTentative) {
        this.idHistoriqueTentative = idHistoriqueTentative;
    }

    public HistoriqueTentative(LocalDateTime dateTentative, Users idUsers) {
        this.dateTentative = dateTentative;
        this.idUsers = idUsers;
    }

    public Integer getIdHistoriqueTentative() {
        return idHistoriqueTentative;
    }

    public void setIdHistoriqueTentative(Integer idHistoriqueTentative) {
        this.idHistoriqueTentative = idHistoriqueTentative;
    }

    public LocalDateTime getDateTentative() {
        return dateTentative;
    }

    public void setDateTentative(LocalDateTime dateTentative) {
        this.dateTentative = dateTentative;
    }
    
    public Users getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Users idUsers) {
        this.idUsers = idUsers;
    }
}
