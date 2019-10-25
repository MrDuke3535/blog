package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.custom.UserMapperCustom;
import cn.aybss.blog.mapper.generator.UserMapper;
import cn.aybss.blog.model.domain.User;
import cn.aybss.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapperCustom userMapperCustom;

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findUser() {
        List<User> users = userMapperCustom.findUser();
        if(users!=null&&users.size()>0){
            return users.get(0);
        }else {
            return new User();
        }
    }

    @Override
    public User getByNameAndPwd(String name, String pwd) {
        return userMapperCustom.getByNameAndPwd(name,pwd);
    }

    @Override
    public void updateLoginLastTime(Date date, Integer userId) {
        User user = new User();
        user.setLoginLastTime(date);
        user.setUserId(userId);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateUserNormal(Integer userId) {
        User user = new User();
        user.setUserId(userId);
        user.setLoginError(0);
        user.setLoginEnable("false");
        user.setLoginLastTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Integer updateError() {
        User user = this.findUser();
        user.setLoginError(user.getLoginError()==null? 0:user.getLoginError()+1);
        userMapper.updateByPrimaryKeySelective(user);
        return user.getLoginError();
    }

    @Override
    public void updateLoginEnable(String enable, Integer error) {
        User user = this.findUser();
        user.setLoginEnable(enable);
        user.setLoginError(error);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateDatum(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
