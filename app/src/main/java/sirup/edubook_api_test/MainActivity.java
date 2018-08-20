package sirup.edubook_api_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {
    private ChaptersFragment chaptersFragment;
    private LessonsFragment lessonsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHttpRequest.getInstance(this);

        FragmentManager fm = getFragmentManager();
        chaptersFragment = new ChaptersFragment();
        lessonsFragment = new LessonsFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
        fragmentTransaction.add(R.id.Chapters_Fragment, chaptersFragment);
        fragmentTransaction.setPrimaryNavigationFragment(chaptersFragment);
        fragmentTransaction.hide(lessonsFragment);
        //fragmentTransaction.show(chaptersFragment);
        fragmentTransaction.commit();

        new ChaptersRequest(this).queryChapters(this);
    }

    public void toLessonsFragment(String lessonsURL) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.hide(chaptersFragment);
        fragmentTransaction.show(lessonsFragment);
        fragmentTransaction.commit();
        new LessonsRequest(this).queryLessons(lessonsURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        Button button = findViewById(R.id.return_to_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.show(chaptersFragment);
                fragmentTransaction.detach(lessonsFragment);
                fragmentTransaction.commit();
            }
        });
        return true;
    }
}
