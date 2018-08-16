package sirup.edubook_api_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private JSONArray lessonsArray;
    private RecyclerView recyclerView;

    public LessonsAdapter(String lesson) {
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                lessonsArray = response;
                notifyDataSetChanged();
            }
        };
        myHttpRequest.queryJSONArray(lesson, response);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        if (lessonsArray == null)
            return 0;
        else
            return lessonsArray.length();
    }

    private JSONObject getItem(int position) {
        if (lessonsArray == null) return null;
        else
            return lessonsArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    @NonNull
    public LessonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View lessonsView = inflater.inflate(R.layout.lessonslist_row, parent, false);
        return new LessonsAdapter.ViewHolder(lessonsView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapter.ViewHolder VH, int position) {

        final TextView text = VH.lessonTitle;
        final TextView type = VH.lessonType;
        final ImageView imageView = VH.lessonImage;

        JSONObject json_data = getItem(position);
        ImageLoader imageLoader = myHttpRequest.getInstance().getImageLoader();

        if (null != json_data) {
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String lessonType = json_data.getString("type");
                type.setText(lessonType);
                String url = json_data.getString("url");
                imageLoader.get(url, new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        Log.d(TAG, "response :" + response);
                        imageView.setImageBitmap(response.getBitmap());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError :" + error.toString());
                    }
                }, 100, 100);
                boolean valid = json_data.getBoolean("valid");
                if (!valid) {
                    VH.itemView.setBackgroundColor(0xFF555555);
                    //recyclerView.findViewHolderForAdapterPosition(position);
                }
                /*
                else setOnClickListener()
                {

                }
                 */
            } catch (JSONException e) {
                Log.d("LessonsAdapter: ", e.getMessage(), e);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle;
        TextView lessonType;
        ImageView lessonImage;

        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            lessonImage = itemView.findViewById(R.id.lesson_image);
            lessonType = itemView.findViewById(R.id.lesson_type);
        }
    }
}
