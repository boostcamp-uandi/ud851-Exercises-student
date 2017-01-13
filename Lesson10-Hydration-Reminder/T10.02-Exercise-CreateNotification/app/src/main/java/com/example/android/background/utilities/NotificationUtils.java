package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;

/**
 * Utility class for creating hydration notifications
 */
public class NotificationUtils {

    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;
    private static final int WATER_REMAINDER_NOTIFICATION_ID = 1138;

    public static void remindUserBecauseCharging(Context context){
        NotificationCompat.Builder ntfBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            ntfBuilder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager ntfMgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        ntfMgr.notify(WATER_REMAINDER_NOTIFICATION_ID, ntfBuilder.build());
    }


    private static PendingIntent contentIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
    // should return a PendingIntent. This method will create the pending intent which will trigger when
    // the notification is pressed. This pending intent should open up the MainActivity.
            // - Take the context passed in as a parameter
            // - Takes an unique integer ID for the pending intent (you can create a constant for
            //   this integer above
            // - Takes the intent to open the MainActivity you just created; this is what is triggered
            //   when the notification is triggered
            // - Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
            // intent but update the data


    // returns a Bitmap. This method is necessary to decode a bitmap needed for the notification.
        // resources object and R.drawable.ic_local_drink_black_24px


}
