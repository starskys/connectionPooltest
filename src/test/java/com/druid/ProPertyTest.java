package com.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author starSky
 * @date 11/20/2017 1:35 PM.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class ProPertyTest {
    @Autowired
    private DruidDataSource dataSource;
    @Before
    public void beforeMethod() throws SQLException {
        System.out.println("初始配置");
        System.out.println("最大连接数:"+dataSource.getMaxActive());
        System.out.println("最小连接数:"+dataSource.getMinIdle());
        System.out.println("初始化时获取的连接数:"+dataSource.getInitialSize());
        System.out.println("-------------------------------------------------------------------");
    }




    /**
     * 空闲连接的最大空闲时间,?秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
            * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void minEvictableIdleTimeMillisTest() throws SQLException, InterruptedException {
        System.out.println("当前时间:"+DateFormatUtils.format(new Date(),"HH:mm:ss"));
        for(int i=0;i<10;i++){
            Connection connection = dataSource.getConnection();
            connection.close();
        }
        System.out.println("当前活动连接数量:"+dataSource.getActiveCount());
        System.out.println("当前空闲连接数量:"+dataSource.getPoolingCount());
//        //基本测试？秒后空闲连接是否会被删除
        TimeUnit.SECONDS.sleep(10);
        //15s后再来查看池状态
        System.out.println("当前活动连接数量:"+dataSource.getActiveCount());
        System.out.println("当前空闲连接数量:"+dataSource.getPoolingCount());
    }

    @Test
    public void removeAbandonedTest() throws SQLException, InterruptedException {
        try {
            for(int i=0;i<10;i++){
                Connection connection = dataSource.getConnection();
            }
            System.out.println("当前活动连接数量:"+dataSource.getActiveCount());
            System.out.println("当前空闲连接数量:"+dataSource.getPoolingCount());
//        //基本测试？秒后空闲连接是否会被删除
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //15s后再来查看池状态
            Connection connection = dataSource.getConnection();
            System.out.println("当前活动连接数量:"+dataSource.getActiveCount());
            System.out.println("当前空闲连接数量:"+dataSource.getPoolingCount());
            System.out.println("done");
        } catch (Exception e) {
        }
    }


    /**
     * 测试maxIdleTime的局限性：
     *   1.设置maxIdleTime小于mysql  wait_timeout
     *   2.关闭 idleConnectionTestPeriod
     *   3.在maxIdleTime内关闭mysql 服务
     *    4.取出连接再次使用出现错误
     * 空闲连接的最大空闲时间,?秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
     *
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void maxIdleTimeTestB() throws SQLException, InterruptedException {
//        //初始化
//        Connection connection = dataSource.getConnection();
//        connection.close();
//        //等待关闭连接
//        TimeUnit.MILLISECONDS.sleep(500);
//        System.out.println("当前时间:"+DateFormatUtils.format(new Date(),"HH:mm:ss"));
//        System.out.println("当前连接数量:"+dataSource.getNumConnections());
//        System.out.println("当前活动连接数量:"+dataSource.getNumBusyConnections());
//        System.out.println("当前空闲连接数量:"+dataSource.getNumIdleConnections());
//
//        //未超过MaxIdleTime，此时关闭mysql
//        TimeUnit.SECONDS.sleep(40);
//        // 查看池状态
//        System.out.println("当前时间:"+DateFormatUtils.format(new Date(),"HH:mm:ss"));
//        System.out.println("当前连接数量:"+dataSource.getNumConnections());
//        System.out.println("当前活动连接数量:"+dataSource.getNumBusyConnections());
//        System.out.println("当前空闲连接数量:"+dataSource.getNumIdleConnections());
//        //关闭mysql后取出一个空闲连接测试
//        connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        statement.execute("SELECT 1 FROM DUAL ");
//        statement.close();
//        connection.close();
    }


    /**
     * 每?秒检查所有连接池中的空闲连接的有效性。Default: 0
     * 在数据库正常的情况下idleConnectionTestPeriod 与maxIdleTime个人
     * 感觉只设置一个就可以了但是若数据库被重启，在某个未超过maxIdleTime时
     * 连接池认为该连接仍然是有效的，但其实已经无效了，如果某个应用用到了这个连接
     * 则会出现异常，这时就应该通过idleConnectionTestPeriod 的设置定时检测
     * 这些空闲连接的有效性以避免取到无效连接
     */
    @Test
    public void idleConnectionTestPeriodTest() throws SQLException, InterruptedException {
//        Connection connection = dataSource.getConnection();
//        connection.close();
//        //测试发现，连接关闭的速度有点慢，调用关闭命令后立即查看活动连接发现该连接还没有关闭
//        //所以这里暂停一下
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println("当前连接数量:"+dataSource.getNumConnections());
//        System.out.println("当前活动连接数量:"+dataSource.getNumBusyConnections());
//        System.out.println("当前空闲连接数量:"+dataSource.getNumIdleConnections());
//        TimeUnit.SECONDS.sleep(20);
//        connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        statement.execute("SELECT  '*' FROM DUAL");
//        statement.close();
//        connection.close();
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println("当前连接数量:"+dataSource.getNumConnections());
//        System.out.println("当前活动连接数量:"+dataSource.getNumBusyConnections());
//        System.out.println("当前空闲连接数量:"+dataSource.getNumIdleConnections());

    }


    /**
     * acquireRetryAttempts、acquireRetryDelay、breakAfterAcquireFailure:
     * 连接失败再次尝试连接测试
     *  关闭Mysql 服务并在配置的重置时间内再次启动
     */
    @Test
    public void acquireRetryAttemptsTest() throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果breakAfterAcquireFailure为true当上面无法获取到连接时
        //dataSource被关闭，提示 Attempted to use a closed or broken resource pool
        Connection connection = dataSource.getConnection();
    }


    /**
     * 测试Mysql 8小时问题
     * 需要关闭dbcp的维护功能(即将timeBetweenEvictionRunsMillis设置为一个负数或者做生意一个大于Mysql timeout
     * 的时间)
     * 查看mysql 的timeout:show global variables like '%timeout%'
     * 设置mysql 的timeout:set global wait_timeout=20 (单位是秒默认8h)
     *
     * 解决方案：
     *
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    public void mysqlTimeOutTest() throws SQLException, InterruptedException {
        //设置mysql timeout时间为20秒
//        Connection connection = dataSource.getConnection();
//        System.out.println("activeNumber:"+dataSource.getNumActive());
//        System.out.println("idleNumber:"+dataSource.getNumIdle());
//        connection.close();
//        TimeUnit.SECONDS.sleep(30);
//        //查看池状态
//        System.out.println("activeNumber:"+dataSource.getNumActive());
//        System.out.println("idleNumber:"+dataSource.getNumIdle());
//         connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        //随便取一个连接来测试
//        statement.execute("SELECT '*' FROM DUAL");
//        statement.close();
//        connection.close();
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
