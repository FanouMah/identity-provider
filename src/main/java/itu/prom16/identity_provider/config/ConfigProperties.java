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
    private int delaiTokenInscription;

    @Value("${delai.pin.connexion}")
    private int delaiPinConnexion;
    
    @Value("${nombre.tentative}")
    private int nombreTentative;

    public int getDelaiTokenInscription() {
        return delaiTokenInscription;
    }

    public int getDelaiPinConnexion() {
        return delaiPinConnexion;
    }

    public int getNombreTentative() {
        return nombreTentative;
    }
    
}
