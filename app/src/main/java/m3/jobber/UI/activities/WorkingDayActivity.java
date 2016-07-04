package m3.jobber.UI.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import m3.jobber.R;
import m3.jobber.logic.model.WorkingDay;
import m3.jobber.logic.model.WorkingDay_Table;

public class WorkingDayActivity extends AppCompatActivity {

    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    private WorkingDay workingDay;

    private EditText workStarted;
    private EditText workEnded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_item);

        workStarted = (EditText) findViewById(R.id.work_started);
        workEnded = (EditText) findViewById(R.id.work_ended);

        int day = getIntent().getIntExtra(DAY, 0);
        int month = getIntent().getIntExtra(MONTH, 0);
        int year = getIntent().getIntExtra(YEAR, 0);

        workingDay = SQLite.select()
                    .from(WorkingDay.class)
                    .where(WorkingDay_Table.day.eq(day))
                    .and(WorkingDay_Table.month.eq(month))
                    .and(WorkingDay_Table.year.eq(year))
                    .querySingle();

        if (workingDay == null) {
            workingDay = new WorkingDay(day, month, year, 0);
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");
            workStarted.setText(getResources().getString(R.string.work_started) + ": " + simpleDateFormat.format(workingDay.getStartDate()));

            workEnded.setText(getResources().getString(R.string.work_ended) + ": " + simpleDateFormat.format(workingDay.getEndDate()));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        getSupportActionBar().setTitle(new SimpleDateFormat("dd. MMM. yyyy").format(calendar.getTime()));
    }

    public void setWorkStarted(View view) {
        setDate(workingDay.getStartDate(), 8, 0, workStarted, R.string.work_started);
    }

    public void setEndDate(View view) {
        setDate(workingDay.getEndDate(), 15, 0, workEnded, R.string.work_ended);
    }

    public void setDate(final Date date, int hour, int minute, final EditText editText, final int stringID) {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm");

                editText.setText(getResources().getString(stringID) + ": " + simpleDateFormat.format(calendar.getTime()));
            }
        }, hour, minute, true);

        dialog.show();
    }

    @Override
    protected void onPause() {
        if (workingDay != null) {
            workingDay.save();
        }
        super.onPause();
    }
}