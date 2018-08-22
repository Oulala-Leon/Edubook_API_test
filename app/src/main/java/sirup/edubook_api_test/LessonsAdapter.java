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

        final TextView text = VH.lessonTitle;
        final TextView typeView = VH.lessonType;
        final TextView pageView = VH.lessonPage;

        JSONObject json_data = getItem(position);

        if (null != json_data) {
            try {
                VH.ID = "" + json_data.getInt("ID");
                String title = json_data.getString("title");
                text.setText(title);
                String type = json_data.getString("type");
                type = type.replaceAll("\"", "");
                typeView.setText(type);
                String page =  "Page " + json_data.getInt("page");
                pageView.setText(page);
                boolean valid = json_data.getBoolean("valid");
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
