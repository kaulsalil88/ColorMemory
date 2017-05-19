package colormemory.com.colormemory;

import android.app.Activity;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;

import colormemory.com.colormemory.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private int mCurrentScore = 0;
    boolean mstrategy = false;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) {
                mstrategy = true;
            } else {
                mstrategy = false;
            }
            for (int j = 0; j < 4; j++) {

            }
        }

    }
}
