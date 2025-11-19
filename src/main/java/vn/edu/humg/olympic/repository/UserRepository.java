package vn.edu.humg.olympic.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vn.edu.humg.olympic.model.User;

import java.util.Optional;

@Mapper
public interface UserRepository {
    @Insert("""
            INSERT INTO user_humg (
                        first_name, last_name, email, password_hash,
                        gender, birthday, role,
                        phone, university_name, faculty_name, avatar_url,
                        is_active, created, updated
                    ) VALUES (
                        #{firstName},
                        #{lastName},
                        #{email},
                        #{passwordHash},
                        #{gender}::user_gender,
                        #{birthday},
                        #{role}::user_role,
                        #{phone},
                        #{universityName},
                        #{facultyName},
                        #{avatarUrl},
                        #{isActive},
                        NOW(),
                        NOW()
                    )
            """)
    void insert(User user);

    @Select("""
                SELECT
                    id,
                    first_name,
                    last_name,
                    email,
                    password_hash,
                    gender,
                    birthday,
                    role,
                    phone,
                    university_name,
                    faculty_name,
                    avatar_url,
                    is_active,
                    created,
                    updated
                FROM user_humg
                WHERE email = #{email}
                LIMIT 1
            """)
    Optional<User> findByEmail(String email);

}
