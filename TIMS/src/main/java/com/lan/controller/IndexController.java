package com.lan.controller;

import com.lan.model.UserInformation;
import com.lan.service.ProjectService;
import com.lan.service.UserInfoService;
import com.lan.util.OSSClientUtil;
import com.lan.util.OSSClientUtil_user;
import com.lan.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理index请求
 * @author lanjian
 * */
@Controller
@Slf4j
public class IndexController {
    @Resource
    private ProjectService projectService;
    @Resource
    private UserInfoService userInfoService;
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/tour")
    public String tour(HttpServletRequest request) {
        List<ProjectVO> list=projectService.findProjectNine();
        request.setAttribute("list",list);
        return "tour";
    }
    @RequestMapping(value = "/tour_1")
    public String tour1(HttpServletRequest request) {
        List<ProjectVO> list=projectService.findProjectNine();
        request.setAttribute("list",list);
        return "tour_1";
    }
    @RequestMapping(value = "/index_1")
    public String test() {
        return "index_1";
    }
    @RequestMapping(value = "/header_1")
    public String header_1() {
        return "header_1";
    }
    @RequestMapping(value = "/domestic_tour")
    public String domestic_tour() {
        return "domestic_tour";
    }
    @RequestMapping(value = "/footer_1")
    public String footer_1() {
        return "footer_1";
    }
    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }
    @RequestMapping(value = "/toRegister",method = RequestMethod.POST)
    public String toRegister(HttpServletRequest request, UserInformation userInformation) {
        log.info("+++++"+userInformation.toString());
        userInformation= OSSClientUtil_user.getUserInfor(request,userInformation);
        log.info("+++++"+userInformation.toString());
        userInfoService.saveUserInfo(userInformation);
        return "land";
    }
    @RequestMapping(value = "/index2body")
    public String index2body() {
        return "index";
    }
    @RequestMapping(value = "/uploadOSS", method = RequestMethod.POST)
    public String  addStu(HttpServletRequest request, ProjectVO projectVO) {
        projectVO= OSSClientUtil.getProject(request,projectVO);
        projectService.saveUploadAddress(projectVO);
        return "redirect:index";
    }
}
