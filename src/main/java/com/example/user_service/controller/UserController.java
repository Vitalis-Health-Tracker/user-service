package com.example.user_service.controller;

import com.example.user_service.exception.UserDoesNotExistException;
import com.example.user_service.model.User;
import com.example.user_service.service.UserServices;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userServices.getUserByEmail(email);
    }

    @GetMapping("/retrieve-id/{email}")
    public String getUserById(@PathVariable String email){
        return userServices.getUserByEmail(email).getUId();
    }

    @PostMapping("/{email}")
    public Boolean createAcc(@PathVariable String email){
        User user = new User();
        user.setEmail(email);
        if(userServices.saveUser(user) != null)
            return true;
        else
            return false;
    }

    @PutMapping("/{userId}")
    public String updateAcc(@PathVariable String userId, @RequestBody User user) throws UserDoesNotExistException {
        if(userServices.getUserById(userId) != null){
            userServices.updateUser(userId, user);
            return "User updated successfully";
        }
        return "User does not exist";
    }

    @GetMapping("/{userId}/get-details")
    public User getUser(@PathVariable String userId){
        return userServices.getUserById(userId);
    }

    @GetMapping("/get-all-ids")
    public List<String> getAllUserIds(){
        return userServices.getAllUserIds();
    }
}
