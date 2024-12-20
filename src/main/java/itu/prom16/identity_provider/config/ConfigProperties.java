/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itu.prom16.identity_provider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Fanou
 */
@Component
public class ConfigProperties {
    @Value("${delai.token.inscription}")
    private Integer delaiTokenInscription;
    
    @Value("${delai.token.reset.tentative}")
    private Integer delaiTokenResetTentative;

    @Value("${delai.pin.connexion}")
    private Integer delaiPinConnexion;
    
    @Value("${nombre.tentative}")
    private Integer nombreTentative;
    
    @Value("${server.ip}")
    private String serverIp;
    
    @Value("${server.port}")
    private String serverPort;

    public Integer getDelaiTokenInscription() {
        return delaiTokenInscription;
    }

    public Integer getDelaiPinConnexion() {
        return delaiPinConnexion;
    }

    public Integer getNombreTentative() {
        return nombreTentative;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public Integer getDelaiTokenResetTentative() {
        return delaiTokenResetTentative;
    }
    
}
