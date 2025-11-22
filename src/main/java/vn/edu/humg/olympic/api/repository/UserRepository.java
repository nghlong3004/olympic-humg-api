package vn.edu.humg.olympic.api.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vn.edu.humg.olympic.api.model.User;

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
                        #{gender}::gender,
                        #{birthday},
                        #{role}::role,
                        #{phone},
                        #{universityName},
                        #{facultyName},
                        #{avatarUrl},
                        #{isActive},
                        NOW(),
                        NOW()
                    )
            """)
    void save(User user);

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
                WHERE id = #{id}
                LIMIT 1
            """)
    Optional<User> findById(Long id);
}
