package com.atdxt;
import com.atdxt.UserEntity;
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
    private Integer id;
    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "created_on")
    private String created_on;

    @Column(name = "modify_time")
    private String modify_time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserEntity user;
    public UserEntity2(){
    }
}