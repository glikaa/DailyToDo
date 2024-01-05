package com.example.tasks.dto;

public class CategoryDto {
    private int id;
    private String name;

    // Constructors, Getters and Setters
    public CategoryDto() {}

    public CategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
