package techkids.vn.freemusic.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import techkids.vn.freemusic.fragments.DownloadFragment;
import techkids.vn.freemusic.fragments.FavouriteFragment;
import techkids.vn.freemusic.fragments.MusicFragment;

/**
 * Created by Admins on 10/15/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicFragment();
            case 1:
                return new FavouriteFragment();
            case 2:
                return new DownloadFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
