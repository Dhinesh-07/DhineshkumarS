package com.atdxt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
        this.password = Base64.getEncoder().encodeToString(this.password.getBytes());
        this.confirmpassword=Base64.getEncoder().encodeToString(this.confirmpassword.getBytes());

    }

    public void decryptPassword() {
        this.password = new String(Base64.getDecoder().decode(this.password));
        this.confirmpassword=new String(Base64.getDecoder().decode(this.confirmpassword));
    }


   /* public void encryptconfirmPassword() {
        this.confirmpassword=Base64.getEncoder().encodeToString(this.confirmpassword.getBytes());
    }



    public void decryptconfirmPassword() {
        this.password=new String(Base64.getDecoder().decode(this.confirmpassword));
    }
*/

}