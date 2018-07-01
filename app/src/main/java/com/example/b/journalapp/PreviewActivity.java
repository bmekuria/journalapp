package com.example.b.journalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.b.journalapp.Models.Notes;
import com.example.b.journalapp.Utilites.DatabaseHelper;

public class PreviewActivity extends AppCompatActivity
        implements RecyclerViewAdapter.ItemListener{

    private RecyclerView mNotesList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Notes[] mDataSet = new Notes[0];
    private Boolean mExit = false;
    private Boolean mData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mNotesList = findViewById(R.id.preview_notes);
        mNotesList.setHasFixedSize(true);

        new PreviewExpenses().execute();

        //TODO (1): Add note
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreviewActivity.this,EditorActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new PreviewExpenses().execute();
    }

    public void setData()
    {
        //Log.d("mData State: ", String.valueOf(mData));

            mNotesList.setAdapter(null);
            mLayoutManager = new LinearLayoutManager(this);
            mNotesList.setLayoutManager(mLayoutManager);
            mAdapter = new RecyclerViewAdapter(this, mDataSet, this);
            mNotesList.setAdapter(mAdapter);

    }

    /***********
     * TASK TO RETRIEVE ALL NOTES AND PLACE IN PREVIEW
     */

    public class PreviewExpenses extends AsyncTask<Void, Void, Notes[]> {
        @Override
        protected Notes[] doInBackground(Void... voids) {
            Notes[] allnotes = new Notes[0];

            try{
                DatabaseHelper db = new DatabaseHelper(PreviewActivity.this);
                allnotes = db.querynote(db);
                db.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return allnotes;
        }

        @Override
        protected void onPostExecute(Notes[] notes) {
            super.onPostExecute(notes);

            if(notes.length > 0) {
                //mData = true;

                mDataSet = new Notes[notes.length];
                for(int i = 0; i < notes.length; i++) {
                    mDataSet[i] = new Notes();
                    mDataSet[i].id   = notes[i].id;
                    mDataSet[i].title = notes[i].title;
                    mDataSet[i].text = notes[i].text;
                    mDataSet[i].timestamp = notes[i].timestamp;

                }
            }else {
                Toast.makeText(PreviewActivity.this,"List not updated",Toast.LENGTH_SHORT).show();
                //mData = false;
            }
            setData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item_about: //TODO Popup Dialog with information about the app
                Toast.makeText(this,"ABOUT",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_settings: //TODO: Options to change settings
                Toast.makeText(this,"SETTINGS",Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_bar_search: //TODO: Fix search functionality and shortlist messages accordingly
                Toast.makeText(this,"SEARCH",Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Notes item) {

        //Toast.makeText(this,"NOTE",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PreviewActivity.this, EditorActivity.class);
        intent.putExtra("id",item.id);
        intent.putExtra("title",item.title);
        intent.putExtra("text",item.text);
        intent.putExtra("time",item.timestamp);
        Log.d("mNote ID sent", String.valueOf(item.id));
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        if(mExit) {
            finish();
        }
        else
        {
            Toast.makeText(this, R.string.backagain,Toast.LENGTH_SHORT).show();
            mExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mExit = false;
                }
            }, 3 * 1000);
        }
    }

}
