package sirup.edubook_api_test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;


////////////////////////Unused, this class will probably get destroyed soon//////////////////////////////////


public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.ViewHolder> {
    private JSONArray templatesArray;

    public TemplatesAdapter(JSONArray lessons) {
        templatesArray = lessons;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return templatesArray.length();
    }

    private JSONObject getItem(int position) {
        return templatesArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    @NonNull
    public TemplatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View lessonsView = inflater.inflate(R.layout.lessonslist_row, parent, false);
        return new TemplatesAdapter.ViewHolder(lessonsView);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplatesAdapter.ViewHolder VH, int position) {
        ImageView imageView = VH.imageView;
        WebView webView = VH.webView;
        JSONObject json_data = getItem(position);

        if (null != json_data) {
            try {
                if (json_data.getString("url") != null) {
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
                }
            } catch (JSONException e) {
                Log.d("LessonsAdapter: ", e.getMessage(), e);
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        WebView webView;

        public ViewHolder(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.template_html);
            imageView = itemView.findViewById(R.id.template_image);
        }
    }
}
