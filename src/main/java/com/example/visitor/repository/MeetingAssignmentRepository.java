package com.example.visitor.repository;

import com.example.visitor.entity.MeetingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingAssignmentRepository  extends JpaRepository<MeetingAssignment,Long> {
  Optional<MeetingAssignment> findByEmail(String email);
}
