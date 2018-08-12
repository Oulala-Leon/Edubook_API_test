package sirup.edubook_api_test;

import android.app.Activity;
import android.content.Context;
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

public class JSONAdapter extends BaseAdapter implements ListAdapter {

    private JSONArray _jsonArray;
    private Context _context;

    public JSONAdapter (JSONArray array) {
        _jsonArray = array;
        _context.getApplicationContext();
    }

    @Override public int getCount() {
        if(_jsonArray ==null)
            return 0;
        else
            return _jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(_jsonArray ==null) return null;
        else
            return _jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);
        return jsonObject.optLong("id");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.chapterslist_row, parent); //possibly second argument should be null
        }

        TextView text = convertView.findViewById(R.id.chapter_title);
        ImageView imageView =convertView.findViewById(R.id.chapter_image);

        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            try {
                String title = json_data.getString("title");
                text.setText(title);
                String url = json_data.getString("url");
                //Bitmap image = myHttpRequest.getImage(url);
                //imageView.setImageBitmap(image);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
