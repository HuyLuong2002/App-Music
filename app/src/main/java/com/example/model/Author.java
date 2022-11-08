package com.example.model;

public class Author {
    private int idAuthor;
    private String authorName;

    public Author() {
    }

    public Author(int idAuthor, String authorName) {
        this.idAuthor = idAuthor;
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }
}
