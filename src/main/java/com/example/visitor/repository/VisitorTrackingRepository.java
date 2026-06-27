package com.example.visitor.repository;

import com.example.visitor.entity.VisitorTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorTrackingRepository extends JpaRepository<VisitorTracking,Long> {
Optional<VisitorTracking> findByEmail(String email);
}
