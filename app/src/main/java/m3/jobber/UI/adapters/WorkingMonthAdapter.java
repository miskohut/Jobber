package m3.jobber.UI.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import m3.jobber.R;
import m3.jobber.logic.model.WorkingMonth;

/**
 * Created by misko on 21.6.2016.
 */
public class WorkingMonthAdapter extends ArrayAdapter<WorkingMonth> {

    private ArrayList<WorkingMonth> workingMonths;

    public WorkingMonthAdapter(Context context, int resource, int textViewResourceId, List<WorkingMonth> objects) {
        super(context, resource, textViewResourceId, objects);

        workingMonths = (ArrayList<WorkingMonth>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.working_month, parent, false);
        }

        WorkingMonth workingMonth = workingMonths.get(position);

//        GridView gridView = (GridView) convertView.findViewById(R.id.calendar_view);
//        gridView.setAdapter(new GridViewAdapter(getContext(), workingMonth.getCalendarMonth()));

        return convertView;
    }
}
