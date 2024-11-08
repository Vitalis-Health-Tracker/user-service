package com.example.user_service.repository;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
    public User getUserByEmail(String email);
}