package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateEntryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Entry;

import java.util.List;

public interface EntryService {

    public Entry createEntry(Diary diary, CreateEntryRequest request);

    DeleteResponse deleteEntry(Long id);

    Entry findByEntryId(Long id);

    List<Entry> getEntriesByDiaryId(Long diaryId);

    List<Entry> searchEntry(String searchQuery,Long diaryId);

    Entry updateEntry(Long id, CreateEntryRequest updateRequest);

    Long numberOfDiaryEntry(Long diaryId);

    Long totalNumberOfEntries();


}
