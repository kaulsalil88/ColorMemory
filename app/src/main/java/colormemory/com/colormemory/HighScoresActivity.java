package colormemory.com.colormemory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import colormemory.com.colormemory.databinding.ActivityHighScoresBinding;
import db.ScoreContract;
import db.ScoreDBHelper;
import model.ScoreRowModel;

public class HighScoresActivity extends AppCompatActivity {

    private static final String TAG = HighScoresActivity.class.getSimpleName();
    ActivityHighScoresBinding mBinding;
    List<ScoreRowModel> scores = new ArrayList<ScoreRowModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_high_scores);
        getCurrentHighScore();
    }


    private void getCurrentHighScore() {
        ScoreDBHelper scoreDBHelper = new ScoreDBHelper(this);
        SQLiteDatabase sqLiteDatabase = scoreDBHelper.getReadableDatabase();
        String[] projection = {
                ScoreContract.Score.COLUMN_NAME_NAME,
                ScoreContract.Score.COLUMN_NAME_SCORE
        };

        //String selection = "ORDER BY " + ScoreContract.Score.COLUMN_NAME_SCORE;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.rvHighScore.setLayoutManager(linearLayoutManager);
        ScoreRowAdapter scoreRowAdapter = new ScoreRowAdapter(scores);

        mBinding.rvHighScore.setAdapter(scoreRowAdapter);
    }


}
