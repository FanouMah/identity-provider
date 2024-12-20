package itu.prom16.identity_provider.entity;

// import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "token_inscription")
public class Token_Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_token_inscription", nullable = false)
    private int idTokenInscription;

    @Column(name = "code", nullable = false,unique = true)
    private String code;

    @Column(name = "date_expiration", nullable = false)
    private LocalDateTime dateExpiration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_session_user", referencedColumnName = "Id_session_user")
    private Session_users idSessionUser;


    public int getidTokenInscription() {
        return idTokenInscription;
    }

    void setidTokenInscription(int idTokenInscription) {
        this.idTokenInscription = idTokenInscription;
    }
    
    public void setidTokenInscription(String idTokenInscription) {
        setidTokenInscription(Integer.parseInt(idTokenInscription));
    }

    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getdateExpiration() {
        return dateExpiration;
    }

    public void setdateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setdateExpiration(String dateExpiration){
        this.dateExpiration = LocalDateTime.parse(dateExpiration);
    }

    
    public Session_users getidSessionUser() {
        return idSessionUser;
    }

    void setidSessionUser(Session_users idSessionUser) {
        this.idSessionUser = idSessionUser;
    }

    public Token_Inscription() {
    }

    public Token_Inscription(String code, Session_users idSessionUser) {
        this.code = code;        
        this.idSessionUser = idSessionUser;
    }
}
