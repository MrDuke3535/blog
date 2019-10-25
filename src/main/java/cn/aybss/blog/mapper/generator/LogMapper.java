package cn.aybss.blog.mapper.generator;

import cn.aybss.blog.model.domain.Log;
import cn.aybss.blog.model.domain.LogExample;

import java.util.List;

public interface LogMapper {

    public int insert(Log log);

    public List<Log> selectByExample(LogExample example);

}
