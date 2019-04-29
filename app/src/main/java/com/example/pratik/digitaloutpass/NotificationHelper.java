package com.example.pratik.digitaloutpass;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationHelper {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String LEGACY_SERVER_KEY = "AIzaSyC5lOEAayO8WAlOeHrUyFIMqna5u_cJtZs";

    public static void dispNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, SplashScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, SplashScreen.CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(0, notificationBuilder.build());

    }

    public static void sendNotification(final String to, final String body, final String title) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json = new JSONObject();
                    JSONObject dataJson = new JSONObject();
                    dataJson.put("body", body);
                    dataJson.put("title", title);
                    json.put("notification", dataJson);
                    json.put("to", to);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization", "key=" + LEGACY_SERVER_KEY)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    Log.d("Notification", "Sent to "+to);
                    Log.d("Notification" , "notification sent");

                } catch (Exception e) {
                    //Log.d(TAG,e+"");
                }
                return null;
            }
        }.execute();

    }

}
