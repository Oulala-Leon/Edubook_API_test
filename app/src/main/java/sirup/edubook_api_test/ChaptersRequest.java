package sirup.edubook_api_test;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersRequest extends android.os.Handler {
    private RecyclerView recyclerView;
    private static int downloaded = 0;

    ChaptersRequest(Activity activity) {
        queryChapters(activity);
    }

    public void queryChapters(final Activity activity) {
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                recyclerView = activity.findViewById(R.id.Chapters_List);
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(new ChaptersAdapter(response));
                recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                recyclerView.setHasFixedSize(true);
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
    }
}
