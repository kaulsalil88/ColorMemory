package model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by salil-kaul on 20/5/17.
 */

public class ScoreRowModel extends RealmObject {

    @PrimaryKey
    int score;

    String name;
}
