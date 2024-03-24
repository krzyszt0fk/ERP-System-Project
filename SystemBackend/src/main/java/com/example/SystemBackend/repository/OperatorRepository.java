package com.example.SystemBackend.repository;

import com.example.SystemBackend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator,Long> {
    public Optional<Operator> findByLogin(String login);
}
