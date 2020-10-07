package com.example.criinfo;

public class pojoplayer {

    public String playername;

    public String playerage;

    public String playertype;
    public int playerimage;

    public pojoplayer() {
    }

    public pojoplayer(String playername, String playerage, String playertype, int playerimage) {
        this.playername = playername;
        this.playerage = playerage;
        this.playertype = playertype;
        this.playerimage=playerimage;
    }

    public String getPlayername(
    ) {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public String getPlayerage() {
        return playerage;
    }

    public void setPlayerage(String playerage) {
        this.playerage = playerage;
    }

    public String getPlayertype() {
        return playertype;
    }

    public void setPlayertype(String playertype) {
        this.playertype = playertype;
    }

    public int getPlayerimage() {
        return playerimage;
    }

    public void setPlayerimage(int playerimage) {
        this.playerimage = playerimage;
    }
}
