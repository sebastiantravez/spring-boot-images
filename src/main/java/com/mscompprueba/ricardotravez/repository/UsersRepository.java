package com.mscompprueba.ricardotravez.repository;

import com.mscompprueba.ricardotravez.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsernameAndPassword(String username, String password);
}

