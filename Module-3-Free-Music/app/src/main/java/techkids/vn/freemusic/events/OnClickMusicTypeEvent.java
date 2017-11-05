package techkids.vn.freemusic.events;

import techkids.vn.freemusic.databases.MusicTypeModel;

/**
 * Created by Admins on 10/17/2017.
 */

public class OnClickMusicTypeEvent {
    private MusicTypeModel musicTypeModel;

    public OnClickMusicTypeEvent(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }

    public MusicTypeModel getMusicTypeModel() {
        return musicTypeModel;
    }
}
