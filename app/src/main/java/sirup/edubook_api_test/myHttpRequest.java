package sirup.edubook_api_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class myHttpRequest extends Volley{
    private static myHttpRequest instance = null;
    private Bitmap image;
    private static RequestQueue queue;
    private myHttpRequest (Context context)
    {
        queue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized myHttpRequest getInstance(Context context)
    {
        if (instance == null)
            instance = new myHttpRequest(context);
        return instance;
    }

    public static synchronized myHttpRequest getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException(myHttpRequest.class.getSimpleName() +
            " is not initialised, call getInstance(context) first");
        }
        return instance;
    }

    public static synchronized JSONArray getJSONArray(String url, final JSONArray array) {
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "response :" + response);
                            getReturnedArray(new JSONArray(response), array);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return array;
    }

    private static synchronized void getReturnedArray(JSONArray response, JSONArray array) {
        array = response;
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
