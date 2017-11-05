package techkids.vn.freemusic.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.events.OnTopSongEvent;
import techkids.vn.freemusic.utils.Download;
import techkids.vn.freemusic.utils.MusicHandle;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {
    @BindView(R.id.iv_close)
    ImageView ivBack;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_singer_name)
    TextView tvSingerName;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_current_time_song)
    TextView tvCurrentTime;
    @BindView(R.id.tv_duration_time_song)
    TextView tvDurationTime;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;
    @BindView(R.id.sb_main)
    SeekBar sb;

    private TopSongModel topSongModel;

    public MainPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_player, container,
                false);

        setupUI(view);
        EventBus.getDefault().register(this);

        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandle.playPause();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;

                String fileName = topSongModel.getSong() + "-" + topSongModel.getArtist() + ".mp3";
//                Log.d(TAG, "onClick: xxxxxx" + fileName );

                File listMusicDownload = new File(Environment.getExternalStorageDirectory(),
                        "Song Download");

                if (!listMusicDownload.exists()) {
                    listMusicDownload.mkdirs();
                }

                for (File file : listMusicDownload.listFiles()) {
                    if (file.getName().equals(fileName)) {
                        check = true;
                    }
                }

                if (check) {
                    Toast.makeText(getContext(), "This song has been Downloaded", Toast.LENGTH_SHORT).show();
                } else {
                    final Uri destinationUri = Uri.parse(listMusicDownload + "/" + fileName);
                    Uri uriDownLoad = Uri.parse(topSongModel.getUrl());

                    DownloadRequest downloadRequest = new DownloadRequest(uriDownLoad)
                            .addCustomHeader("Vu-Co", "Holy shit")
                            .setRetryPolicy(new DefaultRetryPolicy())
                            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                            .setDownloadListener(new DownloadStatusListener() {
                                @Override
                                public void onDownloadComplete(int id) {
                                    Toast.makeText(getContext(), "Completed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {

                                }
                            });
//                            .setDownloadContext()

                    Download.getInstance().add(downloadRequest);
                }
            }
        });
    }

    @Subscribe(sticky = true)
    public void onReceiveTopSong(OnTopSongEvent onTopSongEvent) {
        topSongModel = onTopSongEvent.getTopSongModel();

        tvSingerName.setText(topSongModel.getArtist());
        tvSongName.setText(topSongModel.getSong());
        Picasso.with(getContext()).load(topSongModel.getLargeImage())
                .transform(new CropCircleTransformation())
                .into(ivSong);

        MusicHandle.updateRealtime(sb, fbPlay, ivSong, tvCurrentTime, tvDurationTime);
    }

}
