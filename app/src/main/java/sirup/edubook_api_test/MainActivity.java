package sirup.edubook_api_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity activity = this;
        myHttpRequest.getInstance(activity);

        //set Chapters
        FragmentManager fm = getFragmentManager();
        ChaptersFragment cf = new ChaptersFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Chapters_Fragment, cf);
        fragmentTransaction.commit();

        new ChaptersRequest(activity);
        new LessonsRequest(activity, this);

        //set Templates
        /*TemplatesFragment tf = (TemplatesFragment) fm.findFragmentById(R.id.templates);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.templates, tf);
        //fragmentTransaction.commit();*/
    }

    public void toLessonsFragment() {
        FragmentManager fm = getFragmentManager();
        LessonsFragment lessonsFragment = new LessonsFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.Lessons_Fragment, lessonsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
