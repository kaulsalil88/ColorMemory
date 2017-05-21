package colormemory.com.colormemory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import colormemory.com.colormemory.databinding.FragmentScoreUpdateBinding;
import db.ScoreContract;
import db.ScoreDBHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreUpdateDialogFragment extends DialogFragment {


    FragmentScoreUpdateBinding mBinding;

    public ScoreUpdateDialogFragment() {
    }


    public static ScoreUpdateDialogFragment newInstance() {
        ScoreUpdateDialogFragment scoreUpdateDialogFragment = new ScoreUpdateDialogFragment();
        return scoreUpdateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_update, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle bundle) {
        //ScoreDBHelper scoreDBHelper = new ScoreDBHelper(view.getContext());
        mBinding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }


    private void insertData() {
        // Gets the data repository in write mode
        ScoreDBHelper scoreDBHelper = new ScoreDBHelper(mBinding.getRoot().getContext());
        SQLiteDatabase db = scoreDBHelper.getWritableDatabase();
//
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ScoreContract.Score.COLUMN_NAME_NAME, mBinding.etName.getText().toString());

        values.put(ScoreContract.Score.COLUMN_NAME_SCORE, mBinding.tvScore.getText().toString());
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ScoreContract.Score.TABLE_NAME, null, values);
        Log.d(TAG, "insertData() called" + newRowId);
        dismiss();
    }
}
