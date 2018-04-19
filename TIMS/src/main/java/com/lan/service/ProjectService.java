package com.lan.service;


import com.lan.model.Project;
import com.lan.vo.PageAndProjectsVo;
import com.lan.vo.ProjectVO;
import com.lan.vo.QueryParams;

import java.util.List;


/**
 *
 * @author
 */
public interface ProjectService {
    /**
     * 根据queryParams查找出符合的project集合
     * @param  queryParams 封装的查询参数
     * @return  分页信息已及查询结果
     */
    PageAndProjectsVo findProjectByPage(QueryParams queryParams);
    /**
     * 用于保存project
     * @param  projectVO 传入的对象
     */
    void saveObject(ProjectVO projectVO);
    /**
     * 用于保存project
     * @param  projectId 项目id
     * @return  ProjectVO 旅游项目VO
     */
    ProjectVO findProjectById(Integer projectId);
    /**
     * 用于通过id删除project
     * @param  projectId 项目id
     */
    void deleteProjectById(Integer projectId);
    /**
     * 用于通过id删除project
     * @param  idStr 字符串的项目id
     * @param  valid 禁用或启用
     */
    void validByIds(String idStr,Integer valid);
    /**
     * 用于保存旅游项目图片地址
     * @param  projectVO 里只有图片地址和项目id
     * @return  ProjectVO 旅游项目VO
     */
    void  saveUploadAddress(ProjectVO projectVO);
    List<ProjectVO> findProjectNine();
}
