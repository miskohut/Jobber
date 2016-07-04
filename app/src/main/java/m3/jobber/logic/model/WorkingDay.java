package m3.jobber.logic.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import m3.jobber.logic.database.AppDatabase;

/**
 * Created by misko on 19.6.2016.
 */

@ModelContainer
@Table(database = AppDatabase.class, name = "workingDays")
public class WorkingDay extends BaseModel {

    @PrimaryKey
    private int day;

    @PrimaryKey
    private int month;

    @PrimaryKey
    private int year;

    @Column
    private float workedHours;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    public WorkingDay(int day, int month, int year, float workedHours) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.workedHours = workedHours;

        startDate = new Date();
        endDate = new Date();
    }

    public WorkingDay() {

    }

    public float getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(float workedHours) {
        this.workedHours = workedHours;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
