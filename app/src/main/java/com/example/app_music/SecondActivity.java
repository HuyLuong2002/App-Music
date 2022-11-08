package com.example.app_music;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.DAO.DBHelper;
import com.example.custom.CustomAdapterAlbum;
import com.example.model.Album;
import com.example.model.DetailAlbum;
import com.example.model.Song;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener, MediaPlayer.OnTimedTextListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    public static int flag_music = 0;
    private static final String TAG = "TimedTextTest";
    public boolean flag_play = true;
    private static boolean flag_random_music = false;
    private static boolean flag_repeat_music = false;
    public static MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView tv3_txt, tv2_txt, tv4_txt;
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private View lyrics_view;
    private Dialog dialogAlbum, dialogAddAlbum;
    private CustomAdapterAlbum customAdapterAlbum = null;
    private Button btn_Huy, btn_ThemAlbum;
    private ListView listView;
    private ImageButton imageButton, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8;
    private ImageView imageView;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //Lấy intent từ main activity
        Intent intent = getIntent();
        //Tạo đối tượng hanler và mediaplayer để chạy bài hát khi click vào đầu tiên
        handler = new Handler();
        mediaPlayer = new MediaPlayer();
        dialogAlbum = new Dialog(SecondActivity.this);
        dialogAddAlbum = new Dialog(SecondActivity.this);
        //get object
        tv2_txt = findViewById(R.id.textView2);
        tv3_txt = findViewById(R.id.textView3);
        seekBar = findViewById(R.id.seekBar);
        imageButton = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton7 = findViewById(R.id.imageButton7);
        imageButton8 = findViewById(R.id.imageButton8);
        imageView = findViewById(R.id.imageView);
        linearLayout = findViewById(R.id.linearLayout);
        tabLayout = findViewById(R.id.tabLayout);
        //Add gif image to imageView
        Glide.with(this).load(R.drawable.music_disc_player).into(new DrawableImageViewTarget(imageView));
        //set event cho tabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showTabPlaying();
                        hintTabLyrics();
                        break;
                    case 1:
                        showTabLyrics();
                        hintTabPlaying();
                        try {
                            floatLyrics();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //get value from intent 1
        flag_music = intent.getIntExtra("index_music", 0);
        //solve
        tv2_txt.setText(MainActivity.list_song.get(flag_music).getSongName());
        imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
        flag_play = false;
        try {
            playSong();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //Set event click
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    updateTextProgressSeekBar();
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void clearMediaPlayer(MediaPlayer mp) {
        if (mp != null) {
            mp.release();// this will clear memory
        }
    }


    public void playSong() throws IOException {
        clearMediaPlayer(mediaPlayer);
        visibleFavorite();
        imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(MainActivity.list_song.get(flag_music).getSongPath()));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
    }

    public void pauseMusic() {
        imageButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        mediaPlayer.pause();
    }

    public void playMusic() {
        visibleFavorite();
        imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer.start();
    }

    public void nextMusic() {
        mediaPlayer.release();
        if (flag_music < MainActivity.list_song.size() - 1) {
            flag_music = flag_music + 1;
        } else {
            flag_music = 0;
        }
        visibleFavorite();
        imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(MainActivity.list_song.get(flag_music).getSongPath()));
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.start();
    }

    public void previousMusic() {
        mediaPlayer.release();
        if (flag_music > 0 && flag_music <= MainActivity.list_song.size() - 1) {
            flag_music = flag_music - 1;
        } else {
            flag_music = MainActivity.list_song.size() - 1;
        }
        visibleFavorite();
        imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(MainActivity.list_song.get(flag_music).getSongPath()));
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.start();

    }

    private void updateSeekbar() {
        int currPos = mediaPlayer.getCurrentPosition();
        updateTextProgressSeekBar();
        tv3_txt.setVisibility(View.VISIBLE);
        seekBar.setProgress(currPos);
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));
        return buf.toString();
    }

    public void updateTextProgressSeekBar() {
        int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        tv3_txt.setText(getTimeString(mediaPlayer.getCurrentPosition()));
        tv3_txt.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        clearMediaPlayer(mp);
        try {
            if (flag_random_music == true) {
                double random_double = Math.random() * (MainActivity.list_song.size() - 1 + 1) + 1;
                flag_music = (int) random_double;
            }
            if (flag_repeat_music == true) {
                playSong();
            } else {
                nextMusic();
                tv2_txt.setText(MainActivity.list_song.get(flag_music).getSongName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        seekBar.setMax(mp.getDuration());
        mp.start();
        updateSeekbar();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        double ratio = percent / 100.0;
        int bufferingLevel = (int) (mp.getDuration() * ratio);
        seekBar.setSecondaryProgress(bufferingLevel);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageButton) {
            if (flag_play == false) {
                flag_play = true;
                pauseMusic();
            } else {
                flag_play = false;
                playMusic();
            }
        } else if (v.getId() == R.id.imageButton2) {
            if (flag_random_music == true) {
                double random_double = Math.random() * (MainActivity.list_song.size() - 1 + 1) + 1;
                flag_music = (int) random_double;
            }
            if (flag_repeat_music == true) {
                try {
                    playSong();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            nextMusic();
            tv2_txt.setText(MainActivity.list_song.get(flag_music).getSongName());
        } else if (v.getId() == R.id.imageButton3) {
            if (flag_random_music == true) {
                double random_double = Math.random() * (MainActivity.list_song.size() - 1 + 1) + 1;
                flag_music = (int) random_double;
            }
            if (flag_repeat_music == true) {
                try {
                    playSong();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            previousMusic();
            tv2_txt.setText(MainActivity.list_song.get(flag_music).getSongName());
        } else if (v.getId() == R.id.imageButton4) {
            if (flag_random_music == false) {
                flag_random_music = true;
                flag_repeat_music = false;
                imageButton4.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                imageButton5.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            } else {
                flag_random_music = false;
                imageButton4.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            }
        } else if (v.getId() == R.id.imageButton5) {
            if (flag_repeat_music == false) {
                flag_repeat_music = true;
                flag_random_music = false;
                imageButton5.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                imageButton4.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            } else {
                flag_repeat_music = false;
                imageButton5.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
            }
        } else if (v.getId() == R.id.imageButton6) {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageButton7) {
            handleFavorite();
        } else if (v.getId() == R.id.imageButton8) {
            dialogAlbum.setContentView(R.layout.layout_dialog_add_detail_album);
            listView = dialogAlbum.findViewById(R.id.ListView3);
            btn_Huy = dialogAlbum.findViewById(R.id.btn_Huy);
            btn_ThemAlbum = dialogAlbum.findViewById(R.id.btn_ThemAlbum);
            setDialogAlbumData();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DBHelper db = new DBHelper(SecondActivity.this);
                    Cursor res = db.getSaveDetailAlbum(MainActivity.list_song.get(flag_music).getIdSong());
                    ArrayList<DetailAlbum> list_save_detail_album = new ArrayList<>();
                    if (res.getCount() == 0) {
                        //Xử lý thêm vào detail album cho bài hát chưa có trong album nào
                        Album add_album = (Album) listView.getItemAtPosition(position);
                        Song curr_song = MainActivity.list_song.get(flag_music);
                        DBHelper add_db = new DBHelper(SecondActivity.this);
                        String add_res = add_db.addRecordDetailAlbum(add_album.getIdAlbum(), curr_song.getIdSong());
                        Toast.makeText(SecondActivity.this, add_res, Toast.LENGTH_SHORT).show();
                    }
                    while (res.moveToNext()) {
                        DetailAlbum detailAlbum = new DetailAlbum();
                        detailAlbum.setIdAlbum(res.getInt(0));
                        detailAlbum.setIdSong(res.getInt(1));
                        list_save_detail_album.add(detailAlbum);
                    }
                    Album album = (Album) listView.getItemAtPosition(position);
                    for (DetailAlbum detailAlbum : list_save_detail_album) {
                        if (album.getIdAlbum() == detailAlbum.getIdAlbum()) {
                            Toast.makeText(SecondActivity.this, "Thêm vào album thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            //Xử lý thêm vào detail album cho bài hát đã thêm vào album khác
                            Album add_album = (Album) listView.getItemAtPosition(position);
                            Song curr_song = MainActivity.list_song.get(flag_music);
                            DBHelper add_db = new DBHelper(SecondActivity.this);
                            String add_res = add_db.addRecordDetailAlbum(add_album.getIdAlbum(), curr_song.getIdSong());
                            Toast.makeText(SecondActivity.this, add_res, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            btn_Huy.setOnClickListener(this);
            btn_ThemAlbum.setOnClickListener(this);
            dialogAlbum.show();
        } else if (v.getId() == R.id.btn_Huy) {
            dialogAlbum.dismiss();
        } else if (v.getId() == R.id.btn_ThemAlbum) {
            dialogAlbum.dismiss();
            dialogAddAlbum.setContentView(R.layout.layout_dialog_add_album);
            Button btn_ThemAlbumMoi = dialogAddAlbum.findViewById(R.id.btn_ThemAlbumMoi);
            EditText edit_tenAlbum = dialogAddAlbum.findViewById(R.id.edit_tenAlbum);
            btn_Huy = dialogAddAlbum.findViewById(R.id.btn_Huy);
            btn_ThemAlbumMoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ten_album_moi = edit_tenAlbum.getText().toString().trim();
                    DBHelper db = new DBHelper(SecondActivity.this);
                    String res = db.addRecordAlbum(ten_album_moi);
                    Toast.makeText(SecondActivity.this, res, Toast.LENGTH_SHORT).show();
                    dialogAddAlbum.dismiss();
                    setDialogAlbumData();
                    dialogAlbum.show();
                }
            });
            btn_Huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogAddAlbum.dismiss();
                }
            });
            dialogAddAlbum.show();
        }
    }

    public void setDialogAlbumData() {
        getDataAlbum();
        //Tải dữ liệu lên custom adapter song
        customAdapterAlbum = new CustomAdapterAlbum(SecondActivity.this
                , R.layout.my_list_album, MainActivity.list_album);
        listView.setAdapter(customAdapterAlbum);
    }

    public void getDataAlbum() {
        DBHelper db = new DBHelper(this);
        Cursor res = db.getDataAlbum();
        MainActivity.list_album.clear();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Không đọc được dữ liệu album", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()) {
            Album album = new Album();
            album.setIdAlbum(res.getInt(0));
            album.setAlbumName(res.getString(1));
            MainActivity.list_album.add(album);
        }
    }

    public void hintTabPlaying() {
        imageView.setVisibility(View.INVISIBLE);
    }

    public void showTabPlaying() {
        imageView.setVisibility(View.VISIBLE);
    }

    public void showTabLyrics() {
        ConstraintLayout constraintLayout_second = findViewById(R.id.contraintLayout);
        lyrics_view = getLayoutInflater().inflate(R.layout.lyrics_view, null, true);
        lyrics_view.setX(220);
        lyrics_view.setY(450);
        constraintLayout_second.addView(lyrics_view);
    }

    public void hintTabLyrics() {
        lyrics_view.setVisibility(View.INVISIBLE);
    }

    public void floatLyrics() throws IOException {
        tv4_txt = lyrics_view.findViewById(R.id.textView4);
        if (!MainActivity.list_song.get(flag_music).getLyricPath().equals("unknown")) {
            mediaPlayer.addTimedTextSource(MainActivity.list_song.get(flag_music).getLyricPath(), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            int textTrackIndex = findTrackIndexFor(
                    MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
            if (textTrackIndex >= 0) {
                mediaPlayer.selectTrack(textTrackIndex);
            } else {
                Log.w(TAG, "Cannot find text track!");
            }
            mediaPlayer.setOnTimedTextListener(this);
        } else {
            tv4_txt.setText("No lyrics!!!");
        }


    }

    private int findTrackIndexFor(int mediaTrackType, MediaPlayer.TrackInfo[] trackInfo) {
        int index = -1;
        for (int i = 0; i < trackInfo.length; i++) {
            if (trackInfo[i].getTrackType() == mediaTrackType) {
                return i;
            }
        }
        return index;
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    // A handy method I use to close all the streams
    private void closeStreams(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable stream : closeables) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTimedText(final MediaPlayer mp, final TimedText text) {
        if (text != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv4_txt.setText(text.getText());
                }
            });
        }
    }

    public void visibleFavorite() {
        if (MainActivity.list_song.get(flag_music).getCheckFavorite() == 1) {
            imageButton7.setImageResource(R.drawable.ic_baseline_favorite_24);
            imageButton7.setColorFilter(getResources().getColor(R.color.favorite), PorterDuff.Mode.SRC_ATOP);
        } else {
            imageButton7.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            imageButton7.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void handleFavorite() {
        if (MainActivity.list_song.get(flag_music).getCheckFavorite() == 1) {
            MainActivity.list_song.get(flag_music).setCheckFavorite(0);
            updateRecordSongFavoriteCheck();
            imageButton7.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            imageButton7.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        } else {
            MainActivity.list_song.get(flag_music).setCheckFavorite(1);
            updateRecordSongFavoriteCheck();
            imageButton7.setImageResource(R.drawable.ic_baseline_favorite_24);
            imageButton7.setColorFilter(getResources().getColor(R.color.favorite), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void updateRecordSongFavoriteCheck() {
        DBHelper dbHelper = new DBHelper(this);
        int id = MainActivity.list_song.get(flag_music).getIdSong();
        Song song = MainActivity.list_song.get(flag_music);
        int res = dbHelper.updateRecordSongFavoriteCheck(id, song.getSongName(),
                song.getSongImg(), song.getSongPath(), song.getLyricPath(),
                song.getIdAuthor(), song.getIdSinger(), song.getCheckFavorite());
        if (res == 1) {
            Toast.makeText(this, "Lưu yêu thích thành công", Toast.LENGTH_SHORT).show();
        } else if (res == 0) {
            Toast.makeText(this, "Bỏ lưu yêu thích thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
