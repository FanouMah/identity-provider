/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 *
 * @author Fanou
 */
@Entity
@Table(name = "token_reset_pin")
public class TokenResetPin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_token_reset_pin")
    private Integer idTokenResetPin;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "code")
    private String code;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;
    
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    @ManyToOne(optional = false)
    private Users idUsers;

    public TokenResetPin() {
    }

    public TokenResetPin(Integer idTokenResetPin) {
        this.idTokenResetPin = idTokenResetPin;
    }

    public TokenResetPin(Integer idTokenResetPin, String code, LocalDateTime dateExpiration) {
        this.idTokenResetPin = idTokenResetPin;
        this.code = code;
        this.dateExpiration = dateExpiration;
    }

    public TokenResetPin(String code, LocalDateTime dateExpiration, Users idUsers) {
        this.code = code;
        this.dateExpiration = dateExpiration;
        this.idUsers = idUsers;
    }

    public Integer getIdTokenResetPin() {
        return idTokenResetPin;
    }

    public void setIdTokenResetPin(Integer idTokenResetPin) {
        this.idTokenResetPin = idTokenResetPin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
