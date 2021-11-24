package io.choerodon.iam.app.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.choerodon.asgard.schedule.QuartzDefinition;
import io.choerodon.asgard.schedule.annotation.JobTask;
import io.choerodon.asgard.schedule.annotation.TimedTask;
import io.choerodon.iam.app.service.FixService;
import io.choerodon.iam.app.service.TenantC7nService;

/**
 * @Author: scp
 * @Description:
 * @Date: Created in 2021/2/4
 * @Modified By:
 */
@Component
public class NameToPinyinTask {
    private static final Logger logger = LoggerFactory.getLogger(NameToPinyinTask.class);

    @Autowired
    private FixService fixService;
    @Autowired
    private TenantC7nService tenantC7nService;

    @JobTask(maxRetryCount = 3, code = "nameToPinyinHeadChar", description = "同步拼音字段/拼音首字母")
    @TimedTask(name = "nameToPinyinHeadChar", description = "同步拼音字段/拼音首字母", oneExecution = true,
            repeatCount = 0, repeatInterval = 1, repeatIntervalUnit = QuartzDefinition.SimpleRepeatIntervalUnit.HOURS, params = {})
    public void syncRealNameToPinyin(Map<String, Object> map) {
        logger.info("===========begin to sync name to pinyin!!=============");
        fixService.fixRealNameToPinyin();
        logger.info("===========end to sync name to pinyin!!===============");
        logger.info("===========begin to sync name to pinyin head char!!=============");
        fixService.fixRealNameToPinyinHeaderChar();
        logger.info("===========end to sync name to pinyin head char!!===============");
    }

    @JobTask(maxRetryCount = 3, code = "syncTenantTl", description = "同步组织多语言")
    @TimedTask(name = "syncTenantTl", description = "同步组织多语言", oneExecution = true,
            repeatCount = 0, repeatInterval = 1, repeatIntervalUnit = QuartzDefinition.SimpleRepeatIntervalUnit.HOURS, params = {})
    public void syncTenantTl(Map<String, Object> map) {
        tenantC7nService.syncTenantTl();
    }

}
