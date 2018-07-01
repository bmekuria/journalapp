package com.example.b.journalapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    ImageView mDelete, mSave;
    Notes mItem;
    Intent mIntent;
    int mNoteId = 0;

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
        mSave = findViewById(R.id.ic_menu_save);


        mSave.setClickable(true);

        checkIntent();

        mDelete.setClickable(true);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mNote ID", String.valueOf(mNoteId));
                if(mNoteId > 0) {
                    new deleteNote().execute(mNoteId);
                }

            }
        });


    }

    private void checkIntent() {
        mIntent = getIntent();
        if(mIntent != null){
            mTitle.setText(mIntent.getStringExtra("title"));
            mEditor.setText(mIntent.getStringExtra("text"));
            mNoteId = mIntent.getIntExtra("id",0);
        }
    }

    public void clickSave(View v) {
        Log.d("clickSave", "clicked");
            mItem = new Notes();
            mItem.id = mNoteId;
            mItem.title = String.valueOf(mTitle.getText());
            mItem.text = String.valueOf(mEditor.getText());
            new addNote().execute(mItem);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIntent();
    }

    public void setClick(boolean mValue) {
        mDelete.setClickable(mValue);
        mSave.setClickable(mValue);
    }

    /*
    AsyncTask that creates the note.
     */
    public class addNote extends AsyncTask<Notes, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Notes... notes) {

            boolean mResponse = false;

            DatabaseHelper db = new DatabaseHelper(EditorActivity.this);
            if(notes[0].id == 0) {
                mResponse = db.createnote(db, notes[0]);
            }
            else
            {
                mResponse = db.updateNote(db,notes[0]);
            }
            db.close();

            return mResponse;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText (EditorActivity.this,"Note Saved",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText (EditorActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Delete a note
     * @param
     * @return boolean
     */

    public class deleteNote extends  AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {

            DatabaseHelper db = new DatabaseHelper(EditorActivity.this);
            int result = db.deleteNote(db, integers[0]);

            return result;
        }

        @Override
        protected void onPostExecute(Integer aResult) {
            super.onPostExecute(aResult);
            if(aResult > 0) {
                Toast.makeText(EditorActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(EditorActivity.this,"Error in deleting note",Toast.LENGTH_SHORT).show();
            }

        }
    }

    /*
    Modify a note
     */


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
