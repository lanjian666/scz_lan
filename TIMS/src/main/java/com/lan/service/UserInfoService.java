package com.lan.service;

import com.lan.model.UserInformation;
import com.lan.vo.ProjectVO;

import java.util.List;

public interface UserInfoService {
    /**
     * 用于保存userInformation
     * @param  userInformation 传入的对象
     */
    void saveUserInfo(UserInformation userInformation);
    UserInformation  findUserInfoByUsername(String name);
    List<UserInformation> findByPage(int page);
}
