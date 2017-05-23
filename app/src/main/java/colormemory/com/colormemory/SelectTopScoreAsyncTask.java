package colormemory.com.colormemory;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import colormemory.com.colormemory.OnScoresLoaded;
import db.ScoreContract;
import db.ScoreDBHelper;
import model.ScoreRowModel;

/**
 * Created by salil-kaul on 21/5/17.
 */

public class SelectTopScoreAsyncTask extends AsyncTask<Void, Void, List<ScoreRowModel>> {

    OnScoresLoaded mOnScoresLoaded = null;
    //Using weak reference to avoid memory leaks .
    WeakReference<Activity> myActivity;


    public SelectTopScoreAsyncTask(Activity activity) {
        this.myActivity = new WeakReference<Activity>(activity);
    }

    public void setmOnScoresLoaded(OnScoresLoaded mOnScoresLoaded) {
        this.mOnScoresLoaded = mOnScoresLoaded;
    }

    @Override
    protected List<ScoreRowModel> doInBackground(Void... params) {
        List<ScoreRowModel> scores = new ArrayList<>();
        if (myActivity.get() != null) {
            ScoreDBHelper scoreDBHelper = new ScoreDBHelper(myActivity.get());
            SQLiteDatabase sqLiteDatabase = scoreDBHelper.getReadableDatabase();
            String[] projection = {
                    ScoreContract.Score.COLUMN_NAME_NAME,
                    ScoreContract.Score.COLUMN_NAME_SCORE
            };
            String sortOrder = ScoreContract.Score.COLUMN_NAME_SCORE + " DESC  ";
            Cursor cursor = sqLiteDatabase.query(ScoreContract.Score.TABLE_NAME, projection,                               // The columns to return
                    null,
                    null,
                    null,
                    null,
                    sortOrder);

            int currentHighScore = 0;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
                ScoreRowModel scoreRowModel = new ScoreRowModel(count, cursor.getInt(
                        cursor.getColumnIndexOrThrow(ScoreContract.Score.COLUMN_NAME_SCORE)), cursor.getString(
                        cursor.getColumnIndexOrThrow(ScoreContract.Score.COLUMN_NAME_NAME)));
                scores.add(scoreRowModel);
            }
            cursor.close();
        }
        return scores;
    }

    @Override
    public void onPostExecute(List<ScoreRowModel> scores) {
        mOnScoresLoaded.onScoresLoaded(scores);
    }

}
