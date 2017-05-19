package colormemory.com.colormemory;

import android.graphics.drawable.Drawable;

/**
 * Created by salil-kaul on 19/5/17.
 */

public class CardModel {

    int id;
    Drawable drawable;
    boolean isRevealed = false;

    public CardModel(int id, Drawable drawable) {
        this.id = id;
        this.drawable = drawable;
    }
}
