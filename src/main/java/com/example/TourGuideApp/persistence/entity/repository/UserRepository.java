package com.example.TourGuideApp.persistence.entity.repository;

import com.example.TourGuideApp.persistence.entity.UserEntity; // Importa la clase UserEntity correctamente
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    
    
    Optional<UserEntity> findUserEntityByUsername(String username);
    
}

