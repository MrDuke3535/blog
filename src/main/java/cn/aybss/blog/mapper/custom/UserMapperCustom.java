package cn.aybss.blog.mapper.custom;

import cn.aybss.blog.model.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperCustom {

    public List<User> findUser();

    public User getByNameAndPwd(@Param(value = "name") String name, @Param(value = "pwd") String pwd);

}
