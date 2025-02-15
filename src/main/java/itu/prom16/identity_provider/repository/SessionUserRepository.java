package itu.prom16.identity_provider.repository;

import itu.prom16.identity_provider.entity.Session_users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionUserRepository extends JpaRepository<Session_users, Integer> {
}
