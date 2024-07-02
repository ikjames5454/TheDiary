package com.authorization.jwtAuthorization.service;

import com.authorization.jwtAuthorization.dto.CountryUserCount;
import com.authorization.jwtAuthorization.dto.RequestResponse;
import com.authorization.jwtAuthorization.entity.Users;
import com.authorization.jwtAuthorization.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private Verification verification;

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
            newUsers.setRole(register.getRole());
            newUsers.setCountry(register.getCountry());
            newUsers.setState(register.getState());
            newUsers.setPassword(passwordEncoder.encode(register.getPassword()));
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
                response.setUsersList(new ArrayList<>());
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
                currentUser.setCountry(updatedUsers.getCountry());
                currentUser.setState(updatedUsers.getState());
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

    @Override
    public RequestResponse getUserByJwtToken(String jwt) {
        RequestResponse response = new RequestResponse();
        try{
            String email = jwtUtils.extractUsername(jwt);
            Optional<Users> optionalUser = usersRepo.findByEmail(email);

            if (optionalUser.isPresent()) {
                response.setUsers(optionalUser.get());
                response.setStatusCode(200);
                response.setMessage("User found successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }
        return response;
    }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred while fetching getting user " + e.getMessage());
        }
        return response;
    }

    @Override
    public RequestResponse numberOfUsers() {
        RequestResponse response = new RequestResponse();
        Long count = usersRepo.count();
        response.setCount(count);
        response.setStatusCode(200);
        return response;
    }

    @Override
    public RequestResponse findByCountry(String country) {
        RequestResponse response = new RequestResponse();
        try {
            List<Users> usersByCountry = verification.findByCountry(country);
            response.setStatusCode(200);
            response.setMessage("List of users by country fetched successful");
            response.setUsersList(usersByCountry);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError("Error Occurred while fetching getting user " + e.getMessage());
            response.setUsersList(new ArrayList<>());
        }
        return response;
    }

    @Override
    public  RequestResponse totalNumberOfUsersByCountry(String county) {
        RequestResponse response = new RequestResponse();
        try {
            Long count = usersRepo.countByCountry(county);
            response.setStatusCode(200);
            response.setMessage("number of users by country fetched successful");
            response.setCount(count);
        }catch (RuntimeException e){
            response.setStatusCode(500);
            response.setError("Error Occurred while fetching number of users by country " + e.getMessage());
        }
        return response;
    }

    @Override
    public RequestResponse countUsersByCountry() {
        RequestResponse response = new RequestResponse();
        try{
            List<CountryUserCount> countries = usersRepo.countUsersByCountry();
            response.setMessage("number of users by country fetched successful");
            response.setCountryUserCount(countries);
        }catch (RuntimeException e){
            response.setStatusCode(500);
            response.setError("Error Occurred while fetching number of users and their countries " + e.getMessage());
            response.setUsersList(new ArrayList<>());
        }
        return response;
    }

    public List<Users> country(String country) {
        List<Users> same = usersRepo.findAll();
        List<Users> add = new ArrayList<>();
        for (Users users: same){
            if(users.getCountry().equalsIgnoreCase(country)){
                add.add(users);

            }
        }
        if(!add.isEmpty()) return add;
        else return new ArrayList<>();
    }


}
