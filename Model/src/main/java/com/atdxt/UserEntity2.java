package com.atdxt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "dhinesh_demo2")
public class UserEntity2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "image_url")
    private String imageUrl;




    @Column(name = "created_on")
    private String created_on;

    @Column(name = "modify_time")
    private String modify_time;


  /* *//* @Column(name = "user_id", nullable = false)
    private Integer user_id;
*//*
   @OneToOne(fetch = FetchType.LAZY)
   @PrimaryKeyJoinColumn
   private UserEntity user;*/

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "user_id")
    private UserEntity user;
    public UserEntity2(){
    }
}