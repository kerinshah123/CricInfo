package com.example.criinfo.More;

public class Schedule {

    private String team1;
    private String team2;
    private String LeagueId;

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

    public String getLeagueId() {
        return LeagueId;
    }

    public void setLeagueId(String leagueId) {
        LeagueId = leagueId;
    }

    public String getMatchBall() {
        return matchBall;
    }

    public void setMatchBall(String matchBall) {
        this.matchBall = matchBall;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    private String matchBall;
    private String matchDate;
    private String matchType;

}
