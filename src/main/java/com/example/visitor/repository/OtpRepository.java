package com.example.visitor.repository;

import com.example.visitor.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpVerification,Long> {
    Optional<OtpVerification> findByEmail(String email);
    Optional<OtpVerification> findByEmailOrOtp(String email,String otp);
}
