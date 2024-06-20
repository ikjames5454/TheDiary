package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementServiceImpl implements UserManagementService{

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public RequestResponse register(RequestResponse register) {
        RequestResponse response = new RequestResponse();
        if(usersRepo.existsByEmail(register.getEmail())){
            throw new RuntimeException("email already Exist");
        }
        try{
            Users newUsers = new Users();
            newUsers.setEmail(register.getEmail());
            newUsers.setName(register.getName());
            newUsers.setPassword(passwordEncoder.encode(register.getPassword()));
            newUsers.setRole(register.getRole());
            newUsers.setCity(register.getCity());
            Users registeredUser = usersRepo.save(newUsers);
            if(registeredUser.getId() > 0){
                response.setUsers((registeredUser));
                response.setMessage("User saved Successfully");
                response.setStatusCode(200);
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    @Override
    public RequestResponse login(RequestResponse loginRequest) {
        RequestResponse response = new RequestResponse();
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
               var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
                var jwt = jwtUtils.generateToken(user);
                var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshToken);
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully logged In");
                response.setRole(user.getRole());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Invalid login Credentials");
        }
        return response;
    }

    @Override
    public RequestResponse refreshToken(RequestResponse refreshRequest) {
        RequestResponse response = new RequestResponse();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshRequest.getToken());
            Users users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if(jwtUtils.isTokenValid(refreshRequest.getToken(),users)){
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
            return response;
        }

    }

    @Override
    public RequestResponse getAllUsers() {
        RequestResponse response = new RequestResponse();
        try{
            List<Users> result = usersRepo.findAll();
            if(!result.isEmpty()){
                response.setStatusCode(200);
                response.setUsersList(result);
                response.setMessage("Successful");
            } else{
                response.setStatusCode(404);
                response.setMessage("No users found");
            }
            return response;
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred: " + e.getMessage());
            return response;
        }


    }

    @Override
    public RequestResponse getUserById(Long id) {
        RequestResponse response = new RequestResponse();
        try{
            Users userById = usersRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
            response.setUsers(userById);
            response.setStatusCode(200);
            response.setMessage("Users with id: " + id + " found successfully");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred: " + e.getMessage());

        }
        return response;
    }

    @Override
    public RequestResponse deleteUser(Long id) {
        RequestResponse response = new RequestResponse();
        try{
            Optional<Users> optional = usersRepo.findById(id);
            if(optional.isPresent()) {
                usersRepo.deleteById(id);
                response.setStatusCode(200);
                response.setMessage("User deleted successfully");
            }else{
                response.setStatusCode(404);
                response.setMessage("No users found");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred: " + e.getMessage());

        }
        return response;
    }

    @Override
    public RequestResponse updateUser(Long id, Users updatedUsers) {
        RequestResponse response = new RequestResponse();
        try{
            Optional<Users> option = usersRepo.findById(id);
            if(option.isPresent()){
                Users currentUser = option.get();
                currentUser.setEmail(updatedUsers.getEmail());
                currentUser.setName(updatedUsers.getName());
                currentUser.setRole(updatedUsers.getRole());
                currentUser.setCity(updatedUsers.getCity());
                if(updatedUsers.getPassword() != null && !updatedUsers.getPassword().isEmpty()){
                    currentUser.setPassword(passwordEncoder.encode(updatedUsers.getPassword()));
                }
                Users savedUser = usersRepo.save(currentUser);
                response.setUsers(savedUser);
                response.setStatusCode(200);
                response.setMessage("User updated successfully");
            }else{
                response.setStatusCode(404);
                response.setMessage("No users found for update");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred while updating user: " + e.getMessage());

        }
        return response;
    }

    @Override
    public RequestResponse getMyInfo(String email) {
        RequestResponse response = new RequestResponse();
        try{
            Optional<Users> option = usersRepo.findByEmail(email);
            if (option.isPresent()) {
                response.setUsers(option.get());
                response.setStatusCode(200);
                response.setMessage("User Info found Successfully");
            }else{
            response.setStatusCode(404);
            response.setMessage("User Info not found Successfully");
        }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred while fetching user info: " + e.getMessage());

        }
        return response;

    }


}
