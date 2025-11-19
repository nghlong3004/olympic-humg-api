package vn.edu.humg.olympic.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private UserGender gender;
    private Date birthday;
    private UserRole role;
    private String phone;
    private String universityName;
    private String facultyName;
    private String avatarUrl;
    private Boolean isActive;
    private Timestamp created;
    private Timestamp updated;
}
