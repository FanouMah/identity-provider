package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.Token_Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenInscriptionRepository extends JpaRepository<Token_Inscription, Integer> {
    Optional<Token_Inscription> findByCode(String code); 
}

