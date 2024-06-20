package com.authorization.jwtAuthorization.repository;

import com.authorization.jwtAuthorization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users,Long> {

   
    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);


}
