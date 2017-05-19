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

import colormemory.com.colormemory.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private int mCurrentScore = 0;
    boolean mstrategy = false;
    ActivityMainBinding mBinding;

    private ImageView mPreviousSelectedCard, mCurrentlySelectedCard;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setHandler(this);
        mHandler = new Handler();
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                mstrategy = true;
            } else {
                mstrategy = false;
            }

            for (int j = 0; j < 2; j++) {
                int identifier = getResources().getIdentifier("colour" + (i + j + 1), "drawable", getPackageName());
                Log.e(TAG, "name of drawable" + "colour" + (i + j + 1));
                Log.e(TAG, " drawable identifier " + identifier);
                CardModel cardModel = new CardModel(i + j,
                        ContextCompat.getDrawable(this, identifier));
                int imageViewIdentifier = getResources().getIdentifier("iv_" + i + "" + j, "id", getPackageName());
                Log.e(TAG, "name of iv " + "iv_" + i + "" + j);
                Log.e(TAG, " imageview identifier " + imageViewIdentifier);
                findViewById(imageViewIdentifier).setTag(cardModel);
            }


        }

    }


    public void onClick(final View view) {
        final CardModel cardModel = ((CardModel) view.getTag());
        ((ImageView) view).setBackground(cardModel.drawable);
        if (mPreviousSelectedCard == null) {
            mPreviousSelectedCard = ((ImageView) view);
        } else {
            mCurrentlySelectedCard = ((ImageView) view);
            final CardModel cardModel1 = (CardModel) mPreviousSelectedCard.getTag();
            final CardModel cardModel2 = (CardModel) mCurrentlySelectedCard.getTag();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (cardModel1.id == cardModel2.id) {
                        cardModel.isRevealed = true;
                        cardModel2.isRevealed = true;
                    } else {
                        mPreviousSelectedCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                        mCurrentlySelectedCard.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.card_bg));
                        mCurrentlySelectedCard = mCurrentlySelectedCard = null;
                    }
                }
            }, 500);

        }


    }


}
