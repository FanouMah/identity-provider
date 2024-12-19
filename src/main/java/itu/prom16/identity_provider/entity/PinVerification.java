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
@Table(name = "pin_verification")
public class PinVerification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pin_verification")
    private Integer idPinVerification;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "pin")
    private Integer pin;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;
    
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    @ManyToOne(optional = false)
    private Users idUsers;

    public PinVerification() {
    }

    public PinVerification(Integer idPinVerification) {
        this.idPinVerification = idPinVerification;
    }

    public PinVerification(Integer idPinVerification, Integer pin, LocalDateTime dateExpiration) {
        this.idPinVerification = idPinVerification;
        this.pin = pin;
        this.dateExpiration = dateExpiration;
    }

    public PinVerification(Integer pin, LocalDateTime dateExpiration, Users idUsers) {
        this.pin = pin;
        this.dateExpiration = dateExpiration;
        this.idUsers = idUsers;
    }
    

    public Integer getIdPinVerification() {
        return idPinVerification;
    }

    public void setIdPinVerification(Integer idPinVerification) {
        this.idPinVerification = idPinVerification;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Users getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Users idUsers) {
        this.idUsers = idUsers;
    }
    
}
