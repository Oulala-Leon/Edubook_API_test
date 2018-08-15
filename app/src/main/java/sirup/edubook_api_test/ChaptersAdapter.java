package sirup.edubook_api_test;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

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

    public JSONObject getItem(int position) {
        if (chaptersArray == null) return null;
        else
            return chaptersArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    public void onCreateViewHolder(ViewGroup parent, int position) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chapterslist_row, parent, false);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder VH, int position) {

        TextView text = convertView.findViewById(R.id.chapter_title);
        ImageView imageView = convertView.findViewById(R.id.chapter_image);

        JSONObject json_data = getItem(position);
        if (null != json_data) {
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String url = json_data.getString("url");
                myHttpRequest.queryImage(url, imageView);
            } catch (JSONException e) {
                Log.d("ChaptersAdapter: ", e.getMessage(), e);
            }
        }
        return convertView;
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
