package com.atdxt;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "dhinesh_demo")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserEntity2 userEntity2;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public UserEntity2 getUserEntity2() {
        return userEntity2;
    }

    public void setUserEntity2(UserEntity2 userEntity2) {
        this.userEntity2 = userEntity2;
    }

    public String getFormattedDate() {
        return formatDate(date);
    }

    public void setFormattedDate(String formattedDate) {
        this.date = parseDate(formattedDate);
    }

    public String getFormattedModifiedDate() {
        return formatDate(modifiedDate);
    }

    public void setFormattedModifiedDate(String formattedModifiedDate) {
        this.modifiedDate = parseDate(formattedModifiedDate);
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private LocalDateTime parseDate(String formattedDate) {
        return LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
