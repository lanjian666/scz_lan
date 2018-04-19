package com.lan.controller;

import com.lan.model.AdminInfo;
import com.lan.model.UserInformation;
import com.lan.repository.AdminInfoRepository;
import com.lan.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理登陆注册请求
 * @author lanjian
 * */
@Controller
public class LandController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private AdminInfoRepository adminInfoRepository;
    @RequestMapping(value = "/land")
    public String land() {
        return "land";
    }
    @RequestMapping(value = "/toLand")
    public String toLand(HttpServletRequest request) {
        String status=request.getParameter("status");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String url=null;
        if ("yk".equals(status)){
            UserInformation userInformation=userInfoService.findUserInfoByUsername(username);
            if (userInformation==null||"".equals(userInformation)){
                url="redirect:land";
            }else if (userInformation.getPassword().equals(password)){
                return "redirect:index_1";
            }
        }else if ("admin".equals(status)){
            AdminInfo adminInfo = adminInfoRepository.findByUsername(username);
            if (adminInfo==null||"".equals(adminInfo)){
                url="redirect:land";
            }else if (adminInfo.getPassword().equals(password)){
                request.getSession().setAttribute("Session","ok");
                url= "index";
            }
        }
        return url;
    }
    @RequestMapping(value = "/user/listUI")
    public String userList(HttpServletRequest request) {
        String pageStr=request.getParameter("page");
        if (pageStr==null){
            pageStr="0";
        }
        int page=Integer.valueOf(pageStr);
        if (page<0){
            page=0;
        }
        List<UserInformation> list=userInfoService.findByPage(page);
        if (list.isEmpty()) {
            page=page-1;
            list=userInfoService.findByPage(page);
        }
        request.setAttribute("list",list);
        System.out.println(page);
        request.setAttribute("page",page);
        return "list";
    }
}
