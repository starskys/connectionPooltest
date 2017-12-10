package com.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author starSky
 * @date 12/5/2017 7:37 PM.
 */
@Component
public class MonitorComponent {
    @Autowired
    private MonitorDao monitorDao;

    @Scheduled(cron="0/1 * * * * ? ")
    public void monitorJob(){
        try {
            monitorDao.monitor(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron="0/2 * * * * ? ")
    @Transactional
    public void monitorTxJob(){
        try {
            monitorDao.monitor(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

