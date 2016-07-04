package m3.jobber.logic.publicholidays;

import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import m3.jobber.logic.database.AppDatabase;

@ModelContainer
@Table(database = AppDatabase.class)
public class PublicHoliday extends BaseModel {

    @PrimaryKey
    private int day;

    @PrimaryKey
    private int month;

    @PrimaryKey
    private int year;

    @PrimaryKey
    private String country;

    public PublicHoliday() {
    }

    public PublicHoliday(int day, int month, int year, String country) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.country = country;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
