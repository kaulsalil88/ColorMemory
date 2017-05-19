package db;

import android.provider.BaseColumns;

/**
 * Created by salil-kaul on 20/5/17.
 */

public final class ScoreContract {

    private ScoreContract() {

    }

    public static class Score implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_NAME = "name";
    }


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Score.TABLE_NAME + " (" +
                    Score._ID + " INTEGER PRIMARY KEY," +
                    Score.COLUMN_NAME_SCORE + " INTEGER," +
                    Score.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Score.TABLE_NAME;


}
