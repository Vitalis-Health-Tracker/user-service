package com.example.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String uId;
    private String email;
    private String userName;
    private float height;
    private float weight;
    private String gender;
    private double bmi;
    private enum journey{
        MUSCLE_GAIN,
        WEIGHT_LOSS,
        MAINTAIN
    }
}
