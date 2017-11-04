package techkids.vn.freemusic.utils;

import com.thin.downloadmanager.DownloadManager;
import com.thin.downloadmanager.ThinDownloadManager;

/**
 * Created by vuanhlevis on 04/11/2017.
 */

public class Download {
    private static ThinDownloadManager downLoadmanager;

    public static DownloadManager getInstance() {
        if (downLoadmanager == null) {

            downLoadmanager = new ThinDownloadManager();
        }
        return downLoadmanager;
    }
}
