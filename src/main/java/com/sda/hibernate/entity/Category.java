package com.sda.hibernate.entity;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50, nullable = false , unique = true)
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
