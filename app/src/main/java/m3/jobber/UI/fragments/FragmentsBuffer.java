package m3.jobber.UI.fragments;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import m3.jobber.logic.model.CalendarMonth;
import m3.jobber.logic.model.Constants;

/**
 * Created by misko on 29.6.2016.
 */
public class FragmentsBuffer {

    public static final int LEFT = -1;
    public static final int RIGHT = -2;

    private int currentMiddle = Constants.CURRENT_POSITION;

    private ArrayList<MonthFragment> monthFragments = new ArrayList<>();

    public FragmentsBuffer() {

        CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(currentMiddle - 2);
        monthFragments.add((MonthFragment) MonthFragment.createFragment(helper.getMonth(), helper.getYear()));

        helper = new CalendarMonth.CalendarMonthHelper(currentMiddle - 1);
        monthFragments.add((MonthFragment) MonthFragment.createFragment(helper.getMonth(), helper.getYear()));

        helper = new CalendarMonth.CalendarMonthHelper(currentMiddle);
        monthFragments.add((MonthFragment) MonthFragment.createFragment(helper.getMonth(), helper.getYear()));

        helper = new CalendarMonth.CalendarMonthHelper(currentMiddle + 1);
        monthFragments.add((MonthFragment) MonthFragment.createFragment(helper.getMonth(), helper.getYear()));

        helper = new CalendarMonth.CalendarMonthHelper(currentMiddle + 2);
        monthFragments.add((MonthFragment) MonthFragment.createFragment(helper.getMonth(), helper.getYear()));
    }

    public void addFragmentLeft(MonthFragment monthFragment) {
        monthFragments.remove(4);
        monthFragments.add(0, monthFragment);

        currentMiddle--;
    }

    public void addFragmentRight(MonthFragment monthFragment) {
        monthFragments.remove(0);
        monthFragments.add(monthFragment);

        currentMiddle++;
    }

    public Fragment getFragment(int position) {
        try {
            return monthFragments.get((position - currentMiddle) + 2);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();

            CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(position);

            return MonthFragment.createFragment(helper.getMonth(), helper.getYear());
        }
    }
}
