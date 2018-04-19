package com.lan.test;

import com.lan.model.Project;
import com.lan.model.Team;
import com.lan.repository.ProjectRepository;
import com.lan.repository.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebAppConfiguration

//使用注解
@RunWith(SpringJUnit4ClassRunner.class)
//指定配置文件
@ContextConfiguration(locations="classpath:spring-jpa.xml")

public class TeamRepositoryTest {
    @Resource
    private TeamRepository teamRepository;
    @Test
    public void testDataSource() throws SQLException {

            Team team=new Team(null,"环球六日游",49,null,1,"aaaaaaa",new Date(),new Date());
            teamRepository.save(team);

    }
    private PageRequest buildPageRequest(int page,int size) {
        List<Sort.Order> orders= new ArrayList<>();
        orders.add( new Sort.Order(Sort.Direction.DESC, "valid"));
        return new PageRequest(page,size, new Sort(orders));
    }
    @Test
    public void fenye() throws SQLException {
        Integer valid = null;
        Integer projectId = null;
        Integer pageCurrent = 0;
        PageRequest pageRequest=buildPageRequest(pageCurrent, 3);
        Specification<Team> specification= (root, criteriaQuery, cb) -> {
           /* Predicate p1 = cb.equal(root.get("projectId").as(String.class), projectId);*/
            Predicate p2 = cb.equal(root.get("valid").as(String.class), valid);
            Predicate p;
           /* if (valid!=null){
                if (projectId!=null){
                    p = cb.and(p1,p2);
                }else{
                    p = p2;
                }
            }else{
                if (projectId!=null){
                    p = p1;
                }else{
                    p = cb.or(p1,p2);
                }
            }*/
           p=null;
           System.out.println("projectId:"+projectId+"+valid:"+valid);
            return p;
        };
        Page<Team> page=teamRepository.findAll(specification,pageRequest);
        List<Team> projects = page.getContent();
        System.out.println(projects);
    }

}
