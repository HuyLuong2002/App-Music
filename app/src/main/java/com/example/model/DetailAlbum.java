package com.example.model;

public class DetailAlbum {
    private int idAlbum;
    private int idSong;

    public DetailAlbum() {
    }

    public DetailAlbum(int idAlbum, int idSong) {
        this.idAlbum = idAlbum;
        this.idSong = idSong;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }
}
