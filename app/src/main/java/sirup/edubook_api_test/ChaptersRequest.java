package sirup.edubook_api_test;

import android.app.Activity;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.squareup.picasso.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersRequest extends android.os.Handler {
    private RecyclerView recyclerView;
    //private final ChaptersAdapter chaptersAdapter;
    private JSONArray chaptersArray;
    private ArrayList<String> titlesArray;
    private ArrayList<ImageView> picturesArray;
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
                chaptersArray = response;
                Log.d("QueryChapters: ", "Array Filled");
                recyclerView = activity.findViewById(R.id.Chapters_List);
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(new ChaptersAdapter());
                recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
                recyclerView.setHasFixedSize(true);
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
    }

    public void onCompletion() {

    }
}
