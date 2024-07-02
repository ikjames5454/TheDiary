package com.authorization.jwtAuthorization;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.dto.requestDto.CreateEntryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Entry;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.DiaryRepo;
import com.authorization.jwtAuthorization.repository.EntryRepo;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import com.authorization.jwtAuthorization.service.DiaryService;
import com.authorization.jwtAuthorization.service.EntryService;
import com.authorization.jwtAuthorization.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EntryTest {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private DiaryRepo diaryRepo;

    private RequestResponse requestResponse;

    private CreateDiaryRequest createDiaryRequest;

    private  CreateEntryRequest updateRequest;

    private CreateEntryRequest entryRequest;

    @BeforeEach
    public void startWith(){
    entryRequest = new CreateEntryRequest();
    requestResponse = new RequestResponse();
    createDiaryRequest = new CreateDiaryRequest();
    updateRequest = new  CreateEntryRequest();
    }

    @BeforeEach
    public void tearDown(){
        usersRepo.deleteAll();
        diaryRepo.deleteAll();
        entryRepo.deleteAll();

    }

    @Test
    public void createEntry(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");


    }

    @Test
    public void updateEntryRequest(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");
        Long id = entry.getId();

        updateRequest.setTitle(" tinsdale");
        updateRequest.setBody(" very annoying girl");

        Entry update = entryService.updateEntry(id,updateRequest);
        assertEquals(update.getTitle(),"That ashley tinsdale" );
        assertEquals(update.getBody(),"That stubborn girl that they call ashley very annoying girl");


    }

    @Test
    public void deleteEntry(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");
        Long id = entry.getId();
        DeleteResponse delete = entryService.deleteEntry(id);
        assertEquals(delete.getMessage(), "Deleted Successfully");

    }

    @Test
    public void canGetEntriesByAParticularDiary(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        Long diaryId = diary.getId();
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");

        entryRequest.setTitle("That Moyin");
        entryRequest.setBody("She can disturb for africa");

        Entry second = entryService.createEntry(diary,entryRequest);
        assertEquals(second.getTitle(),"That Moyin");

        List<Entry> listEntryByDiary = entryService.getEntriesByDiaryId(diaryId);
        assertEquals(listEntryByDiary.size(),2L);

        for(Entry entries: listEntryByDiary){
            if(entries.getTitle().equalsIgnoreCase("That Moyin")){
                assertEquals(entries.getBody(),"She can disturb for africa");
            } else if (entries.getTitle().equalsIgnoreCase("That ashley")) {
                assertEquals(entries.getBody(),"That stubborn girl that they call ashley");
            }
        }


    }

    @Test
    public void canSearchForAnEntryByADiary(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        Long diaryId = diary.getId();
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");

        entryRequest.setTitle("That Moyin");
        entryRequest.setBody("She can disturb for africa");

        Entry second = entryService.createEntry(diary,entryRequest);
        assertEquals(second.getTitle(),"That Moyin");

        List<Entry> searchQuery = entryService.searchEntry("That Moyin",diaryId);
        assertEquals(searchQuery.size(),1L);
        for(Entry search: searchQuery){
            assertEquals(search.getBody(),"She can disturb for africa");
        }

    }


    @Test
    public void canFindEntryById(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        entryRequest.setTitle("That ashley");
        entryRequest.setBody("That stubborn girl that they call ashley");

        Entry entry = entryService.createEntry(diary,entryRequest);
        assertEquals(entry.getTitle(),"That ashley");
        Long id = entry.getId();

        Entry findEntry = entryService.findByEntryId(id);
        assertEquals(findEntry.getBody(),"That stubborn girl that they call ashley");
    }



}
