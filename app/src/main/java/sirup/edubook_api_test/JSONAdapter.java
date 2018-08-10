package sirup.edubook_api_test;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter implements ListAdapter {

    private final JSONArray jsonArray;
    public JSONAdapter (JSONArray jsonArray) {
        assert jsonArray != null;
        this.jsonArray = jsonArray;
    }

    @Override public int getCount() {
        if(jsonArray==null)
            return 0;
        else
            return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(null==jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.row, null);



        TextView text =(TextView)convertView.findViewById(R.id.txtAlertText);

        JSONObject json_data = getItem(position);
        if(null!=json_data ){
            String jj=json_data.getString("f_name");
            text.setText(jj);
        }
        return convertView;
    }
}
