package com.example.b.journalapp.Utilites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.example.b.journalapp.Models.Notes;

public class DatabaseHelper extends SQLiteOpenHelper{

    public String TABLE_NAME = "NOTES";

    public DatabaseHelper(Context ctx) {
        super(ctx, "journalapp.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_NOTES = "CREATE TABLE IF NOT EXISTS NOTES("+
                "_id INTEGER PRIMARY KEY,"+
                "title TEXT,"+
                "notes TEXT,"+
                "timestamp DATE NOT NULL,"+
                "category TEXT" +
                ")";

        try {

            db.execSQL(CREATE_TABLE_NOTES);

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS NOTES");
            onCreate(db);
        }
        else {
            onCreate(db);
        }

    }

    /*
    Create a note
     */
    public boolean createnote(DatabaseHelper db, Notes mItem) {

        SQLiteDatabase sq = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if(!TextUtils.isEmpty(mItem.text)) {
            cv.put("title", mItem.title);
            cv.put("notes", mItem.text);
            cv.put("timestamp", System.currentTimeMillis());

            sq.insert(TABLE_NAME, null, cv);
            Log.d("NEW NOTE","Note added"+mItem.text);
            return true;
        }

        return false;

    }

    /*
    Query all the notes
     */
    public Notes[] querynote(DatabaseHelper db) {

        Notes[] allnotes = new Notes[0];
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor mCursor = null;
        String QUERY = "SELECT * FROM "+TABLE_NAME;

        try
        {
            mCursor = sq.rawQuery(
                    QUERY,
                    null
            );
        }catch (SQLException | NullPointerException e){
            e.printStackTrace();
        }

        if(mCursor.moveToFirst()) {
            allnotes = new Notes[mCursor.getCount()];
            try{
                for(int i = 0; i < mCursor.getCount(); i++, mCursor.moveToNext()) {
                    allnotes[i] = new Notes();
                    allnotes[i].title = mCursor.getString(mCursor.getColumnIndex("title"));
                    allnotes[i].text = mCursor.getString(mCursor.getColumnIndex("notes"));
                    allnotes[i].timestamp = mCursor.getLong(mCursor.getColumnIndex("timestamp"));

                }
                mCursor.close();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        else {
            mCursor.close();
            Log.d("NO NOTES","");
        }

        return allnotes;
    }

    /*
    QUERY TO DELETE NOTE
     */

    public int deleteNote(DatabaseHelper db, int noteId) {

        SQLiteDatabase sq = db.getReadableDatabase();
        int value = 0;

        try {
            value = sq.delete(TABLE_NAME,"_id=?",
                    new String[] {Integer.toString(noteId)});

        }catch (SQLException e) {
            e.printStackTrace();
            value = 0;
        }

        return value;
    }


    /*
    TODO:
    1. Create database
    2. Create table Notes
    3. Create query to create note
    4. Create query to update note (title, text, category, timestamp)
    5. Create query to delete note
     */
}
