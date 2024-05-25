package com.example.TourGuideApp.persistence.entity.repository;

import com.example.TourGuideApp.persistence.entity.UserEntity; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { 
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email); 
    UserEntity findByVerificationCode(String verificationCode);
}



