package sirup.edubook_api_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private JSONArray templatesArray;
    private Context context;

    ViewPagerAdapter(JSONArray templatesArray, Context context) {
        this.templatesArray = arraySorter(templatesArray);
        this.context = context;
    }

    public JSONArray arraySorter(JSONArray unsortedArray) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        try {
            JSONObject jsonObject = templatesArray.getJSONObject(position);
            if (jsonObject.getString("src") != null) {
                ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.viewpager_webview, collection, false);
                collection.addView(layout);
                return layout;
            }
        } catch (JSONException e) {
            Log.d("MainActivity queryTemplates: ", e.getMessage(), e);
        }
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.viewpager_webview, collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return templatesArray.length(); // stupid temp to fix
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
