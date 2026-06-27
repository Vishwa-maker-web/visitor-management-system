package com.example.visitor.repository;

import com.example.visitor.entity.ReceptionQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceptionQueueRepository extends JpaRepository<ReceptionQueue,Long> {

    List<ReceptionQueue> findByStatus(String status);
    Optional<ReceptionQueue> findByEmail(String email);
}
