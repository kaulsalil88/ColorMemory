package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Scores.db";

    public ScoreDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreContract.Score.TABLE_NAME + " (" +
                    ScoreContract.Score._ID + " INTEGER PRIMARY KEY," +
                    ScoreContract.Score.COLUMN_NAME_SCORE + " INTEGER," +
                    ScoreContract.Score.COLUMN_NAME_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreContract.Score.TABLE_NAME;
}
