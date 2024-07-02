package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.DiaryRepo;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService{

    @Autowired
    private DiaryRepo diaryRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private Verification verification;
    @Override
    public Diary createDiary(Users user, CreateDiaryRequest request) {
        verification.ifDiaryNameExist(request.getDiaryName());
        Diary diary = new Diary();
        diary.setDiaryName(request.getDiaryName());
        diary.setImageUrl(request.getImageUrl());
        diary.setUser(user);
        diary.setName(user.getName());
        diary = diaryRepo.save(diary);
        user.setDiary(diary);
        usersRepo.save(user);
        return diary;
    }

    @Override
    public Diary updateDiary(Long diaryId, CreateDiaryRequest request) {
        Diary diary = findDiaryById(diaryId);
        diary.setDiaryName(request.getDiaryName());
        diary.setImageUrl(request.getImageUrl());
        diary.setTimeAt(LocalTime.now());
        diary.setCreatedAt(LocalDate.now());
        return diaryRepo.save(diary);

    }

    @Override
    public DeleteResponse deleteDiary(Long diaryId) {
        DeleteResponse deleteResponse = new DeleteResponse();
        try{
            Diary diary = findDiaryById(diaryId);
            diary.setEntry(null);
            diaryRepo.delete(diary);
            deleteResponse.setMessage("Deleted Successfully");
            deleteResponse.setStatusCode(200);
            deleteResponse.setError(null);
        }catch (RuntimeException e){
            deleteResponse.setStatusCode(400);
            deleteResponse.setMessage("Unable to delete diary");
            deleteResponse.setError(e.getMessage());
        }
        return deleteResponse;
    }

    @Override
    public List<Diary> getAllDiary() {
        return diaryRepo.findAll();
    }

    @Override
    public List<Diary> searchQuery(String query) {
        return verification.search(query);
    }

    @Override
    public Diary findDiaryById(Long id) {
        return verification.findDiaryById(id);
    }

    @Override
    public Diary findDiaryByUserId(Long userId) {
        return diaryRepo.findByUserId(userId);
    }
}
