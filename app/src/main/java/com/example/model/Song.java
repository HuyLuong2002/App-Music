package com.example.model;

public class Song {
    private int idSong;
    private String songName;
    private String songImg;
    private String songPath;
    private String lyricPath;
    private int idAuthor;
    private int idSinger;
    private int checkFavorite;


    public Song() {}

    public Song(int idSong, String songName, String songImg, String songPath, String lyricPath, int idAuthor, int idSinger, int checkFavorite) {
        this.idSong = idSong;
        this.songName = songName;
        this.songImg = songImg;
        this.songPath = songPath;
        this.lyricPath = lyricPath;
        this.idAuthor = idAuthor;
        this.idSinger = idSinger;
        this.checkFavorite = checkFavorite;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongImg() {
        return songImg;
    }

    public void setSongImg(String songImg) {
        this.songImg = songImg;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public String getLyricPath() {
        return lyricPath;
    }

    public void setLyricPath(String lyricPath) {
        this.lyricPath = lyricPath;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(int idSinger) {
        this.idSinger = idSinger;
    }

    public int getCheckFavorite() {
        return checkFavorite;
    }

    public void setCheckFavorite(int checkFavorite) {
        this.checkFavorite = checkFavorite;
    }
}
