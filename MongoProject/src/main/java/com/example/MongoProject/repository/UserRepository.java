package com.example.MongoProject.repository
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);

    
}