package com.example.visitor.repository;

import com.example.visitor.entity.IndoorRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndoorRouteRepository extends JpaRepository<IndoorRoute,Long> {
    Optional<IndoorRoute> findByRoom(String room);
}
