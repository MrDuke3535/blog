package cn.aybss.blog.service;

import cn.aybss.blog.model.domain.Log;
import com.github.pagehelper.PageInfo;

public interface LogService {

    public void save(Log log);

    public PageInfo<Log> findLogs(int page,int limit);

}
