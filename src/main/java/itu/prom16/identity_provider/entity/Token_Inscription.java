package itu.prom16.identity_provider.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "token_inscription")
public class Token_Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_session_user", nullable = false)
    private int Id_token_inscription;

    @Column(name = "code", nullable = false,unique = true)
    private String code;

    @Column(name = "date_expiration", nullable = false)
    private LocalDate date_expiration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_session_user", referencedColumnName = "Id_session_user")
    private Session_users sessionUser;


    public int getId_token_inscription() {
        return Id_token_inscription;
    }

    void setId_token_inscription(int id_token_inscription) {
        Id_token_inscription = id_token_inscription;
    }

    public void setId_token_inscription(String id_token_inscription) {
        setId_token_inscription(Integer.parseInt(id_token_inscription));
    }

    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDate_expiration() {
        return date_expiration;
    }

    void setDate_expiration(LocalDate date_expiration) {
        this.date_expiration = date_expiration;
    }

    public void setDate_expiration(String date_expiration){
        this.date_expiration = LocalDate.parse(date_expiration);
    }

    
    public Session_users getSessionUser() {
        return sessionUser;
    }

    void setSessionUser(Session_users sessionUser) {
        this.sessionUser = sessionUser;
    }

    public Token_Inscription() {
    }

    public Token_Inscription(String code, Session_users sessionUser) {
        this.code = code;        
        this.sessionUser = sessionUser;
    }
}
