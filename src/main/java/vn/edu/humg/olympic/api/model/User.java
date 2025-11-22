package vn.edu.humg.olympic.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Gender gender;
    private Date birthday;
    private Role role;
    private String phone;
    private String universityName;
    private String facultyName;
    private String avatarUrl;
    private Boolean isActive;
    private Timestamp created;
    private Timestamp updated;
}
