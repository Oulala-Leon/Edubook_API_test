package sirup.edubook_api_test;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class LessonsRequest {
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private JSONArray lessonsArray;

    LessonsRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void queryLessons(String chapterID) {
        String Url = "https://api.lelivrescolaire.fr/public/chapters/";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                if (response.length() > 0) {
                    recyclerView = mainActivity.findViewById(R.id.Lesson_List);
                    LinearLayoutManager manager = new LinearLayoutManager(mainActivity);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new LessonsAdapter(response));
                    recyclerView.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(mainActivity, "No lessons could be found for this chapter.", Toast.LENGTH_LONG).show();
                }
            }
        };
        myHttpRequest.queryJSONArray(Url + chapterID + "/lessons", response);
    }
}
