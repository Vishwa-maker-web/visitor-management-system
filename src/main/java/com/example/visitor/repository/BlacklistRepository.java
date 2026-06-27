package com.example.visitor.repository;

import com.example.visitor.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlacklistRepository extends JpaRepository <Blacklist,Long> {
    Optional<Blacklist> findByEmailOrPhone(String email,String phone);
}
