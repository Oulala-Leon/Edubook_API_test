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
import com.squareup.picasso.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private JSONArray lessonsArray;
    private MainActivity mainActivity;

    public LessonsAdapter(JSONArray lessons, MainActivity mainActivity) {
        lessonsArray = lessons;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
            return lessonsArray.length();
    }

    private JSONObject getItem(int position) {
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

        JSONObject jsonData = getItem(position);

        if (null != jsonData) {
            try {
                VH.ID = "" + jsonData.getInt("id");
                String title = jsonData.getString("title");
                VH.lessonTitle.setText(title);
                String type = jsonData.getString("type");
                type = type.replaceAll("\"", "");
                VH.lessonType.setText(type);
                String page =  "Page " + jsonData.getInt("page");
                VH.lessonPage.setText(page);
                boolean valid = jsonData.getBoolean("valid");
                if (!valid) {
                    VH.itemView.setBackgroundColor(0xFF555555);
                } else {
                    VH.itemView.setBackgroundColor(0xFFFFFFFF);
                }
            } catch (JSONException e) {
                Log.d("LessonsAdapter: ", e.getMessage(), e);
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView lessonTitle;
        TextView lessonType;
        TextView lessonPage;
        String ID;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            lessonPage = itemView.findViewById(R.id.lesson_page);
            lessonType = itemView.findViewById(R.id.lesson_type);
        }

        @Override
        public void onClick(final View itemView) {
            Log.d("clickety", "click");
            mainActivity.toTemplatesViewPager(ID, lessonTitle.getText().toString());
        }
    }
}
