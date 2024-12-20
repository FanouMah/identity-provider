/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.TokenResetPin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fanou
 */
public interface TokenResetPinRepository extends JpaRepository<TokenResetPin, Integer>{
    Optional<TokenResetPin> findByCode(String code); 
}
