package m3.jobber.logic.publicholidays;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import m3.jobber.logic.model.Constants;

/**
 * Created by misko on 3.7.2016.
 */
public class PublicHolidaysSync {

    public static final String ACTION = "action";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static String COUNTRY = "country";

    public static final String ACTION_NAME = "getPublicHolidaysForDateRange";
    public static final String URL = "http://kayaposoft.com/enrico/json/v1.0/?";

    private AsyncHttpClient client = new AsyncHttpClient();
    private RequestParams requestParams = new RequestParams();

    public PublicHolidaysSync(String country) {
        SQLite.delete().from(PublicHoliday.class);

        COUNTRY = country;

        requestParams.add(ACTION, ACTION_NAME);
        requestParams.add(FROM_DATE, "01-01-" + Constants.MIN_YEAR);
        requestParams.add(TO_DATE, "31-12-" + Constants.MAX_YEAR);
        requestParams.add(COUNTRY, "svk");

        client.get(URL, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                responseString = responseString.replaceAll("\\\\", "");

                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(responseString);
                } catch (JSONException e) {
                    return;
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = null;

                    try {
                        object = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        continue;
                    }

                    JSONObject dateObject = null;

                    try {
                        dateObject = object.getJSONObject("date");
                    } catch (JSONException e) {
                        continue;
                    }

                    int day;
                    int month;
                    int year;

                    try {
                        day = dateObject.getInt("day");
                        month = dateObject.getInt("month");
                        year = dateObject.getInt("year");
                    } catch (JSONException e) {
                        continue;
                    }

                    PublicHoliday publicHoliday = new PublicHoliday(day, month, year, COUNTRY);
                    publicHoliday.save();
                }
            }
        });
    }
}
