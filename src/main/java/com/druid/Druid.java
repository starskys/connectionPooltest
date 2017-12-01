package com.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author starSky
 * @date 11/20/2017 6:20 PM.
 */
//@Component
public class Druid {
    @Autowired
    public DruidDataSource dataSource;

}
