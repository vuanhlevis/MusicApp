package techkids.vn.freemusic.network;

import retrofit2.Call;
import retrofit2.http.GET;
import techkids.vn.freemusic.network.json_model.MainObjectJSON;

/**
 * Created by Admins on 10/12/2017.
 */

public interface GetMusicTypesService {
    @GET("api")
    Call<MainObjectJSON> getMusicTypes();
}
