package com.mscompprueba.ricardotravez.repository;

import com.mscompprueba.ricardotravez.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
