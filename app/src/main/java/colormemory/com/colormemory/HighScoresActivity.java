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

public class HighScoresActivity extends AppCompatActivity implements OnScoresLoaded {

    private static final String TAG = HighScoresActivity.class.getSimpleName();
    ActivityHighScoresBinding mBinding;
    List<ScoreRowModel> scores = new ArrayList<ScoreRowModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_high_scores);
        mBinding.toolbar.setTitle(getString(R.string.highscores));
        getCurrentHighScore();
    }


    private void getCurrentHighScore() {
        SelectTopScoreAsyncTask selectTopScoreAsyncTask = new SelectTopScoreAsyncTask(this);
        selectTopScoreAsyncTask.setmOnScoresLoaded(this);
        selectTopScoreAsyncTask.execute();

    }


    @Override
    public void onScoresLoaded(List<ScoreRowModel> scores) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mBinding.rvHighScore.setLayoutManager(linearLayoutManager);
        ScoreRowAdapter scoreRowAdapter = new ScoreRowAdapter(scores);

        mBinding.rvHighScore.setAdapter(scoreRowAdapter);
    }
}
