package m3.jobber.UI.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import m3.jobber.R;
import m3.jobber.logic.model.CalendarMonth;
import m3.jobber.logic.model.Constants;
import m3.jobber.logic.model.WorkingMonth;

/**
 * Created by misko on 30.6.2016.
 */
public class ViewPagerViewAdapter extends PagerAdapter {

    private Context context;
    private final int[] resources = {R.id._1,  R.id._2,  R.id._3,  R.id._4,  R.id._5,  R.id._6,  R.id._7,  R.id._8,  R.id._9,
                                     R.id._10, R.id._11, R.id._12, R.id._13, R.id._14, R.id._15, R.id._16, R.id._17, R.id._18,
                                     R.id._19, R.id._20, R.id._21, R.id._22, R.id._23, R.id._24, R.id._25, R.id._26,
                                     R.id._27, R.id._28, R.id._29, R.id._30, R.id._31, R.id._32, R.id._33, R.id._34,
                                     R.id._35, R.id._36, R.id._37, R.id._38, R.id._39, R.id._40, R.id._41, R.id._42 };

    public ViewPagerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(position);
        WorkingMonth workingMonth = new WorkingMonth(helper.getMonth(), helper.getYear());

        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.working_month, container, false);

        for (int i = 0; i < 42; i++) {
            TextView textView = (TextView) viewGroup.findViewById(resources[i]);
            textView.setText(workingMonth.getCalendarMonth().getDayNumbers()[i] + "");
        }

        container.addView(viewGroup);

        return viewGroup;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Constants.MAX_MONTHS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "bla";
    }
}
