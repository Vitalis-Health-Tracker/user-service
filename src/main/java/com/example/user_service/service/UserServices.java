package com.example.user_service.service;

import com.example.user_service.exception.UserDoesNotExistException;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    @CircuitBreaker(name = "cirCreateUser" , fallbackMethod = "fallbackCreateUser")
    public User saveUser(User user){
        return userRepo.save(user);
    }

    public User fallbackCreateUser(User user){
        return new User();
    }

    public  User getUserById(String uId){
        return userRepo.findById(uId).orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepo.getUserByEmail(email);
    }

    public User updateUser(String uId, User newUser) throws UserDoesNotExistException {
        User oldUser = userRepo.findById(uId).orElse(null);
        if(oldUser != null)
        {
            oldUser.setUserName(newUser.getUserName());
            oldUser.setAge(newUser.getAge());
            oldUser.setWeight(newUser.getWeight());
            oldUser.setHeight(newUser.getHeight());
            oldUser.setGender(newUser.getGender());
            oldUser.setJourney(newUser.getJourney());
            Float bmi = newUser.getWeight()/(newUser.getHeight() * newUser.getHeight());
            newUser.setBmi(bmi);
            userRepo.save(oldUser);
            return oldUser;
        }
        else
        {
            throw new UserDoesNotExistException();
        }
    }

    public boolean deleteUser(String uId){
        if(getUserById(uId) == null) {
            userRepo.deleteById(uId);
            if (getUserById(uId) != null) {
                return false;
            }
            return true;
        }
        return false;
    }

    public List<String> getAllUserIds(){
        return userRepo.getUserId();
    }
}
