package colormemory.com.colormemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import colormemory.com.colormemory.databinding.ActivityMainBinding;
import db.ScoreContract;
import db.ScoreDBHelper;
import model.CardModel;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int mCurrentScore = 0;
    boolean mstrategy = false;
    ActivityMainBinding mBinding;
    int mColorNumber = 1, mColorCount = 0;
    private ImageView mPreviousSelectedCard, mCurrentlySelectedCard;

    private Handler mHandler;
    int mSelectedPairs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setHandler(this);
        mHandler = new Handler();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {

                Log.e(TAG, "i is " + i + "and j is " + j + " and the mColorNumber is " + mColorNumber);
                setImageViewData(i, j);
                //Setting the card at 2units away on the x axis
                int tempy = j + 2;
                setImageViewData(i, tempy);
                //Incrementing the color number
                mColorNumber++;


            }


        }

        mBinding.tvHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchHighScoreActivity = new Intent(v.getContext(), HighScoresActivity.class);
                startActivity(launchHighScoreActivity);
            }
        });

    }


    public void onClick(final View view) {

        if (mCurrentlySelectedCard == null || (mCurrentlySelectedCard != null && mPreviousSelectedCard == null)) {
            final CardModel cardModel = ((CardModel) view.getTag());
            if (!cardModel.isRevealed()) {
                ((ImageView) view).setBackgroundDrawable(cardModel.getDrawable());
                if (mPreviousSelectedCard == null) {
                    mPreviousSelectedCard = ((ImageView) view);
                    return;
                } else {
                    mCurrentlySelectedCard = ((ImageView) view);
                    final CardModel cardModel1 = (CardModel) mPreviousSelectedCard.getTag();
                    final CardModel cardModel2 = (CardModel) mCurrentlySelectedCard.getTag();
                    //Scoring will happen after a delay of 1 second .
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (cardModel1.getId() == cardModel2.getId()) {
                                mSelectedPairs++;
                                cardModel1.setRevealed(true);
                                cardModel2.setRevealed(true);
                                mCurrentScore = mCurrentScore + 2;
                                mBinding.tvCurrentscore.setText(String.valueOf(mCurrentScore));
                                mCurrentlySelectedCard = mPreviousSelectedCard = null;
                                //All the cards have been revealed .
                                if (mSelectedPairs == 8) {
                                    Toast.makeText(view.getContext(), getString(R.string.congratsnewhighscore), Toast.LENGTH_SHORT).show();
                                    getCurrentHighScore();
                                }
                            } else {
                                mCurrentScore = mCurrentScore - 1;
                                mBinding.tvCurrentscore.setText(String.valueOf(mCurrentScore));
                                mPreviousSelectedCard.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                                mCurrentlySelectedCard.setBackgroundDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                                mCurrentlySelectedCard = mPreviousSelectedCard = null;
                            }
                        }
                    }, 1000);


                }

            } else {
                Toast.makeText(view.getContext(), getString(R.string.alreadymatched), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(view.getContext(), getString(R.string.pleasewait), Toast.LENGTH_SHORT).show();
        }


    }


    private void setImageViewData(int x, int y) {
        int drawableIdentifier = getResources().getIdentifier("colour" + mColorNumber, "drawable", getPackageName());
        int imageViewIdentifier = getResources().getIdentifier("iv_" + x + "" + y, "id", getPackageName());
        CardModel cardModel = new CardModel(mColorNumber,
                ContextCompat.getDrawable(this, drawableIdentifier));
        findViewById(imageViewIdentifier).setTag(cardModel);
    }


    private void launchSaveScoreScreen() {
        ScoreInsertDialogFragment scoreInsertDialogFragment = ScoreInsertDialogFragment.newInstance(mCurrentScore);
        scoreInsertDialogFragment.show(getSupportFragmentManager(), ScoreInsertDialogFragment.TAG);

    }


    public void reset() {
        mCurrentScore = 0;
        mSelectedPairs = 0;
        mCurrentlySelectedCard = mPreviousSelectedCard = null;
        mBinding.tvCurrentscore.setText(String.valueOf(mCurrentScore));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int imageViewIdentifier = getResources().getIdentifier("iv_" + i + "" + j, "id", getPackageName());
                ImageView imageView = ((ImageView) findViewById(imageViewIdentifier));
                imageView.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.card_bg));
                CardModel cardModel = ((CardModel) imageView.getTag());
                cardModel.setRevealed(false);
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        reset();
    }


    private void getCurrentHighScore() {
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... params) {
                ScoreDBHelper scoreDBHelper = new ScoreDBHelper(MainActivity.this);
                SQLiteDatabase sqLiteDatabase = scoreDBHelper.getReadableDatabase();
                String[] projection = {
                        ScoreContract.Score.COLUMN_NAME_NAME,
                        ScoreContract.Score.COLUMN_NAME_SCORE
                };

                String sortOrder = ScoreContract.Score.COLUMN_NAME_SCORE + " DESC  LIMIT 1";
                Cursor cursor = sqLiteDatabase.query(ScoreContract.Score.TABLE_NAME, projection,                               // The columns to return
                        null,
                        null,
                        null,
                        null,
                        sortOrder);

                int currentHighScore = 0;
                while (cursor.moveToNext()) {
                    currentHighScore = cursor.getInt(
                            cursor.getColumnIndexOrThrow(ScoreContract.Score.COLUMN_NAME_SCORE));

                    Log.e(TAG, "calculateCurrentHighScore: HighScore " + currentHighScore);
                }
                cursor.close();
                return currentHighScore;
            }

            @Override
            public void onPostExecute(Integer integer) {
                //Saving the data in the table if the value is greater
                //than the highest value stored in the table
                if (mCurrentScore > integer) {
                    launchSaveScoreScreen();
                }
            }
        }.execute();


    }


}
