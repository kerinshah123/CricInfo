package com.example.criinfo.More;

public class PointsTablePojo {

    String teamId;
    int matchPlayed;
    int matchloss;
    int matchwin;
    int point;

    public PointsTablePojo() {
    }

    public PointsTablePojo(String teamId, int matchPlayed, int matchloss, int matchwin, int point) {
        this.teamId = teamId;
        this.matchPlayed = matchPlayed;
        this.matchloss = matchloss;
        this.matchwin = matchwin;
        this.point = point;
    }

    public String getTeamId()
    {
        return teamId;
    }

    public void setTeamId(String teamId)
    {
        this.teamId = teamId;
    }

    public int getMatchPlayed()
    {
        return matchPlayed;
    }

    public void setMatchPlayed(int matchPlayed)
    {
        this.matchPlayed = matchPlayed;
    }

    public int getMatchloss()
    {
        return matchloss;
    }

    public void setMatchloss(int matchloss)
    {
        this.matchloss = matchloss;
    }

    public int getMatchwin()
    {
        return matchwin;
    }

    public void setMatchwin(int matchwin)
    {
        this.matchwin = matchwin;
    }

    public int getPoint()
    {
        return point;
    }

    public void setPoint(int point)
    {
        this.point = point;
    }
}
