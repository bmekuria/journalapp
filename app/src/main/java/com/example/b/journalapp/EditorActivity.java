package com.example.b.journalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.b.journalapp.Models.Notes;
import com.example.b.journalapp.Utilites.DatabaseHelper;

public class EditorActivity extends AppCompatActivity {

    EditText mEditor, mTitle;
    ImageView mDelete, mInfo, mSave;
    Notes mItem;
    Intent mIntent;

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTitle = findViewById(R.id.title_editText);
        mEditor = findViewById(R.id.editor_editText);
        mDelete = findViewById(R.id.deleteoption);
        mInfo = findViewById(R.id.ic_menu_dialog);
        mSave = findViewById(R.id.ic_menu_save);

        mIntent = getIntent();
        if(mIntent != null){
            mTitle.setText(mIntent.getStringExtra("title"));
            mEditor.setText(mIntent.getStringExtra("text"));
        }

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete note
            }
        });

        //mInfo.setOnClickListener();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItem = new Notes();
                mItem.title = String.valueOf(mTitle.getText());
                mItem.text = String.valueOf(mEditor.getText());
                new addNote().execute(mItem);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /*
    AsyncTask that creates the note.
     */
    public class addNote extends AsyncTask<Notes, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Notes... notes) {

            boolean mResponse = false;

            DatabaseHelper db = new DatabaseHelper(EditorActivity.this);

            mResponse = db.createnote(db, notes[0]);

            db.close();

            return mResponse;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText (EditorActivity.this,"Created",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText (EditorActivity.this,"NOT Created",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    TODO:
    - Be able to edit text.
    - When the user goes UP, the timestamp needs to be updated for the note and title, text updated accordingly.
]    - Change the size of font
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
            default:break;
        }
        return super.onOptionsItemSelected(item);

    }

}
