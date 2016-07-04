package m3.jobber.events;

import m3.jobber.logic.model.WorkingMonth;

/**
 * Created by misko on 22.6.2016.
 */
public class WorkingMonthAdded {

    private WorkingMonth workingMonth;

    public WorkingMonthAdded(WorkingMonth workingMonth) {
        this.workingMonth = workingMonth;
    }

    public WorkingMonth getWorkingMonth() {
        return workingMonth;
    }
}
