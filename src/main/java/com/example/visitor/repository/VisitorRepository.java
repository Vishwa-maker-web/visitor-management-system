package com.example.visitor.repository;

import com.example.visitor.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor,Long> {
    Optional<Visitor> findByEmailOrPhone(String email, String phone);
    Optional<Visitor> findByEmail(String email);
}
