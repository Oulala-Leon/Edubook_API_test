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
import com.squareup.picasso.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
    private JSONArray chaptersArray;
    private final MainActivity mainActivity;

    public ChaptersAdapter(JSONArray chaptersArray, MainActivity mainActivity) {
        if (chaptersArray != null)
            this.chaptersArray = chaptersArray;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return chaptersArray.length();
    }

    private JSONObject getItem(int position) {
        return chaptersArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    @NonNull
    public ChaptersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View chaptersView = inflater.inflate(R.layout.chapterslist_row, parent, false);
        return new ViewHolder(chaptersView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder VH, int position) {
        final TextView text = VH.lessonTitle;
        final ImageView imageView = VH.lessonImage;

        JSONObject json_data = getItem(position);
        if (null != json_data) {
            try {
                VH.ID = json_data.getInt("id");
                String title = json_data.getString("title");
                text.setText(title);
                final String url = json_data.getString("url");
                myHttpRequest.queryImage(VH.itemView.getContext(), url, imageView, 1000, 1000, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "image " + url + " has been downloaded.");
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "image download had an error in Picasso");
                    }
                });
                boolean valid = json_data.getBoolean("valid");
                if (!valid) {
                    VH.itemView.setBackgroundColor(0xFF555555);
                } else {
                    VH.itemView.setBackgroundColor(0xFFFFFFFF);
                }
            } catch (JSONException e) {
                Log.d("ChaptersAdapter: ", e.getMessage(), e);
            }

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lessonTitle;
        ImageView lessonImage;
        int ID;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            lessonTitle = itemView.findViewById(R.id.chapter_title);
            lessonImage = itemView.findViewById(R.id.chapter_image);
        }

        @Override
        public void onClick(final View itemView) {
            Log.d("clickety", "click");
            mainActivity.toLessonsFragment("" + ID, lessonTitle.getText().toString());
        }
    }
}
