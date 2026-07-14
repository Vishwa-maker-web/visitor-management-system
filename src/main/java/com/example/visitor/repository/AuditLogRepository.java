package com.example.visitor.repository;

import com.example.visitor.dto.DailyVisitorDTO;
import com.example.visitor.dto.LocationCountDTO;
import com.example.visitor.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    @Query("""
SELECT new com.example.visitor.dto.LocationCountDTO(a.location, COUNT(a))
FROM AuditLog a
GROUP BY a.location
ORDER BY COUNT(a) DESC
""")
    List<LocationCountDTO> getHighTrafficRoutes();

    @Query("""
SELECT
FUNCTION('DATE_FORMAT', a.time, '%Y-%m-%d'),
COUNT(a)
FROM AuditLog a
GROUP BY FUNCTION('DATE_FORMAT', a.time, '%Y-%m-%d')
ORDER BY FUNCTION('DATE_FORMAT', a.time, '%Y-%m-%d')
""")
    List<Object[]> getDailyVisitors();
}