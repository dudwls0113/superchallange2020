package com.softsquared.android.superchallange2020.src;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.softsquared.android.superchallange2020.R;
import com.softsquared.android.superchallange2020.src.curation.CurationActivity;
import com.softsquared.android.superchallange2020.src.main.MainActivity;

import java.util.Random;

import static com.softsquared.android.superchallange2020.src.ApplicationClass.channel_id;

public class FireBaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Firebase", "FireBaseMessagingService : " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("Firebase", remoteMessage.getFrom());

        if (remoteMessage != null && remoteMessage.getData().size() > 0) {
            Log.d("message", "Message Notification Body: " + remoteMessage.getData().get("message"));
//추후에 대댓글 푸시용
//            if(Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get("commentNo")))>0){
//                sendNotificationForComment(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), Integer.parseInt(remoteMessage.getData().get("postNo"), Integer.parseInt(remoteMessage.getData().get("commentNo"))));
//            }
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), Integer.parseInt(remoteMessage.getData().get("seatNo")));
//            }
        }
    }

    private void sendNotification(String messageTitle, String messageBody, int seatNo) {
        Intent intent = new Intent(this, CurationActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CurationActivity.class);
        stackBuilder.addNextIntent(intent);
        intent.putExtra("seatNo", seatNo);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(channel_id, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = String.valueOf(new Random().nextInt(10000));

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setSound(defaultSoundUri)
                .setGroup("com.softsquared.android.superchallange2020")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "SUPER";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(new Random().nextInt(10000), notificationBuilder.build());
    }

}
