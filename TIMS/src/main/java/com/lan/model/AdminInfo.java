package com.lan.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "admin_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "管理员信息实体")
public class AdminInfo {
    @Id
    @Column(name = "admin_id",length=32)
    @GeneratedValue
    private int adminId;
    @Column(name = "user_name",nullable=false,length=30)
    private  String username;
    private  String password;
}
