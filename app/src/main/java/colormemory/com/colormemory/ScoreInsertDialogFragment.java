package colormemory.com.colormemory;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import colormemory.com.colormemory.databinding.FragmentScoreUpdateBinding;
import db.ScoreContract;
import db.ScoreDBHelper;
import model.ScoreRowModel;

/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreInsertDialogFragment extends DialogFragment implements OnScoresLoaded {


    public static final String TAG = ScoreInsertDialogFragment.class.getSimpleName();
    FragmentScoreUpdateBinding mBinding;
    private int mScore;

    public ScoreInsertDialogFragment() {
    }


    public static ScoreInsertDialogFragment newInstance(int score) {
        ScoreInsertDialogFragment scoreUpdateDialogFragment = new ScoreInsertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("score", score);
        scoreUpdateDialogFragment.setArguments(bundle);
        return scoreUpdateDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScore = getArguments().getInt("score");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_score_update, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(final View view, Bundle bundle) {
        //ScoreDBHelper scoreDBHelper = new ScoreDBHelper(view.getContext());
        mBinding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mBinding.etName.getText().toString())) {
                    insertData();
                } else {
                    Toast.makeText(view.getContext(), getString(R.string.namecantbeempty), Toast.LENGTH_SHORT).show();
                }


            }
        });
        mBinding.tvScore.setText(String.valueOf(mScore));
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
        Log.e(TAG, "insertData() called" + newRowId);
        getRank();
        dismiss();
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = super.onCreateDialog(bundle);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }


    private void getRank() {
        SelectTopScoreAsyncTask scoreAsyncTask = new SelectTopScoreAsyncTask(getActivity());
        scoreAsyncTask.setmOnScoresLoaded(this);
        scoreAsyncTask.execute();


    }

    @Override
    public void onScoresLoaded(List<ScoreRowModel> scores) {
        ScoreRowModel scoreRowModel = null;
        for (ScoreRowModel scoreRowModel1 : scores) {
            if (scoreRowModel1.getScore() == mScore) {
                scoreRowModel = scoreRowModel1;
                break;
            }
        }

        Toast.makeText(this.getContext(), getString(R.string.yourscoreis, scoreRowModel.getIndexOne()), Toast.LENGTH_SHORT).show();
    }
}
