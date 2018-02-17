package hotels.app.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import hotels.app.model.Hotel;
import hotels.app.util.Utils;

public class ApiService {


    public ApiService() {
    }


    public List<Hotel> getHotels() {
        List<Hotel> hotelList = new ArrayList<>();

        InputStream in = getInputStream(ApiURL.BASE_URL);
        try {
            JSONObject jsonObject = new JSONObject(Utils.convertStreamToString(in));
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                Hotel h = new Hotel();
                h.parseHotel(items.getJSONObject(i));
                hotelList.add(h);
            }

            return hotelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public InputStream getInputStream(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
            URLConnection connection;
            try {
                connection = url.openConnection();
                connection.setUseCaches(true);
                connection.connect();
                InputStream response = connection.getInputStream();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }

}