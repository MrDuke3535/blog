package cn.aybss.blog.service.impl;

import cn.aybss.blog.mapper.generator.LogMapper;
import cn.aybss.blog.model.domain.Log;
import cn.aybss.blog.model.domain.LogExample;
import cn.aybss.blog.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void save(Log log) {
        logMapper.insert(log);
    }

    @Override
    public PageInfo<Log> findLogs(int page, int limit) {
        PageHelper.startPage(page,limit);
        LogExample logExample = new LogExample();
        logExample.setOrderByClause("log_id DESC ");
        List<Log> logs = logMapper.selectByExample(logExample);
        return new PageInfo<>(logs);
    }
}
