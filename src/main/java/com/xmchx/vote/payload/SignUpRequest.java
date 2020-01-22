package com.xmchx.vote.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author LifeOverflow  Ex-boyfriend
 * @date 2020/1/6 19:37
 */
public class SignUpRequest {
    @NotBlank(message = "姓名不能为空")
    @Size(min = 4, max = 40,message = "姓名长度不低于4位且低于40位")
    private String name;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 15,message = "用户名长度不低于3位且低于15位")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Size(max = 40,message = "邮箱长度小于40")
    @Email(message = "邮箱不符合")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20,message = "密码长度不能小于6位且低于20位")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
