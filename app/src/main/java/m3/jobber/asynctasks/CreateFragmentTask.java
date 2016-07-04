package m3.jobber.asynctasks;

import android.os.AsyncTask;

import m3.jobber.UI.activities.MainActivity;
import m3.jobber.UI.fragments.MonthFragment;

/**
 * Created by misko on 29.6.2016.
 */
public class CreateFragmentTask extends AsyncTask<Integer, Void, MonthFragment> {

    public MonthFragmentCreated monthFragmentCreated = null;
    private int direction;

    public CreateFragmentTask(MonthFragmentCreated monthFragmentCreated) {
        this.monthFragmentCreated = monthFragmentCreated;
    }

    @Override
    protected MonthFragment doInBackground(Integer... params) {
        int month = params[0];
        int year = params[1];
        direction = params[2];

        return (MonthFragment) MonthFragment.createFragment(month, year);
    }

    @Override
    protected void onPostExecute(final MonthFragment monthFragment) {
        monthFragmentCreated.onMonthFragmentCreated(monthFragment, direction);
    }

    public interface MonthFragmentCreated {
        void onMonthFragmentCreated(MonthFragment monthFragment, int direction);
    }
}
