package com.wherego.delivery.driver.fcm;


import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
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
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wherego.delivery.driver.DriverMainActivity;
import com.wherego.delivery.driver.R;
import com.wherego.delivery.driver.activities.SplashScreen;
import com.wherego.delivery.driver.chat.UserChatActivity;
import com.wherego.delivery.driver.helpers.SharedHelper;
import com.wherego.delivery.driver.utills.Utilities;

import java.util.List;
import java.util.Map;


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
            String msg = "";
            Log.d(TAG, "From: " + remoteMessage.getData().get("body"));
            Log.d(TAG, "Message: " + remoteMessage.toString());
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            String message = remoteMessage.getData().get("body");
            if (!isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Utilities.printV(TAG, "foreground");
            } else {
                wakeUpScreen();
                Utilities.printV(TAG, "background");
                if (message.equalsIgnoreCase("New Incoming Ride")) {
                    KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("IN");
                    kl.disableKeyguard();
                    //Intent mainIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    Intent mainIntent = new Intent(this, SplashScreen.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(mainIntent);
                }
            }
            handleNotification(remoteMessage);
        } catch (Exception ne) {
            Log.v("Exceptionnotification", "Exceptionnotification");
            ne.printStackTrace();
           // sendNotification(remoteMessage.getData().get("message"));
        }


    }

    private void wakeUpScreen() {
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        Log.e("screen on......", "" + isScreenOn);
        if (!isScreenOn) {
            @SuppressLint("InvalidWakeLockTag")
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag")
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
            wl_cpu.acquire(10000);
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
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
        String message = remoteMessage.getData().get("body");

        if (!isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Utilities.printV(TAG, "foreground");
        } else {

            wakeUpScreen();
            Utilities.printV(TAG, "background");
            if (message.equalsIgnoreCase("New Incoming Ride")) {
                KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("IN");
                kl.disableKeyguard();

                Intent mainIntent = new Intent(this, SplashScreen.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
            }
        }

        sendNotification(getString(R.string.app_name), remoteMessage.getData().get("title"), message);
    }

    private void sendNotification(String notificationTitle, String notificationBody, String userName) {
        Intent intent = new Intent(this, SplashScreen.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.e(TAG, "Notification JSON "  + userName + notificationBody);
        try {
            String title = notificationTitle;
            intent.putExtra("userName", userName);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* TripRequest code */, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, chanelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(userName)
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


        long when = System.currentTimeMillis();  // notification time
        String CHANNEL_ID = "CHAT MESSAGE RIDE";    // The id of the channel.
        CharSequence name = "Chat Message Ride";// The user-visible name of the channel.
        int importance = 0;

        try {

            String message = notificationBody;
            intent.putExtra("message", message);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "PUSH");
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine(message);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* RequestList code */, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification;
            notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(getString(R.string.app_name))
                    .setWhen(when)
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentIntent(pendingIntent)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setStyle(inboxStyle)
                    .setWhen(when)
                    .setSmallIcon(getNotificationIcon(mBuilder))
                    .setContentText(message)
                    .setChannelId(CHANNEL_ID)
                    .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            // check for orio 8
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                // String channelId = context.getString(R.string.default_notification_channel_id);
                NotificationChannel notificationChannel = new NotificationChannel(chanelId, notificationBody, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
                notificationBuilder.setChannelId(chanelId);
            }*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                android.app.NotificationChannel mChannel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
                notificationManager.createNotificationChannel(mChannel);
            }
            assert notificationManager != null;
            notificationManager.notify(0, notification);
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



         /*  if (remoteMessage.getData().get("msg_type") != null) {
                msg_type = remoteMessage.getData().get("msg_type");
                msg = remoteMessage.getNotification().getBody();
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
            }
            else if (msg_type.contains("admin")) {
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
                handleNotification(remoteMessage);
               *//* Log.v("generalnotification", "generalnotification");
                sendNotification(remoteMessage.getData().get("message"));*//*
            }*/


}
