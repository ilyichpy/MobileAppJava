package com.example.cattleapp.models;

public class Animal {
    private int id;
    private String name;
    private int age;
    private String type;
    private String feedingTime;
    private int ownerId;

    public Animal() {
    }

    public Animal(String name, int age, String type, String feedingTime, int ownerId) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.feedingTime = feedingTime;
        this.ownerId = ownerId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeedingTime() {
        return feedingTime;
    }

    public void setFeedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
