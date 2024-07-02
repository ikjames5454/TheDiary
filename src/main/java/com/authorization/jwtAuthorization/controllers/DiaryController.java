package com.authorization.jwtAuthorization.controllers;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.service.DiaryService;
import com.authorization.jwtAuthorization.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/user/create_diary")
    public ResponseEntity<Diary> createDiary(@RequestHeader("Authorization") String jwt,@RequestBody CreateDiaryRequest createDiaryRequest){
        Users user = userManagementService.getUserByJwtToken(jwt).getUsers();
        Diary diary = diaryService.createDiary(user,createDiaryRequest);
        return new ResponseEntity<>(diary, HttpStatus.OK);

    }

    @PutMapping("/user/update_diary/{id}")
    public ResponseEntity<Diary> updateDiary(@PathVariable Long id,@RequestBody CreateDiaryRequest createDiaryRequest ){
        Diary diary = diaryService.updateDiary(id,createDiaryRequest);
        return new ResponseEntity<>(diary,HttpStatus.OK);

    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<DeleteResponse> deleteDiary(@PathVariable Long id){
        return ResponseEntity.ok(diaryService.deleteDiary(id));
    }

    @GetMapping("/adminSubAdmin/find")
    public ResponseEntity<List<Diary>> getAllDiary(){
        List<Diary> diaries = diaryService.getAllDiary();
        return new ResponseEntity<>(diaries,HttpStatus.OK);
    }

   @GetMapping ("/adminSubAdmin/search")
    public ResponseEntity<List<Diary>> search(@RequestParam String search){
       List<Diary> diaries = diaryService.searchQuery(search);
       return new ResponseEntity<>(diaries,HttpStatus.OK);
   }

   @GetMapping("/adminUserSubAdmin/{id}")
    public ResponseEntity<Diary> findByDiaryId(@PathVariable Long id){
        Diary find = diaryService.findDiaryById(id);
        return new ResponseEntity<>(find,HttpStatus.OK);
   }

  @GetMapping("/adminSubAdmin/user/{user_id}")
    public ResponseEntity<Diary> findDiaryByUserId(@PathVariable Long user_id){
        Diary find = diaryService.findDiaryByUserId(user_id);
        return new ResponseEntity<>(find,HttpStatus.OK);
  }


}
