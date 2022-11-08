package com.example.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String dbname = "AppMusic.db";
    private static final String SQL_CREATE_TABLE_SONG = "create table Song (id integer primary key autoincrement, name text, img text, pathSong text, pathLyric text, idAuthor integer, idSinger integer, checkFavorite integer)";
    private static final String SQL_CREATE_TABLE_SINGER = "create table Singer (id integer primary key autoincrement, name text)";
    private static final String SQL_CREATE_TABLE_AUTHOR = "create table Author (id integer primary key autoincrement, name text)";
    private static final String SQL_CREATE_TABLE_ALBUM = "create table Album (id integer primary key autoincrement, name text)";
    private static final String SQL_CREATE_TABLE_DETAIL_ALBUM = "create table DetailAlbum (id integer primary key autoincrement, idSong integer)";
    private static final String SQL_CREATE_TABLE_USER = "create table User (id integer primary key autoincrement, username text, password text, idAuth integer)";



    public DBHelper(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SONG);
        db.execSQL(SQL_CREATE_TABLE_SINGER);
        db.execSQL(SQL_CREATE_TABLE_AUTHOR);
        db.execSQL(SQL_CREATE_TABLE_ALBUM);
        db.execSQL(SQL_CREATE_TABLE_DETAIL_ALBUM);
        db.execSQL(SQL_CREATE_TABLE_USER);

        System.out.println("Tạo bảng thành công");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Song");
        db.execSQL("DROP TABLE IF EXISTS Singer");
        db.execSQL("DROP TABLE IF EXISTS Author");
        db.execSQL("DROP TABLE IF EXISTS Album");
        db.execSQL("DROP TABLE IF EXISTS DetailAlbum");
        db.execSQL("DROP TABLE IF EXISTS User");

        onCreate(db);
    }

    public String addRecordSong(String p1, String p2, String p3, String p4, int p5, int p6, int p7) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", p1);
        cv.put("img", p2);
        cv.put("pathSong", p3);
        cv.put("pathLyric", p4);
        cv.put("idAuthor", p5);
        cv.put("idSinger", p6);
        cv.put("checkFavorite", p7);

        long res = db.insert("Song", null, cv);
        if (res == -1)
            return "Thêm dữ liệu thất bại";
        else
            return "Thêm dữ liệu thành công";
    }

    public Cursor getDataSong() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Song", null);
        return cursor;
    }

    public int updateRecordSongFavoriteCheck(int id, String p1, String p2, String p3, String p4, int p5, int p6, int p7) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", p1);
        cv.put("img", p2);
        cv.put("pathSong", p3);
        cv.put("pathLyric", p4);
        cv.put("idAuthor", p5);
        cv.put("idSinger", p6);
        cv.put("checkFavorite", p7);

        db.update("Song", cv, "id=" + id, null);
        return p7;
    }

    public String updateRecordSong(int id, String p1, String p2, String p3, String p4, int p5, int p6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", p1);
        cv.put("img", p2);
        cv.put("pathSong", p3);
        cv.put("pathLyric", p4);
        cv.put("idAuthor", p5);
        cv.put("idSinger", p6);

        int res = db.update("Song", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public String deleteRecordSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete("Song", "id=" + id, null);
        if (res == -1)
            return "Xóa dữ liệu thất bại";
        else
            return "Xóa dữ liệu thành công";
    }

    public String updateRecordAlbum(int id, String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", p1);

        int res = db.update("Album", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public String deleteRecordAlbum(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int res = db.delete("Album", "id=" + id, null);
        if (res == -1)
            return "Xóa dữ liệu thất bại";
        else
            return "Xóa dữ liệu thành công";
    }

    public Cursor getDataSinger() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Singer", null);
        return cursor;
    }


    public String addRecordSinger(String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", p1);

        long res = db.insert("Singer", null, cv);
        if (res == -1)
            return "Thêm dữ liệu thất bại";
        else
            return "Thêm dữ liệu thành công";
    }

    public String updateRecordSinger(int id, String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", p1);

        long res = db.update("Singer", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public String deleteRecordSinger(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete("Singer", "id=" + id, null);
        if (res == -1)
            return "Xóa dữ liệu thất bại";
        else
            return "Xóa dữ liệu thành công";
    }


    public Cursor getDataAuthor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Author", null);
        return cursor;
    }

    public String addRecordAuthor(String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", p1);

        long res = db.insert("Author", null, cv);
        if (res == -1)
            return "Thêm dữ liệu thất bại";
        else
            return "Thêm dữ liệu thành công";
    }


    public String updateRecordAuthor(int id, String p1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", p1);

        long res = db.update("Author", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public String deleteRecordAuthor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete("Author", "id=" + id, null);
        if (res == -1)
            return "Xóa dữ liệu thất bại";
        else
            return "Xóa dữ liệu thành công";
    }

    public String addRecordAlbum(String p1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", p1);

        long res = db.insert("Album", null, cv);
        if (res == -1)
            return "Thêm dữ liệu thất bại";
        else
            return "Thêm dữ liệu thành công";
    }

    public Cursor getDataAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User", null);
        return cursor;
    }

    public Cursor getDataUser(String p1, String p2) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where username='" +  p1 + "'" + " and " + "password='" + p2 + "'", null);
        return cursor;
    }

    public String addRecordUser(String p1, String p2, int idAuth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", p1);
        cv.put("password", p2);
        cv.put("idAuth", idAuth);


        long res = db.insert("User", null, cv);
        if (res == -1)
            return "Đăng kí thất bại";
        else
            return "Đăng kí thành công";
    }

    public String updateRecordUser(int id, int p3) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("idAuth", p3);

        long res = db.update("User", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public String updateRecordUserPassword(int id, String p1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("password", p1);

        long res = db.update("User", cv, "id=" + id, null);
        if (res == -1)
            return "Sửa dữ liệu thất bại";
        else
            return "Sửa dữ liệu thành công";
    }

    public Cursor checkUserRegistered(String p1) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where username='" + p1 + "'", null);
        return cursor;
    }

    public Cursor checkUserLogin(String p1, String p2) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where username='" + p1 + "'" + " and " + "password='" + p2 + "'", null);
        return cursor;
    }

    public Cursor getDataAlbum() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Album", null);
        return cursor;
    }

    public Cursor getDataDetailAlbum() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DetailAlbum", null);
        return cursor;
    }

    public String addRecordDetailAlbum(int p1, int p2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("id", p1);
        cv.put("idSong", p2);

        long res = db.insert("DetailAlbum", null, cv);
        if (res == -1)
            return "Thêm dữ liệu thất bại";
        else
            return "Thêm dữ liệu thành công";
    }

    public Cursor getSaveDetailAlbum(int idSong) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from DetailAlbum where idSong=" + idSong, null);
        return cursor;
    }


}
