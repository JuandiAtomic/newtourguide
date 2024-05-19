package com.example.TourGuideApp;

import com.example.TourGuideApp.persistence.entity.PermissionEntity;
import com.example.TourGuideApp.persistence.entity.RoleEntity;
import com.example.TourGuideApp.persistence.entity.RoleEnum;
import com.example.TourGuideApp.persistence.entity.repository.UserRepository;
//import com.example.TourGuideApp.persistence.enums.RoleEnum;
import com.example.TourGuideApp.persistence.entity.UserEntity;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TourGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourGuideApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            /* CREATE PERMISSIONS */
            PermissionEntity createPermission = PermissionEntity.builder()
                .name("CREATE")
                .build();

            PermissionEntity readPermission = PermissionEntity.builder()
                .name("READ")
                .build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                .name("UPDATE")
                .build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                .name("DELETE")
                .build();

            PermissionEntity refactorPermission = PermissionEntity.builder()
                .name("REFACTOR")
                .build();

            /* CREATE ROLES */
            RoleEntity roleAdmin = RoleEntity.builder()
                .RoleEnum(RoleEnum.ADMIN)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

            RoleEntity roleUser = RoleEntity.builder()
                .RoleEnum(RoleEnum.USER)
                .permissionList(Set.of(createPermission, readPermission))
                .build();

            RoleEntity roleInvited = RoleEntity.builder()
                .RoleEnum(RoleEnum.INVITED)
                .permissionList(Set.of(readPermission))
                .build();

            RoleEntity roleModerator = RoleEntity.builder()
                .RoleEnum(RoleEnum.MODERATOR)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();
            
            RoleEntity roleDeveloper = RoleEntity.builder()
                .RoleEnum(RoleEnum.DEVELOPER)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                .build();
            

            /* CREATE USERS */
            UserEntity userSantiago = UserEntity.builder()
                .username("Santi12")
                .password("$2a$10$wnKJPMih/IB8OYIPd02pKOjRQdyUVYX1S3PK91UoEaG75AJurXJp2")
                .lastName("Gonzales")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleAdmin))
                .build();

            UserEntity userDaniel = UserEntity.builder()
                .username("daniel")
                .password("$2a$10$wnKJPMih/IB8OYIPd02pKOjRQdyUVYX1S3PK91UoEaG75AJurXJp2")
                .lastName("Gonzales")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleUser))
                .build();

            UserEntity userAndrea = UserEntity.builder()
                .username("andrea")
                .password("$2a$10$wnKJPMih/IB8OYIPd02pKOjRQdyUVYX1S3PK91UoEaG75AJurXJp2")
                .lastName("Gonzales")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleInvited))
                .build();

            UserEntity userAnyi = UserEntity.builder()
                .username("anyi")
                .password("$2a$10$wnKJPMih/IB8OYIPd02pKOjRQdyUVYX1S3PK91UoEaG75AJurXJp2")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleDeveloper))
                .build();

            userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));
        };
    }
}

