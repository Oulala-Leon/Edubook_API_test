package sirup.edubook_api_test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
    private JSONArray chaptersArray;
    private Context context;

    public ChaptersAdapter(Activity activity) {
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                chaptersArray = response;
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
        this.context = activity;
    }

    @Override
    public int getItemCount() {
        if (chaptersArray == null)
            return 0;
        else
            return chaptersArray.length();
    }

    private JSONObject getItem(int position) {
        if (chaptersArray == null) return null;
        else
            return chaptersArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override @NonNull
    public ChaptersAdapter.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Log.d(TAG, "in onCreateViewHolder");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View chaptersView = inflater.inflate(R.layout.chapterslist_row, parent, false);
        return new ViewHolder(chaptersView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder VH, int position) {

        final TextView text = VH.lessonTitle;
        final ImageView imageView = VH.lessonImage;
        Log.d(TAG, "in onBindViewHolder");

        JSONObject json_data = getItem(position);
        if (null != json_data) {
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String url = json_data.getString("url");
                Response.Listener<Bitmap> image = new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d(TAG, "response :" + response);
                        imageView.setImageBitmap(response);
                    }
                };
                myHttpRequest.queryImage(url, image);
            } catch (JSONException e) {
                Log.d("ChaptersAdapter: ", e.getMessage(), e);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle;
        ImageView lessonImage;

        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            lessonTitle = itemView.findViewById(R.id.chapter_title);
            lessonImage = itemView.findViewById(R.id.chapter_image);
        }
    }
}
