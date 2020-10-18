package com.example.criinfo.More.MyTournamentTabs;

import java.util.ArrayList;

public class Team {
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

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public ArrayList<String> getPlayers() {
        return matchDate;
    }

    public void setPlayers(ArrayList<String> players) {
        this.matchDate = matchDate;
    }

    String city,country,image,managerId,name,sortname;
    ArrayList<String> matchDate;
    ArrayList<String> leagueId;

    public ArrayList<String> getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(ArrayList<String> matchDate) {
        this.matchDate = matchDate;
    }

    public ArrayList<String> getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(ArrayList<String> leagueId) {
        this.leagueId = leagueId;
    }
}
