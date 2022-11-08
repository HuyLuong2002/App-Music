package com.example.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.app_music.MainActivity;
import com.example.app_music.R;
import com.example.model.Album;
import com.example.model.DetailAlbum;
import com.example.model.Song;

import java.util.List;
import java.util.Map;

public class CustomExpandableLVAdapter extends BaseExpandableListAdapter {

    private List<Album> list_album;
    private Map<Album, List<DetailAlbum>> list_detail_album;

    public CustomExpandableLVAdapter(List<Album> list_album, Map<Album, List<DetailAlbum>> list_detail_album) {
        this.list_album = list_album;
        this.list_detail_album = list_detail_album;
    }


    @Override
    public int getGroupCount() {
        if (list_album != null) {
            return list_album.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (list_album != null && list_detail_album != null) {
            return list_detail_album.get(list_album.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list_album.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_detail_album.get(list_album.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        Album album = list_album.get(groupPosition);
        return album.getIdAlbum();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        DetailAlbum detailAlbum = list_detail_album.get(list_album.get(groupPosition)).get(childPosition);
        return detailAlbum.getIdAlbum();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group_ford, parent, false);
        }
        TextView tvGroup = convertView.findViewById(R.id.textViewGroup);
        Album album = list_album.get(groupPosition);
        tvGroup.setText(album.getAlbumName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ford, parent, false);
        }
        TextView tvItem = convertView.findViewById(R.id.textViewItem);
        DetailAlbum detailAlbum = list_detail_album.get(list_album.get(groupPosition)).get(childPosition);
        String detail_album_song_name = null;

        Album album = list_album.get(groupPosition);
        if (detailAlbum.getIdAlbum() == album.getIdAlbum()) {
            for (Song song : MainActivity.list_song) {
                if (detailAlbum.getIdSong() == song.getIdSong()) {
                    detail_album_song_name = song.getSongName();
                }
            }
        }
        tvItem.setText(detail_album_song_name);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
