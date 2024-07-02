package com.authorization.jwtAuthorization.controllers;

import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.service.JWTUtils;
import com.authorization.jwtAuthorization.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
//@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserManagementService userManagementService;


    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse response){
        return ResponseEntity.ok(userManagementService.register(response));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse response){
        return ResponseEntity.ok(userManagementService.login(response));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse response){
        return ResponseEntity.ok(userManagementService.refreshToken(response));
    }

    @GetMapping("/adminSubAdmin/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @GetMapping("/adminUserSubAdmin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userManagementService.getUserById(userId));
    }


    @PutMapping("/adminUserSubAdmin/update/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable Long userId, @RequestBody Users response){
        return ResponseEntity.ok(userManagementService.updateUser(userId, response));
    }

    @GetMapping("/adminUserSubAdmin/getUserByJwt")
    public ResponseEntity<RequestResponse> getUserByJwt( @RequestHeader("Authorization") String jwt){
        System.out.println("JWT received: '" + jwt + "'");
//        if (jwt.startsWith("Bearer ")) {
//            jwt = jwt.substring(7).trim();
//        }
//        System.out.println("After trimming: " + jwt);
        RequestResponse response = userManagementService.getUserByJwtToken(jwt);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/adminUserSubAdmin/get-profile")
    public ResponseEntity<RequestResponse> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        RequestResponse response = userManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping ("/adminUserSubAdmin/delete/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(userManagementService.deleteUser(userId));
    }

    @GetMapping("/adminSubAdmin/number-of-registered-user")
    public ResponseEntity<RequestResponse> numberOfRegisteredUsers(){
        return ResponseEntity.ok(userManagementService.numberOfUsers());
    }

    @GetMapping ("/adminSubAdmin/find_by_country/{country}")
    public ResponseEntity<RequestResponse> findNumberOfUsersByCountry(@PathVariable String country){
        return ResponseEntity.ok(userManagementService.findByCountry(country));
    }

    @GetMapping("/adminSubAdmin/count_by_country")
    public ResponseEntity<RequestResponse> countByCountry(@RequestParam("country") String country){
        return ResponseEntity.ok(userManagementService.totalNumberOfUsersByCountry(country));
    }

    @GetMapping("/adminSubAdmin/list_of_users_by_countries")
    public ResponseEntity<RequestResponse> usersByCountries(){
        return ResponseEntity.ok(userManagementService.countUsersByCountry());
    }


}
