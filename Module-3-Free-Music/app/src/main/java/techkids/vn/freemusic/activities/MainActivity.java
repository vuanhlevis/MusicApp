package techkids.vn.freemusic.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import techkids.vn.freemusic.adapters.ViewPagerAdapter;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.events.OnClickMusicTypeEvent;
import techkids.vn.freemusic.events.OnTopSongEvent;
import techkids.vn.freemusic.fragments.MainPlayerFragment;
import techkids.vn.freemusic.fragments.TopSongFragment;
import techkids.vn.freemusic.notification.MusicNotification;
import techkids.vn.freemusic.utils.MusicHandle;
import techkids.vn.freemusic.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @BindView(R.id.layout_mini)
    RelativeLayout rlMini;
    @BindView(R.id.tv_top_song_name)
    TextView tvSong;
    @BindView(R.id.tv_top_song_artist)
    TextView tvArtist;
    @BindView(R.id.seekbar)
    SeekBar seekBar;
    @BindView(R.id.iv_top_song)
    ImageView ivSong;
    @BindView(R.id.fb_mini)
    FloatingActionButton floatingActionButton;

    private TopSongModel topSongModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        EventBus.getDefault().register(this);

        //TODO: fake
//        MusicTypeModel musicTypeModel = new MusicTypeModel();
//        musicTypeModel.setKey("All");
//        musicTypeModel.setImageID(R.raw.genre_x2_);
//        musicTypeModel.setId("");
//        EventBus.getDefault().postSticky(new OnClickMusicTypeEvent(musicTypeModel));
//        Utils.openFragment(getSupportFragmentManager(),
//                R.id.layout_container, new TopSongFragment());
    }

    @Subscribe(sticky = true)
    public void onReceivedTopSongEvent(OnTopSongEvent topSongEvent) {
        rlMini.setVisibility(View.VISIBLE);
        topSongModel = topSongEvent.getTopSongModel();

        tvSong.setText(topSongModel.getSong());
        tvArtist.setText(topSongModel.getArtist());
        Picasso.with(this).load(topSongModel.getSmallImage())
                .transform(new CropCircleTransformation()).into(ivSong);

        MusicHandle.searchSong(topSongModel, this);
        MusicHandle.updateRealtime(seekBar, floatingActionButton, ivSong,
                null, null);
    }

    private void setupUI() {
        ButterKnife.bind(this);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_file_download_black_24dp));

        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(100);
        tabLayout.getTabAt(2).getIcon().setAlpha(100);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(100);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //an 2 lan lien tuc vao tab
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener
                (new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        seekBar.setPadding(0, 0, 0, 0);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandle.playPause();
            }
        });

        rlMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openFragment(getSupportFragmentManager(),
                        R.id.ll_container,
                        new MainPlayerFragment());
            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        MusicNotification.notificationManager.cancel(MusicNotification.NOTI_ID);
//    }

    @Override
    public void onBackPressed() {
        // khong bi load lai nhac khi vao lai app luc dang chay

        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
//            Log.d(TAG, "onBackPressed: " +);
            super.onBackPressed();
        } else {
            moveTaskToBack(true);
        }


    }
}
