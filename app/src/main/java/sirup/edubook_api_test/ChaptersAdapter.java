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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
    private JSONArray chaptersArray;
    private RecyclerView recyclerView;
    private static int testpos = 0;

    public ChaptersAdapter() {
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                chaptersArray = response;
                Log.d("Adapter: ", "Array Filled");
                notifyDataSetChanged();
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
    }

    public ChaptersAdapter(JSONArray chaptersArray) {
        if (chaptersArray != null)
            this.chaptersArray = chaptersArray;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
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

    public ViewHolder getViewHolder(int position) {
        return (ViewHolder) (recyclerView.getChildViewHolder(recyclerView.getChildAt(position)));
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
        Log.d("onBindViewHolder: ", "At JSONDATA");
        if (null != json_data) {
            try {
                String title = json_data.getString("title");
                text.setText(title);
                Log.d("ViewHolder: ", "At TITLE");
                String url = json_data.getString("url");
                myHttpRequest.queryImage(VH.itemView.getContext(), url, imageView, 1100, 1100, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "image was properly downloaded");
                    }
                    @Override
                    public void onError() {
                        Log.d(TAG, "image download had an error in Picasso");
                    }
                });

                boolean valid = json_data.getBoolean("valid");
                if (!valid) {
                    VH.itemView.setBackgroundColor(0xFF555555);
                    //recyclerView.findViewHolderForAdapterPosition(position);
                }
                                /*
                else setOnClickListener()
                {
                    //set Lessons
                    LessonsFragment lessonsFragment = new LessonsFragment(String item_title);
                    //fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
                    //fragmentTransaction.commit();
                }
                 */
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
            Log.d("ViewHolder: ", "Being Constructed");
            context = itemView.getContext();
            lessonTitle = itemView.findViewById(R.id.chapter_title);
            lessonImage = itemView.findViewById(R.id.chapter_image);

            //JSONObject json_data = getItem(testpos++);
            Log.d("merdepos ", "" + (getAdapterPosition() + 1));

        }
    }
}
