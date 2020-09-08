package com.wherego.driver.fcm;


import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wherego.driver.DriverMainActivity;
import com.wherego.driver.R;
import com.wherego.driver.chat.UserChatActivity;
import com.wherego.driver.helpers.SharedHelper;

import java.util.List;


public class FCMServices extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    final String chanelId = "AppNotification";
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        SharedHelper.putKey(getApplicationContext(), "device_token", "" + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            String msg_type = "";
            if (remoteMessage.getData().get("msg_type") != null) {
                msg_type = remoteMessage.getData().get("msg_type");
            }
            if (msg_type.contains("chat")) {

                if (getTopAppName().equals(UserChatActivity.class.getName())) {
                    Intent intent = new Intent();
                    intent.putExtra("message", remoteMessage.getNotification().getBody());
                    intent.setAction("com.my.app.onMessageReceived");
                    sendBroadcast(intent);

                } else {
                    handleNotification(remoteMessage);
                }
            } else if (msg_type.contains("admin")) {
                String title = remoteMessage.getNotification().getTitle();
                String message = remoteMessage.getNotification().getBody();
                String click_action = "com.quickridejadriver.providers.TARGETNOTIFICATION";
                Intent intent = new Intent(click_action);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this, chanelId);
                notifiBuilder.setContentTitle(title);
                notifiBuilder.setContentText(message);
                notifiBuilder.setSmallIcon(R.mipmap.ic_launcher);
                notifiBuilder.setAutoCancel(true);
                notifiBuilder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notifiBuilder.build());
            } else {
                Log.v("generalnotification", "generalnotification");
                sendNotification(remoteMessage.getData().get("message"));
            }
        } catch (Exception ne) {
            Log.v("Exceptionnotification", "Exceptionnotification");
            ne.printStackTrace();
            sendNotification(remoteMessage.getData().get("message"));
        }


    }

    public String getTopAppName() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        Log.i("topActivity", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getShortClassName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();
        return taskInfo.get(0).topActivity.getClassName();
    }

    private void handleNotification(RemoteMessage remoteMessage) {
        String requestId = remoteMessage.getData().get("request_id");
        sendNotification(getString(R.string.app_name), remoteMessage.getNotification().getBody(), requestId, remoteMessage.getData().get("user_name"));
    }

    private void sendNotification(String notificationTitle, String notificationBody, String requestId, String userName) {
        Intent intent = new Intent(this, UserChatActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.e(TAG, "Notification JSON " + requestId + userName + notificationBody);
        try {
            String title = notificationTitle;
            String message = notificationBody;
            intent.putExtra("message", message);
            intent.putExtra("request_id", requestId);
            intent.putExtra("userName", userName);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* TripRequest code */, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, chanelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // check for orio 8
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(chanelId, title, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationBuilder.setChannelId(chanelId);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void sendNotification(String notificationBody) {
        Intent intent = new Intent(this, DriverMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {

            String message = notificationBody;
            intent.putExtra("message", message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* RequestList code */, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new
                    NotificationCompat.Builder(this, chanelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // check for orio 8
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                // String channelId = context.getString(R.string.default_notification_channel_id);
                NotificationChannel notificationChannel = new NotificationChannel(chanelId, notificationBody, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationBuilder.setChannelId(chanelId);
            }
            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } catch (Exception e) {
            Log.v(TAG, "Exception: " + e.getMessage());
        }
    }


    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
            return R.mipmap.ic_launcher;
        } else {
            return R.mipmap.ic_launcher;
        }
    }
}
