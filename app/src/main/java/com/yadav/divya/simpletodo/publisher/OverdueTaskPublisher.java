package com.yadav.divya.simpletodo.publisher;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.yadav.divya.simpletodo.R;
import com.yadav.divya.simpletodo.data.DbHelper;
import com.yadav.divya.simpletodo.ui.MainActivity;

/**
 * Created by dyadav1 on 2/6/2017.
 */

public class OverdueTaskPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Check for overdue tasks and only then send for notification
        DbHelper dbHelper = new DbHelper(context);
        String query = "SELECT * FROM TASK_LIST WHERE status='false' AND date < " + System.currentTimeMillis();
        Cursor c = dbHelper.getWritableDatabase().rawQuery(query, null);

        Log.d("TODO", String.valueOf(c.getCount()));
        if(c.getCount() > 0) {
            String content = context.getString(R.string.notification_str1) +
                    c.getCount() + context.getString(R.string.notification_str2);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(context, 0,
                    notificationIntent, 0);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(context.getString(R.string.overdue_tasks));
            builder.setContentText(content);
            builder.setSmallIcon(R.drawable.high_priority);
            builder.setAutoCancel(true);
            builder.setContentIntent(pintent);
            notificationManager.notify(1, builder.build());
        }
    }
}
