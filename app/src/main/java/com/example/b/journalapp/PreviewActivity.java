package com.example.b.journalapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.b.journalapp.Models.Notes;

import java.util.ArrayList;
import java.util.Date;

public class PreviewActivity extends AppCompatActivity
        implements RecyclerViewAdapter.ItemListener{

    private RecyclerView mNotesList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Notes[] DUMMY_DATASET = new Notes[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mNotesList = (RecyclerView) findViewById(R.id.preview_notes);
        mNotesList.setHasFixedSize(true);

        DUMMY_DATASET[0] = new Notes();
        DUMMY_DATASET[0].text = "France vs Uruguay";
        DUMMY_DATASET[0].timestamp = System.currentTimeMillis();
        DUMMY_DATASET[0].category = "Sports";

        DUMMY_DATASET[1] = new Notes();
        DUMMY_DATASET[1].text = "Amazing Pasta Dish";
        DUMMY_DATASET[1].timestamp = System.currentTimeMillis();
        DUMMY_DATASET[1].category = "Cooking";

        DUMMY_DATASET[2] = new Notes();
        DUMMY_DATASET[2].text = "Jog! Jog! Jog!";
        DUMMY_DATASET[2].timestamp = System.currentTimeMillis();
        DUMMY_DATASET[2].category = "Health";

        mLayoutManager = new LinearLayoutManager(this);
        mNotesList.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this, DUMMY_DATASET, this);
        mNotesList.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            case R.id.item_about:
                Toast.makeText(this,"ABOUT",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_settings:
                Toast.makeText(this,"SETTINGS",Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_bar_search:
                Toast.makeText(this,"SEARCH",Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Notes item) {

        Toast.makeText(this,"NOTE",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PreviewActivity.this, EditorActivity.class);
        intent.putExtra("id",item.id);
        intent.putExtra("text",item.text);
        intent.putExtra("category",item.category);
        intent.putExtra("time",item.timestamp);
        startActivity(intent);

    }
}
