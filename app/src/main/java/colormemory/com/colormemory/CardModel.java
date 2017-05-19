package colormemory.com.colormemory;

import android.graphics.drawable.Drawable;

/**
 * Created by salil-kaul on 19/5/17.
 */

public class CardModel {

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
}
