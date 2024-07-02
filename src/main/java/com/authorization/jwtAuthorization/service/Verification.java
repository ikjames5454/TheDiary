package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Entry;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.DiaryRepo;
import com.authorization.jwtAuthorization.repository.EntryRepo;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class Verification {

    @Autowired
    private DiaryRepo diaryRepo;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private UsersRepo usersRepo;

    public  void ifDiaryNameExist(String name){
        Diary diaryName = diaryRepo.findByDiaryName(name);
        if(diaryName != null){
            throw new RuntimeException("Diary name " + diaryName + " Already Exist! choose another Name");
        }
    }

    public Diary findDiaryById(Long id){
        Optional<Diary> diaryId = diaryRepo.findById(id);
        if(diaryId.isEmpty()) throw new RuntimeException("Diary does not exist");
        else return diaryId.get();

    }

    public List<Users> findByCountry(String country){
        List<Users> usersByCountry = usersRepo.findByCountry(country);
        if(usersByCountry.isEmpty()) return new ArrayList<>();
        else return usersByCountry;
    }

    public List<Diary> search(String query){
        List<Diary> diary = diaryRepo.bySearchQuery(query);
        if(diary.isEmpty()) return new ArrayList<>();
        else return diary;
    }

    public void ifTitleExist(String title){
        Entry entry = entryRepo.findByTitle(title);
        if(entry != null){
            throw new RuntimeException("Diary name " + entry + " Already Exist! choose another Name");
        }
    }

    public Entry findByEntryId(Long id){
        Optional<Entry> entry = entryRepo.findById(id);
        if(entry.isEmpty()) throw new RuntimeException("Entry does not exist");
        else return entry.get();
    }

    public List<Entry> getEntriesByDiaryId(Long id){
        List<Entry> getEntries = entryRepo.findByDiaryId(id);
        if(getEntries.isEmpty()) return new ArrayList<>();
        else return getEntries;
    }

    public List<Entry> searchQuery(String query,Long diaryId){
        List<Entry> getEntry = entryRepo.searchArtwork(query,diaryId);
        if(getEntry.isEmpty()) return new ArrayList<>();
        else return getEntry;
    }




}
