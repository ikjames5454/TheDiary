package com.authorization.jwtAuthorization.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary")
@Data
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "diary")
    @ToString.Exclude
    @JsonManagedReference(value = "diary-user")
    private Users user;
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "diary-entry")
    private List<Entry> entry = new ArrayList<>();
    private String diaryName;
    private String imageUrl;
    private String name;
    private LocalDate createdAt = LocalDate.now();
    private LocalTime timeAt = LocalTime.now();

}
