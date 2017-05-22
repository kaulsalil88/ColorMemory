package model;


/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreRowModel {

    public int getIndex() {
        return index;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public ScoreRowModel(int index, int score, String name) {
        this.index = index;
        this.score = score;
        this.name = name;

    }

    private int index;
    private int score;

    private String name;
}
