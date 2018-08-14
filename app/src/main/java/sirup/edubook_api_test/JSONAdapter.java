package sirup.edubook_api_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {

    private JSONArray jsonArray;
    private Context context;

    public JSONAdapter(JSONArray array, Context context) {
        jsonArray = array;
        this.context = context.getApplicationContext();
    }

    @Override
    public int getCount() {
        if (jsonArray == null)
            return 0;
        else
            return jsonArray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        if (jsonArray == null) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chapterslist_row, parent, false); //possibly second argument should be null
        }

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
                Log.d("JSONAdapter: ", e.getMessage(), e);
            }
        }
        return convertView;
    }
}
