package com.authorization.jwtAuthorization.repository;

import com.authorization.jwtAuthorization.dto.CountryUserCount;
import com.authorization.jwtAuthorization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users,Long> {

   
    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<Users> findByCountry(String country);

    @Query("SELECT COUNT(u) FROM Users u WHERE u.country = :country")
    Long countByCountry(@Param("country") String country);

    @Query("SELECT new com.authorization.jwtAuthorization.dto.CountryUserCount(u.country, COUNT(u))" + "FROM Users u GROUP BY u.country")
    List<CountryUserCount> countUsersByCountry();
}
