package techkids.vn.freemusic.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.adapters.MusicTypeAdapter;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.network.GetMusicTypesService;
import techkids.vn.freemusic.network.json_model.MainObjectJSON;
import techkids.vn.freemusic.network.RetrofitFactory;
import techkids.vn.freemusic.network.json_model.SubgenresJSON;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private RecyclerView rvMusicType;
    private List<MusicTypeModel> musicTypeModels = new ArrayList<>();
    private MusicTypeAdapter musicTypeAdapter;

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_music, container, false);

        setupUI(view);
        loadData();

        return view;
    }

    private void loadData() {
        GetMusicTypesService getMusicTypesService = RetrofitFactory.getInstance()
                .create(GetMusicTypesService.class);

        final Context context = getContext();
        getMusicTypesService.getMusicTypes().enqueue(new Callback<MainObjectJSON>() {
            @Override
            public void onResponse(Call<MainObjectJSON> call, Response<MainObjectJSON> response) {
                List<SubgenresJSON> subgenresJSONs = response.body().getSubgenres();
                for (int i = 0; i < subgenresJSONs.size(); i++) {
                    SubgenresJSON subgenresJSON = subgenresJSONs.get(i);

                    MusicTypeModel musicTypeModel = new MusicTypeModel();
                    musicTypeModel.setId(subgenresJSON.getId());
                    musicTypeModel.setKey(subgenresJSON.getTranslation_key());
                    musicTypeModel.setImageID(context.getResources()
                            .getIdentifier("genre_x2_" + subgenresJSON.getId(),
                                    "raw", context.getPackageName()));

                    musicTypeModels.add(musicTypeModel);
                    musicTypeAdapter.notifyItemChanged(i);
                }
//                musicTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MainObjectJSON> call, Throwable t) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI(View view) {
        rvMusicType = view.findViewById(R.id.rv_music);
        musicTypeAdapter = new MusicTypeAdapter(musicTypeModels, getContext());
        rvMusicType.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                getContext(),
                2,
                GridLayoutManager.VERTICAL,
                false);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });

        rvMusicType.setLayoutManager(gridLayoutManager);

        rvMusicType.setItemAnimator(new SlideInUpAnimator());
        rvMusicType.getItemAnimator().setAddDuration(300);
    }

}
