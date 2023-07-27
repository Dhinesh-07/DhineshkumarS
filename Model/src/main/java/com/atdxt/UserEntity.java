package com.atdxt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "dhinesh_demo")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;



    @Column(name = "phone_number")

    private String phone_number;

    @Column(name = "created_on")
    private String created_on;

    @Column(name = "modify_time")
    private String modify_time;


    public UserEntity(){
    }



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    UserEntity2 userEntity2;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    UserEncrypt userEncrypt;




}