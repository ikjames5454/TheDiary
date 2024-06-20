package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.entity.Users;

public interface UserManagementService {

    public RequestResponse register(RequestResponse register);

    public RequestResponse login(RequestResponse loginRequest);

   public RequestResponse refreshToken(RequestResponse refreshRequest);

   public RequestResponse getAllUsers();

   public RequestResponse getUserById(Long id);

   public RequestResponse deleteUser(Long id);

   public RequestResponse updateUser(Long id, Users updatedUsers);

   public RequestResponse getMyInfo(String email);
}
