package sirup.edubook_api_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;

import static com.android.volley.VolleyLog.TAG;


public class MainActivity extends AppCompatActivity {
    private LessonsFragment lessonsFragment;
    private FrameLayout frameLayout1;
    private FrameLayout frameLayout2;
    private TextView appTitle;
    private final MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHttpRequest.getInstance(this);
        frameLayout1 = findViewById(R.id.Frame_Layout_1);
        frameLayout2 = findViewById(R.id.Frame_Layout_2);
        appTitle = findViewById(R.id.App_Title);

        FragmentManager fm = getFragmentManager();
        ChaptersFragment chaptersFragment = new ChaptersFragment();
        lessonsFragment = new LessonsFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
        fragmentTransaction.add(R.id.Chapters_Fragment, chaptersFragment);
        fragmentTransaction.setPrimaryNavigationFragment(chaptersFragment);
        fragmentTransaction.commit();

        queryChapters();
    }

    public void queryChapters() {
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.d(TAG, "response :" + response);
                    JSONArray uprightResponse = new JSONArray();
                    try {
                        for (int i = response.length() - 1; i >= 0; i--) {
                            uprightResponse.put(response.get(i));
                        }
                    } catch (JSONException e) {
                        Log.d("ChaptersAdapter: ", e.getMessage(), e);
                    }
                    RecyclerView recyclerView = findViewById(R.id.Chapters_List);
                    LinearLayoutManager manager = new LinearLayoutManager(getParent());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new ChaptersAdapter(uprightResponse, mainActivity));
                    recyclerView.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(getParent(), "No chapters could be found.", Toast.LENGTH_LONG).show();
                }
            }
        };
        myHttpRequest.queryJSONArray(bookUrl, response);
    }

    public void toLessonsFragment(String lessonsURL, String chapterName) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        appTitle.setText(chapterName);
        frameLayout1.setVisibility(View.INVISIBLE);
        frameLayout2.setVisibility(View.VISIBLE);
        fragmentTransaction.show(lessonsFragment);
        fragmentTransaction.commit();
        queryLessons(lessonsURL);
    }

    public void queryLessons(String chapterID) {
        String Url = "https://api.lelivrescolaire.fr/public/chapters/";
        Response.Listener<JSONArray> response = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "response :" + response);
                JSONArray uprightResponse = new JSONArray();
                try {
                    for (int i = response.length() - 1; i >= 0; i--) {
                        uprightResponse.put(response.get(i));
                    }
                } catch (JSONException e) {
                    Log.d("ChaptersAdapter: ", e.getMessage(), e);
                }
                if (response.length() > 0) {
                    RecyclerView recyclerView;
                    recyclerView = findViewById(R.id.Lesson_List);
                    LinearLayoutManager manager = new LinearLayoutManager(mainActivity);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new LessonsAdapter(uprightResponse));
                    recyclerView.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(mainActivity, "No lessons could be found for this chapter.", Toast.LENGTH_LONG).show();
                }
            }
        };
        myHttpRequest.queryJSONArray(Url + chapterID + "/lessons", response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.return_to_start) {
            frameLayout1.setVisibility(View.VISIBLE);
            frameLayout2.setVisibility(View.INVISIBLE);
            appTitle.setText(R.string.book_title);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.detach(lessonsFragment);
            lessonsFragment = new LessonsFragment();
            fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
            fragmentTransaction.commit();
        }
        return true;
    }

}
