package com.authorization.jwtAuthorization.repository;

import com.authorization.jwtAuthorization.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiaryRepo extends JpaRepository<Diary,Long> {

    Diary findByUserId(Long id);

    @Query("SELECT a FROM Diary a WHERE lower(a.user.name) like lower(concat('%',:query,'%') ) ")
    List<Diary> bySearchQuery(@Param("query") String query);

    Diary findByDiaryName(String name);
}
