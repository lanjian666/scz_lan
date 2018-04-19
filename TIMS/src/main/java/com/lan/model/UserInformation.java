package com.lan.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_info",
        indexes = {@Index(name = "i_user", columnList = "user_id")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息实体")

public class UserInformation {
    @Id
    @Column(name = "user_id",length=32)
    @GeneratedValue
    private int userId;
    @Column(name = "user_name",nullable=false,length=30)
    private  String username;
    private  String password;
    private  String name;
    private  String sex;
    private  String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date birthday;
    private  String photoAddress;
}
