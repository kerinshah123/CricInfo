package com.example.criinfo.Home;

public class Matchpojo {

    public String series;
    public String typeofmatch;
    public String matchdescription;
    public String venue;
    public String result;
    public String team1;
    public String team2;
    public String score1;
    public String score2;
    public String matchid;

    public Matchpojo(String series, String typeofmatch, String matchdescription, String venue, String result, String team1, String team2, String score1, String score2, String matchid) {
        this.series = series;
        this.typeofmatch = typeofmatch;
        this.matchdescription = matchdescription;
        this.venue = venue;
        this.result = result;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
        this.matchid=matchid;
    }

    public Matchpojo() {

    }

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTypeofmatch() {
        return typeofmatch;
    }

    public void setTypeofmatch(String typeofmatch) {
        this.typeofmatch = typeofmatch;
    }

    public String getMatchdescription() {
        return matchdescription;
    }

    public void setMatchdescription(String matchdescription) {
        this.matchdescription = matchdescription;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }
}
