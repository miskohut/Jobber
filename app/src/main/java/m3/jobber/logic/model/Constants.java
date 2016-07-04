package m3.jobber.logic.model;

import java.util.Calendar;

/**
 * Created by misko on 28.6.2016.
 */
public class Constants {

    public static final int MAX_MONTHS = 1200;
    public static final int CURRENT_POSITION;
    public static final int MAX_YEAR;
    public static final int MIN_YEAR;

    static {
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);

        MAX_YEAR = currentYear + 50;
        MIN_YEAR = MAX_YEAR - 100;

        CURRENT_POSITION = (MAX_MONTHS - ((MAX_YEAR - (currentYear)) * 12)) + (calendar.get(Calendar.MONTH));
    }
}
