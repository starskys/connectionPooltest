package com.druid;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author starSky
 * @date 12/5/2017 7:37 PM.
 */
@Repository
public interface MonitorDao {

    @Select("SELECT * FROM TEST")
    void monitor(@Param("id")Integer id);
}
