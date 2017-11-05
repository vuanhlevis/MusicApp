package techkids.vn.freemusic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import techkids.vn.freemusic.R;
import techkids.vn.freemusic.activities.MainActivity;
import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.utils.MusicHandle;

/**
 * Created by vuanhlevis on 05/11/2017.
 */

public class MusicNotification {
    public static final int NOTI_ID = 0;
    private static RemoteViews remoteViews;

    private static NotificationCompat.Builder builder;
    private static Context context;
    public static NotificationManager notificationManager;

    public static void setupNoti(Context context, TopSongModel topSongModel) {
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.layout_notification);
        MusicNotification.context = context;

        remoteViews.setTextViewText(R.id.tv_noti_song, topSongModel.getSong());
        remoteViews.setTextViewText(R.id.tv_noti_singer, topSongModel.getArtist());

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // de khi quay lai main la cai main cu k phai load lai

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(R.mipmap.ic_launcher)  // bat buoc phai co mac du minh set anh roi nen cai nay no k hien

                .setContent(remoteViews)
                .setOngoing(true)
                .setContentIntent(pendingIntent);

        notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTI_ID, builder.build());

        Picasso.with(context).load(topSongModel.getSmallImage())
                .into(remoteViews, R.id.iv_noti_song, NOTI_ID, builder.build());


        setOnclickPlayPause(context);
    }

    private static void setOnclickPlayPause(Context context) {

        Intent intent = new Intent(context, MusicService.class);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.iv_noti_play, pendingIntent);
    }

    public static void updateNotification() {

        if (MusicHandle.hybridMediaPlayer.isPlaying()) {
            remoteViews.setImageViewResource(R.id.iv_noti_play, R.drawable.exo_controls_pause);
        } else {
            remoteViews.setImageViewResource(R.id.iv_noti_play, R.drawable.exo_controls_play);
        }


        notificationManager.notify(0, builder.build());
    }
}
