package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.User;

public interface UserMapper {

    public int insert(User user);

    public int updateByPrimaryKeySelective(User user);

}
