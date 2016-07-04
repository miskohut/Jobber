package m3.jobber.UI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import m3.jobber.R;
import m3.jobber.UI.adapters.ViewPagerFragmentAdapter;
import m3.jobber.UI.adapters.ViewPagerViewAdapter;
import m3.jobber.UI.fragments.FragmentsBuffer;
import m3.jobber.UI.fragments.MonthFragment;
import m3.jobber.asynctasks.CreateFragmentTask;
import m3.jobber.events.FragmentCreated;
import m3.jobber.events.WorkingMonthAdded;
import m3.jobber.logic.model.CalendarMonth;
import m3.jobber.logic.model.Constants;
import m3.jobber.logic.publicholidays.PublicHoliday;
import m3.jobber.logic.publicholidays.PublicHolidaysSync;
import m3.jobber.services.LoadHolidaysService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private int currMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int currYear = Calendar.getInstance().get(Calendar.YEAR);
    private int previousPosition = Constants.CURRENT_POSITION;

    private FragmentsBuffer fragmentsBuffer = new FragmentsBuffer();

    //view
    private ViewPager viewPager;
    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FlowManager.init(new FlowConfig.Builder(this).build());

        setDayNames();

        checkHolidays();

        viewPager = (ViewPager) findViewById(R.id.calendar);
        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), this, fragmentsBuffer);

        viewPager.setAdapter(viewPagerFragmentAdapter);
        //viewPager.setAdapter(new ViewPagerViewAdapter(this));
        viewPager.setCurrentItem(Constants.CURRENT_POSITION);
        viewPager.setOffscreenPageLimit(3);

        CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(Constants.CURRENT_POSITION);
        getSupportActionBar().setTitle(getResources().getString(CalendarMonth.CalendarMonthHelper.getMonthName(helper.getMonth())) + " " + helper.getYear());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position != previousPosition) {
                    CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(position);

                    int direction;

                    if (previousPosition > position) {
                        direction = FragmentsBuffer.LEFT;
                    } else {
                        direction = FragmentsBuffer.RIGHT;
                    }

                    new CreateFragmentTask(new CreateFragmentTask.MonthFragmentCreated() {
                        @Override
                        public void onMonthFragmentCreated(MonthFragment monthFragment, int direction) {
                            onWorkingMonthAdded(monthFragment, direction);
                        }
                    }).execute(helper.getMonth(), helper.getYear(), direction);

                    previousPosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {
                CalendarMonth.CalendarMonthHelper helper = new CalendarMonth.CalendarMonthHelper(position);
                getSupportActionBar().setTitle(getResources().getString(CalendarMonth.CalendarMonthHelper.getMonthName(helper.getMonth())) + " " + helper.getYear());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkHolidays() {
        startService(new Intent(this, LoadHolidaysService.class));
    }

    private void setDayNames() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE");

        Calendar calendar = Calendar.getInstance();

        calendar.set(2013, 6, 1);
        ((TextView) findViewById(R.id.monday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 2);
        ((TextView) findViewById(R.id.tuesday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 3);
        ((TextView) findViewById(R.id.wednesday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 4);
        ((TextView) findViewById(R.id.thursday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 5);
        ((TextView) findViewById(R.id.friday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 6);
        ((TextView) findViewById(R.id.saturday_header)).setText(simpleDateFormat.format(calendar.getTime()));

        calendar.set(2013, 6, 7);
        ((TextView) findViewById(R.id.sunday_header)).setText(simpleDateFormat.format(calendar.getTime()));


    }

    public void onWorkingMonthAdded(MonthFragment monthFragment, int direction) {
        if (direction == FragmentsBuffer.RIGHT) {
            fragmentsBuffer.addFragmentRight(monthFragment);
        }
        else {
            fragmentsBuffer.addFragmentLeft(monthFragment);
        }
    }

    @Subscribe
    public void onFragmentCreated(final FragmentCreated fragmentCreated) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             /*   viewPager.setAdapter(null);

                if (fragmentCreated.getDirection() == ViewPagerFragmentAdapter.LEFT) {
                    viewPagerFragmentAdapter.addFragment(fragmentCreated.getFragment(), 0);
                    viewPagerFragmentAdapter.removeFragment(viewPagerFragmentAdapter.getFragments().size() - 1);
                }
                else {
                    viewPagerFragmentAdapter.addFragment(fragmentCreated.getFragment());
                    viewPagerFragmentAdapter.removeFragment(0);
                }

                ArrayList<Fragment> fragments = viewPagerFragmentAdapter.getFragments();
                viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

                for (Fragment fragment : fragments) {
                    viewPagerFragmentAdapter.addFragment(fragment);
                }

                viewPager.setAdapter(viewPagerFragmentAdapter);*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public int getCurrentYear() {
        return currYear;
    }

    public int getCurrentMonth() {
        return currMonth;
    }
}
