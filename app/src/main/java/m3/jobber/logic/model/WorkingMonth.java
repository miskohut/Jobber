package m3.jobber.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;

/**
 * Created by misko on 21.6.2016.
 */
public class WorkingMonth {

    private int month;
    private int year;

    private CalendarMonth calendarMonth;
    private ArrayList<WorkingDay> workingDays;

    public WorkingMonth(int month, int year) {
        this.month = month;
        this.year = year;

        workingDays = (ArrayList<WorkingDay>) SQLite.select()
                                                .from(WorkingDay.class)
                                                .where(WorkingDay_Table.month.eq(month))
                                                .and(WorkingDay_Table.year.eq(year))
                                                .orderBy(WorkingDay_Table.day, true)
                                                .queryList();

        calendarMonth = new CalendarMonth(month, year);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public CalendarMonth getCalendarMonth() {
        return calendarMonth;
    }

    public ArrayList<WorkingDay> getWorkingDays() {
        return workingDays;
    }
}
