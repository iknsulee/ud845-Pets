package com.example.android.habits.data;

import android.provider.BaseColumns;

public class HabitContract {
    private HabitContract() {
    }

    public static class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_COMPLETE = "complete";

        public static final int COMPLETE_NO = 0;
        public static final int COMPLETE_YES = 1;

    }
}
