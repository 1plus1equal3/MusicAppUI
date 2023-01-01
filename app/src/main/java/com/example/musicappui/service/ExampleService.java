package com.example.musicappui.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicappui.MainActivity;
import com.example.musicappui.R;
import com.google.android.exoplayer2.ExoPlayer;


public class ExampleService extends Service {

    public static final String PLAY_PAUSE = "play_or_pause";
    public static final String SKIP_PREVIOUS = "skip_to previous";
    public static final String SKIP_NEXT = "skip_to next";
    public static final String DELETE_NOTIFICATION = "delete_notification";
    ExoPlayer player;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        player = MainActivity.getPlayer();

        //Start MainActivity on click of notification
        Intent ActivityIntent = new Intent(this, MainActivity.class);
        ActivityIntent.setAction(Intent.ACTION_MAIN);
        ActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //Play/Pause song from notification Action Button
        Intent PPB_intent = new Intent(this, Receiver.class);
        PPB_intent.putExtra("Play/Pause", PLAY_PAUSE);
        PPB_intent.setAction(PLAY_PAUSE);
        PendingIntent PPB_pendingIntent = PendingIntent.getBroadcast(this, 0, PPB_intent, PendingIntent.FLAG_IMMUTABLE);

        //Skip to previous song from notification Action Button
        Intent SPB_intent = new Intent(this, Receiver.class);
        SPB_intent.putExtra("SkipPrevious", SKIP_PREVIOUS);
        SPB_intent.setAction(SKIP_PREVIOUS);
        PendingIntent SPB_pendingIntent = PendingIntent.getBroadcast(this, 0, SPB_intent, PendingIntent.FLAG_IMMUTABLE);

        //Skip to next song from notification Action Button
        Intent SNB_intent = new Intent(this, Receiver.class);
        SNB_intent.putExtra("SkipNext", SKIP_NEXT);
        SNB_intent.setAction(SKIP_NEXT);
        PendingIntent SNB_pendingIntent = PendingIntent.getBroadcast(this, 0, SNB_intent, PendingIntent.FLAG_IMMUTABLE);

        //Skip to next song from notification Action Button
        Intent deleteIntent = new Intent(this, Receiver.class);
        SNB_intent.putExtra("DeleteNotification", DELETE_NOTIFICATION);
        SNB_intent.setAction(DELETE_NOTIFICATION);
        PendingIntent deleteIntent_pendingIntent = PendingIntent.getBroadcast(this, 0, deleteIntent, PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setContentTitle("Music Name")
                .setContentText("Music description")
                .setContentIntent(pendingIntent)
                /*.setDeleteIntent(deleteIntent_pendingIntent)*/
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Add an Action Button for Skip Previous
        builder.addAction(R.drawable.ic_baseline_skip_previous_24, "SkipPrevious", SPB_pendingIntent);

        //Add an Action Button for Play/Pause
        builder.addAction(R.drawable.ic_baseline_play_circle_24, "PlayorPause", PPB_pendingIntent);

        //Add an Action Button for Skip Next
        builder.addAction(R.drawable.ic_baseline_skip_next_24, "SkipNext", SNB_pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
        createNotificationChannel(managerCompat);

        startForeground(1, builder.build());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.release();
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        player.release();
        super.onTaskRemoved(rootIntent);
    }

    public void createNotificationChannel(NotificationManagerCompat managerCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            managerCompat.createNotificationChannel(channel);
        }
    }
}
