package com.atdxt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Base64;


@Entity
@Table(name = "dhinesh_demo3")
@Setter
@Getter
public class UserEntity3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_on")
    private String created_on;

    @Column(name = "modify_time")
    private String modify_time;

    public void encryptPassword() {
        this.password = Base64.getEncoder().encodeToString(this.password.getBytes());
    }


    public void decryptPassword() {
        this.password = new String(Base64.getDecoder().decode(this.password));
    }


}