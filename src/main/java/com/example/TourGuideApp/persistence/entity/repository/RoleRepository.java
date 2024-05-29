package com.example.TourGuideApp.persistence.entity.repository;

import com.example.TourGuideApp.persistence.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);

}
