package biz.agbo.baccus.model;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paco on 14/12/14.
 */
public class Winery {
    private static Winery sInstance = null;
    private static final String winesUrl = "http://baccusapp.herokuapp.com/wines";

    private List<Wine> mWines = null;

    @TargetApi(11)
    public static Winery getInstance(){
        if (sInstance == null){
            try{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                sInstance = downloadWines();
            }catch (Exception ex){
                Log.e("Baccus", "Error downloading wines", ex);
                return null;
            }
        }
        return sInstance;
    }

    public static boolean isInstanceAvaible() {
        return sInstance != null;
    }

    private static Winery downloadWines() throws MalformedURLException, IOException, JSONException {
        Winery winery = new Winery();

        URLConnection conn = new URL(winesUrl).openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null){
            response.append(line);
        }

        reader.close();

        JSONArray wines = new JSONArray(response.toString());
        for (int wineIndex = 0; wineIndex < wines.length(); wineIndex++){
            String id = null;
            String name = null;
            String type = null;
            String company = null;
            String companyWeb = null;
            String notes = null;
            int rating = 0;
            String origin = null;
            String picture = null;
            List<String> grapes = new LinkedList<String>();

            JSONObject jSONWine = wines.getJSONObject(wineIndex);
            if (jSONWine.has("name")){
                id = jSONWine.getString("_id");
                name = jSONWine.getString("name");
                type = jSONWine.getString("type");
                company = jSONWine.getString("company");
                companyWeb = jSONWine.getString("company_web");
                notes = jSONWine.getString("notes");
                rating = jSONWine.getInt("rating");
                origin = jSONWine.getString("origin");
                picture = jSONWine.getString("picture");

                JSONArray jSONGrapes = jSONWine.getJSONArray("grapes");
                for (int grapeIndex = 0; grapeIndex < 1; grapeIndex++){
                    grapes.add(jSONGrapes.getJSONObject(grapeIndex).getString("grape"));
                }

                winery.mWines.add(new Wine(id, name, type, picture, companyWeb, notes, origin, rating, company, grapes));
            }
        }
        return winery;
    }

    public Winery(){
        mWines = new LinkedList<Wine>();
    }

    public Wine getWine(int index) {
        return mWines.get(index);
    }

    public int getWineCount() {
        return mWines.size();
    }

    public List<Wine> getWineList (){
        return mWines;
    }
}
