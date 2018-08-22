package sirup.edubook_api_test;

import android.view.View;

public class myRVListener implements View.OnClickListener {
    int id;
    String title;

    @Override
    public void onClick(View view) {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }
}
