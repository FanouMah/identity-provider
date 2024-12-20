/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.PinVerification;
import itu.prom16.identity_provider.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fanou
 */
public interface PinVerificationRepository extends JpaRepository<PinVerification, Integer> {
        Optional<PinVerification> findTopByIdUsersOrderByDateExpirationDesc(Users idUsers);
}
