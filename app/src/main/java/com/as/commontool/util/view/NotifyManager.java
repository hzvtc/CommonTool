package com.as.commontool.util.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.as.commontool.bean.NotifyMsg;

/**
 * Created by FJQ on 2017/2/5.
 */
public class NotifyManager {
    private static NotifyManager mInstance;
    private Context context;

    private NotifyManager(Context context) {
        this.context = context;
    }

    public static NotifyManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotifyManager(context.getApplicationContext());
        }
        return mInstance;
    }
    /**
     * 需要注意build()是在API
     // level16及之后增加的，API11可以使用getNotificatin()来替代
     * @param context
     * @param msg
     */
    public void notifyMsg(NotifyMsg msg){
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, msg.getTarget()), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentIntent(pendingIntent).
                setSmallIcon(msg.getTouchBarRes()).//设置状态栏的图标
                setLargeIcon(BitmapFactory.decodeResource(context.getResources(),msg.getNotifyRes())).
                setTicker(msg.getTickerText()).
                setContentTitle(msg.getContentTitle()).
                setContentText(msg.getContentText()).
                setAutoCancel(msg.isAutoCancel()).
                setWhen(System.currentTimeMillis()).
                build();
        notification.defaults=msg.getDefaults();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        notificationManager.notify(msg.getId(),notification);
    }

    public void cancel(Context context,int notifyId){
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }

    public void cancelAll(Context context){
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
