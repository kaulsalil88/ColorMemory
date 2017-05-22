package model;


/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreRowModel {

    public int getIndexOne() {
        return indexOne;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public ScoreRowModel(int indexOne, int score, String name) {
        this.indexOne = indexOne;
        this.score = score;
        this.name = name;

    }

    private int indexOne;
    private int score;

    private String name;
}
