package colormemory.com.colormemory;

import java.util.List;

import model.ScoreRowModel;

/**
 * Created by salil-kaul on 24/5/17.
 */

public interface OnScoresLoaded {

    void onScoresLoaded(List<ScoreRowModel> scores);
}
