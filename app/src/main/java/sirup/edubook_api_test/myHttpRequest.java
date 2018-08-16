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

    public static synchronized void queryJSONArray(final String url, final Response.Listener<JSONArray> response) {

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error, YOU HAVE NO INTERNET FOOL! :" + error.getMessage());
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response, error);

        queue.add(jsonArrayRequest);
    }


    public static synchronized void queryImage(final String url, Response.
            Listener<Bitmap> response) {

        ImageRequest imageRequest = new ImageRequest(url,response,
                 100, 100, null, null);
        queue.add(imageRequest);
    }
}
