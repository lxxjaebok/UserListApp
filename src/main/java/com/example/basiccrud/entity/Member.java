package com.example.basiccrud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    private int age;

    @Column(name = "address", length = 100)
    private String address;
}
