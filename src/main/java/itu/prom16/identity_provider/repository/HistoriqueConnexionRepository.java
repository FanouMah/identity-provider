/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.HistoriqueConnexion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Fanou
 */
@Repository
public interface HistoriqueConnexionRepository extends JpaRepository<HistoriqueConnexion, Integer>{
    
}
