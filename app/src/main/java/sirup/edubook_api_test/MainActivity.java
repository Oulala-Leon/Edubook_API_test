package sirup.edubook_api_test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;


public class MainActivity extends AppCompatActivity {
    String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Activity activity = this;

        //set Chapters
        RecyclerView recyclerView = findViewById(R.id.Chapters_List);
        myHttpRequest.getInstance(activity);
        myHttpRequest.queryJSONArray(bookUrl, recyclerView);

        FragmentManager fm = getFragmentManager();
        ChaptersFragment cf = (ChaptersFragment) fm.findFragmentById(R.id.reading_fragment);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.reading_fragment, cf);
        //fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
