package sirup.edubook_api_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import static com.android.volley.VolleyLog.TAG;

public class myHttpRequest extends Volley {
    private static myHttpRequest instance = null;
    private static RequestQueue queue;
    private static Context context;
    private myHttpRequest(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static synchronized myHttpRequest getInstance(Context context) {
        if (instance == null)
            instance = new myHttpRequest(context);
        myHttpRequest.context = context;
        return instance;
    }

    public static synchronized myHttpRequest getInstance() {
        if (instance == null) {
            throw new IllegalStateException(myHttpRequest.class.getSimpleName() +
                    " is not initialised, call getInstance(context) first");
        }
        return instance;
    }

    public static synchronized void queryJSONArray(final String url, final RecyclerView view) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "response :" + response);
                        final JSONAdapter adapter = new JSONAdapter(response, context);
                        view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    public static synchronized void queryImage(final String url, final ImageView imageView) {

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, null, null);

        queue.add(imageRequest);
    }
}
