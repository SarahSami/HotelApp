package hotels.app.model;


import org.json.JSONObject;

/**
 * Created by Sarah on 2/15/18.
 */

public class Hotel {

    private String title;
    private String media;
    private String tags;

    public Hotel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void parseHotel(JSONObject object) {
        this.title = object.optString("title");
        this.tags = object.optString("tags");
        this.media = object.optJSONObject("media").optString("m");
    }
}

