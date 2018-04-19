package com.lan.service.impl;

import com.google.common.collect.Lists;
import com.lan.model.Project;
import com.lan.model.Team;
import com.lan.repository.ProjectRepository;
import com.lan.repository.TeamRepository;
import com.lan.service.TeamService;
import com.lan.util.MyRuntimeException;
import com.lan.vo.PageObject;
import com.lan.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Transactional(readOnly = false,rollbackFor = MyRuntimeException.class)
@Service
@Slf4j
public class TeamServiceImpl implements TeamService {
	@Resource
	private TeamRepository teamDao;
	@Resource
	private ProjectRepository projectDao;

/**
	 * 执行团信息的更新操作
	 * 
	 * @param team
	 *            中用于封装页面传入的数据
	 */

	@Override
	public void updateObject(Team team) {
		if (team == null) {
			throw new MyRuntimeException("修改内容不能为空");
		}
		teamDao.save(team);
	}

	@Override
	public Team findObjectById(Integer id) {
		// 1.判定参数的有效性
		if (id == null || id <= 0) {
            throw new MyRuntimeException("id 的值无效:id=" + id);
        }
		// 2.执行查找操作
		Team team = teamDao.findOne(id);
		// 3.根据结果进行判定
		if (team == null) {
			throw new MyRuntimeException("没找到对应结果");
		}
		// 4.返回结果
		return team;
	}

/**
	 * 查询项目id和项目名称,通过此数据 初始化页面上的select列表
     * forEach(project -> map.put(project.getProjectId().toString(),project.getProjectName()))
	 */

	@Transactional(readOnly = true)
	@Override
	public List<Map<String, Object>> findPrjIdAndNames() {
        List<Project> projects = projectDao.findAll();
        Map<String,Object> map =new HashMap();
        for (Project project:projects) {
        	map.put("id",project.getProjectId());
            map.put("name",project.getProjectName());
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map);
        return mapList;
	}

/**
	 * 获得当前页的数据以及分页信息 1)List<Map<String,Object> 2)PageObject
	 */


	@Override
	public Map<String, Object> findObjects(Integer valid, Integer projectId, Integer pageCurrent) {
		log.info("++++++++++++++++++++++++++++++++++++++++++"+projectId+"__________"+valid);
		// 1.判定参数数据的有效性
		if (valid != null && valid != 0 && valid != 1) {
            throw new MyRuntimeException("valid 的值无效");
        }
		if (projectId != null && projectId <= 0) {
            throw new MyRuntimeException("项目id无效");
        }
		if (pageCurrent == null || pageCurrent <= 0) {
            throw new MyRuntimeException("当前页码无效");
        }
		PageRequest pageRequest=buildPageRequest(pageCurrent-1, 3);
		Specification<Team> specification= (root, criteriaQuery, cb) -> {
			Predicate p1 = cb.equal(root.get("projectId").as(String.class), projectId);
			Predicate p2 = cb.equal(root.get("valid").as(String.class), valid);
			Predicate p;
			/*
			if (valid!=null){
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
			log.info("projectId:"+projectId+"+valid:"+valid);
			if (log.isDebugEnabled()){
				log.debug("搜索条件--name:{},--状态:{}",projectId,valid);
			}
			return p;
		};
		Page<Team> page=teamDao.findAll(specification,pageRequest);
		List<Team> projects = page.getContent();
		List<Team> list = new ArrayList<>();
		for (Team team :projects) {
			Project project=projectDao.findOne(team.getProjectId());
			team.setProjectName(project.getProjectName());
			list.add(team);
		}
		// 4.获得记录数,计算分页相关信息并进行封装
		// 4.1根据条件获得记录数
		int rowCount = (int)page.getTotalElements();
		// 4.2将分页信息封装到PageObject
		PageObject pageObject = new PageObject();
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(3);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setStartIndex((pageCurrent-1)*3);
		// 5.封装数据(当前页记录,分页PageObject)
		Map<String, Object> map = new HashMap();
		map.put("list", list);
		map.put("pageObject", pageObject);
		return map;

	}
	private PageRequest buildPageRequest(int page,int size) {
		List<Sort.Order> orders= new ArrayList<>();
		orders.add( new Sort.Order(Sort.Direction.DESC, "valid"));
		return new PageRequest(page,size, new Sort(orders));
	}
/** 定义什么异常会回滚事务 */

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void validById(String idStr, Integer valid) {
		if(idStr==null||idStr.trim().length()==0){
			throw new MyRuntimeException(1111,"至少选择一项");
		}
		if(valid!=0&&valid!=1){
			throw new MyRuntimeException(2222,"valid值必须是0或者1");
		}
		String[] ids=idStr.split(",");
		log.info("Team id数组"+ids.toString());
		List<String> idsList= Arrays.asList(ids);
		log.info("Team id集合"+idsList.toString());
		idsList.stream().forEach(id->teamDao.updateValidById(valid,Integer.parseInt(id)));
	}

/** 执行数据写入操作 */
	@Override
	public void saveObject(Team entity) {
		// 1.对参数进行业务验证
		if (entity == null) {
			throw new MyRuntimeException("保存的数据不能为空");
		}
		// 2.执行保存操作
		teamDao.save(entity);
	}

}
