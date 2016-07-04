package m3.jobber.UI.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import m3.jobber.R;
import m3.jobber.UI.activities.MainActivity;
import m3.jobber.UI.activities.WorkingDayActivity;
import m3.jobber.logic.model.WorkingMonth;

/**
 * Created by misko on 22.6.2016.
 */
public class MonthFragment extends Fragment {

    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";

    private MainActivity mainActivity;
    private int month;
    private int year;

    private final int[] resources = {R.id._1,  R.id._2,  R.id._3,  R.id._4,  R.id._5,  R.id._6,  R.id._7,  R.id._8,  R.id._9,
            R.id._10, R.id._11, R.id._12, R.id._13, R.id._14, R.id._15, R.id._16, R.id._17, R.id._18,
            R.id._19, R.id._20, R.id._21, R.id._22, R.id._23, R.id._24, R.id._25, R.id._26,
            R.id._27, R.id._28, R.id._29, R.id._30, R.id._31, R.id._32, R.id._33, R.id._34,
            R.id._35, R.id._36, R.id._37, R.id._38, R.id._39, R.id._40, R.id._41, R.id._42 };

    private final int[] worked_hours = {R.id.hours_1,  R.id.hours_2,  R.id.hours_3,  R.id.hours_4,  R.id.hours_5,  R.id.hours_6,  R.id.hours_7,  R.id.hours_8,  R.id.hours_9,
            R.id.hours_10, R.id.hours_11, R.id.hours_12, R.id.hours_13, R.id.hours_14, R.id.hours_15, R.id.hours_16, R.id.hours_17, R.id.hours_18,
            R.id.hours_19, R.id.hours_20, R.id.hours_21, R.id.hours_22, R.id.hours_23, R.id.hours_24, R.id.hours_25, R.id.hours_26,
            R.id.hours_27, R.id.hours_28, R.id.hours_29, R.id.hours_30, R.id.hours_31, R.id.hours_32, R.id.hours_33, R.id.hours_34,
            R.id.hours_35, R.id.hours_36, R.id.hours_37, R.id.hours_38, R.id.hours_39, R.id.hours_40, R.id.hours_41, R.id.hours_42 };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.working_month, container, false);

        mainActivity = (MainActivity) getActivity();

        month = getArguments().getInt(MONTH);
        year = getArguments().getInt(YEAR);

        WorkingMonth workingMonth = getWorkingMonth(month, year);

        for (int i = 0; i < 42; i++) {
            final int day = workingMonth.getCalendarMonth().getDayNumbers()[i];

            TextView textView = (TextView) view.findViewById(resources[i]);
            textView.setText(day + "");

            ((View) textView.getParent()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WorkingDayActivity.class);
                    intent.putExtra(WorkingDayActivity.DAY, day);
                    intent.putExtra(WorkingDayActivity.MONTH, month);
                    intent.putExtra(WorkingDayActivity.YEAR, year);

                    startActivity(intent);
                }
            });

            if (!(i < workingMonth.getCalendarMonth().getFirstPosition() || i > (workingMonth.getCalendarMonth().getFirstPosition() + workingMonth.getCalendarMonth().getMonthMax() - 1))) {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//
//                PublicHoliday publicHoliday = SQLite.select().from(PublicHoliday.class).where(PublicHoliday_Table.country.eq(PublicHolidaysSync.COUNTRY)).and(PublicHoliday_Table.day.eq(day))
//                        .and(PublicHoliday_Table.month.eq(month)).and(PublicHoliday_Table.year.eq(year)).querySingle();
//                if (publicHoliday != null) {
//                    textView.setTextColor(Color.rgb(48, 172, 94));
//                }
//                else {
                    textView.setTextColor(Color.BLACK);
//                }

                if (day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) && month == Calendar.getInstance().get(Calendar.MONTH) + 1 && year == Calendar.getInstance().get(Calendar.YEAR)) {
                    textView.setBackgroundResource(R.drawable.current_day);
                }

                TextView workedHours = (TextView) view.findViewById(worked_hours[i]);
                try {
                    workedHours.setText(String.format("%.2f", workingMonth.getWorkingDays().get(i).getWorkedHours()));
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return view;
    }

    public static Fragment createFragment(int month, int year) {
        Fragment fragment = new MonthFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MONTH, month);
        bundle.putInt(YEAR, year);

        fragment.setArguments(bundle);

        return fragment;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    private WorkingMonth getWorkingMonth(final int month, final int year) {
        return new WorkingMonth(month, year);
    }
}
