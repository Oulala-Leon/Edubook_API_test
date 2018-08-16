package sirup.edubook_api_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity activity = this;
        myHttpRequest.getInstance(activity);

        //set Chapters
        RecyclerView recyclerView = findViewById(R.id.Chapters_List);
        recyclerView.setAdapter(new ChaptersAdapter());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //set Lessons
        /*FragmentManager fm = getFragmentManager();
        ChaptersFragment cf = (ChaptersFragment) fm.findFragmentById(R.id.reading_fragment);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.reading_fragment, cf);
        //fragmentTransaction.commit();

        //set Templates
        TemplatesFragment tf = (TemplatesFragment) fm.findFragmentById(R.id.templates);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.templates, tf);
        //fragmentTransaction.commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
