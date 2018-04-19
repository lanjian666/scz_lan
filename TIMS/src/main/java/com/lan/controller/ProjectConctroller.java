package com.lan.controller;

import com.lan.model.Project;
import com.lan.service.ProjectService;
import com.lan.util.JsonResult;
import com.lan.util.OSSClientUtil;
import com.lan.vo.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping("/project/")
/**
 * 用于处理项目管理的请求
 *
 * @author lanjian
 * @date 2018/3/6
 */
@Slf4j
public class ProjectConctroller {
    @Resource
    private ProjectService projectService;
    @GetMapping("listUI")
    @ApiOperation(value = "处理发送的listUI请求")
    public String listUI(){
        //位置  WEB-INF/pages/product/project_list.jsp
        return "product/project_list";
    }
    @GetMapping("editUI")
    @ApiOperation(value = "处理发送的editUI请求")
    public String editUI(){
        //位置  EB-INF/pages/product/project_edit.jsp
        return "product/project_edit";
    }
    @RequestMapping("uploadShow")
    @ApiOperation(value = "处理发送的editUI请求")
    public ModelAndView uploadShow(Integer id){
        log.info("+++++++++++++"+id.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product/project_upload");
        modelAndView.addObject("id", id);
        return modelAndView;
    }
    @PostMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(@RequestBody ProjectVO projectVO){
      log.info("保存数据"+projectVO.toString());
        projectService.saveObject(projectVO);
        return new JsonResult();
    }
    @ApiOperation(value = "处理发送的doFindObjects请求",response = PageObjectVO.class)
    @PostMapping("doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(@RequestBody QueryParams queryParams){
        log.debug("高级查询请求参数：[{}]",queryParams.getName()+"ALL"+queryParams);
        PageAndProjectsVo pageAndProjectsVo=projectService.findProjectByPage(queryParams);
        return new JsonResult(pageAndProjectsVo);
    }
    @ApiOperation(value = "通过id查找一个项目",response = ProjectVO.class)
    @PostMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(@RequestParam(value = "id") Integer id){
        log.info("传入的id"+id);
        ProjectVO projectVO = projectService.findProjectById(id);
        log.info("修改时传到前段的数据"+projectVO.toString());
        JsonResult result = new JsonResult(projectVO);
        log.info("修改时传到前段的数据"+result);
        return result;
    }
    @ApiOperation(value = "去更新旅游项目实体")
    @PostMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(@RequestBody ProjectVO projectVO){
        ProjectVO projectById = projectService.findProjectById(projectVO.getProjectId());
        projectVO.setUploadAddress(projectById.getUploadAddress());
        log.info("需要修改的数据："+projectVO.toString());
        projectService.saveObject(projectVO);
        return new JsonResult();
    }
    @ApiOperation(value = "去更新旅游项目实体")
    @PostMapping("doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(@RequestParam("id") Integer id){
        log.info("删除时传入的id"+id);
        projectService.deleteProjectById(id);
        return  new JsonResult();
    }
    @ApiOperation(value = "禁用或启用多个项目")
    @PostMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(@RequestBody ValidByIds validByIds){
        log.info(validByIds.getCheckedIds()+"---------"+validByIds.getValid());
        projectService.validByIds(validByIds.getCheckedIds(),validByIds.getValid());
        return  new JsonResult();
    }
    @RequestMapping(value = "/uploadOSS", method = RequestMethod.POST)
    public String addStu(HttpServletRequest request, ProjectVO projectVO) {
        projectVO= OSSClientUtil.getProject(request,projectVO);
        projectService.saveUploadAddress(projectVO);
        return "redirect:index";
    }
}
