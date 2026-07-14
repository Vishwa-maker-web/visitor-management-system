package com.example.visitor.repository;

import com.example.visitor.entity.IndoorRoute;
import com.example.visitor.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndoorRouteRepository extends JpaRepository<IndoorRoute,Long> {
    List<IndoorRoute> findByRoom(String room);
    Optional<IndoorRoute> findFirstByRoom(String room);
}
