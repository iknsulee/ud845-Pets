package com.example.android.habits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.habits.data.HabitContract;

public class HabitTracker {

    private static final String LOG_TAG = HabitTracker.class.getSimpleName();

    private final HabitDbHelper habitDbHelper;

    public HabitTracker(Context context) {
        habitDbHelper = new HabitDbHelper(context);
    }

    public long insertHabit(String name, boolean complete) {
        // Gets the database in write mode
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_COMPLETE, complete ? 1 : 0);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the habits table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for habit.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor getCursor() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_COMPLETE
        };
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;

    }

    public void list() {

        Cursor cursor = getCursor();

        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int completeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_COMPLETE);

            while (cursor.moveToNext()) {
                // Use that index to extract String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentComplete = cursor.getInt(completeColumnIndex);

                Log.d(LOG_TAG, "id:" + String.valueOf(currentID) + ",name: " +
                        currentName + ",complete: " + String.valueOf(currentComplete));

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

}
