package com.example.visitor.repository;

import com.example.visitor.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom,Long> {
    Optional<MeetingRoom> findByRoomName(String roomName);
    List<MeetingRoom> findByStatus(String status);
    long countByStatus(String status);
}
