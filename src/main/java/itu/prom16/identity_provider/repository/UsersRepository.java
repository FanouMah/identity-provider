package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.nombreTentative = :nombreTentative WHERE u.email = :email")
    void updateNombreTentativeByEmail(@Param("email") String email, @Param("nombreTentative") int nombreTentative);
}
