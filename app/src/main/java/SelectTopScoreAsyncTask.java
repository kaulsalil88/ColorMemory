import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by salil-kaul on 21/5/17.
 */

public class SelectTopScoreAsyncTask extends AsyncTaskLoader {


    public SelectTopScoreAsyncTask(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {
        return null;
    }
}
