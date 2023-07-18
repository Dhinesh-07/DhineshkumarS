package com.atdxt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

@Entity
@Table(name = "dhinesh_demo3")
@Setter
@Getter
public class UserEncrypt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmpassword;

    @Column(name = "created_on")
    private String created_on;

    @Column(name = "modify_time")
    private String modify_time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserEntity user;

    public void encryptPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
        this.confirmpassword = passwordEncoder.encode(this.confirmpassword);
    }




}