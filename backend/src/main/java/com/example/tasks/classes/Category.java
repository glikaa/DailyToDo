package com.example.tasks.classes;
import jakarta.persistence.*;

@Entity
@Table(name="Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int id;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String name;

    //Getters and setters
}
