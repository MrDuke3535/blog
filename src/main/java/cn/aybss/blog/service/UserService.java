package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.User;

import java.util.Date;

public interface UserService {

    public void save(User user);

    public User findUser();

    public User getByNameAndPwd(String username,String pwd);

    public void updateLoginLastTime(Date date,Integer userId);

    public void updateUserNormal(Integer userId);

    public Integer updateError();

    public void updateLoginEnable(String enable, Integer error);

    public void updateDatum(User user);


}
