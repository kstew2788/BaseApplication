package com.triviumtechnology.baseapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.triviumtechnology.baseapplication.NetworkUtilities.Internet;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity{

    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
   public static TextView mSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = (EditText) findViewById(R.id.activity_main_edit_text);
        mUrlDisplayTextView = (TextView) findViewById(R.id.search_query_text);
        mSearchResults = (TextView) findViewById(R.id.main_activity_scroll_text_view);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.action_search){
           Context context = MainActivity.this;
           String message = "Search Clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            makeSearchQuery();
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeSearchQuery(){
        Internet internet = new Internet();
        String query = mSearchBoxEditText.getText().toString();
        URL url = internet.BuildURL(query);
        mUrlDisplayTextView.setText(url.toString());
        new Internet.HttpQueryTask().execute(url);

    }

}
