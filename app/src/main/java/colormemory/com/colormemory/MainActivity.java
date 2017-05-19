package colormemory.com.colormemory;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.Image;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.StringTokenizer;

import colormemory.com.colormemory.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int mCurrentScore = 0;
    boolean mstrategy = false;
    ActivityMainBinding mBinding;
    int mColorNumber = 1, mColorCount = 0;
    private ImageView mPreviousSelectedCard, mCurrentlySelectedCard;

    private Handler mHandler;
    private boolean mIsMatched = false;

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

    }


    public void onClick(final View view) {
        final CardModel cardModel = ((CardModel) view.getTag());
        if (!cardModel.isRevealed) {
            ((ImageView) view).setBackground(cardModel.drawable);
            if (mPreviousSelectedCard == null) {
                mPreviousSelectedCard = ((ImageView) view);
                return;
            } else {
                mCurrentlySelectedCard = ((ImageView) view);
                final CardModel cardModel1 = (CardModel) mPreviousSelectedCard.getTag();
                final CardModel cardModel2 = (CardModel) mCurrentlySelectedCard.getTag();
                if (cardModel1.id == cardModel2.id) {
                    mIsMatched = true;
                    cardModel.isRevealed = true;
                    cardModel2.isRevealed = true;
                    mCurrentScore = mCurrentScore + 2;
                } else {
                    mIsMatched = false;
                    mCurrentScore = mCurrentScore - 1;
                }
                if (!mIsMatched) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPreviousSelectedCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                            mCurrentlySelectedCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                            mCurrentlySelectedCard = mPreviousSelectedCard = null;
                            mBinding.getRoot().setEnabled(false);
                        }
                    }, 1000);

                    //Disabling the parent layout for the duration till the cards arent reset this to prevent the
                    //mPreviousSelectedCard and mCurrentlySelectedCard from getting reset .
                    mBinding.getRoot().setEnabled(false);

                } else {
                    mCurrentlySelectedCard = mPreviousSelectedCard = null;
                }


            }

        } else {
            Toast.makeText(view.getContext(), getString(R.string.alreadymatched), Toast.LENGTH_SHORT).show();
        }

        mBinding.tvCurrentscore.setText(String.valueOf(mCurrentScore));

    }


    private void setImageViewData(int x, int y) {
        int drawableIdentifier = getResources().getIdentifier("colour" + mColorNumber, "drawable", getPackageName());
        int imageViewIdentifier = getResources().getIdentifier("iv_" + x + "" + y, "id", getPackageName());
        CardModel cardModel = new CardModel(mColorNumber,
                ContextCompat.getDrawable(this, drawableIdentifier));
        findViewById(imageViewIdentifier).setTag(cardModel);
    }


}
