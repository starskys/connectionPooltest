package com.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author starSky
 * @date 11/20/2017 6:20 PM.
 */
//@Component
public class C3P0Bean {
    @Autowired
    public ComboPooledDataSource dataSource;

}
