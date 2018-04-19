package com.lan.test;

import com.lan.model.AdminInfo;
import com.lan.model.Team;
import com.lan.repository.AdminInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;

    @WebAppConfiguration

//使用注解
@RunWith(SpringJUnit4ClassRunner.class)
//指定配置文件
@ContextConfiguration(locations="classpath:spring-jpa.xml")
public class AdminInfoRepositoryTest {
    @Resource
    private AdminInfoRepository adminInfoRepository;
    @Test
    public void testDataSource() throws SQLException {
        AdminInfo adminInfo=new AdminInfo(1,"兰建","123456");
        adminInfoRepository.save(adminInfo);
    }
}
