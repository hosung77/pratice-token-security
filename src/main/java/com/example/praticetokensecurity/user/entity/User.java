package com.example.praticetokensecurity.user.entity;

import com.example.praticetokensecurity.common.entity.TimeStamped;
import com.example.praticetokensecurity.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.UpdateBuilder;

@Entity
@Table(name ="users")
@NoArgsConstructor
@Getter
public class User extends TimeStamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String email, String password, UserRole userRole){
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public static User of (String email, String password, UserRole userRole){
        return new User(email,password,userRole);
    }
}
