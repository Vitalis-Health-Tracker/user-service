package com.example.user_service.service;

import com.example.user_service.exception.UserDoesNotExistException;
import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
            Float bmi = newUser.getWeight()/((newUser.getHeight()/100) * (newUser.getHeight()/100));
            oldUser.setBmi(bmi);
            userRepo.save(oldUser);
            return oldUser;
        }
        else
        {
            throw new UserDoesNotExistException();
        }
    }

    public boolean deleteUser(String uId){
        User user = userRepo.findById(uId).orElse(null);
        if(user != null) {
            userRepo.deleteById(uId);
            if (getUserById(uId) != null)
            {
                return false;
            }
            if(deleteFromAuth(user.getEmail()))
            {
                return true;
            }
        }
        return false;
    }

    private Boolean deleteFromAuth(String email)
    {
        RestClient restClient = RestClient.create();
        return restClient.delete()
                .uri("http://localhost:9090/auth/" + email)
                .retrieve()
                .body(Boolean.class);
    }

    public List<String> getAllUserIds(){
        List<User> users = userRepo.findAll();
        return users.stream().map(User::getUId).toList();
    }
}
