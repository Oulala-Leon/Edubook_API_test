package sirup.edubook_api_test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private LessonsFragment lessonsFragment;
    private FrameLayout frameLayout1;
    private FrameLayout frameLayout2;
    private TextView appTitle;

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

        new ChaptersRequest(this).queryChapters(this);
    }

    public void toLessonsFragment(String lessonsURL, String chapterName) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        appTitle.setText(chapterName);
        frameLayout1.setVisibility(View.INVISIBLE);
        frameLayout2.setVisibility(View.VISIBLE);
        fragmentTransaction.show(lessonsFragment);
        fragmentTransaction.commit();
        new LessonsRequest(this).queryLessons(lessonsURL);
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
