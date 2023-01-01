package com.example.musicappui.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;

public class Receiver extends BroadcastReceiver {

    static ExoPlayer player;

    public static void getPlayer(ExoPlayer player) {
        Receiver.player = player;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ExampleService.PLAY_PAUSE:
                if (player.isPlaying()) {
                    player.setPlayWhenReady(false);
                    player.getPlaybackState();
                    Log.e("Player: ", "Pause");
                } else {
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Continue");
                }
                break;
            case ExampleService.SKIP_NEXT:
                if (player.hasNextMediaItem()) {
                    player.seekToNextMediaItem();
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Skip to next song successfully!");
                } else {
                    Log.e("Player: ", "0 next song!");
                }
                break;
            case ExampleService.SKIP_PREVIOUS:
                if (player.hasPreviousMediaItem()) {
                    player.seekToPreviousMediaItem();
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Skip to next song successfully!");
                } else {
                    Log.e("Player: ", "0 previous song!");
                }
                break;
            /*case ExampleService.DELETE_NOTIFICATION:
                Log.e("123", "123");
                IBinder binder = peekService(context, new Intent(context, ExampleService.class));
                if (binder == null)
                    return;
                ExampleService service = ((ExampleService.LocalBinder) binder).getService();
                service.stopForeground(true);
                service.stopSelf();
                break;*/
        }
    }
}
