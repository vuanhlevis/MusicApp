package techkids.vn.freemusic.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import techkids.vn.freemusic.network.json_model.SearchSongJSON;

/**
 * Created by LapTop on 10/29/2017.
 */

public interface SearchSongService {
    @GET("http://103.1.209.134/services/api/audio")
    Call<SearchSongJSON> getSearchSong(@Query("search_terms") String search);
}
