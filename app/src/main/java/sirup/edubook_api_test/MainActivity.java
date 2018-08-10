package sirup.edubook_api_test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get SVT book
        String bookUrl = "https://api.lelivrescolaire.fr/public/books/1339497/chapters";
        myHttpRequest request = new myHttpRequest();
        JSONArray SVT_Book = request.getHttpResponse(this, bookUrl);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        ChaptersFragment cf = (ChaptersFragment) new ChaptersFragment();
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
