package com.example.model;

public class Singer {
    private int idSinger;
    private String singerName;

    public Singer() {
    }

    public Singer(int idSinger, String singerName) {
        this.idSinger = idSinger;
        this.singerName = singerName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(int idSinger) {
        this.idSinger = idSinger;
    }
}
