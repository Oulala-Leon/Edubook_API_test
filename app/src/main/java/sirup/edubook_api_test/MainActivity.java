package sirup.edubook_api_test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import static com.android.volley.VolleyLog.TAG;


public class MainActivity extends AppCompatActivity {
    private ReadingFragment readingFragment;
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
        readingFragment = new ReadingFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Lessons_Fragment, readingFragment);
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
                    RecyclerView recyclerView = findViewById(R.id.Chapters_List);
                    LinearLayoutManager manager = new LinearLayoutManager(getParent());
                    recyclerView.setLayoutManager(manager);
                    /*recyclerView.setAdapter(new ChaptersAdapter(response, new myRVListener() {
                        @Override
                        public void onClick(View view) {
                            toLessonsFragment("" + id, title);
                        }
                    }));*/
                    recyclerView.setAdapter(new ChaptersAdapter(response, mainActivity));
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
        fragmentTransaction.show(readingFragment);
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
                    recyclerView.setAdapter(new LessonsAdapter(uprightResponse, mainActivity));
                    recyclerView.addItemDecoration(new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL));
                    recyclerView.setHasFixedSize(true);
                } else {
                    Toast.makeText(mainActivity, "No lessons could be found for this chapter.", Toast.LENGTH_LONG).show();
                }
            }
        };
        myHttpRequest.queryJSONArray(Url + chapterID + "/lessons", response);
    }

    public void toTemplatesViewPager(String lessonsURL, String lessonName) {
        appTitle.setText(lessonName);
        frameLayout1.setVisibility(View.INVISIBLE);
        frameLayout2.setVisibility(View.INVISIBLE);
        queryTemplates(lessonsURL);
    }

    public void queryTemplates(String lessonID) {
        String Url = "https://api.lelivrescolaire.fr/public/templates/";
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "response :" + response);
                if (response.length() > 0) {
                    ViewPager viewPager = findViewById(R.id.ViewPager);
                    viewPager.setAdapter(new ViewPagerAdapter(response, mainActivity));
                } else {
                    Toast.makeText(mainActivity, "You really can't learn anything.", Toast.LENGTH_LONG).show();
                }
            }
        };
        Log.d("here", Url + lessonID);

        myHttpRequest.queryString(Url + lessonID, response);
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
            fragmentTransaction.detach(readingFragment);
            readingFragment = new ReadingFragment();
            fragmentTransaction.add(R.id.Lessons_Fragment, readingFragment);
            fragmentTransaction.commit();
        }
        return true;
    }

}
