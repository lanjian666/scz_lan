package com.lan.service.impl;

import com.lan.model.UserInformation;
import com.lan.repository.UserInfoRepository;
import com.lan.service.UserInfoService;
import com.lan.util.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = false,rollbackFor = MyRuntimeException.class)
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoRepository userInfoRepository;
    @Override
    public void saveUserInfo(UserInformation userInformation) {
        userInfoRepository.save(userInformation);
    }

    @Override
    public UserInformation findUserInfoByUsername(String name) {
        UserInformation userInformation=userInfoRepository.findByUsername(name);
        return userInformation;
    }

    @Override
    public List<UserInformation> findByPage(int page) {
        Pageable pageable=new PageRequest(page,5);
        Page<UserInformation> page1=  userInfoRepository.findAll(pageable);
        List<UserInformation> list = page1.getContent();
        return list;
    }
}
