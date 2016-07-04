package m3.jobber.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Date;

import m3.jobber.logic.publicholidays.PublicHolidaysSync;

public class LoadHolidaysService extends IntentService {

    private static final String PREFERENCES_NAME = "jobber_prefferences";
    private static final String TIMESTAMP = "timestamp";
    private static final int MONTH = 2592000;

    public LoadHolidaysService() {
        super("load_holidays");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, 0);
        long previousTime = sharedPreferences.getLong(TIMESTAMP, 0);

        Date date = new Date();

        if (previousTime != 0 ) {
            if (Math.abs(previousTime - date.getTime()) > MONTH) {
                new PublicHolidaysSync(getResources().getConfiguration().locale.getISO3Country().toLowerCase());
            }
        }
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(TIMESTAMP, date.getTime());
            editor.commit();
        }
    }
}