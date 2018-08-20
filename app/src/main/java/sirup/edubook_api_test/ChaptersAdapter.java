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

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder>{
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
        if (null != json_data) {
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String url = json_data.getString("url");
                myHttpRequest.queryImage(VH.itemView.getContext(), url, imageView, 1100, 1100, new Callback() {
                    @Override
                    public void onSuccess() {

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
                    /*new onClickListener()
                    {
                        //set Lessons
                        LessonsFragment lessonsFragment = new LessonsFragment(String item_title);
                        //fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
                        //fragmentTransaction.commit();
                    }*/
                }
            } catch (JSONException e) {
                Log.d("ChaptersAdapter: ", e.getMessage(), e);
            }

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView lessonTitle;
        ImageView lessonImage;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            lessonTitle = itemView.findViewById(R.id.chapter_title);
            lessonImage = itemView.findViewById(R.id.chapter_image);
        }

        @Override
        public void onClick(View itemView) {
            Log.d("clickety","click");
            final ArrayList<JSONArray> jsonArrays = new ArrayList<>();
            try {
                final String[] lessons = getItem(getAdapterPosition()).get("lessons").toString().split(",");
                String api_root = "https://api.lelivrescolaire.fr/public/chapters/";
                for (int i = 0; i == lessons.length; i++) {
                    String str = api_root;
                    str = str.concat(lessons[i]);
                    str = str.concat("/lessons");
                    myHttpRequest.queryJSONArray(str, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            jsonArrays.add(response);
                            if (jsonArrays.size() == lessons.length) {
                                //lessonsFragment
                            }
                        }
                    });
                }
            } catch (JSONException e){
                Log.d("ChaptersAdapter: ", e.getMessage(), e);
            }
        }

    }
}
