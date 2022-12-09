package com.example.musicappui.CallbackInterface;

import com.example.musicappui.Fragment.StorageFragment.SavedSong;

import java.util.ArrayList;

public interface StorageSongsFragCallback {

    void teas(int num);

    void onAdapterSetUp(ArrayList<SavedSong> items);
}
