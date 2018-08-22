package sirup.edubook_api_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class myHttpRequest extends Volley {
    private static myHttpRequest instance = null;
    private static RequestQueue queue;

    private myHttpRequest(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static synchronized myHttpRequest getInstance(Context context) {
        if (instance == null)
            instance = new myHttpRequest(context);
        return instance;
    }

    public static synchronized myHttpRequest getInstance() {
        if (instance == null) {
            throw new IllegalStateException(myHttpRequest.class.getSimpleName() +
                    " is not initialised, call getInstance(context) first");
        }
        return instance;
    }

    public static synchronized void queryJSONArray(final String url, final Response.Listener<JSONArray> response) {

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error, YOU HAVE NO INTERNET FOOL! :" + error.getMessage());
                Log.d("I AM THE ERROR MESSAGE", error.getMessage());
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response, error);
        queue.add(jsonArrayRequest);
    }

    public static synchronized void queryString(final String url, final Response.Listener<String> response) {

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("I AM THE ERROR MESSAGE IN THE STRINGRESPONSE:", error.getMessage());
            }
        };
        queue.add(new StringRequest(Request.Method.GET, url, response, error));
    }

    public static synchronized void queryJsonObject(final String url, final Response.Listener<JSONObject> response) {

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("I AM THE ERROR MESSAGE IN THE STRINGRESPONSE:", error.getMessage());
            }
        };
        queue.add(new JsonObjectRequest(url, new JSONObject(), response, error));
    }

    public static synchronized void queryImage(final Context activityContext, final String url, final ImageView imageView, final int width, final int height, final Callback callback) {
        Picasso.with(activityContext).load(url)
                .error(R.mipmap.ic_launcher)
                .resize(width, height)
                .placeholder(R.drawable.loadspinner)
                .into(imageView, callback);
    }

    public static synchronized RequestQueue getRequestQueue() {
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }
}
