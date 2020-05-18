package io.choerodon.iam.infra.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.iam.app.service.WorkCalendarService;
import io.choerodon.iam.infra.config.WorkCalendarHolidayProperties;
import io.choerodon.iam.infra.factory.WorkCalendarFactory;


/**
 * @author dinghuang123@gmail.com
 * @since 2018/10/8
 */
@Component
@Transactional(rollbackFor = Exception.class)
@ConditionalOnProperty(prefix = "workh", name = "enabled", havingValue = "true")
public class WorkCalendarHolidayRefJobs {

    private WorkCalendarHolidayProperties workCalendarHolidayProperties;

    @Autowired
    private WorkCalendarFactory workCalendarFactory;

    public WorkCalendarHolidayRefJobs(WorkCalendarHolidayProperties workCalendarHolidayProperties) {
        this.workCalendarHolidayProperties = workCalendarHolidayProperties;
    }

    @Scheduled(cron = "${workh.cron}")
    public void updateWorkCalendarHolidayRef() {
        WorkCalendarService workCalendarService = workCalendarFactory.getWorkCalendarHoliday(workCalendarHolidayProperties.getType());
        if (workCalendarService != null) {
            workCalendarService.updateWorkCalendarHolidayRef();
        }
    }
}
