package sirup.edubook_api_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ViewPagerAdapter extends PagerAdapter {
    private JSONObject template;
    private String attempt;
    private Context context;

    ViewPagerAdapter(JSONObject template, Context context) {
        //this.template = arraySorter(template);
        this.context = context;
    }

    ViewPagerAdapter(String attempt, Context context) {
        //this.template = arraySorter(template);
        this.context = context;
        this.attempt = attempt;
    }


    public JSONArray arraySorter(JSONObject unsortedArray) {
        try {
            JSONArray jsonArr = new JSONArray(unsortedArray);
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<>();
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonValues.add(jsonArr.getJSONObject(i));
            }
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                private static final String KEY_NAME = "order";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA;
                    String valB;

                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                        return valA.compareTo(valB);
                    } catch (JSONException e) {
                        Log.d("can't think all too well: ", e.getMessage(), e);
                    }
                    return 0;
                }
            });
            for (int i = 0; i < jsonArr.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
            return sortedJsonArray;
        } catch (JSONException e) {
            Log.d("head burning: ", e.getMessage(), e);
        }
        return null;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        /*JsonParser parser = new JsonParser();

        JsonObject jsonObject = parser.parse(template.toString()).getAsJsonObject();
        Set<String> keys = jsonObject.keySet();*/
        LayoutInflater inflater = LayoutInflater.from(context);
        Gson gson = new Gson();
        dunnowhat lel = gson.fromJson(attempt, dunnowhat.class);
        Log.d("nope", lel.getEnunciated());
        if (lel.getEnunciated() != null) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.viewpager_webview, collection, false);
            collection.addView(layout);
            return layout;
        } else {
            Log.d("nope", "nope");
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.viewpager_webview, collection, false);
            collection.addView(layout);
            return layout;
        }
    }

    @Override
    public int getCount() {
        return 5; // not smart, but testable
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
