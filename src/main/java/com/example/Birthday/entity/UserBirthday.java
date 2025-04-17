package com.example.planAndGoCRUD.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_birthday") // 테이블 이름 지정
public class UserBirthday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate; // 생일

    // 🟢 기본 생성자 (JPA에서 필수)
    public UserBirthday() {
    }

    // 🟢 생성자
    public UserBirthday(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    // 🟢 Getter/Setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
