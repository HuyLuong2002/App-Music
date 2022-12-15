package com.example.app_music;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.example.custom.CustomAdapterAlbum;
import com.example.custom.CustomAdapterAuthor;
import com.example.custom.CustomAdapterFavorite;
import com.example.custom.CustomAdapterSinger;
import com.example.custom.CustomAdapterUser;
import com.example.fragment.AuthorFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.SingerFragment;
import com.example.fragment.UserFragment;
import com.example.model.Album;
import com.example.model.Author;
import com.example.model.DetailAlbum;
import com.example.model.Singer;
import com.example.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.DAO.DBHelper;
import com.example.custom.CustomAdapterSong;
import com.example.model.Song;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SINGER = 1;
    private static final int FRAGMENT_AUTHOR = 2;
    private static final int FRAGMENT_USER = 3;
    private int currentFragment = FRAGMENT_HOME;

    public static ArrayList<Song> list_song = new ArrayList<>();
    public static ArrayList<Singer> list_singer = new ArrayList<>();
    public static ArrayList<Author> list_author = new ArrayList<>();
    public static ArrayList<Album> list_album = new ArrayList<>();
    public static ArrayList<User> list_user = new ArrayList<>();
    public static ArrayList<User> list_all_user = new ArrayList<>();
    public static ArrayList<DetailAlbum> list_detail_album = new ArrayList<>();


    private static int index_music;
    private CustomAdapterSong customAdapterSong = null;
    private CustomAdapterFavorite customAdapterFavorite = null;
    private CustomAdapterSinger customAdapterSinger = null;
    private CustomAdapterAuthor customAdapterAuthor = null;
    private CustomAdapterUser customAdapterUser = null;
    private CustomAdapterAlbum customAdapterAlbum = null;

    private DrawerLayout drawerLayout;
    private Dialog dialog;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private CardView cardView;
    private ListView lv, listView;

    private View album_view = null;
    private ListView listView7;
    private TextView textView6, textView7, textView17;
    private ImageView imageView2;
    private Button btn_Huy, btn_ThemBaiHat, btn_SuaBaiHat, btn_XoaBaiHat;
    private ImageButton imageButton9, imageButton10;
    private EditText edit_tenbaihat, edit_anhbaihat, edit_duongdanbaihat, edit_duongdanloibaihat, edit_macasi, edit_matacgia;
    private EditText edit_tenAlbum, edit_tenCaSi, edit_tenTacGia, edit_maAuth;
    //Mã kết quả activity
    private int requestCode_Song = 1;
    private int requestCode_Image = 2;
    private int requestCode_Lyric = 3;
    private int requestCode_Singer_AddSong = 4;
    private int requestCode_selectSong = 5;
    private int requestCode_Author_AddSong = 6;
    private int requestCode_Album = 7;
    private int requestCode_Singer_AddSinger = 8;
    private int requestCode_Author_AddAuthor = 9;
    private int requestCode_User = 10;
    private int requestCode_selectDelSong = 11;
    //Mã sửa và xóa từ form dialog chọn phần tử listview activity trả về
    private int update_id_song = -1;
    private int update_id_album = -1;
    private int update_id_singer = -1;
    private int update_id_author = -1;
    private int update_id_user = -1;
    private int delete_id_song = -1;
    private int delete_id_album = -1;
    private int delete_id_singer = -1;
    private int delete_id_author = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Đọc dữ liệu
        getDataSong();
        getDataSinger();
        getDataAuthor();
        getDataAlbum();
        getDataUser();
        getDataDetailAlbum();
        //Tải dữ liệu lên custom adapter song
        customAdapterSong = new CustomAdapterSong(this
                , R.layout.my_list_song, list_song);
        lv = findViewById(R.id.ListView1);
        lv.setAdapter(customAdapterSong);

        //Tạo dialog cho button thêm
        dialog = new Dialog(MainActivity.this);
        //find view
        imageButton9 = findViewById(R.id.imageButton9);
        imageButton10 = findViewById(R.id.imageButton10);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        cardView = findViewById(R.id.CardView);
        imageView2 = findViewById(R.id.imageView2);
        imageButton9.setOnClickListener(this);
        imageButton10.setOnClickListener(this);
        cardView.setOnClickListener(this);

        if (imageView2 != null && textView6 != null && textView7 != null && list_song.size() != 0) {
            if (!MainActivity.list_song.get(SecondActivity.flag_music).getSongImg().equals("unknown")) {
                Glide.with(this).load(MainActivity.list_song.get(SecondActivity.flag_music).getSongImg()).into(imageView2);
            } else {
                Glide.with(this).load(R.drawable.music_image).into(imageView2);
            }
            textView6.setText(list_song.get(SecondActivity.flag_music).getSongName());
            Song song = list_song.get(SecondActivity.flag_music);
            String singer_name = "";
            for (Singer singer : list_singer) {
                if (song.getIdSinger() != -1) {
                    if (song.getIdSinger() == singer.getIdSinger()) {
                        singer_name = singer.getSingerName();
                    }
                } else {
                    singer_name = "unknown";
                }
            }
            textView7.setText(singer_name);

        }
        //Xử lý toolbar
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.inflateMenu(R.menu.app_bar_music_menu);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        if (list_user.size() > 0) {
            if (list_user.get(0).getId_auth() == 0) {
                toolbar.getMenu().findItem(R.id.update_song).setVisible(false);
                toolbar.getMenu().findItem(R.id.delete_song).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_singer).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_author).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
            } else {
                getDataAllUser();
            }
        }

        textView17 = navigationView.getHeaderView(0).findViewById(R.id.textView17); // Tên username
        if(list_user.size() > 0)
            textView17.setText(list_user.get(0).getUsername());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_song:
                        handleMusicAddSong();
                        return true;
                    case R.id.update_song:
                        handleMusicUpdateSong();
                        return true;
                    case R.id.delete_song:
                        handleMusicDeleteSong();
                        return true;
                    case R.id.add_album:
                        handleAlbumAddAlbum();
                        return true;
                    case R.id.update_album:
                        handleAlbumUpdateAlbum();
                        return true;
                    case R.id.delete_album:
                        handleAlbumDeleteAlbum();
                        return true;
                    case R.id.add_singer:
                        handleSingerAddSinger();
                        return true;
                    case R.id.update_singer:
                        handleSingerUpdateSinger();
                        return true;
                    case R.id.delete_singer:
                        handleSingerDeleteSinger();
                        return true;
                    case R.id.add_author:
                        handleAuthorAddAuthor();
                        return true;
                    case R.id.update_author:
                        handleAuthorUpdateAuthor();
                        return true;
                    case R.id.delete_author:
                        handleAuthorDeleteAuthor();
                        return true;
                    case R.id.update_user:
                        handleUserUpdateUser();
                        return true;

                }
                return false;
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.page_1:
                        showAppBarMusicMenu();
                        showMusicAndFavoriteView();
                        //Tải dữ liệu lên custom adapter song
                        customAdapterSong = new CustomAdapterSong(MainActivity.this
                                , R.layout.my_list_song, list_song);
                        lv.setAdapter(customAdapterSong);
                        return true;
                    case R.id.page_2:
                        toolbar.getMenu().clear();
                        showMusicAndFavoriteView();
                        //Tải dữ liệu lên custom adapter favorite
                        customAdapterFavorite = new CustomAdapterFavorite(MainActivity.this
                                , R.layout.my_list_song, list_song);
                        lv.setAdapter(customAdapterFavorite);
                        return true;
                    case R.id.page_3:
                        showAppBarAlbumMenu();
                        lv.setVisibility(View.INVISIBLE);
                        if (album_view == null) {
                            album_view = getLayoutInflater().inflate(R.layout.activity_ford, null, false);
                            album_view.setX(0);
                            album_view.setY(350);
                            drawerLayout.addView(album_view, 1);
                        } else {
                            album_view.setVisibility(View.VISIBLE);
                        }
                        listView7 = album_view.findViewById(R.id.ListView7);
                        customAdapterAlbum = new CustomAdapterAlbum(MainActivity.this, R.layout.my_list_album, list_album);
                        listView7.setAdapter(customAdapterAlbum);
                        listView7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MainActivity.this, NineActivity.class);
                                intent.putExtra("album_id", String.valueOf(list_album.get(position).getIdAlbum()));
                                startActivity(intent);
                            }
                        });
                        return true;
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                index_music = (int) lv.getItemIdAtPosition(position);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("index_music", index_music);
                if (SecondActivity.mediaPlayer != null) {
                    SecondActivity.mediaPlayer.stop();
                    SecondActivity.mediaPlayer.release();
                }
                startActivity(intent);
            }
        });
    }

    public void showMusicAndFavoriteView() {
        lv.setVisibility(View.VISIBLE);
        if (album_view != null)
            album_view.setVisibility(View.INVISIBLE);
    }

    public void showAppBarMusicMenu() {
        toolbar.getMenu().clear();
        if (list_user.get(0).getId_auth() == 0) {
            toolbar.getMenu().findItem(R.id.update_song).setVisible(false);
            toolbar.getMenu().findItem(R.id.delete_song).setVisible(false);
        }
        toolbar.inflateMenu(R.menu.app_bar_music_menu);
    }

    public void showAppBarAlbumMenu() {
        toolbar.getMenu().clear();
        if (list_user.get(0).getId_auth() == 0)
            toolbar.inflateMenu(R.menu.app_bar_album_menu);
    }

    public void showAppBarSingerMenu() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.app_bar_singer_menu);
    }

    public void showAppBarUserMenu() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.app_bar_user_menu);
    }

    public void showAppBarAuthorMenu() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.app_bar_author_menu);
    }

    public void handleMusicAddSong() {
        dialog.setContentView(R.layout.layout_dialog_add_song);
        edit_tenbaihat = dialog.findViewById(R.id.edit_tenbaihat);
        edit_anhbaihat = dialog.findViewById(R.id.edit_anhbaihat);
        edit_duongdanbaihat = dialog.findViewById(R.id.edit_duongdanbaihat);
        edit_duongdanloibaihat = dialog.findViewById(R.id.edit_duongdanloibaihat);
        edit_macasi = dialog.findViewById(R.id.edit_macasi);
        edit_matacgia = dialog.findViewById(R.id.edit_matacgia);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        btn_ThemBaiHat = dialog.findViewById(R.id.btn_ThemBaiHat);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ThemBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                int check_favorite = 0;
                String name_song = edit_tenbaihat.getText().toString().trim();
                String img_song = edit_anhbaihat.getText().toString().trim();
                String path_song = edit_duongdanbaihat.getText().toString().trim();
                String path_lyrics = edit_duongdanloibaihat.getText().toString().trim();
                int id_singer = -1;
                int id_author = -1;
                if (edit_macasi.getText().toString().trim().equals("")) {
                    id_singer = -1;
                } else {
                    id_singer = Integer.parseInt(edit_macasi.getText().toString().trim());
                }
                if (edit_matacgia.getText().toString().trim().equals("")) {
                    id_author = -1;
                } else {
                    id_author = Integer.parseInt(edit_matacgia.getText().toString().trim());
                }

                if (name_song.equals("")) {
                    Toast.makeText(MainActivity.this, "Tên bài hát không thể trống", Toast.LENGTH_SHORT).show();
                    return;
                } else if (path_song.equals("")) {
                    Toast.makeText(MainActivity.this, "Đường dẫn bài hát không thể trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (img_song.equals("")) {
                    img_song = "unknown";
                }
                if (path_lyrics.equals("")) {
                    path_lyrics = "unknown";
                }
                String res = db.addRecordSong(name_song, img_song, path_song, path_lyrics, id_singer, id_author, check_favorite);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                getDataSong();
                lv.setAdapter(customAdapterSong);
                dialog.dismiss();
            }
        });
        edit_anhbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooserImg();
            }
        });
        edit_duongdanbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooserSong();
            }
        });
        edit_duongdanloibaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooserLyric();
            }
        });
        edit_macasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(intent, requestCode_Singer_AddSong);
            }
        });
        edit_matacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FiveActivity.class);
                startActivityForResult(intent, requestCode_Author_AddSong);
            }
        });
        dialog.show();
    }

    public void handleMusicUpdateSong() {
        dialog.setContentView(R.layout.layout_dialog_update_song);
        edit_tenbaihat = dialog.findViewById(R.id.edit_tenbaihat);
        edit_anhbaihat = dialog.findViewById(R.id.edit_anhbaihat);
        edit_duongdanbaihat = dialog.findViewById(R.id.edit_duongdanbaihat);
        edit_duongdanloibaihat = dialog.findViewById(R.id.edit_duongdanloibaihat);
        edit_macasi = dialog.findViewById(R.id.edit_macasi);
        edit_matacgia = dialog.findViewById(R.id.edit_matacgia);
        btn_SuaBaiHat = dialog.findViewById(R.id.btn_SuaBaiHat);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edit_tenbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectSong();
            }
        });
        edit_anhbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals(""))
                    OpenFileChooserImg();
            }
        });
        edit_duongdanbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals(""))
                    OpenFileChooserSong();
            }
        });
        edit_duongdanloibaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals(""))
                    OpenFileChooserLyric();
            }
        });
        edit_macasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals("")) {
                    Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                    startActivityForResult(intent, requestCode_Singer_AddSong);
                }
            }
        });
        edit_matacgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals("")) {
                    Intent intent = new Intent(MainActivity.this, FiveActivity.class);
                    startActivityForResult(intent, requestCode_Author_AddSong);
                }
            }
        });

        btn_SuaBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals("")) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String name_song = edit_tenbaihat.getText().toString().trim();
                    String img_song = edit_anhbaihat.getText().toString().trim();
                    String path_song = edit_duongdanbaihat.getText().toString().trim();
                    String path_lyrics = edit_duongdanloibaihat.getText().toString().trim();
                    int id_singer = Integer.parseInt(edit_macasi.getText().toString().trim());
                    int id_author = Integer.parseInt(edit_matacgia.getText().toString().trim());
                    String res = db.updateRecordSong(update_id_song, name_song, img_song, path_song, path_lyrics, id_singer, id_author);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataSong();
                    lv.setAdapter(customAdapterSong);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn thông tin sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void handleMusicDeleteSong() {
        dialog.setContentView(R.layout.layout_dialog_delete_song);
        edit_tenbaihat = dialog.findViewById(R.id.edit_tenbaihat);
        btn_XoaBaiHat = dialog.findViewById(R.id.btn_XoaBaiHat);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edit_tenbaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectDeleteSong();
            }
        });

        btn_XoaBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenbaihat.getText().toString().trim().equals("") || delete_id_song != -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String res = db.deleteRecordSong(delete_id_song);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataSong();
                    lv.setAdapter(customAdapterSong);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn thông tin xóa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    public void handleAlbumAddAlbum() {
        dialog.setContentView(R.layout.layout_dialog_add_album);
        Button btn_ThemAlbumMoi = dialog.findViewById(R.id.btn_ThemAlbumMoi);
        EditText edit_tenAlbum = dialog.findViewById(R.id.edit_tenAlbum);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        btn_ThemAlbumMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten_album_moi = edit_tenAlbum.getText().toString().trim();
                DBHelper db = new DBHelper(MainActivity.this);
                String res = db.addRecordAlbum(ten_album_moi);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                //update list view
                getDataAlbum();
                getDataDetailAlbum();
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleAlbumUpdateAlbum() {
        dialog.setContentView(R.layout.layout_dialog_update_album);
        Button btn_SuaAlbumMoi = dialog.findViewById(R.id.btn_SuaAlbumMoi);
        edit_tenAlbum = dialog.findViewById(R.id.edit_tenAlbum1);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        edit_tenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectAlbum();
            }
        });
        btn_SuaAlbumMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenAlbum.getText().toString().trim().equals("") || update_id_album != -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String album_name = edit_tenAlbum.getText().toString().trim();
                    String res = db.updateRecordAlbum(update_id_album, album_name);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAlbum();
                    getDataDetailAlbum();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn album cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleAlbumDeleteAlbum() {
        dialog.setContentView(R.layout.layout_dialog_delete_album);
        Button btn_XoaAlbum = dialog.findViewById(R.id.btn_XoaAlbum);
        edit_tenAlbum = dialog.findViewById(R.id.edit_tenAlbum2);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        edit_tenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectAlbum();
            }
        });
        btn_XoaAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenAlbum.getText().toString().trim().equals("") || delete_id_album == -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    int album_id = delete_id_album;
                    String res = db.deleteRecordAlbum(album_id);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAlbum();
                    getDataDetailAlbum();
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn album cần xóa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void handleAuthorAddAuthor() {
        dialog.setContentView(R.layout.layout_dialog_add_author);
        Button btn_themTacGia = dialog.findViewById(R.id.btn_ThemTacGia);
        edit_tenTacGia = dialog.findViewById(R.id.edit_tenTacGia);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);

        btn_themTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenTacGia.getText().toString().trim().equals("")) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String author_name = edit_tenTacGia.getText().toString().trim();
                    String res = db.addRecordAuthor(author_name);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAuthor();
                    listView = findViewById(R.id.ListView5);
                    customAdapterAuthor = new CustomAdapterAuthor(MainActivity.this, R.layout.my_list_author, list_author);
                    listView.setAdapter(customAdapterAuthor);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời nhập tên ca sĩ cần thêm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleAuthorUpdateAuthor() {
        dialog.setContentView(R.layout.layout_dialog_update_author);
        Button btn_suaTacGia = dialog.findViewById(R.id.btn_SuaTacGia);
        edit_tenTacGia = dialog.findViewById(R.id.edit_tenTacGia1);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);

        edit_tenTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectAuthor();
            }
        });

        btn_suaTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenTacGia.getText().toString().trim().equals("") || update_id_author != -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String author_name = edit_tenTacGia.getText().toString().trim();
                    String res = db.updateRecordAuthor(update_id_author, author_name);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAuthor();
                    listView = findViewById(R.id.ListView5);
                    customAdapterAuthor = new CustomAdapterAuthor(MainActivity.this, R.layout.my_list_author, list_author);
                    listView.setAdapter(customAdapterAuthor);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời nhập tên tác giả cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleAuthorDeleteAuthor() {
        dialog.setContentView(R.layout.layout_dialog_delete_author);
        Button btn_xoaTacGia = dialog.findViewById(R.id.btn_XoaTacGia);
        edit_tenTacGia = dialog.findViewById(R.id.edit_tenTacGia2);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);

        edit_tenTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectAuthor();
            }
        });

        btn_xoaTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenTacGia.getText().toString().trim().equals("") || delete_id_author == -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String author_name = edit_tenTacGia.getText().toString().trim();
                    String res = db.deleteRecordAuthor(delete_id_author);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAuthor();
                    listView = findViewById(R.id.ListView5);
                    customAdapterAuthor = new CustomAdapterAuthor(MainActivity.this, R.layout.my_list_author, list_author);
                    listView.setAdapter(customAdapterAuthor);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời nhập tên tác giả cần xóa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleSingerUpdateSinger() {
        dialog.setContentView(R.layout.layout_dialog_update_singer);
        Button btn_SuaCaSi = dialog.findViewById(R.id.btn_SuaCaSi);
        edit_tenCaSi = dialog.findViewById(R.id.edit_tenSinger1);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        edit_tenCaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectSinger();
            }
        });
        btn_SuaCaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenCaSi.getText().toString().trim().equals("") || update_id_singer != -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String singer_name = edit_tenCaSi.getText().toString().trim();

                    String res = db.updateRecordSinger(update_id_singer, singer_name);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataSinger();
                    listView = findViewById(R.id.ListView4);
                    customAdapterSinger = new CustomAdapterSinger(MainActivity.this, R.layout.my_list_singer, list_singer);
                    listView.setAdapter(customAdapterSinger);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn ca sĩ cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleSingerDeleteSinger() {
        dialog.setContentView(R.layout.layout_dialog_delete_singer);
        Button btn_XoaCaSi = dialog.findViewById(R.id.btn_XoaCaSi);
        edit_tenCaSi = dialog.findViewById(R.id.edit_tenSinger2);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);
        edit_tenCaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectSinger();
            }
        });
        btn_XoaCaSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenCaSi.getText().toString().trim().equals("") || delete_id_singer == -1) {
                    DBHelper db = new DBHelper(MainActivity.this);

                    String res = db.deleteRecordSinger(delete_id_singer);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataSinger();
                    listView = findViewById(R.id.ListView4);
                    customAdapterSinger = new CustomAdapterSinger(MainActivity.this, R.layout.my_list_singer, list_singer);
                    listView.setAdapter(customAdapterSinger);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời chọn ca sĩ cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleSingerAddSinger() {
        dialog.setContentView(R.layout.layout_dialog_add_singer);
        Button btn_themSinger = dialog.findViewById(R.id.btn_ThemCaSi);
        edit_tenCaSi = dialog.findViewById(R.id.edit_tenCaSi);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);

        btn_themSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_tenCaSi.getText().toString().trim().equals("")) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    String singer_name = edit_tenCaSi.getText().toString().trim();
                    String res = db.addRecordSinger(singer_name);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataSinger();
                    listView = findViewById(R.id.ListView4);
                    customAdapterSinger = new CustomAdapterSinger(MainActivity.this, R.layout.my_list_singer, list_singer);
                    listView.setAdapter(customAdapterSinger);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời nhập tên ca sĩ cần thêm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void handleUserUpdateUser() {
        dialog.setContentView(R.layout.layout_dialog_update_user);
        Button btn_suaUser = dialog.findViewById(R.id.btn_SuaUser);
        edit_maAuth = dialog.findViewById(R.id.edit_maAuth);
        btn_Huy = dialog.findViewById(R.id.btn_Huy);

        edit_maAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSelectUser();
            }
        });

        btn_suaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_maAuth.getText().toString().trim().equals("") || update_id_user != -1) {
                    DBHelper db = new DBHelper(MainActivity.this);
                    int id_Auth = Integer.parseInt(edit_maAuth.getText().toString().trim());
                    String res = db.updateRecordUser(update_id_user, id_Auth);
                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                    //update list view
                    getDataAllUser();
                    listView = findViewById(R.id.ListView6);
                    customAdapterUser = new CustomAdapterUser(MainActivity.this, R.layout.my_list_user, list_all_user);
                    listView.setAdapter(customAdapterUser);
                    dialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Mời nhập mã quyền cần sửa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == 1 && resultcode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            String pathSong_sdcard = uri.getLastPathSegment().split(":")[1];
            String pathSong = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + pathSong_sdcard;
            String pathUri = "/" + uri.getPath().toString().split("/")[1] + "/" + uri.getPath().toString().split("/")[2] + "/";
            if (pathUri.trim().equals("/document/13FE-2804:Music/")) {
                edit_duongdanbaihat.setText(pathSong);
                Toast.makeText(this, "Chọn file thành công", Toast.LENGTH_SHORT).show();

            } else if (!pathUri.trim().equals("/document/13FE-2804:Music/")) {
                edit_duongdanbaihat.setText("");
                Toast.makeText(this, "Hãy lựa chọn file audio nằm trong mục sdcard", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (requestcode == 2 && resultcode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String pathImg_sdcard = uri.getLastPathSegment().split(":")[1];
            String pathImg = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + pathImg_sdcard;
            String pathUri = "/" + uri.getPath().toString().split("/")[1] + "/" + uri.getPath().toString().split("/")[2] + "/";
            if (pathUri.trim().equals("/document/1516-2C17:Pictures/")) {
                edit_anhbaihat.setText(pathImg);
                Toast.makeText(this, "Chọn file thành công", Toast.LENGTH_SHORT).show();

            } else if (!pathUri.trim().equals("/document/1516-2C17:Pictures/")) {
                edit_anhbaihat.setText("");
                Toast.makeText(this, "Hãy lựa chọn file image nằm trong mục sdcard", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (requestcode == 4) {
            if (resultcode == Activity.RESULT_OK) {
                int id_singer = data.getIntExtra("idSinger", -1);
                edit_macasi.setText(String.valueOf(id_singer));
                dialog.show();
            }
        } else if (requestcode == 5 && resultcode == Activity.RESULT_OK) {
            //Get intent data from FordActivity
            update_id_song = data.getIntExtra("idSong", -1);

            String song_name = data.getStringExtra("songName");
            String song_img = data.getStringExtra("songImg");
            String song_path = data.getStringExtra("songPath");
            String song_lyric = data.getStringExtra("lyricPath");
            int id_author = data.getIntExtra("idAuthor", -1);
            int id_singer = data.getIntExtra("idSinger", -1);

            edit_tenbaihat.setText(song_name);
            edit_tenbaihat.setSelection(song_name.trim().length());
            edit_anhbaihat.setText(song_img);
            edit_duongdanbaihat.setText(song_path);
            edit_duongdanloibaihat.setText(song_lyric);
            edit_macasi.setText(String.valueOf(id_singer));
            edit_matacgia.setText(String.valueOf(id_author));
            dialog.show();
        } else if (requestcode == 6 && resultcode == Activity.RESULT_OK) {
            int id_author = data.getIntExtra("idAuthor", -1);
            edit_matacgia.setText(String.valueOf(id_author));
            dialog.show();
        } else if (requestcode == 7 && resultcode == Activity.RESULT_OK) {
            update_id_album = data.getIntExtra("id", -1);
            delete_id_album = data.getIntExtra("id", -1);
            String album_name = data.getStringExtra("name");
            edit_tenAlbum.setText(album_name);
            edit_tenAlbum.setSelection(album_name.trim().length());
            dialog.show();
        } else if (requestcode == 8 && resultcode == Activity.RESULT_OK) {
            update_id_singer = data.getIntExtra("idSinger", -1);
            delete_id_singer = data.getIntExtra("idSinger", -1);
            String singer_name = data.getStringExtra("singerName");
            edit_tenCaSi.setText(singer_name);
            edit_tenCaSi.setSelection(singer_name.trim().length());
            dialog.show();
        } else if (requestcode == 9 && resultcode == Activity.RESULT_OK) {
            update_id_author = data.getIntExtra("idAuthor", -1);
            delete_id_author = data.getIntExtra("idAuthor", -1);
            String author_name = data.getStringExtra("authorName");
            edit_tenTacGia.setText(author_name);
            edit_tenTacGia.setSelection(author_name.trim().length());
            dialog.show();
        } else if (requestcode == 10 && resultcode == Activity.RESULT_OK) {
            update_id_user = data.getIntExtra("id", -1);
            int id_Auth = data.getIntExtra("idAuth", -1);
            edit_maAuth.setText(String.valueOf(id_Auth));
            edit_maAuth.setSelection(String.valueOf(id_Auth).length());
            dialog.show();
        } else if (requestcode == 11 && resultcode == Activity.RESULT_OK) {
            delete_id_song = data.getIntExtra("idSong", -1);
            int id_song = delete_id_song;
            edit_tenbaihat.setText(String.valueOf(id_song));
            dialog.show();
        }
    }

    public void OpenFileChooserSong() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        startActivityForResult(intent, requestCode_Song);
    }

    public void OpenFileChooserLyric() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            intent.setType("application/x-subrip");
        } else {
            intent.setType("application/octet-stream");
        }
        startActivityForResult(intent, requestCode_Lyric);
    }

    public void OpenFileChooserImg() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode_Image);
    }

    public void OpenSelectSong() {
        Intent intent = new Intent(MainActivity.this, FordActivity.class);
        startActivityForResult(intent, requestCode_selectSong);
    }

    public void OpenSelectDeleteSong() {
        Intent intent = new Intent(MainActivity.this, EightActivity.class);
        startActivityForResult(intent, requestCode_selectDelSong);
    }

    public void OpenSelectAlbum() {
        Intent intent = new Intent(MainActivity.this, SixActivity.class);
        startActivityForResult(intent, requestCode_Album);
    }

    public void OpenSelectSinger() {
        Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
        startActivityForResult(intent, requestCode_Singer_AddSinger);
    }

    public void OpenSelectAuthor() {
        Intent intent = new Intent(MainActivity.this, FiveActivity.class);
        startActivityForResult(intent, requestCode_Author_AddAuthor);
    }

    private void OpenSelectUser() {
        Intent intent = new Intent(MainActivity.this, SevenActivity.class);
        startActivityForResult(intent, requestCode_User);
    }

    public void getDataSong() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataSong();
        list_song.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu bài hát", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            Song song = new Song();
            song.setIdSong(res.getInt(0));
            song.setSongName(res.getString(1));
            song.setSongImg(res.getString(2));
            song.setSongPath(res.getString(3));
            song.setLyricPath(res.getString(4));
            song.setIdSinger(res.getInt(5));
            song.setIdAuthor(res.getInt(6));
            song.setCheckFavorite(res.getInt(7));
            list_song.add(song);
        }
    }

    public void getDataSinger() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataSinger();
        list_singer.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu ca sĩ", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            Singer singer = new Singer();
            singer.setIdSinger(res.getInt(0));
            singer.setSingerName(res.getString(1));
            list_singer.add(singer);
        }
    }

    public void getDataAuthor() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataAuthor();
        list_author.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu tác giả", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            Author author = new Author();
            author.setIdAuthor(res.getInt(0));
            author.setAuthorName(res.getString(1));
            list_author.add(author);
        }
    }

    public void getDataAllUser() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataAllUser();
        list_all_user.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu người dùng", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            User user = new User();
            user.setId(res.getInt(0));
            user.setUsername(res.getString(1));
            user.setPassword(res.getString(2));
            user.setId_auth(res.getInt(3));
            list_all_user.add(user);
        }
    }

    public void getDataUser() {
        if (list_user.size() == 0) {
            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            DBHelper db = new DBHelper(this);
            Cursor res = db.getDataUser(username, password);
            if (res.getCount() == 0) {
                Toast.makeText(this, "Không đọc được dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                return;
            }
            while (res.moveToNext()) {
                User user = new User();
                user.setId(res.getInt(0));
                user.setUsername(res.getString(1));
                user.setPassword(res.getString(2));
                user.setId_auth(res.getInt(3));
                list_user.add(user);
            }
        }

    }

    public void getDataAlbum() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataAlbum();
        list_album.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu album", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            Album album = new Album();
            album.setIdAlbum(res.getInt(0));
            album.setAlbumName(res.getString(1));
            list_album.add(album);
        }
    }

    public void getDataDetailAlbum() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataDetailAlbum();
        list_detail_album.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu album", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            DetailAlbum detailAlbum = new DetailAlbum();
            detailAlbum.setIdAlbum(res.getInt(0));
            detailAlbum.setIdSong(res.getInt(1));
            list_detail_album.add(detailAlbum);
        }
    }


    private Map<Album, List<DetailAlbum>> getDataAllAlbum() {
        Map<Album, List<DetailAlbum>> listMap = new HashMap<>();
        for (int i = 0; i < list_album.size(); i++) {
            listMap.put(list_album.get(i), list_detail_album);
        }

        return listMap;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.CardView) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("index_music", index_music);
            if (SecondActivity.mediaPlayer != null) {
                SecondActivity.mediaPlayer.stop();
                SecondActivity.mediaPlayer.release();
            }
            startActivity(intent);
        } else if (v.getId() == R.id.imageButton9) {

        } else if (v.getId() == R.id.imageButton10) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_home) {
            if (currentFragment != FRAGMENT_HOME) {
                showViewSong();
                replaceFragment(new HomeFragment());
                currentFragment = FRAGMENT_HOME;
                navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                //quay về page1 nhưng phải set lại menu
                showAppBarMusicMenu();
                toolbar.setTitle("Danh sách bài hát");
            }
        } else if (id == R.id.nav_author) {
            if (currentFragment != FRAGMENT_AUTHOR) {
                hintViewSong();
                hintViewAlbum();
                replaceFragment(new AuthorFragment());
                showAppBarAuthorMenu();
                currentFragment = FRAGMENT_AUTHOR;
                toolbar.setTitle("Danh sách tác giả");
            }
        } else if (id == R.id.nav_singer) {
            if (currentFragment != FRAGMENT_SINGER) {
                hintViewSong();
                hintViewAlbum();
                replaceFragment(new SingerFragment());
                showAppBarSingerMenu();
                currentFragment = FRAGMENT_SINGER;
                toolbar.setTitle("Danh sách ca sĩ");
            }
        } else if (id == R.id.nav_account) {
            if (currentFragment != FRAGMENT_USER) {
                hintViewSong();
                hintViewAlbum();
                replaceFragment(new UserFragment());
                showAppBarUserMenu();
                currentFragment = FRAGMENT_USER;
                toolbar.setTitle("Danh sách người dùng");
            }

        } else if (id == R.id.nav_my_profile) {

        } else if (id == R.id.nav_change_password) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = getLayoutInflater();
            View viewChangePassword = inflater.inflate(R.layout.layout_dialog_update_password, null);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(viewChangePassword)
                    .setPositiveButton(R.string.dialog_signIn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText edit_passwordHT = viewChangePassword.findViewById(R.id.edit_passwordHT);
                            EditText edit_passwordMoi = viewChangePassword.findViewById(R.id.edit_passwordMoi);
                            EditText edit_passwordMoiValid = viewChangePassword.findViewById(R.id.edit_passwordMoiValid);
                            String passwordHT = edit_passwordHT.getText().toString().trim();
                            String passwordMoi = edit_passwordMoi.getText().toString().trim();
                            String passwordMoiValid = edit_passwordMoiValid.getText().toString().trim();
                            if(passwordHT.equals(list_user.get(0).getPassword().trim())) {
                                if(passwordMoi.equals(passwordMoiValid)) {
                                    DBHelper db = new DBHelper(MainActivity.this);
                                    String res = db.updateRecordUserPassword(list_user.get(0).getId(),passwordMoi);
                                    Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Mật khẩu hiện tại nhập sai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        } else if (id == R.id.nav_logout) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

            builder.setPositiveButton(R.string.dialog_agree, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    private void hintViewSong() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        lv.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
    }

    private void hintViewAlbum() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        if (album_view != null)
            album_view.setVisibility(View.INVISIBLE);
    }

    private void showViewSong() {
        bottomNavigationView.getMenu().findItem(R.id.page_1).setChecked(true);
        bottomNavigationView.setVisibility(View.VISIBLE);
        lv.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
    }
}