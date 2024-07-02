package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateEntryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Entry;
import com.authorization.jwtAuthorization.repository.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService{

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private Verification verification;

    @Override
    public Entry createEntry(Diary diary, CreateEntryRequest request) {
        verification.ifTitleExist(request.getTitle());
        Entry entry = new Entry();
        entry.setTitle(request.getTitle());
        entry.setBody(request.getBody());
        entry.setDiary(diary);

        Entry savedEntry = entryRepo.save(entry);
        diary.getEntry().add(savedEntry);
        return savedEntry;
    }

    @Override
    public DeleteResponse deleteEntry(Long id) {
        DeleteResponse deleteResponse = new DeleteResponse();
        try{
            Entry entry = findByEntryId(id);
            entryRepo.delete(entry);
            deleteResponse.setStatusCode(200);
            deleteResponse.setError(null);
            deleteResponse.setMessage("Deleted Successfully");
        }catch (RuntimeException e){
            deleteResponse.setError(e.getMessage());
            deleteResponse.setMessage("Deleted UnSuccessfully");
            deleteResponse.setStatusCode(400);
        }
        return deleteResponse;
    }

    @Override
    public Entry findByEntryId(Long id) {
        return verification.findByEntryId(id);
    }

    @Override
    public List<Entry> getEntriesByDiaryId(Long diaryId) {
        return verification.getEntriesByDiaryId(diaryId);
    }

    @Override
    public List<Entry> searchEntry(String searchQuery,Long diaryId) {
        return verification.searchQuery(searchQuery,diaryId);
    }

    @Override
    public Entry updateEntry(Long id, CreateEntryRequest updateRequest) {
        Entry find = findByEntryId(id);
        String title = find.getTitle() + updateRequest.getTitle();
        String body = find.getBody() + updateRequest.getBody();
        find.setTitle(title);
        find.setBody(body);
        return entryRepo.save(find);
    }

    @Override
    public Long numberOfDiaryEntry(Long diaryId) {
        return entryRepo.countByDiaryId(diaryId);
    }

    @Override
    public Long totalNumberOfEntries() {
        return entryRepo.count();
    }
}
