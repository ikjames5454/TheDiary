package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.entity.Users;

import java.util.List;

public interface UserManagementService {

    public RequestResponse register(RequestResponse register);

    public RequestResponse login(RequestResponse loginRequest);

   public RequestResponse refreshToken(RequestResponse refreshRequest);

   public RequestResponse getAllUsers();

   public RequestResponse getUserById(Long id);

   public RequestResponse deleteUser(Long id);

   public RequestResponse updateUser(Long id, Users updatedUsers);

   public RequestResponse getMyInfo(String email);

   public RequestResponse getUserByJwtToken(String jwt);

   public RequestResponse numberOfUsers();

   public RequestResponse findByCountry(String country);

   public  RequestResponse totalNumberOfUsersByCountry(String county);

   public RequestResponse countUsersByCountry();

}
