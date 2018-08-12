package sirup.edubook_api_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class myHttpRequest extends Volley{
    private static myHttpRequest _instance = null;
    private static RequestQueue _queue;
    private static Context _context;
    private myHttpRequest (Context context)
    {
        _context = context.getApplicationContext();
        _queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized myHttpRequest getInstance(Context context)
    {
        if (_instance == null)
            _instance = new myHttpRequest(context);
        return _instance;
    }

    public static synchronized myHttpRequest getInstance()
    {
        if (_instance == null)
        {
            throw new IllegalStateException(myHttpRequest.class.getSimpleName() +
            " is not initialised, call getInstance(context) first");
        }
        return _instance;
    }

    public static synchronized void setJSONArray(final String url, final ListView view) {

        JsonArrayRequest JSONArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "response :" + response);
                        final JSONAdapter adapter = new JSONAdapter(response, _context);
                        view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        _queue.add(JSONArrayRequest);
    }

/*    public Bitmap getImage(String url) {

        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                            image = response;
                    }
                }, 0,0, null,null);

// Add the request to the RequestQueue.
        queue.add(imageRequest);
        return image;
    }*/
}
