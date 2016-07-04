package m3.jobber.UI.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import m3.jobber.UI.activities.MainActivity;
import m3.jobber.UI.fragments.FragmentsBuffer;
import m3.jobber.UI.fragments.MonthFragment;
import m3.jobber.logic.model.CalendarMonth;
import m3.jobber.logic.model.Constants;
import m3.jobber.logic.model.WorkingMonth;

/**
 * Created by misko on 22.6.2016.
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private MainActivity mainActivity;
    private FragmentsBuffer fragmentsBuffer;

    public ViewPagerFragmentAdapter(FragmentManager fm, MainActivity mainActivity, FragmentsBuffer fragmentsBuffer) {
        super(fm);
        this.mainActivity = mainActivity;
        this.fragmentsBuffer = fragmentsBuffer;
    }

    @Override
    public Fragment getItem(int position) {
//       return fragmentsBuffer.getFragment(position);
        CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(position);
        return MonthFragment.createFragment(helper.getMonth(), helper.getYear());
    }

    @Override
    public int getCount() {
        return Constants.MAX_MONTHS;
    }
}
