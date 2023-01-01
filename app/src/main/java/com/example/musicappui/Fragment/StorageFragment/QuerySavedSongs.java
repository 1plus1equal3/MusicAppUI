package com.example.musicappui.Fragment.StorageFragment;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import java.util.ArrayList;

public class QuerySavedSongs extends Thread{


    private final ArrayList<SavedSong> songs = new ArrayList<>();
    private final Context context;

    public QuerySavedSongs(Context context) {
        this.context = context;
    }

    public ArrayList<SavedSong> getSongs() {
        return songs;
    }

    @Override
    public void run() {
        getSongFromExternalStorage(context);
        super.run();
    }

    public void getSongFromExternalStorage(Context context) {
        Log.e("@", "Running");
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        //Column indexes
        int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
        int albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
        int titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
        int artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
        int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        Log.e("@1", ""  + cursor.getCount());
        if(cursor.moveToFirst())
            while (cursor.moveToNext()) {
                Log.e("@1", "Running");
                songs.add(new SavedSong(
                        cursor.getString(idColumn),
                        cursor.getString(albumColumn),
                        cursor.getString(artistColumn),
                        cursor.getString(titleColumn),
                        cursor.getString(pathColumn)
                ));
            }
        Log.e("@@", "" + songs.size());
        cursor.close();
    }

}
