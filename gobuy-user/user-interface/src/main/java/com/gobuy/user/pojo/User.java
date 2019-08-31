package com.gobuy.user.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;


@Table(name = "user")
public class User {
    @Id
    private Integer id;

    @Length(min = 4, max = 30, message = "用户名长度4-30位")
    private String username;

    // @JsonIgnore
    @Length(min = 6, max = 16, message = "密码长度6-16位")
    private String password;

    @Pattern(regexp = "1[345678]\\d{9}")
    private String phone;

    @Email(message = "请输入正确的邮箱")
    private String email;

    @JsonIgnore
    private String salt;

    private Timestamp createTime;


    public User() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
