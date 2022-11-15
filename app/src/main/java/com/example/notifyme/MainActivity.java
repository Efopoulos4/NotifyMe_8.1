package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button button_notify;
    private Button button_update;
    private Button button_cancel;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder notifyBuilder;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_notify = findViewById(R.id.notify);
        button_update = findViewById(R.id.update);
        button_cancel = findViewById(R.id.cancel);
        createNotificationChannel();
    }


    public void sendNotification(View view) {
        notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void updateNotification(View view) {
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.ic_android2);
//        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!"));
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void cancelNotification(View view) {
        mNotifyManager.cancel(NOTIFICATION_ID);
    }

    public void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification for Mascot");
            mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                notificationIntent,
                PendingIntent.FLAG_MUTABLE);


        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You have been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notifyBuilder.setContentIntent(notificationPendingIntent).setAutoCancel(true);
        return notifyBuilder;
    }

}