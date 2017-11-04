package techkids.vn.freemusic.fragments;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.adapters.TopSongAdapter;
import techkids.vn.freemusic.databases.TopSongModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    private static final String TAG = "";
    @BindView(R.id.rv_song_download)
    RecyclerView rv_download;

    private List<TopSongModel> topSongModels;


    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        setupUI(view);


        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        List<TopSongModel> list = new ArrayList<>();

        File listMusicDownload = new File(Environment.getExternalStorageDirectory(), "Song Download");

        if (!listMusicDownload.exists()) {
            listMusicDownload.mkdirs();
        }


        for (File file : listMusicDownload.listFiles()) {
            String name = file.getName();
            int temp = name.indexOf("-");

            TopSongModel topSongModel = new TopSongModel(name.substring(0, temp),
                    name.substring(temp + 1, name.indexOf(".")), null);

            list.add(topSongModel);
//            Log.d(TAG, "setupUI: xxx" + topSongModel.getSong());

        }

        TopSongAdapter songDownloadAdapter = new TopSongAdapter(getContext(), list);

        rv_download.setAdapter(songDownloadAdapter);
        rv_download.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
