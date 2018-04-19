package com.lan.service.impl;


import com.google.common.collect.Lists;
import com.lan.model.Project;
import com.lan.repository.ProjectRepository;
import com.lan.service.ProjectService;
import com.lan.util.MyRuntimeException;
import com.lan.vo.PageAndProjectsVo;
import com.lan.vo.PageObjectVO;
import com.lan.vo.ProjectVO;
import com.lan.vo.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现ProjectService
 * @author  lanjian
 * */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectRepository projectRepository;
    @Override
    public PageAndProjectsVo findProjectByPage(QueryParams queryParams) {
        if (queryParams==null){
            throw new MyRuntimeException(1003,"查询参数为空");
        }
        PageRequest pageRequest=buildPageRequest(queryParams.getPageCurrent()-1, queryParams.getPageSize());
        Specification<Project> specification= (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("projectName").as(String.class), "%" + queryParams.getName() + "%");
                Predicate p2 = cb.equal(root.get("valid").as(String.class), queryParams.getValid());
                Predicate p;
                if (queryParams.getValid()!=null){
                    if (StringUtils.isNotBlank(queryParams.getName())){
                        p = cb.and(p1,p2);
                    }else{
                        p = p2;
                    }
                }else{
                    if (StringUtils.isNotBlank(queryParams.getName())){
                        p = p1;
                    }else{
                        p = cb.or(p1,p2);
                    }
                }
                log.info("name:"+queryParams.getName()+"+valid:"+queryParams.getValid());
                if (log.isDebugEnabled()){
                    log.debug("搜索条件--name:{},--状态:{}",queryParams.getName(),queryParams.getValid());
                }
                return p;
            };
        Page<Project> page=projectRepository.findAll(specification,pageRequest);
        List<Project> projects = page.getContent();
        List<ProjectVO> projectVOs= Lists.newArrayList();
        if (projects==null||projects.isEmpty()){
           return buildProjectRespons(projectVOs,page);
        }
         projectVOs= projects.stream().map(pro->{
             ProjectVO proVo = new ProjectVO();
             BeanUtils.copyProperties(pro,proVo);
             return proVo;
         }).collect(Collectors.toList());
        return buildProjectRespons(projectVOs,page);
    }
    /**
     * 建立分页排序请求
     * @param page
     * @param size
     * @return
     */
    private PageRequest buildPageRequest(int page,int size) {
        List<Sort.Order> orders= new ArrayList<>();
        orders.add( new Sort.Order(Sort.Direction.DESC, "valid"));
        orders.add( new Sort.Order(Sort.Direction.DESC, "beginDate"));
        orders.add( new Sort.Order(Sort.Direction.ASC, "endDate"));
        return new PageRequest(page,size, new Sort(orders));
    }
    /**
     *将查询出来的projectVOS和page封装到PageAndProjectsVo
     * @param projectVOS 项目集合
     * @param page 分页信息
     * @return
     */
    private PageAndProjectsVo buildProjectRespons(List<ProjectVO> projectVOS,Page<Project> page){
        PageAndProjectsVo pageAndProjectsVo =new PageAndProjectsVo();
        PageObjectVO pageObjectVO=new PageObjectVO();
        pageObjectVO.setRowCount(page.getTotalElements());
        pageObjectVO.setPageCount(page.getTotalPages());
        pageObjectVO.setPageCurrent(page.getNumber()+1);
        pageAndProjectsVo.setList(projectVOS);
        pageAndProjectsVo.setPageObject(pageObjectVO);
        return pageAndProjectsVo;
    }
    @Override
    public void saveObject(ProjectVO projectVO) {
        if (projectVO==null){
            throw new MyRuntimeException(1001,"传入project为空");
        }
        Project project =new Project();
        BeanUtils.copyProperties(projectVO,project);
        projectRepository.save(project);
    }

    @Override
    public ProjectVO findProjectById(Integer projectId) {
        if (projectId==null){
            throw new MyRuntimeException(1002,"找不到id");
        }
        Project project = projectRepository.findOne(projectId);
        ProjectVO projectVO=new ProjectVO();
        BeanUtils.copyProperties(project,projectVO);
        return projectVO;
    }

    @Override
    public void deleteProjectById(Integer projectId) {
        if (projectId==null){
            throw new MyRuntimeException(1002,"找不到id");
        }
        projectRepository.delete(projectId);
    }

    @Override
    public void validByIds(String idStr, Integer valid) {
        if(idStr==null||idStr.trim().length()==0){
            throw new MyRuntimeException(1111,"至少选择一项");
        }
        if(valid!=0&&valid!=1){
            throw new MyRuntimeException(2222,"valid值必须是0或者1");
        }
        String[] ids=idStr.split(",");
        log.info("id数组"+ids.toString());
        List<String> idsList= Arrays.asList(ids);
        log.info("id集合"+idsList.toString());
        idsList.stream().forEach(id->projectRepository.updateValidById(valid,Integer.parseInt(id)));
    }

    @Override
    public void saveUploadAddress(ProjectVO projectVO) {
        Project project = projectRepository.findOne(projectVO.getProjectId());
        project.setUploadAddress(projectVO.getUploadAddress());
        projectRepository.save(project);
    }

    @Override
    public List<ProjectVO> findProjectNine() {
        QueryParams queryParams=new QueryParams("",1,9,1);
        PageAndProjectsVo all=findProjectByPage(queryParams);
        all.getList();
        return all.getList();
    }
}
