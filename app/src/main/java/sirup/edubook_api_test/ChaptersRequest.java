package sirup.edubook_api_test;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersRequest extends android.os.Handler {
    private RecyclerView recyclerView;
    private MainActivity mainActivity;

    ChaptersRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /*
        Requests the JSONArray for the chapters
        On Response, constructs the chapters RecyclerView
        Sets the Chapters Adapter
     */
    public void queryChapters(final Activity activity) {
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.d(TAG, "response :" + response);
                    JSONArray uprightResponse = new JSONArray();
                    try {
                        for (int i = response.length() - 1; i >= 0; i--) {
                            uprightResponse.put(response.get(i));
                        }
                    } catch (JSONException e) {
                        Log.d("ChaptersAdapter: ", e.getMessage(), e);
                    }
                    recyclerView = activity.findViewById(R.id.Chapters_List);
                    LinearLayoutManager manager = new LinearLayoutManager(activity);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new ChaptersAdapter(uprightResponse, mainActivity));
                    recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(mainActivity, "No chapters could be found.", Toast.LENGTH_LONG).show();
                }
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
    }
}
