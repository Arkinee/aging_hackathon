package com.elderlink.android.src.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.elderlink.android.R;
import com.elderlink.android.src.fcm.model.FcmView;
import com.elderlink.android.src.splash.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Locale;


public class FirebaseCloudMessagingService extends FirebaseMessagingService implements FcmView {

    private TextToSpeech tts;

    public FirebaseCloudMessagingService() {

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

//        HashMap<String, Object> params = new HashMap<>();
//        params.put("fcmToken", s);
//        final SplashService fcmService = new SplashService(this, this, params);
//        fcmService.tryPatchFcmToken();

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage);
            return;
        }

        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage);
        }
    }

    boolean isRunning() {
        boolean tf;
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        tf = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        return tf;
    }

    private void TextTTS(String message){
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        String text = "알림이 도착했습니다";
        String text2 = message;

        ttsGreater21(text);
    }

    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        // foreground
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        Intent notificationIntent;
        PendingIntent pendingIntent;


        Log.d("revely", "background");
        notificationIntent = new Intent(getApplicationContext(), SplashActivity.class);
        notificationIntent.putExtra("enterFromPush", true);


        pendingIntent = PendingIntent.getActivity(getApplicationContext(), (int) (System.currentTimeMillis() / 1000), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /**
         * 오레오 버전부터는 Notification Channel이 없으면 푸시가 생성되지 않는 현상이 있습니다.
         * **/

        String channel = "ELDER_LINK";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channel_nm = "event_notification";

            NotificationManager noticeChannel = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channelMessage.setDescription("ElderLink 앱 이벤트 푸시알림 채널");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
            noticeChannel.createNotificationChannel(channelMessage);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setChannelId(channel)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) (System.currentTimeMillis() / 1000), notificationBuilder.build());

        } else {
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channel)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) (System.currentTimeMillis() / 1000), notificationBuilder.build());

        }

//        Log.d("revely", "title: " + title + " message: " + message);
    }

    @Override
    public void postFcmToekenSuccess() {

    }

    @Override
    public void postFcmToekenSFailure(String message) {

    }
}

