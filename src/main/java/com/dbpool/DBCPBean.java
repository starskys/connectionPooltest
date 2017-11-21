package com.dbpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author starSky
 * @date 11/20/2017 6:20 PM.
 */
@Component
public class DBCPBean {
    @Autowired
    public   DataSource dataSource;

}
