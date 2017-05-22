package colormemory.com.colormemory;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import colormemory.com.colormemory.databinding.ItemScoreRowBinding;
import model.ScoreRowModel;

/**
 * Created by salil-kaul on 22/5/17.
 */

public class ScoreRowAdapter extends RecyclerView.Adapter<ScoreRowAdapter.ScoreRowVH> {
    List<ScoreRowModel> scoreRowModels;

    public ScoreRowAdapter(List<ScoreRowModel> scoreRowModels) {
        this.scoreRowModels = scoreRowModels;
    }

    @Override
    public ScoreRowAdapter.ScoreRowVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ScoreRowVH(layoutInflater.inflate(R.layout.item_score_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoreRowAdapter.ScoreRowVH holder, int position) {
        ScoreRowModel scoreRowModel = scoreRowModels.get(position);
        ItemScoreRowBinding binding = holder.getmBinding();
        binding.setModel(scoreRowModel);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return scoreRowModels.size();
    }

    static class ScoreRowVH extends RecyclerView.ViewHolder {
        ItemScoreRowBinding mBinding;

        public ScoreRowVH(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }


        ItemScoreRowBinding getmBinding() {
            return mBinding;
        }
    }

}
