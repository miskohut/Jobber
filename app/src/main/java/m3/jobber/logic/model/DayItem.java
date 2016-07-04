package m3.jobber.logic.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.UUID;

import m3.jobber.logic.database.AppDatabase;

/**
 * Created by misko on 20.6.2016.
 */

/*@ModelContainer
@Table(database = AppDatabase.class)*/
public class DayItem extends BaseModel {

//    @PrimaryKey(autoincrement = true)
    private int ID;

//    @Column
    private String DayID;

//    @Column
    private Date startTime;

//    @Column
    private Date endTime;

//    @Column
    private float workedHours;

//    @Column
    private String note;

    public DayItem(int ID, Date startTime, Date endTime, float workedHours, String note) {
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workedHours = workedHours;
        this.note = note;
    }

    public DayItem() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(float workedHours) {
        this.workedHours = workedHours;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDayID() {
        return DayID;
    }

    public void setDayID(String dayID) {
        DayID = dayID;
    }
}
