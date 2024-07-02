package com.authorization.jwtAuthorization;

import com.authorization.jwtAuthorization.dto.DeleteResponse;
import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.dto.requestDto.CreateDiaryRequest;
import com.authorization.jwtAuthorization.entity.Diary;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.DiaryRepo;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import com.authorization.jwtAuthorization.service.DiaryService;
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
public class DiaryApplicationTest {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private DiaryRepo diaryRepo;

    private RequestResponse requestResponse;

    private CreateDiaryRequest createDiaryRequest;

    private CreateDiaryRequest updateRequest;



    @BeforeEach
    public void startWith(){

        requestResponse = new RequestResponse();
        createDiaryRequest = new CreateDiaryRequest();
        updateRequest = new CreateDiaryRequest();
    }

    @BeforeEach
    public void tearDown(){
        usersRepo.deleteAll();
        diaryRepo.deleteAll();
    }

    @Test
    public void createUserDiary(){
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

    }


    @Test
    public void canUpdateDiary(){
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
        Long id = diary.getId();

        updateRequest.setDiaryName("Dear diary");
        updateRequest.setImageUrl("ggfgfhhfhhvgdfdfdfdfdfdfdfdddddddddddddddddddddddddddddddddddddddddddddddddddkkkkkkkkkkkkkkkkkkkkkkkk");
        Diary updatedDiary = diaryService.updateDiary(id,updateRequest);
        System.out.println(updatedDiary);


    }

    @Test
    public void canDeleteDiary(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);
        Long userId = response.getUsers().getId();
        System.out.println("UserId: " + userId);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);
        Long id = diary.getId();

        usersRepo.deleteById(userId);
        DeleteResponse delete =  diaryService.deleteDiary(id);
        System.out.println(delete);

    }

    @Test
    public void canGetAllDiaries(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);
        Long userId = response.getUsers().getId();
        System.out.println("UserId: " + userId);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        requestResponse.setName("Ikenna james");
        requestResponse.setEmail("IkennaJames03@gmail.com");
        requestResponse.setPassword("Password");
        requestResponse.setState("Anambra");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse theResponse = userManagementService.register(requestResponse);
        assertEquals(theResponse.getUsers().getName(),"Ikenna james");
        System.out.println(theResponse);
        assertNotNull(response);
        Long id = theResponse.getUsers().getId();
        System.out.println("UserId: " + id);

        Optional<Users> user = usersRepo.findByEmail(theResponse.getUsers().getEmail());
        Users theUsers = user.get();
        System.out.println(theUsers);

        createDiaryRequest.setDiaryName("MyDiary");
        createDiaryRequest.setImageUrl("oojhggffretetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary myDiary = diaryService.createDiary(theUsers,createDiaryRequest);
        System.out.println(myDiary);
        assertNotNull(myDiary);

        List<Diary> list = diaryService.getAllDiary();

        assertNotNull(list);
        assertEquals(2, list.size());

        assertEquals("MyPersonal", list.get(0).getDiaryName());
        assertEquals("MyDiary", list.get(1).getDiaryName());
        assertEquals("Moyin oluwakpo", list.get(0).getName());


    }

    @Test
    public void canSearch(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);
        Long userId = response.getUsers().getId();
        System.out.println("UserId: " + userId);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);

        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        requestResponse.setName("Ikenna james");
        requestResponse.setEmail("IkennaJames03@gmail.com");
        requestResponse.setPassword("Password");
        requestResponse.setState("Anambra");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse theResponse = userManagementService.register(requestResponse);
        assertEquals(theResponse.getUsers().getName(),"Ikenna james");
        System.out.println(theResponse);
        assertNotNull(response);
        Long id = theResponse.getUsers().getId();
        System.out.println("UserId: " + id);

        Optional<Users> user = usersRepo.findByEmail(theResponse.getUsers().getEmail());
        Users theUsers = user.get();
        System.out.println(theUsers);

        createDiaryRequest.setDiaryName("MyDiary");
        createDiaryRequest.setImageUrl("oojhggffretetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary myDiary = diaryService.createDiary(theUsers,createDiaryRequest);
        System.out.println(myDiary);
        assertNotNull(myDiary);

         List<Diary>search = diaryService.searchQuery("Ikenna james");
        assertNotNull(search.get(0));
        assertEquals(search.get(0).getDiaryName(),"MyDiary" );


    }

    @Test
    public void findDiaryByUserId(){
        requestResponse.setName("Moyin oluwakpo");
        requestResponse.setEmail("IkennaJames@gmail.com");
        requestResponse.setPassword("123Password");
        requestResponse.setState("Lagos");
        requestResponse.setCountry("Nigeria");
        requestResponse.setRole("USER");
        RequestResponse response = userManagementService.register(requestResponse);
        System.out.println(response);
        assertNotNull(response);
        Long userId = response.getUsers().getId();
        System.out.println("UserId: " + userId);

        Optional<Users> users = usersRepo.findByEmail(response.getUsers().getEmail());
        Users theUser = users.get();
        System.out.println(theUser);


        createDiaryRequest.setDiaryName("MyPersonal");
        createDiaryRequest.setImageUrl("retetetdgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");

        Diary diary = diaryService.createDiary(theUser,createDiaryRequest);
        System.out.println(diary);
        assertNotNull(diary);

        Diary find = diaryService.findDiaryByUserId(userId);
        assertEquals(find.getDiaryName(),"MyPersonal");
        assertEquals(find.getUser().getName(),"Moyin oluwakpo");
    }

}
