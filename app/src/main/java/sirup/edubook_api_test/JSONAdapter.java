package sirup.edubook_api_test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter implements ListAdapter {

    private JSONArray jsonArray;
    private Context context;
    private Activity activity;
    private myHttpRequest httpRequest;

    public JSONAdapter (JSONArray array, Activity activity, myHttpRequest myhttprequest) {
        this.jsonArray = array;
        this.activity = activity;
        this.httpRequest = myhttprequest;
    }

    @Override public int getCount() {
        if(jsonArray==null)
            return 0;
        else
            return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(jsonArray==null) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.chapterslist_row, null);

        TextView text =convertView.findViewById(R.id.chapter_title);
        ImageView imageView =convertView.findViewById(R.id.chapter_image);

        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String url = json_data.getString("url");
                //Bitmap image = httpRequest.getImage(url);
                //imageView.setImageBitmap(image);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
