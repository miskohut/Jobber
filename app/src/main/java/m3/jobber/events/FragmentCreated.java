package m3.jobber.events;

import m3.jobber.UI.fragments.MonthFragment;

/**
 * Created by misko on 27.6.2016.
 */
public class FragmentCreated {

    private MonthFragment fragment;
    private int direction;

    public FragmentCreated(MonthFragment fragment, int direction) {
        this.fragment = fragment;
        this.direction = direction;
    }

    public MonthFragment getFragment() {
        return fragment;
    }

    public int getDirection() {
        return direction;
    }
}
