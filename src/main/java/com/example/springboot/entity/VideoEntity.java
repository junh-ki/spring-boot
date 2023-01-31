package com.example.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
public class VideoEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    public VideoEntity(String name, String description) {
        this.id = null;
        this.name = name;
        this.description = description;
    }

}
