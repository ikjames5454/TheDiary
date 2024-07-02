package com.authorization.jwtAuthorization.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "entry")
@Data
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private LocalDate createdAt = LocalDate.now();
    private LocalTime createAt = LocalTime.now();
    private static LocalDateTime lastUpdated = LocalDateTime.MIN;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    @JsonBackReference(value = "diary-entry")
    private Diary diary;



}
