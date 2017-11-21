package com.dbpool;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author starSky
 * @date 11/20/2017 1:35 PM.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ProPertyTest {
    @Autowired
    private BasicDataSource  dataSource;
    @Before
    public void beforeMethod(){
        System.out.println("初始配置");
        System.out.println("maxTotal:"+dataSource.getMaxTotal());
        System.out.println("maxIdle:"+dataSource.getMaxIdle());
        System.out.println("minIdle:"+dataSource.getMinIdle());
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
        System.out.println("-------------------------------------------------------------------");
    }

    @Test
    public void propertyTest(){
        BasicDataSource dataSource = (BasicDataSource)DBCPUtil.getDataSource();
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
        dataSource.getMinIdle();
    }


    /**
     * minIdle测试 ：
     *可以在池中保持空闲的最小连接数，低于设置值时，空闲连接将被创建，以努力保持最小空闲连接数>=minIdle，如设置为0，则不创建
     *这里设置的数值生效的前提是：timeBetweenEvictionRunsMillis（空闲连接回收器运行时间频率）被设置为正数。
     * initialSize=2
     * minIdle=1
     * maxTotal=5
     * timeBetweenEvictionRunsMillis=2000
     * @throws InterruptedException
     * @throws SQLException
     * @throws ExecutionException
     */
    @Test
    public void propertyTestify() throws InterruptedException, SQLException, ExecutionException {
        for(int i=0;i<4;i++){
            Connection connection = DBCPUtil.getConnection();
            work(connection,i,10L);
        }
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
        TimeUnit.SECONDS.sleep(5);
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
    }

    /**
     * 空闲连接回收测试：
     * initialSize=2
     * minIdle=1
     * maxTotal=5
     * timeBetweenEvictionRunsMillis=2000
     * minEvictableIdleTimeMillis：2000
     */
    @Test
    public void idleRecycleTest() throws InterruptedException, SQLException {
        for(int i=0;i<4;i++){
            Connection connection = dataSource.getConnection();
            work(connection,i,5L);
        }
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
        TimeUnit.SECONDS.sleep(10);
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
    }


    /**
     *
     */
    @Test
    public void depratchedTest() throws SQLException, InterruptedException {
        for(int i=0;i<4;i++){
            //打开连接不关闭
           dataSource.getConnection();
        }
        //10s后关闭连接，在此之前先查看一下当前池状态
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
        TimeUnit.SECONDS.sleep(15);
        //15s后再来查看池状态
        System.out.println("activeNumber:"+dataSource.getNumActive());
        System.out.println("idleNumber:"+dataSource.getNumIdle());
    }





    private void work(Connection connection,int i,long laborHour) {
        Thread thread=new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(laborHour);
                System.out.println("完成:"+i);
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        });
        thread.start();

    }
}