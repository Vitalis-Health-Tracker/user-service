package com.example.user_service.repository;

import com.example.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
    public User getUserByEmail(String email);

    @Query(value = "{}", fields = "{'_id': 1}")
    public List<String> getUserId();
}
