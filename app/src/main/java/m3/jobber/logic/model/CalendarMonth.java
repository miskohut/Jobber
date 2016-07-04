package m3.jobber.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import m3.jobber.R;

/**
 * Created by misko on 20.6.2016.
 */
public class CalendarMonth {

    public static final int MAX_DAYS = 42;

    private int[] dayNumbers = new int[MAX_DAYS];
    private int year;
    private int month;

    private int monthMax;
    private int firstPosition;

    public CalendarMonth(int month, int year) {
        this.month = month - 1;
        this.year = year;

        setDayNumbers();
    }

    private void setDayNumbers() {
        Calendar currMonth = (Calendar) Calendar.getInstance().clone();
        currMonth.set(Calendar.MONTH, month);
        currMonth.set(Calendar.YEAR, year);
        currMonth.set(Calendar.DAY_OF_MONTH, 1);

        Calendar prevMonth = (Calendar) Calendar.getInstance().clone();

        monthMax = currMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        firstPosition = getPositionInWeek(currMonth);

        currMonth.set(Calendar.DAY_OF_MONTH, monthMax);

        int lastDayInWeek = getPositionInWeek(currMonth);

        int prevYear = year;
        int prevMon = month - 1;

        if (prevMon == Calendar.JANUARY) {
            prevYear--;
            prevMon = Calendar.DECEMBER;
        }

        prevMonth.set(Calendar.MONTH, prevMon);
        prevMonth.set(Calendar.YEAR, prevYear);

        int prevMonthMax = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = firstPosition - 1; i >= 0; i--) {
            dayNumbers[i] = prevMonthMax--;
        }

        for (int i = firstPosition; i < monthMax + firstPosition; i++) {
            dayNumbers[i] = i - firstPosition + 1;
        }

        for (int i = monthMax + firstPosition, counter = 1; i < MAX_DAYS; i++, counter++) {
            dayNumbers[i] = counter;
        }
    }

    public int getMonthMax() {
        return monthMax;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    private int getPositionInWeek(Calendar calendar) {
        int position = calendar.get(Calendar.DAY_OF_WEEK);

        return (position == Calendar.SUNDAY) ? 6 : (position - 2);
    }

    public int[] getDayNumbers() {
        return dayNumbers;
    }

    public static class CalendarMonthHelper {
        private int year;
        private int month;

        public CalendarMonthHelper(int position) {
            positionToYearMonth(position);
        }

        private void positionToYearMonth(int position) {
            year = Constants.MIN_YEAR + position / 12;
            month = position % 12 + 1;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public static int getMonthName(int month) {
            switch (month) {
                case 1:
                    return R.string.january;

                case 2:
                    return R.string.february;

                case 3:
                    return R.string.march;

                case 4:
                    return R.string.april;

                case 5:
                    return R.string.may;

                case 6:
                    return R.string.june;

                case 7:
                    return R.string.july;

                case 8:
                    return R.string.august;

                case 9:
                    return R.string.september;

                case 10:
                    return R.string.october;

                case 11:
                    return R.string.november;

                case 12:
                    return R.string.december;
            }

            return R.string.january;
        }
    }
}
