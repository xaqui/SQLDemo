package com.example.sqldemo;

public class CustomerModel {

    private int id;
    private String name;
    private int age;
    private boolean isActive;

    public CustomerModel(int id) {
        this.id = id;
    }

    public CustomerModel(int id, String name, int age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public boolean isActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "["+id+"] " + name + " | Age: "+age + " | " + (isActive ? "ACTIVE" : "INACTIVE");
    }
}
