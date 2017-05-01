package com.motomecha.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class  MyService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent =new Intent(this,BasicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int color = 0xff123456;
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifica = new NotificationCompat.Builder(this);
        notifica.setContentTitle("MOTOMECHA");
        notifica.setContentText(remoteMessage.getNotification().getBody());
        notifica.setAutoCancel(true);
        notifica.setColor(color);
        notifica.setOnlyAlertOnce(true);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notifica.setSound(alarmSound);
        notifica.setSmallIcon(R.drawable.ic_stat_name);
        notifica.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifica.build());
    }
}
