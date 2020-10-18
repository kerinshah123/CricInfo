package com.example.criinfo.More.MyTeamTabs;

import java.util.ArrayList;

public class Players {
    String name,role,age,city,country,image;

    public ArrayList<String> getTeamId() {
        return teamId;
    }

    public void setTeamId(ArrayList<String> teamId) {
        this.teamId = teamId;
    }

    ArrayList<String> teamId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
