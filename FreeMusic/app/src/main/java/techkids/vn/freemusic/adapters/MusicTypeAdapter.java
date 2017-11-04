package techkids.vn.freemusic.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.events.OnClickMusicTypeEvent;
import techkids.vn.freemusic.fragments.TopSongFragment;
import techkids.vn.freemusic.utils.Utils;

/**
 * Created by Admins on 10/15/2017.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    private static final String TAG = "MusicTypeAdapter";
    private List<MusicTypeModel> musicTypeModels;
    private Context context;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModels, Context context) {
        this.musicTypeModels = musicTypeModels;
        this.context = context;
    }

    // create item view if needed
    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_music_type, parent, false);

        return new MusicTypeViewHolder(itemView);
    }

    // load data
    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModels.get(position));
    }

    // 1
    @Override
    public int getItemCount() {
        return musicTypeModels.size();
    }

    class MusicTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMusicType;
        TextView tvMusicType;
        View view;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);

            ivMusicType = itemView.findViewById(R.id.iv_music_type);
            tvMusicType = itemView.findViewById(R.id.tv_music_type);
            view = itemView;
        }

        public void setData(final MusicTypeModel musicTypeModel) {
            Picasso.with(context).load(musicTypeModel.getImageID()).into(ivMusicType);
            tvMusicType.setText(musicTypeModel.getKey());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");

                    EventBus.getDefault().postSticky(new OnClickMusicTypeEvent(musicTypeModel));
                    Utils.openFragment(((FragmentActivity) context).getSupportFragmentManager(),
                            R.id.layout_container, new TopSongFragment());
                }
            });
        }
    }
}
