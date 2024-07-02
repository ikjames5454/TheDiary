package com.authorization.jwtAuthorization.controllers;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateEntryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Entry;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.service.DiaryService;
import com.authorization.jwtAuthorization.service.EntryService;
import com.authorization.jwtAuthorization.service.UserManagementService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private EntryService entryService;


    @PostMapping("/user/create_entry")
    public ResponseEntity<Entry> createEntry(@RequestHeader("Authorization") String jwt, @RequestBody CreateEntryRequest entryRequest){
        Users user = userManagementService.getUserByJwtToken(jwt).getUsers();
        Diary diary = diaryService.findDiaryByUserId(user.getId());
        Entry entry = entryService.createEntry(diary, entryRequest);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/delete_Entry/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long id){
        return ResponseEntity.ok(entryService.deleteEntry(id));
    }

    @GetMapping("/user/find/{id}")
    public ResponseEntity<Entry> findById(@PathVariable Long id){
        Entry entry = entryService.findByEntryId(id);
        return new ResponseEntity<>(entry,HttpStatus.OK);
    }

    @GetMapping("/user/find_by_diaryId/{diaryId}")
    public ResponseEntity<List<Entry>> listOfEntries(@PathVariable Long diaryId){
        List<Entry> entries = entryService.getEntriesByDiaryId(diaryId);
        return new ResponseEntity<>(entries,HttpStatus.OK);

    }

    @GetMapping("/user/search_entry")
    public ResponseEntity<List<Entry>> searchQuery(@RequestParam String query, @RequestParam Long id){
        List<Entry> entries = entryService.searchEntry(query,id);
        return new ResponseEntity<>(entries,HttpStatus.OK);
    }

    @GetMapping("/user/update_entry/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody CreateEntryRequest updateEntry){
        Entry entry = entryService.updateEntry(id,updateEntry);
        return new ResponseEntity<>(entry,HttpStatus.CREATED);
    }

    @GetMapping("/user/number_diary_entry/{id}")
    public ResponseEntity<Long> numberOfDiaryEntry(@PathVariable Long id){
        return new ResponseEntity<>(entryService.numberOfDiaryEntry(id),HttpStatus.OK);
    }

    @GetMapping("/adminSubAdmin/entry_count")
    public ResponseEntity<Long> totalEntryOfDiaries(){
        return new ResponseEntity<>(entryService.totalNumberOfEntries(),HttpStatus.OK);
    }
}
