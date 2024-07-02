package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Users;

import java.util.List;

public interface DiaryService {
    Diary createDiary(Users user, CreateDiaryRequest request);
    Diary updateDiary(Long diaryId,CreateDiaryRequest request );

    DeleteResponse deleteDiary(Long diaryId);

    List<Diary> getAllDiary();

    List<Diary> searchQuery(String query);

    Diary findDiaryById(Long id);

    Diary findDiaryByUserId(Long userId);
}
