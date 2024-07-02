package com.authorization.jwtAuthorization.repository;

import com.authorization.jwtAuthorization.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRepo extends JpaRepository<Entry,Long> {
    Entry findByTitle(String title);

    List<Entry> findByDiaryId(Long id);

    @Query("SELECT e FROM Entry e WHERE e.title LIKE %:keyword% AND e.diary.id = :diaryId")
    List<Entry> searchArtwork(@Param("keyword") String keyword, @Param("diaryId") Long diaryId);
    @Query("SELECT COUNT(e) FROM Entry e WHERE e.diary.id = :diaryId")
    Long countByDiaryId(@Param("diaryId") Long diaryId);
}
