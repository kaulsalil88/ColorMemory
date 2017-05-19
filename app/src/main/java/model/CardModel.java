package model;

import android.graphics.drawable.Drawable;

/**
 * Created by salil-kaul on 19/5/17.
 */

public class CardModel {
    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    //The id of the card .
    int id;

    //The drawable to be revealed on click
    Drawable drawable;

    //The flag which is set to true to determine wether the card has been revealed or not .
    boolean isRevealed = false;

    public CardModel(int id, Drawable drawable) {
        this.id = id;
        this.drawable = drawable;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public int getId() {
        return id;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
