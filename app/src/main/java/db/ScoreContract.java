package db;

import android.provider.BaseColumns;

/**
 * Created by salil-kaul on 20/5/17.
 */

public final class ScoreContract {

    private ScoreContract() {

    }

    public static class Score implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_NAME = "name";
    }





}
