package com.example.b.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

    EditText mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mEditor = findViewById(R.id.editor_editText);

        Intent mIntent = getIntent();
        if(mIntent != null){
            mEditor.setText(mIntent.getStringExtra("text"));
        }

    }

    /*
    TODO:
    - Be able to edit text.
    - When the user goes UP, the timestamp needs to be updated for the note.
    - Delete note option
    - Change the size of font
    - Add, Change or remove from category NP
     */
}
