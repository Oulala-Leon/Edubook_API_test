package sirup.edubook_api_test;

import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.android.volley.VolleyLog.TAG;

public class ChaptersHandler extends Handler {
    private final RecyclerView recyclerView;
    private final ChaptersAdapter chaptersAdapter;

    ChaptersHandler(Activity activity) {
        recyclerView = activity.findViewById(R.id.Chapters_List);
        chaptersAdapter = new ChaptersAdapter();
        recyclerView.setAdapter(chaptersAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void queryChapters() {
        for (int i = 0; i <= chaptersAdapter.getItemCount(); i++) {
            final ChaptersAdapter.ViewHolder VH = chaptersAdapter.getViewHolder(i);
            TextView text = VH.lessonTitle;
            ImageView imageView = VH.lessonImage;
            JSONObject json_data = chaptersAdapter.getItem(i);
            if (null != json_data) {
                try {
                    String title = json_data.getString("title");
                    text.setText(title);
                    String url = json_data.getString("url");
                    myHttpRequest.queryImage(VH.itemView.getContext(), url, imageView, 100, 100, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "image was properly downloaded");
                            chaptersAdapter.notifyItemChanged(VH.getAdapterPosition());
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
    }

    @Override
    public void publish(LogRecord record) {

    }

    @Override
    public void flush()
    {
        
    }

    @Override
    public void close()
    {

    }
}
