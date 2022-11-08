package com.example.model;

public class Album {
    private int idAlbum;
    private String albumName;

    public Album() {
    }

    public Album(int idAlbum, String albumName) {
        this.idAlbum = idAlbum;
        this.albumName = albumName;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

}
