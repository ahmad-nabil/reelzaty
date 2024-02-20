package com.ahmad.reelzaty;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.ahmad.reelzaty.CustomObjects.NotficationCustomObject;
import com.ahmad.reelzaty.informationUser.Database.RealtimeDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotficationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    if (message.getNotification()!=null){
        RealtimeDatabase realtimeDatabase=new RealtimeDatabase();
        NotficationCustomObject customObject=new NotficationCustomObject();
        customObject.setBody(message.getNotification().getBody());
        customObject.setTitle(message.getNotification().getTitle());
        realtimeDatabase.AddNotfications(customObject);
        ShowNotfication(customObject);
    }
    }

    private void ShowNotfication(NotficationCustomObject customObject) {
        Intent intent=new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notfi=new NotificationCompat.Builder(getApplicationContext(),"Channel_notfi").setSmallIcon(R.drawable.bellnotfi)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent).setContentTitle(customObject.getTitle())
                .setContentText(customObject.getBody());
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel=new NotificationChannel("Channel_notfi","APP",NotificationManager.IMPORTANCE_HIGH);
notificationManager.createNotificationChannel(notificationChannel);
notificationManager.notify(0,notfi.build());
    }
}
