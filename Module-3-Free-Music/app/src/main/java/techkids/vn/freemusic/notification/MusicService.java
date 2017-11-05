package techkids.vn.freemusic.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import techkids.vn.freemusic.utils.MusicHandle;

/**
 * Created by vuanhlevis on 05/11/2017.
 */

public class MusicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MusicHandle.playPause();

        return super.onStartCommand(intent, flags, startId);
    }
}
