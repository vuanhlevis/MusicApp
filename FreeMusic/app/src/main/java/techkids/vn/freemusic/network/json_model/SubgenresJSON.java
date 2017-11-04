package techkids.vn.freemusic.network.json_model;

/**
 * Created by Admins on 10/12/2017.
 */

public class SubgenresJSON {
    private String id;
    private String translation_key;

    public SubgenresJSON(String id, String translation_key) {
        this.id = id;
        this.translation_key = translation_key;
    }

    public String getId() {
        return id;
    }

    public String getTranslation_key() {
        return translation_key;
    }
}
